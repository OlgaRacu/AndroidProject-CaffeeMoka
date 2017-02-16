package com.example.olga.caffemokka5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void contactReturnHomeClick(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
