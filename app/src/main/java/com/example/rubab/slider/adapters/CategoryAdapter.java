package com.example.rubab.slider.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubab.slider.R;
import com.example.rubab.slider.fragments.EStoreFragment;
import com.example.rubab.slider.models.CategoriesModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoriesModel> productList;
    public  CategoryAdapter(Context context, List<CategoriesModel> productList)
    {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, null);
        return new CategoryViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        final CategoriesModel item = productList.get(i);
        categoryViewHolder.txt_title.setText(item.getTitle());

        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHomeFragment(new EStoreFragment(), item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView cat_img;
        TextView txt_title;



        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView=itemView.findViewById(R.id.item_view);
            cat_img=itemView.findViewById(R.id.cat_img);
            txt_title=itemView.findViewById(R.id.txt_title);
            this.itemView= itemView;
        }
    }

    private void setupHomeFragment(Fragment fragment, String id) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack();
        Fragment ldf = fragment;
        Bundle args = new Bundle();
        args.putString("id", id);
        ldf.setArguments(args);
        fragmentManager.beginTransaction().add(R.id.content_main, fragment).commit();
    }

}
