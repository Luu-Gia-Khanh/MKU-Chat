package com.example.mku_chat.Model;

public class Post {
    private String postid;
    private String postimage;
    private String desc;
    private String publisher;
    private String time_post;

    public Post(String postid, String postimage, String desc, String publisher, String time_post) {
        this.postid = postid;
        this.postimage = postimage;
        this.desc = desc;
        this.publisher = publisher;
        this.time_post = time_post;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTime_post() {
        return time_post;
    }

    public void setTime_post(String time_post) {
        this.time_post = time_post;
    }
}
