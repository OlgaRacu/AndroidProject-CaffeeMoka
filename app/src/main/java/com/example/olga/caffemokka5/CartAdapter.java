package com.example.olga.caffemokka5;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Olga on 2016-11-27.
 */

public class CartAdapter extends ArrayAdapter<Cart> {

    Context mContext;
    ArrayList<Cart> cartList = new ArrayList<Cart>();

    int quantityInt;

    Double priceDouble, subtotalDouble;

    TextView name, category, price, subtotal;

    Button btDelete;


    public CartAdapter(Context context, int resource, Cart[] itemname) {
        super(context, R.layout.cartlist, itemname);

        this.mContext=context;

        for (Cart c :((GlobalClass)context.getApplicationContext()).cartList) {
            cartList.add(c);
        }

    }

    Integer categoryNr = 10;

    public View getView(final int position, View view, ViewGroup parent) {

        //Toast.makeText(parent.getContext(), "Adapter works" , Toast.LENGTH_LONG).show();

        LayoutInflater inflater= (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.cartlist, null,true);


        name = (TextView) rowView.findViewById(R.id.tvName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivList);
        category = (TextView) rowView.findViewById(R.id.tvCategory);
        //quantity = (TextView) rowView.findViewById(R.id.tvQuantity);
        price = (TextView) rowView.findViewById(R.id.tvPrice);
        subtotal = (TextView) rowView.findViewById(R.id.tvSubtotal);

        quantityInt = cartList.get(position).getQuantity();
        priceDouble = cartList.get(position).getPrice();
        name.setText(cartList.get(position).getName());
        imageView.setImageBitmap(cartList.get(position).getImage());
        category.setText(cartList.get(position).getCategory());
        price.setText("$" + priceDouble);
        //quantity.setText("" + quantityInt);
        subtotal.setText("Subtotal\n $" + priceDouble);



        final Spinner spinner = (Spinner) rowView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(parent.getContext(),
                R.array.qtyValues, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();

                subtotalDouble = Integer.parseInt(selectedItemText) * priceDouble;

                subtotal.setText("Subtotal\n $" + subtotalDouble);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subtotal.setText("Subtotal\n $" + priceDouble);
            }
        });

        btDelete= (Button) rowView.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete the airport?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Cart item = globalVariable.cartList.get(position);
                            globalVariable.cartList.remove(position);
                            //listC =  globalVariable.cartList.toArray(new Cart[globalVariable.cartList.size()]);
                            //updateAdapter(cartList);
                            //((BaseAdapter) CartActivity.list.getAdapter()).notifyDataSetChanged();
                            notifyDataSetChanged();
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
        });



        return rowView;

    };

    public void updateAdapter(ArrayList<Cart> cartList) {
        this.cartList = cartList;

        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }


}

