package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.TaskerDetails;
import com.app.pojos.Tasker;

public interface ITaskerService {
	Tasker authenticateTasker(String email,String password);
	Tasker insertTaskerDetails(Tasker tasker);
	Tasker getTaskerDetails(int taskerId);
	List<TaskerDetails> getTaskerDetailsByLocationAndSkill(String location, String skill);
	void updateOverallRating(int taskerId);
	Tasker updateTaskerDetails(Tasker tasker,int id);
	Tasker updateTaskerPassword(String password,int id);
	public Tasker insertImage(int taskerId, MultipartFile imageFile) throws IOException;
}
