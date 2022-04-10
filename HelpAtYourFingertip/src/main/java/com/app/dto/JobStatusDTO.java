package com.app.dto;

import com.app.pojos.JobStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class JobStatusDTO {
	int jobId;
	JobStatus status;
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	public JobStatusDTO(int jobId, JobStatus status) {
		super();
		this.jobId = jobId;
		this.status = status;
	}
	public JobStatusDTO() {
		super();
	}
	
	
}
