package com.example.benhvien758.Data;

public class Data_item_khoa {
    private String id_user;
    private String id_bacsi_khoa;

    public Data_item_khoa() {
    }

    public Data_item_khoa(String id_user, String id_bacsi_khoa) {
        this.id_user = id_user;
        this.id_bacsi_khoa = id_bacsi_khoa;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_bacsi_khoa() {
        return id_bacsi_khoa;
    }

    public void setId_bacsi_khoa(String id_bacsi_khoa) {
        this.id_bacsi_khoa = id_bacsi_khoa;
    }

    @Override
    public String toString() {
        return "Data_item_khoa{" +
                "id_user='" + id_user + '\'' +
                ", id_bacsi_khoa='" + id_bacsi_khoa + '\'' +
                '}';
    }
}
