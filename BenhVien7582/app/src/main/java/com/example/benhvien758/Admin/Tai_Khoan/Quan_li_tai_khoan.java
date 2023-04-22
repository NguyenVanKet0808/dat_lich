package com.example.benhvien758.Admin.Tai_Khoan;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Admin.Khoa.Adapter_quan_li_khoa;
import com.example.benhvien758.Admin.Khoa.Quan_li_khoa;
import com.example.benhvien758.Admin.TrangChuAdmin;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quan_li_tai_khoan extends BaseActivity {
    private ImageView img_quay_lai, img_search;
    private EditText edt_search;
    private RecyclerView recyclerView;
    private Adapter_quan_li_tai_khoan adapter_quan_li_tai_khoan;
    private lang_nghe_item lang_nghe_item;
    Context context;



    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");

    @Override
    protected int getLayoutId() {
        return R.layout.quan_li_tai_khoan;
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data(lang_nghe_item,"");
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                load_data(lang_nghe_item,charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void load_data(lang_nghe_item lang_nghe_item,String key) {
        recyclerView.setHasFixedSize(true);
        ArrayList<User_khachhang_login> data = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (data != null) {         // nếu data rỗng thì không hiển thị gì cả
                    data.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {// với key rỗng thì nó load tất cả object lên
                    if (key.equals("")){
                        User_khachhang_login user_khachhang_login = dataSnapshot.getValue(User_khachhang_login.class);
                        data.add(0, user_khachhang_login);
                    }
                    else {
                        User_khachhang_login user_khachhang_login = dataSnapshot.getValue(User_khachhang_login.class);
                        if (user_khachhang_login.getName().contains(key)) {
                            data.add(0, user_khachhang_login);
                        }
                    }
                }

                adapter_quan_li_tai_khoan.notifyDataSetChanged();  // setadapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quan_li_tai_khoan.this, "loi r", Toast.LENGTH_SHORT).show();
            }
        });
        lang_nghe_item = new lang_nghe_item() {
            @Override
            public void click_edt(String id_item) {
                load_bottomsheet(id_item);
            }
        };

        adapter_quan_li_tai_khoan = new Adapter_quan_li_tai_khoan(data);
        adapter_quan_li_tai_khoan.lang_nghe_item = lang_nghe_item;
        recyclerView.setAdapter(adapter_quan_li_tai_khoan);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void load_bottomsheet(String id_item) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheetdialog_edt_vai_tro);
        AppCompatEditText edt_name = bottomSheetDialog.findViewById(R.id.edt_name_bottomsheetdialog);
        AppCompatEditText edt_email = bottomSheetDialog.findViewById(R.id.edt_email_bottomsheetdialog);
        AppCompatEditText edit_phone = bottomSheetDialog.findViewById(R.id.edt_phone_bottomsheetdialog);
        AppCompatEditText edt_diachi = bottomSheetDialog.findViewById(R.id.edt_diachi_bottomsheetdialog);
        AppCompatEditText edt_cmnd = bottomSheetDialog.findViewById(R.id.edt_cmnd_bottomsheetdialog);
        AutoCompleteTextView edt_ngaysinh = bottomSheetDialog.findViewById(R.id.edt_ngaysinh_bottomsheetdialog);
        AutoCompleteTextView edt_gioitinh = bottomSheetDialog.findViewById(R.id.edt_gioitinh_bottomsheetdialog);
        AutoCompleteTextView edt_vaitro = bottomSheetDialog.findViewById(R.id.edt_vaitro_bottomsheetdialog);
        AutoCompleteTextView edt_khoa = bottomSheetDialog.findViewById(R.id.edt_khoa_bottomsheetdialog);
        Button btn_update = bottomSheetDialog.findViewById(R.id.btn_update_bottomsheetdialog);
        myRef.child(id_item).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User_khachhang_login user = snapshot.getValue(User_khachhang_login.class);
                    edt_name.setText(user.getName());
                    edt_email.setText(user.getEmail());
                    edit_phone.setText(user.getPhone());
                    edt_diachi.setText(user.getDiachi());
                    edt_cmnd.setText(user.getCmnd());
                    edt_ngaysinh.setText(user.getNgaysinh());
                    edt_gioitinh.setText(user.getGioitinh());
                    edt_vaitro.setText(user.getVaitro());
                }
                loadvaitro(edt_vaitro,edt_khoa);

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        upload(edt_khoa,edt_vaitro,id_item);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomSheetDialog.show();

    }

    private void upload(AutoCompleteTextView edt_khoa,AutoCompleteTextView edt_vaitro, String id_item) {
        String vaitro = edt_vaitro.getText().toString().trim();
        String khoa = edt_khoa.getText().toString().trim();
        if(khoa.isEmpty()){
            Toast.makeText(Quan_li_tai_khoan.this, "vui lòng chọn đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            myRef.child(id_item).child("vaitro").setValue(vaitro, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (vaitro.contains("Bác Sĩ")) {
                        myRef_khoa.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Data_Khoa data_khoa = dataSnapshot.getValue(Data_Khoa.class);
                                    if (data_khoa.getTen_khoa().contains(khoa)) {
                                        String id = "";
                                        myRef.child(id_item).child("khoa").setValue(data_khoa.id_khoa, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                            }
                                        });
                                        String id_bacsi_khoa = "";
                                        Data_item_khoa item_khoa = new Data_item_khoa(id, id_bacsi_khoa);
                                        myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").push().setValue(item_khoa, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                if (error == null) {
                                                    myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").child(ref.getKey()).child("id_user").setValue(id_item);
                                                    myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").child(ref.getKey()).child("id_bacsi_khoa").setValue(ref.getKey());
                                                    Toast.makeText(Quan_li_tai_khoan.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                                                }
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
                    else {
                        Toast.makeText(Quan_li_tai_khoan.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void load_data_khoa(AutoCompleteTextView edt_khoa) {
        ArrayList<String> arrayList = new ArrayList<>();
        myRef_khoa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList != null){
                    arrayList.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data_Khoa data = dataSnapshot.getValue(Data_Khoa.class);
                    arrayList.add(data.getTen_khoa());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        edt_khoa.setAdapter(arrayAdapter);

    }

    private void loadvaitro(AutoCompleteTextView edt_vaitro,AutoCompleteTextView edt_khoa ) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Khách Hàng");
        arrayList.add("Bác Sĩ");
        arrayList.add("Admin");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        edt_vaitro.setAdapter(arrayAdapter);
        edt_vaitro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (charSequence.toString().contains("Khách Hàng")){
                   return;
               }
               else {
                   load_data_khoa(edt_khoa);
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        img_quay_lai = findViewById(R.id.img_quay_lai_admin_quan_li_tai_khoan);
        img_search = findViewById(R.id.img_search_quan_li_tai_khoan);
        edt_search = findViewById(R.id.edt_search_quan_li_tai_khoan);
        recyclerView = findViewById(R.id.recylerview_quan_li_tai_khoan);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_quay_lai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quan_li_tai_khoan.this, TrangChuAdmin.class);
                startActivity(intent);
            }
        });
    }
}
