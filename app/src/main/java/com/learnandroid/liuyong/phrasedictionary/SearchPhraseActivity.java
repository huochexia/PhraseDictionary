package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.learnandroid.liuyong.phrasedictionary.Adapter.PhraseListAdapter;
import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;
import com.learnandroid.liuyong.phrasedictionary.db.dao.CustomPhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.dao.PhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class SearchPhraseActivity extends ParentWithNavigationActivity {


    PhraseListAdapter mAdapter;
    List<Object> mList;
    @Bind(R.id.rv_phrase_list)
    RecyclerView mRvPhraseList;
    @Bind(R.id.et_navi_search)
    EditText mEtNaviSearch;

    @Override
    public Object left() {
        return R.drawable.ic_left;
    }

    @Override
    public ToolbarListener setToolbarListener() {
        return new ToolbarListener() {
            @Override
            public void changedText() {
                mList.clear();
                mList.addAll(getSearchResult(mEtNaviSearch.getText().toString()));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void changedBefore() {

            }

            @Override
            public void changedAfter() {

            }

            @Override
            public void clickleft() {
                finish();
            }

            @Override
            public void clickright() {

            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phrase);
        ButterKnife.bind(this);
        initToolbarView();
        mList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRvPhraseList.setLayoutManager(llm);
        mAdapter = new PhraseListAdapter(this, mList);
        mRvPhraseList.setAdapter(mAdapter);

    }

    public List<Object> getSearchResult(String hypy) {
        List<Object> mlists = new ArrayList<>();
        PhraseDbManager db = new PhraseDbManager();
        QueryBuilder qb = db.getQueryBuilder();
        qb.where(PhraseDao.Properties.MHypy.like(hypy + "%"));
        mlists.addAll(qb.list());
        CustomPhraseDbManager cpd = new CustomPhraseDbManager();
        qb = cpd.getQueryBuilder();
        qb.where(CustomPhraseDao.Properties.MHypy.like(hypy + "%"));
        mlists.addAll(qb.list());
        return mlists;
    }
}
