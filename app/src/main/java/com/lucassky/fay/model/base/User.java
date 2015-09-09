package com.lucassky.fay.model.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lucas on 2015/8/26.
 * WeiBo user model
 */
public class User implements Parcelable{
    private long id;//用户UID
    private String idstr;//字符串型的用户UID
    private String screen_name;//用户昵称
    private String name;//友好显示名称
    private int province;//用户所在省级ID
    private int city;//用户所在城市ID
    private String location;//	string	用户所在地
    private String description;//	string	用户个人描述
    private String url;//	string	用户博客地址
    private String profile_image_url;//	string	用户头像地址（中图），50×50像素
    private String profile_url;//	string	用户的微博统一URL地址
    private String domain;//	string	用户的个性化域名
    private String weihao;//	string	用户的微号
    private String gender;//	string	性别，m：男、f：女、n：未知
    private int followers_count;//粉丝数
    private int friends_count;//关注数
    private int statuses_count;//微博数
    private int favourites_count;//收藏数
    private String created_at;//用户创建（注册）时间
    private boolean following;//暂未支持
    private boolean allow_all_act_msg;//是否允许所有人给我发私信，true：是，false：否
    private boolean geo_enabled;//是否允许标识用户的地理位置，true：是，false：否
    private boolean verified;//是否是微博认证用户，即加V用户，true：是，false：否
    private int verified_type;//	暂未支持
    private String remark;//用户备注信息，只有在查询用户关系时才返回此字段
    private Status object;//用户的最近一条微博信息字段 详细
    private boolean allow_all_comment;//是否允许所有人对我的微博进行评论，true：是，false：否
    private String avatar_large;//用户头像地址（大图），180×180像素
    private String avatar_hd;//用户头像地址（高清），高清头像原图
    private String verified_reason;//认证原因
    private boolean follow_me;//该用户是否关注当前登录用户，true：是，false：否
    private int online_status;//用户的在线状态，0：不在线、1：在线
    private int bi_followers_count;//用户的互粉数
    private String lang;//用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语

    protected User(Parcel in) {
        id = in.readLong();
        idstr = in.readString();
        screen_name = in.readString();
        name = in.readString();
        province = in.readInt();
        city = in.readInt();
        location = in.readString();
        description = in.readString();
        url = in.readString();
        profile_image_url = in.readString();
        profile_url = in.readString();
        domain = in.readString();
        weihao = in.readString();
        gender = in.readString();
        followers_count = in.readInt();
        friends_count = in.readInt();
        statuses_count = in.readInt();
        favourites_count = in.readInt();
        created_at = in.readString();
        verified_type = in.readInt();
        remark = in.readString();
        object = in.readParcelable(Status.class.getClassLoader());
        avatar_large = in.readString();
        avatar_hd = in.readString();
        verified_reason = in.readString();
        online_status = in.readInt();
        bi_followers_count = in.readInt();
        lang = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(int statuses_count) {
        this.statuses_count = statuses_count;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(int favourites_count) {
        this.favourites_count = favourites_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public void setAllow_all_act_msg(boolean allow_all_act_msg) {
        this.allow_all_act_msg = allow_all_act_msg;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getVerified_type() {
        return verified_type;
    }

    public void setVerified_type(int verified_type) {
        this.verified_type = verified_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getObject() {
        return object;
    }

    public void setObject(Status object) {
        this.object = object;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public void setAllow_all_comment(boolean allow_all_comment) {
        this.allow_all_comment = allow_all_comment;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public void setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public void setFollow_me(boolean follow_me) {
        this.follow_me = follow_me;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public int getBi_followers_count() {
        return bi_followers_count;
    }

    public void setBi_followers_count(int bi_followers_count) {
        this.bi_followers_count = bi_followers_count;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(idstr);
        dest.writeString(screen_name);
        dest.writeString(name);
        dest.writeInt(province);
        dest.writeInt(city);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(profile_image_url);
        dest.writeString(profile_url);
        dest.writeString(domain);
        dest.writeString(weihao);
        dest.writeString(gender);
        dest.writeInt(followers_count);
        dest.writeInt(friends_count);
        dest.writeInt(statuses_count);
        dest.writeInt(favourites_count);
        dest.writeString(created_at);
        dest.writeInt(verified_type);
        dest.writeString(remark);
        dest.writeParcelable(object, flags);
        dest.writeString(avatar_large);
        dest.writeString(avatar_hd);
        dest.writeString(verified_reason);
        dest.writeInt(online_status);
        dest.writeInt(bi_followers_count);
        dest.writeString(lang);
    }
}
