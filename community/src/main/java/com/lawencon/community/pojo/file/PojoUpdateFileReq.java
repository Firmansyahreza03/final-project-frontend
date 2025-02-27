package com.lawencon.community.pojo.file;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PojoUpdateFileReq {
	@NotNull(message = "Id cannot be empty")
	private String id;
	@NotNull(message = "Version cannot be empty")
	private Integer version;
	@NotNull(message = "must be true or false")
	private Boolean isActive;

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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
