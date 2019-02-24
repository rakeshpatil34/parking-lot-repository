package com.parkinglot.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglot.entities.ParkingLot;
import com.parkinglot.entities.ParkingLotSearchFilter;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.repositories.ParkingLotRepository;

@Service
public class ParkingLotService {
	
	@Autowired
	private ParkingLotRepository lotRepository;
	
	@Transactional(readOnly=true)
	public List<ParkingLot> getAllLots() {
		return lotRepository.findAllByIsReady(true);
	}
	
	@Transactional(readOnly=true)
	public ParkingLot getLotDetails(Long id) throws InvalidParkingLotException {
		Optional<ParkingLot> optionalParkingLot = lotRepository.findById(id);
		if (optionalParkingLot.isPresent())
			return optionalParkingLot.get();
		
		throw new InvalidParkingLotException(id);
	}
	
	@Transactional(readOnly = true)
	public ParkingLot addNewParkingLot(ParkingLot lot) throws InvalidParkingLotException {
		ParkingLot existingParkingLot = lotRepository.findByLongitudeAndLatitude(lot.getLongitude(), lot.getLatitude());
		if (existingParkingLot == null) {
			lot.setId(null);
			return lotRepository.save(lot);
		}
		throw new InvalidParkingLotException(lot.getLongitude(), lot.getLatitude());
	}
	
	@Transactional(rollbackFor=Exception.class, isolation=Isolation.REPEATABLE_READ)
	public ParkingLot updateParkingLot(ParkingLot lot) {
		return lotRepository.save(lot);
	}
	
	public List<ParkingLot> searchParkingLotWithinRadius(ParkingLotSearchFilter searchFilter) {
		Double leftX = searchFilter.getLeftX();
		Double bottomY = searchFilter.getBottomY();
		
		Double rightX = searchFilter.getRightX();
		Double topY = searchFilter.getTopY();
		Double rSquare = searchFilter.getRadiusSquare();
		
		List<ParkingLot> parkingLots = lotRepository.findParkingLotsWithinRange(leftX, rightX, bottomY, topY);
		List<ParkingLot> parkingLotsWithinRadius = new LinkedList<ParkingLot>();
		
		for (ParkingLot parkingLot : parkingLots) {
			Double squareDistance = searchFilter.getSquareDistance(parkingLot);

			if (squareDistance <= rSquare)
				parkingLotsWithinRadius.add(parkingLot);
		}
		
		return parkingLotsWithinRadius;
	}
}
