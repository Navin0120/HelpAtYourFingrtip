package com.app.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Tasker;

public interface ITaskerRepository extends JpaRepository<Tasker, Integer> {
	List<Tasker> findByCity(String city,Pageable page);
	@Query("select u from Tasker u where u.tasker.id=?1")
	Tasker getByUserId(int id);
	@Query("select u.id from Tasker u where u.tasker.id=?1")
	Integer getTaskerIdByUserId(int id);
}
