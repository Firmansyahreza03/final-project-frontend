package com.lawencon.community.pojo.article;

import java.time.LocalDateTime;

public class PojoDataArticle {
	private String id;
	private Boolean isActive;
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	private String title;
	private String content;
	private String idIndustry;
	private String nameIndustry;
	private LocalDateTime createdAt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIdIndustry() {
		return idIndustry;
	}

	public void setIdIndustry(String idIndustry) {
		this.idIndustry = idIndustry;
	}

	public String getNameIndustry() {
		return nameIndustry;
	}

	public void setNameIndustry(String nameIndustry) {
		this.nameIndustry = nameIndustry;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
