package com.lawencon.community.pojo.user;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PojoUpdateUserReq {
	@NotBlank(message = "id can not be empty")
	private String id;
	@NotBlank(message = "Subscription status can not be empty")
	private String subscriptionStatusId;
	@NotNull(message = "must be true or false")
	private Boolean isSubscriber;
	private LocalDateTime expiredAt;
	private String fileId;
	@NotNull(message = "must be true or false")
	private Boolean isActive;
	@NotNull(message = "version can not be empty")
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubscriptionStatusId() {
		return subscriptionStatusId;
	}

	public void setSubscriptionStatusId(String subscriptionStatusId) {
		this.subscriptionStatusId = subscriptionStatusId;
	}

	public Boolean getIsSubscriber() {
		return isSubscriber;
	}

	public void setIsSubscriber(Boolean isSubscriber) {
		this.isSubscriber = isSubscriber;
	}

	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

}
