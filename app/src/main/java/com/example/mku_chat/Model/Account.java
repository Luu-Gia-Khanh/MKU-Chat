package com.example.mku_chat.Model;

public class Account {
    private String id;
    private String username;
    private String imageUrl;
    private String status;
    private String search;
    private String phone;
    private String address;

    public Account(String id, String username, String imageURL, String status, String search, String phone, String address) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageURL;
        this.status = status;
        this.search = search;
        this.phone = phone;
        this.address = address;

    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
