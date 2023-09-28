package com.yxf.bindercode.hicar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class RemoteCardView extends CardView {

    public TextView txtCardType;

    public TextView getTxtCardType() {
        return txtCardType;
    }

    public void setTxtCardType(TextView txtCardType) {
        this.txtCardType = txtCardType;
    }

    public RemoteCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
