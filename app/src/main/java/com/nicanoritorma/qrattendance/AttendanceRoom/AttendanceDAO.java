package com.nicanoritorma.qrattendance.AttendanceRoom;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

@Dao
public interface AttendanceDAO {

    @Insert
    void insert(AttendanceModel attendance);

    @Update
    void update(AttendanceModel attendance);

    @Query("UPDATE attendance_table SET date =:date ,time=:time WHERE id =:id")
    void updateTime(int id, String date, String time);

    @Query("SELECT * FROM attendance_table WHERE id=:id")
    LiveData<AttendanceModel> getAttendanceDT(int id);

    @Delete
    void delete(AttendanceModel attendance);

    @Query("DELETE FROM attendance_table")
    void deleteAllStudent();

    @Query("SELECT * FROM attendance_table ORDER BY id ASC")
    LiveData<List<AttendanceModel>> getAllAttendance();


}
