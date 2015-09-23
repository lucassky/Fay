package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.adapter.PreviewPicVPAdapter;
import com.lucassky.fay.model.base.ThumbnailPic;

import java.util.ArrayList;

/**
 * Preview Status Pics
 * Created by Administrator on 2015/9/9.
 */
public class PreviewPicActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TextView mPageInditer;
    private int mCount;
    private ArrayList<ThumbnailPic> mThumbnailPics;
    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preview_pic);
    mViewPager = (ViewPager) findViewById(R.id.ac_preview_pic_vp);
    Intent intent = getIntent();
    mThumbnailPics = intent.getParcelableArrayListExtra("thumbnailPics");
    mCount = mThumbnailPics.size();
    mPos = intent.getIntExtra("pos", 0);
    initView();
    System.out.println(mThumbnailPics.size());
}

    private void initView() {
        PreviewPicVPAdapter mAdapter = new PreviewPicVPAdapter(this, mThumbnailPics);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPos, true);
        mViewPager.addOnPageChangeListener(this);

        mPageInditer = (TextView) findViewById(R.id.ac_preview_pic_tv);
        mPageInditer.setText(mPos + 1 + "/" + mCount);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageInditer.setText(position + 1 + "/" + mCount);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
