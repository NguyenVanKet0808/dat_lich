package com.example.benhvien758.Login.KhachHang;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class taotaikhoan_khachhang extends BaseActivity {
    private EditText edt_name,edt_password;
    private Button btn_dangky;
    private ImageView tv_back;
    private String name, password;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_taotaikhoan_khachhang;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        tv_back = findViewById(R.id.tv_back);
        edt_name = findViewById(R.id.edt_name);
        edt_password = findViewById(R.id.edt_passwod);
        btn_dangky = findViewById(R.id.btn_dangky);
        pd = new ProgressDialog(this);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        kiemtra();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(taotaikhoan_khachhang.this, Login_khachhang.class);
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
                    btn_dangky.setBackgroundDrawable(getResources().getDrawable(R.drawable.custombutton));

                }
                else {
                    btn_dangky.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button1));
                    btn_dangky.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pd.setMessage("Vui lòng chờ ít phút");
                            pd.show();
                            name = edt_name.getText().toString().trim();
                            password = edt_password.getText().toString().trim();
                            taotaikhoan(name,password);
                        }
                    });
                }

            }
        });

    }
    private void taotaikhoan(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(taotaikhoan_khachhang.this, Activity_Capnhatthongtin1.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(taotaikhoan_khachhang.this, "Tài khoản đã tồn tại vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}