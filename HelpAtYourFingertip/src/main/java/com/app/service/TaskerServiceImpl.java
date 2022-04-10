package com.app.service;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exception.WrongInputException;
import com.app.dao.IJobRepository;
import com.app.dao.ITaskerRepository;
import com.app.pojos.Tasker;

@Service // MANDATORY to tell SC : following is spring bean , containing B.L
@Transactional // annotation meant for SC , to automatically manage txs
public class TaskerServiceImpl implements ITaskerService {
	@Autowired // autowire=byType
	private ITaskerRepository taskerDao;
	@Autowired
	private IJobRepository jobDao;
	public static String uploadDirectory=System.getProperty("user.dir")+"/src/main/resources/images";

	@Override
	public Tasker insertImage(int taskerId, MultipartFile imageFile) throws IOException {
		Tasker tasker=taskerDao.findById(taskerId).orElseThrow(()->new RuntimeException("wrong id"));
			String filname="tasker_"+taskerId+imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().length()-4);
			Path filenameAndPath=Path.of(uploadDirectory, filname);
			
				Files.write(filenameAndPath, imageFile.getBytes());
			
			tasker.setImage(filname);
			return tasker;
	}
	@Override
	public Tasker getTaskerDetails(int taskerId) {
			return taskerDao.findById(taskerId)
					.orElseThrow(() -> new WrongInputException("Tasker with ID " + taskerId + " not found!!!!!!!!!"));

	}
	
	@Override
	public List<Tasker> getTaskerDetailsByLocationAndSkill(String location, String skill,int page) {
		return taskerDao.findByCity(location,PageRequest.of(page, 5, Sort.by(Direction.DESC,"overallRating"))).stream().filter(ser -> ser.isServicePresent(skill))
				.collect(Collectors.toList());
	}

	@Override
	public void updateOverallRating(int taskerId) {
			  double sum=jobDao.SumRating(taskerId);
			  double count=jobDao.CountRating(taskerId);
			  System.out.println(sum+" "+count);
			  double overallRating=sum/count;
			  Tasker tasker = taskerDao.findById(taskerId).orElseThrow(()->new WrongInputException("Tasker id not found"));
			  tasker.setOverallRating(round(overallRating,1));
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	@Override
	public Tasker getTaskerFromUserId(int id) {
		return taskerDao.getByUserId( id);
	}
	@Override
	public int getTaskerIdFromUserId(int id) {
		return taskerDao.getTaskerIdByUserId(id);
		
	}



}
