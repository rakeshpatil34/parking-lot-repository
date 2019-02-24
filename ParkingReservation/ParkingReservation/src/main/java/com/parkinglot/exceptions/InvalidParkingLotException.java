package com.parkinglot.exceptions;

public class InvalidParkingLotException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidParkingLotException(Long lotId) {
		super("Invalid Parking lot id: "+lotId);
	}
	
	public InvalidParkingLotException(Double longitude, Double latitude) {
		super("Parking lot already registered with longitude:"+longitude+", latitude:"+latitude);
	}
}
