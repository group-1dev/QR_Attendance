package com.nicanoritorma.qrattendance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceAdapterVH> {

    private List<AttendanceModel> attendanceList = new ArrayList<>();

    public static class AttendanceAdapterVH extends RecyclerView.ViewHolder {

        private TextView tv_heading1, tv_heading2, tv_heading3;

        public AttendanceAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            tv_heading3 = itemView.findViewById(R.id.tv_heading3);
        }
    }

    public void setList(List<AttendanceModel> attendanceList)
    {
        this.attendanceList = attendanceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttendanceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new AttendanceAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapterVH holder, int position) {
        AttendanceModel attendanceModel = attendanceList.get(position);
        if (!attendanceModel.getAttendanceName().isEmpty())
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(attendanceModel.getAttendanceName());
        }
        if (!attendanceModel.getDetails().isEmpty())
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(attendanceModel.getDetails());
        }
        if ((!attendanceModel.getDate().isEmpty()) || (!attendanceModel.getTime().isEmpty()))
        {
            holder.tv_heading3.setVisibility(View.VISIBLE);
            holder.tv_heading3.setText(attendanceModel.getDate() + " " + attendanceModel.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}
