package com.parkinglot.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinglot.exceptions.OverlappingScheduleException;

@Entity
public class ParkingLot {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double longitude; //x
	private Double latitude; //y
	private Double pricePerHour;
	private String description;
	private Boolean isReady;
	
//	@OneToMany
//	private Set<Schedule> schedules;
	
//	@OneToMany
//	private Set<Reservation> reservations;
	
	public ParkingLot() {
		
	}
	
	public ParkingLot(Double Longitude, Double latitude) {
		super();
		this.longitude = Longitude;
		this.latitude = latitude;
		this.pricePerHour = 20d;
//		this.schedules = new HashSet<Schedule>();
	}
	
	

	public ParkingLot(Double Longitude, Double latitude, Double pricePerHour) {
		super();
		this.longitude = Longitude;
		this.latitude = latitude;
		this.pricePerHour = pricePerHour;
//		this.schedules = new HashSet<Schedule>();
	}


//	public Boolean isAvailable(Schedule reservationSchedule) throws OverlappingScheduleException {
//		for(Schedule currSchedule: schedules) {
//			if(currSchedule.isOverLapping(reservationSchedule))
//				throw new OverlappingScheduleException(currSchedule);
//		}
//		return true;
//	}
	
	public Boolean isAvailable(Schedule reservationSchedule) throws OverlappingScheduleException {
//		for(Reservation reservation: reservations) {
//			if(reservation.getSchedule().isOverLapping(reservationSchedule))
//				throw new OverlappingScheduleException(reservation.getSchedule());
//		}
		return true;
	}


	public void reserve(Schedule reservationSchedule) throws OverlappingScheduleException {
		isAvailable(reservationSchedule);
//		schedules.add(reservationSchedule);
	} 
	
//	public void cancelReservation(Schedule reservationSchedule) {
//		schedules.contains(reservationSchedule);
//		schedules.remove(reservationSchedule);
//	}

	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double Longitude) {
		this.longitude = Longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	
	public Double getPricePerHour() {
		return pricePerHour;
	}

	@JsonIgnore
	public Double getPricePerMinute() {
		return pricePerHour/60;
	}
	
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Boolean getIsReady() {
		return isReady;
	}



	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}



	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

//	public Set<Reservation> getReservations() {
//		return reservations;
//	}
//
//	public void setReservations(Set<Reservation> reservations) {
//		this.reservations = reservations;
//	}
//	
	
}
