package com.yxf.bindercode.hicar;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yxf.bindercode.CarApplication;
import com.yxf.bindercode.R;

public class RemoteCardViewClient {

    private static volatile RemoteCardViewClient instance;

    public static RemoteCardViewClient getInstance() {
        if (instance == null) {
            synchronized (RemoteCardViewClient.class) {
                if (instance == null) {
                    instance = new RemoteCardViewClient();
                }
            }
        }
        return instance;
    }

    public RemoteCardView generatorView(ViewGroup parentView) {
        RemoteCardView remoteCardView = (RemoteCardView) LayoutInflater.from(CarApplication.getInstance()).inflate(R.layout.item_remote_card_layout, parentView, false);
        return remoteCardView;
    }
}
