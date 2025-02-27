package com.lawencon.community.pojo.file;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PojoInsertFileReq {
	@NotNull(message = "must be true or false")
	private Boolean isActive;
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}

	@NotBlank(message = "name file cannot be empty")
	private String name;
	@NotBlank(message = "extension file cannot be empty")
	@Size(min = 1, max=10, message = "extension file must consist of 1 to 10 characters")
	private String extension;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
