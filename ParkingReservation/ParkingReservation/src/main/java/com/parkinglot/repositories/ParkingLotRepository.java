package com.parkinglot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entities.ParkingLot;

@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Long>{
	List<ParkingLot> findAllByIsReady(Boolean isReady);
	
	ParkingLot findByLongitudeAndLatitude(Double longitude, Double latitude);
	
	@Query(value="select * from parking_lot where longitude >= ?1 and longitude <= ?2 and latitude >= ?3 and latitude <= ?4",nativeQuery=true)
	List<ParkingLot> findParkingLotsWithinRange(Double leftX, Double rightX, Double bottomY, Double topY);
}
