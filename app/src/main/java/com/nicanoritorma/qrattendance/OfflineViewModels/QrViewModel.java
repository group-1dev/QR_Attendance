package com.nicanoritorma.qrattendance.OfflineViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.OfflineRepository.StudentRepository;

import java.util.List;

public class QrViewModel extends AndroidViewModel {

    private StudentRepository repository;
    private LiveData<List<StudentModel>> allStudent;

    public QrViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudent = repository.getAllStudent();
    }

    public void insert(StudentModel student)
    {
        repository.insert(student);
    }

    public void update(StudentModel student)
    {
        repository.update(student);
    }

    public void delete(StudentModel student)
    {
        repository.delete(student);
    }

    public void deleteAllNotes() {
        repository.deleteAllStudent();
    }

    public LiveData<List<StudentModel>> getAllStudent() {
        return allStudent;
    }
}
