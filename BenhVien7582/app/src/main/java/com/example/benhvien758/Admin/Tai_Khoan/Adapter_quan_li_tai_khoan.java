package com.example.benhvien758.Admin.Tai_Khoan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Admin.Khoa.Adapter_quan_li_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;

import java.util.ArrayList;

public class Adapter_quan_li_tai_khoan extends RecyclerView.Adapter<Adapter_quan_li_tai_khoan.ViewHolder>{
    private ArrayList<User_khachhang_login> data;
    lang_nghe_item lang_nghe_item;

    public Adapter_quan_li_tai_khoan(ArrayList<User_khachhang_login> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.danh_sach_tai_khoan,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User_khachhang_login user = data.get(position);
        holder.txt_vaitro.setText(user.getVaitro());
        holder.txt_email.setText(user.getEmail());
        holder.txt_ten.setText(user.getName());
        Glide.with(holder.img_avt).load(user.getHinhanh()).into(holder.img_avt);
        holder.img_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang_nghe_item.click_edt(user.getId_user());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_avt, img_sua;
        TextView txt_ten,txt_email,txt_vaitro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avt = itemView.findViewById(R.id.img_avt_tai_khoan);
            img_sua =itemView.findViewById(R.id.img_edt_tai_khoan);
            txt_email = itemView.findViewById(R.id.txt_email_tai_khoan);
            txt_ten = itemView.findViewById(R.id.txt_ten_tai_khoan);
            txt_vaitro = itemView.findViewById(R.id.txt_vai_tro_tai_khoan);
        }
    }
}
