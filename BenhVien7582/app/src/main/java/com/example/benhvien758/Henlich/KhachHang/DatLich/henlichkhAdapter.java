package com.example.benhvien758.Henlich.KhachHang.DatLich;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.R;

import java.util.ArrayList;

public class henlichkhAdapter extends RecyclerView.Adapter<henlichkhAdapter.ViewHolder> {
    ArrayList<Data_dat_lich_kh> data_dat_lich_khs;
    Context context;

    public henlichkhAdapter(ArrayList<Data_dat_lich_kh> data_dat_lich_khs, Context context) {
        this.data_dat_lich_khs = data_dat_lich_khs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.danhsachhenlich,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtngay.setText(data_dat_lich_khs.get(position).getNgayhen());
        holder.txttenbacsi.setText(data_dat_lich_khs.get(position).getTen_bac_si());
        holder.txtbuoi.setText(data_dat_lich_khs.get(position).getThoigian());
        holder.txthinhthuc.setText(data_dat_lich_khs.get(position).getHinhthuc());
        holder.txtkhoa.setText(data_dat_lich_khs.get(position).getKhoa());
    }

    @Override
    public int getItemCount() {
        return data_dat_lich_khs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbacsi,txtbuoi,txthinhthuc,txtkhoa,txtngay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenbacsi = itemView.findViewById(R.id.tenbasi);
            txtbuoi = itemView.findViewById(R.id.Buoi);
            txthinhthuc = itemView.findViewById(R.id.hinhthuc);
            txtkhoa = itemView.findViewById(R.id.Khoa);
            txtngay = itemView.findViewById(R.id.ngay);
        }
    }
}
