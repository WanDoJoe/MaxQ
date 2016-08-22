package com.maxq.activity;

import android.app.ActionBar;
import android.os.Bundle;

import com.maxq.BaseActivity;
import com.maxq.R;

public class LoginActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_index_item);
		setTitle("登录页");
		ActionBar actionBar = getActionBar();  
		actionBar.setDisplayHomeAsUpEnabled(true); 
	}

}
