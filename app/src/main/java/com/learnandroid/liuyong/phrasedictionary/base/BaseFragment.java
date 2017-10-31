package com.learnandroid.liuyong.phrasedictionary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class BaseFragment extends Fragment{

    protected void runOnMain(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    protected final static String NULL = "";
    private Toast toast;
    public void toast(final Object obj) {
        try {
            runOnMain(new Runnable() {

                @Override
                public void run() {
                    if (toast == null)
                        toast = Toast.makeText(getActivity(), NULL,Toast.LENGTH_SHORT);
                    toast.setText(obj.toString());
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**启动指定Activity
     * @param target
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), target);
        if (bundle != null)
            intent.putExtra(getActivity().getPackageName(), bundle);
        getActivity().startActivity(intent);
    }
}
