package com.nicanoritorma.qrattendance;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nicanoritorma.qrattendance.ui.adapter.AttendanceAdapter;

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
        ab.setTitle("Attendance List");

        rv_attendanceList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_attendanceList.setHasFixedSize(true);

        attendanceAdapter = new AttendanceAdapter();

        rv_attendanceList.setAdapter(attendanceAdapter);

    }
}