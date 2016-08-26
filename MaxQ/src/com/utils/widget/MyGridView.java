package com.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 可以嵌套的GridView
 * 所有absListView 只要复写onMeasure 即可实现正常嵌套
 * @author sinosoft_wan
 *
 */
public class MyGridView extends GridView{
	public MyGridView(Context context,
			AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
