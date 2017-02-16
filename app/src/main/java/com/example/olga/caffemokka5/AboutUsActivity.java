package com.example.olga.caffemokka5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    public void aboutUsReturnHomeClick(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
