package com.example.benhvien758.Bacsi.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.Login.KhachHang.Login_khachhang;
import com.example.benhvien758.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Home_bs extends BaseFragment {
    private TextView tv_ngay, tv_thongbao_sang,tv_thongbao_chieu;
    private ImageView tv_logout;
    private RecyclerView recyclerView_sang, recyclerView_chieu;
    private Adapter_item_lich_lam_home adapter_item_lich_lam_home_sang,adapter_item_lich_lam_home_chieu;
    private Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs;



    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_bs;
    }




    @Override
    protected void anhXa() {
        super.anhXa();
        tv_ngay = requireView().findViewById(R.id.tv_ngay_lam_bs);
        tv_logout = requireView().findViewById(R.id.img_logout);
        recyclerView_sang = requireView().findViewById(R.id.recylerview_sang_bs);
        recyclerView_chieu = requireView().findViewById(R.id.recylerview_chieu_bs);
        tv_thongbao_sang = requireView().findViewById(R.id.tv_thongbao_sang);
        tv_thongbao_chieu = requireView().findViewById(R.id.tv_thongbao_chieu);
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tv_ngay.append(simpleDateFormat.format(calendar.getTime()));
        load_recylerview_sang(lang_nghe_item_benh_nhan_bs);
        load_recylerview_chieu(lang_nghe_item_benh_nhan_bs);
    }

    private void load_recylerview_chieu(Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs) {
        recyclerView_chieu.setHasFixedSize(true);


        ArrayList<Data_dat_lich_kham> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

        String ngayhomnay = tv_ngay.getText().toString();
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                    myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                                if (data_item_khoa.getId_user().contains(user.getUid())) {
                                    myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                            .child("lich kham").child(ngayhomnay).child("Chiều").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (data!=null){
                                                        data.clear();
                                                    }
                                                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                                        Data_dat_lich_kham data_dat_lich_kham = dataSnapshot2.getValue(Data_dat_lich_kham.class);
                                                        data.add(0, data_dat_lich_kham);
                                                    }
                                                    if (data.isEmpty()){
                                                        recyclerView_chieu.setVisibility(View.GONE);
                                                        tv_thongbao_chieu.setVisibility(View.VISIBLE);
                                                    }
                                                    else {
                                                        recyclerView_chieu.setVisibility(View.VISIBLE);
                                                        tv_thongbao_chieu.setVisibility(View.GONE);
                                                    }
                                                    adapter_item_lich_lam_home_chieu.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lang_nghe_item_benh_nhan_bs = new Lang_nghe_item_benh_nhan_bs() {
            @Override
            public void click_item(String id_item) {
                loadFragment(new Fragment_tra_ket_qua(),id_item,ngayhomnay,"Chiều");
            }
        };
        adapter_item_lich_lam_home_chieu = new Adapter_item_lich_lam_home(data,getActivity());
        adapter_item_lich_lam_home_chieu.lang_nghe_item_benh_nhan_bs = lang_nghe_item_benh_nhan_bs;
        recyclerView_chieu.setAdapter(adapter_item_lich_lam_home_chieu);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView_chieu.addItemDecoration(dividerItemDecoration);
    }

    private void load_recylerview_sang(Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs) {
        recyclerView_sang.setHasFixedSize(true);


        ArrayList<Data_dat_lich_kham> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

        String ngayhomnay = tv_ngay.getText().toString();
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                            if (data_item_khoa.getId_user().contains(user.getUid())) {
                                myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                        .child("lich kham").child(ngayhomnay).child("Sáng").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(data!=null){
                                                    data.clear();
                                                }
                                                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                                    Data_dat_lich_kham data_dat_lich_kham = dataSnapshot2.getValue(Data_dat_lich_kham.class);
                                                    data.add(0, data_dat_lich_kham);
                                                }
                                                if (data.isEmpty()){
                                                    recyclerView_sang.setVisibility(View.GONE);
                                                    tv_thongbao_sang.setVisibility(View.VISIBLE);
                                                }
                                                else {
                                                    recyclerView_sang.setVisibility(View.VISIBLE);
                                                    tv_thongbao_sang.setVisibility(View.GONE);
                                                }

                                                adapter_item_lich_lam_home_sang.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lang_nghe_item_benh_nhan_bs = new Lang_nghe_item_benh_nhan_bs() {
            @Override
            public void click_item(String id_item) {
                loadFragment(new Fragment_tra_ket_qua(),id_item,ngayhomnay,"Sáng");
            }
        };


        adapter_item_lich_lam_home_sang = new Adapter_item_lich_lam_home(data,getActivity());
        adapter_item_lich_lam_home_sang.lang_nghe_item_benh_nhan_bs = lang_nghe_item_benh_nhan_bs;
        recyclerView_sang.setAdapter(adapter_item_lich_lam_home_sang);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView_sang.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), Login_khachhang.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    private void loadFragment(Fragment fragment,String id_item,String ngaykham,String thoigian) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle a = new Bundle();
        a.putString("id_item",id_item);
        a.putString("ngay_kham",ngaykham);
        a.putString("thoigian",thoigian);
        fragment.setArguments(a);
        transaction.replace(R.id.fragment_bac_si, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
