package com.learnandroid.liuyong.phrasedictionary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;
import com.learnandroid.liuyong.phrasedictionary.db.manager.AbstractDatabaseManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ParentWithNavigationActivity implements OnNavigationItemSelectedListener {

    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.navigation)
    BottomNavigationView mNavigation;
    @Bind(R.id.tv_navi_title)
    TextView mTvNaviTitle;

    private MenuItem mMenuItem;


    @Override
    public String title() {
        return "成语学习";
    }

    @Override
    public Object right() {
        return R.drawable.ic_search;
    }

    @Override
    public ToolbarListener setToolbarListener() {
        return new ToolbarListener() {
            @Override
            public void changedText() {

            }

            @Override
            public void changedBefore() {

            }

            @Override
            public void changedAfter() {

            }

            @Override
            public void clickleft() {

            }

            @Override
            public void clickright() {
                startActivity(SearchPhraseActivity.class, null, false);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            AbstractDatabaseManager.initOpenHelper(getApplicationContext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbarView();

        mViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mNavigation.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mNavigation.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        setupViewPager(mViewpager);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AbstractDatabaseManager.initOpenHelper(getApplicationContext());
                } else {
                    toast("用户不允许使用存储！");
                }

        }
    }

    /**
     * 加载Fragment
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance());
        adapter.addFragment(LibraryFragment.newInstance());
        adapter.addFragment(BrowseFragment.newInstance());
        adapter.addFragment(MyFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mViewpager.setCurrentItem(0);
                return true;
            case R.id.navigation_library:
                mViewpager.setCurrentItem(1);
                return true;
            case R.id.navigation_browse:
                mViewpager.setCurrentItem(2);
                return true;
            case R.id.navigation_setting:
                mViewpager.setCurrentItem(3);
                return true;
        }
        return false;
    }

    /**
     * 实现再按一次返回键退出程序
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出程序！");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
