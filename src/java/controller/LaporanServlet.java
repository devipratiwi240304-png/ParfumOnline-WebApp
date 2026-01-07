/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ParfumDAO;
import dao.TransaksiDAO;
import dao.PenggajianDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Devi Pratiwi
 */
@WebServlet(name = "LaporanServlet", urlPatterns = {
    "/admin/laporan", 
    "/admin/laporan/penjualan", 
    "/admin/laporan/stok", 
    "/admin/laporan/generate-penjualan", 
    "/admin/laporan/generate-stok", 
    "/admin/laporan/slip-gaji"
})
public class LaporanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ParfumDAO parfumDAO;
    private TransaksiDAO transaksiDAO;
    private PenggajianDAO penggajianDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        parfumDAO = new ParfumDAO();
        transaksiDAO = new TransaksiDAO();
        penggajianDAO = new PenggajianDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        switch (action) {
            case "/admin/laporan":
            case "/admin/laporan/penjualan":
                showLaporanPenjualanPage(request, response);
                break;
            case "/admin/laporan/stok":
                showLaporanStokPage(request, response);
                break;
            case "/admin/laporan/generate-penjualan":
                generateLaporanPenjualan(request, response);
                break;
            case "/admin/laporan/generate-stok":
                generateLaporanStok(request, response);
                break;
            case "/admin/laporan/slip-gaji":
                generateSlipGaji(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showLaporanPenjualanPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listTransaksi", transaksiDAO.getAll(null));
        request.setAttribute("pageTitle", "Laporan Penjualan");
        request.getRequestDispatcher("/views/admin/laporan/penjualan.jsp").forward(request, response);
    }

    private void showLaporanStokPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Laporan Stok");
        request.getRequestDispatcher("/views/admin/laporan/stok.jsp").forward(request, response);
    }

    private void generateLaporanPenjualan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listTransaksi", transaksiDAO.getAll(null));
        request.setAttribute("pageTitle", "Cetak Laporan Penjualan");
        request.getRequestDispatcher("/views/admin/laporan/print_penjualan.jsp").forward(request, response);
    }

    private void generateLaporanStok(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/laporan/stok.jsp").forward(request, response);
    }

    private void generateSlipGaji(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                model.Penggajian slip = penggajianDAO.getById(id);
                if (slip != null) {
                    request.setAttribute("slip", slip);
                    request.getRequestDispatcher("/views/admin/laporan/print_slip_gaji.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Return to previous page or show error
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/penggajian?error=Slip tidak ditemukan");
    }

}
