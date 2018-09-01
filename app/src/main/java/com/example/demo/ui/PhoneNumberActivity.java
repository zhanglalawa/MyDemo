package com.example.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.StaticClass;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.Response;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    PhoneNumberActivity
 *  描述：      归属地查询
 */
public class PhoneNumberActivity extends BaseActivity implements View.OnClickListener {
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDelete, buttonQuery;
    private EditText phoneNumber;
    private TextView informationTextView;
    private ImageView operator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        initView();
    }

    private void initView() {
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        informationTextView = (TextView) findViewById(R.id.information);
        operator = (ImageView) findViewById(R.id.operator);
        button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);

        buttonDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phoneNumber.setText("");
                return true;
            }
        });

        buttonQuery = (Button) findViewById(R.id.button_query);
        buttonQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String editText = phoneNumber.getText().toString();
        switch (v.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                phoneNumber.setText(editText + ((Button) v).getText());
                phoneNumber.setSelection(editText.length() + 1);
                break;
            case R.id.button_delete:
                if (!TextUtils.isEmpty(editText)) {
                    phoneNumber.setText(editText.substring(0, editText.length() - 1));
                    phoneNumber.setSelection(phoneNumber.getText().toString().length());
                }
                break;
            case R.id.button_query:
                String phone = phoneNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    String url = "http://api.ip138.com/mobile/?mobile=" + phone + "&datatype=xml&token=" + StaticClass.PHONE_QUERY_KEY;
                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PhoneNumberActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String resonseText = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    parsXml(resonseText);
                                }
                            });

                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void parsXml(String responseText) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseText));
            int eventType = xmlPullParser.getEventType();
            StringBuilder information = new StringBuilder();
            boolean flag = true;
            while (eventType != XmlPullParser.END_DOCUMENT && flag) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("ret".equals(nodeName)) {
                            if ("err".equals(xmlPullParser.nextText())){
                                flag = false;
                                Toast.makeText(this,"手机号码无效",Toast.LENGTH_SHORT).show();
                            }
                        } else if ("province".equals(nodeName)) {
                            information.append("归属地：" + xmlPullParser.nextText());
                        } else if ("city".equals(nodeName)) {
                            information.append(xmlPullParser.nextText());
                        } else if ("card".equals(nodeName)) {
                            String company = xmlPullParser.nextText();
                            switch (company) {
                                case "移动":
                                    Glide.with(this).load(R.drawable.china_mobile).into(operator);
                                    break;
                                case "电信":
                                    Glide.with(this).load(R.drawable.china_telecom).into(operator);
                                    break;
                                case "联通":
                                    Glide.with(this).load(R.drawable.china_unicom).into(operator);
                                    break;
                            }
                            information.append("\n运营商：中国" + company);
                        } else if ("zone".equals(nodeName)) {
                            information.append("\n区号：" + xmlPullParser.nextText());
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            informationTextView.setText(information.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
