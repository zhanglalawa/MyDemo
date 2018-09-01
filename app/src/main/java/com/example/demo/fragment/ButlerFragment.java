package com.example.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.adapter.ChatAdapter;
import com.example.demo.adapter.MessageAdapter;
import com.example.demo.entity.ChatData;
import com.example.demo.entity.Message;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.fragment
 *  文件名：    ButlerFragment
 *  描述：      之前用的ListView由于Toolbar不会响应ListView的滑动 所以后面全部换成了RecyclerView
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {
    //private ListView chatListView;
    //private List<ChatData> chatDataList;
    private RecyclerView messageRecyclerView;
    private List<Message> messageList = new ArrayList<>();
    //private ChatAdapter adapter;
    private MessageAdapter adapter;
    private EditText editText;
    private Button sendButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        //chatListView = (ListView)view.findViewById(R.id.chat_list_view);
        //chatDataList = new ArrayList<>();
        messageRecyclerView = (RecyclerView)view.findViewById(R.id.message_recycler_view);

        editText = (EditText)view.findViewById(R.id.edit_text);
        sendButton = (Button)view.findViewById(R.id.send_message);
        sendButton.setOnClickListener(this);
        //设置listView适配器
       //adapter = new ChatAdapter(getActivity(),chatDataList);
       //chatListView.setAdapter(adapter);
        //addLeftItem("你好，我是巨硬老火");
        //设置RecyclerView适配器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        messageRecyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messageList);
        messageRecyclerView.setAdapter(adapter);

        messageList.add(new Message("无聊吗！上滑屏幕可以陪你聊天哦~",Message.TYPE_RECEIVED));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_message:
                /*
                * 获取输入框内容
                * 判断是否为空
                * 发送给机器人，向接口请求内容
                * 给listView添加相关的发送内容和机器人的回复*/
                final String message = editText.getText().toString();
                editText.setText("");
                addRightItem(message);
                if (!TextUtils.isEmpty(message)){
                    String url =" http://api.avatardata.cn/Tuling/Ask?key="+ StaticClass.CHAT_ROBOT_KEY+"&info="+message;
                    HttpUtils.sendOkHttpRequest(url,new okhttp3.Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String responseText = response.body().string();
                                        JSONObject jsonObject = new JSONObject(responseText);
                                        if (jsonObject.getInt("error_code")==0) {
                                            JSONObject result = jsonObject.getJSONObject("result");
                                            String answer = result.getString("text");
                                            addLeftItem(answer);
                                        }else {
                                            Toast.makeText(getActivity(),"机器人好像除了点问题......",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    });
                }

                break;
        }
    }

    private void addLeftItem(String received){
        Message receviedMessage = new Message(received,Message.TYPE_RECEIVED);
        updateRecyclerView(receviedMessage);
    }

    private void addRightItem(String sent){
        Message sentMessage = new Message(sent,Message.TYPE_SENT);
        updateRecyclerView(sentMessage);
    }

    private void updateRecyclerView(Message newMessage){
        messageList.add(newMessage);
        adapter.notifyItemInserted(messageList.size()-1);//刷新显示
        messageRecyclerView.scrollToPosition(messageList.size()-1);//RecyclerView定位到最后一行
    }
    /*//添加左边文本
    private void addLeftItem(String text){
        ChatData data = new ChatData();
        data.setType(ChatAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        chatDataList.add(data);
        //通知刷新了
        adapter.notifyDataSetChanged();
        //滚动到底部
        chatListView.setSelection(chatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text){
        ChatData data = new ChatData();
        data.setType(ChatAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        chatDataList.add(data);
        //通知刷新了
        adapter.notifyDataSetChanged();
        //滚动到底部
        chatListView.setSelection(chatListView.getBottom());
    }*/


}
