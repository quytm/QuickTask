package com.tmq.t3h.quicktask.mainui;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.DataContactSharedPref;
import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.note.ListNoteAdapter;

public class NoteFragment extends Fragment implements	OnItemClickListener, OnClickListener{
	private static final String TAG = "NoteFragment";
	private View mRootView;
	private ListView listNote;
	private ListNoteAdapter adapter;
	private Context mContext;
	
	private View currentView = null;
	private int posViewIsClick = -1;
	
	private Dialog dialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.note_main_ui, null);
		mContext = container.getContext();
		dialog = new Dialog(mContext, R.style.PauseDialog);
		initViews();
		return mRootView;
	}
	
	private void initViews(){
		adapter = new ListNoteAdapter(mContext);
		listNote = (ListView) mRootView.findViewById(R.id.listNote);
		listNote.setAdapter(adapter);
		listNote.setOnItemClickListener(this);
	}
	private AdapterView<?> adapterView = null;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(mContext, adapter.getItem(position).note, Toast.LENGTH_SHORT).show();
		showDialogWhenClickItem(view);
		
		adapterView = parent;
		posViewIsClick = position;
		currentView = view;
		
		
		
		final AdapterView<?> p = parent;
		final int pos = position;
		/*
		view.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				p.removeViews(pos, 1);
				ListNoteAdapter adapter = (ListNoteAdapter) p.getAdapter();
				adapter.removeItem(pos);
			}
		}, 500);
		*/
	}
	EditText edtContact, edtContent;
	private void showDialogWhenClickItem(View view){
		dialog.setContentView(R.layout.note_dialog_when_click_item);
		dialog.setTitle("Edit note:");
		
		// InitViews
		edtContact 	= (EditText)		dialog.findViewById(R.id.edtNoteDialogContact);
		edtContent 	= (EditText)		dialog.findViewById(R.id.edtNoteDialogContent);
		ImageButton btnDelete	= (ImageButton)		dialog.findViewById(R.id.btnNoteDialogDelete);
		ImageButton btnCancel	= (ImageButton)		dialog.findViewById(R.id.btnNoteDialogCancel);
		ImageButton btnCopy		= (ImageButton)		dialog.findViewById(R.id.btnNoteDialogCopy);
		ImageButton btnSave		= (ImageButton)		dialog.findViewById(R.id.btnNoteDialogSave);
		
		TextView txtContact = (TextView)	view.findViewById(R.id.txtNoteItemContact);
		TextView txtContent = (TextView)	view.findViewById(R.id.txtNoteContent);
		
		// Set for Views
		edtContact.setText(txtContact.getText());
		edtContent.setText(txtContent.getText());
		
		// Set click for button
		btnDelete.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnCopy.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		
		dialog.show();
	}
	
	@Override
	public void onClick(View v) {
		DataContactSharedPref sharedPref = new DataContactSharedPref(mContext);
		
		View view = adapterView.getChildAt(posViewIsClick);
		TextView txtContent = (TextView) view.findViewById(R.id.txtNoteContent);
		switch (v.getId()) {
		case R.id.btnNoteDialogDelete:
			Toast.makeText(mContext, "Note is deleted", Toast.LENGTH_SHORT).show();
			adapterView.removeViews(posViewIsClick, 1);
			sharedPref.removeData(adapter.getItem(posViewIsClick).position);
			Log.i(TAG, "pos = " + adapter.getItem(posViewIsClick).position);
			adapter.removeItem(posViewIsClick);
			break;
		case R.id.btnNoteDialogCancel:
			Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnNoteDialogCopy:
			Toast.makeText(mContext, "Note is copied to clipboard!", Toast.LENGTH_SHORT).show();
			setClipboard(txtContent.getText().toString());
			break;
		case R.id.btnNoteDialogSave:
			Toast.makeText(mContext, "Note is changed", Toast.LENGTH_SHORT).show();
			txtContent.setText(edtContent.getText().toString());
			adapter.setItem(edtContact.getText().toString(), edtContent.getText().toString(), posViewIsClick);
			sharedPref.setNoteDataAt(adapter.getItem(posViewIsClick).position, edtContent.getText().toString());
			break;
		}
		
		dialog.dismiss();
	}
	
	// copy text to clipboard
	private void setClipboard(String text) {
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
	        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
	        clipboard.setText(text);
	    } else {
	        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
	        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
	        clipboard.setPrimaryClip(clip);
	    }
	}

}


