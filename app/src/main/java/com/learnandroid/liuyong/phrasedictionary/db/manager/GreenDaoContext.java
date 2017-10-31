package com.learnandroid.liuyong.phrasedictionary.db.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.learnandroid.liuyong.phrasedictionary.MainActivity;
import com.learnandroid.liuyong.phrasedictionary.PhraseApplication;
import com.learnandroid.liuyong.phrasedictionary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class GreenDaoContext extends ContextWrapper {
    private String currentUserId = "greendao";//一般用来针对一个用户一个数据库，以免数据混乱问题
    private Context mContext;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    public GreenDaoContext() {
        super(PhraseApplication.getContext());
        this.mContext = PhraseApplication.getContext();
//        this.currentUserId = "greendao";//初始化
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        //1、首先定义数据库存放路径字符串
        String dbDir = Environment.getExternalStorageDirectory()+File.separator+"phraselibrary";//自定义方法
       //2、创建数据库路径目录
        File baseFile = new File(dbDir);
        // 目录不存在则自动创建目录
        if (!baseFile.exists()){
            baseFile.mkdirs();
        }
        //3、组装数据库路径全名
        StringBuffer buffer = new StringBuffer();
        buffer.append(baseFile.getPath());
        buffer.append(File.separator);
        buffer.append(currentUserId);
        dbDir = buffer.toString();// 数据库所在目录
        buffer.append(File.separator);
//        buffer.append(dbName+"_"+currentUserId);//也可以采用此种方式，将用户id与表名联系到一块命名
        buffer.append(dbName);
        String dbPath = buffer.toString();// 数据库路径
        // 4、判断组建好的数据库目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 5、判断文件是否存在，不存在则创建该文件。如果有随软件发行的数据库文件，则从raw资源文件下
        //将数据库文件拷贝到指定数据库文件目录下
        File dbFile = new File(dirFile,dbName);
        if (!dbFile.exists()) {
            try {
//                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                //不存在得到数据库输入流对象
                InputStream is = mContext.getResources().openRawResource(
                        R.raw.phrase_library);
                //创建输出流
                FileOutputStream fos = new FileOutputStream(dbFile);
                //将数据输出
                byte[] bufferfile = new byte[8192];
                int count = 0;
                while ((count = is.read(bufferfile)) > 0)
                {
                    fos.write(bufferfile, 0, count);
                }
                //关闭资源
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;
        // 返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else
            return super.getDatabasePath(dbName);
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);

        return result;
    }

}
