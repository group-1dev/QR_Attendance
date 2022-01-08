package com.nicanoritorma.qrattendance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceAdapterVH> {


    public static class AttendanceAdapterVH extends RecyclerView.ViewHolder {

        private TextView tv_heading1, tv_heading2;

        public AttendanceAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
        }
    }

    @NonNull
    @Override
    public AttendanceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new AttendanceAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapterVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
