package com.maxq.activity;



import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maxq.R;
import com.maxq.utils.CostomValue;
import com.utils.tools.DeviceUtil;

@SuppressWarnings("deprecation")
public class IndexActivity extends ActivityGroup implements RadioGroup.OnCheckedChangeListener{
	private static IndexActivity indexActivity;
	public static String indexPage="indexPage"; 
	
	RadioButton homeRb, productRb, cartBr, memberRb;
	RadioGroup tabRadioGroup;
	LinearLayout index_containerBody;
	Context context;
	int indexLoaction;
	SharedPreferences preferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_layout);
		context=this;
		initView();
		setRefresh();
		initLayout(this,GoodsActivity.class);
		
//		Editor e=preferences.edit();
//		e.putBoolean("isLogin", false);
//		e.commit();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		preferences=getSharedPreferences(CostomValue.SP_NAME, MODE_PRIVATE);
		if(preferences.getBoolean(CostomValue.SP_KEY_LOGIN, false)){
			initLayout(this,GoodsActivity.class);
		}else{
			String flag=getIntent().getStringExtra("loginFlag");
			if("cart".equals(flag)){
				initLayout(this,ShopcartActivity.class);
			}else if("member".equals(flag)){
				initLayout(this,MemBerActivity.class);
			}else if("".equals(flag)){
				
			}
		}
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
		
		
		homeRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_home_rb_bg,36,36), null, null);
		productRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_sort_rb_bg,36,36), null, null);
		cartBr.setCompoundDrawables(null,DeviceUtil.setCompoundDrawables(this,R.drawable.index_shopcart_rb_bg,36,36), null, null);
		memberRb.setCompoundDrawables(null, DeviceUtil.setCompoundDrawables(this,R.drawable.index_personal_rb_bg,36,36), null, null);
		
		homeRb.setText("首页");
		productRb.setText("分类");
		cartBr.setText("购物车");
		memberRb.setText("我的");
	}
	
	public static IndexActivity getInstance() {
		if (indexActivity == null) {
			synchronized (IndexActivity.class) {
				if (indexActivity == null) {
					indexActivity = new IndexActivity();
				}
			}
		}
		return indexActivity;

	}
	
	 public void initLayout(Context context, Class<?> cls) {
//	        index_containerBody.removeAllViews();  GoodsActivity
	        index_containerBody.addView(getLocalActivityManager().startActivity(
	                "home",
	                new Intent(context, cls)
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
			if(preferences.getBoolean("isLogin", false)){
			indexLoaction=R.id.index_activity_cart;
			Log.e(this.getClass().getName(), "index_activity_cart");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"shopcar",
							new Intent(IndexActivity.this, ShopcartActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			}else{
				indexLoaction=R.id.index_activity_cart;
				Log.e(this.getClass().getName(), "index_activity_cart");
				index_containerBody.removeAllViews();
				Intent intent= new Intent(IndexActivity.this, LoginActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("loginFlag", "cart");
				index_containerBody.addView(getLocalActivityManager()
						.startActivity(
								"Login",intent
								)
						.getDecorView());
			}
			break;
		case R.id.index_activity_member:
			if(preferences.getBoolean("isLogin", false)){
			indexLoaction=R.id.index_activity_member;
			Log.e(this.getClass().getName(), "index_activity_member");
			index_containerBody.removeAllViews();
			index_containerBody.addView(getLocalActivityManager()
					.startActivity(
							"user",
							new Intent(IndexActivity.this, MemBerActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView());
			}else{
				indexLoaction=R.id.index_activity_member;
				Log.e(this.getClass().getName(), "index_activity_member");
				index_containerBody.removeAllViews();
				Intent intent= new Intent(IndexActivity.this, LoginActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("loginFlag", "member");
				index_containerBody.addView(getLocalActivityManager()
						.startActivity(
								"Login",
								intent)
						.getDecorView());
			}
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
				homeRb.setChecked(true);
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
