package com.example.foodmanager.models;

public class Product {
    private long id;
    private String name;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;

    // Конструктор класса
    public Product() {

    }

    public Product(long id, String name, double proteins, double fats, double carbohydrates, double calories) {
        this.id = id;
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
    }

    // геттеры полей

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    //сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
