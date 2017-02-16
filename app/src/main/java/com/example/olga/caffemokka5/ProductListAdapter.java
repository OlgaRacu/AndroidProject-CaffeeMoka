package com.example.olga.caffemokka5;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Olga on 2016-11-27.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {
    Context mContext;
    Integer categoryNr = 10;


    public ProductListAdapter(Activity context, int resource, Product itemname[]) {
        super(context, R.layout.productlist, itemname);

        this.mContext=context;
        notifyDataSetChanged();
        categoryNr = ((GlobalClass)context.getApplicationContext()).getCategoryNr();

    }

    public View getView(int position, View view, ViewGroup parent) {

        notifyDataSetChanged();
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.productlist, null,true);

       // Toast.makeText(mContext, "Category Number  "+ categoryNr, Toast.LENGTH_SHORT).show();

        TextView name = (TextView) rowView.findViewById(R.id.tvName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivList);
        TextView shortDescription = (TextView) rowView.findViewById(R.id.tvShortDescription);
        TextView category = (TextView) rowView.findViewById(R.id.tvCategory);
        TextView price = (TextView) rowView.findViewById(R.id.tvPrice);

        DatabaseAccess dba = DatabaseAccess.getInstance(parent.getContext());
        dba.open();

        name.setText(dba.getProductsList(categoryNr).get(position).getName());
        imageView.setImageBitmap(dba.getProductsList(categoryNr).get(position).getImage());
        shortDescription.setText(dba.getProductsList(categoryNr).get(position).getShortDescription());
        category.setText(dba.getProductsList(categoryNr).get(position).getCategory());
        price.setText("$" + dba.getProductsList(categoryNr).get(position).getPrice());

        dba.close();

        price.setGravity(Gravity.RIGHT);
        return rowView;

    };


}

