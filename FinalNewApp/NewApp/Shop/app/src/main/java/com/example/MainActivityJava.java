package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shop.R;

import java.util.ArrayList;
import java.util.List;

import Chat.ToChat;


public class MainActivityJava extends AppCompatActivity {
    private List<Button> buttonList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();


    private ViewPager2 viewPager;
    private LinearLayout indicatorLayout;
    private List<Integer> imageList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentPage = 0;


    List<Items_model> items =  new ArrayList<>();
    GridLayout containerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);

        if (!isLogged) {
            Intent intent = new Intent(this, SignInPage.class);
            startActivity(intent);
            finish();
            Toast.makeText(MainActivityJava.this, "Залогинтесь", Toast.LENGTH_SHORT).show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Начало Выделение нажатой кнопки
        buttonList.add(findViewById(R.id.button_all));
        buttonList.add(findViewById(R.id.button_hits));
        buttonList.add(findViewById(R.id.button_for_you));
        buttonList.add(findViewById(R.id.button_clothes_for_man));
        buttonList.add(findViewById(R.id.button_clothes_for_women));
        buttonList.add(findViewById(R.id.button_clothes_for_children));

        imageViewList.add(findViewById(R.id.selecteble_all));
        imageViewList.add(findViewById(R.id.selecteble_hits));
        imageViewList.add(findViewById(R.id.selecteble_for_you));
        imageViewList.add(findViewById(R.id.selecteble_clothes_for_man));
        imageViewList.add(findViewById(R.id.selecteble_clothes_for_women));
        imageViewList.add(findViewById(R.id.selecteble_clothes_for_children));

        for (int i = 0; i < buttonList.size(); i++)
        {
            int index = i;
            buttonList.get(index).setOnClickListener(v -> updateVisibility(imageViewList.get(index)));
        }
        // Конец

        // Начало Добаление точек

        viewPager = findViewById(R.id.viewpager_for_news);
        indicatorLayout = findViewById(R.id.indicatorLayout_for_news);

        imageList.add(R.drawable.news_card1);
        imageList.add(R.drawable.news_card2);
        imageList.add(R.drawable.news_card3);
        imageList.add(R.drawable.news_card4);

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, imageList);
        viewPager.setAdapter(adapter);

        setupIndicators(imageList.size());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }
        });

        startAutoScroll();
        // Конец

        // Начало добавления товаров
        containerLayout = findViewById(R.id.Main_Container_for_products);

        items.add(new Items_model("Iphone 16 128 gb memory 100 megapx camera", 12, "Best phone for you!!!", false, false, false, R.drawable.iphone, true, true, false));
        items.add(new Items_model("manTest", 13, "Huinia2", true, false, false, R.drawable.man_test, false, true, false));
        items.add(new Items_model("womanTest", 11, "Huinia3", false, true, false, R.drawable.woman_test, false, false, false));
        items.add(new Items_model("childTest", 10.11, "Huinia4", false, false, true, R.drawable.child_test, true, false, false));
        items.add(new Items_model_with_sale("childTest", 10.11, 9, "Huinia4", false, false, true, R.drawable.child_test, false, false,false));

        Products_ScrollView_Adapter product_adapter = new Products_ScrollView_Adapter(this, containerLayout, items);



        product_adapter.reShow(items);
        // Конец
        // Сорта Начало


        buttonList.get(3).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(3));
            List<Items_model> temp = new ArrayList<>();
        for(int i = 0; i < items.size(); i++) {
            int index = i;
            if (items.get(index).isForMans() == true) {
                temp.add(items.get(index));
            }
        }
        product_adapter.reShow(temp);});

        buttonList.get(0).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(0));
            List<Items_model> temp = new ArrayList<>();
            for(int i = 0; i < items.size(); i++) {
                int index = i;
                if (true) {
                    temp.add(items.get(index));
                }
            }
            product_adapter.reShow(temp);});

        buttonList.get(1).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(1));
            List<Items_model> temp = new ArrayList<>();
            for(int i = 0; i < items.size(); i++) {
                int index = i;
                if (items.get(index).isHits() == true) {
                    temp.add(items.get(index));
                }
            }
            product_adapter.reShow(temp);});
        buttonList.get(2).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(2));
            List<Items_model> temp = new ArrayList<>();
            for(int i = 0; i < items.size(); i++) {
                int index = i;
                if (items.get(index).isForYou() == true) {
                    temp.add(items.get(index));
                }
            }
            product_adapter.reShow(temp);});
        buttonList.get(4).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(4));
            List<Items_model> temp = new ArrayList<>();
            for(int i = 0; i < items.size(); i++) {
                int index = i;
                if (items.get(index).isForWomens() == true) {
                    temp.add(items.get(index));
                }
            }
            product_adapter.reShow(temp);});
        buttonList.get(5).setOnClickListener(v -> {
            updateVisibility(imageViewList.get(5));
            List<Items_model> temp = new ArrayList<>();
            for(int i = 0; i < items.size(); i++) {
                int index = i;
                if (items.get(index).isForChilds() == true) {
                    temp.add(items.get(index));
                }
            }
            product_adapter.reShow(temp);});

        ImageButton toBasket = this.findViewById(R.id.toBasket);
        toBasket.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityJava.this, ToBasket.class);
            startActivity(intent);
        });

        ImageButton toChat = this.findViewById(R.id.toChat);
        toChat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityJava.this, ToChat.class);
            startActivity(intent);

        });

    }
    private void updateVisibility(ImageView selectedImage) {
        for(int i = 0; i < imageViewList.size(); i++){
            int index = i;
            imageViewList.get(index).setVisibility(View.GONE);
        }

        selectedImage.setVisibility(View.VISIBLE);
    }

    private void setupIndicators(int count) {
        for (int i = 0; i < count; i++) {
            View indicator = new View(this);
            indicator.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
            indicator.setBackgroundResource(R.drawable.inactivepoint);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            indicatorLayout.addView(indicator);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
            View indicator = indicatorLayout.getChildAt(i);
            if (i == position) {
                indicator.setBackgroundResource(R.drawable.activepoint);
            } else {
                indicator.setBackgroundResource(R.drawable.inactivepoint);
            }
        }
    }

    private void startAutoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }
}

