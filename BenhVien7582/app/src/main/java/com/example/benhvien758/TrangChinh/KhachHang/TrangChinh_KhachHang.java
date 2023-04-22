package com.example.benhvien758.TrangChinh.KhachHang;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.GiaoDien.KhachHang.Fragment_home_kh;
import com.example.benhvien758.HoSo.Fragment_hoso;
import com.example.benhvien758.Henlich.KhachHang.DatLich.Fragment_DatLich;
import com.example.benhvien758.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrangChinh_KhachHang extends BaseActivity {
    private BottomNavigationView bottomNavigationView;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef_login = database.getReference("Taikhoan").child(user.getUid());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trang_chinh_khach_hang;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        bottomNavigationView = findViewById(R.id.navigation);
        myRef_login.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_khachhang_login = snapshot.getValue(User_khachhang_login.class);
                Toast.makeText(TrangChinh_KhachHang.this, "Xin Chào " + user_khachhang_login.getName(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        loadFragment(new Fragment_home_kh());
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                loadFragment(new Fragment_home_kh());
                                break;
                            case R.id.datlich:
                                loadFragment(new Fragment_DatLich());
                                break;
                            case R.id.hoso:
                                loadFragment(new Fragment_hoso());
                                break;

                        }
                        return true;
                    }
                }
        );
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_chinh, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}