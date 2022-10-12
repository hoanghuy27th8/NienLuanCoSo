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
public class TaiKhoan {
    String username, password;
    int ad;

    public TaiKhoan() {
    }

    public TaiKhoan(String username, String password, int ad) {
        this.username = username;
        this.password = password;
        this.ad = ad;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    
    
}
