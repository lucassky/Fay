package com.lucassky.fay.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.utils.StringUtil;
import com.lucassky.fay.utils.TextUitl;
import com.lucassky.fay.view.ExpandGridView;
import com.lucassky.fay.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 朋 on 2015/8/29.
 */
public class StatusAdapter extends BaseAdapter {
    private List<Status> mStatuses;
    private LayoutInflater mInflayter;
    private Context mContext;

    public StatusAdapter(List<Status> mStatuses, Context context) {
        this.mStatuses = mStatuses;
        mInflayter = LayoutInflater.from(context);
        mContext = context;
    }

    public void setmStatuses(List<Status> statuses) {
        if (mStatuses == null)
            mStatuses = new ArrayList<Status>();
        mStatuses.clear();
        mStatuses.addAll(statuses);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return mStatuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflayter.inflate(R.layout.item_status, null);
            viewHolder.userIcon = (RoundImageView) convertView.findViewById(R.id.user_icon);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.statusTvContent = (TextView) convertView.findViewById(R.id.status_tv_content);
            viewHolder.statusFromTime = (TextView) convertView.findViewById(R.id.status_from_time);
            viewHolder.statusTranAndCom = (TextView) convertView.findViewById(R.id.status_transmit_comment);
            viewHolder.gridViewForStatus = (ExpandGridView) convertView.findViewById(R.id.status_gd);
            viewHolder.status2RL = (LinearLayout) convertView.findViewById(R.id.status2_rl);
            viewHolder.status2TvContent = (TextView) convertView.findViewById(R.id.status2_tv_content);
            viewHolder.status2TranAndCom = (TextView) convertView.findViewById(R.id.status2_transmit_comment);
            viewHolder.gridViewForStatus2 = (ExpandGridView) convertView.findViewById(R.id.status2_gd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Status status = mStatuses.get(position);
        Status statusIn = status.getRetweeted_status();
        Picasso.with(mContext).load(status.getUser().getAvatar_large()).into(viewHolder.userIcon);
        viewHolder.setPos(position);
        viewHolder.userName.setText(status.getUser().getName());
        viewHolder.statusFromTime.setText(Html.fromHtml(status.getSource()) + " · " + StringUtil.formarTime(status.getCreated_at()));
        TextUitl.addURLSpan(mContext, status.getText(), viewHolder.statusTvContent);
        if (status.getReposts_count() == 0 && status.getComments_count() == 0) {
            viewHolder.statusTranAndCom.setVisibility(View.GONE);
        } else if (status.getReposts_count() != 0 && status.getComments_count() != 0) {
            viewHolder.statusTranAndCom.setText(status.getReposts_count() + "转发 & " + status.getComments_count() + "评论");
            viewHolder.statusTranAndCom.setVisibility(View.VISIBLE);
        } else if (status.getReposts_count() != 0) {
            viewHolder.statusTranAndCom.setText(status.getReposts_count() + "转发");
            viewHolder.statusTranAndCom.setVisibility(View.VISIBLE);
        } else if (status.getComments_count() != 0) {
            viewHolder.statusTranAndCom.setText(status.getComments_count() + "评论");
            viewHolder.statusTranAndCom.setVisibility(View.VISIBLE);
        }

        if (status.getPic_urls() != null && status.getPic_urls().size() > 0) {
            StatusGridViewAdapter statusGridViewAdapter = new StatusGridViewAdapter(mContext);
            viewHolder.gridViewForStatus.setAdapter(statusGridViewAdapter);
            statusGridViewAdapter.setmThumbnailPics(status.getPic_urls());
            viewHolder.gridViewForStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.gridViewForStatus.setVisibility(View.GONE);
        }


        if (statusIn != null) {
            TextUitl.addURLSpan(mContext, "@" + statusIn.getUser().getName() + ":" + statusIn.getText(), viewHolder.status2TvContent);
            viewHolder.status2TvContent.setMovementMethod(LinkMovementMethod.getInstance());

            viewHolder.status2TvContent.setVisibility(View.VISIBLE);
            if (statusIn.getReposts_count() == 0 && statusIn.getComments_count() == 0) {
                viewHolder.status2TranAndCom.setText("");
                viewHolder.status2TranAndCom.setVisibility(View.GONE);
            } else if (statusIn.getReposts_count() != 0 && statusIn.getComments_count() != 0) {
                viewHolder.status2TranAndCom.setText(statusIn.getReposts_count() + "转发 & " + statusIn.getComments_count() + "评论");
                viewHolder.status2TranAndCom.setVisibility(View.VISIBLE);
            } else if (statusIn.getReposts_count() != 0) {
                viewHolder.status2TranAndCom.setText(statusIn.getReposts_count() + "转发");
                viewHolder.status2TranAndCom.setVisibility(View.VISIBLE);
            } else if (statusIn.getComments_count() != 0) {
                viewHolder.status2TranAndCom.setText(statusIn.getComments_count() + "评论");
                viewHolder.status2TranAndCom.setVisibility(View.VISIBLE);
            }
            viewHolder.status2RL.setVisibility(View.VISIBLE);


            if (statusIn.getPic_urls() != null && statusIn.getPic_urls().size() > 0) {
                StatusGridViewAdapter statusGridViewInAdapter = new StatusGridViewAdapter(mContext);
                viewHolder.gridViewForStatus2.setAdapter(statusGridViewInAdapter);
                statusGridViewInAdapter.setmThumbnailPics(statusIn.getPic_urls());
                viewHolder.gridViewForStatus2.setVisibility(View.VISIBLE);
            } else {
                viewHolder.gridViewForStatus2.setVisibility(View.GONE);
            }

        } else {
            viewHolder.status2TranAndCom.setVisibility(View.GONE);
            viewHolder.status2TranAndCom.setText("");
            viewHolder.status2TvContent.setText("");
            viewHolder.status2RL.setVisibility(View.GONE);
            viewHolder.status2TvContent.setVisibility(View.GONE);
        }
        return convertView;
    }

    private static class ViewHolder {
        int pos;
        private RoundImageView userIcon;
        private TextView userName;
        private TextView statusFromTime;
        private TextView statusTvContent;
        private TextView statusTranAndCom;
        private LinearLayout status2RL;
        private TextView status2TvContent;
        private TextView status2TranAndCom;
        private ExpandGridView gridViewForStatus;
        private ExpandGridView gridViewForStatus2;

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getPos() {
            return pos;
        }
    }
}
