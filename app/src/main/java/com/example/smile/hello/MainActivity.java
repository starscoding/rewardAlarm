package com.example.smile.hello;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity {

    private Timer timer = null;
    private TimerTask timerTask = null;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCookie();
        //初始化一个Adapter
//        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Teacher.getAllTeachers());

        //通过ID获取listView
//        ListView listView = (ListView) findViewById(R.id.teacher_listView);

        //设置listView的Adapter
//        listView.setAdapter(teacherAdapter);

        //this.asynchttpGet();
//        TipHelper.startAlarm(this);

    }
    //5指定onClick属性方式
    public void click(View v) {
        // TODO Auto-generated method stub
        EditText refreshTimeText = findViewById(R.id.refreshTimeText);
        String refreshTimeStr = refreshTimeText.getText().toString();
        EditText alarmTimeText =  findViewById(R.id.alarmTimeText);
        String alarmTimeStr = alarmTimeText.getText().toString();
        EditText cookieText =  findViewById(R.id.cookieText);
        String cookieStr = cookieText.getText().toString();
        Button sureBtn = findViewById(R.id.button);
        sureBtn.setEnabled(false);
        Log.i("获取文本框值",refreshTimeStr+alarmTimeStr);

        this.asynchttpGet2(Integer.parseInt(refreshTimeStr),Integer.parseInt(alarmTimeStr),getCookieText());

    }

    private void asynchttpGet(final int refreshTime, final int alarmTime, final String cookie) {
        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://apis.juhe.cn/mobile/get?phone=13666666666&key=335adcc4e891ba4e4be6d7534fd54c5d";
        String url = "http://www.bjtheden.com/index.php/myhoutai!!nihao/product/index.html";
        client.addHeader("Cookie","PHPSESSID=jnfq9e55v1olk5m5odtq57rpb4");
//        client.addHeader("Cookie",cookie);
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = "";
                try {
                    result = new String(responseBody,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("接口返回值",result);
                TextView textView = (TextView) findViewById(R.id.textView2);
                TextView textView4 = (TextView) findViewById(R.id.textView4);
                String lastRecordTimeStr = Teacher.getResult(result);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date nowTime = new Date();
                Date lastRecordTime = new Date();
                try {
                    lastRecordTime = sdf.parse(lastRecordTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long dif = nowTime.getTime()-lastRecordTime.getTime();

                textView.setText(Teacher.getResult(result));
                textView4.setText(dif/1000+"");
                Log.i("时间间隔",dif/1000+"");
                Toast.makeText(MainActivity.this, "“时间间隔"+dif/1000+"秒", Toast.LENGTH_SHORT).show();
                if(dif/1000>alarmTime){
                    TipHelper.startAlarm(MainActivity.this);
                    try {
                        Thread.sleep(1000*60);
                        asynchttpGet(refreshTime,alarmTime,cookie);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        Thread.sleep(1000*refreshTime);
                        asynchttpGet(refreshTime,alarmTime,cookie);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //Toast.makeText(MainActivity.this, "“request success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                String result = "";
//                try {
//                    result = new String(responseBody,"utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                TextView textView = (TextView) findViewById(R.id.textView2);
                textView.setText(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void asynchttpGet2(final int refreshTime, final int alarmTime, final String cookie) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    //do something
                    Log.i("提示：","进入循环方法！");
                    AsyncHttpClient client = new AsyncHttpClient();
                    String url = "http://www.bjtheden.com/index.php/myhoutai!!nihao/product/index.html";
//                    client.addHeader("Cookie","PHPSESSID=jnfq9e55v1olk5m5odtq57rpb4");
                    client.addHeader("Cookie",cookie);
                    client.get(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String result = "";
                            try {
                                result = new String(responseBody,"utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Log.i("接口返回值",result);
                            TextView textView = findViewById(R.id.textView2);
                            TextView textView4 = findViewById(R.id.textView4);
                            String lastRecordTimeStr = Teacher.getResult(result);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date nowTime = new Date();
                            Date lastRecordTime = new Date();
                            try {
                                lastRecordTime = sdf.parse(lastRecordTimeStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long dif = nowTime.getTime()-lastRecordTime.getTime();

                            textView.setText(Teacher.getResult(result));
                            textView4.setText(dif/1000+"");
                            Log.i("时间间隔",dif/1000+"");
                            Toast.makeText(MainActivity.this, "“最新记录时间："+Teacher.getResult(result)+",时间间隔"+dif/1000+"秒", Toast.LENGTH_SHORT).show();
                            if(dif/1000>alarmTime){
                                TipHelper.startAlarm(MainActivity.this);
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }
                                if (timerTask != null) {
                                    timerTask.cancel();
                                    timerTask = null;
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            TextView textView = (TextView) findViewById(R.id.textView2);
                            textView.setText(error.getMessage());
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                super.handleMessage(msg);
            }
        };

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,1000,refreshTime*1000);//延时1s，每隔500毫秒执行一次run方法
    }

    private String getCookie(){
        final String result = "";
        String loginUrl = "http://www.bjtheden.com/index.php/myhoutai!!nihao/login/login.html";
        AsyncHttpClient client = new AsyncHttpClient();
        //保存cookie，自动保存到了shareprefercece
        PersistentCookieStore myCookieStore = new PersistentCookieStore(MainActivity.this);
        RequestParams params = new RequestParams();
        params.put("username", "jie888");
        params.put("password", "jie888.");
        client.setCookieStore(myCookieStore);
        client.post(loginUrl, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("提示","登录成功！");
                try {
                    String result = new String(responseBody,"utf-8");
                    Log.i("提示",result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "登录成功，cookie=" + getCookieText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return result;
    }

    /**
     * 获取标准 Cookie
     */
    private String getCookieText() {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(MainActivity.this);
        List<Cookie> cookies = myCookieStore.getCookies();
        Log.d("提示", "cookies.size() = " + cookies.size());
        Utils.setCookies(cookies);
        for (Cookie cookie : cookies) {
            Log.d("tip", cookie.getName() + " = " + cookie.getValue());
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        Log.e("cookie", sb.toString());
        return sb.toString();
    }
}
