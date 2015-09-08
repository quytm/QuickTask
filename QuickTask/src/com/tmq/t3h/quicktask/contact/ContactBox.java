package com.tmq.t3h.quicktask.contact;

import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.tmq.t3h.quicktask.R;
import com.tmq.t3h.quicktask.service.LayoutInWindowMgr;

public class ContactBox extends LayoutInWindowMgr{
	private ListView listContact;
	private AdapterForContactList adapter;

	@Override
	protected int setIdLayout() {
		return R.layout.contact_box;
	}

	@Override
	protected void setForLayoutParams() {
		mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
		mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
						LayoutParams.FLAG_NOT_TOUCH_MODAL |
						LayoutParams.FLAG_HARDWARE_ACCELERATED;
	}

	@Override
	protected void initViewsInLayout() {
		adapter = new AdapterForContactList(this);
		listContact = (ListView) mView.findViewById(R.id.listContact);
		listContact.setAdapter(adapter);
		
		Animation myAni = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_bottom);
//		listContact.startAnimation(myAni);
		mView.startAnimation(myAni);
		
	}
	
	@Override
	public void onDestroy() {
		removeLayoutInScreen();
		super.onDestroy();
	}

}
