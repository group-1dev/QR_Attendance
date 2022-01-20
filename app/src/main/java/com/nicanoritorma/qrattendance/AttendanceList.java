package com.nicanoritorma.qrattendance;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
        AttendanceVM attendanceVM = new AttendanceVM(getApplication());
        attendanceVM.getAllAttendance().observe(this, new Observer<List<AttendanceModel>>() {
            @Override
            public void onChanged(List<AttendanceModel> attendanceModels) {
                attendanceAdapter.setList(attendanceModels, new AttendanceAdapter.OnItemClick() {
                    @Override
                    public void onItemClick(int position) {
                        AttendanceModel attendanceModel = attendanceModels.get(position);
                        openClickedAttendance(new AttendanceModel(attendanceModel.getId(), attendanceModel.getAttendanceName(), attendanceModel.getDetails(),
                                attendanceModel.getDate(), attendanceModel.getTime()));
                    }
                });
            }
        });

        /**
         * Online View Model
         */
//        AttendanceViewModel attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
//        attendanceViewModel.getAttendanceList().observe(this, new Observer<List<AttendanceModel>>() {
//            @Override
//            public void onChanged(List<AttendanceModel> attendanceModels) {
//                attendanceAdapter.setList(attendanceModels, new AttendanceAdapter.OnItemClick() {
//                    @Override
//                    public void onItemClick(int position) {
//                        AttendanceModel attendanceModel = attendanceModels.get(position);
//                        openClickedAttendance(new AttendanceModel(attendanceModel.getId(), attendanceModel.getAttendanceName(), attendanceModel.getDetails(),
//                                attendanceModel.getDate(), attendanceModel.getTime()));
//                    }
//                });
//            }
//        });
    }

    private void openClickedAttendance(AttendanceModel attendance)
    {
        Intent intent = new Intent(AttendanceList.this, ClickedAttendance.class);
        intent.putExtra("ITEM_ID", attendance.getId());
        intent.putExtra(ClickedAttendance.EXTRA_ATTENDANCE_NAME, attendance.getAttendanceName());
        intent.putExtra(ClickedAttendance.EXTRA_DETAILS, attendance.getDetails());
        intent.putExtra(ClickedAttendance.EXTRA_DATE, attendance.getDate());
        intent.putExtra(ClickedAttendance.EXTRA_TIME, attendance.getTime());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}