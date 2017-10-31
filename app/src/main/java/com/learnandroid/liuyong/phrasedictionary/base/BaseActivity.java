package com.learnandroid.liuyong.phrasedictionary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }

    @Subscribe
    public void onEvent(Boolean empty) {

    }

    /**
     * 启动指定Activity
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null) {
            intent.putExtra(getPackageName(), bundle);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName())) {
            return getIntent().getBundleExtra(getPackageName());
        }
        return null;
    }

    /**
     * 主界面显示提示内容
     */
    protected final static String NULL = "";
    protected Toast mToast;

    public void toast(final Object obj) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null)
                        mToast = Toast.makeText(BaseActivity.this, NULL, Toast.LENGTH_SHORT);
                    mToast.setText(obj.toString());
                    mToast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
