package com.example.benhvien758.Login.KhachHang;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benhvien758.Admin.TrangChuAdmin;
import com.example.benhvien758.Bacsi.Trang_Chinh_Bac_Si;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.example.benhvien758.TrangChinh.KhachHang.TrangChinh_KhachHang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_khachhang extends BaseActivity {
    private EditText edt_name,edt_password;
    private Button btn_login,btn_taotaikhoan;
    private TextView tv_quen_mk;
    private String name, password;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private User_khachhang_login user_khachhang_login;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_khachhang;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        edt_name = findViewById(R.id.edt_name);
        edt_password = findViewById(R.id.edt_passwod);
        btn_login = findViewById(R.id.btn_login);
        btn_taotaikhoan = findViewById(R.id.btn_dangky);
        tv_quen_mk = findViewById(R.id.tv_qmk);
        pd = new ProgressDialog(this);


    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkvaitro(user.getEmail());
        } else {
            return;
        }
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        kiemtra();
        btn_taotaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Login_khachhang.this, taotaikhoan_khachhang.class);
                startActivity(intent);
            }
        });
        tv_quen_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_khachhang.this,Quen_mk.class);
                startActivity(intent);
            }
        });

    }

    private void kiemtra() {
        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    btn_login.setBackgroundDrawable(getResources().getDrawable(R.drawable.custombutton));

                }
                else {
                    btn_login.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button1));
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pd.setMessage("Vui lòng chờ ít phút");
                            pd.show();
                            name = edt_name.getText().toString().trim();
                            password = edt_password.getText().toString().trim();
                            checktaikhoan(name,password);
                        }
                    });
                }

            }
        });

    }

    private void checktaikhoan(String email,String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
//
                           checkvaitro(email);
                        } else {
                            pd.dismiss();
                            Toast.makeText(Login_khachhang.this, R.string.check_taikhoan, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkvaitro(String email) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user_khachhang_login = dataSnapshot.getValue(User_khachhang_login.class);
                    if (email.equals(user_khachhang_login.getEmail()) && user_khachhang_login.getVaitro().contains("Khách Hàng")) {
                        Intent intent = new Intent(Login_khachhang.this, TrangChinh_KhachHang.class);
                        startActivity(intent);
                    }
                    if (email.equals(user_khachhang_login.getEmail()) && user_khachhang_login.getVaitro().contains("Admin")) {
                        Intent intent = new Intent(Login_khachhang.this, TrangChuAdmin.class);
                        startActivity(intent);
                    }
                    if (email.equals(user_khachhang_login.getEmail()) && user_khachhang_login.getVaitro().contains("Bác Sĩ")) {
                        Intent intent = new Intent(Login_khachhang.this, Trang_Chinh_Bac_Si.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}