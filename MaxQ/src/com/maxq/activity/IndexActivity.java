package com.maxq.activity;



import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maxq.R;
import com.utils.tools.DeviceUtil;

@SuppressWarnings("deprecation")
public class IndexActivity extends ActivityGroup implements RadioGroup.OnCheckedChangeListener{
	
	RadioButton homeRb,productRb,cartBr,memberRb;
	 RadioGroup tabRadioGroup;
	 LinearLayout  index_containerBody;
	 Context context;
	 int indexLoaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_layout);
		context=this;
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
		
		
		homeRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_bottom_rb_bg,32,32), null, null);
		productRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_bottom_rb_bg,32,32), null, null);
		cartBr.setCompoundDrawables(null,DeviceUtil.setCompoundDrawables(this,R.drawable.index_bottom_rb_bg,32,32), null, null);
		memberRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_bottom_rb_bg,32,32), null, null);
		
		homeRb.setText("首页");
		productRb.setText("分类");
		cartBr.setText("购物车");
		memberRb.setText("我的");
	}
	
	
	
	 private void initLayout() {
//	        index_containerBody.removeAllViews();
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
			indexLoaction=R.id.index_activity_home;
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"home",
							new Intent(IndexActivity.this,
									GoodsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.index_activity_product:
			indexLoaction=R.id.index_activity_product;
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"product",
							new Intent(IndexActivity.this, ProductActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.index_activity_cart:
			indexLoaction=R.id.index_activity_cart;
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"shopcar",
							new Intent(IndexActivity.this, ShopcartActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		case R.id.index_activity_member:
			indexLoaction=R.id.index_activity_member;
			Log.e(this.getClass().getName(), "index_activity_home");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"user",
							new Intent(IndexActivity.this, GoodsActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			break;
		default:
			break;
		}
	}

	long mkeyTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (indexLoaction != R.id.index_activity_home) {
				indexLoaction=R.id.index_activity_home;
				index_containerBody.removeAllViews();
				index_containerBody.addView(getLocalActivityManager()
								.startActivity("home",
										new Intent(IndexActivity.this,
												GoodsActivity.class)
												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
								.getDecorView());
			} else {
				if ((System.currentTimeMillis() - mkeyTime) > 2000) {
					mkeyTime = System.currentTimeMillis();
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				} else {
					finish();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
