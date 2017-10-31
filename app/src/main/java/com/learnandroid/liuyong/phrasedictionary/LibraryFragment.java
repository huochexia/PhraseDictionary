package com.learnandroid.liuyong.phrasedictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class LibraryFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.add_phrase_to_library)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),AddPhraseToLibrary.class);
        startActivity(intent);

    }
}
