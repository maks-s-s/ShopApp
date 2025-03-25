package Chat;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shop.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MessageActionAdapter extends RecyclerView.Adapter<MessageActionAdapter.ViewHolder> {
    private List<MessageAction> actions;

    public MessageActionAdapter(List<MessageAction> actions) {
        this.actions = actions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_action_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageAction action = actions.get(position);

        holder.button.setText(action.getTitle());
        holder.button.setOnClickListener(action.getOnClickListener());

        if (position == actions.size() - 1) {
            holder.spliterator.setVisibility(View.GONE);
        } else {
            holder.spliterator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        View spliterator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btnAction);
            spliterator = itemView.findViewById(R.id.spliterator);
        }
    }
}
