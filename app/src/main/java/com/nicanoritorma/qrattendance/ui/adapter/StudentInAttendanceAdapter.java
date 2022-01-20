package com.nicanoritorma.qrattendance.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    private List<StudentInAttendanceModel> selectedStudents = new ArrayList<>();
    private OnItemLongClick onItemLongClick;

    public void selectAll()
    {
        Log.d("studentsAdded: ", String.valueOf(studentsAdded.size()));
        selectedStudents.clear();
        selectedStudents.addAll(studentsAdded);
        Log.d("selectAll: ", String.valueOf(selectedStudents.size()));
        notifyDataSetChanged();
    }

    public interface OnItemLongClick{
        void onItemLongClick(int position);
    }

    public static class StudentInAttendanceVH extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        TextView tv_heading1, tv_heading2;
        RelativeLayout item;
        OnItemLongClick onItemLongClick;

        public StudentInAttendanceVH(@NonNull View itemView, OnItemLongClick onItemLongClick) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            item = itemView.findViewById(R.id.item_relative_layout);
            this.onItemLongClick = onItemLongClick;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClick != null && getAdapterPosition() != RecyclerView.NO_POSITION)
            {
                onItemLongClick.onItemLongClick(getAdapterPosition());
                item.setBackgroundResource(R.drawable.selected_item_border);
            }
            return true;
        }
    }

    public void setList(List<StudentInAttendanceModel> studentsAdded, OnItemLongClick onItemLongClick)
    {
        this.studentsAdded = studentsAdded;
        this.onItemLongClick = onItemLongClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentInAttendanceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new StudentInAttendanceVH(v, onItemLongClick);
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