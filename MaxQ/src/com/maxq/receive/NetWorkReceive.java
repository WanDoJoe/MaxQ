package com.maxq.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.utils.tools.NetWorkUtil;

public class NetWorkReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!NetWorkUtil.isAvailable(context)) {
			Toast.makeText(context, "找个能上网的地儿！我等着你。", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (!NetWorkUtil.isWifiConnected(context)) {
				 Toast.makeText(context, "当前网络为WIFI模式下",
							 Toast.LENGTH_SHORT).show();
			} else {
				// Toast.makeText(this, "当前网络为WIFI模式下",
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

}
