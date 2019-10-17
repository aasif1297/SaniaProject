package com.example.rubab.slider.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rubab.slider.R;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.fragments.ProductDetailFragment;
import com.example.rubab.slider.models.CartModel;

import java.io.IOException;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartModel> productList;
    DatabaseHelper sqLiteDatabase;

    public CartAdapter(Context context, List<CartModel> productList)
    {
        this.context = context;
        this.productList = productList;

        try {
            sqLiteDatabase = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, null);
        return new CartViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        final CartModel item = productList.get(i);
        cartViewHolder.txt_title.setText(item.getProduct_name());
        cartViewHolder.txt_price.setText("Price Rs. "+item.getProduct_price());
        cartViewHolder.txt_qty.setText("Quantity "+item.getQty());

        cartViewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHomeFragment(new ProductDetailFragment(),item.getId(), item.getC_id(), item.getProduct_name(), item.getProduct_detail(), item.getProduct_price(), item.getProduct_image(), item.getQty());
            }
        });

        cartViewHolder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDatabase.deleteProduct(item.getId());
            }
        });

        Glide.with(cartViewHolder.itemView)
                .load(item.getProduct_image())
                .into(cartViewHolder.cat_img);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView cat_img;
        TextView txt_title;
        TextView txt_price;
        TextView txt_qty;
        CardView imgEdit;
        CardView imgDel;



        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView=itemView.findViewById(R.id.item_view);
            cat_img=itemView.findViewById(R.id.img_product);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_qty=itemView.findViewById(R.id.txt_qty);
            imgEdit=itemView.findViewById(R.id.img_edit);
            imgDel=itemView.findViewById(R.id.img_del);
            this.itemView= itemView;
        }
    }

    private void setupHomeFragment(Fragment fragment, String id, String catid, String title, String des, String price, String img, String qty) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        Fragment ldf = fragment;
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("catid", catid);
        args.putString("title", title);
        args.putString("des", des);
        args.putString("price", price);
        args.putString("image", img);
        args.putInt("qty", Integer.parseInt(qty));
        args.putInt("update", 1);
        ldf.setArguments(args);
        transaction.commit();
    }

}
