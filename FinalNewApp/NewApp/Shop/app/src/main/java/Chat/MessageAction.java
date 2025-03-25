package Chat;

import android.view.View;

public class MessageAction {
    private String title;
    private boolean isVisible;
    private View.OnClickListener onClickListener;

    public MessageAction(String title, boolean isVisible, View.OnClickListener onClickListener) {
        this.title = title;
        this.isVisible = isVisible;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
