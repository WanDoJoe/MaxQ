package com.utils.widget.head;

import android.widget.BaseAdapter;

/**
 * 
 * @author sinosoft_wan 用于回调 是否到顶部。解决下拉刷新时候的控件冲突
 */
public interface ScollToTop {
	public boolean isScollToTop();
	public void update();
}
