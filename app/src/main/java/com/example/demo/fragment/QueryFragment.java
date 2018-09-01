package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.ui.ExpressageActivity;
import com.example.demo.ui.PhoneNumberActivity;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.fragment
 *  文件名：    WechatFragment
 *  描述：      TODO
 */
public class QueryFragment extends Fragment implements View.OnClickListener {
    private ImageView queryExpressage;
    private ImageView queryPhoneNumber;
    private ImageView moreThing;
    private CardView expressage;
    private CardView phoneNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query,null);
        initView(view);
        return view;
    }

    private void initView(View view){
        queryExpressage = (ImageView)view.findViewById(R.id.query_expressage);
        Glide.with(this).load(R.drawable.truck).into(queryExpressage);
        queryPhoneNumber = (ImageView)view.findViewById(R.id.query_phone_number);
        Glide.with(this).load(R.drawable.phone).into(queryPhoneNumber);
        moreThing = (ImageView)view.findViewById(R.id.more);
        Glide.with(this).load(R.drawable.ellipsis).into(moreThing);
        expressage = (CardView)view.findViewById(R.id.expressage);
        expressage.setOnClickListener(this);
        phoneNumber = (CardView)view.findViewById(R.id.phone_number);
        phoneNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.expressage:
                startActivity(new Intent(getActivity(), ExpressageActivity.class));
                break;
            case R.id.phone_number:
                startActivity(new Intent(getActivity(), PhoneNumberActivity.class));
                break;
        }
    }
}
