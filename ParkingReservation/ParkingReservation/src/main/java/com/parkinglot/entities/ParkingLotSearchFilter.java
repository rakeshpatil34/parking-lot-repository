package com.parkinglot.entities;

public class ParkingLotSearchFilter {
	private Double longitude;
	private Double latitude;
	private Double radius;
	
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Double getLeftX() {
		return longitude-radius;
	}

	public Double getBottomY() {
		return latitude-radius;
	}
	
	public Double getRightX() {
		return longitude+radius;
	}
	
	public Double getTopY() {
		return latitude+radius;
	}
	
	public Double getRadiusSquare() {
		return radius*radius;
	}
	public Double getSquareDistance(ParkingLot parkingLot) {
		Double tmpX = (longitude - parkingLot.getLongitude());
		Double tmpY = (latitude - parkingLot.getLatitude());
		return (tmpX * tmpX) + (tmpY * tmpY);
	}
}
