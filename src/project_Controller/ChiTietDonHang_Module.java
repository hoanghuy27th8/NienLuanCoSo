/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import Data.data_main;
import project_Model.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class ChiTietDonHang_Module {
    public boolean themCTDH(ChiTietDonHang c) throws Exception{
        String sql = "insert into CHITIETDONHANG (DH_MaDH, HH_MaHH, CTDH_SoLuong) values (?,?,?)";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, c.getDH_MaDH());
            pr.setString(2, c.getMa_HH());
            pr.setInt(3, c.getSoluong());
            return pr.executeUpdate() >0;
        }
    }
    public List<ChiTietDonHang> getDataCTDH() throws Exception{
        String sql ="select * from CHITIETDONHANG";
        try(
                Connection con = data_main.connect_main();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            List<ChiTietDonHang> lctdh = new ArrayList<ChiTietDonHang>();
            while(rs.next()){
                ChiTietDonHang ctdh = new ChiTietDonHang();
                ctdh.setDH_MaDH(rs.getString("DH_MaDH"));
                ctdh.setMa_HH(rs.getString("HH_MaHH"));
                ctdh.setSoluong(rs.getInt("CTDH_SoLuong"));
                lctdh.add(ctdh);
            }
            return lctdh;
        }
    }
}
