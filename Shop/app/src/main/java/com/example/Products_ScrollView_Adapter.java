package com.example;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.R;

import java.text.DecimalFormat;

import java.util.List;

public class Products_ScrollView_Adapter {
    private Context context;
    private GridLayout gridLayout;
    private List<Items_model> items;

    public Products_ScrollView_Adapter(Context context, GridLayout gridLayout, List<Items_model> items) {
        this.context = context;
        this.gridLayout = gridLayout;
        this.items = items;
    }

    public void reShow(List<Items_model> items) {
        gridLayout.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            int index = i;
            View itemView = LayoutInflater.from(context).inflate(R.layout.product_item, gridLayout, false);

            ImageButton imageButton = itemView.findViewById(R.id.product_example);
            TextView title = itemView.findViewById(R.id.description_text_view);
            TextView price = itemView.findViewById(R.id.price);
            TextView price_with_sale = itemView.findViewById(R.id.price_with_sale);
            ImageButton make_favorite = itemView.findViewById(R.id.make_favorite);
            if (items.get(index).isFavorite()) {
                make_favorite.setImageResource(R.drawable.heart_filled);
                make_favorite.setTag(R.drawable.heart_filled);
            } else {
                make_favorite.setImageResource(R.drawable.heart_3511);
                make_favorite.setTag(R.drawable.heart_3511);
            }


            price.setText(round_for_adapter(items.get(index).getPrice()));
            title.setText(items.get(index).getName());
            imageButton.setImageResource(items.get(index).getImageId());

            if (items.get(index) instanceof Items_model_with_sale) {
                Items_model_with_sale imws = (Items_model_with_sale) items.get(index);
                //price_with_sale.setText(String.valueOf(imws.getPrice_with_sale()));
                price_with_sale.setText(round_for_adapter(imws.getPrice_with_sale()));
                price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                price_with_sale.setVisibility(View.INVISIBLE);
            }


            imageButton.setOnClickListener(v -> {

                Toast.makeText(context, "Вы выбрали " + items.get(index).getName(), Toast.LENGTH_SHORT).show();
            });
            title.setOnClickListener(v -> {
                Toast.makeText(context, "Вы выбрали " + items.get(index).getName(), Toast.LENGTH_SHORT).show();
            });

            make_favorite.setOnClickListener(v -> {
                if (items.get(index).isFavorite()) {
                    make_favorite.setImageResource(R.drawable.heart_3511);
                    make_favorite.setTag(R.drawable.heart_3511);
                    items.get(index).setFavorite(false);
                } else {
                    make_favorite.setImageResource(R.drawable.heart_filled);
                    make_favorite.setTag(R.drawable.heart_filled);
                    items.get(index).setFavorite(true);
                }
            });

            gridLayout.addView(itemView);
        }
    }

    public static String round_for_adapter(double number) {
        String res;
        DecimalFormat format = new DecimalFormat("0.##");
        res = format.format(number) + " грн";
    return res;
    }
}