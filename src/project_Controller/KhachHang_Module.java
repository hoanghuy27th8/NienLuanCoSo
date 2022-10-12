/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import Data.data_main;
import project_Model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class KhachHang_Module {
    public boolean themKH(KhachHang h) throws Exception{
        String sql = "insert into KHACH_HANG (KH_MaKH, KH_TenKH, KH_SDT, KH_DiaChi) values (?,?,?,?)";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, h.getKH_MaKH());
            pr.setString(2, h.getKH_TenKH());
            pr.setString(3, h.getKH_SDT());
            pr.setString(4, h.getKH_DiaChi());
            
            return pr.executeUpdate() > 0;
        }
    }
    public List<KhachHang> loadKH() throws Exception{
        List <KhachHang> lkh = new ArrayList<KhachHang>();
        String sql = "select * from KHACH_HANG";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareCall(sql);
                ){
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                KhachHang kh = new KhachHang();
                kh.setKH_MaKH(rs.getString("KH_MaKH"));
                kh.setKH_TenKH(rs.getString("KH_TenKH"));
                kh.setKH_SDT(rs.getString("KH_SDT"));
                kh.setKH_DiaChi(rs.getString("KH_DiaChi"));
                lkh.add(kh);
            }
        }
        return lkh;
    }
    public int demKH() throws Exception{
        int count =0 ;
        String sql ="select * from KHACH_HANG";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                count++;
            }
            return count;
        }
    }
    public KhachHang timSDT(String sdt) throws Exception{
        String sql = "select * from KHACH_HANG where KH_SDT = ?";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, sdt);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                KhachHang k = new KhachHang();
                k.setKH_MaKH(rs.getString("KH_MaKH"));
                k.setKH_DiaChi(rs.getString("KH_DiaChi"));
                k.setKH_TenKH(rs.getString("KH_TenKH"));
                return k;
            }
        }
        return null;
    }
}
