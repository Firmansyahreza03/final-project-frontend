package com.lawencon.community.pojo.user;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PojoInsertUserReq {

	@NotBlank(message = "email can not be empty")
	private String userEmail;
	@NotBlank(message = "password can not be empty")
	private String userPassword;
	private String subscriptionStatusId;
	@NotNull(message = "must be true or false")
	private Boolean isSubscriber;
	private LocalDateTime expiredAt;
	@NotBlank(message = "verification code can not be empty")
	private String verificationCode;
	@NotBlank(message = "role can not be empty")
	private String roleId;
	private String fileId;
	private String balanceId;
	@NotNull(message = "must be true or false")
	private Boolean isActive;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
