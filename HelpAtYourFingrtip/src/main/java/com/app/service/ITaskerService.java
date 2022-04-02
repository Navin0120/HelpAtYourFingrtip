package com.app.service;

import java.util.List;

import com.app.dto.TaskerDetails;
import com.app.pojos.Tasker;

public interface ITaskerService {
	Tasker authenticateTasker(String email,String password);
	Tasker insertTaskerDetails(Tasker tasker);
	Tasker getTaskerDetails(int taskerId);
	List<TaskerDetails> getTaskerDetailsByLocationAndSkill(String location, String skill);
	void updateOverallRating(int taskerId);
}
