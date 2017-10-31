package com.learnandroid.liuyong.phrasedictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learnandroid.liuyong.phrasedictionary.R;
import com.learnandroid.liuyong.phrasedictionary.SinglePhraseActivity;
import com.learnandroid.liuyong.phrasedictionary.db.bean.CustomPhrase;
import com.learnandroid.liuyong.phrasedictionary.db.bean.Phrase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class PhraseListAdapter extends RecyclerView.Adapter<PhraseListAdapter.ViewHolder> {
    List<Object> mObjectList = new ArrayList<>();
    Context mContext;
    LayoutInflater mInflater;
    public PhraseListAdapter(Context context,List<Object> list) {
        mObjectList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public PhraseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_phrase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.serialNum.setText((position+1)+"");
        if (mObjectList.get(position) instanceof Phrase) {
            holder.phraseName.setText(((Phrase) mObjectList.get(position)).getMPhrase());
            holder.phraseExpl.setText(((Phrase) mObjectList.get(position)).getMExplain());
        } else if (mObjectList.get(position) instanceof CustomPhrase) {
            holder.phraseName.setText(((CustomPhrase) mObjectList.get(position)).getMPhrase());
            holder.phraseExpl.setText(((CustomPhrase) mObjectList.get(position)).getMExplain());
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, SinglePhraseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("phrase", (Serializable) mObjectList);
                bundle.putInt("currentIndex",position);
                intent.putExtra(mContext.getPackageName(), bundle);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mObjectList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialNum;
        TextView phraseName;
        TextView phraseExpl;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.ll_item_phrase);
            serialNum = (TextView) itemView.findViewById(R.id.tv_item_phrase_serial_number);
            phraseName = (TextView) itemView.findViewById(R.id.tv_item_phrase_name);
            phraseExpl = (TextView) itemView.findViewById(R.id.tv_item_phrase_explain);

        }
    }
}
