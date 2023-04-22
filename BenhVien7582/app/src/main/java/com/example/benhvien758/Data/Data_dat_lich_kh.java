package com.example.benhvien758.Data;

public class Data_dat_lich_kh {
    private String khoa;
    private String ngayhen;
    private String hinhthuc;
    private String id_bacsi;
    private String thoigian;
    private String ten_bac_si;
    private String trang_thai;
    private String ket_qua;
    private String chuan_doan;

    public Data_dat_lich_kh() {
    }

    public Data_dat_lich_kh(String khoa, String ngayhen, String hinhthuc, String id_bacsi, String thoigian, String ten_bac_si, String trang_thai, String ket_qua) {
        this.khoa = khoa;
        this.ngayhen = ngayhen;
        this.hinhthuc = hinhthuc;
        this.id_bacsi = id_bacsi;
        this.thoigian = thoigian;
        this.ten_bac_si = ten_bac_si;
        this.trang_thai = trang_thai;
        this.ket_qua = ket_qua;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getNgayhen() {
        return ngayhen;
    }

    public void setNgayhen(String ngayhen) {
        this.ngayhen = ngayhen;
    }

    public String getHinhthuc() {
        return hinhthuc;
    }

    public void setHinhthuc(String hinhthuc) {
        this.hinhthuc = hinhthuc;
    }

    public String getId_bacsi() {
        return id_bacsi;
    }

    public void setId_bacsi(String id_bacsi) {
        this.id_bacsi = id_bacsi;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getTen_bac_si() {
        return ten_bac_si;
    }

    public void setTen_bac_si(String ten_bac_si) {
        this.ten_bac_si = ten_bac_si;
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

    public String getChuan_doan() {
        return chuan_doan;
    }

    public void setChuan_doan(String chuan_doan) {
        this.chuan_doan = chuan_doan;
    }

    @Override
    public String toString() {
        return "Data_dat_lich_kh{" +
                "khoa='" + khoa + '\'' +
                ", ngayhen='" + ngayhen + '\'' +
                ", hinhthuc='" + hinhthuc + '\'' +
                ", id_bacsi='" + id_bacsi + '\'' +
                ", thoigian='" + thoigian + '\'' +
                ", ten_bac_si='" + ten_bac_si + '\'' +
                ", trang_thai='" + trang_thai + '\'' +
                ", ket_qua='" + ket_qua + '\'' +
                ", chuan_doan='" + chuan_doan + '\'' +
                '}';
    }
}
