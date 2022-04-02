package com.app.dto;

import com.app.pojos.JobStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobStatusDTO {
	int jobId;
	JobStatus status;	
}
