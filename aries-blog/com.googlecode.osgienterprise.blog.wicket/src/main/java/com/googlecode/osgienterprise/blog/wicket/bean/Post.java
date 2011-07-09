package com.googlecode.osgienterprise.blog.wicket.bean;

import java.io.Serializable;

public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String title;
    private String text;
    private String tags;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
