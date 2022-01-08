package com.nicanoritorma.qrattendance.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.List;

@Dao
public interface StudentDAO {
    @Insert
    void insert(StudentModel student);

    @Update
    void update(StudentModel student);

    @Delete
    void delete(StudentModel student);

    @Query("DELETE FROM student_table")
    void deleteStudentList();

    @Query("SELECT * FROM student_table ORDER BY id ASC")
    LiveData<List<StudentModel>> getStudentList();
}
