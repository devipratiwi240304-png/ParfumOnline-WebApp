/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;
import util.SessionUtil;

/**
 *
 * @author Devi Pratiwi
 */
@WebServlet(name = "AuthServlet", urlPatterns = {"/auth/*", "/login", "/logout", "/register"})
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    private void initDAO() {
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
        initDAO();
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        // Handle direct URL mappings (/login, /logout, /register)
        if (action == null || action.equals("/")) {
            if (servletPath.equals("/login") || servletPath.equals("/auth")) {
                showLoginPage(request, response);
            } else if (servletPath.equals("/logout")) {
                processLogout(request, response);
            } else if (servletPath.equals("/register")) {
                showRegisterPage(request, response);
            } else {
                showLoginPage(request, response);
            }
        } else if (action.equals("/login")) {
            showLoginPage(request, response);
        } else if (action.equals("/logout")) {
            processLogout(request, response);
        } else if (action.equals("/register")) {
            showRegisterPage(request, response);
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
        initDAO();
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        // Handle direct URL mappings (/login, /logout, /register)
        if (action == null || action.equals("/")) {
            if (servletPath.equals("/login")) {
                processLogin(request, response);
            } else if (servletPath.equals("/logout")) {
                processLogout(request, response);
            } else if (servletPath.equals("/register")) {
                processRegister(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            return;
        }
        
        switch (action) {
            case "/login":
                processLogin(request, response);
                break;
            case "/logout":
                processLogout(request, response);
                break;
            case "/register":
                processRegister(request, response);
                break;
            default:
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

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = userDAO.authenticate(username, password);
        
        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userName", user.getNamaLengkap());
            session.setAttribute("userRole", user.getRole().toUpperCase());
            session.setAttribute("nama", user.getNamaLengkap());
            session.setAttribute("role", user.getRole().toLowerCase()); // keep for legacy if any
            session.setMaxInactiveInterval(30 * 60);
            
            // Redirect berdasarkan role
            String redirectPath = "/admin/dashboard";
            if ("karyawan".equals(user.getRole())) {
                redirectPath = "/karyawan/dashboard";
            }
            
            response.sendRedirect(request.getContextPath() + redirectPath);
        } else {
            request.setAttribute("error", "Username atau password salah");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        }
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String email = request.getParameter("email");
        String namaLengkap = request.getParameter("nama_lengkap");
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password dan konfirmasi password tidak sama");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Cek username sudah ada atau belum
        User existingUser = userDAO.getByUsername(username);
        if (existingUser != null) {
            request.setAttribute("error", "Username sudah digunakan");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        User newUser = new User(username, password, email, "karyawan", namaLengkap);
        
        if (userDAO.create(newUser)) {
            request.setAttribute("success", "Registrasi berhasil! Silakan login.");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registrasi gagal. Silakan coba lagi.");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
        }
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cek jika sudah login, redirect ke dashboard
        if (SessionUtil.isLoggedIn(request)) {
            String role = (String) SessionUtil.getAttribute(request, "role");
            if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/karyawan/dashboard");
            }
            return;
        }
        
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

}
