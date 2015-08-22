package com.tmq.t3h.quicktask.recall;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;

public class RecallLaterBox extends LayoutInWindowMgr implements
																OnClickListener, 
																OnSeekBarChangeListener{
	private static final String TAG = "RecallLaterBox";
	private TextView txtAmountOfHour, txtAmountOfMinute;
	private SeekBar sbrHour, sbrMinute;
	private Button btnRecallLater;

	@Override
	protected int setIdLayout() {
		Log.i(TAG, "setLayout");
		return R.layout.recall_later_box;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_FOCUSABLE |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
		mParams.y = 200;
	}

	@Override
	protected void initViewsInLayout() {
		txtAmountOfHour = (TextView) mView.findViewById(R.id.txtRecallAmuontOfHour);
		txtAmountOfMinute = (TextView) mView.findViewById(R.id.txtRecallAmuontOfMinute);
		sbrHour = (SeekBar) mView.findViewById(R.id.sbrRecallHour);
		sbrMinute = (SeekBar) mView.findViewById(R.id.sbrRecallMinute);
		btnRecallLater = (Button) mView.findViewById(R.id.btnRecallLater);
		
		sbrHour.setOnSeekBarChangeListener(this);
		sbrMinute.setOnSeekBarChangeListener(this);
		
		btnRecallLater.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Toast.makeText(this, 
				"Recall later: " + txtAmountOfHour.getText() + "h " + txtAmountOfMinute.getText() + "m.", 
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.sbrRecallHour:
			txtAmountOfHour.setText(""+progress);
			break;
		case R.id.sbrRecallMinute:
			txtAmountOfMinute.setText(""+progress);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	
}