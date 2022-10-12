/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Hoàng Huy
 */
public class data_login {
    public static Connection conect1() throws Exception{
        String url ="jdbc:sqlserver://localhost;database=QL_BanHang";
        String user="sa";
        String pass="123";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection c = DriverManager.getConnection(url, user, pass);
        return c;
    }
    
    public boolean kiemtra(String username, String password, int ad){
        try
            {
            Connection c = conect1();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select * from TAI_KHOAN");
            while(rs.next()){ 
                if(rs.getString(1).equals(username) && rs.getString(2).equals(password) && rs.getInt(3)==ad) 
                    return true;
            }
        }catch(Exception e){
        }
        return false;
    }
//    public void in() throws Exception{
//        Connection c = conect1();
//        Statement st = c.createStatement();
//        ResultSet rs = st.executeQuery("select * from TAI_KHOAN");
//        
//        while(rs.next()){
//            if(rs.getInt(3)==1) System.out.println("dung");
////            System.out.println(rs.getString(1)+"\n" + rs.getString(2)+"\n"+ rs.getInt(3));
//       }
//    }
//    public static void main(String[] args) throws Exception {
//        data_login d = new data_login();
//        d.in();
////        String a = "b1906677";
////        String b="123";
////        int ad=1;
////        if(d.kiemtra(a, a, ad)) System.out.println("dung");
////        else System.out.println("sai");
//    }
}
