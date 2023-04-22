package com.example.benhvien758.Henlich.KhachHang.DatLich;

public class data_mhenlich {
    private String ngay;
    private String tenbacsi;
    private String buoi;
    private String diadiem;
    private String khoa;

    public data_mhenlich() {
    }

    public data_mhenlich(String ngay, String tenbacsi, String buoi, String diadiem, String khoa) {
        this.ngay = ngay;
        this.tenbacsi = tenbacsi;
        this.buoi = buoi;
        this.diadiem = diadiem;
        this.khoa = khoa;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTenbacsi() {
        return tenbacsi;
    }

    public void setTenbacsi(String tenbacsi) {
        this.tenbacsi = tenbacsi;
    }

    public String getBuoi() {
        return buoi;
    }

    public void setBuoi(String buoi) {
        this.buoi = buoi;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    @Override
    public String toString() {
        return "data_mhenlich{" +
                "ngay='" + ngay + '\'' +
                ", tenbacsi='" + tenbacsi + '\'' +
                ", buoi='" + buoi + '\'' +
                ", diadiem='" + diadiem + '\'' +
                ", khoa='" + khoa + '\'' +
                '}';
    }
}
