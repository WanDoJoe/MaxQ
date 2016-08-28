package com.maxq.activity;



import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maxq.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.readystatesoftware.systembartint.SystemBarTintManager.SystemBarConfig;

@SuppressWarnings("deprecation")
public class IndexActivity extends ActivityGroup implements RadioGroup.OnCheckedChangeListener{
	
	RadioButton homeRb,productRb,cartBr,memberRb;
	 RadioGroup tabRadioGroup;
	 LinearLayout  index_containerBody;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_layout);
		initView();
		initLayout();
		setRefresh();
		 
	}

	private void setRefresh() {
	}

	private void initView() {
		tabRadioGroup=(RadioGroup) findViewById(R.id.index_activity_tab);
		index_containerBody=(LinearLayout ) findViewById(R.id.index_containerBody);
		tabRadioGroup.setOnCheckedChangeListener(this);
		homeRb = (RadioButton) findViewById(R.id.index_activity_home);
		productRb = (RadioButton) findViewById(R.id.index_activity_product);
		cartBr = (RadioButton) findViewById(R.id.index_activity_cart);
		memberRb = (RadioButton) findViewById(R.id.index_activity_member);
	}
	
	
	 private void initLayout() {
	        index_containerBody.removeAllViews();
	        index_containerBody.addView(getLocalActivityManager().startActivity(
	                "home",
	                new Intent(IndexActivity.this, GoodsActivity.class)
	                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	                .getDecorView());
//	        titleTv.setText("主页");
	    }

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case R.id.index_activity_home:
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"home",
							new Intent(IndexActivity.this,
									GoodsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			// titleTv.setText("主页");
			break;
		case R.id.index_activity_product:
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"seach",
							new Intent(IndexActivity.this, ProductActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			// titleTv.setText("发现");
			break;
		case R.id.index_activity_cart:
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"msg",
							new Intent(IndexActivity.this, GoodsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			// titleTv.setText("信息");
			break;
		case R.id.index_activity_member:
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"user",
							new Intent(IndexActivity.this, GoodsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			// titleTv.setText("个人中心");
			break;
		default:
			break;
		}
	}
	
}
