package com.yxf.bindercode.db.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Project {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "proId")
    public int projectId;

    private String projectName;

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Project{" +
                "uid=" + uid +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
