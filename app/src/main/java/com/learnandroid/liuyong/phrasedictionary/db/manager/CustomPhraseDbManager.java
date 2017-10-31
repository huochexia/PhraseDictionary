package com.learnandroid.liuyong.phrasedictionary.db.manager;

import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class CustomPhraseDbManager extends AbstractDatabaseManager<CustomPhrase,Long> {
    @Override
    AbstractDao<CustomPhrase, Long> getAbstractDao() {
        return daoSession.getCustomPhraseDao();
    }
}
