package com.example.benhvien758.Data;

public class Data_quan_li_chat {
    private String id_user;

    public Data_quan_li_chat() {
    }

    public Data_quan_li_chat(String id_user) {
        this.id_user = id_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Data_quan_li_chat{" +
                "id_user='" + id_user + '\'' +
                '}';
    }
}
