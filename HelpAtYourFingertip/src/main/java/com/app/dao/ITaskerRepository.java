package com.app.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Tasker;

public interface ITaskerRepository extends JpaRepository<Tasker, Integer> {
	//Optional<Tasker> findByEmailAndPassword(String em,String pass);
	List<Tasker> findByCity(String city,Pageable page);
	/*
	 * @Query("Select t from Tasker t where t.city=?1 and ?2 member of t.services")
	 * List<Tasker> findByCityAndSkill(String city,String skill,Pageable page);
	 */
	@Query("select u from Tasker u where u.tasker.id=?1")
	Tasker getByUserId(int id);
	@Query("select u.id from Tasker u where u.tasker.id=?1")
	int getTaskerIdByUserId(int id);
}
