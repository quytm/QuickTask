package com.tmq.t3h.quicktask;

import android.content.Context;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class TouchToDeleteItem implements OnTouchListener{
	private static final String TAG = "TouchToDeleteItem";
	private Context mContext;
	private Animation myAni;
	private float 	xDown, yDown,
					xPre, yPre;	
	
	public TouchToDeleteItem(Context c) {
		mContext = c;
		myAni = AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_out_bottom);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			xPre = v.getTranslationX();
			yPre = v.getTranslationY();
			v.setBackgroundColor(mContext.getResources().getColor(android.R.color.darker_gray));
			break;
		case MotionEvent.ACTION_MOVE:
			float xMove = event.getRawX() - xDown;
			float yMove = event.getRawY() - yDown;
			v.setTranslationX(xPre + xMove);
//			mParams.y = (int)(yPre + yMove);
			
			float x = event.getRawX();//v.getTranslationX();
			float y = event.getRawY();//v.getTranslationY();
			Log.i(TAG, x + "..." + y);
			if (x<0 || x>=v.getWidth() || y<0 || y>=v.getHeight()) {	// Touch is out of bound
				v.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
				Log.i(TAG, "out of bound"+v.getWidth() + ","  + v.getHeight());
			}
			
//			if (Math.abs(xMove)>Math.abs(yMove)){
			if (v.getTranslationX()>100){
				v.startAnimation(myAni);
			}
			else {
				v.setTranslationX(0);
				TextView txtNote = (TextView) v.findViewById(R.id.txtNoteContent);
//				Toast.makeText(mContext, txtNote.getText(), Toast.LENGTH_SHORT).show();
			}
			Log.i(TAG, "... getTranX = " + v.getTranslationX());
			break;
		case MotionEvent.ACTION_UP:
			v.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
			break;
		default:
			break;
		}
		
		
		return true;
	}
	
	

}
