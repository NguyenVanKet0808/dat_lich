package com.example.benhvien758.Bacsi.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_item_lich_lam_home extends RecyclerView.Adapter<Adapter_item_lich_lam_home.ViewHolder>{
    private ArrayList<Data_dat_lich_kham> data_dat_lich_khams ;
    Context context;
    public Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");


    public Adapter_item_lich_lam_home(ArrayList<Data_dat_lich_kham> data_dat_lich_khams, Context context) {
        this.data_dat_lich_khams = data_dat_lich_khams;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_bacsi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data_dat_lich_kham data = data_dat_lich_khams.get(position);
        holder.tv_trangthai.setText(data.getTrang_thai());
        holder.tv_hinhthuc.setText(data.getHinh_thuc());
        myRef.child(data.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                holder.tv_tenkhachhang.setText(user_khachhang_login.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lang_nghe_item_benh_nhan_bs.click_item(data.getId_lich_kham());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_dat_lich_khams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tenkhachhang,tv_thoigian,tv_hinhthuc,tv_trangthai,tv_ngay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenkhachhang = itemView.findViewById(R.id.tv_tenkhachhang_home_bs);
            tv_hinhthuc = itemView.findViewById(R.id.tv_hinhthuc_home_bs);
            tv_trangthai = itemView.findViewById(R.id.tv_trang_thai_home_bs);
        }
    }
}
