package com.example.benhvien758.Data;

public class User_khachhang_login {
    private String id_user;
    private String name;
    private String email;
    private String phone;
    private String diachi;
    private String cmnd;
    private String ngaysinh;
    private String gioitinh;
    private String vaitro;
    private String hinhanh;
    private String khoa;


    public User_khachhang_login() {
    }

    public User_khachhang_login(String id_user, String name, String email, String phone, String diachi, String cmnd, String ngaysinh, String gioitinh, String vaitro, String hinhanh, String khoa) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.cmnd = cmnd;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.vaitro = vaitro;
        this.hinhanh = hinhanh;
        this.khoa = khoa;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    @Override
    public String toString() {
        return "User_khachhang_login{" +
                "id_user='" + id_user + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", diachi='" + diachi + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", ngaysinh='" + ngaysinh + '\'' +
                ", gioitinh='" + gioitinh + '\'' +
                ", vaitro='" + vaitro + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                ", khoa='" + khoa + '\'' +
                '}';
    }
}