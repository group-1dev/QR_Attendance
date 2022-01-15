package com.nicanoritorma.qrattendance.OfflineViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nicanoritorma.qrattendance.OfflineRepository.AttendanceRepository;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

public class AttendanceVM extends AndroidViewModel {

    private AttendanceRepository repository;
    private LiveData<List<AttendanceModel>> attendanceList;

    public AttendanceVM(@NonNull Application application) {
        super(application);
        repository = new AttendanceRepository(application);
        attendanceList = repository.getAttendanceList();
    }

    public void insert(AttendanceModel attendance) {
        repository.insert(attendance);
    }

    public void update(AttendanceModel attendance) {
        repository.update(attendance);
    }

    public void delete(AttendanceModel attendance) {
        repository.delete(attendance);
    }

    public void deleteAllNotes() {
        repository.deleteAllAttendance();
    }

    public LiveData<List<AttendanceModel>> getAllAttendance() {
        return attendanceList;
    }
}
