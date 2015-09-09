package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.Status;

/**
 * Created by Administrator on 2015/9/9.
 */
public class WeiBoDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);

        Intent intent = getIntent();
        Status status = intent.getParcelableExtra("status");
        System.out.println(status.getText());
    }
}
