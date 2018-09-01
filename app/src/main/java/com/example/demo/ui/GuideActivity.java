package com.example.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    GuidActivity
 *  描述：      第一次运行程序引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    //容器
    private List<View>mList = new ArrayList<>();
    //引导页面的三片界面，要在下面加载布局获得view
    private View view1,view2,view3;
    //三个小圆点
    private ImageView point1,point2,point3;
    private List<ImageView> points = new ArrayList<>();
    private List<Boolean> isChecked = new ArrayList<>(3);
    //跳过
    private ImageView jump;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    //初始化View
    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.mViewPager);

        //加载布局获得View
        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);

        //设置字体
        UtilTools.setFont(this,(TextView)view1.findViewById(R.id.tv_guide));
        UtilTools.setFont(this,(TextView)view2.findViewById(R.id.tv_guide));
        UtilTools.setFont(this,(TextView)view3.findViewById(R.id.tv_guide));

        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        //初始化容器
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //初始化point
        point1 = (ImageView)findViewById(R.id.point1);
        point2 = (ImageView)findViewById(R.id.point2);
        point3= (ImageView)findViewById(R.id.point3);

        //容器初始化
        points.add(point1);
        points.add(point2);
        points.add(point3);
        isChecked.add(true);
        isChecked.add(false);
        isChecked.add(false);
        //小圆点视图初始化
        setPointImg(isChecked);

        jump = (ImageView) findViewById(R.id.jump);
        jump.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < isChecked.size(); i ++){
                    if (i!=position){
                        isChecked.set(i,false);
                    }else{
                        isChecked.set(i,true);
                    }
                }
                //跳过的可见行
                setPointImg(isChecked);
                if (position == isChecked.size() - 1) {
                    jump.setVisibility(View.GONE);
                }else{
                    jump.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ((ViewPager)container).addView(mList.get(position));
                return mList.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                ((ViewPager)container).removeView(mList.get(position));
            }
        });
    }

    //设置小圆点的选中效果
    private void setPointImg(List<Boolean> isChecked){
        for (int i = 0; i < isChecked.size(); i ++){
            if(isChecked.get(i)){
                points.get(i).setBackgroundResource(R.drawable.point_on);
            }else{
                points.get(i).setBackgroundResource(R.drawable.point_off);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
            case R.id.jump:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
