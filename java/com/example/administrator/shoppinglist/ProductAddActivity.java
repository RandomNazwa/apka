package com.example.administrator.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class ProductAddActivity extends AppCompatActivity {

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbOpenHelper = new DBOpenHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText name = findViewById(R.id.add_name);
        EditText price = findViewById(R.id.add_price);
        EditText quantity = findViewById(R.id.add_quantity);
        CheckBox bought = findViewById(R.id.edit_bought);


    Intent intent = getIntent();
    final int identify = intent.getIntExtra(DBOpenHelper.COLUMN_NAME.id.toString(), 0);
        if (identify != 0) {
        Product product = dbOpenHelper.getOneProduct(identify);
        name.setText(product.name);
        price.setText(Double.toString(product.price));
        quantity.setText(Integer.toString(product.quantity));
        if (product.bought) bought.setChecked(true);

            findViewById(R.id.deleteBtn).setOnClickListener(v -> {
                dbOpenHelper.deleteProduct(identify);
                startActivity(new Intent(this, ProductListActivity.class));
            });
        } else {
            findViewById(R.id.deleteBtn).setVisibility(View.GONE);
        }


        findViewById(R.id.addBtn).setOnClickListener(v -> {
            dbOpenHelper.addProduct(new Product(identify,
                    name.getText().toString(),
                    Double.parseDouble(price.getText().toString()),
                    Integer.parseInt(quantity.getText().toString()),
                    bought.isChecked()
            ));

            startActivity(new Intent(this, ProductListActivity.class));
        });
}}
