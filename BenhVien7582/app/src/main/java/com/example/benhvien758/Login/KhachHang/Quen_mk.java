package com.example.benhvien758.Login.KhachHang;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Quen_mk extends BaseActivity {
    private EditText edt_email;
    private Button btn_check;
    @Override
    protected int getLayoutId() {
        return R.layout.activyti_quen_mk;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        edt_email = findViewById(R.id.edt_nhap_email);
        btn_check = findViewById(R.id.btn_check_email);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                email = edt_email.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(Quen_mk.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = email;

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Quen_mk.this, "Vui lòng kiểm tra email của bạn", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Quen_mk.this, Login_khachhang.class);
                                        startActivity(i);
                                    }
                                    else {
                                        Toast.makeText(Quen_mk.this, "không tìm thấy thông tin", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
