package com.nicanoritorma.qrattendance.OfflineViewModels;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.nicanoritorma.qrattendance.OfflineRepository.StudentInAttendanceRepository;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.List;

public class StudentInAttendanceVM extends AndroidViewModel {

    private StudentInAttendanceRepository repository;
    private LiveData<List<StudentInAttendanceModel>> studentList;

    public StudentInAttendanceVM(@NonNull Application application) {
        super(application);

        repository = new StudentInAttendanceRepository(application);
        //studentList = repository.getStudentInAttendanceList();
    }

    public void insert(StudentInAttendanceModel student) {
        repository.insert(student);
    }

    public void update(StudentInAttendanceModel student) {
        repository.update(student);
    }

    public void delete(StudentInAttendanceModel student) {
        repository.delete(student);
    }

    public void deleteAllStudentInAttendance() {
        repository.deleteAllStudentInAttendance();
    }

    public LiveData<List<StudentInAttendanceModel>> getAllStudentInAttendance() {
        return studentList;
    }

    //get all attendance content to be save to device storage
    public Cursor getAttendanceContent(int id) {
        return repository.getAttendanceContent(id);
    }

    public LiveData<List<StudentInAttendanceModel>> getStudentList(int parentId)
    {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM studentAdded_table WHERE parentId = " + parentId);
        studentList = repository.getStudentsInAttendance(query);
        return studentList;
    }
}
