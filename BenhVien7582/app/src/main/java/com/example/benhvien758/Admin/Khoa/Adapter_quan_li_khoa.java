package com.example.benhvien758.Admin.Khoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Henlich.KhachHang.DatLich.henlichkhAdapter;
import com.example.benhvien758.R;

import java.util.ArrayList;

public class Adapter_quan_li_khoa extends RecyclerView.Adapter<Adapter_quan_li_khoa.ViewHolder>{

    private ArrayList<Data_Khoa> data_khoas;

    public Adapter_quan_li_khoa(ArrayList<Data_Khoa> data_khoas) {
        this.data_khoas = data_khoas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.danh_sach_khoa,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_ten_khoa.setText(data_khoas.get(position).getTen_khoa());
    }

    @Override
    public int getItemCount() {
        return data_khoas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_ten_khoa, txt_truong_khoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ten_khoa = itemView.findViewById(R.id.txt_ten_khoa);
        }
    }
}
