package com.parkinglot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.ParkingLotSearchFilter;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.repositories.ParkingLotRepository;

public class ParkingLotServiceTest {
     
    @InjectMocks
    private ParkingLotService parkingLotService;
     
    @Mock
    private ParkingLotRepository parkingLotRepository;
 
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     
    @Test
    public void getAllLotsTest_01()
    {
    	ParkingLot lot1 = new ParkingLot();
    	ParkingLot lot2 = new ParkingLot();
    	ParkingLot lot3 = new ParkingLot();
    	List<ParkingLot> list = new LinkedList<ParkingLot>();
    	list.add(lot1);
    	list.add(lot2);
    	list.add(lot3);
    	
    	List<ParkingLot> returnedList = null;
        when(parkingLotRepository.findAllByIsReady(true)).thenReturn(list);

    	returnedList = parkingLotService.getAllLots();

    	assertNotNull(returnedList);
    	assertEquals(list.size(), returnedList.size());
        verify(parkingLotRepository, times(1)).findAllByIsReady(true);
    }
    
    @Test
    public void getLotDetailsTest_01()
    {
    	Long parkingLotId = 1l;
    	ParkingLot parkingLot = null;
    	
    	Optional<ParkingLot> optionalParkingLot = Optional.empty();
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(optionalParkingLot);

        Exception exception=null;
        try {
        	parkingLot = parkingLotService.getLotDetails(parkingLotId);
		} catch (InvalidParkingLotException e) {
			exception = e;
		}
         
        assertNotNull(exception);
        assertNull(parkingLot);
        verify(parkingLotRepository, times(1)).findById(parkingLotId);
    }
    
    @Test
    public void getLotDetailsTest_02()
    {
    	Long parkingLotId = 1l;
    	ParkingLot parkingLot = new ParkingLot();
    	ParkingLot returnedParkingLot = null;
    	Optional<ParkingLot> optionalParkingLot = Optional.of(parkingLot);
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(optionalParkingLot);

        Exception exception=null;
        try {
        	returnedParkingLot = parkingLotService.getLotDetails(parkingLotId);
		} catch (InvalidParkingLotException e) {
			exception = e;
		}
         
        assertNull(exception);
        assertNotNull(returnedParkingLot);
        verify(parkingLotRepository, times(1)).findById(parkingLotId);
    }
    
    @Test
    public void addNewParkingLotTest_01()
    {
    	Double longitude = 15d, latitude=20d;
    	ParkingLot parkingLot = new ParkingLot(longitude, latitude);
    	ParkingLot registeredParkingLot=null;
    	
        when(parkingLotRepository.findByLongitudeAndLatitude(longitude, latitude)).thenReturn(parkingLot);

        Exception exception=null;
        try {
        	registeredParkingLot = parkingLotService.addNewParkingLot(parkingLot);
		} catch (InvalidParkingLotException e) {
			exception = e;
		}
         
        assertNotNull(exception);
        assertNull(registeredParkingLot);
        verify(parkingLotRepository, times(1)).findByLongitudeAndLatitude(longitude, latitude);
    }
    
    @Test
    public void addNewParkingLotTest_02()
    {
    	Double longitude = 15d, latitude=20d;
    	ParkingLot parkingLot = new ParkingLot(longitude, latitude);
    	ParkingLot registeredParkingLot=null;
        when(parkingLotRepository.findByLongitudeAndLatitude(longitude, latitude)).thenReturn(null);
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);
        
        Exception exception=null;
        try {
			registeredParkingLot = parkingLotService.addNewParkingLot(parkingLot);
		} catch (InvalidParkingLotException e) {
			exception = e;
		}
         
        assertNull(exception);
        assertNotNull(registeredParkingLot);
        
        verify(parkingLotRepository, times(1)).findByLongitudeAndLatitude(longitude, latitude);
        verify(parkingLotRepository, times(1)).save(registeredParkingLot);
    }
    
    @Test
    public void searchParkingLotWithinRadiusTest_01() {
    	ParkingLot lot1 = new ParkingLot(15d,20d);
    	ParkingLot lot2 = new ParkingLot(17d,22d);
    	ParkingLot lot3 = new ParkingLot(18d,18d);
    	List<ParkingLot> list = new LinkedList<ParkingLot>();
    	list.add(lot1);
    	list.add(lot2);
    	list.add(lot3);
    	
    	ParkingLotSearchFilter searchFilter = new ParkingLotSearchFilter();
    	searchFilter.setLongitude(16d);
    	searchFilter.setLatitude(18d);
    	searchFilter.setRadius(4d);
    	
		Double leftX = searchFilter.getLeftX();
		Double bottomY = searchFilter.getBottomY();
		
		Double rightX = searchFilter.getRightX();
		Double topY = searchFilter.getTopY();
		
    	when(parkingLotRepository.findParkingLotsWithinRange(leftX, rightX, bottomY, topY)).thenReturn(list);
    	
    	List<ParkingLot> returnedLots = parkingLotService.searchParkingLotWithinRadius(searchFilter);
    	assertEquals(2, returnedLots.size());
    	
    }
    
  
}