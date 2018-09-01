package com.example.demo.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.adapter.ExpressageAdapter;
import com.example.demo.entity.ExpressageData;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    ExpressageActivity
 *  描述：      TODO
 */
public class ExpressageActivity extends BaseActivity implements View.OnClickListener {
    /** popup窗口里的ListView */
    private ListView popupListView;

    /** popup窗口 */
    private PopupWindow popupWindow;

    Map<String,String> companyAndCode = new HashMap<String,String>(){{
        put("顺丰快递","sf");put("中通快递","zto");put("申通快递","sto");
        put("天天快递","tt");put("圆通快递","yt");put("EMS","ems");
        put("宅急送","zjs");put("韵达快递","yd");put("京东快递","jd");
        put("汇通快递","ht");
    }};

    private TextView editCompany;
    private EditText editListNumber;
    private Button query;
    private ListView listView;
    private List<ExpressageData> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expressage);

        initView();
    }

    private void initView() {
        editCompany = (TextView) findViewById(R.id.company);
        editCompany.setOnClickListener(this);
        editListNumber = (EditText) findViewById(R.id.list_number);
        query = (Button) findViewById(R.id.query);
        listView = (ListView) findViewById(R.id.list_view);
        query.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.company:
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (popupWindow != null && !popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(editCompany);
                }
                break;
            case R.id.query:
                /*
                 * 获取输入框内容
                 * 判断是否为空
                 * 根据输入框的内容去请求数据
                 * 得到返回数据并且解析
                 * ListView适配器
                 * item实体类
                 * 设置数据显示效果*/
                String company = companyAndCode.get(editCompany.getText().toString());
                String listNumber = editListNumber.getText().toString();
                String url = "http://v.juhe.cn/exp/index?key=" +
                        StaticClass.EXPRSSAGE_QUERY_KEY + "&com=" + company + "&no=" + listNumber;
                if (!TextUtils.isEmpty(company)
                        && !TextUtils.isEmpty(listNumber)) {
                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ExpressageActivity.this, "网络请求失败",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dataList = new ArrayList<>();
                                    /*Toast.makeText(ExpressageActivity.this, "response succeed",
                                            Toast.LENGTH_SHORT).show();*/
                                    parseJson(responseText);
                                }
                            });
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 初始化popup窗口
     */
    private void initSelectPopup() {
        popupListView = new ListView(this);
        Set<String> companySet = companyAndCode.keySet();
        final List<String> companyList = new ArrayList<>(companySet);
        // 设置适配器
        ArrayAdapter<String> testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, companyList);
        popupListView.setAdapter(testDataAdapter);

        // 设置ListView点击事件监听
        popupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = companyList.get(position);
                // 把选择的数据展示对应的TextView上
                editCompany.setText(value);
                // 选择完后关闭popup窗口
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(popupListView, editCompany.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.pop_bg);
        popupWindow.setBackgroundDrawable(drawable);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                popupWindow.dismiss();
            }
        });
    }
    /*
    * 解析json数据
    * */
    private void parseJson(String responseText) {
        try {
            JSONObject jsonObject = new JSONObject(responseText);
            if (jsonObject.getInt("error_code") != 0){
                Toast.makeText(this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            //快递列表 让它倒着来 我们才能在最上面获取到最新的消息
            for (int i = jsonArray.length() - 1; i >= 0; i--) {
                JSONObject object = jsonArray.getJSONObject(i);
                ExpressageData data = new ExpressageData();
                data.setRemark(object.getString("remark"));
                data.setDatetime(object.getString("datetime"));
                dataList.add(data);
            }
            ExpressageAdapter adapter = new ExpressageAdapter(this,dataList);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
