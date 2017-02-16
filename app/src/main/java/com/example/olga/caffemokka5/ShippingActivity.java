package com.example.olga.caffemokka5;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShippingActivity extends AppCompatActivity {

    TextView etFirstName, etLastName, etAddress, etCity, etPostalCode, etPhone, etEmail;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        etFirstName = (TextView) findViewById(R.id.etFirstName);
        etLastName = (TextView) findViewById(R.id.etLastName);
        etAddress = (TextView) findViewById(R.id.etAddress);
        etCity = (TextView) findViewById(R.id.etCity);
        etPostalCode = (TextView) findViewById(R.id.etPostalCode);
        etPhone = (TextView) findViewById(R.id.etPhone);
        etEmail = (TextView) findViewById(R.id.etEmail);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendOrderClick(final View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ready to send?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                    globalVariable.cartList.clear();

                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);

                } catch (SQLException sql) {
                    sql.printStackTrace();
                    sql.getMessage();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String address = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String postalCode = etPostalCode.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();


        DatabaseAccess dba = DatabaseAccess.getInstance(this);
        dba.open();

        dba.insertCustomers(firstName, lastName, address, city, postalCode, phone, email);

        //Toast.makeText(view.getContext(), "Successfull insert" , Toast.LENGTH_LONG).show();

        //Log.d(LOG_TAG, "--- Insert successfully! ---");

        dba.insertOrders();

        dba.insertOrderDetails();

        dba.close();


    }

    public void closeOrderClick(final View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to cancel the cart??");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                    globalVariable.cartList.clear();

                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);

                } catch (SQLException sql) {
                    sql.printStackTrace();
                    sql.getMessage();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    public void returnCartListClick(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        view.invalidate();
        startActivity(intent);
    }

}
