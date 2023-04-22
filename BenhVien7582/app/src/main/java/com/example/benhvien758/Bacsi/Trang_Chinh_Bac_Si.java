package com.example.benhvien758.Bacsi;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benhvien758.Bacsi.Home.Home_bs;
import com.example.benhvien758.Bacsi.Lich_lam_viec.lich_lam_viec;
import com.example.benhvien758.Bacsi.Tra_cuu.Fragment_tra_cuu;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Trang_Chinh_Bac_Si extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_trang_chinh_bac_si;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        bottomNavigationView = findViewById(R.id.congcu_bac_si);

    }
    @Override
    protected void thietLapView() {
        super.thietLapView();
        loadFragment(new Home_bs());
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home_bs:
                                loadFragment(new Home_bs());
                                break;
                            case R.id.item_lich_lam_bs:
                                loadFragment(new lich_lam_viec());
                                break;
                            case R.id.item_tra_cuu_benh_nhan:
                                loadFragment(new Fragment_tra_cuu());
                                break;



                        }
                        return true;
                    }
                }
        );
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_bac_si, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
