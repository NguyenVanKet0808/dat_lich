package com.example.benhvien758.Admin.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class Adapter_chat_admin extends RecyclerView.Adapter<Adapter_chat_admin.ViewHolder>{
    private ArrayList<Data_quan_li_chat> data_quan_li_chats;
    lang_nghe_item_chat lang_nghe_item_chat;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");

    public Adapter_chat_admin(ArrayList<Data_quan_li_chat> data_quan_li_chats) {
        this.data_quan_li_chats = data_quan_li_chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_admin_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data_quan_li_chat data = data_quan_li_chats.get(position);
        if (data == null){
            return;
        }
        else {
            myRef.child(data.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User_khachhang_login user_data = snapshot.getValue(User_khachhang_login.class);
                    holder.btn_chat.setText(user_data.getName());
                    holder.btn_chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lang_nghe_item_chat.click_item(data.getId_user());
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data_quan_li_chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button btn_chat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_chat = itemView.findViewById(R.id.btn_item_chat_admin);
        }
    }
}
