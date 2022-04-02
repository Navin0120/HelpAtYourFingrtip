package com.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.WrongInputException;
import com.app.dao.IJobRepository;
import com.app.dao.ITaskerRepository;
import com.app.dto.TaskerDetails;
import com.app.pojos.Tasker;

@Service // MANDATORY to tell SC : following is spring bean , containing B.L
@Transactional // annotation meant for SC , to automatically manage txs
public class TaskerServiceImpl implements ITaskerService {
	@Autowired // autowire=byType
	private ITaskerRepository taskerDao;
	@Autowired
	private IJobRepository jobDao;
	@Override
	public Tasker authenticateTasker(String email, String password) {
		return taskerDao.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new WrongInputException("Invalid Credentials!!!!!"));
	}
	@Override
	public Tasker insertTaskerDetails(Tasker tasker) {
		if(tasker.getPassword().equals(tasker.getConfirmPassword())) {
			return taskerDao.save(tasker);
		}
		else {
			throw new WrongInputException("Confirm password should be same as password");
		}
	}
	@Override
	public Tasker getTaskerDetails(int taskerId) {

			return taskerDao.findById(taskerId)
					.orElseThrow(() -> new WrongInputException("Tasker Id not found"));

	}
	
	@Override
	public List<TaskerDetails> getTaskerDetailsByLocationAndSkill(String location, String skill) {
		List<TaskerDetails> taskersDetails = new ArrayList<>();
		taskerDao.findByCity(location).stream().filter(ser -> ser.isServicePresent(skill))
				.forEach(tasker -> taskersDetails.add(new TaskerDetails(tasker.getFirstName(), tasker.getLastName(),
						tasker.getContactNo(), tasker.getOverallRating(), tasker.getBio())));
		return taskersDetails;
	}

	@Override
	public void updateOverallRating(int taskerId) {
			  double sum=jobDao.SumRating(taskerId);
			  double count=jobDao.CountRating(taskerId);
			  double overallRating=sum/count;
			  Tasker tasker = taskerDao.findById(taskerId).orElseThrow(()->new WrongInputException("Tasker Id not found"));
			  tasker.setOverallRating(overallRating);
	}

}
