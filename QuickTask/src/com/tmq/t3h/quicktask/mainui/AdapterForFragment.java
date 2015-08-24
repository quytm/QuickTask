package com.tmq.t3h.quicktask.mainui;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterForFragment extends FragmentPagerAdapter{
	private ArrayList<Fragment> listFragment; 

	public AdapterForFragment(FragmentManager fm) {
		super(fm);
		listFragment = new ArrayList<Fragment>();
		listFragment.add((NoteFragment)(new NoteFragment()));
		listFragment.add((NoteFragment)(new NoteFragment()));
		listFragment.add((NoteFragment)(new NoteFragment()));
		listFragment.add((NoteFragment)(new NoteFragment()));
	}
	

	@Override
	public Fragment getItem(int arg0) {
		return listFragment.get(arg0);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}

}
