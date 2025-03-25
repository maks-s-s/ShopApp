package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import database.ApiClient;
import database.RDBUser;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPage extends AppCompatActivity {
    UserApi userApi = ApiClient.getUserApi();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        Button login_to_account = this.findViewById(R.id.login_to_account);
        login_to_account.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpPage.this, SignInPage.class);
            startActivity(intent);
        });

        EditText nameEditText = this.findViewById(R.id.signUP_name_EditText);
        EditText emailEditText = this.findViewById(R.id.signUP_emailEditText);
        EditText passwordEditText = this.findViewById(R.id.signUP_passeordEditText);

        Button signUp = this.findViewById(R.id.signUp);
        CheckBox agree = this.findViewById(R.id.agree_checkBox_signUp);

        signUp.setOnClickListener(v -> {
            if (agree.isChecked()) {
            if (nameEditText.getText().toString().isEmpty() == false && emailEditText.getText().toString().isEmpty() == false && passwordEditText.getText().toString().isEmpty() == false) {
                addNewUser(nameEditText.getText().toString().trim(), emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), () -> {
                    Intent intent = new Intent(SignUpPage.this, SignInPage.class);
                    startActivity(intent);
                });
            }
            else {
                Toast.makeText(SignUpPage.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        }
        else {
                Toast.makeText(SignUpPage.this, "Нажмите галочку", Toast.LENGTH_SHORT).show();
        }
    });
    }

    public void addNewUser (String name, String email, String password, Runnable onSuccess) {
        Call<Integer> count = userApi.countUsersWithEmail(email.trim());
        count.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int countf = response.body();

                    if (countf == 0) {
                        RDBUser user = new RDBUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);

                        Call<RDBUser> createUser = userApi.createUser(user);
                        createUser.enqueue(new Callback<RDBUser>() {
                            @Override
                            public void onResponse(Call<RDBUser> call, Response<RDBUser> response) {
                                if (response.isSuccessful()) {
                                    Log.d("API", "Successful-Reg" + response.body().getId());
                                }
                                else {
                                    Log.e("API", "Error-Reg: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<RDBUser> call, Throwable t) {
                                Log.e("API", "Failure-Reg: " + t.getMessage());
                            }
                        });
                        runOnUiThread(onSuccess);
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(SignUpPage.this, "Пользователь с таким email (" + email + ") уже существует", Toast.LENGTH_SHORT).show();
                        });
                    }

                    Log.d("API", "Successful-Count");
                }
                else {
                    runOnUiThread(() -> {
                        Toast.makeText(SignUpPage.this, "Неверный логин", Toast.LENGTH_SHORT).show();
                    });
                    Log.e("API", "Error-Count: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("API", "Failure-Count: " + t.getMessage());
            }
        });
    }
}