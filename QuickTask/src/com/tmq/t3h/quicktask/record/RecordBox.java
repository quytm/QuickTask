package com.tmq.t3h.quicktask.record;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class RecordBox {
	private static final String TAG = "RecordBox";
	private Context mContext;
	private static File audioFile;
	private static MediaRecorder recorder;
	private static boolean recordstarted  = false; 
	
	public static void startRecord(){
		String out = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new Date());
		File sampleDir = new File(Environment.getExternalStorageDirectory(), "/RecordPhoneCall");
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		String file_name = "Rec_" + out;
		try {
			audioFile = File.createTempFile(file_name, ".amr", sampleDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(audioFile.getAbsolutePath());
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		recorder.start();
		recordstarted   = true;
	}

	public static void stopRecord(){
		if (recordstarted) {
			recorder.stop();
			recordstarted = false;
		}
	}
}
