package com.nicanoritorma.qrattendance.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "studentAdded_table")
public class StudentInAttendanceModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String idNum;
    private int parentId;

    public StudentInAttendanceModel() {}

    public StudentInAttendanceModel(String name, String idNum, int parentId) {
        this.name = name;
        this.idNum = idNum;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
