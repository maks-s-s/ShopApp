package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

import database.ApiClient;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToBasket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket);

        UserApi userApi = ApiClient.getUserApi();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button logOut = this.findViewById(R.id.logout_button);
        logOut.setOnClickListener(v -> {
            editor.putBoolean("isRemembered", false);
            editor.putBoolean("isLogged", false);
            editor.putInt("rememberedId", -1);
            editor.putString("currentUserName","Undefined");
            editor.putString("currentUserEmail","Undefined");
            editor.putString("currentUserPermission","UNDEFINED");
            editor.apply();

            Intent intent = new Intent(ToBasket.this, SignInPage.class);
            startActivity(intent);
        });
        Button CleanChat = this.findViewById(R.id.clean_button);
        CleanChat.setOnClickListener(v -> {
            userApi.clearChat().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.i("API", "succesful - cleanChat");
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });

    }
}
