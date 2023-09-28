package com.yxf.bindercode.chat;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yxf.baselibrary.DensityUtil;
import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.BaseActivity;
import com.yxf.bindercode.CarApplication;
import com.yxf.bindercode.R;
import com.yxf.bindercode.databinding.ActivityChatLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity<ActivityChatLayoutBinding> {

    public static final String TAG = "ChatActivity ";

    private ChatAdapter chatAdapter;
    private List<ChatBean> chatBeanList = new ArrayList<>();

    @Override
    public int layoutID() {
        return R.layout.activity_chat_layout;
    }

    @Override
    public void initView() {
        super.initView();
        binding.idRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.idRecyclerView.setAdapter(chatAdapter = new ChatAdapter(this, chatBeanList));

        ChatViewModel chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        binding.setChatViewModel(chatViewModel);

        binding.btnSubmit.setOnClickListener(v -> {
            String content = binding.etContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                LogUtil.w(TAG, "内容为空.");
                return;
            }
            ChatBean chatBean = new ChatBean(ChatAdapter.LayoutType.RIGHT.getValue(), content);
            binding.etContent.setText("");
            chatBeanList.add(chatBean);
            binding.idRecyclerView.scrollToPosition(chatBeanList.size() - 1);
            chatAdapter.notifyDataSetChanged();
        });
        LogUtil.w(TAG, "width:" + DensityUtil.getScreenWidth() + "px ==> " + DensityUtil.px2dp(DensityUtil.getScreenWidth()) + " dp");

        binding.idNavigation.txtTitle.setOnClickListener(v -> {
            chatViewModel.name.set("test");
        });

        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.i(TAG, "输入框内容:" + s.toString() + " ,viewModel name:" + chatViewModel.name.get());
                chatViewModel.test2.set(s.toString());
            }
        });

        binding.idImg.post(new Runnable() {
            @Override
            public void run() {
                int width = binding.idImg.getMeasuredWidth();
                int height = binding.idImg.getHeight();
                LogUtil.w(TAG, "width:" + width + ",height:" + height);
            }
        });


        chatViewModel.heartRate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                LogUtil.w(TAG, "onChanged integer :" + integer);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        chatBeanList.clear();
        chatBeanList.add(new ChatBean(1, "hello!"));
        chatBeanList.add(new ChatBean(2, "hello! who is that?"));
        chatBeanList.add(new ChatBean(1, "I am Tom, Nice to meet you!"));
        chatAdapter.notifyDataSetChanged();
    }

    public void hide_keyboard_from() {
        InputMethodManager inputMethodManager = (InputMethodManager) CarApplication.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public String getTopTxt() {
        return "聊天室";
    }
}
