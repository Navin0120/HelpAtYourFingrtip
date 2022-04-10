package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.TaskerDetails;
import com.app.pojos.Tasker;

public interface ITaskerService {
	 Tasker getTaskerFromUserId(int id); 
	int getTaskerIdFromUserId(int id);
	Tasker getTaskerDetails(int taskerId);
	List<TaskerDetails> getTaskerDetailsByLocationAndSkill(String location, String skill,int page);
	public Tasker insertImage(int taskerId, MultipartFile imageFile) throws IOException;
	Tasker updateTaskerDetails(Tasker tasker,int id);
	void updateOverallRating(int taskerId) ;
	
}
