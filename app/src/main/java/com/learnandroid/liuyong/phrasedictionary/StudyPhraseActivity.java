package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.Util.DateUtil;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;
import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;
import com.learnandroid.liuyong.phrasedictionary.db.manager.AbstractDatabaseManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

public class StudyPhraseActivity extends ParentWithNavigationActivity {
    @Bind(R.id.tv_study_phrase)
    TextView mTvStudyPhrase;
    @Bind(R.id.tv_study_explain)
    TextView mTvStudyExplain;
    @Bind(R.id.tv_study_comment)
    TextView mTvStudyComment;
    @Bind(R.id.btn_study_flag)
    Button mBtnStudyFlag;
    @Bind(R.id.btn_study_cancel_flag)
    Button mBtnStudyCancelFlag;

    List<Object> mList;
    int currentIndex = 0;
    @Bind(R.id.v_top)
    View mVTop;
    @Bind(R.id.iv_navi_left)
    ImageView mIvNaviLeft;
    @Bind(R.id.et_navi_search)
    EditText mEtNaviSearch;
    @Bind(R.id.tv_navi_title)
    TextView mTvNaviTitle;
    @Bind(R.id.iv_navi_right)
    ImageView mIvNaviRight;
    @Bind(R.id.btn_study_prev)
    Button mBtnStudyPrev;
    @Bind(R.id.btn_study_next)
    Button mBtnStudyNext;
    @Bind(R.id.iv_study_label)
    ImageView mIvStudyLabel;

    AbstractDatabaseManager manager;
    @Bind(R.id.tv_study_py)
    TextView mTvStudyPy;

    @Override
    public Object left() {
        return R.drawable.ic_left;
    }

    @Override
    public String title() {
        return "认真学习";
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
        setContentView(R.layout.activity_study_phrase);
        Bundle bundle = getBundle();
        mList = (List<Object>) bundle.getSerializable("phraselist");
        ButterKnife.bind(this);
        initToolbarView();
        mBtnStudyPrev.setEnabled(false);
        setPhraseList(mList.get(currentIndex));
        setFirstStudyDate(mList.get(currentIndex));

    }

    @OnClick({R.id.btn_study_prev, R.id.btn_study_next, R.id.btn_study_flag, R.id.btn_study_cancel_flag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_study_prev:
                currentIndex--;
                setPhraseList(mList.get(currentIndex));
                if (currentIndex == 0) {
                    mBtnStudyPrev.setEnabled(false);
                    mBtnStudyNext.setEnabled(true);
                } else {
                    mBtnStudyNext.setEnabled(true);
                }
                break;
            case R.id.btn_study_next:
                currentIndex++;
                setPhraseList(mList.get(currentIndex));
                setFirstStudyDate(mList.get(currentIndex));
                if (currentIndex == mList.size() - 1) {
                    mBtnStudyNext.setEnabled(false);
                    mBtnStudyPrev.setEnabled(true);
                } else {

                    mBtnStudyPrev.setEnabled(true);
                }

                break;
            case R.id.btn_study_flag:
                mBtnStudyCancelFlag.setVisibility(View.VISIBLE);
                mBtnStudyFlag.setVisibility(View.INVISIBLE);
                mIvStudyLabel.setVisibility(View.VISIBLE);
                if (mList.get(currentIndex) instanceof Phrase) {
                    manager = new PhraseDbManager();
                    ((Phrase) mList.get(currentIndex)).setMLabel(1);
                    manager.update(mList.get(currentIndex));
                } else if (mList.get(currentIndex) instanceof CustomPhrase) {
                    manager = new CustomPhraseDbManager();
                    ((CustomPhrase) mList.get(currentIndex)).setMLabel(1);
                    manager.update(mList.get(currentIndex));
                }
                break;
            case R.id.btn_study_cancel_flag:
                mBtnStudyFlag.setVisibility(View.VISIBLE);
                mBtnStudyCancelFlag.setVisibility(View.INVISIBLE);
                mIvStudyLabel.setVisibility(View.INVISIBLE);
                if (mList.get(currentIndex) instanceof Phrase) {
                    manager = new PhraseDbManager();
                    ((Phrase) mList.get(currentIndex)).setMLabel(0);
                    manager.update(mList.get(currentIndex));
                } else if (mList.get(currentIndex) instanceof CustomPhrase) {
                    manager = new CustomPhraseDbManager();
                    ((CustomPhrase) mList.get(currentIndex)).setMLabel(0);
                    manager.update(mList.get(currentIndex));
                }
                break;
        }
    }

    /**
     * 记录第一次学习的时间
     */
    private void setFirstStudyDate(Object obj) {
        if (obj instanceof Phrase) {
            manager = new PhraseDbManager();
            if (((Phrase) obj).getMFirstTime() == null) {
                ((Phrase) obj).setMFirstTime(DateUtil.getCurrentDateString());
                manager.update(obj);
            }

        } else if (obj instanceof CustomPhrase) {
            manager = new CustomPhraseDbManager();
            if (((CustomPhrase) obj).getMFirstTime() == null) {
                ((CustomPhrase) obj).setMFirstTime(DateUtil.getCurrentDateString());
                manager.update(obj);
            }
        }
    }

    public void setPhraseList(Object obj) {
        if (obj instanceof Phrase) {
            if (((Phrase) obj).getMLabel() == 1) {
                mBtnStudyFlag.setVisibility(View.INVISIBLE);
                mBtnStudyCancelFlag.setVisibility(View.VISIBLE);
                mIvStudyLabel.setVisibility(View.VISIBLE);

            } else {
                mBtnStudyFlag.setVisibility(View.VISIBLE);
                mBtnStudyCancelFlag.setVisibility(View.INVISIBLE);
                mIvStudyLabel.setVisibility(View.INVISIBLE);
            }
            mTvStudyPhrase.setText(((Phrase) obj).getMPhrase());
            mTvStudyPy.setText(((Phrase) obj).getMHypy());
            mTvStudyExplain.setText(((Phrase) obj).getMExplain());
            mTvStudyComment.setText(((Phrase) obj).getMComment());
        } else if (obj instanceof CustomPhrase) {
            if (((CustomPhrase) obj).getMLabel() == 1) {
                mBtnStudyFlag.setVisibility(View.INVISIBLE);
                mBtnStudyCancelFlag.setVisibility(View.VISIBLE);
                mIvStudyLabel.setVisibility(View.VISIBLE);
            } else {
                mBtnStudyFlag.setVisibility(View.VISIBLE);
                mBtnStudyCancelFlag.setVisibility(View.INVISIBLE);
                mIvStudyLabel.setVisibility(View.INVISIBLE);
            }
            mTvStudyPhrase.setText(((CustomPhrase) obj).getMPhrase());
            mTvStudyPy.setText(((CustomPhrase) obj).getMHypy());
            mTvStudyExplain.setText(((CustomPhrase) obj).getMExplain());
            mTvStudyComment.setText(((CustomPhrase) obj).getMComment());
        }
    }
}
