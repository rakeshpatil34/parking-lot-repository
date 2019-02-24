package com.parkinglot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.entities.Reservation;
import com.parkinglot.entities.Schedule;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.exceptions.OverlappingScheduleException;
import com.parkinglot.exceptions.UserNotFoundException;
import com.parkinglot.services.ReservationService;

@RestController
@RequestMapping(value="/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	
	@RequestMapping(value="/parking-lot/{lotId}",method=RequestMethod.GET)
	public List<Reservation> getAllReservationsByParkingLot(@PathVariable Long lotId) throws InvalidParkingLotException{
		return reservationService.getAllReservationsByParkingLot(lotId);
	}

	@RequestMapping(value="/user/{userId}",method=RequestMethod.GET)
	public List<Reservation> getAllReservationsByUser(@PathVariable Long userId) throws InvalidParkingLotException{
		return reservationService.getAllReservationsByUser(userId);
	}

	@RequestMapping(value="/reserve/{lotId}", method=RequestMethod.POST)
	public Reservation reserveParkingLot(@PathVariable Long lotId, @RequestBody Schedule schedule) throws OverlappingScheduleException, InterruptedException, InvalidParkingLotException, UserNotFoundException{
		 return reservationService.reserveParkingLot(lotId, schedule);
	}
	
	@RequestMapping(value="/cancel/{reservationId}", method=RequestMethod.GET)
	public Reservation cancelReservation(@PathVariable Long reservationId) throws Exception{
		 return reservationService.cancelReservation(reservationId);
	}
	
}
