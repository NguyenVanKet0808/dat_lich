package com.example.benhvien758.Bacsi.Lich_lam_viec;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Bacsi.Home.Adapter_item_lich_lam_home;
import com.example.benhvien758.Bacsi.Home.Fragment_tra_ket_qua;
import com.example.benhvien758.Bacsi.Home.Lang_nghe_item_benh_nhan_bs;
import com.example.benhvien758.Bacsi.Tra_cuu.Fragment_tra_cuu;
import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

public class lich_lam_viec extends BaseFragment {
    private CalendarView calendarView_xep_lich;
    private RecyclerView recyclerView_sang, recyclerView_chieu;
    private TextView tv_thongbao_sang, tv_thongbao_chieu;
    private Adapter_item_lich_lam_home adapter_item_lich_lam_home_sang,adapter_item_lich_lam_home_chieu;
    private Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lich_lam_viec;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        calendarView_xep_lich = requireView().findViewById(R.id.calender_truong_khoa);

    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        calendarView_xep_lich.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1+1) + "/" + i;
                Calendar calendar = Calendar.getInstance();
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date_data = simpleDateFormat.format(calendar.getTime());
                load_bottomsheet_lich_lam(date,date_data, lang_nghe_item_benh_nhan_bs);

            }
        });
    }

    private void load_bottomsheet_lich_lam(String date, String date_data, Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheetdialog_xep_lich);
        TextView tv_time = bottomSheetDialog.findViewById(R.id.tv_time);
        recyclerView_sang = bottomSheetDialog.findViewById(R.id.recylerview_danh_sach_sang);
        recyclerView_chieu = bottomSheetDialog.findViewById(R.id.recylerview_danh_sach_chieu);
        tv_thongbao_sang = bottomSheetDialog.findViewById(R.id.tv_thongbao_sang_lich_kham);
        tv_thongbao_chieu = bottomSheetDialog.findViewById(R.id.tv_thongbao_chieu_lich_kham);
        load_data_sang(recyclerView_sang,date_data,lang_nghe_item_benh_nhan_bs,bottomSheetDialog);
        load_data_chieu(recyclerView_chieu,date_data,lang_nghe_item_benh_nhan_bs,bottomSheetDialog);
        tv_time.setText(date);
        bottomSheetDialog.show();
    }

    private void load_data_chieu(RecyclerView recyclerView_chieu, String date_data, Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs,BottomSheetDialog bottomSheetDialog) {
        recyclerView_chieu.setHasFixedSize(true);


        ArrayList<Data_dat_lich_kham> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

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
                                        .child("lich kham").child(date_data).child("Chiều").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(data != null){
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
                loadFragment(new Fragment_tra_ket_qua(),id_item,date_data,"Chiều");
                bottomSheetDialog.dismiss();
            }
        };

        adapter_item_lich_lam_home_chieu = new Adapter_item_lich_lam_home(data,getActivity());
        adapter_item_lich_lam_home_chieu.lang_nghe_item_benh_nhan_bs = lang_nghe_item_benh_nhan_bs;
        recyclerView_chieu.setAdapter(adapter_item_lich_lam_home_chieu);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView_chieu.addItemDecoration(dividerItemDecoration);
    }

    private void load_data_sang(RecyclerView recyclerView_sang, String date_data, Lang_nghe_item_benh_nhan_bs lang_nghe_item_benh_nhan_bs,BottomSheetDialog bottomSheetDialog) {
        recyclerView_sang.setHasFixedSize(true);


        ArrayList<Data_dat_lich_kham> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

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
                                        .child("lich kham").child(date_data).child("Sáng").addValueEventListener(new ValueEventListener() {
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
                loadFragment(new Fragment_tra_ket_qua(),id_item,date_data,"Sáng");
                bottomSheetDialog.dismiss();
            }
        };

        adapter_item_lich_lam_home_sang = new Adapter_item_lich_lam_home(data,getActivity());
        adapter_item_lich_lam_home_sang.lang_nghe_item_benh_nhan_bs = lang_nghe_item_benh_nhan_bs;
        recyclerView_sang.setAdapter(adapter_item_lich_lam_home_sang);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView_sang.addItemDecoration(dividerItemDecoration);
    }
    private void loadFragment(Fragment fragment, String id_item, String ngaykham, String thoigian) {
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
