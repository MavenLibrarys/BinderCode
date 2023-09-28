package com.yxf.bindercode.hicar;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.R;
import com.yxf.bindercode.RemoteCardData;
import com.yxf.bindercode.hicar.adapter.RemoteCardAdapter;

import java.util.List;

public class BlurManagerClient {

    public static final String TAG = "BlurManagerClient";
    private RecyclerView cardRecyclerView;

    private volatile static BlurManagerClient instance = null;

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            LogUtil.w(TAG, "data onChanged.");
            refreshBackground(-1);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            LogUtil.w(TAG, "data onItemRangeChanged，positionStart==" + positionStart + " ,itemCount==" + itemCount);
            refreshBackground(positionStart);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            LogUtil.w(TAG, "onItemRangeChanged 333333333333");
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    //double check
    public static BlurManagerClient getInstance() {
        if (instance == null) {
            synchronized (BlurManagerClient.class) {
                if (instance == null) {
                    instance = new BlurManagerClient();
                }
            }
        }
        return instance;
    }

    public void bindItem(View view) {
        if (view instanceof RecyclerView) {
            cardRecyclerView = (RecyclerView) view;
        }
        if (cardRecyclerView.getAdapter() == null) {
            LogUtil.w(TAG, "cardRecyclerview adapter is null!");
            return;
        }
        cardRecyclerView.getAdapter().registerAdapterDataObserver(mDataObserver);
    }

    private void refreshBackground(int position) {
        List<RemoteCardData> cardList = ((RemoteCardAdapter) cardRecyclerView.getAdapter()).getCardList();
        if (position == -1) {
            for (RemoteCardData cardData :
                    cardList) {
                View contentView = cardData.getRemoteCardView();
                if (contentView != null) {
                    blur(contentView);
                }
            }
            return;
        }
        RemoteCardView remoteCardView = cardList.get(position).getRemoteCardView();
        if (remoteCardView != null) {
            blur(remoteCardView);
            remoteCardView.txtCardType.setText("更新文字.");
        }
    }

    //添加毛玻璃背景
    private void blur(View view) {
        LogUtil.w(TAG, "blur remote card view.");
        view.setBackgroundResource(R.drawable.blur_bg);
    }

}
