package com.example.benhvien758.Data;

public class Data_Khoa {
    public String id_khoa;
    private String ten_khoa;

    public Data_Khoa() {
    }

    public Data_Khoa(String id_khoa, String ten_khoa) {
        this.id_khoa = id_khoa;
        this.ten_khoa = ten_khoa;
    }

    public String getId_khoa() {
        return id_khoa;
    }

    public void setId_khoa(String id_khoa) {
        this.id_khoa = id_khoa;
    }

    public String getTen_khoa() {
        return ten_khoa;
    }

    public void setTen_khoa(String ten_khoa) {
        this.ten_khoa = ten_khoa;
    }

    @Override
    public String toString() {
        return "Data_Khoa{" +
                "id_khoa='" + id_khoa + '\'' +
                ", ten_khoa='" + ten_khoa + '\'' +
                '}';
    }
}
