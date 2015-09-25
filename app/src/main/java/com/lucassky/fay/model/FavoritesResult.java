package com.lucassky.fay.model;

import com.lucassky.fay.model.base.Favorite;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public class FavoritesResult {
    private List<Favorite> favorites;
    private int total_number;

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
