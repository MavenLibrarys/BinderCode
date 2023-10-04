package com.yxf.bindercode.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yxf.bindercode.db.entry.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM project")
    List<Project> getAll();

    @Insert
    void insertProject(Project project);
}
