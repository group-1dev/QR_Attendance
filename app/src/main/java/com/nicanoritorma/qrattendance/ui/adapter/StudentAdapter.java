package com.nicanoritorma.qrattendance.ui.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentAdapterVH> {

    private List<StudentModel> studentList = new ArrayList<>();

    public static class StudentAdapterVH extends RecyclerView.ViewHolder {

        private TextView tv_heading1, tv_heading2, tv_heading3;
        private ImageView iv_qrCode;

        public StudentAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            tv_heading3 = itemView.findViewById(R.id.tv_heading3);
            iv_qrCode = itemView.findViewById(R.id.iv_itemLayout);
        }
    }

    public void setList(List<StudentModel> studentList)
    {
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new StudentAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapterVH holder, int position) {
        StudentModel student = studentList.get(position);
        if (!student.getName().isEmpty())
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(student.getName());
        }
        if (!student.getIdNum().isEmpty())
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(student.getIdNum());
        }
        if (!student.getCollege().isEmpty())
        {
            holder.tv_heading3.setVisibility(View.VISIBLE);
            holder.tv_heading3.setText(student.getCollege());
        }
        if (!student.getQrCode().isEmpty())
        {
            holder.iv_qrCode.setVisibility(View.VISIBLE);
            String qr = student.getQrCode();
            byte[] qrCode = Base64.getDecoder().decode(qr);
            holder.iv_qrCode.setImageBitmap(
                    BitmapFactory.decodeByteArray
                            (qrCode, 0, qrCode.length));
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
