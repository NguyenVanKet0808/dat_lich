package com.example.benhvien758.Chat;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Bacsi.Home.Adapter_item_lich_lam_home;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Base.BaseFragment;
import com.example.benhvien758.Data.Data_chat;
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

public class Activyti_chat extends BaseActivity {
    private ImageView img_thoat,img_send;
    private RecyclerView recyclerView_chat;
    private Adapter_chat adapter_chat;
    private EditText edt_noidung;


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("admin").child("Ho Tro").child(user.getUid());
    private DatabaseReference myRef_admin = database.getReference("admin").child("Ho Tro").child(user.getUid()).child("chat");


    @Override
    protected int getLayoutId() {
        return R.layout.activyti_chat;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        img_thoat = findViewById(R.id.img_thoat_chat);
        img_send = findViewById(R.id.img_send_chat);
        recyclerView_chat = findViewById(R.id.recylerview_chat);
        edt_noidung = findViewById(R.id.edt_noidung_chat);
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
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data();
    }

    private void load_data() {
        recyclerView_chat.setHasFixedSize(true);
        ArrayList<Data_chat> data = new ArrayList<>();
        myRef_admin.addValueEventListener(new ValueEventListener() {
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
        recyclerView_chat.setAdapter(adapter_chat);

    }

    private void send() {

        String noidung = edt_noidung.getText().toString().trim();
        if (noidung.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập nội dung !", Toast.LENGTH_SHORT).show();
        }
        else{
            String id_chat = "";
            Data_chat data_chat = new Data_chat(id_chat,noidung,user.getUid());
            myRef.child("id_user").setValue(user.getUid());
            myRef_admin.push().setValue(data_chat, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    myRef_admin.child(ref.getKey()).child("id_chat").setValue(ref.getKey(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            load_data();
                            edt_noidung.setText("");
                        }
                    });
                }

            });



        }
    }
}
