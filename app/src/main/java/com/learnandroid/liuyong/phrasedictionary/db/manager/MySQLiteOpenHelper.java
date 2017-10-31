package com.learnandroid.liuyong.phrasedictionary.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoMaster;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context,name);
    }
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
