/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.KaryawanDAO;
import dao.PenggajianDAO;
import dao.UserDAO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Karyawan;
import model.Penggajian;
import model.User;

/**
 *
 * @author Devi Pratiwi
 */
@WebServlet(name = "PenggajianServlet", urlPatterns = {"/admin/penggajian", "/admin/penggajian/*", "/penggajian", "/penggajian/*"})
public class PenggajianServlet extends HttpServlet {
    private PenggajianDAO penggajianDAO;
    private KaryawanDAO karyawanDAO;
    private UserDAO userDAO;

    private void initDAOs() {
        if (penggajianDAO == null) penggajianDAO = new PenggajianDAO();
        if (karyawanDAO == null) karyawanDAO = new KaryawanDAO();
        if (userDAO == null) userDAO = new UserDAO();
    }

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
        initDAOs();
        String action = request.getPathInfo();
        
        if (action == null || action.equals("/") || action.equals("/list")) {
            List<Penggajian> list = penggajianDAO.getAll(null, null);
            request.setAttribute("penggajianList", list);
            request.setAttribute("pageTitle", "Daftar Penggajian");
            request.getRequestDispatcher("/views/penggajian/list.jsp").forward(request, response);
            
        } else if (action.equals("/create")) {
            List<Karyawan> karyawanList = karyawanDAO.getAll();
            request.setAttribute("karyawanList", karyawanList);
            request.setAttribute("pageTitle", "Tambah Penggajian");
            request.getRequestDispatcher("/views/penggajian/form.jsp").forward(request, response);
            
        } else if (action.equals("/edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Penggajian penggajian = penggajianDAO.getById(id);
            List<Karyawan> karyawanList = karyawanDAO.getAll();
            
            request.setAttribute("penggajian", penggajian);
            request.setAttribute("karyawanList", karyawanList);
            request.setAttribute("pageTitle", "Edit Penggajian");
            request.getRequestDispatcher("/views/penggajian/form.jsp").forward(request, response);
            
        } else if (action.equals("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            penggajianDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/penggajian");
            
        } else if (action.equals("/print")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Penggajian p = penggajianDAO.getById(id);
            request.setAttribute("penggajian", p);
            request.getRequestDispatcher("/views/penggajian/print_slip.jsp").forward(request, response);
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
        initDAOs();
        String action = request.getPathInfo();
        
        if (action != null && action.equals("/save")) {
            try {
                Penggajian p = new Penggajian();
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    p.setId(Integer.parseInt(idStr));
                }
                
                p.setKaryawanId(Integer.parseInt(request.getParameter("karyawan_id")));
                
                // Construct date from month and year
                String bulan = request.getParameter("bulan");
                String tahun = request.getParameter("tahun");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date periode = sdf.parse(tahun + "-" + bulan + "-01");
                p.setPeriode(periode);
                
                p.setGajiPokok(new BigDecimal(request.getParameter("gaji_pokok")));
                p.setTunjangan(new BigDecimal(request.getParameter("tunjangan")));
                p.setPotongan(new BigDecimal(request.getParameter("potongan")));
                p.setBonus(BigDecimal.ZERO); // default
                
                BigDecimal total = p.getGajiPokok().add(p.getTunjangan()).subtract(p.getPotongan());
                p.setTotalGaji(total);
                p.setStatus(request.getParameter("status"));
                p.setMetodePembayaran("transfer"); // lowercase to match DB enum
                
                boolean success;
                if (idStr != null && !idStr.isEmpty()) {
                    success = penggajianDAO.update(p); 
                } else {
                    success = penggajianDAO.create(p);
                }
                
                if (success) {
                    request.getSession().setAttribute("success", "Data penggajian berhasil disimpan");
                } else {
                    request.getSession().setAttribute("error", "Gagal menyimpan data penggajian");
                }
                
                response.sendRedirect(request.getContextPath() + "/admin/penggajian");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Terjadi kesalahan: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/admin/penggajian");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
