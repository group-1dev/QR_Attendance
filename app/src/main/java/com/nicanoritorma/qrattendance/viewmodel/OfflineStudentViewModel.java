package com.nicanoritorma.qrattendance.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.repository.StudentRepo;

import java.util.List;

public class OfflineStudentViewModel extends AndroidViewModel {

    private StudentRepo studentRepo;
    private LiveData<List<StudentModel>> allStudent;

    public OfflineStudentViewModel(@NonNull Application application) {
        super(application);
        studentRepo = new StudentRepo(application);
        allStudent = studentRepo.getAllStudent();
    }

    public void insert(StudentModel student)
    {
        studentRepo.insert(student);
    }

    public void update(StudentModel student)
    {
        studentRepo.update(student);
    }

    public void delete(StudentModel student)
    {
        studentRepo.delete(student);
    }

    public LiveData<List<StudentModel>> getAllStudent()
    {
        return allStudent;
    }

}
