package com.app.dao;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Job;
import com.app.pojos.JobStatus;

public interface IJobRepository extends JpaRepository<Job, Integer> {
/*	@Modifying
	@Query("update Job j set j.jobStatus=:status where j.id=:jobId")
	int setJobStatus(@Param(value = "status") JobStatus status, @Param(value = "jobId") int jobId);*/

	@Query("select j FROM Job j WHERE j.tasker.id=:taskerId AND j.jobStatus= 'BOOKED'")
	List<Job> findJobsBytaskerIdAndStatus(@Param(value = "taskerId") int taskerId,Pageable page);

	@Query("select sum(j.rating) from Job j where j.tasker.id=?1")
	int SumRating(int taskerId);

	@Query("select count(j.rating) from Job j where j.tasker.id=?1 and j.rating>0")
	int CountRating(int taskerId);

	@Query("select j from Job j where j.customer.id=?1 and j.jobStatus=?2 and j.paymentStatus=?3 and j.rating=0")
	List<Job> getJobDetailsByCustomerAndStatus(int custId, JobStatus status, boolean st,Pageable page);

	@Query("select j FROM Job j WHERE j.tasker.id=:taskerId AND j.jobStatus= 'PENDING'")
	List<Job> findPendingTasksByTaskerId(@Param(value = "taskerId") int taskerId,Pageable page);
	
	@Query("select j FROM Job j WHERE j.tasker.id=:taskerId AND j.jobStatus IN( 'PENDING'  ,'COMPLETED')")
	List<Job> findTaskHistoryByTaskerId(@Param(value = "taskerId") int taskerId,Pageable page);
	
	@Query("select j FROM Job j WHERE j.customer.id=:customerId AND j.jobStatus IN( 'PENDING'  ,'COMPLETED')")
	List<Job> findTaskHistoryByCustomerId(@Param(value = "customerId") int customerId,Pageable page);

	@Query("select j FROM Job j WHERE j.customer.id=:customerId AND j.jobStatus= 'BOOKED' ")
	List<Job> findBookedTaskByCustomerId(@Param(value = "customerId") int customerId,Pageable page);
}
