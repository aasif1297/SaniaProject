package com.example.rubab.slider.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rubab.slider.R;
import com.example.rubab.slider.database.DatabaseHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerDetailFragment extends Fragment {

    private int sum = 0, shippingCharge = 0;
    private TextView txtTotal, txtPayable;
    private EditText txtEmail, txtFName, txtLName, txtAddress, txtPhone, txtState, txtCity;
    private Button btnConfirm;
    private DatabaseHelper sqLiteDatabase;
    private boolean isBooked = false;
    private String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        txtTotal = root.findViewById(R.id.txt_total);
        txtPayable = root.findViewById(R.id.txt_payable);
        btnConfirm = root.findViewById(R.id.btn_confirm);
        txtEmail = root.findViewById(R.id.ed_email);
        txtFName = root.findViewById(R.id.ed_f_name);
        txtLName = root.findViewById(R.id.ed_l_name);
        txtAddress = root.findViewById(R.id.ed_address);
        txtPhone = root.findViewById(R.id.ed_cell);
        txtState = root.findViewById(R.id.ed_state);
        txtCity = root.findViewById(R.id.city);

        try {
            sqLiteDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (getArguments() != null) {
            sum = getArguments().getInt("totalsum");
        }

        txtTotal.setText(""+sum);
        txtPayable.setText(""+sum+shippingCharge);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date dNow = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
                uid = ft.format(dNow);
                isBooked = sqLiteDatabase.confirmOrder(uid,txtEmail.getText().toString(), txtFName.getText().toString(), txtLName.getText().toString(), txtPhone.getText().toString(),txtAddress.getText().toString(), txtState.getText().toString(), txtCity.getText().toString(), txtPayable.getText().toString());
                if (isBooked){
                    setupHomeFragment(new OrderConfirmedFragment());
                    String clearCart = "Delete FROM cart";
                    sqLiteDatabase.myDataBase.execSQL(clearCart);
                }
            }
        });

        return root;
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        Fragment ldf = fragment;
        Bundle args = new Bundle();
        args.putString("id", uid);
        ldf.setArguments(args);
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }
}
