package com.example.foodmanager.models;

public class Category {
    private long id ;
    private String name;

    public Category (long id, String name){
        this.id=id;
        this.name=name;
    }

    // геттеры полей
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    //сеттеры
    public void setName(String name) {
        this.name = name;
    }
}

