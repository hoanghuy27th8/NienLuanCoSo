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
public class HangHoa {
    public String HH_MaHH, HH_TenHH, HH_MaLoaiHH, HH_imgHH;
    public int HH_Slcon;
    public long HH_Gia;
    public Date NgayApDung;
    public HangHoa() {
    }

    public HangHoa(String HH_MaHH, String HH_TenHH, String HH_MaLoaiHH, int HH_Slcon, long HH_Gia, Date NgayApDung, String img) {
        this.HH_MaHH = HH_MaHH;
        this.HH_TenHH = HH_TenHH;
        this.HH_MaLoaiHH = HH_MaLoaiHH;
        this.HH_Slcon = HH_Slcon;
        this.HH_Gia = HH_Gia;
        this.NgayApDung = NgayApDung;
        this.HH_imgHH = img;
    }

    public String getHH_imgHH() {
        return HH_imgHH;
    }

    public void setHH_imgHH(String HH_imgHH) {
        this.HH_imgHH = HH_imgHH;
    }

    public String getHH_MaHH() {
        return HH_MaHH;
    }

    public void setHH_MaHH(String HH_MaHH) {
        this.HH_MaHH = HH_MaHH;
    }

    public String getHH_TenHH() {
        return HH_TenHH;
    }

    public void setHH_TenHH(String HH_TenHH) {
        this.HH_TenHH = HH_TenHH;
    }

    public int getHH_Slcon() {
        return HH_Slcon;
    }

    public void setHH_Slcon(int HH_Slcon) {
        this.HH_Slcon = HH_Slcon;
    }

    public long getHH_Gia() {
        return HH_Gia;
    }

    public void setHH_Gia(long HH_Gia) {
        this.HH_Gia = HH_Gia;
    }

    public Date getNgayApDung() {
        return NgayApDung;
    }

    public void setNgayApDung(Date NgayApDung) {
        this.NgayApDung = NgayApDung;
    }
    public String getHH_MaLoaiHH() {
        return HH_MaLoaiHH;
    }
    public void setHH_MaLoaiHH(String ma) {
        this.HH_MaLoaiHH = ma;
    }
//    public static void main(String[] args) {
//        HangHoa h = new HangHoa();
//        LoaiHang l = new LoaiHang("b3", "bánh");
////        h.setLh(l);
//        h.setHH_MaLoaiHH(l);
//        System.out.println(h.getHH_MaLoaiHH());
//    }
}
