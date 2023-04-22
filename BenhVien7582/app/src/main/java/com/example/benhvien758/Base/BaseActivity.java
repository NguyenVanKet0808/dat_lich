package com.example.benhvien758.Base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected void anhXa() {}
    protected void hanhDong() {}
    protected void thietLapView() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        anhXa();
        hanhDong();
        thietLapView();
    }
}
