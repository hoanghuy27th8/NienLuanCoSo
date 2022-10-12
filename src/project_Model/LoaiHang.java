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
public class LoaiHang {
    public String LH_MaLH, LH_TenLH;

    public LoaiHang() {
    }

    public LoaiHang(String LH_MaLH, String LH_TenLH) {
        this.LH_MaLH = LH_MaLH;
        this.LH_TenLH = LH_TenLH;
    }
    public LoaiHang(LoaiHang l){
        LH_MaLH = l.LH_MaLH;
        LH_TenLH = l.LH_TenLH;
    }

    public String getLH_MaLH() {
        return LH_MaLH;
    }

    public void setLH_MaLH(String LH_MaLH) {
        this.LH_MaLH = LH_MaLH;
    }

    public String getLH_TenLH() {
        return LH_TenLH;
    }

    public void setLH_TenLH(String LH_TenLH) {
        this.LH_TenLH = LH_TenLH;
    }
    
}
