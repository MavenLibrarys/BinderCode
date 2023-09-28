package com.yxf.bindercode;

import com.yxf.bindercode.hicar.CardType;
import com.yxf.bindercode.hicar.RemoteCardView;

public class RemoteCardData {
    private String packageName;
    private int cardId;
    private CardType cardType;
    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private RemoteCardView remoteCardView;

    public RemoteCardData(CardType cardType) {
        this.cardType = cardType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public RemoteCardView getRemoteCardView() {
        return remoteCardView;
    }

    public void setRemoteCardView(RemoteCardView remoteCardView) {
        this.remoteCardView = remoteCardView;
    }
}
