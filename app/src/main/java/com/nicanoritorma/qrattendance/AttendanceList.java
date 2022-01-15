package com.nicanoritorma.qrattendance;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.nicanoritorma.qrattendance.OfflineViewModels.AttendanceVM;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.ui.adapter.AttendanceAdapter;
import com.nicanoritorma.qrattendance.OnlineViewModels.AttendanceViewModel;

import java.util.List;

public class AttendanceList extends BaseActivity {

    private RecyclerView rv_attendanceList;
    private AttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        rv_attendanceList = findViewById(R.id.rv_attendanceList);

        initUI();
    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Attendance List");
        }

        rv_attendanceList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_attendanceList.setHasFixedSize(true);
        attendanceAdapter = new AttendanceAdapter();
        rv_attendanceList.setAdapter(attendanceAdapter);

        //offline attendance list
        AttendanceVM attendanceVM = new ViewModelProvider(this).get(AttendanceVM.class);
        attendanceVM.getAllAttendance().observe(this, new Observer<List<AttendanceModel>>() {
            @Override
            public void onChanged(List<AttendanceModel> attendanceModels) {
                attendanceAdapter.setList(attendanceModels);
            }
        });
    }
}