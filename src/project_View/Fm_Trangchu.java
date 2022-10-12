/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_View;

import Data.data_main;
import project_Controller.ChiTietDonHang_Module;
import project_Controller.DonHang_Module;
import project_Model.HangHoa;
import project_Controller.HangHoa_Module;
import project_Controller.KhachHang_Module;
import project_Model.LoaiHang;
import project_Controller.LoaiHang_Module;
import project_Model.NhanVien;
import project_Controller.NhanVien_Module;
import project_Model.TaiKhoan;
import project_Controller.TaiKhoan_Module;
import project_Model.ChiTietDonHang;
import project_Model.DonHang;
import project_Model.KhachHang;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Hoàng Huy
 */
public class Fm_Trangchu extends javax.swing.JFrame {

    public static String user, pass;
    public static int ad;
    public DefaultTableModel tbl;
    public DefaultTableModel tbl_tk;
    public DefaultTableModel tbl_lh;
    public DefaultTableModel tbl_hh;
    public DefaultTableModel tbl_timSp;
    public DefaultTableModel tbl_SPDonhang;
    public DefaultTableModel tbl_TKSP_Het;
    public DefaultTableModel tbl_DHTimThay;
    public DefaultTableModel tbl_CTDH_TimThay;
    public List<NhanVien> lnv;
    public List<TaiKhoan> TK;
    public List<LoaiHang> list_LH;
    public List<HangHoa> list_HH;
    public List<DonHang> list_DH;
    public List<ChiTietDonHang> list_SpDHtam = new ArrayList<ChiTietDonHang>();
    public List<ChiTietDonHang> list_TK_CTDH = new ArrayList<ChiTietDonHang>();
    public String img_nv = null; // name anh nhan vien
    public String img_hh = null; // name anh hang hoa

    /**
     * Creates new form Fm_Home_Admin
     */
    public Fm_Trangchu(String user1, String pass1, int ad1) {
        user = user1;
        pass = pass1;
        ad = ad1;
        initComponents();
        creatable_NV();
        creatable_TK();
        creatableLH();
        creatableHH();
        creatableSPTam();
        creatable_SPTK_het();
        creatable_DH();
        creatable_CTDH();
        this.setTitle("Trang chủ");
        setLocationRelativeTo(null);
        String url_img = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\src\\Img\\logo_login.png";
        ImageIcon img = new ImageIcon(new ImageIcon(url_img).getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_SMOOTH));
        lbl_logo.setIcon(img);
        this.setResizable(false);
    }

    // load dữ liệu từ sql lên bảng nhân viên
    public void creatable_NV() {
        String cot_nv[] = {"Mã nhân viên", "Tên nhân viên", "SDT", "Địa chỉ", "Quyền", "TK_Username", "TK_Password", "Path_img"};
        tbl = new DefaultTableModel();
        tbl.setColumnIdentifiers(cot_nv);
        tbl_nhanvien.setModel(tbl);
    }

    public void creatable_TK() {
        String cot_tk[] = {"Tài khoản", "Mật khẩu", "Quyền"};
        tbl_tk = new DefaultTableModel();
        tbl_tk.setColumnIdentifiers(cot_tk);
        tbl_taikhoan.setModel(tbl_tk);
    }

    public void creatableLH() {
        String column[] = {"Mã loại hàng", "Tên loại hàng"};
        tbl_lh = new DefaultTableModel();
        tbl_lh.setColumnIdentifiers(column);
        tbl_LoaiHang.setModel(tbl_lh);
    }

    public void creatableHH() {
        String column[] = {"Mã hàng hóa", "Tên hàng hóa", "Số lượng còn", "Mã loại hàng", "Giá", "Ngày áp dụng", "Path_img"};
        tbl_hh = new DefaultTableModel();
        tbl_hh.setColumnIdentifiers(column);
        tbl_HangHoa.setModel(tbl_hh);
        tbl_TKSP.setModel(tbl_hh);
    }

    public void creatableSPTam() {
        String column[] = {"Mã hàng hóa", "Tên hàng hóa", "Mã loại", "Số lượng", "Giá"};
        tbl_SPDonhang = new DefaultTableModel();
        tbl_SPDonhang.setColumnIdentifiers(column);
        tbl_SPchoDH.setModel(tbl_SPDonhang);
    }

    public void creatable_SPTK_het() {
        String cot_sp[] = {"Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng hiện tại"};
        tbl_TKSP_Het = new DefaultTableModel();
        tbl_TKSP_Het.setColumnIdentifiers(cot_sp);
        tbl_SPTK_BoSung.setForeground(Color.red);
        tbl_SPTK_BoSung.setModel(tbl_TKSP_Het);
    }

    public void creatable_DH() {
        String cot_dh[] = {"Mã đơn hàng", "Ngày lập","Người bán", "Khách hàng" ,"Tổng tiền"};
        tbl_DHTimThay = new DefaultTableModel();
        tbl_DHTimThay.setColumnIdentifiers(cot_dh);
        tbl_DHTimkiem.setModel(tbl_DHTimThay);
    }

    public void creatable_CTDH() {
        String cot_ctdh[] = {"Mã sản phẩm", "Tên sản phẩm", "Sô lượng"};
        tbl_CTDH_TimThay = new DefaultTableModel();
        tbl_CTDH_TimThay.setColumnIdentifiers(cot_ctdh);
        tbl_ChiTietDonHang.setModel(tbl_CTDH_TimThay);
    }
