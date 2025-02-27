package com.lawencon.community.pojo.memberCommunity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PojoInsertMemberCommunityReq {
	@NotNull(message = "must be true or false")
	private Boolean isActive;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@NotBlank(message = "id comunity cannot be empty")
	private String idCommunity;
	@NotBlank(message = "id payment cannot be empty")
	private String idPayment;

	public String getIdCommunity() {
		return idCommunity;
	}

	public void setIdCommunity(String idCommunity) {
		this.idCommunity = idCommunity;
	}

	public String getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(String idPayment) {
		this.idPayment = idPayment;
	}
}
