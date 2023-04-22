package com.example.benhvien758.Admin.Khoa;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benhvien758.Admin.TrangChuAdmin;
import com.example.benhvien758.Base.BaseActivity;
import com.example.benhvien758.Data.Data_Khoa;
import com.example.benhvien758.Data.Data_dat_lich_kh;
import com.example.benhvien758.Henlich.KhachHang.DatLich.henlichkhAdapter;
import com.example.benhvien758.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quan_li_khoa extends BaseActivity {

    private ImageView img_quay_lai, img_add, img_search;
    private EditText edt_search;
    private RecyclerView recyclerView;
    private Adapter_quan_li_khoa adapter_quan_li_khoa;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Taikhoan");
    private DatabaseReference myRef_khoa = database.getReference("Khoa");
    private String khoa;


    @Override
    protected int getLayoutId() {
        return R.layout.quan_li_khoa;
    }

    @Override
    protected void anhXa() {
        super.anhXa();
        img_quay_lai = findViewById(R.id.img_quay_lai_admin);
        img_add = findViewById(R.id.img_add_khoa);
        recyclerView = findViewById(R.id.recylerview_quan_li_khoa);
    }

    @Override
    protected void thietLapView() {
        super.thietLapView();
        load_data();
    }

    private void load_data() {
        recyclerView.setHasFixedSize(true);

        ArrayList<Data_Khoa> data = new ArrayList<>(); // tạo ra 1 danh sách data_car để truyền lên recylerview

        // LOAD DATA VÊ
        myRef_khoa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (data != null) {         // nếu data rỗng thì không hiển thị gì cả
                    data.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {// với key rỗng thì nó load tất cả object lên
                    Data_Khoa data_khoa = dataSnapshot.getValue(Data_Khoa.class);
                    data.add(0, data_khoa);
                }

                adapter_quan_li_khoa.notifyDataSetChanged();  // setadapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quan_li_khoa.this, "loi r", Toast.LENGTH_SHORT).show();
            }
        });
        adapter_quan_li_khoa = new Adapter_quan_li_khoa(data);
        recyclerView.setAdapter(adapter_quan_li_khoa);
    }

    @Override
    protected void hanhDong() {
        super.hanhDong();
        img_quay_lai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Quan_li_khoa.this, TrangChuAdmin.class);
                startActivity(intent);
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taokhoa();
            }
        });
    }
    private void taokhoa() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tao_khoa);
        EditText edt_tenkhoa = dialog.findViewById(R.id.tkhoa);
        Button btn_tao_khoa = dialog.findViewById(R.id.btn_update_khoa);
        btn_tao_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_tenkhoa.getText().toString().isEmpty()){
                    Toast.makeText(Quan_li_khoa.this, "Hãy Nhập Tên Khoa", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id_khoa="";
                    Data_Khoa data_khoa = new Data_Khoa(id_khoa,edt_tenkhoa.getText().toString());
                    myRef_khoa.push().setValue(data_khoa, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error == null){
                                myRef_khoa.child(ref.getKey()).child("id_khoa").setValue(ref.getKey());
                                Toast.makeText(Quan_li_khoa.this, "Tạo Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                       }
                    });
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
