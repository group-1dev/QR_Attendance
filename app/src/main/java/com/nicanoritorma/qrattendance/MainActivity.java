package com.nicanoritorma.qrattendance;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.GridLayout;

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
    }

    public void btn_attendanceList(View view) {
        startActivity(new Intent(this, AttendanceList.class));
    }

    public void btn_generatedQr(View view) {
        startActivity(new Intent(this, GeneratedQr.class));
    }

    public void btn_stats(View view) {
    }
}