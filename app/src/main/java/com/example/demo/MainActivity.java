package com.example.demo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.example.demo.fragment.ButlerFragment;
import com.example.demo.fragment.ZhiHuDailyFragment;
import com.example.demo.fragment.UserFragment;
import com.example.demo.fragment.QueryFragment;
import com.example.demo.ui.BaseActivity;
import com.example.demo.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TabLayout
    private TabLayout mTablayout;
    //ViewPager
    private ViewPager mViewPager;
    //Tile
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //FloatingActionButton
    private FloatingActionButton fab_setting;
    //toolbar
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //去掉阴影
        getSupportActionBar().setElevation(0);

        initDate();
        initView();
    }

    //初始化数据
    private void initDate() {
        mTitle = new ArrayList<>();
        mTitle.add(this.getString(R.string.service_manager));
        mTitle.add(this.getString(R.string.selection_wechat));
        mTitle.add(this.getString(R.string.girls));
        mTitle.add(this.getString(R.string.personal_center));

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new QueryFragment());
        mFragment.add(new ZhiHuDailyFragment());
        mFragment.add(new UserFragment());
    }

    //初始化View
    private void initView() {
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        fab_setting.setVisibility(View.GONE);
        mTablayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        //预加载 设置页面数量
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听，在第一个Page上面把FloatingActionBar隐藏
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //这个方法会监听滑动到哪个page了
                // 可以用Log来边滑动边看，Log.i("TAG","position" + position);
                if (position == 0) {
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器 和ListView一样继承自Viewgroup，需要适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的Item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回Item个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
        }
    }
}
