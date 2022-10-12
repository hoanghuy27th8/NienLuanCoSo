/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import Data.data_main;
import project_Model.DonHang;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class DonHang_Module {
    public boolean themDH(DonHang dh) throws Exception{
        String sql = "insert into DON_HANG (DH_MaDH, DH_TienGiam, DH_TongTien, DH_NgayLap, MaNV, MaKH) values (?,?,?,?,?,?)";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, dh.getDH_MaDH());
            pr.setLong(2, dh.getDH_TienGiam());
            pr.setLong(3, dh.getDH_TongTien());
            pr.setDate(4, dh.getDH_NgayLap());
            pr.setString(5, dh.getNV_MaNV());
            pr.setString(6, dh.getKH_MaKH());
            
            return pr.executeUpdate() > 0;
        }
    }
    
    public List<DonHang> timDH(Date ngayduoi, Date ngaytren) throws Exception{
        String sql ="select * from DON_HANG where DH_NgayLap between ? and ?;";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setDate(1, ngayduoi);
            pr.setDate(2, ngaytren);
            ResultSet rs = pr.executeQuery();
            List<DonHang> ldh = new ArrayList<DonHang>();
            while(rs.next()){
                DonHang d = new DonHang();
                d.setDH_MaDH(rs.getString("DH_MaDH"));
                d.setDH_TienGiam(rs.getLong("DH_TienGiam"));
                d.setDH_TongTien(rs.getLong("DH_TongTien"));
                d.setDH_NgayLap(rs.getDate("DH_NgayLap"));
                d.setKH_MaKH(rs.getString("MaKH"));
                d.setNV_MaNV(rs.getString("MaNV"));
                ldh.add(d);
            }
            return ldh;
        }
    }
    
    public List<DonHang> getDataDonHang()throws Exception{
        String sql = "select * from DON_HANG";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            List <DonHang> dh = new ArrayList<DonHang>();
            while(rs.next()){
                DonHang d = new DonHang();
                d.setDH_MaDH(rs.getString("DH_MaDH"));
                d.setDH_TienGiam(rs.getLong("DH_TienGiam"));
                d.setDH_TongTien(rs.getLong("DH_TongTien"));
                d.setDH_NgayLap(rs.getDate("DH_NgayLap"));
                d.setKH_MaKH(rs.getString("MaKH"));
                d.setNV_MaNV(rs.getString("MaNV"));
                dh.add(d);
            }
            return dh;
        }
    }
    
    public DonHang timTheoMa(String s) throws Exception{
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement("select * from DON_HANG where DH_MaDH = ?;");
                ){
            pr.setString(1, s);
            ResultSet rs = pr.executeQuery();
            DonHang d = new DonHang();
            while(rs.next()){
                d.setDH_MaDH(rs.getString("DH_MaDH"));
                d.setDH_TienGiam(rs.getLong("DH_TienGiam"));
                d.setDH_TongTien(rs.getLong("DH_TongTien"));
                d.setDH_NgayLap(rs.getDate("DH_NgayLap"));
                d.setKH_MaKH(rs.getString("MaKH"));
                d.setNV_MaNV(rs.getString("MaNV"));
            }
            return d;
        }
    }
}
