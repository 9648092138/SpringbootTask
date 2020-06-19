package com.op.task.entity;
import javax.validation.constraints.Size;

public class MobileRequest {
	@Size(max = 10,min = 10)
	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
