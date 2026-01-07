package controller;

import dao.KaryawanDAO;
import dao.ParfumDAO;
import dao.TransaksiDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Karyawan;
import model.Parfum;
import model.Transaksi;
import model.DetailTransaksi;
import model.User;

/**
 *
 * @author Devi Pratiwi
 */
@WebServlet(name = "KaryawanServlet", urlPatterns = {"/admin/karyawan", "/admin/karyawan/*", "/karyawan", "/karyawan/*"})
public class KaryawanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private KaryawanDAO karyawanDAO;
    private UserDAO userDAO;
    private TransaksiDAO transaksiDAO;

    private void initDAOs() {
        if (karyawanDAO == null) karyawanDAO = new KaryawanDAO();
        if (userDAO == null) userDAO = new UserDAO();
        if (transaksiDAO == null) transaksiDAO = new TransaksiDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initDAOs();
        String action = request.getPathInfo();
        if (action == null || action.equals("/") || action.equals("/list")) {
            listKaryawan(request, response);
        } else if (action.equals("/dashboard")) {
            showDashboard(request, response);
        } else if (action.equals("/parfum")) {
            showParfumList(request, response);
        } else if (action.equals("/transaksi") || action.equals("/transaksi/list")) {
            showTransaksiList(request, response);
        } else if (action.equals("/transaksi/create")) {
            showTransaksiForm(request, response);
        } else if (action.equals("/transaksi/detail")) {
            showTransaksiDetail(request, response);
        } else if (action.equals("/transaksi/print")) {
            printTransaksi(request, response);
        } else if (action.equals("/new")) {
            showNewForm(request, response);
        } else if (action.equals("/edit")) {
            showEditForm(request, response);
        } else if (action.equals("/delete")) {
            deleteKaryawan(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initDAOs();
        String action = request.getPathInfo();
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if (action.equals("/create")) {
            createKaryawan(request, response);
        } else if (action.equals("/update")) {
            updateKaryawan(request, response);
        } else if (action.equals("/transaksi/save")) {
            saveTransaksi(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listKaryawan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Karyawan> karyawanList = karyawanDAO.getAll();
        request.setAttribute("karyawanList", karyawanList);
        request.setAttribute("pageTitle", "Daftar Karyawan");
        request.getRequestDispatcher("/views/admin/karyawan/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> userList = userDAO.getAll();
        request.setAttribute("userList", userList);
        request.setAttribute("pageTitle", "Tambah Karyawan");
        request.getRequestDispatcher("/views/admin/karyawan/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Karyawan karyawan = karyawanDAO.getById(id);
        
        if (karyawan != null) {
            List<User> userList = userDAO.getAll();
            request.setAttribute("karyawan", karyawan);
            request.setAttribute("userList", userList);
            request.setAttribute("pageTitle", "Edit Karyawan");
            request.getRequestDispatcher("/views/admin/karyawan/form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/karyawan/list");
        }
    }

    private void deleteKaryawan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        if (karyawanDAO.delete(id)) {
            request.getSession().setAttribute("success", "Karyawan berhasil dihapus");
        } else {
            request.getSession().setAttribute("error", "Gagal menghapus karyawan");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/karyawan/list");
    }

    private void createKaryawan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Karyawan karyawan = new Karyawan();
            karyawan.setUserId(Integer.parseInt(request.getParameter("user_id")));
            karyawan.setNik(request.getParameter("nik"));
            karyawan.setJabatan(request.getParameter("jabatan"));
            karyawan.setGajiPokok(new BigDecimal(request.getParameter("gaji_pokok")));
            
            String tunjangan = request.getParameter("tunjangan");
            if (tunjangan != null && !tunjangan.isEmpty()) {
                karyawan.setTunjangan(new BigDecimal(tunjangan));
            }
            
            karyawan.setBank(request.getParameter("bank"));
            karyawan.setNomorRekening(request.getParameter("nomor_rekening"));
            
            if (karyawanDAO.create(karyawan)) {
                request.getSession().setAttribute("success", "Karyawan berhasil ditambahkan");
            } else {
                request.getSession().setAttribute("error", "Gagal menambahkan karyawan");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/karyawan/list");
    }

    private void updateKaryawan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Karyawan karyawan = karyawanDAO.getById(id);
            
            if (karyawan != null) {
                karyawan.setNik(request.getParameter("nik"));
                karyawan.setJabatan(request.getParameter("jabatan"));
                karyawan.setGajiPokok(new BigDecimal(request.getParameter("gaji_pokok")));
                
                String tunjangan = request.getParameter("tunjangan");
                if (tunjangan != null && !tunjangan.isEmpty()) {
                    karyawan.setTunjangan(new BigDecimal(tunjangan));
                }
                
                karyawan.setBank(request.getParameter("bank"));
                karyawan.setNomorRekening(request.getParameter("nomor_rekening"));
                
                if (karyawanDAO.update(karyawan)) {
                    request.getSession().setAttribute("success", "Karyawan berhasil diupdate");
                } else {
                    request.getSession().setAttribute("error", "Gagal mengupdate karyawan");
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/karyawan/list");
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initDAOs();
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");
        
        // Filter by user if not admin
        Integer filterId = "KARYAWAN".equalsIgnoreCase(role) ? userId : null;
        
        int transaksiHariIni = transaksiDAO.getTransaksiCountToday(filterId);
        BigDecimal totalPenjualanHariIni = transaksiDAO.getTotalPenjualanToday(filterId);
        String parfumTerlaris = transaksiDAO.getTopSellingParfum(filterId);
        
        ParfumDAO parfumDAO = new ParfumDAO();
        int stokKritis = parfumDAO.getLowStock(10).size();
        
        request.setAttribute("transaksiHariIni", transaksiHariIni);
        request.setAttribute("totalPenjualanHariIni", totalPenjualanHariIni);
        request.setAttribute("parfumTerlaris", parfumTerlaris);
        request.setAttribute("stokKritis", stokKritis);
        
        request.setAttribute("pageTitle", "Dashboard Karyawan");
        request.getRequestDispatcher("/views/karyawan/dashboard.jsp").forward(request, response);
    }

    private void showParfumList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ParfumDAO parfumDAO = new ParfumDAO();
        List<Parfum> parfumList = parfumDAO.getAll();
        request.setAttribute("parfumList", parfumList);
        request.setAttribute("pageTitle", "Daftar Parfum");
        request.getRequestDispatcher("/views/parfum/list.jsp").forward(request, response);
    }

    private void showTransaksiList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");
        Integer userId = null;
        
        // If employee, only show their own transactions
        if ("KARYAWAN".equalsIgnoreCase(role)) {
            userId = (Integer) session.getAttribute("userId");
        }
        
        List<Transaksi> transaksiList = transaksiDAO.getAll(userId);
        request.setAttribute("transaksiList", transaksiList);
        request.setAttribute("pageTitle", "Daftar Transaksi");
        request.getRequestDispatcher("/views/karyawan/transaksi/list.jsp").forward(request, response);
    }

    private void showTransaksiForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ParfumDAO parfumDAO = new ParfumDAO();
        List<Parfum> parfumList = parfumDAO.getAll();
        request.setAttribute("parfumList", parfumList);
        request.setAttribute("pageTitle", "Transaksi Baru");
        request.getRequestDispatcher("/views/karyawan/transaksi/form.jsp").forward(request, response);
    }

    private void showTransaksiDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            TransaksiDAO transaksiDAO = new TransaksiDAO();
            Transaksi t = transaksiDAO.getById(id);
            
            if (t != null) {
                request.setAttribute("transaksi", t);
                request.setAttribute("pageTitle", "Detail Transaksi #" + t.getKodeTransaksi());
                request.getRequestDispatcher("/views/karyawan/transaksi/detail.jsp").forward(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void printTransaksi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            TransaksiDAO transaksiDAO = new TransaksiDAO();
            Transaksi t = transaksiDAO.getById(id);
            
            if (t != null) {
                request.setAttribute("transaksi", t);
                request.setAttribute("pageTitle", "Cetak Struk #" + t.getKodeTransaksi());
                request.getRequestDispatcher("/views/karyawan/transaksi/print.jsp").forward(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void saveTransaksi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }

            Transaksi t = new Transaksi();
            String kode = "TRX-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            t.setKodeTransaksi(kode);
            t.setUserId(userId);
            
            String metode = request.getParameter("metode_pembayaran");
            t.setMetodePembayaran(metode != null ? metode : "tunai");
            
            t.setPembayaran(new BigDecimal(request.getParameter("pembayaran")));
            
            List<DetailTransaksi> details = new ArrayList<>();
            BigDecimal totalHarga = BigDecimal.ZERO;
            ParfumDAO parfumDAO = new ParfumDAO();
            
            // Extract items from request
            int i = 0;
            while (true) {
                String parfumIdStr = request.getParameter("items[" + i + "].parfumId");
                if (parfumIdStr == null) break;
                
                int parfumId = Integer.parseInt(parfumIdStr);
                int qty = Integer.parseInt(request.getParameter("items[" + i + "].quantity"));
                
                Parfum p = parfumDAO.getById(parfumId);
                if (p != null) {
                    DetailTransaksi dt = new DetailTransaksi();
                    dt.setParfumId(parfumId);
                    dt.setJumlah(qty);
                    dt.setHargaSatuan(p.getHarga());
                    dt.setSubtotal(p.getHarga().multiply(new BigDecimal(qty)));
                    details.add(dt);
                    totalHarga = totalHarga.add(dt.getSubtotal());
                }
                i++;
            }
            
            t.setDetails(details);
            t.setTotalHarga(totalHarga);
            t.setKembalian(t.getPembayaran().subtract(totalHarga));
            
            if (transaksiDAO.save(t)) {
                session.setAttribute("success", "Transaksi berhasil disimpan! Kode: " + kode);
            } else {
                session.setAttribute("error", "Gagal menyimpan transaksi (DB Error): " + transaksiDAO.getLastError());
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Format angka pembayaran tidak valid!");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Sistem Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/karyawan/transaksi");
    }
}
