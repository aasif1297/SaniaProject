package com.example.rubab.slider.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rubab.slider.fragments.EStoreFragment;
import com.example.rubab.slider.fragments.ProductDetailFragment;
import com.example.rubab.slider.models.CategoriesModel;
import com.example.rubab.slider.R;
import com.example.rubab.slider.models.ItemsModel;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private Context context;
    private List<ItemsModel> productList;
    public  ItemsAdapter(Context context,List<ItemsModel> productList)
    {
        this.context = context;
        this.productList = productList;
    }



    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_categories, null);
        return new ItemsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int position) {

        ItemsModel item = productList.get(position);

        itemsViewHolder.tv.setText(item.getDescription());
        itemsViewHolder.txt_price.setText(item.getPrice());
        itemsViewHolder.txt_title.setText(item.getTitle());
        Glide.with(itemsViewHolder.itemView)
                .load(item.getImageUrl())
                .into(itemsViewHolder.imageView);

        itemsViewHolder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHomeFragment(new ProductDetailFragment());
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
        TextView tv;
        TextView txt_price;
        TextView txt_title;
        Button btn_buy;


        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_img);
            tv=itemView.findViewById(R.id.txt_des);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_title=itemView.findViewById(R.id.txt_title);
            btn_buy=itemView.findViewById(R.id.btn_buy);
            this.itemView= itemView;
        }
    }

    private void setupHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }
}


