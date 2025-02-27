package com.lawencon.community.pojo.user;

import javax.validation.constraints.NotBlank;

public class PojoUpdateUserPassReq {
	@NotBlank(message = "Old password can't be null")
	private String oldPass;

	@NotBlank(message = "New password can't be null")
	private String newPass;

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
}
