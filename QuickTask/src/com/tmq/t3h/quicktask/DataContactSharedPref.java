package com.tmq.t3h.quicktask;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DataContactSharedPref {
	private static final String TAG = "DataContactSharedPref";
	private SharedPreferences data;
	
	
	//-----------------------------------------------------------------------------------
	// Use for Data Contact
	//-----------------------------------------------------------------------------------
	public class DataItem{
		public String 	name,			// Name Contact
						number,			// PhoneNumber of Contact
						note,			// Note is saved
						recallTime;		// Time save/Time remine
		public int	 	recallId,		// Id when start alarm remine
						position;		// position in SharedPreferences
		public DataItem(String name, String number, String note, 
						String recallTime, int id, int pos) {
			this.name 	= name;
			this.number = number;
			this.note	= note;
			this.recallTime = recallTime;
			this.recallId	= id;
			this.position 	= pos;
		}
	}
	
	public DataContactSharedPref(Context context) {
		data = context.getSharedPreferences(CommonVL.DATA_CONTACT_SHAREPREFERENCES, Context.MODE_PRIVATE);
	}
	
	public void putData(String name, String number, String note, 
						String recallTime, int recallId){
		// Number of Contact is saved in SharedPreferences
		int indexOfContact = data.getInt(CommonVL.NUMBER_DATA_CONTACT, 0) + 1;
		if (indexOfContact>CommonVL.NUMBER_DATA_CONTACT_MAX) indexOfContact = 1;
		// Create Editor to put Data
		SharedPreferences.Editor editor = data.edit();
		
		editor.putString(CommonVL.CONTACT_NAME_			+ indexOfContact, name);
		editor.putString(CommonVL.CONTACT_PHONE_NUMBER_ + indexOfContact, number);
		editor.putString(CommonVL.CONTACT_TIME_START_ 	+ indexOfContact, recallTime);
		
		// If save Note
		if (!note.equals("null")) {
			editor.putString(CommonVL.CONTACT_NOTE_ 	+ indexOfContact, note);
			editor.putInt(CommonVL.CONTACT_RECALL_ID_	+ indexOfContact, -1);
		}
		// If save Recall Later
		else {
			editor.putInt(CommonVL.CONTACT_RECALL_ID_	+ indexOfContact, recallId);
			editor.putString(CommonVL.CONTACT_NOTE_ 	+ indexOfContact, "null");
		}
		
		editor.remove(CommonVL.NUMBER_DATA_CONTACT);
		editor.putInt(CommonVL.NUMBER_DATA_CONTACT, indexOfContact);
		
		editor.commit();
	}
	
	public DataItem getData(int position){
		int id			= data.getInt(CommonVL.CONTACT_RECALL_ID_		+ position, -1);
		String name 	= data.getString(CommonVL.CONTACT_NAME_			+ position, "null");
		String number 	= data.getString(CommonVL.CONTACT_PHONE_NUMBER_	+ position, "null");
		String note 	= data.getString(CommonVL.CONTACT_NOTE_			+ position, "null");
		String timeStart= data.getString(CommonVL.CONTACT_TIME_START_	+ position, "null");
		return new DataItem(name, number, note, timeStart, id, position);
	}
	
	public int sizeOfDataContact(){
		return data.getInt(CommonVL.NUMBER_DATA_CONTACT, 0);
	}
	
	public void removeData(int position){
		int size = sizeOfDataContact();
		
		// Out of bound
		if (position>size || position<1) {
//			Log.i(TAG, "out of bound " + position);
			return;
		}
		
		// If data is Note and Recall -> Set note="null"
		if ( (getData(position).recallId != -1)   &&   (!getData(position).note.equals("null")) ){
			setNoteDataAt(position, "null", getData(position).name);
//			Log.i(TAG, "data is recall " + position);
			return;
		}
		
		// If data is Note
		for (int i=position; i<size; i++){
			deleteDataAt(i);
			setDataAt(i, getData(i+1));
		}
		deleteDataAt(size);
		// Size = Size-1
		SharedPreferences.Editor editor = data.edit();
		editor.remove(CommonVL.NUMBER_DATA_CONTACT);
		editor.putInt(CommonVL.NUMBER_DATA_CONTACT, size-1);
		editor.commit();
	}
	
	private void deleteDataAt(int position){
		SharedPreferences.Editor editor = data.edit();
		
		// remove
		editor.remove(CommonVL.CONTACT_NAME_		+ position);
		editor.remove(CommonVL.CONTACT_PHONE_NUMBER_+ position);
		editor.remove(CommonVL.CONTACT_TIME_START_ 	+ position);
		editor.remove(CommonVL.CONTACT_NOTE_ 		+ position);
		editor.remove(CommonVL.CONTACT_RECALL_ID_	+ position);
		
		editor.commit();
		
//		Log.i(TAG, "delete at " + position);
	}
	
	private void setDataAt(int position, DataItem item){
		deleteDataAt(position);
		SharedPreferences.Editor editor = data.edit();
		
		editor.putString(CommonVL.CONTACT_NAME_			+ position, item.name);
		editor.putString(CommonVL.CONTACT_PHONE_NUMBER_ + position, item.number);
		editor.putString(CommonVL.CONTACT_TIME_START_ 	+ position, item.recallTime);
		editor.putString(CommonVL.CONTACT_NOTE_ 		+ position, item.note);
		editor.putInt(CommonVL.CONTACT_RECALL_ID_		+ position, item.recallId);
		
		editor.commit();
		
//		Log.i(TAG, "setData at " + position);
	}
	
	public void setNoteDataAt(int position, String newNote, String newContact){
		DataItem item = getData(position);
		if (item.note.equals("null")) return;
		else{
			SharedPreferences.Editor editor = data.edit();
			
			editor.remove(CommonVL.CONTACT_NOTE_ 	+ position);
			editor.remove(CommonVL.CONTACT_NAME_ 	+ position);
			editor.putString(CommonVL.CONTACT_NOTE_ + position, newNote);
			editor.putString(CommonVL.CONTACT_NAME_	+ position, newContact);
			
			editor.commit();
		}
		
//		Log.i(TAG, "setNote at " + position + ", newNode = " + newNote);
	}
	
	//-------------------------------------------------------------------------------
	// Use for setting
	//-------------------------------------------------------------------------------

	public void settingDefault(){
		SharedPreferences.Editor editor = data.edit();
		// Mode use hand
		int mode = data.getInt(CommonVL.MODE_HAND, 0);
		if (mode == 0){
			editor.putInt(CommonVL.MODE_HAND, CommonVL.MODE_HAND_RIGHT);
		}
		
		
		editor.commit();
	}
	
	
	public void setModUseHand(int type){
		SharedPreferences.Editor editor = data.edit();
		editor.remove(CommonVL.MODE_HAND);
		editor.putInt(CommonVL.MODE_HAND, type);
		editor.commit();
	}
	
	//---------------------- Get Setting ------------------------------------------------
	
	public int getModeHand(){
		return data.getInt(CommonVL.MODE_HAND, 0);
	}
	
}
