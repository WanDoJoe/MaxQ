package in.srain.cube.views.ptr;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

public abstract class PtrDefaultHandler2 extends PtrDefaultHandler implements PtrHandler2 {

    public static boolean canChildScrollDown(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else if (view instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) view;
                if (scrollView.getChildCount() == 0) {
                    return false;
                } else {
                    return scrollView.getScrollY() < scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
                }
            } else {
                return false;
            }
        } else {
            return view.canScrollVertically(1);
        }
    }


    public static boolean checkContentCanBePulledUp(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollDown(content);
    }
    /**
     * Default implement for check can perform pull to refresh
     *	判断当前是否可以上拉加载
     * @param frame
     * @param content
     * @param header
     * @return
     */
    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return checkContentCanBePulledUp(frame, content, footer);
    }
}