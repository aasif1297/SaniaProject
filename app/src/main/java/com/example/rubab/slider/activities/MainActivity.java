package com.example.rubab.slider.activities;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.CategoriesModel;
import com.example.rubab.slider.R;
import com.example.rubab.slider.adapters.ItemsAdapter;
import com.example.rubab.slider.adapters.SliderAdapterExample;
import com.example.rubab.slider.models.ItemsModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    SliderAdapterExample adapter;

    RecyclerView recyclerView;
    ItemsAdapter itadapter;

    DatabaseHelper sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sliderView = findViewById(R.id.imageSlider);
        adapter =new  SliderAdapterExample(this);


        try {
            sqLiteDatabase = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ItemsModel> productsList =  sqLiteDatabase.fetchProducts();

        recyclerView= findViewById(R.id.my_recycler_view);
        itadapter=new ItemsAdapter(this,productsList);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(itadapter);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM) ;//set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);

        sliderView.startAutoCycle();
    }


}
