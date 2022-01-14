package com.nicanoritorma.qrattendance;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.nicanoritorma.qrattendance.OfflineViewModels.AttendanceVM;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewAttendance extends BaseActivity {

    private EditText et_attendance, et_details;
    private MaterialButton btn_createAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_attendance);

        et_attendance = findViewById(R.id.et_attendance);
        et_details = findViewById(R.id.et_details);
        btn_createAttendance = findViewById(R.id.btn_createAttendance);

        initUI();

    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create Attendance");

        btn_createAttendance.setOnClickListener(view -> AddAttendanceToDb(getData()[0], getData()[1], getData()[2], getData()[3]));
    }

    private String[] getData()
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        Date mDate = new Date();

        String attendanceName = et_attendance.getText().toString().trim();
        String details = et_details.getText().toString().trim();
        String date = dateFormatter.format(mDate);
        String time = timeFormatter.format(mDate);

        return new String[]{attendanceName, details, date, time};
    }

    private void AddAttendanceToDb(String attendanceName, String details, String date, String time)
    {
        //offline db
        AttendanceVM attendanceVM = new ViewModelProvider(this).get(AttendanceVM.class);
        attendanceVM.insert(new AttendanceModel(attendanceName, details, date, time));

//        //online db
//        AttendanceViewModel attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
//        attendanceViewModel.insert(attendanceName, details, date, time);
//
    }
}