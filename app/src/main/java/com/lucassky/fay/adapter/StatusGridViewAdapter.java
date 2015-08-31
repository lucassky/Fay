package com.lucassky.fay.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lucassky.fay.R;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 */
public class StatusGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ThumbnailPic> mThumbnailPics = new ArrayList<ThumbnailPic>();
    public StatusGridViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setmThumbnailPics(List<ThumbnailPic> mThumbnailPics) {
        this.mThumbnailPics = mThumbnailPics;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mThumbnailPics.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbnailPics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_status_gd,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_stauts_gd_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(mThumbnailPics.get(position).getThumbnail_pic()).into(viewHolder.imageView);
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
    }
}