// test git thôi chứ không có gì
    public void data_table_DH() throws Exception { // bảng dữ liệu  toàn bộ đơn hàng khi vừa mở form đã có. Để gọi lại khi ấn nút Làm mới
        DonHang_Module dhm = new DonHang_Module();
        ChiTietDonHang_Module ctdhm = new ChiTietDonHang_Module();
        KhachHang_Module khm = new KhachHang_Module();
        list_TK_CTDH = ctdhm.getDataCTDH();
        list_DH = dhm.getDataDonHang();
        while (tbl_DHTimThay.getRowCount() > 0) {
            tbl_DHTimThay.removeRow(0);
        }
        List<KhachHang> list_KH = khm.loadKH();
        tbl_DHTimkiem.setSelectionBackground(new Color(0, 153, 153));
        tbl_ChiTietDonHang.setSelectionBackground(new Color(0, 153, 153));
        for (DonHang dh : list_DH) {
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String day = s.format(dh.getDH_NgayLap());
            String tenKH = new String();
            for (KhachHang kh : list_KH) {
                if (kh.getKH_MaKH().equals(dh.getKH_MaKH())) {
                    tenKH = kh.getKH_TenKH();
                    break;
                }
            }
            tbl_DHTimThay.addRow(new String[]{dh.getDH_MaDH(), day, dh.getNV_MaNV(), tenKH, String.valueOf(dh.getDH_TongTien())});
        }
        tbl_DHTimThay.fireTableDataChanged();
    }

    public void data_table_DH2() throws Exception {// đưa các đơn hàng vừa tìm kiếm được khi ấn nút tìm kiếm vào đây 
        DonHang_Module dhm = new DonHang_Module();
        ChiTietDonHang_Module ctdhm = new ChiTietDonHang_Module();
        KhachHang_Module khm = new KhachHang_Module();
        list_TK_CTDH = ctdhm.getDataCTDH();
        while (tbl_DHTimThay.getRowCount() > 0) {
            tbl_DHTimThay.removeRow(0);
        }
        List<KhachHang> list_KH = khm.loadKH();
        tbl_DHTimkiem.setSelectionBackground(new Color(0, 153, 153));
        tbl_ChiTietDonHang.setSelectionBackground(new Color(0, 153, 153));
        for (DonHang dh : list_DH) {
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String day = s.format(dh.getDH_NgayLap());
            String tenKH = new String();
            for (KhachHang kh : list_KH) {
                if (kh.getKH_MaKH().equals(dh.getKH_MaKH())) {
                    tenKH = kh.getKH_TenKH();
                    break;
                }
            }
            tbl_DHTimThay.addRow(new String[]{dh.getDH_MaDH(), day, dh.getNV_MaNV(), tenKH, String.valueOf(dh.getDH_TongTien())});
        }
        tbl_DHTimThay.fireTableDataChanged();
    }

    public void data_table_LH() throws Exception {
        LoaiHang_Module lhm = new LoaiHang_Module();
        list_LH = lhm.load_LH();
        while (tbl_lh.getRowCount() > 0) {
            tbl_lh.removeRow(0);
        }
        tbl_LoaiHang.setSelectionBackground(new Color(0, 153, 153));
        for (LoaiHang lh : list_LH) {
            tbl_lh.addRow(new String[]{lh.getLH_MaLH(), lh.getLH_TenLH()});
        }
        tbl_lh.fireTableDataChanged();
    }

    public void data_table_nv() throws Exception {
        NhanVien_Module nvm = new NhanVien_Module();
        lnv = nvm.load_data_nv();
        while (tbl.getRowCount() > 0) {
            tbl.removeRow(0);
        }
        tbl_nhanvien.setSelectionBackground(new Color(0, 153, 153));
        String quyen = new String();
        String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_NhanVien\\";
        for (NhanVien nv : lnv) {
            quyen = nv.getNV_Ad() > 0 ? "Quản lý" : "Nhân Viên";
            tbl.addRow(new String[]{nv.getNV_MaNV(), nv.getNV_TenNV(), nv.getNV_SDT(), nv.getNV_DiaChi().trim(), quyen, nv.getNV_Username(), nv.getNV_Password(), nv.getNV_img()});
        }

        tbl.fireTableDataChanged();
    }

    public void data_table_tk() throws Exception {
        TaiKhoan_Module tkm = new TaiKhoan_Module();
        TK = tkm.loadTK();
        while (tbl_tk.getRowCount() > 0) {
            tbl_tk.removeRow(0);
        }
        tbl_taikhoan.setSelectionBackground(new Color(0, 153, 153));
        String quyen = new String();
        for (TaiKhoan taikhoan : TK) {
            quyen = taikhoan.getAd() > 0 ? "Quản lý" : "Nhân Viên";
            tbl_tk.addRow(new String[]{taikhoan.getUsername(), taikhoan.getPassword(), quyen});
        }
        tbl_tk.fireTableDataChanged();
    }

    public void data_table_hh() throws Exception {
        HangHoa_Module hhm = new HangHoa_Module();
        list_HH = hhm.loadHH();
        while (tbl_hh.getRowCount() > 0) {
            tbl_hh.removeRow(0);
        }
        tbl_HangHoa.setSelectionBackground(new Color(0, 153, 153));
        for (HangHoa hh : list_HH) {
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String day = s.format(hh.getNgayApDung());
            tbl_hh.addRow(new String[]{hh.getHH_MaHH(), hh.getHH_TenHH(), String.valueOf(hh.getHH_Slcon()), hh.getHH_MaLoaiHH(), String.valueOf(hh.getHH_Gia()), day, hh.getHH_imgHH()});
        }
        tbl_hh.fireTableDataChanged();
    }

    public void data_table_dhTam() {
        while (tbl_SPDonhang.getRowCount() > 0) {
            tbl_SPDonhang.removeRow(0);
        }
        tbl_SPchoDH.setSelectionBackground(new Color(0, 153, 153));
        for (ChiTietDonHang ct : list_SpDHtam) {
            for (HangHoa h : list_HH) {
                if (h.getHH_MaHH().equals(ct.getMa_HH())) {
                    tbl_SPDonhang.addRow(new String[]{h.getHH_MaHH(), h.getHH_TenHH(), h.getHH_MaLoaiHH(), String.valueOf(ct.getSoluong()), String.valueOf(h.getHH_Gia())});
                    continue;
                }
            }
        }
        tbl_SPDonhang.fireTableDataChanged();
    }

    public void data_table_TKSP_BS() {
        while (tbl_TKSP_Het.getRowCount() > 0) {
            tbl_TKSP_Het.removeRow(0);
        }
        for (HangHoa h : list_HH) {
            if (h.getHH_Slcon() <= 0) {
                tbl_TKSP_Het.addRow(new String[]{h.getHH_MaHH(), h.getHH_TenHH(), String.valueOf(h.getHH_Gia()), String.valueOf(h.getHH_Slcon())});
            }
        }
        tbl_TKSP_Het.fireTableDataChanged();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Menu = new javax.swing.JPanel();
        menu_sanpham = new javax.swing.JLabel();
        menu_Nhanvien = new javax.swing.JLabel();
        menu_BanHang = new javax.swing.JLabel();
        menu_ThongKe = new javax.swing.JLabel();
        img_NVDangNhap = new javax.swing.JLabel();
        txt_TenDangNhap = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        br_logo = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        pnl_SanPham = new javax.swing.JPanel();
        jtp_SanPham = new javax.swing.JTabbedPane();
        pnl_ThongTinSP = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenHH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        img_sp = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_GiaHH = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_SLT = new javax.swing.JTextField();
        btn_ResertHH = new javax.swing.JButton();
        btn_LuuHH = new javax.swing.JButton();
        btn_SuaHH = new javax.swing.JButton();
        btn_XoaHH = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_HangHoa = new javax.swing.JTable();
        cbo_LoaiSP = new javax.swing.JComboBox<>();
        btn_anhSP = new javax.swing.JButton();
        btn_InSP = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txt_HH_NgayApDung = new com.toedter.calendar.JDateChooser();
        btn_ThemHH = new javax.swing.JButton();
        pnl_LoaiSP = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_resert_LH = new javax.swing.JButton();
        btn_ThemLoai = new javax.swing.JButton();
        btn_SuaLoai = new javax.swing.JButton();
        btn_XoaLoai = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_LoaiHang = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txt_MaLoai = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_TenLoai = new javax.swing.JTextField();
        pnl_QLNV = new javax.swing.JTabbedPane();
        QLNV = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_nhanvien = new javax.swing.JTable();
        img_nhanvien = new javax.swing.JLabel();
        btn_themanh_NV = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_TenNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_SDT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_Diachi = new javax.swing.JTextField();
        btn_CapNhatNV = new javax.swing.JButton();
        btn_LuuNV = new javax.swing.JButton();
        btn_XoaNV = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_quyen = new javax.swing.JTextField();
        btn_InNV = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        txt_Password = new javax.swing.JTextField();
        btn_lammoiNV = new javax.swing.JButton();
        btn_ThemNV = new javax.swing.JButton();
        TaiKhoan = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        ql_Username = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        ql_password = new javax.swing.JTextField();
        chk_ADTK = new javax.swing.JCheckBox();
        btn_themTK = new javax.swing.JButton();
        btn_xoaTK = new javax.swing.JButton();
        btn_capNhatTK = new javax.swing.JButton();
        btn_lammoiTK = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_taikhoan = new javax.swing.JTable();
        pnl_BanHang = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_MaNV_BH = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenNV_BH = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_MaDH = new javax.swing.JTextField();
        txtNgayLap = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_MaKH = new javax.swing.JTextField();
        txt_TenKH = new javax.swing.JTextField();
        txt_Dc_KH = new javax.swing.JTextField();
        txt_SĐT_KH = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_MaSp = new javax.swing.JTextField();
        txt_TenSP = new javax.swing.JTextField();
        btn_TangSL = new javax.swing.JButton();
        btn_GiamSL = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_SpTimKiem = new javax.swing.JTable();
        btn_ThemSPvaoDH = new javax.swing.JButton();
        cboTimKiemSP = new javax.swing.JComboBox<>();
        btn_timSP = new javax.swing.JButton();
        btn_XoaSPTam = new javax.swing.JButton();
        txt_SL = new javax.swing.JTextField();
        txt_ThanhTien = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_TongGiam = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        btn_InHD = new javax.swing.JButton();
        btn_LuuHD = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_SPchoDH = new javax.swing.JTable();
        btn_LamMoiDH = new javax.swing.JButton();
        jtp_thongke = new javax.swing.JTabbedPane();
        pnl_TKSP = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_TKSP = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txt_SPTK_Con = new javax.swing.JLabel();
        txt_SPTK_BS = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_SPTK_BoSung = new javax.swing.JTable();
        pnl_TKDH = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txt_TKmaDH = new javax.swing.JTextField();
        btn_timMadon = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_NgayTKCanDuoi = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_TongDHTimThay = new javax.swing.JLabel();
        txt_TongGiaTriDHTK = new javax.swing.JLabel();
        txt_NgayTKCanTren = new com.toedter.calendar.JDateChooser();
        btn_TimkiemTK = new javax.swing.JButton();
        btn_LammoiTK = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_DHTimkiem = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_ChiTietDonHang = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_sanpham.setBackground(new java.awt.Color(0, 153, 153));
        menu_sanpham.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menu_sanpham.setForeground(new java.awt.Color(255, 255, 255));
        menu_sanpham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_sanpham.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\san_pham.png")); // NOI18N
        menu_sanpham.setText("Quản Lý Sản Phẩm");
        menu_sanpham.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        menu_sanpham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menu_sanpham.setOpaque(true);
        menu_sanpham.setPreferredSize(new java.awt.Dimension(132, 121));
        menu_sanpham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menu_sanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_sanphamMousePressed(evt);
            }
        });

        menu_Nhanvien.setBackground(new java.awt.Color(0, 153, 153));
        menu_Nhanvien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menu_Nhanvien.setForeground(new java.awt.Color(255, 255, 255));
        menu_Nhanvien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_Nhanvien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Account.png"))); // NOI18N
        menu_Nhanvien.setText("Quản Lý Nhân Viên");
        menu_Nhanvien.setToolTipText("");
        menu_Nhanvien.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        menu_Nhanvien.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu_Nhanvien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menu_Nhanvien.setOpaque(true);
        menu_Nhanvien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menu_Nhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_NhanvienMousePressed(evt);
            }
        });

        menu_BanHang.setBackground(new java.awt.Color(0, 153, 153));
        menu_BanHang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menu_BanHang.setForeground(new java.awt.Color(255, 255, 255));
        menu_BanHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_BanHang.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\shopping-icon.png")); // NOI18N
        menu_BanHang.setText("Quản Lý Bán Hàng");
        menu_BanHang.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        menu_BanHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menu_BanHang.setOpaque(true);
        menu_BanHang.setPreferredSize(new java.awt.Dimension(132, 121));
        menu_BanHang.setVerifyInputWhenFocusTarget(false);
        menu_BanHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menu_BanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_BanHangMousePressed(evt);
            }
        });

        menu_ThongKe.setBackground(new java.awt.Color(0, 153, 153));
        menu_ThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menu_ThongKe.setForeground(new java.awt.Color(255, 255, 255));
        menu_ThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_ThongKe.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\thong-ke.png")); // NOI18N
        menu_ThongKe.setText("Quản lý Đơn Hàng & Thống kê");
        menu_ThongKe.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        menu_ThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menu_ThongKe.setOpaque(true);
        menu_ThongKe.setPreferredSize(new java.awt.Dimension(132, 121));
        menu_ThongKe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menu_ThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_ThongKeMousePressed(evt);
            }
        });

        img_NVDangNhap.setBackground(new java.awt.Color(204, 51, 0));
        img_NVDangNhap.setOpaque(true);

        txt_TenDangNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_TenDangNhap.setText("jLabel38");

        jLabel5.setBackground(new java.awt.Color(255, 51, 51));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Thoát");
        jLabel5.setOpaque(true);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });

        lbl_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_logo.setOpaque(true);

        javax.swing.GroupLayout br_logoLayout = new javax.swing.GroupLayout(br_logo);
        br_logo.setLayout(br_logoLayout);
        br_logoLayout.setHorizontalGroup(
            br_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(br_logoLayout.createSequentialGroup()
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        br_logoLayout.setVerticalGroup(
            br_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, br_logoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnl_MenuLayout = new javax.swing.GroupLayout(pnl_Menu);
        pnl_Menu.setLayout(pnl_MenuLayout);
        pnl_MenuLayout.setHorizontalGroup(
            pnl_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu_sanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_BanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_ThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_MenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(img_NVDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(br_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_Nhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_MenuLayout.setVerticalGroup(
            pnl_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_MenuLayout.createSequentialGroup()
                .addComponent(br_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_Nhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(menu_BanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(menu_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(menu_ThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(pnl_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(img_NVDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_TenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(pnl_Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 910));

        pnl_SanPham.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jtp_SanPham.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jtp_SanPham.setToolTipText("");
        jtp_SanPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Mã sản phẩm:");

        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Tên sản phẩm:");

        txtTenHH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Loại sản phẩm:");

        img_sp.setBackground(new java.awt.Color(255, 255, 255));
        img_sp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        img_sp.setForeground(new java.awt.Color(255, 255, 255));
        img_sp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_sp.setText("Img_Sản Phẩm");
        img_sp.setOpaque(true);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Ngày áp dụng:");

        txt_GiaHH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Số lượng:");

        txt_SLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_ResertHH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ResertHH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_ResertHH.setText("Làm mới");
        btn_ResertHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResertHHActionPerformed(evt);
            }
        });

        btn_LuuHH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LuuHH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\save.png")); // NOI18N
        btn_LuuHH.setText("Lưu");
        btn_LuuHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuHHActionPerformed(evt);
            }
        });

        btn_SuaHH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_SuaHH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\update.png")); // NOI18N
        btn_SuaHH.setText("Cập nhật");
        btn_SuaHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaHHActionPerformed(evt);
            }
        });

        btn_XoaHH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_XoaHH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\trash.png")); // NOI18N
        btn_XoaHH.setText("Xóa");
        btn_XoaHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaHHActionPerformed(evt);
            }
        });

        tbl_HangHoa.setAutoCreateRowSorter(true);
        tbl_HangHoa.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_HangHoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_HangHoa.setRowHeight(30);
        tbl_HangHoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HangHoaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_HangHoa);

        cbo_LoaiSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_LoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_anhSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_anhSP.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\Folder-Add-icon.png")); // NOI18N
        btn_anhSP.setText("Cập nhật ảnh");
        btn_anhSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anhSPActionPerformed(evt);
            }
        });

        btn_InSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_InSP.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\print.png")); // NOI18N
        btn_InSP.setText("In ds Sp");
        btn_InSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InSPActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Giá:");

        txt_HH_NgayApDung.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_ThemHH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ThemHH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\add.png")); // NOI18N
        btn_ThemHH.setText("Thêm");
        btn_ThemHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemHHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_ThongTinSPLayout = new javax.swing.GroupLayout(pnl_ThongTinSP);
        pnl_ThongTinSP.setLayout(pnl_ThongTinSPLayout);
        pnl_ThongTinSPLayout.setHorizontalGroup(
            pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ThongTinSPLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(img_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_anhSP))
                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(16, 16, 16)
                                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenHH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbo_LoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(55, 55, 55)
                                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel13))
                                        .addGap(65, 65, 65)
                                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_SLT, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_HH_NgayApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_GiaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(164, 164, 164))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ThongTinSPLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_ResertHH)
                                .addGap(34, 34, 34)
                                .addComponent(btn_LuuHH, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btn_ThemHH)
                                .addGap(32, 32, 32)
                                .addComponent(btn_SuaHH)
                                .addGap(27, 27, 27)
                                .addComponent(btn_XoaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btn_InSP)
                                .addGap(3, 3, 3))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1086, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        pnl_ThongTinSPLayout.setVerticalGroup(
            pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ThongTinSPLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(img_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ThongTinSPLayout.createSequentialGroup()
                                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(txt_SLT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addComponent(jLabel7)
                                .addGap(40, 40, 40))
                            .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenHH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(txt_GiaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)))
                        .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(cbo_LoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_ThongTinSPLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(11, 11, 11))
                            .addComponent(txt_HH_NgayApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_anhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_ThongTinSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_ResertHH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_LuuHH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_SuaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_XoaHH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_InSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ThemHH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtp_SanPham.addTab("     Thông tin sản phẩm     ", pnl_ThongTinSP);

        btn_resert_LH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_resert_LH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_resert_LH.setText("Resert");
        btn_resert_LH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resert_LHActionPerformed(evt);
            }
        });

        btn_ThemLoai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ThemLoai.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\add.png")); // NOI18N
        btn_ThemLoai.setText("Thêm");
        btn_ThemLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemLoaiActionPerformed(evt);
            }
        });

        btn_SuaLoai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_SuaLoai.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\update.png")); // NOI18N
        btn_SuaLoai.setText("Cập nhật");
        btn_SuaLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaLoaiActionPerformed(evt);
            }
        });

        btn_XoaLoai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_XoaLoai.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\trash.png")); // NOI18N
        btn_XoaLoai.setText("Xóa");
        btn_XoaLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaLoaiActionPerformed(evt);
            }
        });

        tbl_LoaiHang.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_LoaiHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_LoaiHang.setRowHeight(25);
        tbl_LoaiHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_LoaiHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_LoaiHang);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Mã Loại:");

        txt_MaLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Tên loại");

        txt_TenLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_resert_LH)
                        .addGap(85, 85, 85)
                        .addComponent(btn_ThemLoai)
                        .addGap(98, 98, 98)
                        .addComponent(btn_SuaLoai)
                        .addGap(81, 81, 81)
                        .addComponent(btn_XoaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(160, 160, 160))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(txt_MaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(txt_TenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 323, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txt_MaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txt_TenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_resert_LH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SuaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XoaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_LoaiSPLayout = new javax.swing.GroupLayout(pnl_LoaiSP);
        pnl_LoaiSP.setLayout(pnl_LoaiSPLayout);
        pnl_LoaiSPLayout.setHorizontalGroup(
            pnl_LoaiSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_LoaiSPLayout.setVerticalGroup(
            pnl_LoaiSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtp_SanPham.addTab("     Loại sản phẩm     ", pnl_LoaiSP);

        pnl_QLNV.setToolTipText("");
        pnl_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_QLNV.setName(""); // NOI18N
        pnl_QLNV.setOpaque(true);
        pnl_QLNV.setPreferredSize(new java.awt.Dimension(1113, 896));

        QLNV.setPreferredSize(new java.awt.Dimension(1125, 915));

        tbl_nhanvien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tbl_nhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_nhanvien.setRowHeight(30);
        tbl_nhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_nhanvienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_nhanvien);

        img_nhanvien.setBackground(new java.awt.Color(255, 255, 255));
        img_nhanvien.setForeground(new java.awt.Color(0, 153, 0));
        img_nhanvien.setOpaque(true);

        btn_themanh_NV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_themanh_NV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\Folder-Add-icon.png")); // NOI18N
        btn_themanh_NV.setText("Tải ảnh");
        btn_themanh_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themanh_NVActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Họ tên nhân viên: ");

        txt_TenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã nhân viên:");

        txt_MaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("SDT:");

        txt_SDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Địa chỉ:");

        txt_Diachi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_CapNhatNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_CapNhatNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\update.png")); // NOI18N
        btn_CapNhatNV.setText("Cập nhật");
        btn_CapNhatNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhatNVActionPerformed(evt);
            }
        });

        btn_LuuNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LuuNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\save.png")); // NOI18N
        btn_LuuNV.setText("Lưu");
        btn_LuuNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuNVActionPerformed(evt);
            }
        });

        btn_XoaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_XoaNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\trash.png")); // NOI18N
        btn_XoaNV.setText("Xóa");
        btn_XoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaNVActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Quyền:");

        txt_quyen.setEditable(false);
        txt_quyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quyen.setOpaque(false);

        btn_InNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_InNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\print.png")); // NOI18N
        btn_InNV.setText("In danh sách");
        btn_InNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InNVActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Username:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Password");

        txt_Username.setEditable(false);
        txt_Username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Username.setOpaque(false);

        txt_Password.setEditable(false);
        txt_Password.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Password.setOpaque(false);

        btn_lammoiNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_lammoiNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_lammoiNV.setText("Làm mới");
        btn_lammoiNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiNVActionPerformed(evt);
            }
        });

        btn_ThemNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ThemNV.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\add.png")); // NOI18N
        btn_ThemNV.setText("Thêm");
        btn_ThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout QLNVLayout = new javax.swing.GroupLayout(QLNV);
        QLNV.setLayout(QLNVLayout);
        QLNVLayout.setHorizontalGroup(
            QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLNVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(QLNVLayout.createSequentialGroup()
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(QLNVLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(img_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(QLNVLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel9)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QLNVLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1))))
                            .addGroup(QLNVLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btn_themanh_NV)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_LuuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(QLNVLayout.createSequentialGroup()
                                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_MaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addComponent(txt_TenNV)
                                    .addComponent(txt_SDT)
                                    .addComponent(txt_Diachi))
                                .addGap(89, 89, 89)
                                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(QLNVLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(QLNVLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QLNVLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(QLNVLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_CapNhatNV)
                                .addGap(35, 35, 35)
                                .addComponent(btn_XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btn_lammoiNV)
                                .addGap(33, 33, 33)
                                .addComponent(btn_InNV)))))
                .addContainerGap())
        );
        QLNVLayout.setVerticalGroup(
            QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QLNVLayout.createSequentialGroup()
                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(QLNVLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(img_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(QLNVLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel11)
                                .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel28)
                            .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel32)
                            .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Diachi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addGap(24, 24, 24)
                .addGroup(QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themanh_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_CapNhatNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_InNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lammoiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LuuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_QLNV.addTab("   Thông tin nhân viên   ", QLNV);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Username:");

        ql_Username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Password");

        ql_password.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chk_ADTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chk_ADTK.setText("Check Admin");

        btn_themTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_themTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\add.png")); // NOI18N
        btn_themTK.setText("Thêm");
        btn_themTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themTKActionPerformed(evt);
            }
        });

        btn_xoaTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_xoaTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\trash.png")); // NOI18N
        btn_xoaTK.setText("Xóa");
        btn_xoaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaTKActionPerformed(evt);
            }
        });

        btn_capNhatTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_capNhatTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\update.png")); // NOI18N
        btn_capNhatTK.setText("Cập nhật");
        btn_capNhatTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capNhatTKActionPerformed(evt);
            }
        });

        btn_lammoiTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_lammoiTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_lammoiTK.setText("Làm mới");
        btn_lammoiTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiTKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ql_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ql_password, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addComponent(chk_ADTK)
                                .addGap(331, 331, 331))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(btn_themTK)
                                .addGap(78, 78, 78)
                                .addComponent(btn_capNhatTK)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                                .addComponent(btn_xoaTK)
                                .addGap(102, 102, 102)))
                        .addComponent(btn_lammoiTK)))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33)
                    .addComponent(ql_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ql_password, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addComponent(chk_ADTK)
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themTK)
                    .addComponent(btn_capNhatTK)
                    .addComponent(btn_xoaTK)
                    .addComponent(btn_lammoiTK))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        tbl_taikhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_taikhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_taikhoan.setRowHeight(25);
        tbl_taikhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_taikhoanMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_taikhoan);

        javax.swing.GroupLayout TaiKhoanLayout = new javax.swing.GroupLayout(TaiKhoan);
        TaiKhoan.setLayout(TaiKhoanLayout);
        TaiKhoanLayout.setHorizontalGroup(
            TaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        TaiKhoanLayout.setVerticalGroup(
            TaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_QLNV.addTab("   Tài khoản   ", TaiKhoan);

        pnl_BanHang.setPreferredSize(new java.awt.Dimension(1125, 915));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin cơ bản", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel3.setToolTipText("a");
        jPanel3.setEnabled(false);
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.setName(""); // NOI18N

        txt_MaNV_BH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Tên nhân viên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mã đơn hàng:");

        txtTenNV_BH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Ngày lập:");

        txt_MaDH.setEditable(false);
        txt_MaDH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNgayLap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Mã Nhân viên:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Mã khách hàng:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Tên khách hàng:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("SĐT:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Địa chỉ:");

        txt_MaKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_TenKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_Dc_KH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_SĐT_KH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SĐT_KH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_SĐT_KHCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_MaNV_BH)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(txt_MaDH, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenNV_BH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_MaKH)
                    .addComponent(txt_TenKH)
                    .addComponent(txt_Dc_KH)
                    .addComponent(txt_SĐT_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(142, 142, 142))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txt_MaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(txt_TenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txt_Dc_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_SĐT_KH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_MaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_MaNV_BH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNV_BH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Tên sản phẩm:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Mã sản phẩm:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Số lượng:");

        txt_MaSp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_TenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_TangSL.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_TangSL.setText("+");
        btn_TangSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TangSLActionPerformed(evt);
            }
        });

        btn_GiamSL.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_GiamSL.setText("-");
        btn_GiamSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GiamSLActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Tìm kiếm:");

        tbl_SpTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_SpTimKiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Tên Hàng Hóa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_SpTimKiem.setRowHeight(30);
        tbl_SpTimKiem.setRowMargin(2);
        tbl_SpTimKiem.setSurrendersFocusOnKeystroke(true);
        tbl_SpTimKiem.setVerifyInputWhenFocusTarget(false);
        tbl_SpTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SpTimKiemMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_SpTimKiem);

        btn_ThemSPvaoDH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ThemSPvaoDH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\add.png")); // NOI18N
        btn_ThemSPvaoDH.setText("Thêm");
        btn_ThemSPvaoDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemSPvaoDHActionPerformed(evt);
            }
        });

        cboTimKiemSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_timSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_timSP.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\search.png")); // NOI18N
        btn_timSP.setText("Tìm kiếm");
        btn_timSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timSPActionPerformed(evt);
            }
        });

        btn_XoaSPTam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_XoaSPTam.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\trash.png")); // NOI18N
        btn_XoaSPTam.setText("Xóa");
        btn_XoaSPTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaSPTamActionPerformed(evt);
            }
        });

        txt_SL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SL.setText("0");
        txt_SL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_SLCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btn_GiamSL)
                                .addGap(8, 8, 8)
                                .addComponent(txt_SL, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_TangSL))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel29))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_MaSp)
                            .addComponent(cboTimKiemSP, 0, 226, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_timSP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_ThemSPvaoDH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_XoaSPTam, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(148, 148, 148))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cboTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timSP, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txt_MaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txt_TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel27)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_TangSL)
                        .addComponent(txt_SL, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_GiamSL, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ThemSPvaoDH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_XoaSPTam, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_ThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Thành tiền:");

        txt_TongGiam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Tổng giảm:");

        btn_InHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_InHD.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\print.png")); // NOI18N
        btn_InHD.setText("In đơn hàng");
        btn_InHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InHDActionPerformed(evt);
            }
        });

        btn_LuuHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LuuHD.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\save.png")); // NOI18N
        btn_LuuHD.setText("Lưu đơn hàng");
        btn_LuuHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuHDActionPerformed(evt);
            }
        });

        tbl_SPchoDH.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tbl_SPchoDH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_SPchoDH.setRowHeight(30);
        tbl_SPchoDH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SPchoDHMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_SPchoDH);

        btn_LamMoiDH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LamMoiDH.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_LamMoiDH.setText("Làm mới");
        btn_LamMoiDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiDHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_BanHangLayout = new javax.swing.GroupLayout(pnl_BanHang);
        pnl_BanHang.setLayout(pnl_BanHangLayout);
        pnl_BanHangLayout.setHorizontalGroup(
            pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_BanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_BanHangLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_BanHangLayout.createSequentialGroup()
                                .addComponent(btn_LamMoiDH)
                                .addGap(18, 18, 18)
                                .addComponent(btn_LuuHD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_InHD))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnl_BanHangLayout.createSequentialGroup()
                                    .addComponent(jLabel30)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_TongGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnl_BanHangLayout.createSequentialGroup()
                                    .addComponent(jLabel31)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        pnl_BanHangLayout.setVerticalGroup(
            pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_BanHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txt_TongGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txt_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_InHD, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LuuHD, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LamMoiDH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
        );

        jPanel3.getAccessibleContext().setAccessibleDescription("");

        jtp_thongke.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tbl_TKSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_TKSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_TKSP.setRowHeight(30);
        jScrollPane8.setViewportView(tbl_TKSP);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel40.setText("Tổng sản phẩm hiện có:");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Số sản phẩm cần bổ sung:");

        txt_SPTK_Con.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_SPTK_Con.setText("0");

        txt_SPTK_BS.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_SPTK_BS.setForeground(new java.awt.Color(255, 51, 51));
        txt_SPTK_BS.setText("0");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 102, 102));
        jLabel43.setText("Tổng quát");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_SPTK_BS)
                    .addComponent(txt_SPTK_Con, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel43)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txt_SPTK_Con))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txt_SPTK_BS))
                .addGap(48, 48, 48))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel5.setForeground(new java.awt.Color(102, 102, 102));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 102, 102));
        jLabel42.setText("Sản phẩm cần bổ sung");

        tbl_SPTK_BoSung.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_SPTK_BoSung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_SPTK_BoSung.setRowHeight(30);
        jScrollPane9.setViewportView(tbl_SPTK_BoSung);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(109, 109, 109))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_TKSPLayout = new javax.swing.GroupLayout(pnl_TKSP);
        pnl_TKSP.setLayout(pnl_TKSPLayout);
        pnl_TKSPLayout.setHorizontalGroup(
            pnl_TKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TKSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(pnl_TKSPLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_TKSPLayout.setVerticalGroup(
            pnl_TKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TKSPLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnl_TKSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtp_thongke.addTab("     Sản Phẩm     ", pnl_TKSP);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel6.setForeground(new java.awt.Color(240, 240, 240));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm theo mã đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tìm kiếm: ");

        txt_TKmaDH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_timMadon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_timMadon.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\search.png")); // NOI18N
        btn_timMadon.setText("Tìm kiếm");
        btn_timMadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timMadonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_TKmaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_timMadon)
                .addGap(10, 10, 10))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel10))
                    .addComponent(btn_timMadon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_TKmaDH, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm theo thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Từ:");

        txt_NgayTKCanDuoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Đến:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Tổng số đơn hàng tìm thấy:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Tổng giá trị đơn hàng tìm thấy:");

        txt_TongDHTimThay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_TongDHTimThay.setText("0");

        txt_TongGiaTriDHTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_TongGiaTriDHTK.setText("0");

        txt_NgayTKCanTren.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_TimkiemTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_TimkiemTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\search.png")); // NOI18N
        btn_TimkiemTK.setText("Tìm kiếm");
        btn_TimkiemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimkiemTKActionPerformed(evt);
            }
        });

        btn_LammoiTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LammoiTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\huyb1\\OneDrive\\Máy tính\\JAVA\\Test_PJ_Quanlybanhang\\icon-btn\\refresh.png")); // NOI18N
        btn_LammoiTK.setText("Làm mới");
        btn_LammoiTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LammoiTKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txt_NgayTKCanDuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(txt_NgayTKCanTren, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_TongDHTimThay, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_TongGiaTriDHTK, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_LammoiTK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_TimkiemTK)
                .addGap(34, 34, 34))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel35)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jLabel17))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(txt_NgayTKCanDuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txt_NgayTKCanTren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_TimkiemTK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LammoiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txt_TongDHTimThay))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txt_TongGiaTriDHTK))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbl_DHTimkiem.setAutoCreateRowSorter(true);
        tbl_DHTimkiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_DHTimkiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_DHTimkiem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_DHTimkiem.setRowHeight(30);
        tbl_DHTimkiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DHTimkiemMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_DHTimkiem);

        tbl_ChiTietDonHang.setAutoCreateRowSorter(true);
        tbl_ChiTietDonHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_ChiTietDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_ChiTietDonHang.setRowHeight(30);
        jScrollPane10.setViewportView(tbl_ChiTietDonHang);

        javax.swing.GroupLayout pnl_TKDHLayout = new javax.swing.GroupLayout(pnl_TKDH);
        pnl_TKDH.setLayout(pnl_TKDHLayout);
        pnl_TKDHLayout.setHorizontalGroup(
            pnl_TKDHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TKDHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_TKDHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_TKDHLayout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_TKDHLayout.setVerticalGroup(
            pnl_TKDHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_TKDHLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_TKDHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                    .addComponent(jScrollPane10))
                .addContainerGap())
        );

        jtp_thongke.addTab("     Đơn hàng     ", pnl_TKDH);

        javax.swing.GroupLayout pnl_SanPhamLayout = new javax.swing.GroupLayout(pnl_SanPham);
        pnl_SanPham.setLayout(pnl_SanPhamLayout);
        pnl_SanPhamLayout.setHorizontalGroup(
            pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1148, Short.MAX_VALUE)
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtp_SanPham))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_SanPhamLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnl_BanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 1124, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnl_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_SanPhamLayout.createSequentialGroup()
                    .addComponent(jtp_thongke)
                    .addContainerGap()))
        );
        pnl_SanPhamLayout.setVerticalGroup(
            pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 908, Short.MAX_VALUE)
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_SanPhamLayout.createSequentialGroup()
                    .addComponent(jtp_SanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_SanPhamLayout.createSequentialGroup()
                    .addComponent(pnl_BanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 897, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_SanPhamLayout.createSequentialGroup()
                    .addComponent(pnl_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 30, Short.MAX_VALUE)))
            .addGroup(pnl_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_SanPhamLayout.createSequentialGroup()
                    .addComponent(jtp_thongke)
                    .addContainerGap()))
        );

        pnl_QLNV.getAccessibleContext().setAccessibleName("");

        getContentPane().add(pnl_SanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 0, 1150, 910));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void load_combo_SP() {
        cbo_LoaiSP.removeAllItems();
        cboTimKiemSP.removeAllItems();
        for (LoaiHang lh : list_LH) {
            cbo_LoaiSP.addItem(lh.getLH_TenLH());
            cboTimKiemSP.addItem(lh.getLH_TenLH());
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            QLNV.setVisible(false);
            pnl_BanHang.setVisible(false);
            menu_sanpham.setBackground(new Color(0, 102, 102));
            String adress_img = "huy_string_C:\\Users\\huyb1\\OneDrive\\Hình ảnh\\icon JAVA\\nv2.jpg";
            String url_img = adress_img;
            ImageIcon img = new ImageIcon(new ImageIcon(url_img).getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_SMOOTH));
            img_sp.setIcon(img);
            btn_LuuNV.setEnabled(false);
            btn_LuuHH.setEnabled(false);
//            btn_InHD.setEnabled(false);
            data_table_nv();
            data_table_tk();
            data_table_LH();
            data_table_hh();
            data_table_DH();
            load_combo_SP();
            evenOpenWindowForDonHang();
            evenOpenWindowForThongKe();
            tbl_TKSP.setSelectionBackground(new Color(0, 153, 153));
            tbl_SPTK_BoSung.setSelectionBackground(new Color(0, 153, 153));
            for(NhanVien nv : lnv){
                if(nv.getNV_Username().equals(user)){
                    txt_TenDangNhap.setText(nv.getNV_TenNV());
                    String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_NhanVien\\";
                    ImageIcon iconNV = new ImageIcon(new ImageIcon(danC  + nv.getNV_img()).getImage().getScaledInstance(img_NVDangNhap.getWidth(), img_NVDangNhap.getHeight(), Image.SCALE_SMOOTH));
                    img_NVDangNhap.setIcon(iconNV);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_LuuNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuNVActionPerformed
        if (txt_MaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txt_MaNV.setBackground(Color.yellow);
            txt_MaNV.requestFocus();
            return;
        } else {
            txt_MaNV.setBackground(Color.white);
        }

        try {
            NhanVien nv = new NhanVien();
            nv.setNV_MaNV(txt_MaNV.getText());
            nv.setNV_DiaChi(txt_Diachi.getText());
            nv.setNV_SDT(txt_SDT.getText());
            nv.setNV_TenNV(txt_TenNV.getText());
//            nv.setNV_Username(txt_Username.getText());
            nv.setNV_img(img_nv);

            NhanVien_Module nvm = new NhanVien_Module();
            nvm.them(nv);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            data_table_nv();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_LuuNVActionPerformed

    private void tbl_nhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nhanvienMouseClicked
        int row = tbl_nhanvien.getSelectedRow();
        if (row >= 0) {
            String ma = (String) tbl_nhanvien.getValueAt(row, 0);
            for (NhanVien nv : lnv) {
                if (nv.getNV_MaNV().equals(ma)) {
                    txt_MaNV.setText(ma.trim());
                    txt_TenNV.setText(nv.getNV_TenNV().trim());
                    txt_SDT.setText(nv.getNV_SDT().trim());
                    txt_Diachi.setText(nv.getNV_DiaChi().trim());
                    txt_Username.setText(nv.getNV_Username().trim());
                    txt_Password.setText(nv.getNV_Password().trim());
                    String quyen = (nv.getNV_Ad() > 0) ? "Quản lý" : "Nhân Viên";
                    txt_quyen.setText(quyen);
                    String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_NhanVien\\";
                    ImageIcon img_nv = new ImageIcon(new ImageIcon(danC + nv.getNV_img()).getImage().getScaledInstance(img_nhanvien.getWidth(), img_nhanvien.getHeight(), Image.SCALE_SMOOTH));
                    img_nhanvien.setIcon(img_nv);
                }
            }
        }

    }//GEN-LAST:event_tbl_nhanvienMouseClicked

    private void btn_themanh_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themanh_NVActionPerformed
        JFileChooser choose = new JFileChooser("C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_NhanVien");
        choose.showOpenDialog(this);
        File f = choose.getSelectedFile();
        img_nv = f.getName();
        String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_NhanVien\\";
        ImageIcon img = new ImageIcon(new ImageIcon(danC + img_nv).getImage().getScaledInstance(img_nhanvien.getWidth(), img_nhanvien.getHeight(), Image.SCALE_SMOOTH));
        img_nhanvien.setIcon(img);
    }//GEN-LAST:event_btn_themanh_NVActionPerformed

    private void btn_lammoiNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiNVActionPerformed
        txt_MaNV.setText("");
        txt_TenNV.setText("");
        txt_SDT.setText("");
        txt_Diachi.setText("");
        txt_quyen.setText("");
        txt_Username.setText("");
        txt_Password.setText("");
        btn_ThemNV.setEnabled(true);
        btn_XoaNV.setEnabled(true);
        btn_CapNhatNV.setEnabled(true);
        btn_InNV.setEnabled(true);
        btn_LuuNV.setEnabled(false);
    }//GEN-LAST:event_btn_lammoiNVActionPerformed

    private void btn_themTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themTKActionPerformed
        TaiKhoan_Module tkm = new TaiKhoan_Module();
        int ad2 = chk_ADTK.isSelected() ? 1 : 0;
        try {
            tkm.themTK(ql_Username.getText(), ql_password.getText(), ad2);
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công");
            data_table_tk();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btn_themTKActionPerformed

    private void btn_capNhatTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capNhatTKActionPerformed
        try {
            TaiKhoan tk = new TaiKhoan();
            tk.setUsername(ql_Username.getText());
            tk.setPassword(ql_password.getText());
            tk.setAd(chk_ADTK.isSelected() ? 1 : 0);
            TaiKhoan_Module tkm = new TaiKhoan_Module();
            tkm.capnhatTK(tk);
            JOptionPane.showMessageDialog(this, "Đã cập nhật thành công tài khoản");
            data_table_tk();
            data_table_nv();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btn_capNhatTKActionPerformed

    private void btn_xoaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaTKActionPerformed
        try {
            TaiKhoan_Module tkm = new TaiKhoan_Module();
//            btn_XoaNVActionPerformed(evt);
            tkm.xoaTK(ql_Username.getText());
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            data_table_tk();
            data_table_nv();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btn_xoaTKActionPerformed

    private void btn_XoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaNVActionPerformed
        if (txt_MaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống Mã nhân viên");
            txt_MaNV.setBackground(Color.yellow);
            txt_MaNV.requestFocus();
            return;
        } else {
            txt_MaNV.setBackground(Color.white);
        }
        try {
            NhanVien_Module nvm = new NhanVien_Module();
            if (txt_MaNV.getText().equals("")) {
                nvm.xoanv(ql_Username.getText());
            } else {
                nvm.xoanv(txt_MaNV.getText());
            }
            JOptionPane.showMessageDialog(this, "Đã xóa nhân viên");
            data_table_nv();
            data_table_DH();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_XoaNVActionPerformed

    private void btn_lammoiTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiTKActionPerformed
        ql_Username.setText("");
        ql_password.setText("");
        chk_ADTK.setSelected(false);
    }//GEN-LAST:event_btn_lammoiTKActionPerformed

    private void btn_CapNhatNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhatNVActionPerformed
        try {
            NhanVien nv = new NhanVien();
            nv.setNV_MaNV(txt_MaNV.getText());
            nv.setNV_TenNV(txt_TenNV.getText());
            nv.setNV_DiaChi(txt_Diachi.getText());
            nv.setNV_SDT(txt_SDT.getText());
            nv.setNV_img(img_nv);
            NhanVien_Module nvm = new NhanVien_Module();
            nvm.capnhatNV(nv);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            data_table_nv();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngu");
        }
    }//GEN-LAST:event_btn_CapNhatNVActionPerformed

    private void tbl_taikhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_taikhoanMouseClicked
        int row = tbl_taikhoan.getSelectedRow();
        if (row >= 0) {
            String ma = (String) tbl_taikhoan.getValueAt(row, 0);
            for (TaiKhoan tk1 : TK) {
                if (tk1.getUsername().equals(ma)) {
                    ql_Username.setText(ma);
                    ql_password.setText(tk1.getPassword());
                    if (tk1.getAd() > 0) {
                        chk_ADTK.setSelected(true);
                    } else {
                        chk_ADTK.setSelected(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_tbl_taikhoanMouseClicked

    private void btn_ThemLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemLoaiActionPerformed
        try {
            LoaiHang lh = new LoaiHang();
            lh.setLH_MaLH(txt_MaLoai.getText());
            lh.setLH_TenLH(txt_TenLoai.getText());
            LoaiHang_Module lhm = new LoaiHang_Module();
            lhm.themLH(lh);
            JOptionPane.showMessageDialog(this, "Đã thêm loại hàng");
            data_table_LH();
            load_combo_SP();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_ThemLoaiActionPerformed

    private void btn_XoaLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaLoaiActionPerformed
        try {
            LoaiHang_Module lhm = new LoaiHang_Module();
            lhm.xoaLH(txt_MaLoai.getText());
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            data_table_LH();
            load_combo_SP();
            data_table_hh();
            data_table_DH();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_XoaLoaiActionPerformed

    private void btn_SuaLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaLoaiActionPerformed
        try {
            LoaiHang lh = new LoaiHang();
            lh.setLH_MaLH(txt_MaLoai.getText());
            lh.setLH_TenLH(txt_TenLoai.getText());

            LoaiHang_Module lhm = new LoaiHang_Module();
            lhm.update_LH(lh);
            JOptionPane.showMessageDialog(this, "Đã cập nhật tên của loại hàng: " + txt_MaLoai.getText());
            data_table_LH();
            load_combo_SP();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btn_SuaLoaiActionPerformed

    private void tbl_LoaiHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_LoaiHangMouseClicked
        int row = tbl_LoaiHang.getSelectedRow();
        if (row >= 0) {
            String maloai = (String) tbl_LoaiHang.getValueAt(row, 0);
            for (LoaiHang lh : list_LH) {
                if (lh.getLH_MaLH().equals(maloai)) {
                    txt_MaLoai.setText(lh.getLH_MaLH());
                    txt_TenLoai.setText(lh.getLH_TenLH());
                }
            }
        }
    }//GEN-LAST:event_tbl_LoaiHangMouseClicked

    private void btn_resert_LHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resert_LHActionPerformed
        txt_MaLoai.setText("");
        txt_TenLoai.setText("");
    }//GEN-LAST:event_btn_resert_LHActionPerformed
    // Xét điều kiện NULL
    public void ConditionNULL(JTextField x) {
        if (x.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            x.setBackground(Color.yellow);
            x.requestFocus();
            return;
        } else {
            x.setBackground(Color.white);
        }
    }
    private void btn_LuuHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuHHActionPerformed

        if (txtMaSP.getText().equals("") || txtTenHH.getText().equals("") || txt_GiaHH.getText().equals("") || txt_SLT.getText().equals("")) {
            ConditionNULL(txtMaSP);
            ConditionNULL(txtTenHH);
            ConditionNULL(txt_SLT);
            ConditionNULL(txt_GiaHH);
            return;
        } else {
            for (HangHoa h : list_HH) {
                if (h.getHH_MaHH().equals(txtMaSP.getText())) {
                    JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại\nVui lòng nhập mã mới", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    txtMaSP.setBackground(Color.yellow);
                    txtMaSP.requestFocus();
                    return;
                }
                txtMaSP.setBackground(Color.white);

            }
        }
        try {
            HangHoa hh = new HangHoa();
            hh.setHH_MaHH(txtMaSP.getText());
            hh.setHH_TenHH(txtTenHH.getText());
            hh.setHH_Slcon(Integer.valueOf(txt_SLT.getText()));
            hh.setHH_Gia(Long.valueOf(txt_GiaHH.getText()));
            for (LoaiHang lh : list_LH) {
                if (cbo_LoaiSP.getSelectedItem().toString().equals(lh.getLH_TenLH())) {
//                    hh.setHH_MaLoaiHH(cbo_LoaiSP.getSelectedItem().toString());
                    hh.setHH_MaLoaiHH(lh.getLH_MaLH());
                }
            }

            java.util.Date date_nonTransfer = txt_HH_NgayApDung.getDate();
            java.sql.Date date_Transfer = new java.sql.Date(date_nonTransfer.getTime());
            hh.setNgayApDung(date_Transfer);
            hh.setHH_imgHH(img_hh);
            HangHoa_Module hhm = new HangHoa_Module();
            hhm.themHH(hh);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            data_table_hh();
            evenOpenWindowForThongKe();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btn_LuuHHActionPerformed

    private void btn_anhSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anhSPActionPerformed
        JFileChooser choose = new JFileChooser("C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_HangHoa");
        choose.showOpenDialog(this);
        File f = choose.getSelectedFile();
        img_hh = f.getName();
//        JOptionPane.showMessageDialog(this, img_hh);
        String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_HangHoa\\";
        ImageIcon img = new ImageIcon(new ImageIcon(danC + img_hh).getImage().getScaledInstance(img_sp.getWidth(), img_sp.getHeight(), Image.SCALE_SMOOTH));
        img_sp.setIcon(img);
    }//GEN-LAST:event_btn_anhSPActionPerformed

    private void btn_SuaHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaHHActionPerformed
        if (txtMaSP.getText().equals("") || txtTenHH.getText().equals("") || txt_GiaHH.getText().equals("") || txt_SLT.getText().equals("")) {
            ConditionNULL(txtMaSP);
            ConditionNULL(txtTenHH);
            ConditionNULL(txt_SLT);
            ConditionNULL(txt_GiaHH);
            return;
        }
        try {
            HangHoa h = new HangHoa();
            h.setHH_MaHH(txtMaSP.getText());
            h.setHH_TenHH(txtTenHH.getText());
            h.setHH_Slcon(Integer.valueOf(txt_SLT.getText()));
            for (LoaiHang lh : list_LH) {
                if (cbo_LoaiSP.getSelectedItem().toString().equals(lh.getLH_TenLH())) {
//                    hh.setHH_MaLoaiHH(cbo_LoaiSP.getSelectedItem().toString());
                    h.setHH_MaLoaiHH(lh.getLH_MaLH());
                }
            }
            h.setHH_Gia(Long.valueOf(txt_GiaHH.getText()));
            java.util.Date nonTransfer = txt_HH_NgayApDung.getDate();
            java.sql.Date transfer = new java.sql.Date(nonTransfer.getTime());
            h.setNgayApDung(transfer);
            h.setHH_imgHH(img_hh);

            HangHoa_Module hhm = new HangHoa_Module();
            hhm.capnhatHH(h);
            JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
            data_table_hh();
            evenOpenWindowForThongKe();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_SuaHHActionPerformed

    private void btn_XoaHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaHHActionPerformed
        try {
            HangHoa_Module hhm = new HangHoa_Module();
            if (JOptionPane.showConfirmDialog(this, "Chắc chắn xóa " + txtMaSP.getText(), "Cảnh báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                hhm.xoaHH(txtMaSP.getText());
                JOptionPane.showMessageDialog(this, "Đã xóa thành công");
                data_table_hh();
            } else {
                JOptionPane.showMessageDialog(this, "Đã hủy xóa");
            }
            evenOpenWindowForThongKe();
            data_table_DH();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btn_XoaHHActionPerformed

    private void btn_ResertHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResertHHActionPerformed
        txtMaSP.setText("");
        txtTenHH.setText("");
        cbo_LoaiSP.setSelectedIndex(0);
        txt_SLT.setText("");
        txt_GiaHH.setText("");
        txt_HH_NgayApDung.cleanup();
        btn_LuuHH.setEnabled(false);
        btn_ThemHH.setEnabled(true);
        btn_SuaHH.setEnabled(true);
        btn_XoaHH.setEnabled(true);
        btn_InSP.setEnabled(true);
    }//GEN-LAST:event_btn_ResertHHActionPerformed

    private void tbl_HangHoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HangHoaMouseClicked
        int row = tbl_HangHoa.getSelectedRow();
        if (row >= 0) {
            String mahh = (String) tbl_HangHoa.getValueAt(row, 0);
            for (HangHoa h : list_HH) {
                if (h.getHH_MaHH().equals(mahh)) {
                    txtMaSP.setText(h.getHH_MaHH());
                    txtTenHH.setText(h.getHH_TenHH());
                    for (LoaiHang lh : list_LH) {
                        if (h.getHH_MaLoaiHH().equals(lh.getLH_MaLH())) {
                            cbo_LoaiSP.setSelectedItem(lh.getLH_TenLH());
                        }
                    }
                    txt_SLT.setText(String.valueOf(h.getHH_Slcon()));
                    txt_GiaHH.setText(String.valueOf(h.getHH_Gia()));
                    txt_HH_NgayApDung.setDate(h.getNgayApDung());
                    String danC = "C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\IMG_HangHoa\\";
                    ImageIcon img_hh2 = new ImageIcon(new ImageIcon(danC + h.getHH_imgHH()).getImage().getScaledInstance(img_sp.getWidth(), img_sp.getHeight(), Image.SCALE_SMOOTH));
                    img_sp.setIcon(img_hh2);
                    img_hh = h.getHH_imgHH();
                }
            }
        }
    }//GEN-LAST:event_tbl_HangHoaMouseClicked

    private void menu_ThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_ThongKeMousePressed
        jtp_SanPham.setVisible(false);
        pnl_QLNV.setVisible(false);
        pnl_BanHang.setVisible(false);
        jtp_thongke.setVisible(true);
        menu_sanpham.setBackground(new Color(0, 153, 153));
        menu_BanHang.setBackground(new Color(0, 153, 153));
        menu_Nhanvien.setBackground(new Color(0, 153, 153));
        menu_ThongKe.setBackground(new Color(0, 102, 102));

    }//GEN-LAST:event_menu_ThongKeMousePressed
// COde cho Đơn hàng
    private void btn_timSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timSPActionPerformed
        String ten = cboTimKiemSP.getSelectedItem().toString();
        String loaihang = "";
        for (LoaiHang lh : list_LH) {
            if (lh.getLH_TenLH().equals(ten)) {
                loaihang = lh.getLH_MaLH();
            }
        }
        tbl_timSp = new DefaultTableModel();
        tbl_timSp.setColumnIdentifiers(new String[]{"\tTên hàng hóa"});
        tbl_SpTimKiem.setModel(tbl_timSp);
        for (HangHoa h : list_HH) {
            if (h.getHH_MaLoaiHH().equals(loaihang)) {
                tbl_timSp.addRow(new String[]{h.getHH_TenHH()});
            }
        }
        tbl_timSp.fireTableDataChanged();
    }//GEN-LAST:event_btn_timSPActionPerformed

    private void tbl_SpTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SpTimKiemMouseClicked
        int row = tbl_SpTimKiem.getSelectedRow();
        if (row >= 0) {
            String tenSp = (String) tbl_SpTimKiem.getValueAt(row, 0);
            txt_TenSP.setText(tenSp);
            for (HangHoa h : list_HH) {
                if (h.getHH_TenHH().equals(tenSp)) {
                    txt_MaSp.setText(h.getHH_MaHH());
                }
            }
        }
    }//GEN-LAST:event_tbl_SpTimKiemMouseClicked

    private void btn_TangSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TangSLActionPerformed
        int max = 0;
        for (HangHoa h : list_HH) {
            if (h.getHH_TenHH().equals(txt_TenSP.getText())) {
                max = h.getHH_Slcon();
            }
        }
        int SoluongCurrent = Integer.parseInt(txt_SL.getText());
        SoluongCurrent++;
        if (SoluongCurrent > max) {
            JOptionPane.showMessageDialog(this, "Vượt quá số lượng còn", "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
            txt_SL.setText(String.valueOf(max));
            return;
        }
        txt_SL.setText(String.valueOf(SoluongCurrent));
    }//GEN-LAST:event_btn_TangSLActionPerformed

    private void btn_GiamSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GiamSLActionPerformed
        int SoluongCurrent = Integer.valueOf(txt_SL.getText());
        SoluongCurrent--;
        txt_SL.setText(String.valueOf(SoluongCurrent));
        if (SoluongCurrent <= 0) {
            JOptionPane.showMessageDialog(this, "Không được bé hơn 0", "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
            txt_SL.setText("1");
            return;
        }
    }//GEN-LAST:event_btn_GiamSLActionPerformed
// CODE khi ấn nút lưu đơn hàng

    public int layMaDHcuoi() throws Exception {
        DonHang_Module dhm = new DonHang_Module();
        List<DonHang> ldh = dhm.getDataDonHang();
        int max = 1;
        for (DonHang dh : ldh) {
            if (max < Integer.parseInt(dh.getDH_MaDH().substring((dh.getDH_MaDH().lastIndexOf("_") + 1)))) {
                max = Integer.parseInt(dh.getDH_MaDH().substring((dh.getDH_MaDH().lastIndexOf("_") + 1)));
            }
        }
        return max;
    }
    private void btn_LuuHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuHDActionPerformed
        ConditionNULL(txt_MaKH);
        if (txt_TenKH.getText().equals("") || txt_Dc_KH.getText().equals("")) {
            ConditionNULL(txt_TenKH);
            ConditionNULL(txt_Dc_KH);
            return;
        }
        if (list_SpDHtam.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có hàng sao mà lặp cái thằng này");
            return;
        }
        try {
            KhachHang_Module khm = new KhachHang_Module();
            KhachHang kh = new KhachHang();
            kh.setKH_MaKH(txt_MaKH.getText());
            kh.setKH_TenKH(txt_TenKH.getText());
            kh.setKH_DiaChi(txt_Dc_KH.getText());
            kh.setKH_SDT(txt_SĐT_KH.getText());
            DonHang dh = new DonHang();
            dh.setDH_MaDH(txt_MaDH.getText());
            java.util.Date non_tranfer = txtNgayLap.getDate();
            java.sql.Date transfer = new java.sql.Date(non_tranfer.getTime());
            dh.setDH_NgayLap(transfer);
            dh.setDH_TienGiam(Long.parseLong(txt_TongGiam.getText()));
            dh.setDH_TongTien(Long.parseLong(txt_ThanhTien.getText()));
            dh.setKH_MaKH(txt_MaKH.getText());
            dh.setNV_MaNV(txt_MaNV_BH.getText());

            DonHang_Module dhm = new DonHang_Module();
            khm.themKH(kh);
            dhm.themDH(dh);
            JOptionPane.showMessageDialog(this, "Thêm đơn hàng thành công");
            ChiTietDonHang_Module ctm = new ChiTietDonHang_Module();
            for (ChiTietDonHang c : list_SpDHtam) {
                ctm.themCTDH(c);
            }
            evenOpenWindowForDonHang(); // gọi lại hàm để khởi tạo lại đơn hàng mới
            data_table_DH();
            evenOpenWindowForThongKe();
//            btn_InHD.setEnabled(true);
        } catch (Exception e1) {
            int idex = e1.getMessage().lastIndexOf(" ") + 1;// bắt lỗi trùng mã khách hàng
            String er1 = "Violation of PRIMARY KEY constraint 'PK_KHACH_HANG'. Cannot insert duplicate key in object 'dbo.KHACH_HANG'. The duplicate key value is " + e1.getMessage().substring(idex);
            if (e1.getMessage().equals(er1)) {
                try {
                    DonHang dh = new DonHang();
                    dh.setDH_MaDH(txt_MaDH.getText());
                    java.util.Date non_tranfer = txtNgayLap.getDate();
                    java.sql.Date transfer = new java.sql.Date(non_tranfer.getTime());
                    dh.setDH_NgayLap(transfer);
                    dh.setDH_TienGiam(Long.parseLong(txt_TongGiam.getText()));
                    dh.setDH_TongTien(Long.parseLong(txt_ThanhTien.getText()));
                    dh.setKH_MaKH(txt_MaKH.getText());
                    dh.setNV_MaNV(txt_MaNV_BH.getText());
                    DonHang_Module dhm = new DonHang_Module();
                    dhm.themDH(dh);
                    ChiTietDonHang_Module ctm = new ChiTietDonHang_Module();
                    for (ChiTietDonHang c : list_SpDHtam) {
                        ctm.themCTDH(c);
                    }
                    JOptionPane.showMessageDialog(this, "Đã thêm đơn hàng thành công Ngoại lệ e1");
                    evenOpenWindowForDonHang(); // gọi lại hàm để khởi tạo lại đơn hàng mới 
                    data_table_DH();
                    evenOpenWindowForThongKe();
//                    btn_InHD.setEnabled(true);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(this, "Lỗi r kìa ở e2 2511");
                }
            }
        }

    }//GEN-LAST:event_btn_LuuHDActionPerformed
// Code khi ấn nút thêm để thêm sản phẩm vào 1 đơn hàng (đang dùng 1 list_SPTam để lưu tất cả hàng hóa đã thêm)

    public boolean kiemtratontai(String ma) {
        for (ChiTietDonHang c : list_SpDHtam) {
            if (c.getMa_HH().equals(ma)) {
                return true;
            }
        }
        return false;
    }
    private void btn_ThemSPvaoDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemSPvaoDHActionPerformed
        String masp = txt_MaSp.getText();
        int soluong = Integer.parseInt(txt_SL.getText());
        if (soluong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải khác 0", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txt_SL.setForeground(Color.red);
            txt_SL.setFont(new Font("Tahoma", Font.BOLD, 14));
            return;
        } else {
            txt_SL.setForeground(Color.black);
            txt_SL.setFont(new Font("Tahoma", Font.PLAIN, 14));
        }
        // kiểm tra xem hàng hóa được chọn đã có trong đơn hàng tạm thời chưa 
        if (kiemtratontai(masp)) { // nếu có thì chỉ cần tăng số Lượng
            for (ChiTietDonHang c : list_SpDHtam) {
                if (c.getMa_HH().equals(masp)) {
                    c.setSoluong(soluong + c.getSoluong());
                    break;
                }
            }
        } else { // Nếu chưa thì tiến hành thêm hàng hóa vào đơn hàng tạm
            ChiTietDonHang cthh = new ChiTietDonHang();
            cthh.setDH_MaDH(txt_MaDH.getText());
            cthh.setMa_HH(masp);
            cthh.setSoluong(soluong);
            list_SpDHtam.add(cthh);
        }
        long tong = 0;
        for (HangHoa h : list_HH) { // Dùng để cập nhật lại số lượng sản phẩm
            if (h.getHH_MaHH().equals(masp)) {
                h.setHH_Slcon(h.getHH_Slcon() - soluong);
                HangHoa_Module hhm = new HangHoa_Module();
                try {
                    hhm.capnhatHH(h);
                } catch (Exception ex) {
                    Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (ChiTietDonHang c : list_SpDHtam) {// dùng đẻ tính tổng tiền sản phẩm tạm được thêm vào chi tiết đơn hàng khi bấm thêm
            for (HangHoa h : list_HH) {
                if (c.getMa_HH().equals(h.getHH_MaHH())) {
                    tong += c.getSoluong() * h.getHH_Gia();
                }
            }
        }
        if (tong > 100000) {
            txt_TongGiam.setText(String.valueOf((int) (tong * 0.05)));
        } else {
            txt_TongGiam.setText("0");
        }
        txt_ThanhTien.setText(String.valueOf((long) tong));
        data_table_dhTam(); // Cập nhật hiển thị lại bảng sản phẩm được thêm vào đơn hàng
        evenOpenWindowForThongKe();
        try {
            data_table_hh(); // Cập nhật hiển thị lại bảng sản phẩm trong danh mục quản lý sản phẩm
        } catch (Exception ex) {
            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
        txt_SL.setText("0");
        data_table_TKSP_BS();// Cập nhật hiển thị lại bảng thống kê sản phẩm hết hàng
    }//GEN-LAST:event_btn_ThemSPvaoDHActionPerformed

// Code ấn nút làm mới
    private void btn_LamMoiDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiDHActionPerformed
//        txt_MaKH.setText("");
        txt_TenKH.setText("");
        txt_Dc_KH.setText("");
        txt_SĐT_KH.setText("");
        txt_TongGiam.setText("0");
        txt_ThanhTien.setText("0");
        list_SpDHtam.clear();
        txt_MaSp.setText("");
        txt_TenSP.setText("");
        cboTimKiemSP.setSelectedIndex(0);
//        btn_InHD.setEnabled(false);
        data_table_dhTam();
    }//GEN-LAST:event_btn_LamMoiDHActionPerformed
// Nhấn nút xóa sẽ tìm mã sp trong list_spTamj thời. Sau đó, xóa và cập nhật lại số lượng cho sản phẩm 
    private void btn_XoaSPTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaSPTamActionPerformed
        int i = 0; 
        for (ChiTietDonHang c : list_SpDHtam) {
            if (c.getMa_HH().equals(txt_MaSp.getText())) {
                for (HangHoa h : list_HH) { // tìm và cộng lại số lượng sản phẩm cho bảng hiển thị sản phẩm ở danh mục quản lý sản phẩm
                    if (h.getHH_MaHH().equals(c.getMa_HH())) {
                        h.setHH_Slcon(h.getHH_Slcon() + c.getSoluong());
                        HangHoa_Module hhm = new HangHoa_Module();
                        try {
                            hhm.capnhatHH(h); // Cập nhật vào trong CSDL của bảng HANG_HOA
                        } catch (Exception ex) {
                            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            }
            i++;
        }
        list_SpDHtam.remove(i);// số sản phẩm thứ i trong list tạm của đơn hàng
        int tong = 0;
        for (ChiTietDonHang c : list_SpDHtam) {// dùng đẻ tính tổng tiền sản phẩm tạm được thêm vào chi tiết đơn hàng khi bấm thêm
            for (HangHoa h : list_HH) {
                if (c.getMa_HH().equals(h.getHH_MaHH())) {
                    tong += c.getSoluong() * h.getHH_Gia();
                }
            }
        }
        if (tong > 100000) {
            txt_TongGiam.setText(String.valueOf((int) (tong * 0.05)));
        } else {
            txt_TongGiam.setText("0");
        }
        txt_ThanhTien.setText(String.valueOf(tong));
        data_table_dhTam(); // Cập nhật hiển thị lại bảng sản phẩm được thêm vào đơn hàng
        try {
            data_table_hh();// Cập nhật hiển thị lại bảng sản phẩm trong danh mục quản lý sản phẩm
        } catch (Exception ex) {
            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
        txt_SL.setText("1");
        evenOpenWindowForThongKe();
        data_table_TKSP_BS();// Cập nhật lại bảng thống kê sản phẩm hết hàng
    }//GEN-LAST:event_btn_XoaSPTamActionPerformed

    private void tbl_SPchoDHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SPchoDHMouseClicked
        int row = tbl_SPchoDH.getSelectedRow();
        if (row >= 0) {
            txt_MaSp.setText(tbl_SPchoDH.getValueAt(row, 0).toString());
            txt_TenSP.setText(tbl_SPchoDH.getValueAt(row, 1).toString());
            txt_SL.setText(tbl_SPchoDH.getValueAt(row, 3).toString());
        }
    }//GEN-LAST:event_tbl_SPchoDHMouseClicked
// Kiểm đến khi nhập 1 sdt hợp lệ có trong CSDL
    private void txt_SĐT_KHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_SĐT_KHCaretUpdate
        KhachHang k = new KhachHang();
        KhachHang_Module khm = new KhachHang_Module();
        try {
            k = khm.timSDT(txt_SĐT_KH.getText());
        } catch (Exception ex) {
            System.out.println("Chưa nhìn thấy");
        }
        if (k != null) {
            txt_MaKH.setText(k.getKH_MaKH());
            txt_TenKH.setText(k.getKH_TenKH());
            txt_Dc_KH.setText(k.getKH_DiaChi());
        } else {
            try {// Nếu số điện thoại chưa có khách hàng thì tạo dữ liệu để nhập khách hàng mới
                txt_MaKH.setText("KH_" + String.valueOf(khm.demKH() + 1));
                txt_TenKH.setText("");
                txt_Dc_KH.setText("");
            } catch (Exception ex) {
                Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_SĐT_KHCaretUpdate

    private void btn_ThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNVActionPerformed
        btn_ThemNV.setEnabled(false);
        btn_XoaNV.setEnabled(false);
        btn_CapNhatNV.setEnabled(false);
        btn_InNV.setEnabled(false);
        btn_LuuNV.setEnabled(true);
    }//GEN-LAST:event_btn_ThemNVActionPerformed

    private void btn_ThemHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemHHActionPerformed
        btn_LuuHH.setEnabled(true);
        btn_ThemHH.setEnabled(false);
        btn_SuaHH.setEnabled(false);
        btn_XoaHH.setEnabled(false);
        btn_InSP.setEnabled(false);
    }//GEN-LAST:event_btn_ThemHHActionPerformed

    private void tbl_DHTimkiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DHTimkiemMouseClicked
        while (tbl_CTDH_TimThay.getRowCount() > 0) {
            tbl_CTDH_TimThay.removeRow(0);
        }
        int row = tbl_DHTimkiem.getSelectedRow();
        if (row >= 0) {
            String madh = (String) tbl_DHTimkiem.getValueAt(row, 0);
            for (ChiTietDonHang l : list_TK_CTDH) {
                String tensp = "";
                for (HangHoa h : list_HH) {
                    if (h.getHH_MaHH().equals(l.getMa_HH())) {
                        tensp = h.getHH_TenHH();
                        break;
                    }
                }
                if (l.getDH_MaDH().equals(madh)) {
                    tbl_CTDH_TimThay.addRow(new String[]{l.getMa_HH(), tensp, String.valueOf(l.getSoluong())});
                }
            }
        }
        tbl_CTDH_TimThay.fireTableDataChanged();
    }//GEN-LAST:event_tbl_DHTimkiemMouseClicked

    private void btn_LammoiTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LammoiTKActionPerformed
        try {
            data_table_DH();// chứa dữ liệu của toàn bộ đơn hàng
            long tonggt = 0;
            for (DonHang d : list_DH) {
                tonggt += d.getDH_TongTien();
            }
            txt_TongGiaTriDHTK.setText(String.valueOf(tonggt));
            txt_TongDHTimThay.setText(String.valueOf(list_DH.size()));
            JOptionPane.showMessageDialog(this, "Đã làm mới");
            while (tbl_CTDH_TimThay.getRowCount() > 0) { // xóa bảng chi tiết đươn hàng đang load ra màn hình
                tbl_CTDH_TimThay.removeRow(0);
            }
            txt_NgayTKCanDuoi.setDate(null);
            txt_NgayTKCanTren.setDate(new Date(System.currentTimeMillis()));
            txt_TKmaDH.setText("");
        } catch (Exception ex) {
            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_LammoiTKActionPerformed

    private void btn_TimkiemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimkiemTKActionPerformed
        java.util.Date day1_nontransfer = txt_NgayTKCanDuoi.getDate();
        java.util.Date day2_nontransfer = txt_NgayTKCanTren.getDate();
        java.sql.Date day1_transfer = new java.sql.Date(day1_nontransfer.getTime());
        java.sql.Date day2_transfer = new java.sql.Date(day2_nontransfer.getTime());
        DonHang_Module dhm = new DonHang_Module();
        try {
            list_DH = dhm.timDH(day1_transfer, day2_transfer);
            data_table_DH2(); // chứa dữ liệu của các đơn hàng vừa tìm đc
            long tonggt = 0;
            for (DonHang d : list_DH) {
                tonggt += d.getDH_TongTien();
            }
            txt_TongGiaTriDHTK.setText(String.valueOf(tonggt));// cập nhật lại tooongrr giá trị đơn hàng
            txt_TongDHTimThay.setText(String.valueOf(list_DH.size()));// cập nhật lại số đơn hàng
            txt_TKmaDH.setText("");
            JOptionPane.showMessageDialog(this, "Đã tìm thành công");
            while (tbl_CTDH_TimThay.getRowCount() > 0) { // xóa bảng chi tiết đươn hàng đang load ra màn hình
                tbl_CTDH_TimThay.removeRow(0);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_btn_TimkiemTKActionPerformed
    // Xử lý ấn nút In ds nhân viên
    // thiết lập cho tiêu đề (Font chữ, căn giữa,...)
    public XSSFFont caitienFont_Tieude(XSSFWorkbook w ){
        XSSFFont font = w.createFont(); // chỉnh Font chữ
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("Arial");
        font.setFontHeight((short) (24*20)); 
        return font;
    }
    public CellStyle caitienStyle_Tieude(XSSFWorkbook w , XSSFFont font){
        CellStyle style = w.createCellStyle();
        style.setFont(font); //ấp dụng Font chữ vào style   
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor (IndexedColors.BLACK.getIndex ());
        return style;
    }
    // Thiết lập cho tiêu đề cột(Font chữ, căn giữa,...)
    public XSSFFont caitienFont_Cot(XSSFWorkbook w ){
        XSSFFont font = w.createFont(); // chỉnh Font chữ
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontName("Arial");
        font.setFontHeight((short) (14*20));
        return font;
    }
    public CellStyle caitienStyle_Cot(XSSFWorkbook w , XSSFFont font){
        CellStyle style = w.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.GREEN.getIndex()); // chỉnh màu nền
        style.setFillPattern(FillPatternType.SQUARES);
        style.setFont(font); //ấp dụng Font chữ vào style            
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    // thiết lập cho văn bản thường (Font chữ, tạo đường viền,..)
    public XSSFFont caitienFont_Vanban(XSSFWorkbook w){
        XSSFFont font = w.createFont(); // chỉnh Font chữ
        font.setColor(IndexedColors.BLACK1.getIndex());
        font.setFontName("Arial");
        font.setFontHeight((short) (12*20));
        return font;
    }
    public CellStyle caitienStyle_Vanban(XSSFWorkbook w , XSSFFont font){// Không có căn giữa
        CellStyle style = w.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor (IndexedColors.BLACK.getIndex ());
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor (IndexedColors.BLACK.getIndex ());
        return style;
    }
    public CellStyle caitienStyle_Vanban2(XSSFWorkbook w, XSSFFont font) { // Có căn giữa và chỉnh ngày
        CellStyle style = w.createCellStyle();
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        CreationHelper h = w.getCreationHelper();
        style.setDataFormat(h.createDataFormat().getFormat("dd/MM/yyyy"));
        return style;
    }
    

    private void btn_InNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InNVActionPerformed
// Cách nguyên thủy:
//        MessageFormat header = new MessageFormat("Danh sách nhân viên");
//        MessageFormat footer = new MessageFormat("Trang {0,number,integer}");  
//        try {
//            tbl_nhanvien.print(JTable.PrintMode.FIT_WIDTH, header, footer);
//        } catch (PrinterException ex) {
//            Logger.getLogger(Fm_Trangchu.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            XSSFWorkbook w = new XSSFWorkbook();
            XSSFSheet sheet = w.createSheet("Danh sách nhân viên");
            
            XSSFRow row = null;
            Cell cell =null;

            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH NHÂN VIÊN");
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(1, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(2, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(3, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(4, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(5, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(6, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            
            row = sheet.createRow(1);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã nhân viên");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tên nhân viên");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("SĐT");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Địa chỉ");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Chức vụ");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Username");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Password");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            for(int i=0 ; i<lnv.size(); i++){
                row = sheet.createRow(i+2);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_MaNV());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(0);
                
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_TenNV());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(1);
                
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(lnv.get(i).getNV_SDT());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(2);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_DiaChi());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(3);
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_Ad() > 0 ? "Quản lý":"Nhân Viên");
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(4);
                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_Username());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(5);
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(lnv.get(i).getNV_Password()); 
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(6);
            }
            try {
                FileOutputStream output = new FileOutputStream(new File("C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\File xuất\\Danh sách nhân viên.xlsx"));
                w.write(output);
                output.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Sai ở xuất file - In Nhân viên \n"+ e.getMessage());
            }
            JOptionPane.showMessageDialog(this, "In thành công\nKiểm tra tại: C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\File xuất\\Danh sách nhân viên.xlsx");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sai ở In Nhân viên \n"+ e.getMessage());
        }
    
    }//GEN-LAST:event_btn_InNVActionPerformed

    private void txt_SLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_SLCaretUpdate
        int max = 0;
        for (HangHoa h : list_HH) {
            if (h.getHH_MaHH().equals(txt_MaSp.getText())) {
                max = h.getHH_Slcon();
            }
        }
        try {
            if(txt_SL.getText().equals("")) return;
            if (Integer.parseInt(txt_SL.getText()) > max) {
                JOptionPane.showMessageDialog(this, "Vượt quá số lượng còn rồi!", "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception e) {
            System.out.println("Sản phẩm: "+txt_MaSp.getText() +" Số lượng: "+max);
        }
    }//GEN-LAST:event_txt_SLCaretUpdate

    private void btn_InSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InSPActionPerformed
        try {
            XSSFWorkbook w = new XSSFWorkbook();
            XSSFSheet sheet = w.createSheet("Danh sách sản phẩm");
            XSSFRow row = null;
            Cell cell = null;
            
            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH SẢN PHẨM");
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(1, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(2, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(3, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(4, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            cell = row.createCell(5, CellType.STRING);
            cell.setCellStyle(caitienStyle_Tieude(w, caitienFont_Tieude(w)));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            
            row = sheet.createRow(1);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã sản phẩm");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tên sản phẩm");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Số lượng");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Mã loại hàng");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Giá bán");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));
            
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Ngày áp dụng");
            cell.setCellStyle(caitienStyle_Cot(w, caitienFont_Cot(w)));

            for(int i=0; i<list_HH.size(); i++){
                row = sheet.createRow(i+2);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(list_HH.get(i).getHH_MaHH());
                cell.setCellStyle(caitienStyle_Vanban2(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(0);
                
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(list_HH.get(i).getHH_TenHH());
                cell.setCellStyle(caitienStyle_Vanban(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(1);
                
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(list_HH.get(i).getHH_Slcon());
                cell.setCellStyle(caitienStyle_Vanban2(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(2);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(list_HH.get(i).getHH_MaLoaiHH());
                cell.setCellStyle(caitienStyle_Vanban2(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(3);
                
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(list_HH.get(i).getHH_Gia());
                cell.setCellStyle(caitienStyle_Vanban2(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(4);
                
                cell = row.createCell(5);
                cell.setCellValue(list_HH.get(i).getNgayApDung());
                cell.setCellStyle(caitienStyle_Vanban2(w, caitienFont_Vanban(w)));
                sheet.autoSizeColumn(5);
            }
            File f = new File("C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\File xuất\\Danh sách sản phẩm.xlsx");
            FileOutputStream out = new FileOutputStream(f);
            w.write(out);
            out.close();
            JOptionPane.showMessageDialog(this, "Đã in thành công\nKiểm tra tại: C:\\Users\\huyb1\\OneDrive\\Máy tính\\Niên luận cơ sở\\File xuất\\Danh sách sản phẩm.xlsx");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_InSPActionPerformed

    private void btn_timMadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timMadonActionPerformed
        if(txt_TKmaDH.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Không được bỏ trống !");
            txt_TKmaDH.requestFocus();
            txt_TKmaDH.setBackground(Color.yellow);
            return;
        }else txt_TKmaDH.setBackground(Color.white);
        try {
            DonHang_Module dhm = new DonHang_Module();
            DonHang dh = dhm.timTheoMa(txt_TKmaDH.getText());
            KhachHang_Module khm = new KhachHang_Module();
            List<KhachHang> lkh = khm.loadKH();
            while (tbl_DHTimThay.getRowCount() > 0) {
                tbl_DHTimThay.removeRow(0);
            }
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
            String day = s.format(dh.getDH_NgayLap());
            String tenKH = new String();
            for(KhachHang k : lkh){
                if(k.getKH_MaKH().equals(dh.getKH_MaKH())){
                    tenKH = k.getKH_TenKH();
                    break;
                }
            }
            tbl_DHTimThay.addRow(new String[]{dh.getDH_MaDH(), day, dh.getNV_MaNV(), tenKH, String.valueOf(dh.getDH_TongTien())});
            tbl_DHTimThay.fireTableDataChanged();
            txt_TongDHTimThay.setText("1");
            txt_TongGiaTriDHTK.setText(String.valueOf(dh.getDH_TongTien()));
            while (tbl_CTDH_TimThay.getRowCount() > 0) { // xóa bảng chi tiết đươn hàng đang load ra màn hình
                tbl_CTDH_TimThay.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng có mã: "+ txt_TKmaDH.getText());
        }

    }//GEN-LAST:event_btn_timMadonActionPerformed

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        this.setVisible(false);
        Form_Dangnhap dn = new Form_Dangnhap();
        dn.setVisible(true);
    }//GEN-LAST:event_jLabel5MousePressed

    private void btn_InHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InHDActionPerformed
        try {
            Hashtable map = new Hashtable();
            JasperReport jr = JasperCompileManager.compileReport("src/project_View/xuatDonHang.jrxml");
            String madon = "DH_"+ layMaDHcuoi();
            map.put("MaDH", madon);
            Connection con = data_main.connect_main();
            JasperPrint jp = JasperFillManager.fillReport(jr, map, con);
            JasperViewer.viewReport(jp, false);
//            JOptionPane.showMessageDialog(this, "In thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_InHDActionPerformed

    private void menu_NhanvienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_NhanvienMousePressed
        jtp_SanPham.setVisible(false);
        pnl_QLNV.setVisible(true);
        pnl_BanHang.setVisible(false);
        jtp_thongke.setVisible(false);
        menu_sanpham.setBackground(new Color(0, 153, 153));
        menu_BanHang.setBackground(new Color(0, 153, 153));
        menu_Nhanvien.setBackground(new Color(0, 102, 102));
        menu_ThongKe.setBackground(new Color(0, 153, 153));
    }//GEN-LAST:event_menu_NhanvienMousePressed

    private void menu_BanHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_BanHangMousePressed
        jtp_SanPham.setVisible(false);
        pnl_QLNV.setVisible(false);
        pnl_BanHang.setVisible(true);
        jtp_thongke.setVisible(false);
        menu_sanpham.setBackground(new Color(0, 153, 153));
        menu_BanHang.setBackground(new Color(0, 102, 102));
        menu_Nhanvien.setBackground(new Color(0, 153, 153));
        menu_ThongKe.setBackground(new Color(0, 153, 153));
    }//GEN-LAST:event_menu_BanHangMousePressed

    private void menu_sanphamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_sanphamMousePressed
        jtp_SanPham.setVisible(true);
        pnl_QLNV.setVisible(false);
        pnl_BanHang.setVisible(false);
        jtp_thongke.setVisible(false);
        menu_sanpham.setBackground(new Color(0, 102, 102));
        menu_BanHang.setBackground(new Color(0, 153, 153));
        menu_Nhanvien.setBackground(new Color(0, 153, 153));
        menu_ThongKe.setBackground(new Color(0, 153, 153));
    }//GEN-LAST:event_menu_sanphamMousePressed

    public void evenOpenWindowForDonHang() throws Exception {
        for (NhanVien nv : lnv) {
            if (nv.getNV_MaNV().equals(user)) {
                txt_MaNV_BH.setText(nv.getNV_MaNV());
                txtTenNV_BH.setText(nv.getNV_TenNV());
            }
        }
        txtNgayLap.setDate(new Date(System.currentTimeMillis()));
        DonHang_Module dhm = new DonHang_Module();
        KhachHang_Module khm = new KhachHang_Module();
        txt_MaKH.setText("KH_" + String.valueOf(khm.demKH() + 1));
        txt_MaDH.setText("DH_" + (layMaDHcuoi() + 1));
    }

    public void evenOpenWindowForThongKe() {
        txt_SPTK_Con.setText(String.valueOf(list_HH.size()));
        int canmua = 0;
        for (HangHoa h : list_HH) {
            if (h.getHH_Slcon() < 1) {
                canmua++;
            }
        }
        if (canmua > 0) {
            txt_SPTK_BS.setForeground(Color.red);
        } else {
            txt_SPTK_BS.setForeground(Color.black);
        }
        txt_SPTK_BS.setText(String.valueOf(canmua));
        data_table_TKSP_BS();
        txt_NgayTKCanTren.setDate(new Date(System.currentTimeMillis()));
        txt_TongDHTimThay.setText(String.valueOf(list_DH.size()));
        long tonggt = 0;
        for (DonHang d : list_DH) {
            tonggt += d.getDH_TongTien();
        }
        txt_TongGiaTriDHTK.setText(String.valueOf(tonggt));
        txt_TongDHTimThay.setText(String.valueOf(list_DH.size()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fm_Trangchu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fm_Trangchu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fm_Trangchu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fm_Trangchu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fm_Trangchu(user, pass, ad).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JPanel TaiKhoan;
    private javax.swing.JPanel br_logo;
    private javax.swing.JButton btn_CapNhatNV;
    private javax.swing.JButton btn_GiamSL;
    private javax.swing.JButton btn_InHD;
    private javax.swing.JButton btn_InNV;
    private javax.swing.JButton btn_InSP;
    private javax.swing.JButton btn_LamMoiDH;
    private javax.swing.JButton btn_LammoiTK;
    private javax.swing.JButton btn_LuuHD;
    private javax.swing.JButton btn_LuuHH;
    private javax.swing.JButton btn_LuuNV;
    private javax.swing.JButton btn_ResertHH;
    private javax.swing.JButton btn_SuaHH;
    private javax.swing.JButton btn_SuaLoai;
    private javax.swing.JButton btn_TangSL;
    private javax.swing.JButton btn_ThemHH;
    private javax.swing.JButton btn_ThemLoai;
    private javax.swing.JButton btn_ThemNV;
    private javax.swing.JButton btn_ThemSPvaoDH;
    private javax.swing.JButton btn_TimkiemTK;
    private javax.swing.JButton btn_XoaHH;
    private javax.swing.JButton btn_XoaLoai;
    private javax.swing.JButton btn_XoaNV;
    private javax.swing.JButton btn_XoaSPTam;
    private javax.swing.JButton btn_anhSP;
    private javax.swing.JButton btn_capNhatTK;
    private javax.swing.JButton btn_lammoiNV;
    private javax.swing.JButton btn_lammoiTK;
    private javax.swing.JButton btn_resert_LH;
    private javax.swing.JButton btn_themTK;
    private javax.swing.JButton btn_themanh_NV;
    private javax.swing.JButton btn_timMadon;
    private javax.swing.JButton btn_timSP;
    private javax.swing.JButton btn_xoaTK;
    private javax.swing.JComboBox<String> cboTimKiemSP;
    private javax.swing.JComboBox<String> cbo_LoaiSP;
    private javax.swing.JCheckBox chk_ADTK;
    private javax.swing.JLabel img_NVDangNhap;
    private javax.swing.JLabel img_nhanvien;
    private javax.swing.JLabel img_sp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jtp_SanPham;
    private javax.swing.JTabbedPane jtp_thongke;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel menu_BanHang;
    private javax.swing.JLabel menu_Nhanvien;
    private javax.swing.JLabel menu_ThongKe;
    private javax.swing.JLabel menu_sanpham;
    private javax.swing.JPanel pnl_BanHang;
    private javax.swing.JPanel pnl_LoaiSP;
    private javax.swing.JPanel pnl_Menu;
    private javax.swing.JTabbedPane pnl_QLNV;
    private javax.swing.JPanel pnl_SanPham;
    private javax.swing.JPanel pnl_TKDH;
    private javax.swing.JPanel pnl_TKSP;
    private javax.swing.JPanel pnl_ThongTinSP;
    private javax.swing.JTextField ql_Username;
    private javax.swing.JTextField ql_password;
    private javax.swing.JTable tbl_ChiTietDonHang;
    private javax.swing.JTable tbl_DHTimkiem;
    private javax.swing.JTable tbl_HangHoa;
    private javax.swing.JTable tbl_LoaiHang;
    private javax.swing.JTable tbl_SPTK_BoSung;
    private javax.swing.JTable tbl_SPchoDH;
    private javax.swing.JTable tbl_SpTimKiem;
    private javax.swing.JTable tbl_TKSP;
    private javax.swing.JTable tbl_nhanvien;
    private javax.swing.JTable tbl_taikhoan;
    private javax.swing.JTextField txtMaSP;
    private com.toedter.calendar.JDateChooser txtNgayLap;
    private javax.swing.JTextField txtTenHH;
    private javax.swing.JTextField txtTenNV_BH;
    private javax.swing.JTextField txt_Dc_KH;
    private javax.swing.JTextField txt_Diachi;
    private javax.swing.JTextField txt_GiaHH;
    private com.toedter.calendar.JDateChooser txt_HH_NgayApDung;
    private javax.swing.JTextField txt_MaDH;
    private javax.swing.JTextField txt_MaKH;
    private javax.swing.JTextField txt_MaLoai;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_MaNV_BH;
    private javax.swing.JTextField txt_MaSp;
    private com.toedter.calendar.JDateChooser txt_NgayTKCanDuoi;
    private com.toedter.calendar.JDateChooser txt_NgayTKCanTren;
    private javax.swing.JTextField txt_Password;
    private javax.swing.JTextField txt_SDT;
    private javax.swing.JTextField txt_SL;
    private javax.swing.JTextField txt_SLT;
    private javax.swing.JLabel txt_SPTK_BS;
    private javax.swing.JLabel txt_SPTK_Con;
    private javax.swing.JTextField txt_SĐT_KH;
    private javax.swing.JTextField txt_TKmaDH;
    private javax.swing.JLabel txt_TenDangNhap;
    private javax.swing.JTextField txt_TenKH;
    private javax.swing.JTextField txt_TenLoai;
    private javax.swing.JTextField txt_TenNV;
    private javax.swing.JTextField txt_TenSP;
    private javax.swing.JTextField txt_ThanhTien;
    private javax.swing.JLabel txt_TongDHTimThay;
    private javax.swing.JLabel txt_TongGiaTriDHTK;
    private javax.swing.JTextField txt_TongGiam;
    private javax.swing.JTextField txt_Username;
    private javax.swing.JTextField txt_quyen;
    // End of variables declaration//GEN-END:variables
}
