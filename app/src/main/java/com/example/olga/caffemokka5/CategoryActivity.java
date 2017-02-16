package com.example.olga.caffemokka5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import static android.R.id.list;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        Button btCart = (Button)findViewById(R.id.cartCategory);
        btCart.setText("" + globalVariable.cartList.size());
    }

    public void categoryClick(View v){
        Intent intent = new Intent(this, ProductListActivity.class);

        final GlobalClass gv = (GlobalClass) getApplicationContext();

            switch (v.getId()) {
                case R.id.button_coffee:
                    gv.setCategoryNr(1);
                    gv.setCategoryName("Coffee");
                    break;
                case R.id.button_tea:
                    gv.setCategoryNr(2);
                    gv.setCategoryName("Tea");
                    break;
                case R.id.button_breakfast:
                    gv.setCategoryNr(3);
                    gv.setCategoryName("Breakfast");
                    break;
                case R.id.button_cookies:
                    gv.setCategoryNr(4);
                    gv.setCategoryName("Cookies");
                    break;
                default:
                    gv.setCategoryNr(10);
                    gv.setCategoryName("");
                    break;
            }

        startActivity(intent);
        }


    public void categorylistReturnHomeClick(View view){
        Intent intent = new Intent(this, HomeActivity.class);
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
