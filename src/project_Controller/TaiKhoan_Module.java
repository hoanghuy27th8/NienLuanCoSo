/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_Controller;

import project_Model.TaiKhoan;
import Data.data_login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoàng Huy
 */
public class TaiKhoan_Module {
    public boolean themTK(String user, String Password, int ad) throws Exception{
        String sql ="insert into TAI_KHOAN(Username, Password, CheckAd) values (?,?,?)";
        try(
            Connection con = data_login.conect1();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(1, user);
            pr.setString(2, Password);
            pr.setInt(3, ad);
            
            return pr.executeUpdate()>0;
        }
    }
    
    public List loadTK() throws Exception{
        String sql = "select * from TAI_KHOAN";
        List<TaiKhoan> tk = new ArrayList<TaiKhoan>();
        try(
            Connection con = data_login.conect1();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                TaiKhoan otk = new TaiKhoan();
                otk.setUsername(rs.getString("Username"));
                otk.setPassword(rs.getString("Password"));
                otk.setAd(rs.getInt("CheckAd"));
                
                tk.add(otk);
            }
        }
        
        return tk;
    }
    
    public boolean capnhatTK(TaiKhoan tk) throws Exception{
        String sql = "update TAI_KHOAN set Password = ?, CheckAd = ? where Username = ?";
        try(
                Connection con = data_login.conect1();
                PreparedStatement pr = con.prepareStatement(sql);
                ){
            pr.setString(3,tk.getUsername());
            pr.setString(1,tk.getPassword());
            pr.setInt(2,tk.getAd());
            return pr.executeUpdate() >  0;
        }
    }
    
    public boolean xoaTK(String Username) throws Exception{
        String sql = "delete from TAI_KHOAN where Username = ?";
        try (
            Connection con = data_login.conect1();
            PreparedStatement pr = con.prepareStatement(sql);
                ){
            TaiKhoan_Module tk = new TaiKhoan_Module();
//            tk.xoaTK(Username);
            pr.setString(1, Username);
            return pr.executeUpdate()>0;
            
        } 
    }
}
