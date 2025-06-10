package Chat;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import database.ApiClient;
import database.ApiHelper;
import database.RDBUser;
import database.UserApi;

public class ListOfAbusesAdapter extends RecyclerView.Adapter<ListOfAbusesAdapter.ViewHolder> implements ApiHelper {
    UserApi userApi = ApiClient.getUserApi();
    List<Abuse> abuseList;
    Optional<String> currentUserAccess;
    ImageView isAbused;
    Context context;
    public ListOfAbusesAdapter (List<Abuse> abuseList, String access, Context context, ImageView isAbused) {
        this.abuseList = abuseList;
        this.currentUserAccess = Optional.ofNullable(access);
        this.context = context;
        this.isAbused = isAbused;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.abuse_example, parent, false);
        return new ListOfAbusesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Abuse abuse = abuseList.get(position);
        RDBUser sender = getHelper(userApi.getUserByEmail(abuse.getSenderEmail()));

        holder.name.setText(sender.getUsername());
        holder.name.setTextColor(Color.parseColor(sender.getNameColor()));

        holder.tag.setText(sender.getTag());
        holder.tag.setTextColor(Color.parseColor(sender.getTagColor()));

        holder.reason.setText("Reason: " + abuse.getReason());
        holder.description.setText(abuse.getDescription());

        holder.time.setText(abuse.getTime());

        holder.deleteAbuse.setOnClickListener(v -> {
            if (!currentUserAccess.orElse("user").equals("dev") && sender.getPermission().equals("dev")) {
                showCustomToast("You don't have permission to delete devs abuses");
            }
            else {
                setHelper(userApi.deleteAbuse(abuse.getId()));
                abuseList.remove(abuse);
                notifyDataSetChanged();
                if (abuseList.isEmpty()) {
                    isAbused.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return abuseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView tag;
        TextView reason;
        TextView time;
        TextView description;
        ImageButton deleteAbuse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.Name);
            this.tag = itemView.findViewById(R.id.Tag);
            this.reason = itemView.findViewById(R.id.reason);
            this.time = itemView.findViewById(R.id.Time);
            this.description = itemView.findViewById(R.id.description);
            this.deleteAbuse = itemView.findViewById(R.id.delte_abuse);


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
}
