package com.app.dto;

public class JobCost {
int jobId;
int cost;
public JobCost() {
	super();
}

public JobCost(int jobId, int cost) {
	super();
	this.jobId = jobId;
	this.cost = cost;
}

public int getJobId() {
	return jobId;
}

public void setJobId(int jobId) {
	this.jobId = jobId;
}

public int getCost() {
	return cost;
}
public void setCost(int cost) {
	this.cost = cost;
}
}
