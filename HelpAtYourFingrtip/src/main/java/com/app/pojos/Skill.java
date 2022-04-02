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

}
