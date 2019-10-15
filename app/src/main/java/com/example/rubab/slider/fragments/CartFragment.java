package com.example.rubab.slider.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rubab.slider.R;
import com.example.rubab.slider.adapters.CartAdapter;
import com.example.rubab.slider.adapters.ItemsAdapter;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.CartModel;
import com.example.rubab.slider.models.ItemsModel;

import java.io.IOException;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    String id;

    DatabaseHelper sqLiteDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        try {
            sqLiteDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<CartModel> productsList =  sqLiteDatabase.fetchCartItems();

        recyclerView= root.findViewById(R.id.recyclerView);
        cartAdapter = new CartAdapter(getContext(),productsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);


        return root;
    }
}
