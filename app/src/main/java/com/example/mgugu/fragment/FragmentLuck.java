package com.example.mgugu.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.mgugu.BaseActivity;
import com.example.mgugu.R;

/**
 * 首页面“拼手气”的fragment
 */
public class FragmentLuck extends BaseFragment {
    private Button mBtnAddPoint;//加小红点
    private CheckBox mCheckBoxPre;//活动中的“摇一摇”按键
    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luck, null);
        mBtnAddPoint = (Button) view.findViewById(R.id.btn_fragment_add_redpoint);
        mCheckBoxPre = (CheckBox) getActivity().findViewById(R.id.checkbox_preferential);
        mBtnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.dot_red_16);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    mCheckBoxPre.setCompoundDrawables(null, null, rightDrawable, null);

                } else {
//                    Drawable rightDrawable = getResources().getDrawable(R.color.white);
//                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    mCheckBoxPre.setCompoundDrawables(null, null, null, null);
                }
                flag = !flag;

            }
        });
        return view;
    }
}
