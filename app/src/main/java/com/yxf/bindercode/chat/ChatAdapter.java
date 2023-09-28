package com.yxf.bindercode.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxf.bindercode.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "ChatAdapter ";
    private Context context;
    private List<ChatBean> chatBeanList;

    public ChatAdapter(Context context, List<ChatBean> chatBeanList) {
        this.context = context;
        this.chatBeanList = chatBeanList;
    }

    @Override
    public int getItemViewType(int position) {
        ChatBean chatBean = chatBeanList.get(position);
        return chatBean.getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutType layoutType = LayoutType.getLayoutType(viewType);
        if (layoutType == LayoutType.LEFT) {
            return new LeftViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_chat_receive_item_layout, parent, false));
        } else {
            return new RightViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_chat_send_item_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBean chatBean = chatBeanList.get(position);
        if (holder instanceof LeftViewHolder) {
            ((LeftViewHolder) holder).txtContext.setText(chatBean.getContent());
        } else {
            ((RightViewHolder) holder).txtContext.setText(chatBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return chatBeanList.size();
    }

    private class LeftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtContext;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContext = itemView.findViewById(R.id.txtContent);
        }
    }

    private class RightViewHolder extends RecyclerView.ViewHolder {
        public TextView txtContext;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContext = itemView.findViewById(R.id.txtContent);
        }
    }

    public enum LayoutType {
        LEFT(1), RIGHT(2);

        private int value;

        public int getValue() {
            return value;
        }

        public static LayoutType getLayoutType(int type) {
            for (LayoutType layoutType :
                    values()) {
                if (type == layoutType.value) {
                    return layoutType;
                }
            }
            return LEFT;
        }

        LayoutType(int value) {
            this.value = value;
        }
    }
}
