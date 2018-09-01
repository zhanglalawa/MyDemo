package com.example.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.entity.Message;

import java.util.List;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.adapter
 *  文件名：    MessageAdapter
 *  描述：      聊天信息recyclerview的适配器
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private List<Message> messageList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMessage;
        TextView rightMessage;

        public ViewHolder(View view){
            super(view);
            leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            leftMessage = (TextView)view.findViewById(R.id.left_msg);
            rightMessage = (TextView)view.findViewById(R.id.right_msg);
        }
    }

    public MessageAdapter(List<Message>messageList){
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (message.getType()){
            case Message.TYPE_RECEIVED:
                holder.leftLayout.setVisibility(View.VISIBLE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.leftMessage.setText(message.getContent());
                break;
            case Message.TYPE_SENT:
                holder.rightLayout.setVisibility(View.VISIBLE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightMessage.setText(message.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
