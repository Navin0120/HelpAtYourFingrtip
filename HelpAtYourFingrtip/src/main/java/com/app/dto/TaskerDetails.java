package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskerDetails {
	private String firstName;
	private String lastName;
	private String contactNo;
	private double overallRating;
	private String bio;

}
