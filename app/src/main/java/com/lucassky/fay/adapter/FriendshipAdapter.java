package com.lucassky.fay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.User;
import com.lucassky.fay.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FriendshipAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<User> mUser;

    public FriendshipAdapter(Context mContext, List<User> users) {
        this.mContext = mContext;
        this.mUser = users;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_friendship, null);
            viewHolder.roundImageView = (RoundImageView) convertView.findViewById(R.id.user_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.user_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = mUser.get(position);
        Picasso.with(mContext).load(user.getAvatar_large()).into(viewHolder.roundImageView);
        viewHolder.textView.setText(user.getScreen_name());
        return convertView;
    }

    class ViewHolder {
        private RoundImageView roundImageView;
        private TextView textView;
    }
}
