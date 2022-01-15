package com.nicanoritorma.qrattendance;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.OfflineViewModels.QrViewModel;
import com.nicanoritorma.qrattendance.ui.adapter.StudentAdapter;
import com.nicanoritorma.qrattendance.OnlineViewModels.GeneratedQrViewModel;

import java.util.List;

public class GeneratedQr extends BaseActivity {

    private RecyclerView rv_generatedQr;
    private StudentAdapter studentAdapter;
    private GeneratedQrViewModel qrViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_qr);

        rv_generatedQr = findViewById(R.id.rv_generatedQr);
        initUI();
    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Generated QR Codes");

        rv_generatedQr.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_generatedQr.setHasFixedSize(true);
        studentAdapter = new StudentAdapter();
        rv_generatedQr.setAdapter(studentAdapter);

        //offline db
        QrViewModel qrViewModel = new QrViewModel(getApplication());
        qrViewModel.getAllStudent().observe(this, new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentAdapter.setList(studentModels);
            }
        });

//        //online db
//        qrViewModel = new GeneratedQrViewModel(getApplication());
//        qrViewModel.getStudentList().observe(this, new Observer<List<StudentModel>>() {
//            @Override
//            public void onChanged(List<StudentModel> studentModels) {
//                studentAdapter.setList(studentModels);
//            }
//        });
    }
}