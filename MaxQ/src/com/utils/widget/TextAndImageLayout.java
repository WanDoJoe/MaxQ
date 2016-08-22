package com.utils.widget;

import java.util.List;

import org.xutils.x;

import com.utils.tools.RegularUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextAndImageLayout extends LinearLayout {
	Context context;

	public TextAndImageLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public TextAndImageLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public TextAndImageLayout(Context context) {
		super(context);
		this.context = context;
	}

	public void setView(List<String> list) {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < list.size(); i++) {
			if (RegularUtils.isURL(list.get(i))) {
				ImageView imageView = new ImageView(context);
				x.image().bind(imageView, list.get(i));
				addView(imageView, params);
			} else {
				TextView textView = new TextView(context);
				textView.setText(list.get(i));
				addView(textView, params);
			}
		}

	}
}
