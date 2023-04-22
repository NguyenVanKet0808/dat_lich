package com.example.benhvien758.Data;

public class Data_chat {
    private String id_chat;
    private String noidung;
    private String id_user;

    public Data_chat() {
    }

    public Data_chat(String id_chat, String noidung, String id_user) {
        this.id_chat = id_chat;
        this.noidung = noidung;
        this.id_user = id_user;
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Data_chat{" +
                "id_chat='" + id_chat + '\'' +
                ", noidung='" + noidung + '\'' +
                ", id_user='" + id_user + '\'' +
                '}';
    }
}
