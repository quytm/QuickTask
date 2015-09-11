package com.tmq.t3h.quicktask.record;

public class AudioItem {
	private String	title,
					data,
					displayName,
					duration;
	
	public AudioItem(String t, String d, String di, String du) {
		title = t;
		data = d;
		displayName = di;
		duration = du;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
}
