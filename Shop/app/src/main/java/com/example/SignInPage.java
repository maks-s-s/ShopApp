package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shop.R;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Chat.ToChat;
import database.ApiClient;
import database.RDBUser;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);
        String currentUserName = sharedPreferences.getString("currentUserName", "Undefined");
        String currentUserEmail = sharedPreferences.getString("currentUserEmail", "Undefined");
        String currentUserPermission = sharedPreferences.getString("currentUserPermission", "user");

        if (isRemembered) {
            Intent intent = new Intent(this, MainActivityJava.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        UserApi userApi = ApiClient.getUserApi();

        SharedPreferences.Editor editor = sharedPreferences.edit();


        Button sign_in = this.findViewById(R.id.sign_in);
        EditText password = this.findViewById(R.id.sign_in_password_textEdit);
        EditText email = this.findViewById(R.id.sign_in_email_textEdit);

        ImageButton sign_in_password_hide = this.findViewById(R.id.sign_in_password_hide);
        sign_in_password_hide.setTag(R.drawable.eye);
        sign_in_password_hide.setOnClickListener(v -> {
            if (sign_in_password_hide.getTag().equals(R.drawable.hidden)) {
                sign_in_password_hide.setImageResource(R.drawable.eye);
                sign_in_password_hide.setTag(R.drawable.eye);
            } else {
                sign_in_password_hide.setImageResource(R.drawable.hidden);
                sign_in_password_hide.setTag(R.drawable.hidden);
            }
        });

        Button create_account = this.findViewById(R.id.create_account);
        create_account.setOnClickListener(v -> {
            Intent intent = new Intent(SignInPage.this, SignUpPage.class);
            startActivity(intent);
        });

        Button forgot_password = this.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(v -> {
            Intent intent = new Intent(SignInPage.this, ChangePasswordPage.class);
            startActivity(intent);
        });


        CheckBox rememberMe = this.findViewById(R.id.remember_me);
        sign_in.setOnClickListener(v -> {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                Call<String> getpass = userApi.getPasswordByEmail(enteredEmail);

                getpass.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String storedPassword = response.body();
                            Log.d("API", "Password: " + storedPassword);

                            if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                                Call<String> currentUserName = userApi.getUserNameByEmail(enteredEmail);
                                currentUserName.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful()) {
                                            Call<String> getPermission = userApi.getPermissionByEmail(enteredEmail);
                                            getPermission.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if (response.isSuccessful()) {
                                                        editor.putString("currentUserPermission", response.body());
                                                        editor.apply();
                                                        Log.d("API", "GetPermission Successful" + response.body());
                                                    }
                                                    else {
                                                        Log.d("API", "GetPermission UnSuccessful");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Log.d("API", "GetPermission Failure");
                                                }
                                            });

                                            String storedName = response.body();
                                            Log.d("API", "Name: " + storedName);
                                            editor.putString("currentUserName", storedName);
                                            editor.putString("currentUserEmail", enteredEmail);
                                            editor.apply();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.e("API", "Failure: " + t.getMessage());
                                    }
                                });

                                editor.putBoolean("isLogged", true);
                                editor.apply();
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(SignInPage.this, MainActivityJava.class);
                                    startActivity(intent);
                                });
                                if (rememberMe.isChecked()) {
                                    editor.putBoolean("isRemembered", true);
                                }
                            } else {
                                runOnUiThread(() -> {
                                    Toast.makeText(SignInPage.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                                });
                            }

                        } else {
                            Log.e("API", "Error: " + response.code());
                            runOnUiThread(() -> {
                                Toast.makeText(SignInPage.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("API", "Failure: " + t.getMessage());
                    }
                });
        });

    }
}
