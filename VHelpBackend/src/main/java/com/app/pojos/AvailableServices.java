package com.app.pojos;

import static com.app.pojos.RateType.HR;


public enum AvailableServices{

	  CARPENTRY(HR),PLUMBING(HR),MEDICAL_ASSISTANCE(HR),CLEANING(HR);
	  RateType rateType; 
	  
	  private AvailableServices(RateType rate) {
		  this.rateType = rate; 
	  }
	 
}
