package com.yxf.bindercode.hicar.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxf.bindercode.R;
import com.yxf.bindercode.RemoteCardData;
import com.yxf.bindercode.hicar.CardType;
import com.yxf.bindercode.hicar.RemoteCardView;
import com.yxf.bindercode.hicar.RemoteCardViewClient;

import java.util.List;

public class RemoteCardAdapter extends RecyclerView.Adapter<RemoteCardAdapter.VH> {
    private List<RemoteCardData> cardList;

    public RemoteCardAdapter(List<RemoteCardData> cardList) {
        this.cardList = cardList;
    }

    public List<RemoteCardData> getCardList() {
        return cardList;
    }

    @Override
    public int getItemViewType(int position) {
        CardType cardType = cardList.get(position).getCardType();
        return cardType.getValue();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RemoteCardView view = RemoteCardViewClient.getInstance().generatorView(parent);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RemoteCardView remoteCardView = (RemoteCardView) holder.itemView;
        remoteCardView.setTxtCardType(holder.txtCardType);
        RemoteCardData remoteCardData = cardList.get(position);
        remoteCardData.setRemoteCardView(remoteCardView);
        holder.txtCardType.setText(remoteCardData.getCardType().name());
        holder.idProgress.setProgress(remoteCardData.getProgress());
    }

    @Override
    public int getItemCount() {
        return cardList == null ? 0 : cardList.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        protected TextView txtCardType;
        protected ProgressBar idProgress;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtCardType = itemView.findViewById(R.id.idCardType);
            idProgress = itemView.findViewById(R.id.idProgress);
        }
    }

}
