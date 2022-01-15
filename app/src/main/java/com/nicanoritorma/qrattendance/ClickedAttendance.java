package com.nicanoritorma.qrattendance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nicanoritorma.qrattendance.ui.adapter.StudentInAttendanceAdapter;
import com.nicanoritorma.qrattendance.utils.QrScanner;

public class ClickedAttendance extends BaseActivity {

    public static final String EXTRA_ID = "com.nicanoritorma.qrattendance.EXTRA_ID";
    public static final String EXTRA_ATTENDANCE_NAME = "com.nicanoritorma.qrattendance.EXTRA_ATTENDANCE_NAME";
    public static final String EXTRA_DETAILS = "com.nicanoritorma.qrattendance.EXTRA_DETAILS";
    public static final String EXTRA_DATE = "com.nicanoritorma.qrattendance.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.nicanoritorma.qrattendance.EXTRA_TIME";
    private RecyclerView rv_studentsAdded;
    private FloatingActionButton fab_add;
    private StudentInAttendanceAdapter studentInAttendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_attendance);

        rv_studentsAdded = findViewById(R.id.rv_studentsInAttendance);
        fab_add = findViewById(R.id.fab_addStudent);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClickedAttendance.this, QrScanner.class));
            }
        });

        initUI();
    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();

        Intent intent = getIntent();

        if (ab != null) {
            if (intent.hasExtra(EXTRA_ATTENDANCE_NAME))
            {
                ab.setTitle(intent.getStringExtra(EXTRA_ATTENDANCE_NAME));
                ab.setSubtitle(intent.getStringExtra(EXTRA_DATE) + " " + intent.getStringExtra(EXTRA_TIME));
            }
        }

        rv_studentsAdded.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_studentsAdded.setHasFixedSize(true);
        studentInAttendanceAdapter = new StudentInAttendanceAdapter();
        rv_studentsAdded.setAdapter(studentInAttendanceAdapter);

    }
}