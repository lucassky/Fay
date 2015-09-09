package com.lucassky.fay.model.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/8/31.
 */
public class ThumbnailPic implements Parcelable{
    private String thumbnail_pic;//缩略图地址

    protected ThumbnailPic(Parcel in) {
        thumbnail_pic = in.readString();
    }

    public static final Creator<ThumbnailPic> CREATOR = new Creator<ThumbnailPic>() {
        @Override
        public ThumbnailPic createFromParcel(Parcel in) {
            return new ThumbnailPic(in);
        }

        @Override
        public ThumbnailPic[] newArray(int size) {
            return new ThumbnailPic[size];
        }
    };

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail_pic);
    }
}
