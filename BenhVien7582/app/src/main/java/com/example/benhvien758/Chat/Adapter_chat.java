package com.example.benhvien758.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class Adapter_chat extends RecyclerView.Adapter<Adapter_chat.ViewHolder>{
    private ArrayList<Data_chat> data_chats ;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_login = database.getReference("Taikhoan");

    public Adapter_chat(ArrayList<Data_chat> data_chats) {
        this.data_chats = data_chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data_chat data = data_chats.get(position);
        holder.tv_noidung.setText(data.getNoidung());
        myRef_login.child(data.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_khachhang_login user_data = snapshot.getValue(User_khachhang_login.class);
                if (user_data.getVaitro().contains("Admin")) {
                    holder.tv_ten.setText("Admin");
                    Glide.with(holder.img_avt_chat).load(R.drawable.ic_use_1).into(holder.img_avt_chat);
                    holder.layout.setBackgroundResource(R.drawable.custom_textview_1);
                } else {
                    holder.tv_ten.setText(user_data.getName());
                    Glide.with(holder.img_avt_chat).load(user_data.getHinhanh()).into(holder.img_avt_chat);
                    holder.layout.setBackgroundResource(R.drawable.custom_textview);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return data_chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_ten, tv_noidung;
        private ImageView img_avt_chat;
        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tv_ten_chat);
            tv_noidung = itemView.findViewById(R.id.tv_noi_dung_chat);
            img_avt_chat = itemView.findViewById(R.id.img_avt_chat);
            layout = itemView.findViewById(R.id.chat_mau);
        }
    }
}
