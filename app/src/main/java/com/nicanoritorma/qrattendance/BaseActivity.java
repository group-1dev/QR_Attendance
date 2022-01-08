package com.nicanoritorma.qrattendance;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nicanoritorma.qrattendance.api.RequestQueueSingleton;

import java.net.InetAddress;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    public static RequestQueueSingleton requestQueueSingleton;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.frame_layout);
        progressBar = findViewById(R.id.progressBar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(layoutResID);
    }

    public void showProgressBar(boolean visibility)
    {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public static String getDbUrl()
    {
        //TODO: change database address on production
        return "http://192.168.8.100/qr_atten_sys/";
    }

    public RequestQueueSingleton requestSingleton()
    {
        return requestQueueSingleton = RequestQueueSingleton.getInstance(getApplicationContext());
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            Log.d("isInternetAvailable: ", ipAddr.toString());
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
