package com.nicanoritorma.qrattendance;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends BaseActivity {

    private GridLayout drop_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drop_down = findViewById(R.id.drop_down_gridView);
        drop_down.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }

    public void expandDropDown(View view) {
        int drop = (drop_down.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(drop_down, new AutoTransition());
        drop_down.setVisibility(drop);
    }

    public void btn_createQr(View view) {
        startActivity(new Intent(this, CreateQr.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void btn_attendanceList(View view) {
        startActivity(new Intent(this, AttendanceList.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void btn_generatedQr(View view) {
        startActivity(new Intent(this, GeneratedQr.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void newAttendance(View view) {
        startActivity(new Intent(this, NewAttendance.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}