package com.learnandroid.liuyong.phrasedictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.github.promeg.pinyinhelper.Pinyin;
import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.base.ParentWithNavigationActivity;
import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;
import com.learnandroid.liuyong.phrasedictionary.db.dao.PhraseDao;
import com.learnandroid.liuyong.phrasedictionary.db.manager.CustomPhraseDbManager;
import com.learnandroid.liuyong.phrasedictionary.db.manager.PhraseDbManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/21 0021.
 */

public class AddPhraseToLibrary extends ParentWithNavigationActivity {

    @Bind(R.id.tv_new_phrase)
    EditText mTvNewPhrase;
    @Bind(R.id.tv_new_phrase_py)
    EditText mTvNewPhrasePy;
    @Bind(R.id.add_phrase_explain)
    EditText mAddPhraseExplain;
    @Bind(R.id.add_phrase_comment)
    EditText mAddPhraseComment;

    private CustomPhraseDbManager mPhraseDbManager;
    @Override
    public String title() {
        return "增加成语";
    }

    @Override
    public Object left() {
        return R.drawable.ic_left;
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
        ButterKnife.bind(this);
        initToolbarView();
        mPhraseDbManager = new CustomPhraseDbManager();
        mTvNewPhrase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pinYin = "";
                for(int i= 0 ;i<s.length();i++) {
                    String c=Pinyin.toPinyin(s.charAt(i));//获得第i个字的拼音
                    pinYin = pinYin + c.charAt(0);
                }

                mTvNewPhrasePy.setText(pinYin);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.add_phrase_ok, R.id.add_phrase_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_phrase_ok:
                if (!TextUtils.isEmpty(mTvNewPhrase.getText()) && !TextUtils.isEmpty(mAddPhraseExplain.getText())) {
                    CustomPhrase one = new CustomPhrase();
                    one.setMPhrase(mTvNewPhrase.getText().toString().trim());
                    one.setMHypy(mTvNewPhrasePy.getText().toString().trim());
                    one.setMExplain(mAddPhraseExplain.getText().toString().trim());
                    one.setMComment(mAddPhraseComment.getText().toString().trim());
                    one.setMLabel(0);
                    mPhraseDbManager.insert(one);
                    clear();
                } else {
                    if (TextUtils.isEmpty(mTvNewPhrase.getText())) {
                        toast("成语不能为空！！！");
                    }
                    if (TextUtils.isEmpty(mAddPhraseExplain.getText())) {
                        toast("成语详解不能为空！！！");
                    }
                }

                break;
            case R.id.add_phrase_cancel:
                finish();
                break;
        }
    }

    private void clear() {
        mTvNewPhrase.setText("");
        mTvNewPhrasePy.setText("");
        mAddPhraseComment.setText("");
        mAddPhraseExplain.setText("");
    }
}
