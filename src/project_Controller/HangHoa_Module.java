/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import project_Model.HangHoa;
import Data.data_main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class HangHoa_Module {
    public boolean themHH(HangHoa h) throws Exception{
        String sql ="insert into HANG_HOA (HH_MaHH, HH_TenHH, HH_SoLuong, MaLoai, HH_Gia, HH_NgayApDung, HH_PathImg) values (?,?,?,?,?,?,?)";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, h.getHH_MaHH());
            pr.setString(2, h.getHH_TenHH());
            pr.setInt(3, h.getHH_Slcon());
            pr.setString(4, h.getHH_MaLoaiHH());
            pr.setLong(5, h.getHH_Gia());
            pr.setDate(6, h.getNgayApDung());
            pr.setString(7, h.getHH_imgHH());
            return pr.executeUpdate() >0;
        }
    }
    public boolean capnhatHH(HangHoa h) throws Exception{
        String sql = "update HANG_HOA set HH_MaHH =?,HH_TenHH =?, HH_SoLuong = ?, MaLoai=?, HH_Gia=?, HH_NgayApDung=?,HH_PathImg=? where HH_MaHH =?";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(8, h.getHH_MaHH());
            pr.setString(1, h.getHH_MaHH());
            pr.setString(2, h.getHH_TenHH());
            pr.setInt(3, h.getHH_Slcon());
            pr.setString(4, h.getHH_MaLoaiHH());
            pr.setLong(5, h.getHH_Gia());
            pr.setDate(6, h.getNgayApDung());
            pr.setString(7, h.getHH_imgHH());
            return pr.executeUpdate()>0;
        }
    }
    public boolean xoaHH(String mahh) throws Exception{
        String sql = "delete from HANG_HOA where HH_MaHH = ?";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, mahh);
            return pr.executeUpdate()>0;
        }
    }
    public List loadHH() throws Exception{
        List<HangHoa> l = new ArrayList<HangHoa> ();
        String sql = "select * from HANG_HOA";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                HangHoa h = new HangHoa();
                h.setHH_MaHH(rs.getString("HH_MaHH"));
                h.setHH_TenHH(rs.getString("HH_TenHH"));
                h.setHH_MaLoaiHH(rs.getString("MaLoai"));
                h.setHH_Slcon(rs.getInt("HH_SoLuong"));
                h.setHH_Gia(rs.getLong("HH_Gia"));
                h.setNgayApDung(rs.getDate("HH_NgayApDung"));
                h.setHH_imgHH(rs.getString("HH_PathImg"));
                l.add(h);
            }
        }
        return l;
    }

    
}
