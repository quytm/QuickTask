package com.tmq.t3h.quicktask.record;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class RecordBox {
	private static final String TAG = "RecordBox";
	private static Context mContext;
	private static File audioFile;
	private static MediaRecorder recorder;
	private static boolean recordstarted  = false; 
	
	public static void startRecord(Context context){
		mContext = context;
//		Date date = Calendar.getInstance().getTime();
		String out = new SimpleDateFormat("ddMM_HHmm").format(new Date());
//		String out = new SimpleDateFormat("ddMM_HHmm").format(date);
		File sampleDir = new File(Environment.getExternalStorageDirectory(), "/RecordPhoneCall");
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		Log.i(TAG, "sample dir = " + sampleDir.getPath());
		String fileName = "Rec_" + out;
		try {
			audioFile = File.createTempFile(fileName, ".amr", sampleDir);
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
			mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(audioFile)));
		}
	}
}
