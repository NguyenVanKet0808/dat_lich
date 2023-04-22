package com.example.benhvien758.Admin.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Admin.Tai_Khoan.Adapter_quan_li_tai_khoan;
import com.example.benhvien758.Admin.Tai_Khoan.Quan_li_tai_khoan;
import com.example.benhvien758.Admin.Tai_Khoan.lang_nghe_item;
import com.example.benhvien758.Admin.TrangChuAdmin;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.Data_chat;
import com.example.benhvien758.Data.Data_quan_li_chat;
import com.example.benhvien758.Data.User_khachhang_login;
import com.example.benhvien758.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat_Admin extends BaseActivity {
    private RecyclerView recyclerView_ql_chat;
    private Adapter_chat_admin adapter_chat_admin;
    private ImageView img_thoat;
    lang_nghe_item_chat lang_nghe_item_chat;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("admin").child("Ho Tro");

    @Override
    protected int getLayoutId() {
        return R.layout.activyti_quan_li_chat;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        recyclerView_ql_chat = findViewById(R.id.recylerview_quan_li_ho_tro);
        img_thoat = findViewById(R.id.img_quay_lai_admin_quan_li_hotrp);
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data(lang_nghe_item_chat);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chat_Admin.this, TrangChuAdmin.class);
                startActivity(intent);
            }
        });
    }

    private void load_data(lang_nghe_item_chat lang_nghe_item_chat) {
        recyclerView_ql_chat.setHasFixedSize(true);
        ArrayList<Data_quan_li_chat> data = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (data != null) {         // nếu data rỗng thì không hiển thị gì cả
                    data.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {// với key rỗng thì nó load tất cả object lên
                    Data_quan_li_chat data_quan_li_chat = dataSnapshot.getValue(Data_quan_li_chat.class);
                    data.add(0, data_quan_li_chat);
                }

                adapter_chat_admin.notifyDataSetChanged();  // setadapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Chat_Admin.this, "loi r", Toast.LENGTH_SHORT).show();
            }
        });
        lang_nghe_item_chat = new lang_nghe_item_chat() {
            @Override
            public void click_item(String id_item) {
                Intent intent = new Intent(Chat_Admin.this, chat_item.class);
                intent.putExtra("id_item",id_item);
                startActivity(intent);
            }
        };

        adapter_chat_admin = new Adapter_chat_admin(data);
        adapter_chat_admin.lang_nghe_item_chat = lang_nghe_item_chat;
        recyclerView_ql_chat.setAdapter(adapter_chat_admin);

    }
}
