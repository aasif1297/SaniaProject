package com.example.rubab.slider.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView txtTotal, txtBack;
    private Button btnCheckout;
    List<CartModel> productsList;
    int totalSum = 0;


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

        productsList =  sqLiteDatabase.fetchCartItems();
        totalSum =  sqLiteDatabase.totalSum();

        recyclerView= root.findViewById(R.id.checkout_recycler);
        txtTotal = root.findViewById(R.id.txt_total);
        txtTotal.setText(""+totalSum);
        txtBack = root.findViewById(R.id.txt_back);
        btnCheckout = root.findViewById(R.id.btn_checkout);


        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHomeFragment(new CustomerDetailFragment());
            }
        });
        cartAdapter = new CartAdapter(getContext(),productsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);


        return root;
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        Fragment ldf = fragment;
        Bundle args = new Bundle();
        args.putInt("totalsum", totalSum);
        ldf.setArguments(args);
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }
}
