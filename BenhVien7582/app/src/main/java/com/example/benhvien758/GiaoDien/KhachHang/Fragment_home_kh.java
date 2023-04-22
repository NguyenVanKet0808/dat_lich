package com.example.benhvien758.GiaoDien.KhachHang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Chat.Activyti_chat;
import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.Henlich.KhachHang.DatLich.Fragment_DatLich;
import com.example.benhvien758.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
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

public class Fragment_home_kh extends BaseFragment {

    private Button btn_dat_lich_home,btn_chat_home,btn_lich_hen_home,btn_lich_su_home;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_login = database.getReference("Taikhoan").child(user.getUid());
    private DatabaseReference myRef_khoa = database.getReference("Khoa");;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_kh;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        btn_dat_lich_home = requireActivity().findViewById(R.id.btn_dat_lich_kh);
        btn_chat_home = requireActivity().findViewById(R.id.btn_chat_kh);
        btn_lich_hen_home = requireActivity().findViewById(R.id.image_button_lich_kham);
        btn_lich_su_home = requireActivity().findViewById(R.id.image_button_lich_su_kham);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        btn_dat_lich_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datlich();
            }
        });
        btn_lich_hen_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_DatLich());
            }
        });
        btn_lich_su_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_lich_su_kham());
            }
        });
        btn_chat_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Activyti_chat.class);
                startActivity(intent);
            }
        });
    }

    private void datlich() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomshetdialog_dat_lich);
        AutoCompleteTextView auto_chon_khoa = bottomSheetDialog.findViewById(R.id.tv_chon_khoa);
        AutoCompleteTextView auto_chon_bac_si = bottomSheetDialog.findViewById(R.id.tv_chon_bac_si);
        AutoCompleteTextView auto_chon_thoi_gian = bottomSheetDialog.findViewById(R.id.tv_chon_thoi_gian);
        AutoCompleteTextView auto_chon_hinh_thuc = bottomSheetDialog.findViewById(R.id.tv_chon_hinh_thuc);
        TextInputEditText tedt_chon_ngay = bottomSheetDialog.findViewById(R.id.tv_chon_ngay);
        Button btn_add_lich_hen = bottomSheetDialog.findViewById(R.id.btn_bottomsheet_xac_nhan_add_lich_hen);
        load_chon_khoa(auto_chon_khoa, auto_chon_bac_si,auto_chon_thoi_gian,auto_chon_hinh_thuc,tedt_chon_ngay);
        btn_add_lich_hen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String khoa,bacsi,ngayhen,thoigian,hinhthuc,id_lich_hen;
                khoa = auto_chon_khoa.getText().toString().trim();
                bacsi = auto_chon_bac_si.getText().toString().trim();
                ngayhen = tedt_chon_ngay.getText().toString().trim();
                thoigian = auto_chon_thoi_gian.getText().toString().trim();
                hinhthuc = auto_chon_hinh_thuc.getText().toString().trim();
                if (khoa.isEmpty()|| bacsi.isEmpty()|| ngayhen.isEmpty()|| thoigian.isEmpty()|| hinhthuc.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng chọn đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot_user : snapshot.getChildren()) {
                                User_khachhang_login data_user = dataSnapshot_user.getValue(User_khachhang_login.class);
                                if (data_user.getName().contains(bacsi)) {
                                    String id_bac_si = data_user.getId_user();
                                    ktra(khoa, bacsi, ngayhen, thoigian, hinhthuc, id_bac_si);
                                    bottomSheetDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            bottomSheetDialog.dismiss();
                        }
                    });}
                }
        });
        bottomSheetDialog.show();
    }


    private void ktra(String khoa, String bacsi, String ngayhen, String thoigian, String hinhthuc,
                      String id_bac_si){
        myRef_khoa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data_Khoa data_khoa = dataSnapshot.getValue(Data_Khoa.class);
                    if (data_khoa.getTen_khoa().contains(khoa)){
                        myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                                    if (data_item_khoa.getId_user().contains(id_bac_si)) {
                                       myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                                .child("lich kham").child(ngayhen).child(thoigian).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                                                        ArrayList<Data_dat_lich_kham> arrayList = new ArrayList<>();
                                                        if (arrayList != null){
                                                            arrayList.clear();
                                                        }
                                                        for (DataSnapshot ktra : snapshot.getChildren()) {
                                                            Data_dat_lich_kham data_dat_lich_kham_ktra = ktra.getValue(Data_dat_lich_kham.class);
                                                            if (data_dat_lich_kham_ktra.getId_user().contains(user.getUid())) {
                                                                arrayList.add(data_dat_lich_kham_ktra);
                                                            }
                                                        }
                                                        if(arrayList.isEmpty()){
                                                            int dem = 0;
                                                            for (DataSnapshot ktra2 : snapshot.getChildren()){
                                                                Data_dat_lich_kham data_dat_lich_kham_ktra2 = ktra2.getValue(Data_dat_lich_kham.class);
                                                                if (data_dat_lich_kham_ktra2.getHinh_thuc().contains("Dịch Vụ")){
                                                                    dem = dem + 30;
                                                                }
                                                                if (data_dat_lich_kham_ktra2.getHinh_thuc().contains("Bình Thường")) {
                                                                    dem = dem + 20;
                                                                }
                                                            }
                                                            if (hinhthuc.contains("Dịch Vụ")){
                                                                dem = dem + 30;
                                                            }
                                                            if (hinhthuc.contains("Bình Thường")) {
                                                                dem = dem + 20;
                                                            }
                                                            if (dem <= 50){
                                                                update(khoa, bacsi, ngayhen, thoigian, hinhthuc, id_bac_si);
                                                            }
                                                            else {
                                                                Toast.makeText(getActivity(), "Hiện tại bác sĩ này đang full lịch", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), "Bạn đã có lịch hẹn với bác sĩ này ngày hôm nay", Toast.LENGTH_SHORT).show();
                                                        }

                                                        }
//                                                        else {
//                                                            Toast.makeText(getActivity(), "full", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void update(String khoa, String bacsi, String ngayhen, String thoigian, String hinhthuc,
                        String id_bac_si) {
        myRef_khoa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data_Khoa data_khoa = dataSnapshot.getValue(Data_Khoa.class);
                    if (data_khoa.getTen_khoa().contains(khoa)){
                        myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                                    if (data_item_khoa.getId_user().contains(id_bac_si)) {
                                        String id_lich_kham = "";
                                        String trangthai = "Đang Chờ Khám";
                                        String ketqua = "";
                                        Data_dat_lich_kham data_dat_lich_kham = new Data_dat_lich_kham(id_lich_kham,user.getUid(),hinhthuc,trangthai,ketqua,ngayhen,thoigian);
                                        myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                                .child("lich kham").child(ngayhen).child(thoigian).push().setValue(data_dat_lich_kham, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error == null) {
                                                            myRef_khoa.child(data_khoa.getId_khoa()).child("bac si").child(data_item_khoa.getId_bacsi_khoa())
                                                                    .child("lich kham").child(ngayhen).child(thoigian).child(ref.getKey()).child("id_lich_kham")
                                                                    .setValue(ref.getKey());
                                                        }else
                                                        {
                                                            Toast.makeText(getActivity(), "loi roi", Toast.LENGTH_SHORT).show();
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child(id_bac_si).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User_khachhang_login data = snapshot.getValue(User_khachhang_login.class);
                    String ten_bacsi = data.getName();
                    String trangthai = "Đang Chờ Khám";
                    String ketqua = "";
                    Data_dat_lich_kh data_dat_lich_kh = new Data_dat_lich_kh(khoa,ngayhen,hinhthuc,id_bac_si,thoigian,ten_bacsi,trangthai,ketqua);
                    myRef_login.child("lich kham").child(ngayhen).setValue(data_dat_lich_kh, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void load_chon_khoa(AutoCompleteTextView auto_chon_khoa, AutoCompleteTextView auto_chon_bac_si,
                                AutoCompleteTextView auto_chon_thoi_gian, AutoCompleteTextView auto_chon_hinh_thuc,
                                TextInputEditText tedt_chon_ngay) {
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        auto_chon_khoa.setAdapter(arrayAdapter);
        auto_chon_khoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String khoa = charSequence.toString().trim();
                load_chon_bac_si(auto_chon_bac_si, auto_chon_thoi_gian, auto_chon_hinh_thuc, tedt_chon_ngay, khoa);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void load_chon_bac_si(@NonNull AutoCompleteTextView auto_chon_bac_si, AutoCompleteTextView auto_chon_thoi_gian,
                                  AutoCompleteTextView auto_chon_hinh_thuc, TextInputEditText tedt_chon_ngay,
                                  String khoa) {

        ArrayList<String> arrayList1 = new ArrayList<>();
        myRef_khoa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data_Khoa data = dataSnapshot.getValue(Data_Khoa.class);
                    if (data.getTen_khoa().contains(khoa)){
                        myRef_khoa.child(data.getId_khoa()).child("bac si").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    Data_item_khoa data_item_khoa = dataSnapshot1.getValue(Data_item_khoa.class);
                                    myRef.child(data_item_khoa.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                User_khachhang_login data_user = snapshot.getValue(User_khachhang_login.class);
                                                arrayList1.add(data_user.getName());

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
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
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList1);
        auto_chon_bac_si.setAdapter(arrayAdapter2);
        auto_chon_bac_si.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                load_chon_ngay(auto_chon_hinh_thuc, auto_chon_thoi_gian, tedt_chon_ngay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void load_chon_ngay(AutoCompleteTextView auto_chon_hinh_thuc, AutoCompleteTextView auto_chon_thoi_gian,
                                TextInputEditText tedt_chon_ngay) {
        tedt_chon_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //i : nam    i1: thang   i2: ngay
                        calendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tedt_chon_ngay.setText(simpleDateFormat.format(calendar.getTime()));
                        load_chon_thoi_gian(auto_chon_thoi_gian,auto_chon_hinh_thuc);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


    }

    private void load_chon_thoi_gian(AutoCompleteTextView auto_chon_thoi_gian, AutoCompleteTextView auto_chon_hinh_thuc) {
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add("Sáng");
        arrayList3.add("Chiều");
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList3);
        auto_chon_thoi_gian.setAdapter(arrayAdapter3);
        auto_chon_thoi_gian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                load_chon_hinh_thuc(auto_chon_hinh_thuc);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void load_chon_hinh_thuc(AutoCompleteTextView auto_chon_hinh_thuc) {
        ArrayList<String> arrayList4 = new ArrayList<>();
        arrayList4.add("Bình Thường");
        arrayList4.add("Dịch Vụ");
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList4);
        auto_chon_hinh_thuc.setAdapter(arrayAdapter4);

    }



    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_chinh, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}