package com.maxq.activity;

import android.os.Bundle;

import com.maxq.BaseActivity;
import com.maxq.R;

public class MemBerActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_layout);
		statusBar(findViewById(R.id.member_layout), R.color.white);
	}

}
