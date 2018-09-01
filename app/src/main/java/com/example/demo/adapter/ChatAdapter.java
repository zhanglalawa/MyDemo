package com.example.demo.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.entity.ChatData;

import java.util.List;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.adapter
 *  文件名：    ChatAdapter
 *  描述：      对话adapter
 *              这里是listView的适配器，由于Toolbar不能响应listview的滑动，所以换成MessageAdapter用Recyclerview了
 */
public class ChatAdapter extends BaseAdapter{
    public static final int VALUE_LEFT_TEXT = 1;
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatData data;
    private List<ChatData> dataList;

    public ChatAdapter(Context mContext,List<ChatData>mList){
        this.mContext = mContext;
        this.dataList = mList;
        //获取系统服务
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的type，根据这个type来区分数据的加载
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.leftTextView = (TextView)convertView.findViewById(R.id.chat_left);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.rightTextView = (TextView)convertView.findViewById(R.id.chat_right);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        }else{
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText)convertView.getTag();
                    break;
            }
        }

        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.leftTextView.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.rightTextView.setText(data.getText());
                break;
        }
        return convertView;
    }
    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        data = dataList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的layout数量
    @Override
    public int getViewTypeCount() {
        return 3;//左边一个layout右边一个layout。本身listView就是个layout
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView leftTextView;
    }
    //右边的文本
    class ViewHolderRightText{
        private TextView rightTextView;
    }
}
