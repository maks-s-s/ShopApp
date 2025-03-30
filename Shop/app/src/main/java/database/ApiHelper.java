package database;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ApiHelper {
    static <T> T getСallHandler (Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d("API", "Успешно: " + response.body().toString());
                return response.body();
            } else {
                Log.e("API", "Ошибка: " + response.errorBody());
            }
        } catch (IOException e) {
            Log.e("API", "Ошибка запроса", e);
        }
        return null;
    }

    default <T> T getHelper (Call<T> call) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit(() -> getСallHandler(call));

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }

    default <T> void setHelper(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "Успешно: " + response.body());
                } else {
                    Log.e("API", "Ошибка: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e("API", "Ошибка запроса", t);
            }
        });
    }
}
