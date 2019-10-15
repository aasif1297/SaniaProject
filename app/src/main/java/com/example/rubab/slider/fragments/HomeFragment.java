package com.example.rubab.slider.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rubab.slider.R;
import com.example.rubab.slider.adapters.CartAdapter;
import com.example.rubab.slider.adapters.CategoryAdapter;
import com.example.rubab.slider.adapters.SliderAdapterExample;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.CategoriesModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment {

    SliderView sliderView;
    SliderAdapterExample adapter;

    RecyclerView recyclerView;
    CategoryAdapter itadapter;

    DatabaseHelper sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = root.findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(getContext());


        try {
            sqLiteDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CategoriesModel> categoryList =  sqLiteDatabase.fetchCategories();

        recyclerView= root.findViewById(R.id.my_recycler_view);
        itadapter=new CategoryAdapter(getContext(),categoryList);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(itadapter);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM) ;//set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);

        sliderView.startAutoCycle();

        return root;
    }
}
