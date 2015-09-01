package com.tmq.t3h.quicktask;

import android.content.Context;
import android.content.SharedPreferences;

public class DataContactSharedPref {
	private SharedPreferences dataContact;
	
	public class DataItem{
		public String 	name,
						number,
						note,
						recallTime;
		public int	 	recallId,
						position;
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
		dataContact = context.getSharedPreferences(CommonVL.DATA_CONTACT_SHAREPREFERENCES, Context.MODE_PRIVATE);
	}
	
	public void putData(String name, String number, String note, 
						String recallTime, int recallId){
		// Number of Contact is saved in SharedPreferences
		int indexOfContact = dataContact.getInt(CommonVL.NUMBER_DATA_CONTACT, 0) + 1;
		if (indexOfContact>CommonVL.NUMBER_DATA_CONTACT_MAX) indexOfContact = 1;
		// Create Editor to put Data
		SharedPreferences.Editor editor = dataContact.edit();
		
		editor.putString(CommonVL.CONTACT_NAME_			+ indexOfContact, name);
		editor.putString(CommonVL.CONTACT_PHONE_NUMBER_ + indexOfContact, number);
		
		
		// If save Note
		if (note!=null) {
			editor.putString(CommonVL.CONTACT_NOTE_ 		+ indexOfContact, note);
		}
		// If save Recall Later
		else if (recallTime!=null){
			editor.putInt(CommonVL.CONTACT_RECALL_ID_		+ indexOfContact, recallId);
			editor.putString(CommonVL.CONTACT_TIME_START_ 	+ indexOfContact, recallTime);
		}
		
		editor.remove(CommonVL.NUMBER_DATA_CONTACT);
		editor.putInt(CommonVL.NUMBER_DATA_CONTACT, indexOfContact);
		
		editor.commit();
	}
	
	public DataItem getData(int position){
		int id			= dataContact.getInt(CommonVL.CONTACT_RECALL_ID_		+ position, -1);
		String name 	= dataContact.getString(CommonVL.CONTACT_NAME_			+ position, "null");
		String number 	= dataContact.getString(CommonVL.CONTACT_PHONE_NUMBER_	+ position, "null");
		String note 	= dataContact.getString(CommonVL.CONTACT_NOTE_			+ position, "null");
		String timeStart= dataContact.getString(CommonVL.CONTACT_TIME_START_	+ position, "null");
		return new DataItem(name, number, note, timeStart, id, position);
	}
	
	public int sizeOfDataContact(){
		return dataContact.getInt(CommonVL.NUMBER_DATA_CONTACT, 0);
	}
	
	public void removeData(int position){
		
	}
}
