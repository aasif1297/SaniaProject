package com.example.rubab.slider.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubab.slider.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderConfirmedFragment extends Fragment {

    private TextView txtOrder;
    private String orderNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_confirmed, container, false);

        txtOrder = root.findViewById(R.id.txt_order_number);

        if (getArguments() != null){
            orderNumber = getArguments().getString("id");
            txtOrder.setText("Your Order Number is #"+orderNumber);
        }

        return root;
    }

}
