package com.yxf.bindercode.db;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yxf.bindercode.CarApplication;
import com.yxf.bindercode.db.dao.GoodsDao;
import com.yxf.bindercode.db.dao.ProjectDao;
import com.yxf.bindercode.db.dao.UserDao;
import com.yxf.bindercode.db.database.AppDatabase;

public class DbCentre {
    public static final String DB_NAME = "yxfDb";
    private static AppDatabase appDatabase;
    private static final DbCentre instance = new DbCentre();

    public static DbCentre getInstance() {
        return instance;
    }

    static {
        appDatabase = Room
                .databaseBuilder(CarApplication.getInstance().getBaseContext(), AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .addMigrations(new Migration(3, 4) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Goods add column goods_price TEXT");
                    }
                })
                .build();
    }

    public UserDao getUserDao() {
        return appDatabase.userDao();
    }

    public ProjectDao getProjectDao() {
        return appDatabase.projectDao();
    }

    public GoodsDao getGoodsDao() {
        return appDatabase.goodsDao();
    }

}
