package com.example.benhvien758.Admin.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Chat.Adapter_chat;
import com.example.benhvien758.Data.Data_chat;
import com.example.benhvien758.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat_item extends BaseActivity {
    private String id_user;
    private ImageView img_thoat,img_send;
    private RecyclerView recyclerView_chat_admin;
    private EditText edt_noidung_chat_admin;
    private Adapter_chat adapter_chat;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("admin").child("Ho Tro").child(user.getUid());
    private DatabaseReference myRef_admin = database.getReference("admin").child("Ho Tro");

    @Override
    protected int getLayoutId() {
        return R.layout.activyti_chat_item;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        Intent intent = getIntent();
        if (intent.getExtras() != null ){
            id_user = intent.getExtras().getString("id_item","");
        }
        img_thoat = findViewById(R.id.img_thoat_chat_admin);
        img_send = findViewById(R.id.img_send_chat_admin);
        recyclerView_chat_admin = findViewById(R.id.recylerview_chat_admin);
        edt_noidung_chat_admin = findViewById(R.id.edt_noidung_chat_admin);
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data();
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
        img_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chat_item.this,Chat_Admin.class);
                startActivity(intent);
            }
        });
    }



    private void send() {

        String noidung = edt_noidung_chat_admin.getText().toString().trim();
        if (noidung.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập nội dung !", Toast.LENGTH_SHORT).show();
        }
        else{
            String id_chat = "";
            Data_chat data_chat = new Data_chat(id_chat,noidung,user.getUid());
            myRef_admin.child(id_user).child("chat").push().setValue(data_chat, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    myRef_admin.child(id_user).child("chat").child(ref.getKey()).child("id_chat").setValue(ref.getKey(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            load_data();
                            edt_noidung_chat_admin.setText("");
                        }
                    });
                }

            });



        }
    }

    private void load_data() {
        recyclerView_chat_admin.setHasFixedSize(true);
        ArrayList<Data_chat> data = new ArrayList<>();
        myRef_admin.child(id_user).child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(data != null){
                    data.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data_chat data_chat = dataSnapshot.getValue(Data_chat.class);
                    data.add( data_chat);
                }
                adapter_chat.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        adapter_chat = new Adapter_chat(data);
        recyclerView_chat_admin.setAdapter(adapter_chat);
    }
}
