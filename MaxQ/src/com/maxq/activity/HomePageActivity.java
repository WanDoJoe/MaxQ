package com.maxq.activity;

import android.app.Activity;
import android.os.Bundle;

import com.achep.header2actionbar.FadingActionBarHelper;
import com.maxq.HomePageFragment;
import com.maxq.R;

public class HomePageActivity extends Activity{
	 private FadingActionBarHelper mFadingActionBarHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 mFadingActionBarHelper = new FadingActionBarHelper(getActionBar(),
	                getResources().getDrawable(R.drawable.tum));

	        if (savedInstanceState == null) {
	            getFragmentManager().beginTransaction()
	                    .add(R.id.container, new HomePageFragment())
	                    .commit();
	        }
	}
	 public FadingActionBarHelper getFadingActionBarHelper() {
	        return mFadingActionBarHelper;
	    }

}
