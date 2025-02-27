package com.lawencon.community.pojo.industry;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PojoInsertIndustryReq {
	@NotNull(message = "must be true or false")
	private Boolean isActive;
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	@NotBlank(message = "name cannot be empty")
	private String name;
	@NotBlank(message = "code cannot be empty")
	private String code;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
