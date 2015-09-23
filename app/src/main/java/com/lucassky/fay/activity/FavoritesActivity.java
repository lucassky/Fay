package com.lucassky.fay.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lucassky.fay.R;
import com.lucassky.fay.adapter.StatusAdapter;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements Callback, SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,StatusAdapter.OnAdapterOnClick{
    private final String LOADMORE = "LOADMORE";//loading more tag
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mLVFStatuses;//关注好友的最新微博
    private List<Status> mStatuses = new ArrayList<Status>();
    private StatusAdapter mStatusAdapter;
    private LinearLayout mFooter;
    private boolean isLoadingMore = false;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mFooter = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer, null);
        mLVFStatuses = (ListView) findViewById(R.id.lv_f_statuses);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mStatusAdapter = new StatusAdapter(mStatuses, this,this);
        mLVFStatuses.setAdapter(mStatusAdapter);
        mLVFStatuses.setOnItemClickListener(this);
        mLVFStatuses.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((visibleItemCount + firstVisibleItem) == mStatuses.size() && mStatuses.size() > 19) {
                if (!isLoadingMore) {
                    System.out.println("需要加载更多了");
                    isLoadingMore = true;
                    mLVFStatuses.addFooterView(mFooter);
                    HttpManager.getStattuesFriends(FavoritesActivity.this, LOADMORE, 0L, 0L, 20, pageIndex, 0, 0, 0, FavoritesActivity.this);
                }
            }
        }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
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
    public void onMainClick(Status status) {

    }

    @Override
    public void onUserPicClick(Status status) {

    }

    @Override
    public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
