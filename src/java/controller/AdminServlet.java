/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.KaryawanDAO;
import dao.ParfumDAO;
import dao.UserDAO;
import dao.TransaksiDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import model.Karyawan;
import model.Parfum;
import model.User;

/**
 *
 * @author Devi Pratiwi
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/dashboard", "/admin/profile"})
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path.equals("/admin/profile")) {
            showProfile(request, response);
        } else {
            showDashboard(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ParfumDAO parfumDAO = new ParfumDAO();
        UserDAO userDAO = new UserDAO();
        KaryawanDAO karyawanDAO = new KaryawanDAO();
        TransaksiDAO transaksiDAO = new TransaksiDAO();
        
        try {
            List<Parfum> parfumList = parfumDAO.getAll();
            List<User> userList = userDAO.getAll();
            List<Karyawan> karyawanList = karyawanDAO.getAll();
            List<Parfum> lowStock = parfumDAO.getLowStock(10);
            
            LocalDate now = LocalDate.now();
            request.setAttribute("totalPenjualan", transaksiDAO.getTotalPenjualanBulanan(now.getYear(), now.getMonthValue()));
            request.setAttribute("transaksiHariIni", transaksiDAO.getTransaksiCountToday(null));
            
            request.setAttribute("totalParfum", parfumList.size());
            request.setAttribute("totalUsers", userList.size());
            request.setAttribute("totalKaryawan", karyawanList.size());
            request.setAttribute("lowStock", lowStock);
            request.setAttribute("recentActivities", transaksiDAO.getRecentTransactions(5));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("pageTitle", "Dashboard Admin");
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.setAttribute("pageTitle", "Profile Admin");
        request.getRequestDispatcher("/views/admin/profile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/admin/profile")) {
            updateProfile(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String email = request.getParameter("email");
        String namaLengkap = request.getParameter("nama_lengkap");
        String password = request.getParameter("password");

        user.setEmail(email);
        user.setNamaLengkap(namaLengkap);

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.update(user);

        if (success && password != null && !password.isEmpty()) {
            success = userDAO.updatePassword(user.getId(), password);
        }

        if (success) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userName", user.getNamaLengkap());
            request.getSession().setAttribute("nama", user.getNamaLengkap());
            request.getSession().setAttribute("success", "Profil berhasil diperbarui");
        } else {
            request.getSession().setAttribute("error", "Gagal memperbarui profil");
        }

        response.sendRedirect(request.getContextPath() + "/admin/profile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
