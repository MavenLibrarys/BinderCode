package com.yxf.bindercode.chat;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yxf.baselibrary.FileUtil;
import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.BaseViewModel;
import com.yxf.bindercode.CarApplication;
import com.yxf.bindercode.adapter.ImageAdapter;

import java.io.File;

public class ChatViewModel extends BaseViewModel {
    public static final String TAG = "ChatViewModel ";
    public ObservableField<String> name = new ObservableField<>("");

    public MutableLiveData<Integer> heartRate = new MutableLiveData<>();

    public String imageUrl = "https://img-blog.csdnimg.cn/img_convert/ea0660662b6e3c11bbc48be0a0a3e6b9.png";
    public ObservableField<String> test2 = new ObservableField<>("def");

    private final Observable.OnPropertyChangedCallback nameCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            LogUtil.i(TAG, "name:" + name.get());
        }
    };

    public ChatViewModel() {
        LogUtil.i(TAG, "构造viewModel...");
        name.addOnPropertyChangedCallback(nameCallback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = ImageAdapter.downloadUrl(imageUrl);
                File chatFile = CarApplication.getInstance().getExternalFilesDir("chatFile");
                if (!chatFile.exists()) {
                    chatFile.mkdirs();
                }
                String path = chatFile.getPath() + "/" + System.currentTimeMillis() + ".png";
                LogUtil.i(TAG, "开始下载");
                FileUtil.saveFile(bytes, path);
                LogUtil.i(TAG, "下载完成");


                try {
                    Thread.sleep(3000);
                    heartRate.postValue(6);//子线程也可以
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtil.i(TAG, "销毁viewModel...");
        name.removeOnPropertyChangedCallback(nameCallback);
    }
}
