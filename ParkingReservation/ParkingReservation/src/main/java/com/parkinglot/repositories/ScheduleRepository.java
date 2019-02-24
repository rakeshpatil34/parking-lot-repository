package com.parkinglot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entities.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long>{

}
