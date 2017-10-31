package com.learnandroid.liuyong.phrasedictionary.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/10/20 0020.
 */
@Entity
public class Phrase implements  Serializable{
    public static final long serialVersionUID = 1;
    @Id(autoincrement = true)
    private Long _id;
    @NotNull
    private String mPhrase;
    @NotNull
    private String mHypy;
    @NotNull
    private String mExplain;
    private String mComment;
    private Integer mLabel;
    private String mFirstTime;
    private Integer mAccurary;
    @Generated(hash = 505733824)
    public Phrase(Long _id, @NotNull String mPhrase, @NotNull String mHypy,
            @NotNull String mExplain, String mComment, Integer mLabel,
            String mFirstTime, Integer mAccurary) {
        this._id = _id;
        this.mPhrase = mPhrase;
        this.mHypy = mHypy;
        this.mExplain = mExplain;
        this.mComment = mComment;
        this.mLabel = mLabel;
        this.mFirstTime = mFirstTime;
        this.mAccurary = mAccurary;
    }
    @Generated(hash = 1136898449)
    public Phrase() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getMPhrase() {
        return this.mPhrase;
    }
    public void setMPhrase(String mPhrase) {
        this.mPhrase = mPhrase;
    }
    public String getMHypy() {
        return this.mHypy;
    }
    public void setMHypy(String mHypy) {
        this.mHypy = mHypy;
    }
    public String getMExplain() {
        return this.mExplain;
    }
    public void setMExplain(String mExplain) {
        this.mExplain = mExplain;
    }
    public String getMComment() {
        return this.mComment;
    }
    public void setMComment(String mComment) {
        this.mComment = mComment;
    }
    public Integer getMLabel() {
        return this.mLabel;
    }
    public void setMLabel(Integer mLabel) {
        this.mLabel = mLabel;
    }
    public String getMFirstTime() {
        return this.mFirstTime;
    }
    public void setMFirstTime(String mFirstTime) {
        this.mFirstTime = mFirstTime;
    }
    public Integer getMAccurary() {
        return this.mAccurary;
    }
    public void setMAccurary(Integer mAccurary) {
        this.mAccurary = mAccurary;
    }


}
