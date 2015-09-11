package com.tmq.t3h.quicktask.record;

import java.util.ArrayList;

import com.tmq.t3h.quicktask.CommonVL;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


public class MediaManager implements Runnable{
	
	private static final String TAG = "MediaManager";
	
	public static final int PLAYING = 1;
	public static final int PAUSING = 2;
	public static final int LOADING = 3;
	public static final int RELEASE = 4;
	public static final int WAITING = 5;
	
	public static final int REPEAT_IS_OFF 			= 20;
	public static final int REPEATING_CURRENT_AUDIO = 21;
	public static final int REPEATING_ALL_AUDIO 	= 22;
	
	private Context mContext;
	private ArrayList<AudioItem> listAudio = new ArrayList<AudioItem>();
	
	private MediaPlayer mPlayer;
	private int repeat;
	private int playState;
	
	
	public MediaManager(Context c) {
		mContext = c;
		mPlayer = new MediaPlayer();
		playState = WAITING;
		repeat = REPEAT_IS_OFF;
	}
//--------------------------------- Interface --------------------------------------------------
	private onUpdateDuration mListener;
	public void onUpdateDuration(onUpdateDuration listen){
		mListener = listen;
	}
	public interface onUpdateDuration{
		public void onUpdate(String currentTime, int percent);
	}
	
//------------------------- Process With Audio -----------------------------------------
	public void readAllAudio(){
		listAudio.clear();
		ContentResolver mContentR = mContext.getContentResolver();
		// Get uri of audio in Internal storage
//		Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		
		String projection[] = new String[]{
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DURATION
		};
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
	            MediaStore.Audio.Media.DATA + " LIKE '" + CommonVL.PATH_RECORD_FILE + "/%'"; 
		Cursor c = mContentR.query(uri, null, selection, null, null);
		if (c==null) return;
		
//		String columNames[] = c.getColumnNames();	// Lay tat ca cac truong trong Audio
//		for (String name: columNames){
//			Log.i(TAG, "ColumName:   " + name);
//		}
//		/*		_data: Duong dan
//		 * 		_type: Loai
//		 * 		_title: Ten
//		 * 		_display_name: Ten khong bao gom duong dan
//		 */
		
		
		c.moveToFirst();
		int indexTitle = c.getColumnIndex(MediaStore.Audio.Media.TITLE);
		int indexData = c.getColumnIndex(MediaStore.Audio.Media.DATA);
		int indexDisplayName = c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
		int indexDuration = c.getColumnIndex(MediaStore.Audio.Media.DURATION);
		
		String title, data, displayName, duration;
		while(!c.isAfterLast()){
			title = c.getString(indexTitle);
			data = c.getString(indexData);
			displayName = c.getString(indexDisplayName);
			duration = c.getString(indexDuration);
			listAudio.add(new AudioItem(title, data, displayName, duration));
			/*
			Log.i(TAG, "-----------------------------------");
			Log.i(TAG, "Title:	" + title);
			Log.i(TAG, "Data:	" + data);
			Log.i(TAG, "DisplayName:	" + displayName);
			Log.i(TAG, "Duration:	" + duration);
			*/
			c.moveToNext();
		}
		
		c.close();
		
	}
	
	public void loadAudioItem(String path){
		if (mPlayer.isPlaying() || playState==PAUSING){
			mPlayer.stop();
			mPlayer.reset();
		}
		try {
			Log.i(TAG, "playState = " + playState);
			mPlayer.setDataSource(path);
			mPlayer.prepare();
			playState = LOADING;
			Log.i(TAG, "Loading...........");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void play(){
		if (playState==WAITING){// If song is not loaded, It will play First song in list
			loadAudioItem(listAudio.get(0).getData());
		}
		if (playState==LOADING || playState==PAUSING){
			mPlayer.start();
			if (playState==LOADING) new Thread(this).start();
			playState = PLAYING;
			Log.i(TAG, "Playing................");
		}
	}
	
	public void pause(){
		if (playState==PLAYING && mPlayer.isPlaying()){
			mPlayer.pause();
			playState = PAUSING;
			Log.i(TAG, "Paused................");
		}
	}
	
	public void release(){
		if (playState==PLAYING || playState==PAUSING){
			mPlayer.stop();
		}
		mPlayer.release();
		playState = RELEASE;
	}
	
//---------------------------------- Get, Set --------------------------------------------------
	public ArrayList<AudioItem> getListAudio() {
		return listAudio;
	}

	public void setListAudio(ArrayList<AudioItem> listAudio) {
		this.listAudio = listAudio;
	}

	public int getPlayState() {
		return playState;
	}
	
	public int getRepeat(){
		return repeat;
	}
	
	public int changeRepeat(){
		switch (repeat) {
			case REPEAT_IS_OFF:
				repeat = REPEATING_CURRENT_AUDIO;
				break;
			case REPEATING_CURRENT_AUDIO:
				repeat = REPEATING_ALL_AUDIO;
				break;
			case REPEATING_ALL_AUDIO:
				repeat = REPEAT_IS_OFF;
				break;
		}
		return repeat;
	}
	
	public void seekTo(int percent){
		mPlayer.seekTo(percent*mPlayer.getDuration()/100);
	}

//---------------------------------- Runable -------------------------------------------------
	@Override
	public void run() {
		while (playState==PLAYING || playState==PAUSING){
			// getCurrentPosition is Miliseconds
			int duration = mPlayer.getCurrentPosition()/1000;	// Seconds
			int totalDuration = mPlayer.getDuration()/1000;		// Seconds
			String time = convertDuration(duration);
			mListener.onUpdate(time, totalDuration==0?100:duration*100/totalDuration);	// if totalDuration is zero then percent is 100%
			// When currentPosition is max
			if (duration>=totalDuration){
				mPlayer.stop();
				mPlayer.reset();
				playState = RELEASE;
				Log.i(TAG, "End of Audio");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//------------------------------- Other --------------------------------------------------	
	public String convertDuration(long duration){
		int minutes = (int)(duration/60);
		int seconds = (int)(duration%60);
		return 	(minutes>9 ? minutes: "0"+minutes) + " : " + 
				(seconds>9 ? seconds: "0"+seconds);
	}
	
}
