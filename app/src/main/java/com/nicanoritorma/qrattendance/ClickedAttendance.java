package com.nicanoritorma.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.nicanoritorma.qrattendance.ui.adapter.StudentInAttendanceAdapter;
import com.nicanoritorma.qrattendance.utils.QrScanner;

import java.util.concurrent.ExecutionException;

public class ClickedAttendance extends BaseActivity {

    public static final String EXTRA_ID = "com.nicanoritorma.qrattendance.EXTRA_ID";
    public static final String EXTRA_ATTENDANCE_NAME = "com.nicanoritorma.qrattendance.EXTRA_ATTENDANCE_NAME";
    public static final String EXTRA_DETAILS = "com.nicanoritorma.qrattendance.EXTRA_DETAILS";
    public static final String EXTRA_DATE = "com.nicanoritorma.qrattendance.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.nicanoritorma.qrattendance.EXTRA_TIME";
    private RecyclerView rv_studentsAdded;
    private FloatingActionButton fab_add;
    private StudentInAttendanceAdapter studentInAttendanceAdapter;
    private FragmentManager mFragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_attendance);

        rv_studentsAdded = findViewById(R.id.rv_studentsInAttendance);
        fab_add = findViewById(R.id.fab_addStudent);

        initUI();


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_add.setVisibility(View.GONE);

                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.addToBackStack("Scanner");
                ft.add(R.id.scannerFragment, QrScanner.class, null);
                ft.commit();
            }
        });
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

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate();
            initUI();
        }
        else super.onBackPressed();
    }
}