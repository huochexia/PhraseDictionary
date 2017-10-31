package com.learnandroid.liuyong.phrasedictionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learnandroid.liuyong.phrasedictionary.Util.DateUtil;
import com.learnandroid.liuyong.phrasedictionary.base.BaseFragment;
import com.learnandroid.liuyong.phrasedictionary.db.dao.CustomPhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.dao.PhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.manager.AbstractDatabaseManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.tv_learn_day_total)
    TextView mTvLearnDayTotal;
    @Bind(R.id.tv_review_number)
    TextView mTvReviewNumber;
    @Bind(R.id.tv_consolidate_number)
    TextView mTvConsolidateNumber;
    @Bind(R.id.tv_today_learn_number)
    TextView mTvTodayLearnNumber;
    @Bind(R.id.tv_learned_number)
    TextView mTvLearnedNumber;
    @Bind(R.id.tv_home_study_library)
    TextView mTvHomeStudyLibrary;
    @Bind(R.id.btn_start_learn)
    Button mBtnStartLearn;

    private int study_number;
    private String study_library;

    AbstractDatabaseManager manager;
    List<Object> allList;
    List<Object> reviewList;
    List<Object> consolidateList;
    SharedPreferences pref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        pref = getContext().getSharedPreferences("my_study_preferences", Context.MODE_PRIVATE);
        study_library = pref.getString("lib", "BaseLibrary");
        if (study_library.equals("BaseLibrary")) {
            mTvHomeStudyLibrary.setText("学习内容：基本词库");
        } else if (study_library.equals("CustomLibrary")) {
            mTvHomeStudyLibrary.setText("学习内容：日积月累词库");
        }
        pref = getContext().getSharedPreferences("my_study_preferences", Context.MODE_PRIVATE);
        study_number = pref.getInt("num", 5);
        mTvTodayLearnNumber.setText(study_number + "");
        mTvLearnedNumber.setText(getStudiedPhraseNum(study_library)+"");
        //复习词语列表
        reviewList =getScheduledDatePhraseList(study_library,-1);
        mTvReviewNumber.setText(reviewList.size()+"");
        //巩固词语列表
        consolidateList = getScheduledDatePhraseList(study_library,-5);
        mTvConsolidateNumber.setText(consolidateList.size()+"");
        //学习天数记录
        mTvLearnDayTotal.setText(pref.getInt("studydays",0)+"");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_start_learn)
    public void onViewClicked() {
        computeStudydays();
        getAllPhrase(study_library);
        List<Object> phrase = getStudyPhraseList(study_number);
        List<Object> learning = new ArrayList<>();
        learning.addAll(phrase);
        learning.addAll(reviewList);
        learning.addAll(consolidateList);
        if (learning.size() != 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("phraselist", (Serializable) learning);
            startActivity(StudyPhraseActivity.class, bundle);
        } else {
            toast("已经学习完！");
        }

    }

    /**
     * 计算学习天数
     */
    private void computeStudydays() {
        //获取学习记录的日期，如果不是当天，则将学习天数加1后保存。
        String studydate =pref.getString("studydate","");
        if (!studydate.equals(DateUtil.getCurrentDateString())) {
            int num = pref.getInt("studydays", 0);
            num++;
            SharedPreferences.Editor editor =pref.edit();
            editor.putInt("studydays", num);
            editor.putString("studydate", DateUtil.getCurrentDateString());
            editor.apply();

        }
    }

    /**
     * 获得已学习过词语数量
     */
    public Integer getStudiedPhraseNum(String library) {
        QueryBuilder qb;
        int num;
        if (library.equals("BaseLibrary")) {
            manager = new PhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(PhraseDao.Properties.MFirstTime.isNotNull());
            num = qb.list().size();
        } else {
            manager = new CustomPhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(CustomPhraseDao.Properties.MFirstTime.isNotNull());
            num = qb.list().size();
        }
        return num;
    }
    /**
     * 获得全部尚学习词语
     */
    public void getAllPhrase(String library) {
        QueryBuilder qb;
        if (library.equals("BaseLibrary")) {
            manager = new PhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(PhraseDao.Properties.MFirstTime.isNull());
            allList = qb.list();
        } else {
            manager = new CustomPhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(CustomPhraseDao.Properties.MFirstTime.isNull());
            allList = qb.list();
        }
    }
    /**
     * 从词库中抽取指定数量的成语
     */
    public List<Object> getStudyPhraseList(int number) {
        List<Object> phrases = new ArrayList<>();
        int today_number;
        today_number = allList.size() < number ? allList.size() : number;
        for (int i = 0; i < today_number; i++) {
            phrases.add(allList.get(i));
        }
       return phrases;
    }

    /**
     * 获得指定库中前几天已学习过的词语
     * @param library  词库
     * @param days 前几天
     * @return
     */
    public List<Object> getScheduledDatePhraseList(String library,int days) {
        QueryBuilder qb;
        List<Object> mlist;
        Date today = new Date(System.currentTimeMillis());//获取当日期
        Date prevday = DateUtil.getNextDay(today,days);
        String date = DateUtil.getStringDate(prevday);
        if (library.equals("BaseLibrary")) {
            manager = new PhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(PhraseDao.Properties.MFirstTime.eq(date));
            mlist = qb.list();
        } else {
            manager = new CustomPhraseDbManager();
            qb = manager.getQueryBuilder();
            qb.where(CustomPhraseDao.Properties.MFirstTime.eq(date));
            mlist = qb.list();
        }
        return mlist;
    }


}
