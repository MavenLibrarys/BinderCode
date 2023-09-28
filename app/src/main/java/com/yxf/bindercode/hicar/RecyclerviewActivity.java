package com.yxf.bindercode.hicar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.yxf.bindercode.R;
import com.yxf.bindercode.RemoteCardData;
import com.yxf.bindercode.hicar.adapter.RemoteCardAdapter;
import com.yxf.bindercode.databinding.ActivityRecyclerviewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerviewActivity extends AppCompatActivity {
    ActivityRecyclerviewBinding binding;
    private List<RemoteCardData> cardList = new ArrayList<>();
    private RemoteCardAdapter remoteCardAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview);
        initData();
        binding.idRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        remoteCardAdapter = new RemoteCardAdapter(cardList);
        binding.idRecyclerView.setAdapter(remoteCardAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.divider_remote_selector));
        binding.idRecyclerView.addItemDecoration(dividerItemDecoration);
        registerListener();
        Object payLoad = new Object();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (RemoteCardData data :
                        cardList) {
                    data.setProgress(data.getProgress() + 1);
                    if (data.getProgress() > 100) {
                        timer.cancel();
                    }
                }
                if (mCount++ >= 3) {
                    timer.cancel();
                }
                runOnUiThread(() -> {
//                    remoteCardAdapter.notifyDataSetChanged();
                    remoteCardAdapter.notifyItemChanged(mCount - 1, payLoad);
                });
            }
        }, 100, 500);
//        binding.idRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                remoteCardAdapter.notifyItemChanged(0, payLoad);
//                remoteCardAdapter.notifyItemChanged(1, payLoad);
////                remoteCardAdapter.notifyItemRangeChanged(0, 2, payLoad);
//            }
//        }, 2000);
    }

    private void registerListener() {
        BlurManagerClient.getInstance().bindItem(binding.idRecyclerView);
    }

    private void initData() {
        cardList.clear();
        cardList.add(new RemoteCardData(CardType.MAP));
        cardList.add(new RemoteCardData(CardType.MEDIA));
        cardList.add(new RemoteCardData(CardType.WEATHER));
        cardList.add(new RemoteCardData(CardType.EDIT));
    }
}