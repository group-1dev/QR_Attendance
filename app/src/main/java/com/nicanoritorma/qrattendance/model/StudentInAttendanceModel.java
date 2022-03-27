package com.nicanoritorma.qrattendance.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Nicanor Itorma
 */

@Entity(tableName = "studentAdded_table")
public class StudentInAttendanceModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fullname;
    private String idNum;
    private int parentId;
    private String timeAndDate;

    public StudentInAttendanceModel() {}

    public StudentInAttendanceModel(String fullname, String idNum, String timeAndDate, int parentId) {
        this.fullname = fullname;
        this.idNum = idNum;
        this.parentId = parentId;
        this.timeAndDate = timeAndDate;
    }

    public StudentInAttendanceModel(String fullname, String idNum, String timeAndDate) {
        this.fullname = fullname;
        this.idNum = idNum;
        this.timeAndDate = timeAndDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }
}
