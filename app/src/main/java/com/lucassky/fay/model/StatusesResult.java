package com.lucassky.fay.model;

import com.lucassky.fay.model.base.Status;

import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public class StatusesResult {
    private List<Status> statuses;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
