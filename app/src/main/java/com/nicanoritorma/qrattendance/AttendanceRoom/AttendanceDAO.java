package com.nicanoritorma.qrattendance.AttendanceRoom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

@Dao
public interface AttendanceDAO {

    @Insert
    void insert(AttendanceModel attendance);

    @Update
    void update(AttendanceModel attendance);

    @Delete
    void delete(AttendanceModel attendance);

    @Query("DELETE FROM attendance_table")
    void deleteAllStudent();

    @Query("SELECT * FROM attendance_table ORDER BY id ASC")
    LiveData<List<AttendanceModel>> getAllAttendance();

}
