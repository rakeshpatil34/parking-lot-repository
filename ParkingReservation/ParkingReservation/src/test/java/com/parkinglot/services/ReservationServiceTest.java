package com.parkinglot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

public class ReservationServiceTest {
     
    @InjectMocks
    private ReservationService reservationService;
     
    @Mock
    private ReservationRepository reservationRepository;
    
    @Mock
	private ParkingLotRepository lotRepository;
	
    @Mock
	private ScheduleRepository scheduleRepository;
	
    @Mock
	private UserRepository userRepository;

 
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     
    @Test
    public void reserveParkingLotTest_01() throws OverlappingScheduleException, InterruptedException, InvalidParkingLotException, UserNotFoundException
    {
    	ParkingLot lot1 = new ParkingLot(15d, 17d);
    	lot1.setPricePerHour(20d);
    	Optional<ParkingLot> optionalLot = Optional.of(lot1);
    	
    	User user = new User(1l, "Rakesh", "9028208008");
    	Optional<User> optionalUser = Optional.of(user);
    	
    	Date sTime = Calendar.getInstance().getTime();
    	Date eTime = Calendar.getInstance().getTime();
    	eTime.setHours(sTime.getHours()+2);
    	Schedule schedule = new Schedule(sTime, eTime);
    	schedule.setUserId(1l);
    	
    	Long lotId = 10l, userId=1l;
    	
    	Reservation reservation = new Reservation();
    	List<Reservation> reservations = new LinkedList<Reservation>();
    	
        when(lotRepository.findById(lotId)).thenReturn(optionalLot);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        when(reservationRepository.findAllByParkingLotIdAndIsCancelled(lotId, false)).thenReturn(reservations);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        
    	Reservation reservation2 = reservationService.reserveParkingLot(lotId, schedule);

    	assertNotNull(reservation2);
    	assertEquals(40, reservation2.getCost(), 0.01d);
    }
    
    @Test
    public void reserveParkingLotTest_02()
    {
    	ParkingLot lot1 = new ParkingLot(15d, 17d);
    	lot1.setPricePerHour(20d);
    	Optional<ParkingLot> optionalLot = Optional.of(lot1);
    	
    	User user = new User(1l, "Rakesh", "9028208008");
    	Optional<User> optionalUser = Optional.of(user);
    	
    	Date sTime = Calendar.getInstance().getTime();
    	Date eTime = Calendar.getInstance().getTime();
    	eTime.setHours(sTime.getHours()+2);
    	Schedule schedule = new Schedule(sTime, eTime);
    	schedule.setUserId(1l);
    	
    	Long lotId = 10l, userId=1l;
    	
    	Reservation reservation = new Reservation();
    	List<Reservation> reservations = new LinkedList<Reservation>();
    	
        when(lotRepository.findById(lotId)).thenReturn(optionalLot);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        when(reservationRepository.findAllByParkingLotIdAndIsCancelled(lotId, false)).thenReturn(reservations);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        
    	Reservation reservation2=null;
    	Exception exception=null;
		try {
			reservation2 = reservationService.reserveParkingLot(11l, schedule);
		} catch (OverlappingScheduleException | InterruptedException | InvalidParkingLotException
				| UserNotFoundException e) {
			exception = e;
		}

    	assertNull(reservation2);
    	assertNotNull(exception);
    }
    
    
    @Test
    public void cancelReservationTest_01()
    {
    	Long reservationId=1l;
    	
    	Reservation reservation = new Reservation();
    	reservation.setIsCancelled(false);
    	Optional<Reservation> optionalReservation = Optional.of(reservation);
    	
        when(reservationRepository.findById(reservationId)).thenReturn(optionalReservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        
    	Reservation reservation2=null;
    	Exception exception=null;

    	try {
			reservation2 = reservationService.cancelReservation(reservationId);
		} catch (Exception e) {
			exception = e;
		}

    	assertNull(exception);
    	assertNotNull(reservation2);
    	assertTrue(reservation.getIsCancelled());
    }
    
      
  
}