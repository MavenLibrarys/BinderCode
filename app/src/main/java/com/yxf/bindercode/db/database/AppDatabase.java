package com.yxf.bindercode.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yxf.bindercode.db.dao.GoodsDao;
import com.yxf.bindercode.db.dao.ProjectDao;
import com.yxf.bindercode.db.entry.Goods;
import com.yxf.bindercode.db.entry.Project;


@Database(entities = {Project.class, Goods.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public static final String TAG = "AppDatabase ";

    public abstract ProjectDao projectDao();

    public abstract GoodsDao goodsDao();
}

