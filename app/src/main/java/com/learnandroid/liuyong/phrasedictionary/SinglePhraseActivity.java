package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;
import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class SinglePhraseActivity extends ParentWithNavigationActivity {
    @Bind(R.id.tv_new_phrase)
    EditText mTvNewPhrase;
    @Bind(R.id.tv_new_phrase_py)
    EditText mTvNewPhrasePy;
    @Bind(R.id.add_phrase_explain)
    EditText mAddPhraseExplain;
    @Bind(R.id.add_phrase_comment)
    EditText mAddPhraseComment;
    @Bind(R.id.add_phrase_ok)
    Button mAddPhraseOk;
    @Bind(R.id.add_phrase_cancel)
    Button mAddPhraseCancel;
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
    @Bind(R.id.iv_home_top_picture)
    ImageView mIvHomeTopPicture;
    @Bind(R.id.add_phrase_prev)
    Button mPrevPhrase;
    @Bind(R.id.add_phrase_next)
    Button mNextPhrase;

    List<Object> mLists;
    int currentIndex;

    @Bind(R.id.iv_addphrase_label)
    ImageView mIvAddphraseLabel;
    @Bind(R.id.btn_add_phrase_flag)
    Button mBtnAddPhraseFlag;
    @Bind(R.id.btn_add_phrase_cancel_flag)
    Button mBtnAddPhraseCancelFlag;
    @Bind(R.id.add_phrase_modi)
    Button mAddPhraseModi;

    @Override
    public Object left() {
        return R.drawable.ic_left;
    }

    @Override
    public String title() {
        return "成语详解";
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
        setContentView(R.layout.activity_addphrase);
        initToolbarView();
        ButterKnife.bind(this);
        initView();
        mTvNewPhrase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pinYin = "";
                for(int i= 0 ;i<s.length();i++) {
                    String c= Pinyin.toPinyin(s.charAt(i));//获得第i个字的拼音
                    pinYin = pinYin + c.charAt(0);
                }

                mTvNewPhrasePy.setText(pinYin);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bundle bundle = getBundle();
        mLists = (List<Object>) bundle.getSerializable("phrase");
        currentIndex = bundle.getInt("currentIndex");
        if (currentIndex == 0)
            mPrevPhrase.setEnabled(false);
        if (currentIndex == mLists.size() - 1)
            mNextPhrase.setEnabled(false);
        getPhraseContent();
    }

    /**
     * 显示成语相应内容
     */
    private void getPhraseContent() {
        if (mLists.get(currentIndex) instanceof Phrase) {
            mTvNewPhrase.setText(((Phrase) mLists.get(currentIndex)).getMPhrase());
            mTvNewPhrasePy.setText(((Phrase) mLists.get(currentIndex)).getMHypy());
            mAddPhraseComment.setText(((Phrase) mLists.get(currentIndex)).getMComment());
            mAddPhraseExplain.setText(((Phrase) mLists.get(currentIndex)).getMExplain());
            if (((Phrase) mLists.get(currentIndex)).getMLabel() == 1) {
                mIvAddphraseLabel.setVisibility(View.VISIBLE);
                mBtnAddPhraseCancelFlag.setVisibility(View.VISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.INVISIBLE);
            } else {
                mIvAddphraseLabel.setVisibility(View.INVISIBLE);
                mBtnAddPhraseCancelFlag.setVisibility(View.INVISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.VISIBLE);
            }
        } else if (mLists.get(currentIndex) instanceof CustomPhrase) {
            mAddPhraseModi.setVisibility(View.VISIBLE);
            mAddPhraseModi.setOnClickListener(modi);
            mTvNewPhrase.setText(((CustomPhrase) mLists.get(currentIndex)).getMPhrase());
            mTvNewPhrasePy.setText(((CustomPhrase) mLists.get(currentIndex)).getMHypy());
            mAddPhraseComment.setText(((CustomPhrase) mLists.get(currentIndex)).getMComment());
            mAddPhraseExplain.setText(((CustomPhrase) mLists.get(currentIndex)).getMExplain());
            if (((CustomPhrase) mLists.get(currentIndex)).getMLabel() == 1) {
                mIvAddphraseLabel.setVisibility(View.VISIBLE);
                mBtnAddPhraseCancelFlag.setVisibility(View.VISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.INVISIBLE);
            } else {
                mIvAddphraseLabel.setVisibility(View.INVISIBLE);
                mBtnAddPhraseCancelFlag.setVisibility(View.INVISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mTvNewPhrase.setEnabled(false);
        mTvNewPhrasePy.setEnabled(false);
        mAddPhraseExplain.setEnabled(false);
        mAddPhraseComment.setEnabled(false);
        mAddPhraseCancel.setVisibility(View.INVISIBLE);
        mAddPhraseOk.setVisibility(View.INVISIBLE);
        mNextPhrase.setVisibility(View.VISIBLE);
        mPrevPhrase.setVisibility(View.VISIBLE);
    }

    /**
     * 点击修改按钮事件
     * @param view
     */
    View.OnClickListener   modi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTvNewPhrase.setEnabled(true);
            mTvNewPhrasePy.setEnabled(true);
            mAddPhraseExplain.setEnabled(true);
            mAddPhraseComment.setEnabled(true);
            mAddPhraseCancel.setVisibility(View.VISIBLE);
            mAddPhraseOk.setVisibility(View.VISIBLE);
            mNextPhrase.setVisibility(View.INVISIBLE);
            mPrevPhrase.setVisibility(View.INVISIBLE);

        }

    };
    @OnClick({R.id.add_phrase_prev, R.id.add_phrase_next, R.id.btn_add_phrase_flag, R.id.btn_add_phrase_cancel_flag,
            R.id.add_phrase_ok, R.id.add_phrase_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_phrase_prev:
                currentIndex--;
                getPhraseContent();
                if (currentIndex == 0) {
                    mPrevPhrase.setEnabled(false);
                    mNextPhrase.setEnabled(true);
                } else {
                    mNextPhrase.setEnabled(true);
                }
                break;
            case R.id.add_phrase_next:
                currentIndex++;
                getPhraseContent();
                if (currentIndex == mLists.size() - 1) {
                    mNextPhrase.setEnabled(false);
                    mPrevPhrase.setEnabled(true);
                } else {
                    mPrevPhrase.setEnabled(true);

                }
                break;
            case R.id.btn_add_phrase_flag:
                mBtnAddPhraseCancelFlag.setVisibility(View.VISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.INVISIBLE);
                mIvAddphraseLabel.setVisibility(View.VISIBLE);
                setLabel(1);
                break;
            case R.id.btn_add_phrase_cancel_flag:
                mBtnAddPhraseCancelFlag.setVisibility(View.INVISIBLE);
                mBtnAddPhraseFlag.setVisibility(View.VISIBLE);
                mIvAddphraseLabel.setVisibility(View.INVISIBLE);
                setLabel(0);
                break;
            case R.id.add_phrase_ok:
                CustomPhraseDbManager manager = new CustomPhraseDbManager();
                CustomPhrase modified = (CustomPhrase) mLists.get(currentIndex);
                modified.setMComment(mAddPhraseComment.getText().toString());

                modified.setMExplain(mAddPhraseExplain.getText().toString());

                modified.setMHypy(mTvNewPhrasePy.getText().toString());

                modified.setMPhrase(mTvNewPhrase.getText().toString());

                manager.update(modified);
                initView();
                break;
            case R.id.add_phrase_cancel:
                initView();
                getPhraseContent();
                break;

        }
    }

    private void setLabel(int label) {
        if (mLists.get(currentIndex) instanceof Phrase) {
            PhraseDbManager manager = new PhraseDbManager();
            ((Phrase) mLists.get(currentIndex)).setMLabel(label);
            manager.update((Phrase) mLists.get(currentIndex));
        } else if (mLists.get(currentIndex) instanceof CustomPhrase) {
            CustomPhraseDbManager manager = new CustomPhraseDbManager();
            ((CustomPhrase) mLists.get(currentIndex)).setMLabel(label);
            manager.update((CustomPhrase) mLists.get(currentIndex));
        }
    }


}
