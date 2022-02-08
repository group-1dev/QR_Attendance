package com.nicanoritorma.qrattendance.ClickedAttendanceRoom;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StudentInAttendanceDAO {

    @Insert
    void insert(StudentInAttendanceModel studentInAttendanceModel);

    @Update
    void update(StudentInAttendanceModel studentInAttendanceModel);

    @Delete
    void delete(StudentInAttendanceModel studentInAttendanceModel);

    @Query("DELETE FROM studentAdded_table")
    void deleteAllStudent();

    @Query("SELECT * FROM studentAdded_table WHERE parentId=:id")
    LiveData<List<StudentInAttendanceModel>> getStudents(int id);

}
