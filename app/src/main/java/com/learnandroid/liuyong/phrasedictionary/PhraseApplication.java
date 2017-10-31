package com.learnandroid.liuyong.phrasedictionary;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.learnandroid.liuyong.phrasedictionary.db.manager.AbstractDatabaseManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.GreenDaoManager;
import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoMaster;
import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoSession;


/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class PhraseApplication extends Application {

//    public DaoMaster.DevOpenHelper dHelper;
//    public DaoMaster mDaoMaster;
//    public DaoSession mDaoSession;
//    public SQLiteDatabase db;
    public static Context context;
    public static PhraseApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        context = getApplicationContext();

//        setDatabase();//第一种初始化数据库方法
//        GreenDaoManager.getInstance();//第二种初始化数据库方法
//        AbstractDatabaseManager.initOpenHelper(getApplicationContext());//第三种初始化数据库方法
    }

    /**
     * 第一种方式
     * @return
     */
//    private void setDatabase() {
//        dHelper = new DaoMaster.DevOpenHelper(this, "PhraseLibrary.db", null);
//        db = dHelper.getWritableDatabase();
//
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }

//    public SQLiteDatabase getDb() {
//        return db;
//    }

    public static PhraseApplication getInstances() {
        return instances;
    }

    public static Context getContext() {
        return context;
    }
}
