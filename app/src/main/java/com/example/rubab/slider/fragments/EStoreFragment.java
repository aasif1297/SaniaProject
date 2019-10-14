package com.example.rubab.slider.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubab.slider.R;
import com.example.rubab.slider.adapters.ItemsAdapter;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.ItemsModel;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EStoreFragment extends Fragment {

    RecyclerView recyclerView;
    ItemsAdapter it_product;

    DatabaseHelper sqLiteDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estore, container, false);

        try {
            sqLiteDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ItemsModel> productsList =  sqLiteDatabase.fetchProducts();

        recyclerView= root.findViewById(R.id.estore_recycler);
        it_product = new ItemsAdapter(getContext(),productsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(it_product);

        return root ;
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_estore, container, false);
//
//        return root;
//    }
}
