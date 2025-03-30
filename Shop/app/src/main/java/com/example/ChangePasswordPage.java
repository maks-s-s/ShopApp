package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import database.ApiClient;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPage extends AppCompatActivity {
    UserApi userApi = ApiClient.getUserApi();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_page);

        Button change_password_back_button = this.findViewById(R.id.change_password_back_button);
        change_password_back_button.setOnClickListener(v -> {
            finish();
        });


        EditText emailET = this.findViewById(R.id.change_password_email_editText);
        EditText oldPasswordET = this.findViewById(R.id.change_password_oldPassword_editText);
        EditText newPasswordET = this.findViewById(R.id.change_password_newPassword_editText);
        EditText confirmNewPasswordET = this.findViewById(R.id.change_password_confirmNewPassword_editText);

        Button changePassword = this.findViewById(R.id.changePassword);

        changePassword.setOnClickListener(v -> {
            String enteredEmail = emailET.getText().toString();
            String enteredOldPassword = oldPasswordET.getText().toString();
            String enteredNewPassword = newPasswordET.getText().toString();
            String enteredconfirmNewPassword = confirmNewPasswordET.getText().toString();

            if (enteredEmail.isEmpty() == false &&
                    enteredOldPassword.isEmpty() == false &&
                    enteredNewPassword.isEmpty() == false &&
                    enteredconfirmNewPassword.isEmpty() == false) {

                changePassword(enteredEmail, enteredOldPassword, enteredNewPassword, enteredconfirmNewPassword);
            }
            else {
                Toast.makeText(ChangePasswordPage.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void changePassword (String enteredEmail, String enteredOldPassword, String enteredNewPassword, String enteredconfirmNewPassword) {
        Call<String> getpassForChangePass = userApi.getPasswordByEmail(enteredEmail);

        getpassForChangePass.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "Successful-GetPass");
                    String pass = response.body();
                    if (pass.equals(enteredOldPassword)) {
                        if (enteredNewPassword.equals(enteredconfirmNewPassword)) {

                            Call<Boolean> change = userApi.changePassword(enteredEmail, enteredNewPassword);
                            change.enqueue(new Callback<Boolean>() {

                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("API", "Successful - change");
                                        runOnUiThread(() -> {
                                            Intent intent = new Intent(ChangePasswordPage.this, SignInPage.class);
                                            startActivity(intent);
                                        });
                                    }
                                    else {
                                        Log.d("API", "Error - change" );
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Log.e("API", "Failure-Reg: " + t.getMessage());
                                }
                            });
                        }
                        else {
                            runOnUiThread(() -> {
                                Toast.makeText(ChangePasswordPage.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                    else {
                        runOnUiThread(() -> {
                            Toast.makeText(ChangePasswordPage.this, "Неверный старый пароль", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                else {
                    Log.d("API", "Error-GetPass");
                    runOnUiThread(() -> {
                        Toast.makeText(ChangePasswordPage.this, "Неверный логин", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("API", "Failure-Reg: " + t.getMessage());
            }
        });

    }
}

/*
       Executor executor2 = Executors.newSingleThreadExecutor();
        executor2.execute(() -> {
            String storedPassword = userDAO.getPasswordByEmail(enteredEmail);
            if (storedPassword != null && storedPassword.equals(enteredOldPassword)) {


                if (enteredNewPassword.equals(enteredconfirmNewPassword)) {

                    userDAO.changePassword(enteredEmail, enteredNewPassword);
                    runOnUiThread(() -> {
                        Toast.makeText(ChangePasswordPage.this, "Успешно" + storedPassword + " -> " + enteredNewPassword, Toast.LENGTH_SHORT).show();
                    });
                }
                else {
                    runOnUiThread(() -> {
                        Toast.makeText(ChangePasswordPage.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    });
                }

            } else {
                runOnUiThread(() -> {
                    Toast.makeText(ChangePasswordPage.this, "Неверный логин или старый пароль", Toast.LENGTH_SHORT).show();
                });
            }
            db.close();
        });
 */