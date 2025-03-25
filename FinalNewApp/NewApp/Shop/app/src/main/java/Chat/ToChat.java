package Chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MainActivityJava;
import com.example.SignUpPage;
import com.example.ToBasket;
import com.example.shop.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import database.ApiClient;
import database.ApiHelper;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToChat extends AppCompatActivity implements ApiHelper {
    UserApi userApi = ApiClient.getUserApi();
    private RecyclerView recyclerView;
    private recyclerViewChatAdapter adapter;
    public List<message> messageList;
    private EditText messageInputField;
    private ImageButton sendButton;
    private String currentUserName;
    private String currentUserEmail;
    private String currentUserPermission;
    private String nameColor;
    private String tagColor;
    private String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tochat);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentUserName = sharedPreferences.getString("currentUserName", "Undefined");
        currentUserEmail = sharedPreferences.getString("currentUserEmail", "Undefined");
        currentUserPermission = sharedPreferences.getString("currentUserPermission", "user");

        ImageButton toBasket = this.findViewById(R.id.toBasket);
        toBasket.setOnClickListener(v -> {
            Intent intent = new Intent(ToChat.this, ToBasket.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.mainChatRecyclerView);
        messageInputField = findViewById(R.id.MessageEditText);
        sendButton = findViewById(R.id.sendButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                newUpdater(messageList);
            }
        }, 0, 5, TimeUnit.SECONDS);

        FrameLayout editingLayout = findViewById(R.id.editing_framelayout);
        TextView editingName = findViewById(R.id.editing_name);
        TextView editingText = findViewById(R.id.editing_text);
        ImageButton editingCancel = findViewById(R.id.cancel_editting_button);
        ImageButton editButton = findViewById(R.id.editButton);
        ImageButton sendButtonE = findViewById(R.id.sendButton);
        TextView editingTag = findViewById(R.id.editing_tag);


        adapter = new recyclerViewChatAdapter(this, messageList, currentUserEmail, editingLayout, editingName, editingText, editingCancel, editButton, sendButtonE, messageInputField, editingTag);
        recyclerView.setAdapter(adapter);
        scrollToTheEnd();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInputField.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    // Создание нового сообщения
                    Call<String> CallNameColor = userApi.getNameColorByEmail(currentUserEmail.trim());
                    CallNameColor.enqueue(new Callback<String>() {

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && !response.body().isEmpty()) {
                                    nameColor = response.body();
                                    Call<String> CallTag = userApi.getTagByEmail(currentUserEmail.trim());
                                    CallTag.enqueue(new Callback<String>() {

                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful()) {
                                                    tag = response.body();

                                                    Call<String> CallTagColor = userApi.getTagColorByEmail(currentUserEmail.trim());
                                                    CallTagColor.enqueue(new Callback<String>() {

                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                            if (response.isSuccessful()) {
                                                                if (response.body() != null && !response.body().isEmpty()) {
                                                                    tagColor = response.body();

                                                                    // Получение текущего времени
                                                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                                                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                                                                    String currentTime = sdf.format(new Date());


                                                                    message newMessage = new message(currentTime, currentUserName, messageText, currentUserEmail);
                                                                    newMessage.setNameColor(nameColor);
                                                                    newMessage.setTag(tag);
                                                                    newMessage.setTagColor(tagColor);
                                                                    newMessage.setSendersAccess(currentUserPermission);

                                                                    userApi.getIdForNextMessage().enqueue(new Callback<Long>() {
                                                                        @Override
                                                                        public void onResponse(Call<Long> call, Response<Long> response) {
                                                                            if (response.isSuccessful()) {
                                                                                newMessage.setId(response.body() + 1);

                                                                                messageList.add(newMessage);
                                                                                CFaddMessage(newMessage).thenAccept(m -> {
                                                                                    runOnUiThread(() -> {

                                                                                    });
                                                                                }).exceptionally(e -> {
                                                                                    runOnUiThread(() -> {
                                                                                        Log.e("API", "Failed to add message: " + e.getMessage());
                                                                                    });
                                                                                    return null;
                                                                                });

                                                                                // Уведомление адаптера об изменении данных
                                                                                adapter.notifyItemInserted(messageList.size() -1);

                                                                                // Прокрутка к последнему сообщению
                                                                                recyclerView.scrollToPosition(messageList.size() - 1);

                                                                                // Очистка поля ввода
                                                                                messageInputField.setText("");
                                                                            }
                                                                            else {
                                                                                Log.e("API", "Unsuccesful - getIdForNextMessage" + response.code());
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Long> call, Throwable t) {
                                                                            Log.e("API", "Fail - getIdForNextMessage" + t);
                                                                        }
                                                                    });
                                                                }
                                                                else {
                                                                    tagColor = "#1E1E1E";
                                                                }

                                                            }
                                                            else {
                                                                Log.e("API", "Error-GetTagColor: " + response.code());
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t) {
                                                            Log.e("API", "Failure-GetTagColor: " + t.getMessage());
                                                        }
                                                    });
                                            }
                                            else {
                                                Log.e("API", "Error-GetTag: " + response.code());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Log.e("API", "Failure-GetTag: " + t.getMessage());
                                        }
                                    });
                                }
                                else {
                                    nameColor = "#1E1E1E";
                                    Log.e("API", "Error-GetNameColor: " + response.body());
                                }
                            }
                            else {
                                Log.e("API", "Error-GetNameColor: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("API", "Failure-GetNameColor: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    public CompletableFuture<List<message>> getAllMessages() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return userApi.getAllMessages().execute().body();
            } catch (Exception e) {
                throw new RuntimeException("Failed to get all messages ", e);
            }
        });
    }

