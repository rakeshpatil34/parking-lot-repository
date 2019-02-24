package com.parkinglot.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.ParkingLotSearchFilter;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.exceptions.OverlappingScheduleException;
import com.parkinglot.services.ParkingLotService;

@RestController
@RequestMapping(value="/parking-lot")
public class ParkingLotController {

	@Autowired
	private ParkingLotService lotService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<ParkingLot> getAllReadyParkingLots() throws ParseException{
		return lotService.getAllLots();
	}
	
	@RequestMapping(value="/{lotId}",method=RequestMethod.GET)
	public ParkingLot getParkingLotDetails(@PathVariable Long lotId) throws InvalidParkingLotException{
		return lotService.getLotDetails(lotId);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ParkingLot addNewParkingLot(@RequestBody ParkingLot lot) throws InvalidParkingLotException {
		return lotService.addNewParkingLot(lot);
	}
	
	@RequestMapping(value="", method=RequestMethod.PATCH)
	public ParkingLot updateParkingLot(@RequestBody ParkingLot lot) {
		return lotService.updateParkingLot(lot);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public List<ParkingLot> reserveParkingLot(@RequestBody ParkingLotSearchFilter searchFilter) throws OverlappingScheduleException, InterruptedException, InvalidParkingLotException{
		 return lotService.searchParkingLotWithinRadius(searchFilter);
	}
}
