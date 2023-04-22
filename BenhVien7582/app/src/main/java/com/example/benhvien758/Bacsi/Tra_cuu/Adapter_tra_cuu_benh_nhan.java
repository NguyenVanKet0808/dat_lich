package com.example.benhvien758.Bacsi.Tra_cuu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.R;

import java.util.ArrayList;

public class Adapter_tra_cuu_benh_nhan extends RecyclerView.Adapter<Adapter_tra_cuu_benh_nhan.ViewHolder>{
    ArrayList<Data_dat_lich_kh> data_dat_lich_khs;
    Context context;

    public Adapter_tra_cuu_benh_nhan(ArrayList<Data_dat_lich_kh> data_dat_lich_khs) {
        this.data_dat_lich_khs = data_dat_lich_khs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_tra_cuu_benh_nhan_bs,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data_dat_lich_kh data = data_dat_lich_khs.get(position);
        holder.tv_chuan_doan.setText(data.getChuan_doan());
        holder.tv_ngay_kham.setText(data.getNgayhen());
        holder.tv_ten_bs.setText(data.getTen_bac_si());
        Glide.with(holder.img_kq).load(data.getKet_qua()).into(holder.img_kq);
    }

    @Override
    public int getItemCount() {
        return data_dat_lich_khs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_ten_bs,tv_ngay_kham,tv_chuan_doan;
        private ImageView img_kq;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_bs = itemView.findViewById(R.id.tv_name_bs_tra_cuu_benh_nhan_bs);
            tv_ngay_kham = itemView.findViewById(R.id.tv_ngay_kham_tra_cuu_benh_nhan_bs);
            tv_chuan_doan = itemView.findViewById(R.id.tv_chuan_doan_tra_cuu_benh_nhan_bs);
            img_kq = itemView.findViewById(R.id.img_ket_qua_tra_cuu_benh_nhan_bs);
        }
    }
}
