/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Model;

/**
 *
 * @author Hoàng Huy
 */
public class NhanVien {
    public String NV_MaNV, NV_TenNV, NV_SDT, NV_DiaChi, NV_Username, NV_Password, NV_img;
    public int NV_Ad;

    public NhanVien() {
    }

    public NhanVien(String NV_MaNV, String NV_TenNV, String NV_SDT, String NV_DiaChi, String NV_Username, String NV_Password, int NV_Ad, String NV_img) {
        this.NV_MaNV = NV_MaNV;
        this.NV_TenNV = NV_TenNV;
        this.NV_SDT = NV_SDT;
        this.NV_DiaChi = NV_DiaChi;
        this.NV_Username = NV_Username;
        this.NV_Password = NV_Password;
        this.NV_Ad = NV_Ad;
        this.NV_img = NV_img;
    }

    public String getNV_img() {
        return NV_img;
    }

    public void setNV_img(String NV_img) {
        this.NV_img = NV_img;
    }

    public String getNV_MaNV() {
        return NV_MaNV;
    }

    public void setNV_MaNV(String NV_MaNV) {
        this.NV_MaNV = NV_MaNV;
    }

    public String getNV_TenNV() {
        return NV_TenNV;
    }

    public void setNV_TenNV(String NV_TenNV) {
        this.NV_TenNV = NV_TenNV;
    }

    public String getNV_SDT() {
        return NV_SDT;
    }

    public void setNV_SDT(String NV_SDT) {
        this.NV_SDT = NV_SDT;
    }

    public String getNV_DiaChi() {
        return NV_DiaChi;
    }

    public void setNV_DiaChi(String NV_DiaChi) {
        this.NV_DiaChi = NV_DiaChi;
    }

    public String getNV_Username() {
        return NV_Username;
    }

    public void setNV_Username(String NV_Username) {
        this.NV_Username = NV_Username;
    }

    public String getNV_Password() {
        return NV_Password;
    }

    public void setNV_Password(String NV_Password) {
        this.NV_Password = NV_Password;
    }

    public int getNV_Ad() {
        return NV_Ad;
    }

    public void setNV_Ad(int NV_Ad) {
        this.NV_Ad = NV_Ad;
    }
    
}
