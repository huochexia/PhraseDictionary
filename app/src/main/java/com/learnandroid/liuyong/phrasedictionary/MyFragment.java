package com.learnandroid.liuyong.phrasedictionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.learnandroid.liuyong.phrasedictionary.Util.DateUtil;
import com.learnandroid.liuyong.phrasedictionary.base.BaseFragment;
import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;
import com.learnandroid.liuyong.phrasedictionary.db.dao.CustomPhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.dao.PhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class MyFragment extends BaseFragment {
    @Bind(R.id.rg_my_number)
    RadioGroup mRgMyNumber;
    @Bind(R.id.rg_my_library)
    RadioGroup mRgMyLibrary;
    @Bind(R.id.rb_my_five)
    RadioButton mRbMyFive;
    @Bind(R.id.rb_my_ten)
    RadioButton mRbMyTen;
    @Bind(R.id.rb_my_fifteen)
    RadioButton mRbMyFifteen;
    @Bind(R.id.rb_my_twenty)
    RadioButton mRbMyTwenty;
    @Bind(R.id.rb_my_base_library)
    RadioButton mRbMyBaseLibrary;
    @Bind(R.id.rb_my_custom_library)
    RadioButton mRbMyCustomLibrary;
    @Bind(R.id.rb_my_restart_all)
    RadioButton mRbMyRestartAll;
    @Bind(R.id.rb_my_restart_label)
    RadioButton mRbMyRestartLabel;
    @Bind(R.id.rg_my_restart)
    RadioGroup mRgMyRestart;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private int clearScope = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getContext().getSharedPreferences("my_study_preferences", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        initEvent();
        switch (getNumPreferences()) {
            case 5:
                mRbMyFive.setChecked(true);
                break;
            case 10:
                mRbMyTen.setChecked(true);
                break;
            case 15:
                mRbMyFifteen.setChecked(true);
                break;
            case 20:
                mRbMyTwenty.setChecked(true);
        }
        String library = getLibPreferences();
        if (library.equals("BaseLibrary")) {
            mRbMyBaseLibrary.setChecked(true);
        } else if (library.equals("CustomLibrary")) {
            mRbMyCustomLibrary.setChecked(true);
        }
        return view;
    }

    public void initEvent() {
        mRgMyNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_five:
                        saveNumPreferences(5);
                        break;
                    case R.id.rb_my_ten:
                        saveNumPreferences(10);
                        break;
                    case R.id.rb_my_fifteen:
                        saveNumPreferences(15);
                        break;
                    case R.id.rb_my_twenty:
                        saveNumPreferences(20);
                        break;
                }
            }
        });
        mRgMyLibrary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_base_library:
                        saveLibPreferences("BaseLibrary");
                        break;
                    case R.id.rb_my_custom_library:
                        CustomPhraseDbManager manager = new CustomPhraseDbManager();
                        if (manager.loadAll().size() == 0) {
                            toast("自定义词库内为空！");
                            mRbMyCustomLibrary.setChecked(false);
                            mRbMyBaseLibrary.setChecked(true);
                        } else {
                            saveLibPreferences("CustomLibrary");
                        }
                        break;
                }
            }
        });
        mRgMyRestart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_restart_all:
                        clearScope = 0;
                        break;
                    case R.id.rb_my_restart_label:
                        clearScope = 1;
                        break;
                }
            }
        });
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 保存学习数量参数值
     *
     * @param number
     */
    public void saveNumPreferences(int number) {
//        SharedPreferences.Editor editor = getContext().getSharedPreferences("my_study_preferences", Context.MODE_PRIVATE).edit();
        mEditor.putInt("num", number);
        mEditor.apply();
    }

    /**
     * 获取每日学习数量
     *
     * @return
     */
    public Integer getNumPreferences() {
        int number;
//        SharedPreferences pref = getContext().getSharedPreferences("my_study_preferences", Context.MODE_PRIVATE);
        number = mPreferences.getInt("num", 5);
        return number;
    }

    /**
     * 保存学习词库名称
     *
     * @param library
     */
    public void saveLibPreferences(String library) {
//        SharedPreferences.Editor editor = getContext().getSharedPreferences("study_lib", Context.MODE_PRIVATE).edit();
        mEditor.putString("lib", library);
        mEditor.apply();
    }

    /**
     * 获得学习词库名称
     *
     * @return
     */
    public String getLibPreferences() {
//        SharedPreferences pref = getContext().getSharedPreferences("study_lib", Context.MODE_PRIVATE);
        String library = mPreferences.getString("lib", "BaseLibrary");
        return library;

    }


    @OnClick(R.id.btn_my_restart)
    public void onViewClicked() {
        clearStudyDate(clearScope);
    }

    /**
     * 清空学习记录
     */
    public void clearStudyDate(int clearScope) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("my_study_preferences",
                Context.MODE_PRIVATE).edit();
        editor.putInt("studydays", 0);
        editor.putString("studydate", "");
        editor.apply();
        if (getLibPreferences().equals("BaseLibrary")) {
            List<Phrase> list = new ArrayList<>();
            PhraseDbManager manager = new PhraseDbManager();
            if (clearScope == 0) {
                list = manager.loadAll();
            } else {
                QueryBuilder qb = manager.getQueryBuilder();
                list = qb.where(PhraseDao.Properties.MLabel.eq(1)).list();
            }
            for (Phrase one : list) {
                one.setMFirstTime(null);
                boolean sucess = manager.update(one);
            }

        } else if (getLibPreferences().equals("CustomLibrary")) {
            List<CustomPhrase> list = new ArrayList<>();
            CustomPhraseDbManager manager = new CustomPhraseDbManager();
            if (clearScope == 0) {
                list = manager.loadAll();
            } else {
                QueryBuilder qb = manager.getQueryBuilder();
                list = qb.where(CustomPhraseDao.Properties.MLabel.eq(1)).list();
            }
            for (CustomPhrase one : list) {
                one.setMFirstTime(null);
                boolean sucess = manager.update(one);
            }

        }
    }
}
