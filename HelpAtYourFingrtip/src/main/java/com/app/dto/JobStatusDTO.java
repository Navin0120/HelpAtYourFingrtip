package com.app.dto;

import com.app.pojos.JobStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobStatusDTO {
	int jobId;
	JobStatus status;	
}
