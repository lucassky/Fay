package com.lucassky.fay.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.User;
import com.lucassky.fay.utils.StringUtil;
import com.lucassky.fay.utils.TextUitl;
import com.lucassky.fay.view.ExpandGridView;
import com.lucassky.fay.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Administrator on 2015/9/6.
 */
public class StatusRVAdapter extends RecyclerView.Adapter<StatusRVAdapter.ViewHolder> {

    private List<Status> mStatuses;
    private Context mContext;
    private RVAdapterOnClick mRVAdapterOnClick;

    public StatusRVAdapter(List<Status> mStatuses, RVAdapterOnClick rVAdapterOnClick) {
        this.mStatuses = mStatuses;
        this.mRVAdapterOnClick = rVAdapterOnClick;
    }

    public void setMStatuses(List<Status> statuses) {
        if (mStatuses == null)
            mStatuses = new ArrayList<Status>();
        mStatuses.clear();
        mStatuses.addAll(statuses);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false));
    }


    private Status mStatus;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mStatuses.get(position);
        mStatus = status;
        Status statusIn = status.getRetweeted_status();
        Picasso.with(mContext).load(status.getUser().getAvatar_large()).into(holder.userIcon);
        holder.cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRVAdapterOnClick.onMainClick(mStatus);
            }
        });
        TextUitl.addURLSpan(mContext, status.getText(), holder.statusTvContent);

        holder.userName.setText(status.getUser().getName());
        holder.statusFromTime.setText(Html.fromHtml(status.getSource()) + " · " + StringUtil.formarTime(status.getCreated_at()));
        if (status.getReposts_count() == 0 && status.getComments_count() == 0) {
            holder.statusTranAndCom.setVisibility(View.GONE);
        } else if (status.getReposts_count() != 0 && status.getComments_count() != 0) {
            holder.statusTranAndCom.setText(status.getReposts_count() + "转发 & " + status.getComments_count() + "评论");
            holder.statusTranAndCom.setVisibility(View.VISIBLE);
        } else if (status.getReposts_count() != 0) {
            holder.statusTranAndCom.setText(status.getReposts_count() + "转发");
            holder.statusTranAndCom.setVisibility(View.VISIBLE);
        } else {
            holder.statusTranAndCom.setText(status.getComments_count() + "评论");
            holder.statusTranAndCom.setVisibility(View.VISIBLE);
        }

        if (status.getPic_urls() != null && status.getPic_urls().size() > 0) {
            StatusGridViewAdapter statusGridViewAdapter = new StatusGridViewAdapter(mContext);
            holder.gridViewForStatus.setAdapter(statusGridViewAdapter);
            statusGridViewAdapter.setmThumbnailPics(status.getPic_urls());
            holder.gridViewForStatus.setVisibility(View.VISIBLE);
        } else {
            holder.gridViewForStatus.setVisibility(View.GONE);
        }


        if (statusIn != null) {
            User user = statusIn.getUser();
            if (user != null) {
                TextUitl.addURLSpan(mContext, "@" + user.getName() + ":" + statusIn.getText(), holder.status2TvContent);
                holder.status2TvContent.setVisibility(View.VISIBLE);
            } else {
                TextUitl.addURLSpan(mContext, statusIn.getText(), holder.status2TvContent);
                holder.status2TvContent.setVisibility(View.VISIBLE);
            }
            if (statusIn.getReposts_count() == 0 && statusIn.getComments_count() == 0) {
                holder.status2TranAndCom.setText("");
                holder.status2TranAndCom.setVisibility(View.GONE);
            } else if (statusIn.getReposts_count() != 0 && statusIn.getComments_count() != 0) {
                holder.status2TranAndCom.setText(statusIn.getReposts_count() + "转发 & " + statusIn.getComments_count() + "评论");
                holder.status2TranAndCom.setVisibility(View.VISIBLE);
            } else if (statusIn.getReposts_count() != 0) {
                holder.status2TranAndCom.setText(statusIn.getReposts_count() + "转发");
                holder.status2TranAndCom.setVisibility(View.VISIBLE);
            } else {
                holder.status2TranAndCom.setText(statusIn.getComments_count() + "评论");
                holder.status2TranAndCom.setVisibility(View.VISIBLE);
            }
            holder.status2RL.setVisibility(View.VISIBLE);

            if (statusIn.getPic_urls() != null && statusIn.getPic_urls().size() > 0) {
                StatusGridViewAdapter statusGridViewInAdapter = new StatusGridViewAdapter(mContext);
                holder.gridViewForStatus2.setAdapter(statusGridViewInAdapter);
                statusGridViewInAdapter.setmThumbnailPics(statusIn.getPic_urls());
                holder.gridViewForStatus2.setVisibility(View.VISIBLE);
            } else {
                holder.gridViewForStatus2.setVisibility(View.GONE);
            }

        } else {
            holder.status2TranAndCom.setVisibility(View.GONE);
            holder.status2TranAndCom.setText("");
            holder.status2TvContent.setText("");
            holder.status2RL.setVisibility(View.GONE);
            holder.status2TvContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mStatuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cardView;
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

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (LinearLayout) itemView.findViewById(R.id.card_view);
            userIcon = (RoundImageView) itemView.findViewById(R.id.user_icon);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            statusTvContent = (TextView) itemView.findViewById(R.id.status_tv_content);
            statusFromTime = (TextView) itemView.findViewById(R.id.status_from_time);
            statusTranAndCom = (TextView) itemView.findViewById(R.id.status_transmit_comment);
            gridViewForStatus = (ExpandGridView) itemView.findViewById(R.id.status_gd);
            status2RL = (LinearLayout) itemView.findViewById(R.id.status2_rl);
            status2TvContent = (TextView) itemView.findViewById(R.id.status2_tv_content);
            status2TranAndCom = (TextView) itemView.findViewById(R.id.status2_transmit_comment);
            gridViewForStatus2 = (ExpandGridView) itemView.findViewById(R.id.status2_gd);
        }
    }

    public interface RVAdapterOnClick {
        public void onMainClick(Status status);

        public void onUserPicClick(Status status);
    }
}
