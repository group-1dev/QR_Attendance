package com.nicanoritorma.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nicanoritorma.qrattendance.OfflineViewModels.StudentInAttendanceVM;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;
import com.nicanoritorma.qrattendance.ui.adapter.StudentInAttendanceAdapter;
import com.nicanoritorma.qrattendance.utils.QrScanner;

import java.util.Calendar;
import java.util.List;

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
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_attendance);

        rv_studentsAdded = findViewById(R.id.rv_studentsInAttendance);
        fab_add = findViewById(R.id.fab_addStudent);

        intent = getIntent();
        mFragmentManager = getSupportFragmentManager();
        initUI();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_add.setVisibility(View.GONE);

                int itemId = intent.getIntExtra("ITEM_ID", 0);

                Bundle bundle = new Bundle();
                bundle.putInt("EXTRA_ID", itemId);
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.addToBackStack("Scanner");
                ft.add(R.id.scannerFragment, QrScanner.class, bundle);
                ft.commit();
            }
        });
    }

    private void initUI()
    {
        fab_add.setVisibility(View.VISIBLE);
        ActionBar ab = getSupportActionBar();

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

        StudentInAttendanceVM studentVM = new StudentInAttendanceVM(getApplication());
        studentVM.getStudentList(intent.getIntExtra("ITEM_ID", 0)).observe(this, new Observer<List<StudentInAttendanceModel>>() {
            @Override
            public void onChanged(List<StudentInAttendanceModel> studentInAttendanceModels) {
                studentInAttendanceAdapter.setList(studentInAttendanceModels);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem changeDT = menu.findItem(R.id.menu_ChangeDT);
        changeDT.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_selectAll:
                Log.d("onOptionsItemSelected: ", "menu_selectAll is clicked");
                return true;
            case R.id.menu_ChangeDT:
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Date picker
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            //change date on attendance repository

            //start time picker
            DialogFragment timeFragment = new TimePickerFragment();
            timeFragment.show(requireActivity().getSupportFragmentManager(), "timePicker");
        }
    }

    //time picker
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            //change time on repository of attendance
        }
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