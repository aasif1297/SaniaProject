package com.example.rubab.slider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rubab.slider.R;
import com.example.rubab.slider.database.DatabaseHelper;
import com.example.rubab.slider.models.SliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.IOException;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    DatabaseHelper dbHelper;

    public SliderAdapterExample(Context context) {
        this.context = context;
        try {
            dbHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        List<SliderModel> list = dbHelper.fetchSlider();


        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(list.get(0).getImageUrl())
                        .into(viewHolder.imageViewBackground);
                viewHolder.textViewDescription.setText(list.get(0).getTitle());
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(list.get(1).getImageUrl())
                        .into(viewHolder.imageViewBackground);
                viewHolder.textViewDescription.setText(list.get(1).getTitle());

                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(list.get(2).getImageUrl())
                        .into(viewHolder.imageViewBackground);
                viewHolder.textViewDescription.setText(list.get(2).getTitle());
                break;
            default:
                Glide.with(viewHolder.itemView)
                        .load(list.get(3).getImageUrl())
                        .into(viewHolder.imageViewBackground);
                viewHolder.textViewDescription.setText(list.get(3).getTitle());
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
