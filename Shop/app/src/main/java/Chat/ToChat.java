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
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        ImageButton toMainPage = this.findViewById(R.id.toMain);
        toMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(ToChat.this, MainActivityJava.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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


        adapter = new recyclerViewChatAdapter(this, messageList, currentUserEmail, recyclerView);
        recyclerView.setAdapter(adapter);
        scrollToTheEnd();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInputField.getText().toString().trim();

                if (!messageText.isEmpty()) {

                    nameColor = getHelper(userApi.getNameColorByEmail(currentUserEmail.trim()));
                    tag = getHelper(userApi.getTagByEmail(currentUserEmail.trim()));
                    tagColor = getHelper(userApi.getTagColorByEmail(currentUserEmail.trim()));

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                    String currentTime = sdf.format(new Date());

                    message newMessage = new message(currentTime, currentUserName, messageText, currentUserEmail);

                    if (nameColor == null) {
                        newMessage.setNameColor("#1E1E1E");
                        nameColor = "#1E1E1E";
                    } else {
                        newMessage.setNameColor(nameColor);
                    }

                    if (tagColor == null) {
                        newMessage.setTagColor("#1E1E1E");
                        tagColor = "#1E1E1E";
                    } else {
                        newMessage.setTagColor(tagColor);
                    }

                    newMessage.setTag(tag);
                    newMessage.setSendersAccess(currentUserPermission);
                    newMessage.setId(getHelper(userApi.getIdForNextMessage()) + 1);

                    messageList.add(newMessage);
                    setHelper(userApi.addNewMessage(newMessage));

                    adapter.notifyItemInserted(messageList.size() -1);
                    recyclerView.scrollToPosition(messageList.size() - 1);
                    messageInputField.setText("");
                }
            }
        });
    }

//                  Old updater
//
//    public void updateMessageList (List<message> MainMessageList) {
//        getAllMessages().thenAccept(messages -> {
//            runOnUiThread(() -> {
//                if (!messages.isEmpty() && !MainMessageList.isEmpty()) {
//                    for (int i = 0; i < messages.size(); i++) {
//                        if (!messages.get(i).equals(MainMessageList.get(i))) {
//                            MainMessageList.set(i, messages.get(i));
//                            adapter.notifyItemChanged(i);
//                        }
//                    }
//                } else {
//                    MainMessageList.addAll(messages);
//                }
//            });
//        }).exceptionally(e -> {
//            runOnUiThread(() -> {
//                Log.e("API", "Failed to get allMessages: " + e.getMessage());
//            });
//            return null;
//        });
//    }

    public void newUpdater (List<message> MainMessageList) {
        List<message> messages = getHelper(userApi.getAllMessages());
        runOnUiThread(() -> {
            if (!messages.isEmpty() && !MainMessageList.isEmpty()) {
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).isPinned()) {
                        message message = messages.get(i);
                        FrameLayout pinLayout = findViewById(R.id.pinLayout);
                        TextView pinTag = findViewById(R.id.pinTag);
                        TextView pinName = findViewById(R.id.pinName);
                        TextView pinMessage = findViewById(R.id.pinMessage);

                        pinTag.setText(message.getTag());
                        pinTag.setTextColor(Color.parseColor(message.getTagColor()));
                        pinName.setText(message.getName());
                        pinName.setTextColor(Color.parseColor(message.getNameColor()));
                        pinMessage.setText(message.getText());
                        if (message.getTextColor() != null) {
                            pinMessage.setTextColor(Color.parseColor(message.getTextColor()));
                        }
                        else {
                            pinMessage.setTextColor(Color.parseColor("#1E1E1E"));

                        }

                        pinLayout.setVisibility(View.VISIBLE);
                    }

                    if (messages.get(i).isWasChanged()) {

                        if (!messages.get(i).getChangerEmail().isEmpty() && messages.get(i).getChangerEmail() != null) {

                            if (!messages.get(i).getChangerEmail().equals(currentUserEmail)) {
                                MainMessageList.set(i, messages.get(i));
                                adapter.notifyItemChanged(i);
                                setHelper(userApi.setUnChanged(messages.get(i).getId()));
                            }
                        }
                    }
                }
            } else {
                MainMessageList.addAll(messages);
            }
        });
    }


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

    public void scrollToTheEnd() {
        List<message> messages = getHelper(userApi.getAllMessages());
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
    }

}
