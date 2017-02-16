package com.example.olga.caffemokka5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String ProductNo = "productNo";

    Button btCart, addToCart;

    TextView id;

    int categoryNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        categoryNr = globalVariable.getCategoryNr();
        btCart = (Button) findViewById(R.id.cart3);
        btCart.setText("" + globalVariable.cartList.size());

        DatabaseAccess dba = DatabaseAccess.getInstance(this);
        dba.open();

        int productNo = (Integer) getIntent().getExtras().get(ProductNo);
        Product product = dba.getProductsList(categoryNr).get(productNo);

        dba.close();

        TextView name = (TextView) findViewById(R.id.tvDetailsName);
        name.setText(product.getName());

        ImageView ivCoffeeList = (ImageView) findViewById(R.id.ivDetails);
        ivCoffeeList.setImageBitmap(product.getImage());
        ivCoffeeList.setContentDescription(product.getName());

        TextView longDescription = (TextView) findViewById(R.id.tvLongDescription);
        longDescription.setText(product.getLongDescription());

        id = (TextView) findViewById(R.id.tvID);
        id.setText("" + product.getId());
        id.setVisibility(View.INVISIBLE);

        TextView price = (TextView) findViewById(R.id.tvPrice);
        price.setText("$" + product.getPrice());

        addToCart = (Button) findViewById(R.id.addToCart);

        for (Cart c : globalVariable.cartList) {
            if (c.getId() == Integer.parseInt(id.getText().toString())) {
                addToCart.setEnabled(false);
                Toast.makeText(this, "This product was add to cart", Toast.LENGTH_LONG).show();

                break;
            }
        }
    }

    public void returnProductListClick(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        view.invalidate();
        startActivity(intent);
    }


    public void addToCartClick(View view) {

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        Cart cart = new Cart();

        id = (TextView) findViewById(R.id.tvID);

        Button addToCart = (Button) findViewById(R.id.addToCart);

        cart.setId(Integer.parseInt(id.getText().toString()));
        cart.setCategory(globalVariable.getCategoryName());
        cart.setQuantity(1);

        globalVariable.cartList.add(cart);

        btCart.setText("" + globalVariable.cartList.size());

        //Toast.makeText(this, " cart size  after increase =  "+ globalVariable.cartList.size(), Toast.LENGTH_LONG).show();

        addToCart.setEnabled(false);
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



