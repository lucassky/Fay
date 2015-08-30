package com.lucassky.fay.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.utils.TextUitl;

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
        if(mStatuses == null)
            mStatuses = new ArrayList<Status>();
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
            viewHolder.userIcon = (ImageView) convertView.findViewById(R.id.user_icon);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.statusTvContent = (TextView) convertView.findViewById(R.id.status_tv_content);
            viewHolder.statusFromTime = (TextView) convertView.findViewById(R.id.status_from_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userName.setText(mStatuses.get(position).getUser().getName());
        viewHolder.statusFromTime.setText(Html.fromHtml(mStatuses.get(position).getSource()) + "-" + mStatuses.get(position).getCreated_at());
        TextUitl.addURLSpan(mContext, mStatuses.get(position).getText(), viewHolder.statusTvContent);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView userIcon;
        private TextView userName;
        private TextView statusFromTime;
        private TextView statusTvContent;
    }
}
