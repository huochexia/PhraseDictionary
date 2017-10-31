package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.learnandroid.liuyong.phrasedictionary.Adapter.PhraseListAdapter;
import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class PhraseListActivity extends ParentWithNavigationActivity {
    List<Object> mObjectList;
    @Bind(R.id.rv_phrase_list)
    RecyclerView mRvPhraseList;
    PhraseListAdapter mAdapter;

    @Override
    public Object left() {
        return R.drawable.ic_left;
    }

    @Override
    public String title() {
        return "成语列表";
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
        initToolbarView();
        ButterKnife.bind(this);
        Bundle bundle = getBundle();
        mObjectList = (List<Object>) bundle.getSerializable("phrase");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvPhraseList.setLayoutManager(manager);
        mAdapter = new PhraseListAdapter(this,mObjectList);
        mRvPhraseList.setAdapter(mAdapter);

    }


}
