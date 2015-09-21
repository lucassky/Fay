package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucassky.fay.R;
import com.lucassky.fay.adapter.StatusRVAdapter;
import com.lucassky.fay.model.StatusesResult;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeiBoTopicActivity extends AppCompatActivity implements Callback, StatusRVAdapter.RVAdapterOnClick, SwipeRefreshLayout.OnRefreshListener {
    private final String LOADMORE = "LOADMORE";//loading more tag
    private final String LOADLAST = "LOADLAST";//loading the last statuses

    private String mTopic;
    private Toolbar mToolBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclView;
    private StatusRVAdapter mStatusRVAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mCount = 20;
    private int mPage = 1;
    private List<Status> mStatuses = new ArrayList<Status>();
    private boolean isLoadingMore = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_topic);
        mTopic = getIntent().getStringExtra("topic");
        HttpManager.getStatusesByTopics(this, LOADLAST, mTopic, mCount, mPage, this);
        initView();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.mytoolbar);
        mToolBar.setTitle(mTopic);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclView = (RecyclerView) findViewById(R.id.lv_f_statuses);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclView.setLayoutManager(mLayoutManager);
        mStatusRVAdapter = new StatusRVAdapter(mStatuses, this);
        mRecyclView.setAdapter(mStatusRVAdapter);
        mRecyclView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastvisiblePos = mLayoutManager.findLastVisibleItemPosition();
                lastvisiblePos++;
                if (lastvisiblePos == mStatuses.size()) {
//                    mLVFStatuses.addView(mFooter);
                    if (!isLoadingMore) {
                        System.out.println("需要加载更多了");
                        isLoadingMore = true;
                        HttpManager.getStatusesByTopics(WeiBoTopicActivity.this, LOADMORE, mTopic, mCount, mPage, WeiBoTopicActivity.this);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wei_bo_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResponse(Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        String str = response.body().string();
        Gson gson = new Gson();

        final StatusesResult statuses = gson.fromJson(str, StatusesResult.class);
//        final List<Status> statuse = gson.fromJson(str, new TypeToken<List<Status>>(){}.getType());
        final List<Status> statusList = statuses.getStatuses();
        if (LOADLAST.equals(response.request().tag())) {//loading last statuses
            if (statusList != null && statusList.size() > 0) {
                mStatuses.addAll(0, statusList);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusRVAdapter.setMStatuses(mStatuses);
                    }
                });
            }
        } else {
            if (statusList != null && statusList.size() > 0) {
                mPage++;
                isLoadingMore = false;
                mStatuses.addAll(statusList);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusRVAdapter.setMStatuses(mStatuses);
                    }
                });
            }
        }
        System.out.println("response" + statuses.toString());
    }

    @Override
    public void onMainClick(Status status) {
        Intent intent = new Intent(this, WeiBoDetailActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
        System.out.println(status.getText());
    }

    @Override
    public void onUserPicClick(Status status) {

    }

    @Override
    public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {
        Intent intent = new Intent(this, PreviewPicActivity.class);
        intent.putParcelableArrayListExtra("thumbnailPics", thumbnailPics);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (mStatuses.size() > 0) {
            isLoadingMore = true;
            HttpManager.getStatusesByTopics(this, LOADLAST, mTopic, mCount, mPage, this);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }
}
