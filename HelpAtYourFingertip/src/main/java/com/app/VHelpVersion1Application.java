package com.app;

import java.io.File;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.controller.TaskerController;

@SpringBootApplication
public class VHelpVersion1Application {

	public static void main(String[] args) {
		new File(TaskerController.uploadDirectory).mkdir();
		SpringApplication.run(VHelpVersion1Application.class, args);
	}

}
