package com.learnandroid.liuyong.phrasedictionary.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoMaster;
import com.learnandroid.liuyong.phrasedictionary.db.dao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**描述：greendao 管理类扩展性比较好，解耦和，实现类只需要实现getAbstractDao方法，返回具体的对应表的实例
 * 由各个表的管理类实现该类
 * Created by Administrator on 2017/10/24 0024.
 */

public abstract class AbstractDatabaseManager<M, K> implements IDatabase<M, K> {
    private static final String DATABASE_NAME_DEFAULT = "phrase_library.db";
    private static MySQLiteOpenHelper mHelper;
    protected static DaoSession daoSession;

    /**
     * 初始化OpenHelper
     *
     * @param context
     */
    public static void initOpenHelper(@NotNull Context context) {
        mHelper = new MySQLiteOpenHelper(new GreenDaoContext(), DATABASE_NAME_DEFAULT, null);
        daoSession = new DaoMaster(mHelper.getWritableDatabase()).newSession();

    }

    /**
     * 获取Dao
     */
    abstract AbstractDao<M, K> getAbstractDao();

    /**
     * 实现接口方法
     */
    @Override
    public boolean insert(M m) {
        try {
            if (m == null) {
                return false;
            } else {
                getAbstractDao().insert(m);

            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NotNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().insertOrReplace(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertList(List<M> mList) {
        return false;
    }

    @Override
    public boolean inserOrReplaceList(List<M> mList) {
        return false;
    }

    @Override
    public boolean delete(M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().delete(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        try {
            if (TextUtils.isEmpty(key.toString()))
                return false;
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<M> mList) {
        try {
            if (mList == null || mList.size() == 0) {
                return false;
            } else {
                getAbstractDao().deleteInTx(mList);
            }

        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

//    @Override
//    public boolean deleteByKeyInTx(K... key) {
//        try {
//            getAbstractDao().deleteByKeyInTx(key);
//        } catch (SQLiteException e) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean deleteAll() {
        try {
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().update(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

//    @Override
//    public boolean updateInTx(M... ms) {
//        try {
//            if (ms == null)
//                return false;
//            getAbstractDao().updateInTx(ms);
//        } catch (SQLiteException e) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean updateList(List<M> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            getAbstractDao().updateInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public M selectByPrimaryKey(K key) {
        try {
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {

            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        try {
            return getAbstractDao().loadAll();
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public boolean refresh(M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().refresh(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public void deleteDatabase() {

    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {

        }
    }

    @Override
    public QueryBuilder<M> getQueryBuilder() {
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<M> queryRaw(String where, String... selectionArg) {
        return getAbstractDao().queryRaw(where,selectionArg);
    }
}
