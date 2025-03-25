package database;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public interface ApiHelper {
    static <T> T callHandler (Call<T> call) {
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

    default <T> T caller (Call<T> call) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit(() -> callHandler(call));

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }
}
