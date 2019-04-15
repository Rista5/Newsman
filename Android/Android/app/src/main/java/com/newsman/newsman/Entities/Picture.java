package com.newsman.newsman.Entities;

public class Picture {
    private int id;
    private String name;
    private String description;
    private int newsId;
    private byte[] data;

    public Picture(){}

    public Picture(int id, String name, String description, int newsId, byte[] data){
        this.id = id;
        this.name = name;
        this.description = description;
        this.newsId = newsId;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
