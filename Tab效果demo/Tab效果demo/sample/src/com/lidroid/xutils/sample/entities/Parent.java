package com.lidroid.xutils.sample.entities;

import com.lidroid.xutils.db.annotation.Finder;

import java.util.Date;
import java.util.List;

/**
 * Author: wyouflf
 * Date: 13-7-25
 * Time: 下午7:06
 */
public class Parent extends EntityBase {

    public String name;

    private String email;

    private boolean isAdmin;

    private Date time;

    private java.sql.Date date;

    //@Finder(valueColumn = "id",targetColumn = "parentId")
    //public FinderLazyLoader<Parent> parent; // 关联对象多时建议使用这种方式，延迟加载效率较高。
    //@Finder(valueColumn = "id",targetColumn = "parentId")
    //public Parent parent;
    @Finder(valueColumn = "id", targetColumn = "parentId")
    private List<Child> children;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
