package com.app.dao;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.pojos.Tasker;

public interface ITaskerRepository extends JpaRepository<Tasker, Integer> {
	Optional<Tasker> findByEmailAndPassword(String em,String pass);
	List<Tasker> findByCity(String city);
}