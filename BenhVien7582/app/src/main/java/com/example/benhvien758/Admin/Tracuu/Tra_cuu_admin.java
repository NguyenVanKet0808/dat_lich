package com.example.benhvien758.Admin.Tracuu;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Bacsi.Tra_cuu.Adapter_tra_cuu_benh_nhan;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tra_cuu_admin extends BaseActivity {
    private EditText edt_search;
    private ImageView img_search,img_avt_benh_nhan;
    private TextView tv_name,tv_email,tv_gioitinh,tv_diachi,tv_phone,tv_thongbao,tv_thongbao_recylerview;
    private RecyclerView recyclerView_ketqua;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private Adapter_tra_cuu_benh_nhan adapter_tra_cuu_benh_nhan;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");

    @Override
    protected int getLayoutId() {
        return R.layout.activyti_tra_cuu;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        progressDialog = new ProgressDialog(this);
        edt_search = findViewById(R.id.edt_search_benh_nhan_admin);
        img_search = findViewById(R.id.img_search_benh_nhan_admin);
        img_avt_benh_nhan = findViewById(R.id.img_avt_search_benh_nhan_admin);
        tv_name = findViewById(R.id.tv_ten_benh_nhanh_search_admin);
        tv_email = findViewById(R.id.tv_email_benh_nhanh_search_admin);
        tv_gioitinh =findViewById(R.id.tv_gioi_tinh_benh_nhanh_search_admin);
        tv_diachi = findViewById(R.id.tv_dia_chi_benh_nhanh_search_admin);
        tv_phone = findViewById(R.id.tv_phone_benh_nhanh_search_admin);
        tv_thongbao = findViewById(R.id.tv_thongbao_search_benh_nhan_admin);
        tv_thongbao_recylerview = findViewById(R.id.tv_thongbao_recylerview_search_benh_nhan_admin);
        recyclerView_ketqua = findViewById(R.id.recylerview_ket_qua_search_benh_nhan_admin);
        linearLayout = findViewById(R.id.s1_admin);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = edt_search.getText().toString().trim();
                if (key.isEmpty()){
                    return;
                }
                else {
                    progressDialog.show();
                    search(key);
                }

            }
        });
    }

    private void search(String key) {
        if (key.isEmpty()){
            linearLayout.setVisibility(View.GONE);
            tv_thongbao.setVisibility(View.VISIBLE);
        }else {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean check = false;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User_khachhang_login user = dataSnapshot.getValue(User_khachhang_login.class);
                        if (user.getEmail().contains(key) && user.getVaitro().contains("Khách Hàng")){
                            check = true;
                            progressDialog.dismiss();
                            tv_name.setText(user.getName());
                            tv_email.setText(user.getEmail());
                            tv_diachi.setText(user.getDiachi());
                            tv_gioitinh.setText(user.getGioitinh());
                            tv_phone.setText(user.getPhone());
                            Glide.with(getApplicationContext()).load(user.getHinhanh()).into(img_avt_benh_nhan);
                            linearLayout.setVisibility(View.VISIBLE);
                            tv_thongbao.setVisibility(View.GONE);
                            load_recylerview(user.getId_user());
                        }
                    }
                    if (check == false){
                        progressDialog.dismiss();
                        Toast.makeText(Tra_cuu_admin.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void load_recylerview(String id_benh_nhan) {
        recyclerView_ketqua.setHasFixedSize(true);
        ArrayList<Data_dat_lich_kh> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview
        myRef.child(id_benh_nhan).child("lich su").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data_dat_lich_kh data_dat_lich_kh = dataSnapshot.getValue(Data_dat_lich_kh.class);
                    data.add(0,data_dat_lich_kh);
                }


                if (data.size()!=0){
                    recyclerView_ketqua.setVisibility(View.VISIBLE);
                    tv_thongbao_recylerview.setVisibility(View.GONE);
                }
                else {
                    recyclerView_ketqua.setVisibility(View.GONE);
                    tv_thongbao_recylerview.setVisibility(View.VISIBLE);
                }
                adapter_tra_cuu_benh_nhan.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter_tra_cuu_benh_nhan = new Adapter_tra_cuu_benh_nhan(data);
        recyclerView_ketqua.setAdapter(adapter_tra_cuu_benh_nhan);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Tra_cuu_admin.this,DividerItemDecoration.VERTICAL);
        recyclerView_ketqua.addItemDecoration(dividerItemDecoration);
    }
}
