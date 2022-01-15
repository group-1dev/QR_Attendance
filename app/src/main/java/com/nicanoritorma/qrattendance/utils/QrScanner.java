package com.nicanoritorma.qrattendance.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.nicanoritorma.qrattendance.BaseActivity;
import com.nicanoritorma.qrattendance.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class QrScanner extends BaseActivity {

    private static final int REQUEST_PERMISSION_CAMERA = 1;
    private static final String TAG = "QRSCANNER";
    private ListenableFuture<ProcessCameraProvider> cameraSource;
    private PreviewView previewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        previewView = findViewById(R.id.cameraPreview);
        initUI();
    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("QR Scanner");
        }
    }


    private void startCamera()
    {
        this.cameraSource = ProcessCameraProvider.getInstance(this);
        this.cameraSource.addListener(() -> {
            Log.d(TAG, "cameraProviderFuture.Listener");
            try {
                ProcessCameraProvider cameraProvider = cameraSource.get();

                Preview preview = new Preview.Builder()
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Camera camera = cameraProvider.bindToLifecycle(this,
                        cameraSelector,
                        preview);

                cameraProvider.unbindAll();

                preview.setSurfaceProvider(this.previewView.getSurfaceProvider());

            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                Log.e(TAG, "cameraProviderFuture.Listener", e);
            }
        }, ContextCompat.getMainExecutor(this));


//        cameraProvider.unbindAll();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//        Preview preview = new Preview.Builder().build();
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
    }

    @Override
    public void onResume() {
        super.onResume();

        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {
            if( this.cameraSource == null ) {
                startCamera();
            }
        } else {
            this.requestPermissions(new String[] { Manifest.permission.CAMERA }, REQUEST_PERMISSION_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( requestCode == REQUEST_PERMISSION_CAMERA ) {
            for( int p = 0; p < permissions.length; p++ ) {
                if(Manifest.permission.CAMERA.equals(permissions[p]) ) {
                    if( grantResults[p] == PackageManager.PERMISSION_GRANTED ) {
                        startCamera();
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}