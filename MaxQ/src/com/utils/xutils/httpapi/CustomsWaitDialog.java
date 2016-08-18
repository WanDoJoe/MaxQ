package com.utils.xutils.httpapi;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxq.R;


/**
 * Created by Se7en_wan on 2016/4/28.
 */

/*
<style name="DialogTheme" parent="@android:style/Theme.Dialog">
<item name="android:windowBackground">@android:color/transparent</item>
<item name="android:windowFrame">@null</item>
<item name="android:windowNoTitle">true</item>
<item name="android:windowIsFloating">true</item>
<item name="android:windowIsTranslucent">true</item>
<item name="android:windowContentOverlay">@null</item>
<!-- 对话框是否有遮盖 -->
<item name="android:windowAnimationStyle">@android:style/Animation.Dialog</item>
<item name="android:backgroundDimEnabled">true</item>
</style>
*/

/*dialog_bg.xml
 <?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle" >

    <corners android:radius="10dp" />

    <solid android:color="#55000000" />

</shape>
*/

/*dialog_layout.xml
 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:background="@drawable/dialog_bg"
    android:gravity="center"
    android:orientation="vertical" >
    <LinearLayout
        android:id="@+id/LinearLayout"
        android:layout_width="160dp"
        android:layout_height="160dp"
        android:background="@drawable/dialog_bg"
        android:gravity="center"
        android:orientation="vertical" >

        <ProgressBar
            android:id="@+id/progressBar1"
            style="?android:attr/progressBarStyleInverse"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@android:color/transparent" />

        <TextView
            android:id="@+id/tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:paddingTop="10dp"
            android:textColor="#ffffff"
            />
    </LinearLayout>

</LinearLayout>  
 */
public class CustomsWaitDialog extends Dialog {
    private TextView tv;

    public CustomsWaitDialog(Context context) {
        super(context, R.style.DialogTheme);
    }

    private CustomsWaitDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        tv = (TextView) this.findViewById(R.id.tv);
        tv.setText("正在上传...");
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }
}
