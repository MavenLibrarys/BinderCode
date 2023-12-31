package com.yxf.bindercode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.yxf.baselibrary.LogUtil;
import com.yxf.bindercode.activity.NotificationActivity;
import com.yxf.bindercode.databinding.ActivityMainBinding;
import com.yxf.bindercode.db.DbCentre;
import com.yxf.bindercode.db.dao.ProjectDao;
import com.yxf.bindercode.db.entry.Project;
import com.yxf.bindercode.hicar.ThirdAppConnectorMgr;
import com.yxf.bindercode.hicar.api.LoginBean;
import com.yxf.bindercode.hicar.api.LoginService;
import com.yxf.bindercode.hicar.api.ProjectService;
import com.yxf.bindercode.hicar.api.UrlConstants;
import com.yxf.bindercode.hicar.api.retrofit.EnjoyRetrofit;
import com.yxf.bindercode.reflect.InjectUtil;
import com.yxf.bindercode.reflect.InjectView;
import com.yxf.bindercode.utils.QueryAlgorithm;
import com.yxf.bindercode.utils.SortAlgorithmUtil;
import com.yxf.bindercode.viewmodel.LiveDataUtil;
import com.yxf.bindercode.viewmodel.MainViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity ";
    public static final int NAV_START = 1;
    public static final int NAV_STOP = 0;
    ActivityMainBinding binding;
    private List<String> flowList = new ArrayList<>();

    @InjectView(R.id.idReflect)
    private Button idReflect;

    @InjectView(R.id.idQuery)
    private Button idQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        InjectUtil.injectView(this);
        binding.idStartNav.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("nav_state", NAV_START);
            ThirdAppConnectorMgr.getInstance().callback(Constants.OPERATOR_ACTION, Constants.AMAP_PACKAGE, bundle);
            ThirdAppConnectorMgr.getInstance().callback(Constants.OPERATOR_ACTION, Constants.BAIDU_PACKAGE, bundle);
        });

        binding.idStopNav.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("nav_state", NAV_STOP);
            ThirdAppConnectorMgr.getInstance().callback(Constants.OPERATOR_ACTION, Constants.AMAP_PACKAGE, bundle);
            ThirdAppConnectorMgr.getInstance().callback(Constants.OPERATOR_ACTION, Constants.BAIDU_PACKAGE, bundle);
        });

        binding.idRetrofit.setOnClickListener(v -> {
            retrofit();
        });
        binding.idNotification.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        });
        dbOperate();

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        idReflect.setOnClickListener(v -> {
            /*LiveDataUtil.getInstance().data.postValue("更新数据02！！！");
            startActivity(new Intent(this, NotificationActivity.class)
                    .putExtra("id", 123)
                    .putExtra("notify_name", "mary"));*/
            idReflect.setText("reflect--after rotate screen.");
            mainViewModel.count = 20;
            LogUtil.i(TAG, "count01:" + mainViewModel.count);
        });

        binding.idSort.setOnClickListener(v -> {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) (Math.random() * 100);
            }
//            SortAlgorithmUtil.bubbleSort(arr);
//            SortAlgorithmUtil.selectSort(arr);
//            SortAlgorithmUtil.insertSort(arr);
            SortAlgorithmUtil.quickSort(arr);
            LogUtil.list(TAG, arr);
            int index = QueryAlgorithm.binaryQuery(arr, 50);
            LogUtil.i(TAG, "index:" + index);
        });
    }

    private void initFlowData() {
        flowList.clear();
        for (int i = 0; i < 3; i++) {
            flowList.add("红心火龙果");
            flowList.add("猕猴桃");
            flowList.add("橙子");
            flowList.add("西红柿");
            flowList.add("红心火龙果");
            flowList.add("香蕉");
            flowList.add("香甜哈密瓜");
        }
    }

    private void dbOperate() {
        binding.idInsert.setOnClickListener(v -> {
            DbCentre dbCentre = DbCentre.getInstance();
            ProjectDao projectDao = dbCentre.getProjectDao();
            projectDao.insertProject(new Project(123, "p123"));
        });

        binding.idDelete.setOnClickListener(v -> {
            DbCentre dbCentre = DbCentre.getInstance();
            ProjectDao projectDao = dbCentre.getProjectDao();
            Project project = projectDao.getProjectByUid(15);
            projectDao.deleteProject(project);
        });

        binding.idUpdate.setOnClickListener(v -> {
            DbCentre dbCentre = DbCentre.getInstance();
            ProjectDao projectDao = dbCentre.getProjectDao();
            Project project = projectDao.getFirstProject();
            if (project == null) {
                return;
            }
            project.setProjectName(project.getProjectName() + "----刚更新了project的名称.");
            int res = projectDao.updateProject(project);
            LogUtil.i(TAG, "update res:" + res);
        });

        idQuery.setOnClickListener(v -> {
            DbCentre dbCentre = DbCentre.getInstance();
            ProjectDao projectDao = dbCentre.getProjectDao();
            Project project = projectDao.getFirstProject();
            LogUtil.i(TAG, "query project:" + project);
        });
    }

    private void enjoyRetrofit() {
        //自定义retrofit请求
        EnjoyRetrofit enjoyRetrofit = new EnjoyRetrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .build();
        ProjectService projectService = enjoyRetrofit.create(ProjectService.class);
        okhttp3.Call projectInfo = projectService.getProjectInfo("294");
        projectInfo.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                LogUtil.i(TAG, "onFailure :" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                ResponseBody body = response.body();
                LogUtil.i(TAG, "onResponse :" + body.string());
            }
        });

        projectService.login("yxf1234", "123456")
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        LogUtil.i(TAG, "onFailure msg:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        LogUtil.i(TAG, "onResponse code:" + response.code());
                        String string = response.body().string();
                        LogUtil.i(TAG, "data:" + string);
                    }
                });
    }

    private void retrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginService loginService = retrofit.create(LoginService.class);
                Call call = loginService.login("yxf1234", "123456");
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        LogUtil.i(TAG, "onResponse :" + response.message());
                        boolean successful = response.isSuccessful();
                        Object body = response.body();
                        LogUtil.i(TAG, "body:" + body.toString());
                        Gson gson = new Gson();
                        String json = gson.toJson(body);
                        LogUtil.i(TAG, "json:" + json);
                        LoginBean loginBean = gson.fromJson(json, LoginBean.class);
                        String nickname = loginBean.getData().getNickname();
                        LogUtil.i(TAG, "login success ,nickname:" + nickname);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        LogUtil.i(TAG, "onFailure :" + t.getMessage());
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy===");
        ThirdAppConnectorMgr.getInstance().destroy();
    }
}