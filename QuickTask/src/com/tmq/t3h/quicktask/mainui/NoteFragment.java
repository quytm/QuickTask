package com.tmq.t3h.quicktask.mainui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.note.ListNoteAdapter;

public class NoteFragment extends Fragment implements OnItemClickListener{
	private static final String TAG = "NoteFragment";
	private View mRootView;
	private ListNoteAdapter adapter;
	private Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.note_main_ui, null);
		mContext = container.getContext();
		initViews();
		return mRootView;
	}
	
	private void initViews(){
		adapter = new ListNoteAdapter(mContext);
		ListView listNote = (ListView) mRootView.findViewById(R.id.listNote);
		listNote.setAdapter(adapter);
		listNote.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(mContext, adapter.getItem(position), Toast.LENGTH_SHORT).show();
	}
	
}
