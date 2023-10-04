package com.yxf.bindercode.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yxf.bindercode.db.dao.GoodsDao;
import com.yxf.bindercode.db.dao.ProjectDao;
import com.yxf.bindercode.db.dao.UserDao;
import com.yxf.bindercode.db.entry.Goods;
import com.yxf.bindercode.db.entry.Project;
import com.yxf.bindercode.db.entry.User;


@Database(entities = {User.class, Project.class, Goods.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ProjectDao projectDao();

    public abstract GoodsDao goodsDao();
}
