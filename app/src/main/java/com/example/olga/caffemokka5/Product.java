package com.example.olga.caffemokka5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by Olga on 2016-11-26.
 */

public class Product {
    private int id;
    private String name;
    private double price;
    private String shortDescription;
    private String longDescription;
    private Bitmap image;
    private String category;



    public Product(){}


    public Product(int id, String name, double price, String shortDescription, String longDescription, Bitmap image, String category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.image = image;
        this.category = category;
    }

    public Product(String name, double price, String shortDescription, String longDescription, Bitmap image, String category){
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.image = image;
        this.category = category;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public String getShortDescription(){
        return shortDescription;
    }
    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }

    public String getLongDescription(){
        return longDescription;
    }
    public void setLongDescription(String longDescription){
        this.longDescription = longDescription;
    }

    public Bitmap getImage(){
        return image;
    }
    public void setImage(Bitmap image){
        this.image = image;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }


    public String toString(){
        return this.name;
    }


    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmapResult;
    }

}
