package com.lucassky.fay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.adapter.StatusGridViewAdapter;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.utils.TextUitl;
import com.lucassky.fay.view.ExpandGridView;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2015/9/9.
 */
public class WeiBoDetailActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextView mTVStatusContent;
    private ExpandGridView mEGStatusMain;

    private LinearLayout mLLStatusIn;
    private TextView mTVStatusInContent;
    private ExpandGridView mEGStatusIn;
    private TextView mTVStatusInReportComment;

    private Status mStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);

        Intent intent = getIntent();
        mStatus = intent.getParcelableExtra("status");
        initView();
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
    }
}
