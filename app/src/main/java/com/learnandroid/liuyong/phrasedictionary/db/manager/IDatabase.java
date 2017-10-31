package com.learnandroid.liuyong.phrasedictionary.db.manager;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24 0024.
 * 针对表对象Dao的主要操作方法，
 */

public interface IDatabase<M,K> {
    //增加
    boolean insert(M m);

    boolean insertOrReplace(@NotNull M m);

    boolean insertList(List<M> mList);

    boolean inserOrReplaceList(List<M> mList);

    //删除
    boolean delete(M m);

    boolean deleteByKey(K key);

    boolean deleteList(List<M> mList);

//    boolean deleteByKeyInTx(K... key);

    boolean deleteAll();

    //修改
    boolean update(M m);

//    boolean updateInTx(M... ms);

    boolean updateList(List<M> mList);

    //查询
    M selectByPrimaryKey(K key);

    List<M> loadAll();

    boolean refresh(M m);

    //清理缓存
    void clearDaoSession();

    //删除数据库中所有表
    void deleteDatabase();

    //事务
    void runInTx(Runnable runnable);

    //自定义查询
    QueryBuilder<M> getQueryBuilder();

    //where
    List<M> queryRaw(String where, String... selectionArg);

}
