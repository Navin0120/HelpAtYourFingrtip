package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Embeddable
public class Skill{
	
	@NotBlank(message="Skill should not be blank")
	@Enumerated(value=EnumType.STRING)
	@Column(length=20)
	private AvailableServices skillName;
	@NotNull(message="Rate can't be null")
	private double rate;
	
	public Skill() {
		super();
	}
	
	public Skill(@NotBlank(message = "Skill should not be blank") AvailableServices skillName,
			@NotNull(message = "Rate can't be null") double rate) {
		super();
		this.skillName = skillName;
		this.rate = rate;
	}

	public void setSkillName(AvailableServices skillName) {
		this.skillName = skillName;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	public AvailableServices getSkillName() {
		return skillName;
	}


}
