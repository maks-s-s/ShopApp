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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import database.ApiClient;
import database.ApiHelper;
import database.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recyclerViewChatAdapter extends  RecyclerView.Adapter<recyclerViewChatAdapter.MessageViewHolder> implements ApiHelper {

    private List<message> messageList;
    private Context context;
    private String currentUserEmail;
    private String currentUserAccess;

    FrameLayout editingLayout;
    TextView editingName;
    TextView editingText;
    ImageButton editingCancel;
    ImageButton editButton;
    ImageButton sendButton;
    EditText messageInputField;
    TextView editingTag;
    //TextView isEdited;
    UserApi userApi = ApiClient.getUserApi();
    Activity activity;
    ImageButton replyButton;
    RecyclerView recyclerView;

    public recyclerViewChatAdapter(Context context, List<message> messageList, String currentUserEmail, RecyclerView recyclerView) {
        this.context = context;
        this.activity = (Activity) context;
        this.messageList = messageList;
        this.currentUserEmail = currentUserEmail;
        this.currentUserAccess = getHelper(userApi.getPermissionByEmail(currentUserEmail));
        this.recyclerView = recyclerView;

        this.editingLayout = activity.findViewById(R.id.editing_framelayout);
        this.editingName = activity.findViewById(R.id.editing_name);
        this.editingText = activity.findViewById(R.id.editing_text);
        this.editingCancel = activity.findViewById(R.id.cancel_editting_button);
        this.editButton = activity.findViewById(R.id.editButton);
        this.sendButton = activity.findViewById(R.id.sendButton);
        this.messageInputField = activity.findViewById(R.id.MessageEditText);
        this.editingTag = activity.findViewById(R.id.editing_tag);
        //this.isEdited = activity.findViewById(R.id.isEdited);
        this.replyButton = activity.findViewById(R.id.replyButton);
    }

    @Override
    public int getItemViewType(int position) {
        message message = messageList.get(position);
        if (message.isReplied()) {
            return message.getSendersEmail().equals(currentUserEmail) ? 2 : 3;
        }
        return message.getSendersEmail().equals(currentUserEmail) ? 0 : 1;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
                view = LayoutInflater.from(context)
                        .inflate(R.layout.mymessageexample, parent, false);
        }
        else if (viewType == 1) {
                view = LayoutInflater.from(context)
                        .inflate(R.layout.othersmessageexample, parent, false);
        }
        else if (viewType == 2) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.myrepliedmessageexample, parent, false);
        }
        else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.othersrepliedmessageexample, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        message message = messageList.get(position);

        if (message.isReplied()) {

            long repId = message.getIdReplied();
            message messageInReply = getHelper(userApi.getMessageById(repId));

            holder.repliedName.setText(messageInReply.getName());
            holder.repliedTag.setText(messageInReply.getTag());
            holder.repliedMessage.setText(messageInReply.getText());

            holder.repliedName.setTextColor(Color.parseColor(messageInReply.getNameColor()));
            holder.repliedTag.setTextColor(Color.parseColor(messageInReply.getTagColor()));

            holder.repliedMessageLayout.setBackgroundColor(message.getRepliedBackGroundColor());
        }
        if (message.isEdited()) {
            holder.isEdited.setVisibility(View.VISIBLE);
        }

        holder.nameTextView.setTextColor(Color.parseColor(message.getNameColor()));
        holder.nameTextView.setText(message.getName());
        holder.textTextView.setText(message.getText());

        if (message.getTextColor() == null) {
            holder.textTextView.setTextColor(Color.parseColor("#1E1E1E"));
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

            actions = getActions(message, popupWindow, holder);

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


        TextView repliedTag;
        TextView repliedName;
        TextView repliedMessage;
        FrameLayout repliedMessageLayout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.Name);
            textTextView = itemView.findViewById(R.id.message);
            timeTextView = itemView.findViewById(R.id.Time);
            TagTextView = itemView.findViewById(R.id.Tag);
            MessageClicableLayout = itemView.findViewById(R.id.MessageClicableLayout);
            isEdited = itemView.findViewById(R.id.isEdited);

            if (itemView.findViewById(R.id.repliedName) != null) {
                repliedName = itemView.findViewById(R.id.repliedName);
                repliedTag = itemView.findViewById(R.id.repliedTag);
                repliedMessage = itemView.findViewById(R.id.repliedMessage);
                repliedMessageLayout = itemView.findViewById(R.id.repliedMessageLayout);
            }
        }
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.customcopytoast, null);

        TextView toastText = layout.findViewById(R.id.toastText);
        toastText.setText(message);

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

    public void editAdditional (message message, String newText) {
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

    private int getRandomPastelColor() {
        Random random = new Random();
        int baseColor = 120;
        int red = baseColor + random.nextInt(106);
        int green = baseColor + random.nextInt(106);
        int blue = baseColor + random.nextInt(106);
        return Color.rgb(red, green, blue);
    }

    public void edit (message m, MessageViewHolder holder) {
        sendButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        editingName.setText(m.getName());
        editingName.setTextColor(Color.parseColor(m.getNameColor()));
        editingText.setText(m.getText());
        editingTag.setText(m.getTag());
        editingTag.setTextColor(Color.parseColor(m.getTagColor()));

        editButton.setOnClickListener(e -> {
            String newText = String.valueOf(messageInputField.getText());
            if (!newText.isEmpty()) {
                editAdditional(m, newText);


                editButton.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                editingLayout.setVisibility(View.GONE);

                messageInputField.setText("");

                holder.isEdited.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        editingCancel.setOnClickListener(c -> {
            editButton.setVisibility(View.GONE);
            sendButton.setVisibility(View.VISIBLE);
            editingLayout.setVisibility(View.GONE);
        });

        editingLayout.setVisibility(View.VISIBLE);
    }

    public void pin (message message) {
        FrameLayout pinLayout = activity.findViewById(R.id.pinLayout);
        TextView pinTag = activity.findViewById(R.id.pinTag);
        TextView pinName = activity.findViewById(R.id.pinName);
        TextView pinMessage = activity.findViewById(R.id.pinMessage);

        if (!message.isPinned()) {
            message.setPinned(true);
            setHelper(userApi.setPinned(message.getId()));

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
        else {
            message.setPinned(false);
            setHelper(userApi.setUnPinned(message.getId()));
            pinLayout.setVisibility(View.GONE);
        }
    }


    // tests
    // trim for login page
    public void reply (message m) {
        sendButton.setVisibility(View.GONE);
        replyButton.setVisibility(View.VISIBLE);
        editingName.setText(m.getName());
        editingName.setTextColor(Color.parseColor(m.getNameColor()));
        editingText.setText(m.getText());
        editingTag.setText(m.getTag());
        editingTag.setTextColor(Color.parseColor(m.getTagColor()));

        replyButton.setOnClickListener(e -> {
            String newMessageText = String.valueOf(messageInputField.getText());
            if (!newMessageText.isEmpty()) {

                // Create repliedMessage
                String nameColor = getHelper(userApi.getNameColorByEmail(currentUserEmail.trim()));
                String tag = getHelper(userApi.getTagByEmail(currentUserEmail.trim()));
                String tagColor = getHelper(userApi.getTagColorByEmail(currentUserEmail.trim()));
                String UserName = getHelper(userApi.getUserNameByEmail(currentUserEmail));

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                String currentTime = sdf.format(new Date());

                message newRepliedMessage = new message(currentTime, UserName, newMessageText, currentUserEmail);

                if (nameColor == null) {
                    newRepliedMessage.setNameColor("#1E1E1E");
                } else {
                    newRepliedMessage.setNameColor(nameColor);
                }

                if (tagColor == null) {
                    newRepliedMessage.setTagColor("#1E1E1E");
                } else {
                    newRepliedMessage.setTagColor(tagColor);
                }

                newRepliedMessage.setTag(tag);
                newRepliedMessage.setSendersAccess(getHelper(userApi.getPermissionByEmail(currentUserEmail)));
                newRepliedMessage.setId(getHelper(userApi.getIdForNextMessage()) + 1);
                newRepliedMessage.setReplied(true);
                newRepliedMessage.setIdReplied(m.getId());
                newRepliedMessage.setRepliedBackGroundColor(getRandomPastelColor());

                messageList.add(newRepliedMessage);
                setHelper(userApi.addNewMessage(newRepliedMessage));

                notifyItemInserted(messageList.size() -1);
                recyclerView.scrollToPosition(messageList.size() - 1);
                messageInputField.setText("");

                replyButton.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                editingLayout.setVisibility(View.GONE);

                messageInputField.setText("");
            }
        });

        editingCancel.setOnClickListener(c -> {
            replyButton.setVisibility(View.GONE);
            sendButton.setVisibility(View.VISIBLE);
            editingLayout.setVisibility(View.GONE);
        });

        editingLayout.setVisibility(View.VISIBLE);
    }

    public List<MessageAction> getActions (message m, PopupWindow popupWindow, MessageViewHolder holder) {
        switch (currentUserAccess) {
            case "user":
                return setUserActions(m, popupWindow, holder);
            case "moderator":
                return setModeratorActions(m, popupWindow, holder);
            case "dev":
                return setDevActions(m, popupWindow, holder);
            default:
                return setDefaultActions(m, popupWindow, holder);
        }
    }

    public List<MessageAction> setUserActions (message m, PopupWindow popupWindow, MessageViewHolder holder) {
        List<MessageAction> actions = new ArrayList<>();
        if (Objects.equals(currentUserEmail, m.getSendersEmail())) {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Изменить", true, v1 -> {
                    edit(m, holder);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Удалить", true, v1 -> {
                    delete(m);
                    popupWindow.dismiss();
                }));
            }
        }
        else {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));
            }
        }
        return actions;
    }

    public List<MessageAction> setModeratorActions (message m, PopupWindow popupWindow, MessageViewHolder holder) {
        List<MessageAction> actions = new ArrayList<>();
        if (Objects.equals(currentUserEmail, m.getSendersEmail())) {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Изменить", true, v1 -> {
                    edit(m, holder);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction(m.isPinned() ? "Открепить" : "Закрепить", true, v1 -> {
                    pin(m);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Удалить", true, v1 -> {
                    delete(m);
                    popupWindow.dismiss();
                }));
            }
        }
        else {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Изменить", true, v1 -> {
                    edit(m, holder);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction(m.isPinned() ? "Открепить" : "Закрепить", true, v1 -> {
                    pin(m);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Удалить", true, v1 -> {
                    delete(m);
                    popupWindow.dismiss();
                }));
            }
        }
        return actions;
    }

    public List<MessageAction> setDevActions (message m, PopupWindow popupWindow, MessageViewHolder holder) {
        List<MessageAction> actions = new ArrayList<>();
        if (Objects.equals(currentUserEmail, m.getSendersEmail())) {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Изменить", true, v1 -> {
                    edit(m, holder);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction(m.isPinned() ? "Открепить" : "Закрепить", true, v1 -> {
                    pin(m);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Удалить", true, v1 -> {
                    delete(m);
                    popupWindow.dismiss();
                }));
            }
        }
        else {
            if (!m.isDeleted()) {
                actions.add(new MessageAction("Ответить", true, v1 -> {
                    reply(m);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Cкопировать", true, v1 -> {
                    copy(m.getText());
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction("Изменить", true, v1 -> {
                    edit(m, holder);
                    popupWindow.dismiss();
                }));

                actions.add(new MessageAction(m.isPinned() ? "Открепить" : "Закрепить", true, v1 -> {
                    pin(m);
                    popupWindow.dismiss();
                }));


                actions.add(new MessageAction("Удалить", true, v1 -> {
                    delete(m);
                    popupWindow.dismiss();
                }));
            }
        }
        return actions;
    }

    public List<MessageAction> setDefaultActions (message m, PopupWindow popupWindow, MessageViewHolder holder) {
        List<MessageAction> actions = new ArrayList<>();
        if (Objects.equals(currentUserEmail, m.getSendersEmail())) {

        }
        else {

        }
        return actions;
    }

}
