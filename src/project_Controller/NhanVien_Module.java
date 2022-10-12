/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import project_Model.NhanVien;
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
public class NhanVien_Module {
    public boolean them(NhanVien nv) throws Exception{
        String sql="insert into NHAN_VIEN(NV_MaNV, NV_TenNV, NV_SDT, NV_DiaChi, path_img) values(?,?,?,?,?)";
        try(
            Connection con = data_main.connect_main();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1,nv.getNV_MaNV());
            pr.setString(2,nv.getNV_TenNV());
            pr.setString(3,nv.getNV_SDT());
            pr.setString(4,nv.getNV_DiaChi());
            pr.setString(5,nv.getNV_img());
            return pr.executeUpdate()>0;
        }
    }
    public List load_data_nv() throws Exception{
        List<NhanVien> lnv = new ArrayList<NhanVien>();
        String sql = "select * from NHAN_VIEN nv join TAI_KHOAN tk on nv.NV_MaNV = tk.Username";
        try(
            Connection con = data_main.connect_main();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                NhanVien nv = new NhanVien();
                nv.setNV_MaNV(rs.getString("NV_MaNV"));
                nv.setNV_TenNV(rs.getString("NV_TenNV"));
                nv.setNV_SDT(rs.getString("NV_SDT"));
                nv.setNV_DiaChi(rs.getString("NV_DiaChi"));
                nv.setNV_Username(rs.getString("Username"));
                nv.setNV_Password(rs.getString("Password"));
                nv.setNV_Ad(rs.getInt("CheckAd"));
                nv.setNV_img(rs.getString("path_img"));
                lnv.add(nv);
            }
            return lnv;
            
        }
    }
    public boolean xoanv(String manv) throws Exception{
        String sql="delete from NHAN_VIEN where NV_MaNV = ?";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, manv);
            return pr.executeUpdate()>0;
        }
    }
    
    public boolean capnhatNV(NhanVien nv) throws Exception{
        String sql ="update NHAN_VIEN set NV_MaNV = ?, NV_TenNV = ?, NV_SDT = ?, NV_DiaChi = ?, path_img = ? where NV_MaNV = ?";
        try(
            Connection con = data_main.connect_main();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(6, nv.getNV_MaNV());
            pr.setString(1, nv.getNV_MaNV());
            pr.setString(2, nv.getNV_TenNV());
            pr.setString(3, nv.getNV_SDT());
            pr.setString(4, nv.getNV_DiaChi());
            pr.setString(5, nv.getNV_img());
            
            return pr.executeUpdate()>0;
        }
    }
}
