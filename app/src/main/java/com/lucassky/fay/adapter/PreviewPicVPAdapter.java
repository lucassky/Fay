package com.lucassky.fay.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lucassky.fay.model.base.ThumbnailPic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/9.
 */
public class PreviewPicVPAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<ThumbnailPic> mThumbnailPics;

    public PreviewPicVPAdapter(Context mContext, ArrayList<ThumbnailPic> mThumbnailPics) {
        this.mContext = mContext;
        this.mThumbnailPics = mThumbnailPics;
    }

    @Override
    public int getCount() {
        return mThumbnailPics.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        String url = mThumbnailPics.get(position).getThumbnail_pic();
        url = url.replace("thumbnail","large");
        Picasso.with(mContext).load(url).into(imageView);
                ((ViewPager) container).addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        (ViewPager) container).RemoveView((View)object);
//        super.destroyItem(container, position, object);
//        destroyItem((View) container, position, object);
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
