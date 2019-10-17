package com.example.rubab.slider.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rubab.slider.R;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.CartModel;

import java.io.IOException;

public class ProductDetailFragment extends Fragment {

    DatabaseHelper sqLiteDatabase;
    TextView txtTitle;
    TextView txt_des;
    TextView txt_price;
    TextView txtQty;
    ImageView img_product;
    ImageView img_add;
    ImageView img_minus;
    Button btnBuy;
    String title;
    String id;
    String description;
    int price;
    String img;
    String catid;
    int qty = 1;
    int initialPrice = 0;
    int update = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_detail_fragment, container, false);

        txtTitle = root.findViewById(R.id.txt_title);
        txt_des = root.findViewById(R.id.txt_des);
        txt_price = root.findViewById(R.id.txt_price);
        img_product = root.findViewById(R.id.img_product);
        img_add = root.findViewById(R.id.img_add);
        img_minus = root.findViewById(R.id.img_minus);
        txtQty = root.findViewById(R.id.txt_qty);
        btnBuy = root.findViewById(R.id.btn_buy);

        if (getArguments() != null) {
            id = getArguments().getString("id");
            catid = getArguments().getString("catid");
            title = getArguments().getString("title");
            description = getArguments().getString("des");
            price = Integer.parseInt(getArguments().getString("price"));
            img = getArguments().getString("image");
            qty = getArguments().getInt("qty");
            update = getArguments().getInt("update");

            Glide.with(this).load(img).into(img_product);
            txtTitle.setText(title);
            txt_des.setText(description);
            txt_price.setText("Price. Rs "+price);
            txtQty.setText(""+qty);
            initialPrice = price;
        }

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty < 10){
                    qty = qty+1;
                    price = price + initialPrice;
                    txt_price.setText("Price. Rs "+price);
                    txtQty.setText(""+qty);
                }
            }
        });

        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty > 1){
                    qty = qty-1;
                    price -= initialPrice;
                    txt_price.setText("Price. Rs "+price);
                    txtQty.setText(""+qty);
                }
            }
        });

        try {
            sqLiteDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (update!=0){
            btnBuy.setText("UPDATE");
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUpdated;
                    String mPrice = String.valueOf(price);
                    CartModel cart = new CartModel(id,mPrice, txtQty.getText().toString());
                    isUpdated = sqLiteDatabase.updateCart(cart);
                    if (isUpdated){
                        setupHomeFragment(new CartFragment());
                    }else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            btnBuy.setText("BUY NOW");
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mPrice = String.valueOf(price);
                    CartModel cart = new CartModel(id,catid,txtTitle.getText().toString(),mPrice, txtQty.getText().toString(),img, txt_des.getText().toString());
                    sqLiteDatabase.addToCart(cart);
                }
            });
        }




        return root;
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
