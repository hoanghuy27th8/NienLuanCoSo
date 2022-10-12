/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class ChiTietDonHang {
    private int soluong;
    private String DH_MaDH,Ma_HH;

    public ChiTietDonHang() {

    }

    public ChiTietDonHang(int soluong, String DH_MaDH, String Ma_HH) {
        this.soluong = soluong;
        this.DH_MaDH = DH_MaDH;
        this.Ma_HH = Ma_HH;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDH_MaDH() {
        return DH_MaDH;
    }

    public void setDH_MaDH(String DH_MaDH) {
        this.DH_MaDH = DH_MaDH;
    }

    public String getMa_HH() {
        return Ma_HH;
    }

    public void setMa_HH(String Ma_HH) {
        this.Ma_HH = Ma_HH;
    }


    
}
