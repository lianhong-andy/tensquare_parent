package com.tensquare.spit.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Collection implements Serializable {
    private String _id;
    private List<Spit> spits;
    private Date createTime;
    private Date updatgeTime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Spit> getSpits() {
        return spits;
    }

    public void setSpits(List<Spit> spits) {
        this.spits = spits;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatgeTime() {
        return updatgeTime;
    }

    public void setUpdatgeTime(Date updatgeTime) {
        this.updatgeTime = updatgeTime;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "_id='" + _id + '\'' +
                ", spits=" + spits +
                ", createTime=" + createTime +
                ", updatgeTime=" + updatgeTime +
                '}';
    }
}
