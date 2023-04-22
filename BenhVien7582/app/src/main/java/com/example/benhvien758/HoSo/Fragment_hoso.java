package com.example.benhvien758.HoSo;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.Henlich.KhachHang.DatLich.Fragment_DatLich;
import com.example.benhvien758.Login.KhachHang.Activity_Capnhatthongtin1;
import com.example.benhvien758.Login.KhachHang.Login_khachhang;
import com.example.benhvien758.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_hoso extends BaseFragment {

    private ImageView img_avt, img_camera;
    private Button btn_xac_nhan, btn_thoat;
    private Uri mUrl,mUrl_data;
    private AppCompatEditText edt_name,edt_email,edt_phone,edt_diachi,edt_cmnd;
    private AppCompatAutoCompleteTextView edt_ngaysinh,edt_gioitinh;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan").child(user.getUid());
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference().child("Anh Dai Dien");


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hoso;
    }
    @Override
    protected void anhXa() {
        super.anhXa();

        img_avt = requireActivity().findViewById(R.id.img_avt_trang_ca_nhan_kh);
        img_camera = requireActivity().findViewById(R.id.img_camera_trang_ca_nhan_kh);
        btn_xac_nhan = requireActivity().findViewById(R.id.btn_xac_nhan_trang_ca_nhan_kh);
        edt_name = requireActivity().findViewById(R.id.edt_name_trang_ca_nhan_kh);
        edt_email = requireActivity().findViewById(R.id.edt_email_trang_ca_nhan_kh);
        edt_phone = requireActivity().findViewById(R.id.edt_phone_trang_ca_nhan_kh);
        edt_diachi = requireActivity().findViewById(R.id.edt_diachi_trang_ca_nhan_kh);
        edt_cmnd = requireActivity().findViewById(R.id.edt_cmnd_trang_ca_nhan_kh);
        edt_ngaysinh = requireActivity().findViewById(R.id.edt_ngaysinh_trang_ca_nhan_kh);
        edt_gioitinh = requireActivity().findViewById(R.id.edt_gioitinh_trang_ca_nhan_kh);
        btn_xac_nhan = requireActivity().findViewById(R.id.btn_xac_nhan_trang_ca_nhan_kh);
        btn_thoat = requireActivity().findViewById(R.id.btn_thoat);

    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_anh();
            }
        });
        edt_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_ngay();
            }
        });
        edt_gioitinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGioitinh();
            }
        });


        btn_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemtra();
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void load_ngay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        edt_gioitinh.setAdapter(arrayAdapter);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), Login_khachhang.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void kiemtra() {
        String name,email,phone,diachi,cmnd,ngaysinh,gioitinh;
        name = edt_name.getText().toString();
        email = edt_email.getText().toString();
        phone = edt_phone.getText().toString();
        diachi = edt_diachi.getText().toString();
        ngaysinh = edt_ngaysinh.getText().toString();
        cmnd = edt_cmnd.getText().toString();
        gioitinh = edt_gioitinh.getText().toString();
        if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||diachi.isEmpty()||cmnd.isEmpty()||
                ngaysinh.isEmpty()||gioitinh.isEmpty()|| mUrl == null){
            Toast.makeText(getActivity(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Up hinh that bai", Toast.LENGTH_SHORT).show();
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

    private void updata(String name, String email, String phone, String diachi, String cmnd, String ngaysinh, String gioitinh) {
        String hinh = String.valueOf(mUrl_data);
        myRef.child("cmnd").setValue(cmnd, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("diachi").setValue(diachi, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("gioitinh").setValue(gioitinh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("hinhanh").setValue(hinh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("name").setValue(name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("ngaysinh").setValue(ngaysinh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        myRef.child("phone").setValue(phone, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            }
        });
        Toast.makeText(getActivity(), "Update thanh cong", Toast.LENGTH_SHORT).show();

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

    private void loadData() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                edt_name.setText(user_khachhang_login.getName());
                edt_email.setText(user_khachhang_login.getEmail());
                edt_phone.setText(user_khachhang_login.getPhone());
                edt_diachi.setText(user_khachhang_login.getDiachi());
                edt_cmnd.setText(user_khachhang_login.getCmnd());
                edt_ngaysinh.setText(user_khachhang_login.getNgaysinh());
                edt_gioitinh.setText(user_khachhang_login.getGioitinh());
                Glide.with(img_avt).load(user_khachhang_login.getHinhanh()).into(img_avt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        loadData();
    }

}