/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import project_Model.LoaiHang;
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
public class LoaiHang_Module {

    public boolean themLH(LoaiHang lh) throws Exception {
        String sql = "insert into LOAI_HANG(LH_MaLH, LH_TenLH) values (?,?)";
        try (
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);) {
            pr.setString(1, lh.getLH_MaLH());
            pr.setString(2, lh.getLH_TenLH());

            return pr.executeUpdate() > 0;
        }
    }

    public boolean xoaLH(String ma) throws Exception {
        String sql = "delete from LOAI_HANG where LH_MaLH = ?";
        try (
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);) {
            pr.setString(1, ma);
            return pr.executeUpdate() > 0;
        }
    }

    public List load_LH() throws Exception {
        String sql = "select * from LOAI_HANG";
        List<LoaiHang> list = new ArrayList<LoaiHang>();
        try (
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                LoaiHang lh = new LoaiHang();
                lh.setLH_MaLH(rs.getString("LH_MaLH"));
                lh.setLH_TenLH(rs.getString("LH_TenLH"));
                list.add(lh);
            }
            return list;
        }
    }
    public boolean update_LH(LoaiHang lh) throws Exception{
        String sql ="update LOAI_HANG set LH_TenLH = ? where LH_MaLH = ?";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            
            pr.setString(2, lh.getLH_MaLH());
            pr.setString(1, lh.getLH_TenLH());
            return pr.executeUpdate() > 0;
        }
    }
}
