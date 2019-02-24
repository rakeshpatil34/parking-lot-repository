package com.parkinglot.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entities.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	List<Reservation> findAllByParkingLotIdAndIsCancelled(Long parkingLotId, Boolean isCancelled);
	List<Reservation> findAllByUserId(Long userId);
}
