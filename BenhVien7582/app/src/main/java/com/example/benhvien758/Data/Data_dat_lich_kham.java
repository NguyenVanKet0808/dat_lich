package com.example.benhvien758.Data;

public class Data_dat_lich_kham {
    private String id_lich_kham;
    private String id_user;
    private String hinh_thuc;
    private String trang_thai;
    private String ket_qua;
    private String ngay_hen;
    private String thoi_gian;
    private String chuan_doan;


    public Data_dat_lich_kham() {
    }

    public Data_dat_lich_kham(String id_lich_kham, String id_user, String hinh_thuc, String trang_thai, String ket_qua, String ngay_hen, String thoi_gian) {
        this.id_lich_kham = id_lich_kham;
        this.id_user = id_user;
        this.hinh_thuc = hinh_thuc;
        this.trang_thai = trang_thai;
        this.ket_qua = ket_qua;
        this.ngay_hen = ngay_hen;
        this.thoi_gian = thoi_gian;
    }

    public String getId_lich_kham() {
        return id_lich_kham;
    }

    public void setId_lich_kham(String id_lich_kham) {
        this.id_lich_kham = id_lich_kham;
    }

    public String getId_user() {
        return id_user;
    }

    public String getChuan_doan() {
        return chuan_doan;
    }

    public void setChuan_doan(String chuan_doan) {
        this.chuan_doan = chuan_doan;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getHinh_thuc() {
        return hinh_thuc;
    }

    public void setHinh_thuc(String hinh_thuc) {
        this.hinh_thuc = hinh_thuc;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getKet_qua() {
        return ket_qua;
    }

    public void setKet_qua(String ket_qua) {
        this.ket_qua = ket_qua;
    }

    public String getNgay_hen() {
        return ngay_hen;
    }

    public void setNgay_hen(String ngay_hen) {
        this.ngay_hen = ngay_hen;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    @Override
    public String toString() {
        return "Data_dat_lich_kham{" +
                "id_lich_kham='" + id_lich_kham + '\'' +
                ", id_user='" + id_user + '\'' +
                ", hinh_thuc='" + hinh_thuc + '\'' +
                ", trang_thai='" + trang_thai + '\'' +
                ", ket_qua='" + ket_qua + '\'' +
                ", ngay_hen='" + ngay_hen + '\'' +
                ", thoi_gian='" + thoi_gian + '\'' +
                ", chuan_doan='" + chuan_doan + '\'' +
                '}';
    }
}
