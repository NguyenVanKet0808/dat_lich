package com.example.benhvien758.Bacsi.Home;
import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.GiaoDien.KhachHang.Fragment_home_kh;
import com.example.benhvien758.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.Calendar;

public class Fragment_tra_ket_qua extends BaseFragment{
    private String id_item,ngay_kham,thoi_gian;
    private TextInputEditText tv_ten_benh_nhan,tv_ten_bs,tv_ngay_kham,tv_chuan_doan;
    private ImageView img_anh,img_thoat,img_xac_nhan;
    private Uri mUrl,mUrl_data;
    private String id_khoa,id_item_khoa,id_benh_nhan;
    private ProgressDialog progressDialog;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference().child("Anh Ket Qua");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tra_ket_qua;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        Bundle args = getArguments();
        id_item = args.getString("id_item");
        ngay_kham = args.getString("ngay_kham");
        thoi_gian = args.getString("thoigian");
        progressDialog = new ProgressDialog(getActivity());
        tv_ten_benh_nhan = requireView().findViewById(R.id.tedt_ten_benh_nhan_tra_kq);
        tv_ten_bs = requireView().findViewById(R.id.tedt_ten_bac_si_tra_kq);
        tv_ngay_kham = requireView().findViewById(R.id.tedt_ngay_kham_tra_kq);
        tv_chuan_doan = requireView().findViewById(R.id.tedt_chuan_doan_tra_kq);
        img_anh = requireView().findViewById(R.id.img_anh_tra_kq);
        img_thoat = requireView().findViewById(R.id.img_thoat_tra_kq);
        img_xac_nhan = requireView().findViewById(R.id.img_xacnhan_tra_kq);
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data();

    }

    private void load_data() {
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                tv_ten_bs.setText(user_khachhang_login.getName());
                tv_ngay_kham.setText(ngay_kham);
                id_khoa = user_khachhang_login.getKhoa();
                myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                            if (data_item_khoa.getId_user().contains(user.getUid())) {
                                id_item_khoa =data_item_khoa.getId_bacsi_khoa();
                                myRef_khoa.child(user_khachhang_login.getKhoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                        .child("lich kham").child(ngay_kham).child(thoi_gian).child(id_item).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    Data_dat_lich_kham data_dat_lich_kham = snapshot.getValue(Data_dat_lich_kham.class);
                                                    if(data_dat_lich_kham.getTrang_thai().contains("Đã Trả Kết Quả")) {
                                                        Glide.with(img_anh).load(data_dat_lich_kham.getKet_qua()).into(img_anh);
                                                        tv_chuan_doan.setText(data_dat_lich_kham.getChuan_doan());
                                                    }
                                                    myRef.child(data_dat_lich_kham.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                User_khachhang_login user_khachhang = snapshot.getValue(User_khachhang_login.class);
                                                                id_benh_nhan = user_khachhang.getId_user();
                                                                tv_ten_benh_nhan.setText(user_khachhang.getName());
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
    }

    private void xoa_lich_kham(User_khachhang_login user_khachhang, String kq) {
        myRef.child(user_khachhang.getId_user()).child("lich kham").child(ngay_kham).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_dat_lich_kh data_dat_lich_kh = snapshot.getValue(Data_dat_lich_kh.class);
                myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).setValue(data_dat_lich_kh, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
                        myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).child("ket_qua").setValue(kq);
                        myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).child("chuan_doan").setValue(tv_chuan_doan.getText().toString().trim());

//                        myRef.child(user_khachhang.getId_user()).child("lich kham").child(ngay_kham).removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Home_bs());
            }
        });
        img_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_anh();
            }
        });
        img_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                xac_nhan();
            }
        });


    }

    private void xac_nhan() {
        String chuandoan = tv_chuan_doan.getText().toString().trim();
        if (chuandoan.isEmpty()){
            Toast.makeText(getContext(), "Chưa cập nhật chuẩn đoán bệnh !", Toast.LENGTH_SHORT).show();
        }
        else {
            if(mUrl == null){
                Toast.makeText(getContext(), "Chưa cập nhật ảnh kết quả !!", Toast.LENGTH_SHORT).show();
            }
            else {
                kiemtra();
            }
        }
    }

    private void kiemtra() {
        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis()+".png");
        // Get the data from an ImageView as bytes
        img_anh.setDrawingCacheEnabled(true);
        img_anh.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_anh.getDrawable()).getBitmap();
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
                    update();
                } else {
                }
            }
        });
    }

    private void update() {

        String kq = String.valueOf(mUrl_data);

        myRef_khoa.child(id_khoa).child("bac si").child(id_item_khoa).child("lich kham")
                .child(ngay_kham).child(thoi_gian).child(id_item).child("ket_qua").setValue(kq, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        myRef_khoa.child(id_khoa).child("bac si").child(id_item_khoa).child("lich kham")
                                .child(ngay_kham).child(thoi_gian).child(id_item).child("chuan_doan").setValue(tv_chuan_doan.getText().toString().trim(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        myRef_khoa.child(id_khoa).child("bac si").child(id_item_khoa).child("lich kham")
                                                .child(ngay_kham).child(thoi_gian).child(id_item).child("trang_thai").setValue("Đã Trả Kết Quả", new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        Toast.makeText(getActivity(), "Up date thành công", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        myRef.child(id_benh_nhan).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                User_khachhang_login user_khachhang = snapshot.getValue(User_khachhang_login.class);
                                                                myRef.child(user_khachhang.getId_user()).child("lich kham").child(ngay_kham).addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        Data_dat_lich_kh data_dat_lich_kh = snapshot.getValue(Data_dat_lich_kh.class);
                                                                        if (data_dat_lich_kh == null ){
                                                                            return;
                                                                        }
                                                                        myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).setValue(data_dat_lich_kh, new DatabaseReference.CompletionListener() {
                                                                            @Override
                                                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                                                Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
                                                                                myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).child("ket_qua").setValue(kq);
                                                                                myRef.child(user_khachhang.getId_user()).child("lich su").child(ngay_kham).child("chuan_doan").setValue(tv_chuan_doan.getText().toString().trim());
                                                                                myRef.child(user_khachhang.getId_user()).child("lich kham").child(ngay_kham).removeValue();

                                                                            }
                                                                        });
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
                                                    }
                                                });
                                    }
                                });
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
                    .into(img_anh);
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_bac_si, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
