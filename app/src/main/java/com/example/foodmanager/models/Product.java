package com.example.foodmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import javax.crypto.spec.PSource;

public class Product implements Parcelable {
    private long id;
    private String name;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    private double weight;
    private int kind;
    private int category;

    // Конструктор класса
    public Product() {

    }

    public Product(long id, String name, double proteins, double fats, double carbohydrates, double calories, double weight) {
        this.id = id;
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.weight = weight;
    }

    public Product(long id, String name, double proteins, double fats, double carbohydrates, double calories, double weight, int kind, int category) {
        this.id = id;
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.weight = weight;
        this.kind = kind;
        this.category = category;
    }

    public Product(String name, double proteins, double fats, double carbohydrates, double calories, double weight, int kind, int category) {
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.weight = weight;
        this.kind = kind;
        this.category = category;
    }

    public Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        proteins = in.readDouble();
        fats = in.readDouble();
        carbohydrates = in.readDouble();
        calories = in.readDouble();
        weight=in.readDouble();
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

    public double getWeight() {
        return weight;
    }

    public int getKind() {return kind;}

    public int getCategory() {return category;}

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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setKind(int kind) {this.kind = kind; }

    public void setCategory(int category) {this.category = category;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeDouble(proteins);
        dest.writeDouble(fats);
        dest.writeDouble(carbohydrates);
        dest.writeDouble(calories);
        dest.writeDouble(weight);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
