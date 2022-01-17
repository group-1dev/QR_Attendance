package com.nicanoritorma.qrattendance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Recyclerview of ClickedAttendance Activity
 */
public class StudentInAttendanceAdapter extends RecyclerView.Adapter<StudentInAttendanceAdapter.StudentInAttendanceVH> {

    private List<StudentInAttendanceModel> studentsAdded = new ArrayList<>();

    public static class StudentInAttendanceVH extends RecyclerView.ViewHolder
    {
        TextView tv_heading1, tv_heading2;

        public StudentInAttendanceVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
        }
    }

    public void setList(List<StudentInAttendanceModel> studentsAdded)
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
        StudentInAttendanceModel student = studentsAdded.get(position);
        if (student.getFullname().length() > 0)
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(student.getFullname());
        }
        if (student.getIdNum().length() > 0)
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(student.getIdNum());
        }
    }

    @Override
    public int getItemCount() {
        return studentsAdded.size();
    }
}