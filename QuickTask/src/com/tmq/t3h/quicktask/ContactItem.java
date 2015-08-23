package com.tmq.t3h.quicktask;

public class ContactItem {
	private String name, phoneNumber;
	
	public ContactItem(String name, String number) {
		this.name = name;
		this.phoneNumber = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
