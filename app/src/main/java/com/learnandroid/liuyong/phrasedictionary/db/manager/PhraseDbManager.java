package com.learnandroid.liuyong.phrasedictionary.db.manager;

import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class PhraseDbManager extends AbstractDatabaseManager<Phrase,Long> {
    @Override
    AbstractDao<Phrase, Long> getAbstractDao() {
        return daoSession.getPhraseDao();
    }
}
