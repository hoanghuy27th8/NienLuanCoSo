/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Model;

import java.sql.Date;

/**
 *
 * @author Hoàng Huy
 */
public class DonHang {
    private String DH_MaDH,NV_MaNV, KH_MaKH;
    private long DH_TienGiam, DH_TongTien;
    private Date DH_NgayLap;

    public DonHang() {
    }

    public DonHang(String DH_MaDH, String NV_MaNV, String KH_MaKH, long DH_TienGiam, long DH_TongTien, Date DH_NgayLap) {
        this.DH_MaDH = DH_MaDH;
        this.NV_MaNV = NV_MaNV;
        this.KH_MaKH = KH_MaKH;
        this.DH_TienGiam = DH_TienGiam;
        this.DH_TongTien = DH_TongTien;
        this.DH_NgayLap = DH_NgayLap;
    }

    public String getDH_MaDH() {
        return DH_MaDH;
    }

    public void setDH_MaDH(String DH_MaDH) {
        this.DH_MaDH = DH_MaDH;
    }

    public String getNV_MaNV() {
        return NV_MaNV;
    }

    public void setNV_MaNV(String NV_MaNV) {
        this.NV_MaNV = NV_MaNV;
    }

    public String getKH_MaKH() {
        return KH_MaKH;
    }

    public void setKH_MaKH(String KH_MaKH) {
        this.KH_MaKH = KH_MaKH;
    }

    public long getDH_TienGiam() {
        return DH_TienGiam;
    }

    public void setDH_TienGiam(long DH_TienGiam) {
        this.DH_TienGiam = DH_TienGiam;
    }

    public long getDH_TongTien() {
        return DH_TongTien;
    }

    public void setDH_TongTien(long DH_TongTien) {
        this.DH_TongTien = DH_TongTien;
    }

    public Date getDH_NgayLap() {
        return DH_NgayLap;
    }

    public void setDH_NgayLap(Date DH_NgayLap) {
        this.DH_NgayLap = DH_NgayLap;
    }
    
    
}
