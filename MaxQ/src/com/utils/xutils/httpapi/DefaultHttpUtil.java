package com.utils.xutils.httpapi;

import org.xutils.common.Callback;

import android.content.Context;



/**
 * Created by Se7en_wan on 2016/1/14.
 */
public class DefaultHttpUtil<T> implements Callback.CommonCallback<T>{
    Context mContext;
    CustomsWaitDialog waitDialog;
    public DefaultHttpUtil(Context mContext){
        this.mContext=mContext;
        waitDialog=new CustomsWaitDialog(mContext);
        waitDialog.setCanceledOnTouchOutside(false);
        waitDialog.show();
    }
    @Override
    public void onSuccess(T t) {
    }

    @Override
    public void onError(Throwable throwable, boolean b) {
        waitDialog.dismiss();
    }

    @Override
    public void onCancelled(CancelledException e) {

        waitDialog.dismiss();
    }

    @Override
    public void onFinished() {

        waitDialog.dismiss();
    }
}
