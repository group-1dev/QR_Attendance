package com.nicanoritorma.qrattendance.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.common.util.concurrent.ListenableFuture;
import com.nicanoritorma.qrattendance.R;

import java.util.concurrent.ExecutionException;

public class QrScanner extends Fragment {

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final String TAG = "QRSCANNER";
    private ListenableFuture<ProcessCameraProvider> cameraSource;
    private PreviewView previewView;
    private ImageView iv_border;

    public QrScanner()
    {
        super(R.layout.activity_qr_scanner);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.cameraSource = ProcessCameraProvider.getInstance(this.requireContext());
        this.cameraSource.addListener(() -> {
            Log.d(TAG, "cameraProviderFuture.Listener");
            try {
                ProcessCameraProvider cameraProvider = cameraSource.get();
                startCamera(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                Log.e(TAG, "cameraProviderFuture.Listener", e);
            }
        }, ContextCompat.getMainExecutor(this.requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qr_scanner, container, false);
        previewView = view.findViewById(R.id.cameraPreview);
        iv_border = view.findViewById(R.id.iv_border);
        return view;
    }

    private void startCamera(ProcessCameraProvider cameraProvider)
    {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        iv_border.setVisibility(View.VISIBLE);
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
    }

    @Override
    public void onResume() {
        super.onResume();

        if( ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {
            if( this.cameraSource == null ) {
                try {
                    startCamera(cameraSource.get());
                } catch (ExecutionException  | InterruptedException e) {
                    e.printStackTrace();
                }
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
                        try {
                            startCamera(cameraSource.get());
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}