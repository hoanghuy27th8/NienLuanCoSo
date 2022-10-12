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
public class KhachHang {
    public String KH_MaKH, KH_TenKH, KH_SDT, KH_DiaChi;

    public KhachHang() {
    }

    public KhachHang(String KH_MaKH, String KH_TenKH, String KH_SDT, String KH_DiaChi) {
        this.KH_MaKH = KH_MaKH;
        this.KH_TenKH = KH_TenKH;
        this.KH_SDT = KH_SDT;
        this.KH_DiaChi = KH_DiaChi;
    }

    public String getKH_MaKH() {
        return KH_MaKH;
    }

    public void setKH_MaKH(String KH_MaKH) {
        this.KH_MaKH = KH_MaKH;
    }

    public String getKH_TenKH() {
        return KH_TenKH;
    }

    public void setKH_TenKH(String KH_TenKH) {
        this.KH_TenKH = KH_TenKH;
    }

    public String getKH_SDT() {
        return KH_SDT;
    }

    public void setKH_SDT(String KH_SDT) {
        this.KH_SDT = KH_SDT;
    }

    public String getKH_DiaChi() {
        return KH_DiaChi;
    }

    public void setKH_DiaChi(String KH_DiaChi) {
        this.KH_DiaChi = KH_DiaChi;
    }

   
    
}
