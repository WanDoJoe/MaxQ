package com.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 可以嵌套的ListView控件  可以防止
 * @author sinosoft_wan
 *
 */
public class InsertionAbsListView extends ListView {

	public InsertionAbsListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public InsertionAbsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InsertionAbsListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandHeightSpec);
	}

}
