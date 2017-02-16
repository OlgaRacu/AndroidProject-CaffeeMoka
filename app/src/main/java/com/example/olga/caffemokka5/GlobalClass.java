package com.example.olga.caffemokka5;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Olga on 2016-11-26.
 */

public class GlobalClass extends Application {

    private int categoryNr;

    public ArrayList<Cart> cartList = new ArrayList<Cart>();

    private String categoryName;


    public int getCategoryNr() {
        return categoryNr;
    }

    public void setCategoryNr(int categoryNr) {

        this.categoryNr = categoryNr;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



}

    /*final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

      globalVariable.setName("Android Example context variable");

      final String name  = globalVariable.getName(); */