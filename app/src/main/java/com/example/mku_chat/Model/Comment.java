package com.example.mku_chat.Model;

public class Comment {

    public String comment;
    public String publisher;
    public String time_comment;

    public Comment(String comment, String publisher, String time_comment) {
        this.comment = comment;
        this.publisher = publisher;
        this.time_comment = time_comment;
    }

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTime_comment() {
        return time_comment;
    }

    public void setTime_comment(String time_comment) {
        this.time_comment = time_comment;
    }
}
