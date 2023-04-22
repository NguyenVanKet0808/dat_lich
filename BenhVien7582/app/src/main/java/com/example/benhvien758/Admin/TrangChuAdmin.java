package com.example.benhvien758.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.benhvien758.Admin.Chat.Chat_Admin;
import com.example.benhvien758.Admin.Khoa.Quan_li_khoa;
import com.example.benhvien758.Admin.Tai_Khoan.Quan_li_tai_khoan;
import com.example.benhvien758.Admin.Tracuu.Tra_cuu_admin;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.Login.KhachHang.Login_khachhang;
import com.example.benhvien758.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrangChuAdmin extends BaseActivity {


    private Button btn_quan_li_khoa,btn_quan_li_tai_khoan,btn_quan_li_ho_tro,btn_thoat,btn_tim_kiem_ho_so;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");
    private String khoa;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        btn_quan_li_khoa = findViewById(R.id.btn_quan_li_khoa);
        btn_quan_li_tai_khoan = findViewById(R.id.btn_quan_li_tai_khoan);
        btn_quan_li_ho_tro = findViewById(R.id.btn_quan_li_ho_tro);
        btn_tim_kiem_ho_so = findViewById(R.id.btn_tim_kiem_ho_so_benh_an);
        btn_thoat = findViewById(R.id.btn_thoat_admin);


    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        btn_quan_li_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuAdmin.this, Quan_li_khoa.class);
                startActivity(intent);
            }
        });
        btn_quan_li_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuAdmin.this, Quan_li_tai_khoan.class);
                startActivity(intent);
            }
        });
        btn_quan_li_ho_tro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuAdmin.this, Chat_Admin.class);
                startActivity(intent);
            }
        });
        btn_tim_kiem_ho_so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuAdmin.this, Tra_cuu_admin.class);
                startActivity(intent);
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });



    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(TrangChuAdmin.this, Login_khachhang.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        TrangChuAdmin.this.finish();
    }



}
