package com.learnandroid.liuyong.phrasedictionary.db.manager;

import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoMaster;
import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoSession;

/**这种方式扩展性差，解耦差，不如AbstractDatabaseManager方式
 * 此软件没有使用它
 * Created by Administrator on 2017/10/21 0021.
 */

public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance;//单例

    private GreenDaoManager() {
        if (mInstance == null) {
//    DaoMaster.DevOpenHelper devOpenHelper = new
//  DaoMaster.DevOpenHelper(MyApplication.getContext(), "database_name", null);//此处openhelper为自动生成开发所使用，发布版本需自定义
            MySQLiteOpenHelper devOpenHelper = new MySQLiteOpenHelper(new GreenDaoContext(), "PhraseLibrary.db", null);//GreenDaoContext为创建数据库路径使用（见《GreenDao设置数据库路径以及数据库升级》
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }


    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }


    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }


    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
