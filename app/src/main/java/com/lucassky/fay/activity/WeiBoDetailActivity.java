package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.adapter.StatusAdapter;
import com.lucassky.fay.adapter.StatusDetailAdapter;
import com.lucassky.fay.adapter.StatusGridViewAdapter;
import com.lucassky.fay.adapter.StatusRVAdapter;
import com.lucassky.fay.model.Comments;
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
public class WeiBoDetailActivity extends AppCompatActivity implements Callback ,StatusDetailAdapter.OnAdapterOnClick{
    private Toolbar mToolBar;
    private TextView mTVStatusContent;
    private ExpandGridView mEGStatusMain;

    private LinearLayout mLLStatusIn;
    private TextView mTVStatusInContent;
    private ExpandGridView mEGStatusIn;
    private TextView mTVStatusInReportComment;

    private RelativeLayout mHeaderView;
    private ListView mListView;
    private LinearLayoutManager mLayoutManager;
    private StatusDetailAdapter mStatusRVAdapter;

    private LinearLayout mReportCommentView;
    private Status mStatus;
    private static Handler mHandler = new Handler() {
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

    private void initView() {
               mToolBar = (Toolbar) findViewById(R.id.mytoolbar);
        mToolBar.setTitle("微博详情");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mHeaderView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.status_detail,mListView,false);
        mReportCommentView = (LinearLayout) findViewById(R.id.report_commtent);


        mTVStatusContent = (TextView)mHeaderView.findViewById(R.id.status_detail_tv_content);
        mEGStatusMain = (ExpandGridView)mHeaderView.findViewById(R.id.status_detail_gd);

        mLLStatusIn = (LinearLayout)mHeaderView.findViewById(R.id.status2_detail_rl);
        mTVStatusInContent = (TextView)mHeaderView.findViewById(R.id.status2_detail_tv_content);
        mEGStatusIn = (ExpandGridView)mHeaderView.findViewById(R.id.status2_detail_gd);
        mTVStatusInReportComment = (TextView)mHeaderView.findViewById(R.id.status2_detail_transmit_comment);


        mTVStatusContent.setText(mStatus.getText());
        TextUitl.addURLSpan(this, mStatus.getText(), mTVStatusContent);
        if (mStatus.getPic_urls() != null && mStatus.getPic_urls().size() > 0) {
            StatusGridViewAdapter statusGridViewAdapter = new StatusGridViewAdapter(this);
            statusGridViewAdapter.setmThumbnailPics(mStatus.getPic_urls());
            mEGStatusMain.setAdapter(statusGridViewAdapter);
            mEGStatusMain.setVisibility(View.VISIBLE);
        }

        if (mStatus.getRetweeted_status() != null) {
            mLLStatusIn.setVisibility(View.VISIBLE);
            TextUitl.addURLSpan(this, "@" + mStatus.getRetweeted_status().getUser().getName() + ":" + mStatus.getRetweeted_status().getText(), mTVStatusInContent);
            mTVStatusInContent.setVisibility(View.VISIBLE);
            if (mStatus.getRetweeted_status().getPic_urls() != null && mStatus.getRetweeted_status().getPic_urls().size() > 0) {
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

        mListView = (ListView) findViewById(R.id.ac_weibo_detail_lv_reports);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem != 0){
                    if(mReportCommentView.getVisibility() == View.GONE)
                        mReportCommentView.setVisibility(View.VISIBLE);
                }else{
                    if(mReportCommentView.getVisibility() == View.VISIBLE)
                         mReportCommentView.setVisibility(View.GONE);
                }
            }
        });
//        mLayoutManager = new LinearLayoutManager(this);
//        mListView.setLayoutManager(mLayoutManager);
//        mStatusRVAdapter = new StatusRVAdapter(mStatuse, new StatusRVAdapter.RVAdapterOnClick() {
//            @Override
//            public void onMainClick(Status status) {
//
//            }
//
//            @Override
//            public void onUserPicClick(Status status) {
//
//            }
//
//            @Override
//            public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {
//
//            }
//        });
        mStatusRVAdapter = new StatusDetailAdapter(mStatuse,this,this);
        mListView.setAdapter(mStatusRVAdapter);
        mListView.addHeaderView(mHeaderView);
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    List<Status> mStatuse = new ArrayList<Status>();

    @Override
    public void onResponse(Response response) throws IOException {

        if (UrlUitl.WEIBO_REPORTS.equals(response.request().tag())) {
            String str = response.body().string();
            Gson gson = new Gson();
            final Comments statuses = gson.fromJson(str, Comments.class);
            if (statuses.getComments() != null && statuses.getComments().size() > 0) {
                mStatuse = statuses.getComments();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusRVAdapter.setmStatuses(mStatuse);
                    }
                });
            }
        }
    }

    @Override
    public void onUserPicClick(Status status) {

    }

    @Override
    public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {

    }
}
