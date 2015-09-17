package com.tmq.t3h.quicktask.record;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioItem {
	private static final String TAG = "AudioItem";
	private String	title,
					data,
					displayName,
					duration,
					size,
					date;
	
	public AudioItem(String t, String d, String di, String du, String siz, String date_) {
		title = t;
		data = d;
		displayName = di;
		duration = du;
		size = convertTime(siz);
		date = convertDate(date_);
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// ----------------------- Other -------------------------------
	private String convertTime(String timeString){
		String value = "";
		long time = Long.parseLong(timeString);
		if (time<1000) value += (time + "B");
		else {
			DecimalFormat df = new DecimalFormat("###.##");
			double timeD = (((double)time)/1000);
			value += (df.format(timeD) + "KB");
		}
		return value;
	}
	
	private String convertDate(String dateString){
		long seconds = Long.parseLong(dateString);
		String out = new SimpleDateFormat("dd/MM/yyyy    HH:mm").format(new Date(seconds*1000));
		return out;
	}
}