//    public CompletableFuture<Long> CFgetIdForNextMessage() {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                return userApi.getIdForNextMessage().execute().body();
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to get id for next message ", e);
//            }
//        });
//    }
    public void updateMessageList (List<message> MainMessageList) {
        getAllMessages().thenAccept(messages -> {
            runOnUiThread(() -> {
                if (!messages.isEmpty() && !MainMessageList.isEmpty()) {
                    for (int i = 0; i < messages.size(); i++) {
                        if (!messages.get(i).equals(MainMessageList.get(i))) {
                            MainMessageList.set(i, messages.get(i));
                            adapter.notifyItemChanged(i);
                        }
                    }
                } else {
                    MainMessageList.addAll(messages);
                }
            });
        }).exceptionally(e -> {
            runOnUiThread(() -> {
                Log.e("API", "Failed to get allMessages: " + e.getMessage());
            });
            return null;
        });
    }

    public void newUpdater (List<message> MainMessageList) {
        getAllMessages().thenAccept(messages -> {
            runOnUiThread(() -> {
                if (!messages.isEmpty() && !MainMessageList.isEmpty()) {
                    for (int i = 0; i < messages.size(); i++) {
                        if (messages.get(i).isWasChanged()) {

                            if (!messages.get(i).getChangerEmail().isEmpty() && messages.get(i).getChangerEmail() != null) {

                                if (!messages.get(i).getChangerEmail().equals(currentUserEmail)) {
                                    MainMessageList.set(i, messages.get(i));
                                    adapter.notifyItemChanged(i);
                                    userApi.setUnChanged(messages.get(i).getId()).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });

                                }
                            }
                        }
                    }
                } else {
                    MainMessageList.addAll(messages);
                }
            });
        }).exceptionally(e -> {
            runOnUiThread(() -> {
                Log.e("API", "Failed to get allMessages: " + e.getMessage());
            });
            return null;
        });

        /*
        1. make new fields "wasChanged", "changersEmail"

        updater
        check Lists, if DB list is Empty - don't do anything,
        check how many they messages contain

        loop for each message

        if item wasСhanged == true
        if !changerEmail.isEmpty, !isNull
        if changersEmail != sendersEmail
        update this item
        else
        do not update

        else
        do not update
        else
        do not upfate



        Deleting

        user press delete button

        update this message for him

        request to change data-base item, isDeleted = true, text = "...", was changed = true,  changerEmail = email

        Other user

        updater detect wasChanged variable and update it

        request to data base wasChanged = false, ChangerEmail = ""

        1: Hi

                                    2.Hi

        1: I am going back to 505

                                    2:Ok

        */
    }

    public void scrollToTheEnd() {
        getAllMessages().thenAccept(messages -> {
            runOnUiThread(() -> {
                messageList.clear();
                if (messages != null) {
                    messageList.addAll(messages);
                    adapter.notifyDataSetChanged();

                    if (!messages.isEmpty()) {
                        recyclerView.scrollToPosition(messages.size() - 1);
                    }
                } else {
                    Log.e("Chat", "No messages received from the server.");
                }
            });
        }).exceptionally(e -> {
            runOnUiThread(() -> Log.e("API", "Failed to fetch messages: " + e.getMessage()));
            return null;
        });
    }

    public CompletableFuture<message> CFaddMessage(message message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return userApi.addNewMessage(message).execute().body();
            } catch (Exception e) {
                throw new RuntimeException("Failed to get add message ", e);
            }
        });
    }

}
