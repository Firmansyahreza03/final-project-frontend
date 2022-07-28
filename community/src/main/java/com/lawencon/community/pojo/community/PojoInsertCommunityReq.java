package com.lawencon.community.pojo.community;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PojoInsertCommunityReq {
	@NotNull(message = "must be true or false")
	private Boolean isActive;

	@NotBlank(message = "title cannot be empty")
	private String title;
	@NotBlank(message = "provider cannot be empty")
	private String provider;
	@NotBlank(message = "location cannot be empty")
	private String location;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.000'Z'")
	@NotNull(message = "start time cannot be empty")
	private LocalDateTime startAt;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.000'Z'")
	@NotNull(message = "end time cannot be empty")
	private LocalDateTime endAt;
	@NotBlank(message = "description cannot be empty")
	private String desc;
	@NotBlank(message = "code cannot be empty")
	private String code;
	@NotNull(message = "price cannot be empty")
	private Long price;
	@NotBlank(message = "id category cannot be empty")
	private String idCategory;
	@NotBlank(message = "id industry cannot be empty")
	private String idIndustry;


	private String idFile;
	private String nameFile;
	private String extFile;
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDateTime getStartAt() {
		return startAt;
	}
	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}
	public LocalDateTime getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}
	public String getIdIndustry() {
		return idIndustry;
	}
	public void setIdIndustry(String idIndustry) {
		this.idIndustry = idIndustry;
	}
	public String getIdFile() {
		return idFile;
	}
	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public String getExtFile() {
		return extFile;
	}
	public void setExtFile(String extFile) {
		this.extFile = extFile;
	}
}
