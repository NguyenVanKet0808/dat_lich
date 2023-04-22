package com.example.benhvien758.Henlich.KhachHang.DatLich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.Data.Data_dat_lich_kham;
import com.example.benhvien758.Data.Data_item_khoa;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
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

public class Fragment_DatLich extends BaseFragment {
    private RecyclerView recyclerView;
    private henlichkhAdapter henlichkhAdapter;



    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan").child(user.getUid());
    private DatabaseReference myRef_khoa = database.getReference("Khoa");


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment_dat_lich;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        recyclerView = requireView().findViewById(R.id.recylerview_henlich);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        loadrecylerview();
    }



    private void loadrecylerview(){
        recyclerView.setHasFixedSize(true);

        ArrayList<Data_dat_lich_kh> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

        // LOAD DATA VÊ
        myRef.child("lich kham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (data != null) {         // nếu data rỗng thì không hiển thị gì cả
                    data.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {// với key rỗng thì nó load tất cả object lên
                    Data_dat_lich_kh data_dat_lich_kh = dataSnapshot.getValue(Data_dat_lich_kh.class);
                    data.add(0, data_dat_lich_kh);
                }

                henlichkhAdapter.notifyDataSetChanged();  // setadapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "loi r", Toast.LENGTH_SHORT).show();
            }
        });
        henlichkhAdapter = new henlichkhAdapter(data,getActivity());
        recyclerView.setAdapter(henlichkhAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }




}