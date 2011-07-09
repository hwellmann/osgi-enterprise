package com.googlecode.osgienterprise.blog.wicket.bean;

import java.io.Serializable;

import com.googlecode.osgienterprise.blog.api.BlogAuthor;

public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String fullName;
    private String emailAddress;
    private String bio;
    private String dateOfBirth;

    public Author() {
    }
    
    public Author(BlogAuthor blogAuthor) {
        this.name = blogAuthor.getName();
        this.fullName = blogAuthor.getFullName();
        this.emailAddress = blogAuthor.getEmailAddress();
        this.bio = blogAuthor.getBio();
        this.dateOfBirth = blogAuthor.getDateOfBirth();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
