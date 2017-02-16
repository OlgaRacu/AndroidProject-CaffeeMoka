package com.example.olga.caffemokka5;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Olga on 2016-11-27.
 */

public class Cart implements Comparable<Cart>{
    private int id;
    private String name;
    private double price;
    private Bitmap image;
    private String category;
    private int quantity;

    //ArrayList<Cart> cartList = new ArrayList<Cart>();

    public Cart(){}


    public Cart(int id, String name, double price, Bitmap image, String category, int quantity){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setImage(image);
        this.setCategory(category);
        this.setQuantity(quantity);
    }

    public Cart(String name, double price, Bitmap image, String category, int quantity){
        this.setName(name);
        this.setPrice(price);
        this.setImage(image);
        this.setCategory(category);
        this.setQuantity(quantity);
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Cart getCartItem(int id, ArrayList<Cart> cartList) {

        return cartList.get(id);
    }

    @Override
    public int compareTo(Cart other) {

        return this.getCategory().compareTo(other.getCategory());
    }


}





