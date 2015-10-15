package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.activity.base.BaseActivity;
import com.lucassky.fay.adapter.StatusFavoritesAdapter;
import com.lucassky.fay.model.FavoritesResult;
import com.lucassky.fay.model.base.Favorite;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends BaseActivity implements Callback, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, StatusFavoritesAdapter.OnAdapterOnClick {
    //    private Toolbar mToolBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mLVFStatuses;//关注好友的最新微博
    private List<Favorite> mFavorites = new ArrayList<Favorite>();
    private StatusFavoritesAdapter mStatusFavoritesAdapter;
    private LinearLayout mFooter;
    private boolean isLoadingMore = false;
    private int pageIndex = 1;
    private List<Favorite> mFavoritesForAdd = new ArrayList<Favorite>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
        HttpManager.getFavorites(this, HttpManager.LOADLAST, 20, pageIndex, this);

//        mToolBar = (Toolbar) findViewById(R.id.activity_my_toolbar);
//        getmToolbar().setTitle("我的收藏");
//        setSupportActionBar(mToolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToolBarTitle("我的收藏");
        mFooter = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer, null);
        mLVFStatuses = (ListView) findViewById(R.id.lv_f_statuses);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mStatusFavoritesAdapter = new StatusFavoritesAdapter(mFavorites, this, this);
        mLVFStatuses.setAdapter(mStatusFavoritesAdapter);
        mLVFStatuses.setOnItemClickListener(this);
        mLVFStatuses.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((visibleItemCount + firstVisibleItem) == mFavorites.size() && mFavorites.size() > 19) {
                    if (!isLoadingMore) {
                        System.out.println("需要加载更多了");
                        isLoadingMore = true;
                        mLVFStatuses.addFooterView(mFooter);
                        HttpManager.getFavorites(FavoritesActivity.this, HttpManager.LOADMORE, 20, pageIndex, FavoritesActivity.this);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favorites;
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
        if (mFavorites.size() > 0) {
            isLoadingMore = true;
            HttpManager.getFavorites(this, HttpManager.LOADLAST, 20, 1, this);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isLoadingMore) {
                    if (mLVFStatuses.getFooterViewsCount() > 0) mLVFStatuses.removeFooterView(mFooter);
                    isLoadingMore = false;
                }
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
                if (mLVFStatuses.getFooterViewsCount() > 0) mLVFStatuses.removeFooterView(mFooter);
            }
        });
        String str = response.body().string();
        Gson gson = new Gson();
        final FavoritesResult favoritesResult = gson.fromJson(str, FavoritesResult.class);
        final List<Favorite> favorites = favoritesResult.getFavorites();

        if (HttpManager.LOADLAST.equals(response.request().tag())) {//loading last statuses
            if (favorites != null && favorites.size() > 0) {
                mFavoritesForAdd.addAll(0, favorites);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusFavoritesAdapter.setmFavorites(mFavoritesForAdd);
                        mStatusFavoritesAdapter.notifyDataSetChanged();
                    }
                });
            }
        } else {
            if (favorites != null && favorites.size() > 0) {
                pageIndex++;
                isLoadingMore = false;
                mFavoritesForAdd.addAll(favorites);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusFavoritesAdapter.setmFavorites(mFavoritesForAdd);
                        mStatusFavoritesAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
