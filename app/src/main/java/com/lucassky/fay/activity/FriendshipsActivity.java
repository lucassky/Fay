package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.activity.base.BaseActivity;
import com.lucassky.fay.model.base.FriendshipsResult;
import com.lucassky.fay.utils.AccessTokenKeeper;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class FriendshipsActivity extends BaseActivity implements Callback {

    private ListView mListView4User;

    private int mCursor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutResource();

        mListView4User = (ListView) findViewById(R.id.lv_f_users);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        long uid = intent.getLongExtra("uid", 0);
        HttpManager.getFriendShips(this, UrlUitl.FRIENDSHIPS, uid,userName,20,mCursor,1,this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_friendships;
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        String str = response.body().string();
        Gson gson = new Gson();
        FriendshipsResult result  = gson.fromJson(str,FriendshipsResult.class);

        System.out.println(result.getTotal_number());
    }
}
