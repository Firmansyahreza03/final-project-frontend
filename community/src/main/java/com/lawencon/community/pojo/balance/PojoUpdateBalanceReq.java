package com.lawencon.community.pojo.balance;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class PojoUpdateBalanceReq {
	@NotNull(message = "Id cannot be empty")
	private String id;
	@NotNull(message = "Version cannot be empty")
	private Integer version;
	@NotNull(message = "must be true or false")
	private Boolean isActive;

	@NotNull(message = "balance cannot be empty")
	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
