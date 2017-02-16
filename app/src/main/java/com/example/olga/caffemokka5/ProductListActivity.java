package com.example.olga.caffemokka5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olga.caffemokka5.ProductDetailsActivity;

public class ProductListActivity extends AppCompatActivity {

    ListView list;

    Button cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        int categoryNr = globalVariable.getCategoryNr();


        cart = (Button)findViewById(R.id.cart2);
        cart.setText("" + globalVariable.cartList.size());

        DatabaseAccess dba = DatabaseAccess.getInstance(this);
        dba.open();
        Product[] listA =  dba.getProductsList(categoryNr).toArray(new Product[dba.getProductsList(categoryNr).size()]);


        TextView category = (TextView) findViewById(R.id.tvCategory);
        category.setText(dba.getProductsList(categoryNr).get(0).getCategory());

        dba.close();

        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.productlist,listA);
        list = (ListView) findViewById(R.id.lv);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                intent.putExtra(ProductDetailsActivity.ProductNo, (int) id);
                startActivity(intent);
            }
        });
    }
    public void cartClick(View view) {

            DatabaseAccess dba = DatabaseAccess.getInstance(view.getContext());
            dba.open();

            dba.fillCartList();

            dba.close();
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


    public void returnCategoryClick(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }
}