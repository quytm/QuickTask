package com.tmq.t3h.quicktask.optionmenu;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmq.t3h.quicktask.R;

public class AdapterForMenuHelp extends PagerAdapter{
	private ArrayList<Item> mList;
	private LayoutInflater lf;
	
	public AdapterForMenuHelp(Context context) {
		lf = LayoutInflater.from(context);
		initArrs();
	}
	
	private void initArrs(){
		mList = new ArrayList<Item>();
		mList.add(new Item(R.drawable.help_btn_open, "Open"));
		mList.add(new Item(R.drawable.help_menu_in_call, "Menu"));
		mList.add(new Item(R.drawable.help_edit_note, "Edit Note"));
		mList.add(new Item(R.drawable.help_recall_later, "Edit Recall"));
	}
//-----------------------------------------------------------------	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mList.get(position).getTitle();
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object instantiateItem(View container, int position) {
		View v = lf.inflate(R.layout.help_item_picture, null);
		ImageView imgFace = (ImageView)	v.findViewById(R.id.imgPicture);
		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		imgFace.setImageResource(mList.get(position).getId());
		txtName.setText(mList.get(position).getTitle());
		
		((ViewPager) container).addView(v);
		
		return v;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View)object);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0.equals(arg1);
	}
	
}
