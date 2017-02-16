package com.example.olga.caffemokka5;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    Context context = null;

    ListView list;
    //Cart[] listC;
    ArrayList<Cart> listObj = new ArrayList<Cart>();
    CartAdapter adapter = null;
    TextView tvTotalPrice;
    Button btRemoveAll, btAbortCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        for (Cart c :globalVariable.cartList) {
            listObj.add(c);
        }

        //listC =  globalVariable.cartList.toArray(new Cart[globalVariable.cartList.size()]);

        list = (ListView) findViewById(R.id.lv);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, list,
                false);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer, list,
                false);
        list.addHeaderView(header, null, false);
        list.addFooterView(footer, null, false);


        adapter = new CartAdapter(this, R.layout.cartlist, listObj );

        list.setAdapter(adapter);
        tvTotalPrice = (TextView) list.findViewById(R.id.tvTotalPrice);
        btRemoveAll = (Button) list.findViewById(R.id.btRemoveAll);
        btAbortCart = (Button) list.findViewById(R.id.btAbortCart);

    }

    public class CartAdapter extends ArrayAdapter<Cart> {

        Context mContext;
        ArrayList<Cart> cartList = new ArrayList<Cart>();

        int quantityInt;

        Double priceDouble, subtotalDouble, totalPrice = 0.0;

        TextView name, category, price, subtotal;

        Button btDelete;
        Spinner spinner;

        public CartAdapter(Context context, int resource, ArrayList<Cart> itemname) {
            super(context, R.layout.cartlist, itemname);

            this.mContext=context;
            this.cartList = itemname;
        }

        Integer categoryNr = 10;

        public View getView(final int position, final View view, final ViewGroup parent) {

            //Toast.makeText(parent.getContext(), "Adapter works" , Toast.LENGTH_LONG).show();

            LayoutInflater inflater= (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            final View rowView=inflater.inflate(R.layout.cartlist, null,true);
            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

            name = (TextView) rowView.findViewById(R.id.tvName);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.ivList);
            category = (TextView) rowView.findViewById(R.id.tvCategory);
            //quantity = (TextView) rowView.findViewById(R.id.tvQuantity);
            price = (TextView) rowView.findViewById(R.id.tvPrice);
            subtotal = (TextView) rowView.findViewById(R.id.tvSubtotal);


            quantityInt = globalVariable.cartList.get(position).getQuantity();
            priceDouble = globalVariable.cartList.get(position).getPrice() ;

            name.setText(globalVariable.cartList.get(position).getName());
            imageView.setImageBitmap(globalVariable.cartList.get(position).getImage());
            category.setText(globalVariable.cartList.get(position).getCategory());
            price.setText("Price\n$" + priceDouble);
            //quantity.setText("" + quantityInt);
            subtotal.setText("Subtotal\n $" + (priceDouble * quantityInt));


            spinner = (Spinner) rowView.findViewById(R.id.spinner);
            final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                    R.array.qtyValues, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);

            spinner.setSelection(quantityInt-1);

/*            totalPrice = 0.0;
            for (Cart c: globalVariable.cartList) {
                totalPrice += c.getPrice() * c.getQuantity();
            }
            tvTotalPrice.setText("$" + String.format("%.2f", totalPrice));*/
            calculateTotalPrice();

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int positionSpinner, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(positionSpinner);
                    int selectedItemInt = Integer.parseInt(selectedItemText);
                    globalVariable.cartList.get(position).setQuantity(selectedItemInt);
                    // Notify the selected item text
                    //Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    subtotal = (TextView) rowView.findViewById(R.id.tvSubtotal);
                    priceDouble =  globalVariable.cartList.get(position).getPrice();
                    subtotalDouble = selectedItemInt * priceDouble;
                    //(TextView)list.getItemAtPosition(position).

                    subtotal.setText("Subtotal\n $" + String.format("%.2f", subtotalDouble));
                    calculateTotalPrice();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //subtotal.setText("Subtotal\n $" + priceDouble);
                }
            });

            btDelete= (Button) rowView.findViewById(R.id.btDelete);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
                    //final CartAdapter nadapter = new CartAdapter(mContext, R.layout.cartlist, listC);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete this product?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Cart item = globalVariable.cartList.get(position);
                                globalVariable.cartList.remove(position);
                                listObj.remove(position);
                                calculateTotalPrice();
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "product " + item.getName() + " was deleted", Toast.LENGTH_SHORT).show();

                                if(globalVariable.cartList.size() < 1){
                                    Toast.makeText(mContext, "Empty card was closed", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

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

            btRemoveAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete all products?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Cart item = globalVariable.cartList.get(position);
                                globalVariable.cartList.clear();
                                listObj.clear();
                                calculateTotalPrice();
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "cart was cleared !  ", Toast.LENGTH_SHORT).show();
                                finish();
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
            btAbortCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final GlobalClass globalVariable = (GlobalClass) mContext.getApplicationContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Abort operation?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Cart item = globalVariable.cartList.get(position);
                                globalVariable.cartList.clear();
                                listObj.clear();
                                calculateTotalPrice();
                                Toast.makeText(mContext, "return home!  ", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(parent.getContext(), HomeActivity.class);
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
            });
            return rowView;

        };

        public void calculateTotalPrice(){
            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            Double totalPrice = 0.0;
            for (Cart c: globalVariable.cartList) {
                totalPrice += c.getPrice() * c.getQuantity();
            }
            tvTotalPrice.setText("$" + String.format("%.2f", totalPrice));
        }

    }

    public void checkOutClick(View viewi){
        Intent intent = new Intent(this, ShippingActivity.class);
        startActivity(intent);
    }

    public void returnCategoryClick(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }


}

