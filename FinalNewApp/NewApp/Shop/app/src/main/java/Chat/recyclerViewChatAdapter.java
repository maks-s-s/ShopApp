package Chat;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.ApiClient;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recyclerViewChatAdapter extends  RecyclerView.Adapter<recyclerViewChatAdapter.MessageViewHolder> {

    private List<message> messageList;
    private Context context;
    private String currentUserEmail;

    FrameLayout editingLayout;
    TextView editingName;
    TextView editingText;
    ImageButton editingCancel;
    ImageButton editButton;
    ImageButton sendButton;
    EditText messageInputField;
    TextView editingTag;
    UserApi userApi = ApiClient.getUserApi();

    public recyclerViewChatAdapter(Context context, List<message> messageList, String currentUserEmail, FrameLayout editingLayout, TextView editingName, TextView editingText,
                                   ImageButton editingCancel, ImageButton editButton, ImageButton sendButton, EditText messageInputField, TextView editingTag) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserEmail = currentUserEmail;
        this.editingLayout = editingLayout;
        this.editingName = editingName;
        this.editingText = editingText;
        this.editingCancel = editingCancel;
        this.editButton = editButton;
        this.sendButton = sendButton;
        this.messageInputField = messageInputField;
        this.editingTag = editingTag;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getSendersEmail().equals(currentUserEmail) ? 1 : 0;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.mymessageexample, parent, false);
        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.othersmessageexample, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        message message = messageList.get(position);
        holder.nameTextView.setTextColor(Color.parseColor(message.getNameColor()));
        holder.nameTextView.setText(message.getName());
        holder.textTextView.setText(message.getText());

        if (message.getTextColor() == null) {
            holder.textTextView.setTextColor(Color.parseColor("#1E1E1E"));
        } else {
            holder.textTextView.setTextColor(Color.parseColor(message.getTextColor()));
        }
        holder.timeTextView.setText(message.getTime());
        holder.TagTextView.setText(message.getTag());
        holder.TagTextView.setTextColor(Color.parseColor(message.getTagColor()));

        if (holder.MessageClicableLayout != null) {
        holder.MessageClicableLayout.setOnClickListener(v -> {
                List<MessageAction> actions = new ArrayList<>();

                View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.recyclerviewforpopupmenu, null);
                PopupWindow popupWindow = new PopupWindow(popupView, 424, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            int[] location = new int[2];
            v.getLocationOnScreen(location);
            if (Objects.equals(currentUserEmail, message.getSendersEmail())) {
                popupWindow.showAsDropDown(v, 0, 0, Gravity.TOP | Gravity.END );
            }
            else {
                popupWindow.showAsDropDown(v, 0, 0, Gravity.TOP | Gravity.START );
            }

            if (Objects.equals(message.getSendersAccess(), "user")) {
                if (Objects.equals(currentUserEmail, message.getSendersEmail())) {
                    MessageAction copy = new MessageAction("Скопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                    MessageAction change = new MessageAction("Изменить", true, v1 -> {
                        sendButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        editingName.setText(message.getName());
                        editingName.setTextColor(Color.parseColor(message.getNameColor()));
                        editingText.setText(message.getText());
                        editingTag.setText(message.getTag());
                        editingTag.setTextColor(Color.parseColor(message.getTagColor()));

                        editButton.setOnClickListener(e -> {
                            String newText = String.valueOf(messageInputField.getText());
                            if (!newText.isEmpty()) {
                                edit(message, newText);

                                editButton.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                editingLayout.setVisibility(View.GONE);

                                messageInputField.setText("");


                                TextView isEdited = v.findViewById(R.id.isEdited);
                                isEdited.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });

                        editingCancel.setOnClickListener(c -> {
                            editButton.setVisibility(View.GONE);
                            sendButton.setVisibility(View.VISIBLE);
                            editingLayout.setVisibility(View.GONE);
                        });

                        editingLayout.setVisibility(View.VISIBLE);
                        popupWindow.dismiss();
                    });
                    actions.add(change);

                    MessageAction delete = new MessageAction("Удалить", true, v1 -> {
                        delete(message);
                        popupWindow.dismiss();
                    });
                    actions.add(delete);

                } else {

                    MessageAction copy = new MessageAction("Cкопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                }
            } else if (Objects.equals(message.getSendersAccess(), "moderator")) {
                if (Objects.equals(currentUserEmail, message.getSendersEmail())) {

                    MessageAction copy = new MessageAction("Cкопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                    MessageAction change = new MessageAction("Изменить", true, v1 -> {
                        sendButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        editingName.setText(message.getName());
                        editingName.setTextColor(Color.parseColor(message.getNameColor()));
                        editingText.setText(message.getText());
                        editingTag.setText(message.getTag());
                        editingTag.setTextColor(Color.parseColor(message.getTagColor()));

                        editButton.setOnClickListener(e -> {
                            String newText = String.valueOf(messageInputField.getText());
                            if (!newText.isEmpty()) {
                                edit(message, newText);


                                editButton.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                editingLayout.setVisibility(View.GONE);

                                messageInputField.setText("");

                                TextView isEdited = v.findViewById(R.id.isEdited);
                                isEdited.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });

                        editingCancel.setOnClickListener(c -> {
                            editButton.setVisibility(View.GONE);
                            sendButton.setVisibility(View.VISIBLE);
                            editingLayout.setVisibility(View.GONE);
                        });

                        editingLayout.setVisibility(View.VISIBLE);
                        popupWindow.dismiss();
                    });
                    actions.add(change);

                    MessageAction delete = new MessageAction("Удалить", true, v1 -> {
                        delete(message);
                        popupWindow.dismiss();
                    });
                    actions.add(delete);

                } else {

                    MessageAction copy = new MessageAction("Cкопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                    MessageAction change = new MessageAction("Изменить", true, v1 -> {
                        sendButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        editingName.setText(message.getName());
                        editingName.setTextColor(Color.parseColor(message.getNameColor()));
                        editingText.setText(message.getText());
                        editingTag.setText(message.getTag());
                        editingTag.setTextColor(Color.parseColor(message.getTagColor()));

                        editButton.setOnClickListener(e -> {
                            String newText = String.valueOf(messageInputField.getText());
                            if (!newText.isEmpty()) {
                                edit(message, newText);


                                editButton.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                editingLayout.setVisibility(View.GONE);

                                messageInputField.setText("");

                                TextView isEdited = v.findViewById(R.id.isEdited);
                                isEdited.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });

                        editingCancel.setOnClickListener(c -> {
                            editButton.setVisibility(View.GONE);
                            sendButton.setVisibility(View.VISIBLE);
                            editingLayout.setVisibility(View.GONE);
                        });

                        editingLayout.setVisibility(View.VISIBLE);
                        popupWindow.dismiss();
                    });
                    actions.add(change);


                    MessageAction delete = new MessageAction("Удалить", true, v1 -> {
                        delete(message);
                        popupWindow.dismiss();
                    });
                    actions.add(delete);
                }
            } else if (Objects.equals(message.getSendersAccess(), "dev")) {
                if (Objects.equals(currentUserEmail, message.getSendersEmail())) {

                    MessageAction copy = new MessageAction("Cкопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                    MessageAction change = new MessageAction("Изменить", true, v1 -> {
                        sendButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        editingName.setText(message.getName());
                        editingName.setTextColor(Color.parseColor(message.getNameColor()));
                        editingText.setText(message.getText());
                        editingTag.setText(message.getTag());
                        editingTag.setTextColor(Color.parseColor(message.getTagColor()));

                        editButton.setOnClickListener(e -> {
                            String newText = String.valueOf(messageInputField.getText());
                            if (!newText.isEmpty()) {
                                edit(message, newText);


                                editButton.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                editingLayout.setVisibility(View.GONE);

                                messageInputField.setText("");

                                TextView isEdited = v.findViewById(R.id.isEdited);
                                isEdited.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });

                        editingCancel.setOnClickListener(c -> {
                            editButton.setVisibility(View.GONE);
                            sendButton.setVisibility(View.VISIBLE);
                            editingLayout.setVisibility(View.GONE);
                        });

                        editingLayout.setVisibility(View.VISIBLE);
                        popupWindow.dismiss();
                    });
                    actions.add(change);

                    MessageAction delete = new MessageAction("Удалить", true, v1 -> {
                        delete(message);
                        popupWindow.dismiss();
                    });
                    actions.add(delete);

                } else {
                    MessageAction copy = new MessageAction("Cкопировать", true, v1 -> {
                        copy(message.getText());
                        popupWindow.dismiss();
                    });
                    actions.add(copy);

                    MessageAction change = new MessageAction("Изменить", true, v1 -> {
                        sendButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        editingName.setText(message.getName());
                        editingName.setTextColor(Color.parseColor(message.getNameColor()));
                        editingText.setText(message.getText());
                        editingTag.setText(message.getTag());
                        editingTag.setTextColor(Color.parseColor(message.getTagColor()));

                        editButton.setOnClickListener(e -> {
                            String newText = String.valueOf(messageInputField.getText());
                            if (!newText.isEmpty()) {
                                edit(message, newText);


                                editButton.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                editingLayout.setVisibility(View.GONE);

                                messageInputField.setText("");

                                TextView isEdited = v.findViewById(R.id.isEdited);
                                isEdited.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });

                        editingCancel.setOnClickListener(c -> {
                            editButton.setVisibility(View.GONE);
                            sendButton.setVisibility(View.VISIBLE);
                            editingLayout.setVisibility(View.GONE);
                        });

                        editingLayout.setVisibility(View.VISIBLE);
                        popupWindow.dismiss();
                    });
                    actions.add(change);

                    MessageAction delete = new MessageAction("Удалить", true, v1 -> {
                        delete(message);
                        popupWindow.dismiss();
                    });
                    actions.add(delete);


                }
            }

            RecyclerView recyclerView = popupView.findViewById(R.id.recyclerViewActions);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            recyclerView.setAdapter(new MessageActionAdapter(actions));});

        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView textTextView;
        TextView timeTextView;
        TextView TagTextView;
        LinearLayout MessageClicableLayout;
        TextView isEdited;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.Name);
            textTextView = itemView.findViewById(R.id.message);
            timeTextView = itemView.findViewById(R.id.Time);
            TagTextView = itemView.findViewById(R.id.Tag);
            MessageClicableLayout = itemView.findViewById(R.id.MessageClicableLayout);
            isEdited = itemView.findViewById(R.id.isEdited);

        }
    }

    private void showCustomToast(String message) {
        // Inflate the custom toast layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.customcopytoast, null);

        // Set the text
        TextView toastText = layout.findViewById(R.id.toastText);
        toastText.setText(message);

        // Create and display the custom toast
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void copy (String text) {
        ClipboardManager clipboard = ContextCompat.getSystemService(context, ClipboardManager.class);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("Copied message text", text);
            clipboard.setPrimaryClip(clip);
            showCustomToast("Скопировано");
        }
    }

    public void delete (message message) {
        message.setText("Massage was deleted...");
        message.setTextColor("#DF2226");
        notifyDataSetChanged();
        userApi.deleteMessage(message.getId(), currentUserEmail).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("API", "successful deleting");
                }
                else {
                    Log.e("API", "Unsuccessful deleting" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Failure-Deleting: " + t.getMessage());
            }
        });
    }

    public void edit (message message, String newText) {
        message.setText(newText);
        notifyDataSetChanged();

        userApi.editMesage(message.getId(), currentUserEmail, newText).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("API", "successful editing");
                }
                else {
                    Log.e("API", "Unsuccessful editing" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Failure-Editing: " + t.getMessage());
            }
        });
    }

}
