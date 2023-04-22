package com.example.benhvien758.Login.KhachHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.example.benhvien758.TrangChinh.KhachHang.TrangChinh_KhachHang;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Capnhatthongtin1 extends BaseActivity {

    private AutoCompleteTextView edt_gioitinh,edt_ngaysinh;
    private AppCompatEditText edt_name,edt_email,edt_phone,edt_diachi,edt_cmnd;
    private Button btn_update;
    private ImageView img_avt,img_camera;
    private Uri mUrl,mUrl_data;
    private String name,email,phone,diachi,cmnd,ngaysinh,gioitinh;


    private FirebaseAuth mAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference().child("Anh Dai Dien");


    @Override
    protected int getLayoutId() {
        return R.layout.activity_capnhatthongtin1;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        edt_name =(AppCompatEditText) findViewById(R.id.edt_name);
        edt_email = (AppCompatEditText)findViewById(R.id.edt_email);
        edt_phone =(AppCompatEditText) findViewById(R.id.edt_phone);
        edt_diachi = (AppCompatEditText)findViewById(R.id.edt_diachi);
        edt_cmnd = (AppCompatEditText)findViewById(R.id.edt_cmnd);
        edt_gioitinh = (AutoCompleteTextView) findViewById(R.id.edt_gioitinh);
        edt_ngaysinh = (AutoCompleteTextView)findViewById(R.id.edt_ngaysinh);
        img_avt = (ImageView)findViewById(R.id.img_avt);
        img_camera = (ImageView)findViewById(R.id.img_camera);
        btn_update = findViewById(R.id.btn_update);


    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        updatedata();
        edt_gioitinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGioitinh();
            }
        });
        edt_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_ngay();
            }
        });
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_anh();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemtra();
            }
        });

    }

    private void load_anh() {
        Intent pickPhoto = new Intent();
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            mUrl = data.getData();
            Glide.with(this)
                    .load(mUrl)
                    .into(img_avt);
        }
    }

    private void load_ngay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt_ngaysinh.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void loadGioitinh() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Nam");
        arrayList.add("Nữ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        edt_gioitinh.setAdapter(arrayAdapter);
    }

    private void kiemtra() {
                name = edt_name.getText().toString();
                email = edt_email.getText().toString();
                phone = edt_phone.getText().toString();
                diachi = edt_diachi.getText().toString();
                ngaysinh = edt_ngaysinh.getText().toString();
                cmnd = edt_cmnd.getText().toString();
                gioitinh = edt_gioitinh.getText().toString();
                if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||diachi.isEmpty()||cmnd.isEmpty()||
                        ngaysinh.isEmpty()||gioitinh.isEmpty()|| mUrl == null){
                    Toast.makeText(Activity_Capnhatthongtin1.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    load_img(name, email, phone, diachi,cmnd,ngaysinh,gioitinh);

                }


    }

    private void load_img(String name, String email, String phone, String diachi, String cmnd, String ngaysinh, String gioitinh) {
        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis()+".png");
        // Get the data from an ImageView as bytes
        img_avt.setDrawingCacheEnabled(true);
        img_avt.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_avt.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Activity_Capnhatthongtin1.this, "Up hinh that bai", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        uploadTask = storageRef.putFile(mUrl);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mUrl_data = downloadUri;
                    updata(name,email,phone,diachi,cmnd,ngaysinh,gioitinh);
                } else {
                }
            }
        });
    }

    private void updata(String name, String email, String phone, String diachi,String cmnd,String ngaysinh, String gioitinh) {
        String hinh = String.valueOf(mUrl_data);

        User_khachhang_login user_khachhang_login = new User_khachhang_login(
                user.getUid(),name,email,phone,diachi,cmnd,ngaysinh,gioitinh, "Khách Hàng",hinh,"");

        myRef.child(user.getUid()).setValue(user_khachhang_login, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent =  new Intent(Activity_Capnhatthongtin1.this, TrangChinh_KhachHang.class);
                startActivity(intent);
            }
        });




    }
    private void updatedata(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(user == null){
        }
        else {
            edt_email.setText(user.getEmail());
        }
    }


}