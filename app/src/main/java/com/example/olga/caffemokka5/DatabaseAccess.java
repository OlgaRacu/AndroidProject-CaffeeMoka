package com.example.olga.caffemokka5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    Context context;

    final String LOG_TAG = "myLogs";



    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

        this.context = context;
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getCustomersList() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT _customerID, _firstName FROM Customers", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getCategories() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Categories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    // Getting All Contacts
    public ArrayList<Product> getProductsList(int catID) {
        ArrayList<Product> productsList = new ArrayList<Product>();
        // Select All Query
        String selectQueryProduct = "SELECT  * FROM Products WHERE _categoryID =  " + catID;

        String selectQueryCategory = "SELECT  * FROM Categories WHERE _categoryID =  " + catID; /*change with _categoryID !!!!!!*/

        Cursor cursorProduct = database.rawQuery(selectQueryProduct, null);

        Cursor cursorCategory = database.rawQuery(selectQueryCategory, null);

        String categoryName = "";

        if (cursorCategory.moveToFirst()) {
            do {
                categoryName = cursorCategory.getString(1);
            } while (cursorCategory.moveToNext());
        }

        // looping through all rows and adding to list
        if (cursorProduct.moveToFirst()) {
            do {
                Product product = new Product();

                byte[] byteArray = cursorProduct.getBlob(5);

                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);

                product.setId(Integer.parseInt(cursorProduct.getString(0)));
                product.setName(cursorProduct.getString(1));
                product.setPrice(Double.parseDouble(cursorProduct.getString(2)));
                product.setShortDescription(cursorProduct.getString(3));
                product.setLongDescription(cursorProduct.getString(4));
                product.setImage(bm);
                product.setCategory(categoryName);

                // Adding contact to list
                productsList.add(product);
            } while (cursorProduct.moveToNext());
        }
        // return contact list
        return productsList;
    }


    public void fillCartList() {

         final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();

        Collections.sort(globalVariable.cartList);

        for (Cart c : globalVariable.cartList) {
            String selectQueryProductById = "SELECT  * FROM Products WHERE _productID =  " + c.getId();
            Cursor cursorProduct = database.rawQuery(selectQueryProductById, null);


            if (cursorProduct.moveToFirst()) {
                do {
                    byte[] byteArray = cursorProduct.getBlob(5);
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                    // Adding contact to list
                    c.setName(cursorProduct.getString(1));
                    c.setPrice(Double.parseDouble(cursorProduct.getString(2)));
                    c.setImage(bm);
                    //c.setQuantity(1);
                } while (cursorProduct.moveToNext());
            }
        }
    }

    public void insertCustomers(String firstName,
                                String lastName,
                                String address,
                                String city,
                                String postalCode,
                                String phone,
                                String email) {

        Cursor c = database.query("Orders", null, null, null, null, null, null);
        if (c.getCount() == 0) {

            Log.d(LOG_TAG, "--- Begin insertCustomers---");

            ContentValues cv = new ContentValues();

            cv.put("_firstName", firstName);
            cv.put("_lastName", lastName);
            cv.put("_address", address);
            cv.put("_city", city);
            cv.put("_postalCode", postalCode);
            cv.put("_phone", phone);
            cv.put("_email", email);

            database.insert("Customers", null, cv);

            Log.d(LOG_TAG, "--- Insert successfully! ---");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertOrders() {
        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        int lastId = 0;
        String query = "SELECT _customerID from Customers order by _customerID DESC limit 1";
        Cursor c1 = database.rawQuery(query, null);
        if (c1 != null && c1.moveToFirst()) {
            lastId = c1.getInt(0);
        }

        Cursor c = database.query("Orders", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put("_date", currentDate);
            cv.put("_customerID", lastId);

            database.insert("Orders", null, cv);
        }
    }



    public void insertOrderDetails() {

        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();

        int lastId = 0;
        String query = "SELECT _orderID from Orders order by _orderID DESC limit 1";
        Cursor c1 = database.rawQuery(query, null);
        if (c1 != null && c1.moveToFirst()) {
            lastId = c1.getInt(0);
        }

        Cursor c = database.query("OrderDetails", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();

            for (Cart ct : globalVariable.cartList) {
                cv.put("_orderID", lastId);
                cv.put("_productID", ct.getId());
                cv.put("_quantity", ct.getQuantity());
                cv.put("_price", ct.getPrice());

                database.insert("OrderDetails", null, cv);
            }
        }
    }


}

