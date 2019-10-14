package com.example.rubab.slider.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rubab.slider.R;
import com.example.rubab.slider.fragments.ProductDetailFragment;
import com.example.rubab.slider.models.ItemsModel;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private Context context;
    private List<ItemsModel> productList;
    public  ItemsAdapter(Context context,List<ItemsModel> productList){
        this.context = context;
        this.productList = productList;
    }



    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, null);
        return new ItemsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int position) {

        final ItemsModel item = productList.get(position);

        itemsViewHolder.txt_des.setText(item.getDescription());
        itemsViewHolder.txt_price.setText(item.getPrice());
        itemsViewHolder.txt_title.setText(item.getTitle());
        Glide.with(itemsViewHolder.itemView)
                .load(item.getImageUrl())
                .into(itemsViewHolder.imageView);

        itemsViewHolder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHomeFragment(new ProductDetailFragment(), item.getId(), item.getCatid(), item.getTitle(), item.getDescription(), item.getPrice(), item.getImageUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView imageView;
        TextView txt_price;
        TextView txt_title;
        TextView txt_des;
        Button btn_buy;


        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_img);
            txt_des=itemView.findViewById(R.id.txt_des);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_title=itemView.findViewById(R.id.txt_title);
            btn_buy=itemView.findViewById(R.id.btn_buy);
            this.itemView= itemView;
        }
    }

    private void setupHomeFragment(Fragment fragment, String id, String catid, String title, String des, String price, String image) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack();
        Fragment ldf = fragment;
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("catid", catid);
        args.putString("title", title);
        args.putString("des", des);
        args.putString("price", price);
        args.putString("image", image);
        ldf.setArguments(args);
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }
}


