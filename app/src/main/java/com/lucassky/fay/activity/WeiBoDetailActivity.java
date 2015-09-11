package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.adapter.StatusGridViewAdapter;
import com.lucassky.fay.adapter.StatusRVAdapter;
import com.lucassky.fay.model.StatusesResult;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.utils.TextUitl;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.lucassky.fay.view.ExpandGridView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class WeiBoDetailActivity extends AppCompatActivity implements Callback{
    private Toolbar mToolBar;
    private TextView mTVStatusContent;
    private ExpandGridView mEGStatusMain;

    private LinearLayout mLLStatusIn;
    private TextView mTVStatusInContent;
    private ExpandGridView mEGStatusIn;
    private TextView mTVStatusInReportComment;

    private RecyclerView mListView;
    private StatusRVAdapter mStatusRVAdapter;

    private Status mStatus;
    private static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);

        Intent intent = getIntent();
        mStatus = intent.getParcelableExtra("status");
        initView();
        HttpManager.getStatusCommtentsByID(this, UrlUitl.WEIBO_REPORTS, mStatus.getId(), 0, 0, 20, 1, 0, this);
    }

    private void initView(){
        mToolBar = (Toolbar) findViewById(R.id.mytoolbar);
        mToolBar.setTitle("微博详情");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTVStatusContent = (TextView) findViewById(R.id.status_detail_tv_content);
        mEGStatusMain = (ExpandGridView) findViewById(R.id.status_detail_gd);

        mLLStatusIn = (LinearLayout) findViewById(R.id.status2_detail_rl);
        mTVStatusInContent = (TextView) findViewById(R.id.status2_detail_tv_content);
        mEGStatusIn = (ExpandGridView) findViewById(R.id.status2_detail_gd);
        mTVStatusInReportComment = (TextView) findViewById(R.id.status2_detail_transmit_comment);


        mTVStatusContent.setText(mStatus.getText());
        TextUitl.addURLSpan(this, mStatus.getText(), mTVStatusContent);
        if(mStatus.getPic_urls() != null && mStatus.getPic_urls().size()>0){
            StatusGridViewAdapter statusGridViewAdapter = new StatusGridViewAdapter(this);
            statusGridViewAdapter.setmThumbnailPics(mStatus.getPic_urls());
            mEGStatusMain.setAdapter(statusGridViewAdapter);
            mEGStatusMain.setVisibility(View.VISIBLE);
        }

        if(mStatus.getRetweeted_status() != null){
            mLLStatusIn.setVisibility(View.VISIBLE);
            TextUitl.addURLSpan(this, "@" + mStatus.getRetweeted_status().getUser().getName() + ":" + mStatus.getRetweeted_status().getText(),  mTVStatusInContent);
            mTVStatusInContent.setVisibility(View.VISIBLE);
            if(mStatus.getRetweeted_status().getPic_urls() != null && mStatus.getRetweeted_status().getPic_urls().size()>0){
                StatusGridViewAdapter statusGridViewAdapter = new StatusGridViewAdapter(this);
                statusGridViewAdapter.setmThumbnailPics(mStatus.getRetweeted_status().getPic_urls());
                mEGStatusIn.setAdapter(statusGridViewAdapter);
                mEGStatusIn.setVisibility(View.VISIBLE);
            }
            if (mStatus.getRetweeted_status().getReposts_count() == 0 && mStatus.getRetweeted_status().getComments_count() == 0) {
                mTVStatusInReportComment.setText("");
                mTVStatusInReportComment.setVisibility(View.GONE);
            } else if (mStatus.getRetweeted_status().getReposts_count() != 0 && mStatus.getRetweeted_status().getComments_count() != 0) {
                mTVStatusInReportComment.setText(mStatus.getRetweeted_status().getReposts_count() + "转发 & " + mStatus.getRetweeted_status().getComments_count() + "评论");
                mTVStatusInReportComment.setVisibility(View.VISIBLE);
            } else if (mStatus.getRetweeted_status().getReposts_count() != 0) {
                mTVStatusInReportComment.setText(mStatus.getRetweeted_status().getReposts_count() + "转发");
                mTVStatusInReportComment.setVisibility(View.VISIBLE);
            } else if (mStatus.getRetweeted_status().getComments_count() != 0) {
                mTVStatusInReportComment.setText(mStatus.getRetweeted_status().getComments_count() + "评论");
                mTVStatusInReportComment.setVisibility(View.VISIBLE);
            }
        }

        mListView = (RecyclerView) findViewById(R.id.ac_weibo_detail_lv_reports);
        mStatusRVAdapter = new StatusRVAdapter(mStatuse, new StatusRVAdapter.RVAdapterOnClick() {
            @Override
            public void onMainClick(Status status) {

            }

            @Override
            public void onUserPicClick(Status status) {

            }

            @Override
            public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {

            }
        });
        mListView.setAdapter(mStatusRVAdapter);
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }
    List<Status> mStatuse = new ArrayList<Status>();
    @Override
    public void onResponse(Response response) throws IOException {

        if(UrlUitl.WEIBO_REPORTS.equals(response.request().tag())){
            String str = response.body().string();
            Gson gson = new Gson();
            final StatusesResult statuses = gson.fromJson(str, StatusesResult.class);
            if(statuses.getStatuses() != null && statuses.getStatuses().size()>0){
                mStatuse = statuses.getStatuses();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusRVAdapter.setMStatuses(mStatuse);
                    }
                });
            }
        }
    }
}
