package com.yxf.bindercode.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yxf.bindercode.db.entry.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(List<User> users);

    @Insert
    void insertUser(User user);
}
