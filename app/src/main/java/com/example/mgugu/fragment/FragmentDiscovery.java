package com.example.mgugu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgugu.BaseActivity;
import com.example.mgugu.R;

/**
 * 首页面“发现”fragment
 */
public class FragmentDiscovery extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_discovery,null);
        return view;
    }
}
