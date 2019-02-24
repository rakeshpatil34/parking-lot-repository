package com.parkinglot.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByPhoneNumber(String phoneNumber);
	List<User> findAll();
}
