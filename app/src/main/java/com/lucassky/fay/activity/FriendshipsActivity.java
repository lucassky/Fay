package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.activity.base.BaseActivity;
import com.lucassky.fay.adapter.FriendshipAdapter;
import com.lucassky.fay.model.base.FriendshipsResult;
import com.lucassky.fay.model.base.User;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FriendshipsActivity extends BaseActivity implements Callback, AdapterView.OnItemClickListener {

    private List<User> mUsers = new ArrayList<>();

    private FriendshipAdapter mFriendshipAdapter;
    private TextView mFooterTV;
    private boolean isLoadingMore = false;

    private long mUid;
    private String mScreenName;

    private int mCursor = 0;

    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutResource();

        ListView mListView4User = (ListView) findViewById(R.id.lv_f_users);
        mFriendshipAdapter = new FriendshipAdapter(this, mUsers);
        mListView4User.setAdapter(mFriendshipAdapter);
        LinearLayout mFooter = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer, null);
        mFooterTV = (TextView) mFooter.findViewById(R.id.footer_tv);
        mListView4User.addFooterView(mFooter);
        mListView4User.setOnItemClickListener(this);

        Intent intent = getIntent();
        mScreenName = intent.getStringExtra("userName");
        mUid = intent.getLongExtra("uid", 0);
        HttpManager.getFriendShips(this, UrlUitl.FRIENDSHIPS, mUid, mScreenName, 20, mCursor, 1, this);

        getData();
    }




    public void getData(){
        new Thread() {
            private BufferedReader br;

            @Override
            public void run() {
                super.run();
                try {
                    String str = "http://fanyi.youdao.com/openapi.do?keyfrom=MDictionary&key=1514213571&type=data&doctype=json&version=1.1&q=" + "猫";
                    URLEncoder.encode(str);
                    URL url = new URL(str);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    uc.setRequestProperty("Charset", "UTF-8");
                    uc.setRequestProperty("Accept-Language", "zh-CN");
                    uc.connect();
                    br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String line;
                    String result = "";
                    while ((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    br.close();

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }











    @Override
    protected int getLayoutResource() {
        return R.layout.activity_friendships;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        isLoadingMore = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFooterTV.setText("点击加载更多");
            }
        });
    }

    @Override
    public void onResponse(Response response) throws IOException {
        isLoadingMore = false;

        String str = response.body().string();

        Gson gson = new Gson();
        FriendshipsResult result = gson.fromJson(str, FriendshipsResult.class);
        mUsers.addAll(result.getUsers());

        mCursor = result.getNext_cursor();
        System.out.println(result.getTotal_number());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFooterTV.setText("点击加载更多");
                mFriendshipAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mUsers.size() > position) {
            Toast.makeText(this, "个人详情页", Toast.LENGTH_SHORT).show();
        } else {
            if (!isLoadingMore) {
                mFooterTV.setText("正在加载更多...");
                isLoadingMore = true;
                HttpManager.getFriendShips(this, UrlUitl.FRIENDSHIPS, mUid, mScreenName, 20, mCursor, 1, this);
            }

        }
    }
}
