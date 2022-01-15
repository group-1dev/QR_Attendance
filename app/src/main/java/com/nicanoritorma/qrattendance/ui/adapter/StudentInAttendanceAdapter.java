package com.nicanoritorma.qrattendance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Recyclerview of ClickedAttendance Activity
 */
public class StudentInAttendanceAdapter extends RecyclerView.Adapter<StudentInAttendanceAdapter.StudentInAttendanceVH> {

    private List<StudentModel> studentsAdded = new ArrayList<>();

    public static class StudentInAttendanceVH extends RecyclerView.ViewHolder
    {
        TextView tv_heading1, tv_heading2, tv_heading3;

        public StudentInAttendanceVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            tv_heading3 = itemView.findViewById(R.id.tv_heading3);
        }
    }

    public void setList(List<StudentModel> studentsAdded)
    {
        this.studentsAdded = studentsAdded;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentInAttendanceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new StudentInAttendanceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentInAttendanceVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return studentsAdded.size();
    }
}