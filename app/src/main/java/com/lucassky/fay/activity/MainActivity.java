package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lucassky.fay.R;
import com.lucassky.fay.fragment.MainFragment;
import com.lucassky.fay.model.base.User;
import com.lucassky.fay.utils.AccessTokenKeeper;
import com.lucassky.fay.utils.Constants;
import com.lucassky.fay.utils.PreferencesUtils;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.lucassky.fay.view.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Callback, View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainFragment mMainFragment;

    private RoundImageView mUserIcon;
    private TextView mUserScreenName;
    private TextView mUserDes;
    private TextView mUserFavorites;
    private TextView mUserFriendships;

    private TextView mUserSettings;
    private FragmentTransaction mFragTransaction;
    private FragmentManager mFragManager;


    private User mUser;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mFragManager = getSupportFragmentManager();
        mFragTransaction = mFragManager.beginTransaction();
        mMainFragment = MainFragment.newInstance("1", "2");
//        mFragTransaction.add(mMainFragment,"mainfrag").commit();
        setmMainFragment();


        HttpManager.getUserInfo(this, UrlUitl.USER_SHOW, Long.parseLong(AccessTokenKeeper.readAccessToken(this).getUid()), this);
    }


    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        mToolbar.inflateMenu(R.menu.main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mydrawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mUserIcon = (RoundImageView) findViewById(R.id.user_icon);
        mUserScreenName = (TextView) findViewById(R.id.user_screen_name);
        mUserDes = (TextView) findViewById(R.id.user_des);
        mUserSettings = (TextView) findViewById(R.id.drawer_tv_settings);
        mUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("mUserSettings.setOnClickListener");
        }
        });
        mUserFavorites = (TextView) findViewById(R.id.drawer_tv_favorites);
        mUserFavorites.setOnClickListener(this);
        mUserFriendships = (TextView) findViewById(R.id.drawer_tv_friendships);
        mUserFriendships.setOnClickListener(this);
    }

    private void setmMainFragment() {
        mFragTransaction.replace(R.id.container, mMainFragment);
//        mFragTransaction.addToBackStack(null);
        // Commit the transaction
        mFragTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

//       HttpManager.getStattuesFriends(getActivity(),0L, 0L, 10, 1, 0, 0, 0);

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        String tag = (String) response.request().tag();
        if (UrlUitl.USER_SHOW.equals(tag)) {//获取到了用户信息
            try {
                String strRes = response.body().string();
                Gson gson = new Gson();
                mUser = gson.fromJson(strRes, User.class);
                PreferencesUtils.saveValue(this, Constants.USER_ICON_URL, mUser.getAvatar_large());
                PreferencesUtils.saveValue(this, Constants.USER_SCREEN_NAME, mUser.getScreen_name());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(MainActivity.this).load(mUser.getAvatar_large()).into(mUserIcon);
                        mUserScreenName.setText(mUser.getScreen_name());
                        mUserDes.setText(mUser.getDescription());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_tv_favorites:
                startActivity(new Intent(this, FavoritesActivity.class));
                break;
            case R.id.drawer_tv_friendships:
                if(mUser != null){
                    Intent intent = new Intent(this, FriendshipsActivity.class);
                    intent.putExtra("userName",mUser.getScreen_name());
                    intent.putExtra("uid",mUser.getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"缺少用户信息",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
