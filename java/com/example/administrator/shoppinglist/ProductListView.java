package com.example.administrator.shoppinglist;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shoppinglist.DBOpenHelper;
import com.example.administrator.shoppinglist.Product;
import com.example.administrator.shoppinglist.ProductAddActivity;
import com.example.administrator.shoppinglist.R;

import java.text.DecimalFormat;

public class ProductListView extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static DecimalFormat formatPrice = new DecimalFormat("$#.00");

    private Context context;
    private DBOpenHelper dbOpenHelperr;

    private Product product;

    private TextView name;
    private TextView price;
    private TextView quantity;
    private CheckBox bought;

    public ProductListView(View view, Context context) {
        super(view);

        this.context = context;

        dbOpenHelperr = new DBOpenHelper(context);

        name = view.findViewById(R.id.add_name);
        price = view.findViewById(R.id.add_price);
        quantity = view.findViewById(R.id.add_quantity);
        bought = view.findViewById(R.id.product_bought);

        view.setOnClickListener(this);

        bought.setOnClickListener(v -> {
            product.bought = !product.bought;
            dbOpenHelperr.addProduct(product);

            if (product.bought) {
                Toast.makeText(context, name.getText() + " bought!", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void bind(Product product) {
        this.product = product;

        name.setText(product.name);
        price.setText(formatPrice.format(product.price));
        quantity.setText(Integer.toString(product.quantity));
        bought.setChecked(product.bought);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this.context, ProductAddActivity.class);
        intent.putExtra(DBOpenHelper.COLUMN_NAME.id.toString(), this.product.id);
        context.startActivity(intent);
    }
}


