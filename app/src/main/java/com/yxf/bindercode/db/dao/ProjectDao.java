package com.yxf.bindercode.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yxf.bindercode.db.entry.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert
    void insertProject(Project project);

    @Delete
    void deleteProject(Project project);

    @Update
    int updateProject(Project project);

    @Update
    void updateMultiProject(List<Project> project);

    @Query("select * from project")
    List<Project> getProjectAll();

    @Query("select * from project")
    Project getFirstProject();

    @Query("select * from project where uid=:uid")
    Project getProjectByUid(int uid);
}
