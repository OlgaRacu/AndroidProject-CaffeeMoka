package com.example.olga.caffemokka5;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class HomeActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        Button btCart = (Button)findViewById(R.id.cart1);
        btCart.setText("" + globalVariable.cartList.size());

    }

    public void menuClick(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

   public void aboutClick(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void contactClick(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }
    public void cartClick(View view) {

        DatabaseAccess dba = DatabaseAccess.getInstance(view.getContext());
        dba.open();

        dba.fillCartList();

        dba.close();
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}

