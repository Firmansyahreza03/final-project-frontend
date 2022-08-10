package com.lawencon.community.pojo.report;
import java.time.LocalDateTime;

public class PojoReportUserByCommunityRes {
	private String nameUser;
	
	private String nameCommunity;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getNameCommunity() {
		return nameCommunity;
	}
	public void setNameCommunity(String nameCommunity) {
		this.nameCommunity = nameCommunity;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
}
