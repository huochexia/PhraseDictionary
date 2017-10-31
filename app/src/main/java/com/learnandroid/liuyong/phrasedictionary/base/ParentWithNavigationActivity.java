package com.learnandroid.liuyong.phrasedictionary.base;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnandroid.liuyong.phrasedictionary.Interface.ToolbarListener;
import com.learnandroid.liuyong.phrasedictionary.R;


/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class ParentWithNavigationActivity extends BaseActivity {

    private ToolbarListener listener;
    private TextView tv_title;
    private EditText et_search;
    private ImageView iv_left;
    private ImageView iv_right;


    private <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 工具栏标题
     *
     * @return
     */
    public String title() {
        return null;
    }
    public Object left() {
        return null;
    }
    public Object right() {
        return null;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_navi_left:
                    listener.clickleft();
                    break;
                case R.id.iv_navi_right:
                    listener.clickright();
                    break;
            }

        }
    };
    /**
     * 得到工具栏监听器
     */
    public ToolbarListener setToolbarListener() {
        return null;
    }

    protected void setNaviListener(ToolbarListener listener) {
        this.listener = listener;
    }

    /**
     * 文本事件
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            listener.changedBefore();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            listener.changedText();
        }

        @Override
        public void afterTextChanged(Editable s) {
            listener.changedAfter();
        }
    };

    /**
     * 初始化工具栏
     */
    public void initToolbarView() {
        tv_title = getView(R.id.tv_navi_title);
        //getAssets(）方法是上下文的静态方法，获取Assets管理器
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/隶书.ttf");
        tv_title.setTypeface(typeFace);
        et_search = getView(R.id.et_navi_search);
        iv_left = getView(R.id.iv_navi_left);
        iv_right = getView(R.id.iv_navi_right);

        setNaviListener(setToolbarListener());
        if (title() != null && !title().equals("")) {
            tv_title.setText(title());
        } else {
            et_search.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
            et_search.addTextChangedListener(mTextWatcher);
        }
        if (left() != null) {
            iv_left.setVisibility(View.VISIBLE);
            iv_left.setImageResource(R.drawable.ic_left);
            iv_left.setOnClickListener(clickListener);

        }
        if (right() != null) {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(R.drawable.ic_search);
            iv_right.setOnClickListener(clickListener);
        }
    }


}
