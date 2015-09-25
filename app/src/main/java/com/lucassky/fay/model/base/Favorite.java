package com.lucassky.fay.model.base;

import com.lucassky.fay.model.base.Status;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public class Favorite {
    private Status status;

    private int total_number;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
