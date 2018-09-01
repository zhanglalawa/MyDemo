package com.example.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.entity.ExpressageData;

import java.util.List;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.adapter
 *  文件名：    ExpressageAdapter
 *  描述：      listView适配器
 */
public class ExpressageAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressageData> mList;
    //布局加载器
    private LayoutInflater inflater;
    public ExpressageAdapter(Context mContext,List<ExpressageData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //如果是第一次加载
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.expressage_item,null);
            viewHolder.remark = (TextView)convertView.findViewById(R.id.remark);
            viewHolder.datetime = (TextView)convertView.findViewById(R.id.date_time);
            convertView.setTag(viewHolder);//缓存
        }else {
            viewHolder = (ViewHolder)convertView.getTag();//取出
        }
        ExpressageData data = mList.get(position);
        viewHolder.remark.setText(data.getRemark());
        viewHolder.datetime.setText(data.getDatetime());
        return convertView;
    }

    class ViewHolder{
        private TextView remark;
        private TextView zone;
        private TextView datetime;
    }
}
