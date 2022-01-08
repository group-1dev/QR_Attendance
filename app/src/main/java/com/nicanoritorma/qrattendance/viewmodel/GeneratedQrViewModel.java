package com.nicanoritorma.qrattendance.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.repository.OnlineStudentRepo;

import java.util.List;

public class GeneratedQrViewModel extends AndroidViewModel {

    private OnlineStudentRepo onlineStudentRepo;

    public GeneratedQrViewModel(Application application) {
        super(application);
        onlineStudentRepo = new OnlineStudentRepo(application);
    }

    public boolean insert(String fullname, String idNum, String dept, String qrCode) {
        return onlineStudentRepo.insert(new StudentModel(fullname, idNum, dept, qrCode));
    }

    public LiveData<List<StudentModel>> getStudentList() {
        return onlineStudentRepo.getStudentList();
    }
}
