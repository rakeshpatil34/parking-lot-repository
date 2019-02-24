package com.parkinglot.exceptions;

import com.parkinglot.entities.Schedule;

public class OverlappingScheduleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OverlappingScheduleException(Schedule s) {
		super("Overlapping with "+s);
	}

}
