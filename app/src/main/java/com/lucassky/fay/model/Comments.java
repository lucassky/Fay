package com.lucassky.fay.model;

import com.lucassky.fay.model.base.Status;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public class Comments {
    private List<Status> comments;

    public List<Status> getComments() {
        return comments;
    }

    public void setComments(List<Status> comments) {
        this.comments = comments;
    }
}
