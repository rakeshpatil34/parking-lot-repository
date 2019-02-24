package com.parkinglot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.Reservation;
import com.parkinglot.entities.Schedule;
import com.parkinglot.entities.User;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.exceptions.OverlappingScheduleException;
import com.parkinglot.exceptions.UserNotFoundException;
import com.parkinglot.repositories.ParkingLotRepository;
import com.parkinglot.repositories.ReservationRepository;
import com.parkinglot.repositories.ScheduleRepository;
import com.parkinglot.repositories.UserRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ParkingLotRepository lotRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Transactional(readOnly=true)
	public List<Reservation> getAllReservationsByUser(Long userId) throws InvalidParkingLotException {
		return reservationRepository.findAllByUserId(userId);
	}
	
	@Transactional(readOnly=true)
	public List<Reservation> getAllReservationsByParkingLot(Long parkingLotId) throws InvalidParkingLotException {
		return reservationRepository.findAllByParkingLotIdAndIsCancelled(parkingLotId, false);
	}
	
	@Transactional(rollbackFor=Exception.class, isolation=Isolation.REPEATABLE_READ)
	public Reservation reserveParkingLot(Long lotId, Schedule schedule) throws OverlappingScheduleException, InterruptedException, InvalidParkingLotException, UserNotFoundException {
		Optional<ParkingLot> parkingLotOptional = lotRepository.findById(lotId);
		Optional<User> optionalUser = userRepository.findById(schedule.getUserId());
		
		if(parkingLotOptional.isPresent() && optionalUser.isPresent()) {
			List<Reservation> reservations = reservationRepository.findAllByParkingLotIdAndIsCancelled(lotId, false);
			for(Reservation reservation: reservations) {
				if(reservation.getSchedule().isOverLapping(schedule))
					throw new OverlappingScheduleException(reservation.getSchedule());
			}
			
			Double cost = parkingLotOptional.get().getPricePerMinute()*schedule.getTotalReservationMinutes();
			schedule = scheduleRepository.save(schedule);
			
			Reservation newReservation = new Reservation();
			newReservation.setParkingLot(parkingLotOptional.get());
			newReservation.setSchedule(schedule);
			newReservation.setUser(optionalUser.get());
			newReservation.setCost(cost);
			newReservation.setIsCancelled(false);
			
			reservationRepository.save(newReservation);
			return newReservation;
		}
		
		if(parkingLotOptional.isPresent())
			throw new UserNotFoundException(schedule.getUserId());
		
		throw new InvalidParkingLotException(lotId);
	}
	
	
	@Transactional(rollbackFor=Exception.class, isolation=Isolation.REPEATABLE_READ)
	public Reservation cancelReservation(Long reservationId) throws Exception {
		Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
		
		if(optionalReservation.isPresent()) {
			Reservation reservation = optionalReservation.get();
			if(reservation.getIsCancelled())
				throw new Exception("Reservation with id: "+reservationId+" already cancelled.");
			
			reservation.setIsCancelled(true);
			return reservationRepository.save(reservation);
		}
		
		throw new Exception("Invalid reservation id: "+reservationId);
	}

	
}
