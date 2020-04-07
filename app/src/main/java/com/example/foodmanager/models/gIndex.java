package com.example.foodmanager.models;

public class gIndex {
    private long id ;
    private String type;

    public gIndex (long id, String type){
        this.id=id;
        this.type=type;
    }

    // геттеры полей
    public long getId() {
        return id;
    }
    public String getName() {
        return type;
    }

    //сеттеры
    public void setName(String type) {
        this.type = type;
    }
}
