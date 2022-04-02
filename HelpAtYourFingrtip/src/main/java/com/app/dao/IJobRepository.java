package com.app.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Job;
import com.app.pojos.JobStatus;

public interface IJobRepository extends JpaRepository<Job, Integer> {
	@Query("select j FROM Job j WHERE j.tasker.id=:taskerId AND j.jobStatus= 'BOOKED'")
	List<Job> findJobsBytaskerIdAndStatus(@Param(value="taskerId") int taskerId);
	@Query("select sum(j.rating) from Job j where j.tasker.id=?1")
	int SumRating(int taskerId);
	@Query("select count(j.rating) from Job j where j.tasker.id=?1 and j.rating>0")
	int CountRating(int taskerId);
	@Query("select j from Job j where j.customer.id=?1 and j.jobStatus=?2 and j.paymentStatus=?3")
	List<Job> getJobDetailsByCustomerAndStatus(int custId,JobStatus status,boolean st);
}
