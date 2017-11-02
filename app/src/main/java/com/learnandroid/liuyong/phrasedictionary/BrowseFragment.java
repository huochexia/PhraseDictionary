package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.learnandroid.liuyong.phrasedictionary.base.BaseFragment;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;
import com.learnandroid.liuyong.phrasedictionary.db.dao.CustomPhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.dao.PhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.manager.AbstractDatabaseManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class BrowseFragment extends BaseFragment {

    @Bind(R.id.rb_browse_base_lib)
    RadioButton mRbBrowseBaseLib;
    @Bind(R.id.rb_browse_custom_lib)
    RadioButton mRbBrowseCustomLib;
    @Bind(R.id.rg_browse_library)
    RadioGroup mRgBrowseLibrary;
    @Bind(R.id.rb_browse_all)
    RadioButton mRbBrowseAll;
    @Bind(R.id.rb_browse_label)
    RadioButton mRbBrowseFlag;
    @Bind(R.id.rg_browse_content)
    RadioGroup mRgBrowseContent;

    AbstractDatabaseManager manager;

    int label=0;//0默认，1标记
    List<Object> mObjectList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new PhraseDbManager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, view);
        initEvent();
        return view;
    }

    private void initEvent() {
        mRgBrowseLibrary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_browse_base_lib:
                        manager = new PhraseDbManager();
                        break;
                    case R.id.rb_browse_custom_lib:
                        manager = new CustomPhraseDbManager();
                        break;
                }
            }
        });
        mRgBrowseContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_browse_all:
                        label = 0;
                        break;
                    case R.id.rb_browse_label:
                        label = 1;
                        break;
                    case R.id.rb_browse_no_label:
                        label = 2;
                        break;
                }
            }
        });
    }

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.pinyin_A, R.id.pinyin_B, R.id.pinyin_C, R.id.pinyin_D,
            R.id.pinyin_E, R.id.pinyin_F, R.id.pinyin_G, R.id.pinyin_H,
            R.id.pinyin_I, R.id.pinyin_J, R.id.pinyin_K, R.id.pinyin_L,
            R.id.pinyin_M, R.id.pinyin_N, R.id.pinyin_O, R.id.pinyin_P,
            R.id.pinyin_Q, R.id.pinyin_R, R.id.pinyin_S, R.id.pinyin_T,
            R.id.pinyin_U, R.id.pinyin_V, R.id.pinyin_W, R.id.pinyin_X,
            R.id.pinyin_Y, R.id.pinyin_Z})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.pinyin_A:
                queryList("A",label);
                break;
            case R.id.pinyin_B:
                queryList("B",label);
                break;
            case R.id.pinyin_C:
                queryList("C",label);
                break;
            case R.id.pinyin_D:
                queryList("D",label);
                break;
            case R.id.pinyin_E:
                queryList("E",label);
                break;
            case R.id.pinyin_F:
                queryList("F",label);
                break;
            case R.id.pinyin_G:
                queryList("G",label);
                break;
            case R.id.pinyin_H:
                queryList("H",label);
                break;
            case R.id.pinyin_I:
                queryList("I",label);
                break;
            case R.id.pinyin_J:
                queryList("J",label);
                break;
            case R.id.pinyin_K:
                queryList("K",label);
                break;
            case R.id.pinyin_L:
                queryList("L",label);
                break;
            case R.id.pinyin_M:
                queryList("M",label);
                break;
            case R.id.pinyin_N:
                queryList("N",label);
                break;
            case R.id.pinyin_O:
                queryList("O",label);
                break;
            case R.id.pinyin_P:
                queryList("P",label);
                break;
            case R.id.pinyin_Q:
                queryList("Q",label);
                break;
            case R.id.pinyin_R:
                queryList("R",label);
                break;
            case R.id.pinyin_S:
                queryList("S",label);
                break;
            case R.id.pinyin_T:
                queryList("T",label);
                break;
            case R.id.pinyin_U:
                queryList("U",label);
                break;
            case R.id.pinyin_V:
                queryList("V",label);
                break;
            case R.id.pinyin_W:
                queryList("W",label);
                break;
            case R.id.pinyin_X:
                queryList("X",label);
                break;
            case R.id.pinyin_Y:
                queryList("Y",label);
                break;
            case R.id.pinyin_Z:
                queryList("Z",label);
                break;
        }
        sendList();
    }

    /**
     * 构建查询范围
     */
    public void queryList(String first,Integer label) {
        mObjectList.clear();
        QueryBuilder qb = manager.getQueryBuilder();
        if (manager instanceof PhraseDbManager) {
            if (label == 0)
                qb.where(PhraseDao.Properties.MHypy.like(first+"%"));
            if (label == 1)
                qb.where(PhraseDao.Properties.MLabel.eq(1),PhraseDao.Properties.MHypy.like(first+"%"));
            if(label ==2)
                qb.where(PhraseDao.Properties.MLabel.notEq(1), PhraseDao.Properties.MHypy.like(first+"%"));
        } else if (manager instanceof CustomPhraseDbManager) {
            if (label == 0)
                qb.where(CustomPhraseDao.Properties.MHypy.like(first+"%"));
            if (label == 1)
                qb.where(CustomPhraseDao.Properties.MLabel.eq(1),CustomPhraseDao.Properties.MHypy.like(first+"%"));
            if(label ==2)
                qb.where(CustomPhraseDao.Properties.MLabel.notEq(1), CustomPhraseDao.Properties.MHypy.like(first+"%"));
        }
        mObjectList.addAll(qb.list());
    }


    /**
     * 传递查询结果，启动显示Activity
     */
    private void sendList() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("phrase", (Serializable) mObjectList);
        startActivity(PhraseListActivity.class, bundle);
    }
}
