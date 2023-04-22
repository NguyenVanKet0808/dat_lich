package com.example.benhvien758.GiaoDien.KhachHang;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.benhvien758.Bacsi.Tra_cuu.Adapter_tra_cuu_benh_nhan;
import com.example.benhvien758.Base.BaseFragment;
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

public class Fragment_lich_su_kham extends BaseFragment {
    private ImageView img_thoat,img_avt_benh_nhan;
    private TextView tv_name,tv_email,tv_gioitinh,tv_diachi,tv_phone,tv_thongbao;
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
        return R.layout.fragment_lich_su_kham_kh;
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data();
    }

    private void load_data() {
        myRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_data = snapshot.getValue(User_khachhang_login.class);
                tv_name.setText(user_data.getName());
                tv_email.setText(user_data.getEmail());
                tv_phone.setText(user_data.getPhone());
                tv_gioitinh.setText(user_data.getGioitinh());
                tv_diachi.setText(user_data.getDiachi());
                Glide.with(img_avt_benh_nhan).load(user_data.getHinhanh()).into(img_avt_benh_nhan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList<Data_dat_lich_kh> data = new ArrayList<>();
        myRef.child(user.getUid()).child("lich su").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Data_dat_lich_kh data_dat_lich_kh = dataSnapshot.getValue(Data_dat_lich_kh.class);
                    data.add(0,data_dat_lich_kh);
                }
                adapter_tra_cuu_benh_nhan.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (data != null){
            linearLayout.setVisibility(View.VISIBLE);
            tv_thongbao.setVisibility(View.GONE);
        }
        else {
            linearLayout.setVisibility(View.GONE);
            tv_thongbao.setVisibility(View.VISIBLE);
        }
        adapter_tra_cuu_benh_nhan = new Adapter_tra_cuu_benh_nhan(data);
        recyclerView_ketqua.setAdapter(adapter_tra_cuu_benh_nhan);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView_ketqua.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        img_thoat = requireView().findViewById(R.id.img_thoat_lich_su_kham);
        img_avt_benh_nhan = requireView().findViewById(R.id.img_avt_lich_su_kham);
        tv_name = requireView().findViewById(R.id.tv_ten_lich_su_kham);
        tv_email = requireView().findViewById(R.id.tv_email_lich_su_kham);
        tv_gioitinh = requireView().findViewById(R.id.tv_gioi_tinh_lich_su_kham);
        tv_diachi = requireView().findViewById(R.id.tv_dia_chi_lich_su_kham);
        tv_phone = requireView().findViewById(R.id.tv_phone_lich_su_kham);
        tv_thongbao = requireView().findViewById(R.id.tv_thongbao_lich_su_kham);
        recyclerView_ketqua = requireView().findViewById(R.id.recylerview_ket_qua_lich_su_kham);
        linearLayout = requireView().findViewById(R.id.s1_lich_su_kham);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_home_kh());
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_chinh, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
