package controller;

import dao.ParfumDAO;
import model.Parfum;
import util.DatabaseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ParfumServlet", 
           urlPatterns = {"/admin/parfum", "/admin/parfum/create", 
                         "/admin/parfum/edit", "/admin/parfum/update", 
                         "/admin/parfum/delete"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1MB
    maxFileSize = 1024 * 1024 * 5,      // 5MB
    maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class ParfumServlet extends HttpServlet {
    
    // Path untuk menyimpan gambar
    private static final String UPLOAD_DIR = "assets/image";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            ParfumDAO parfumDAO = new ParfumDAO(conn);
            
            if ("/admin/parfum".equals(action)) {
                // List all parfum
                List<Parfum> parfumList = parfumDAO.getAllParfum();
                request.setAttribute("parfumList", parfumList);
                request.setAttribute("pageTitle", "Daftar Parfum");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/parfum/list.jsp");
                dispatcher.forward(request, response);
                
            } else if ("/admin/parfum/create".equals(action)) {
                // Show create form
                request.setAttribute("parfum", null);
                request.setAttribute("pageTitle", "Tambah Parfum");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/parfum/form.jsp");
                dispatcher.forward(request, response);
                
            } else if ("/admin/parfum/edit".equals(action)) {
                // Show edit form
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    Parfum parfum = parfumDAO.getParfumById(id);
                    if (parfum != null) {
                        request.setAttribute("parfum", parfum);
                        request.setAttribute("pageTitle", "Edit Parfum");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/parfum/form.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        request.getSession().setAttribute("error", "Parfum tidak ditemukan!");
                        response.sendRedirect(request.getContextPath() + "/admin/parfum");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/parfum");
                }
                
            } else if ("/admin/parfum/delete".equals(action)) {
                // Delete parfum
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    Parfum parfum = parfumDAO.getParfumById(id);
                    
                    // Delete image file if exists
                    if (parfum != null && parfum.getGambar() != null && !parfum.getGambar().isEmpty()) {
                        deleteImage(parfum.getGambar(), request);
                    }
                    
                    boolean deleted = parfumDAO.deleteParfum(id);
                    if (deleted) {
                        request.getSession().setAttribute("success", "Parfum berhasil dihapus!");
                    } else {
                        request.getSession().setAttribute("error", "Gagal menghapus parfum!");
                    }
                }
                response.sendRedirect(request.getContextPath() + "/admin/parfum");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Terjadi kesalahan: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/parfum");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            ParfumDAO parfumDAO = new ParfumDAO(conn);
            
            System.out.println("ParfumServlet Action: " + action);
            
            if ("/admin/parfum".equals(action)) {
                // Create new parfum
                System.out.println("Creating new parfum...");
                createParfum(request, response, parfumDAO);
                
            } else if ("/admin/parfum/update".equals(action)) {
                // Update parfum
                System.out.println("Updating parfum ID: " + request.getParameter("id"));
                updateParfum(request, response, parfumDAO);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Terjadi kesalahan: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/parfum");
        }
    }
    
    private void createParfum(HttpServletRequest request, HttpServletResponse response, 
                              ParfumDAO parfumDAO) throws Exception {
        try {
            Parfum parfum = new Parfum();
            
            // Set basic information
            System.out.println("Params - nama: " + request.getParameter("nama") + 
                               ", merk: " + request.getParameter("merk") + 
                               ", harga: " + request.getParameter("harga"));
                               
            parfum.setNama(request.getParameter("nama"));
            parfum.setMerk(request.getParameter("merk"));
            parfum.setJenis(request.getParameter("jenis"));
            parfum.setKategori("Unisex"); // Legacy support
            parfum.setVolume(Integer.parseInt(request.getParameter("volume")));
            parfum.setHarga(new BigDecimal(request.getParameter("harga")));
            parfum.setStok(Integer.parseInt(request.getParameter("stok")));
            parfum.setDeskripsi(request.getParameter("deskripsi"));
            parfum.setRating(new BigDecimal("0.00"));
            
            // Handle file upload
            Part filePart = request.getPart("gambar");
            String fileName = null;
            
            if (filePart != null && filePart.getSize() > 0) {
                fileName = uploadImage(filePart, request);
                parfum.setGambar(fileName);
            } else {
                parfum.setGambar("default_parfum.jpg"); // default image
            }
            
            // Save to database
            boolean success = parfumDAO.addParfum(parfum);
            
            if (success) {
                request.getSession().setAttribute("success", "Parfum berhasil ditambahkan!");
                response.sendRedirect(request.getContextPath() + "/admin/parfum");
            } else {
                request.setAttribute("error", "Gagal menambahkan parfum: " + parfumDAO.getLastError());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/parfum/form.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (Exception e) {
            throw new Exception("Error creating parfum: " + e.getMessage(), e);
        }
    }
    
    private void updateParfum(HttpServletRequest request, HttpServletResponse response, 
                              ParfumDAO parfumDAO) throws Exception {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Parfum existingParfum = parfumDAO.getParfumById(id);
            
            if (existingParfum == null) {
                request.getSession().setAttribute("error", "Parfum tidak ditemukan!");
                response.sendRedirect(request.getContextPath() + "/admin/parfum");
                return;
            }
            
            // Update basic information
            existingParfum.setNama(request.getParameter("nama"));
            existingParfum.setMerk(request.getParameter("merk"));
            existingParfum.setJenis(request.getParameter("jenis"));
            existingParfum.setVolume(Integer.parseInt(request.getParameter("volume")));
            existingParfum.setHarga(new BigDecimal(request.getParameter("harga")));
            existingParfum.setStok(Integer.parseInt(request.getParameter("stok")));
            existingParfum.setDeskripsi(request.getParameter("deskripsi"));
            
            // Handle file upload
            Part filePart = request.getPart("gambar");
            String existingImage = request.getParameter("existingImage");
            
            if (filePart != null && filePart.getSize() > 0) {
                // Upload new image
                String fileName = uploadImage(filePart, request);
                existingParfum.setGambar(fileName);
                
                // Delete old image if not default
                if (existingImage != null && !existingImage.isEmpty() && 
                    !existingImage.equals("default_parfum.jpg")) {
                    deleteImage(existingImage, request);
                }
            } else if (existingImage != null && !existingImage.isEmpty()) {
                // Keep existing image
                existingParfum.setGambar(existingImage);
            }
            
            // Update in database
            boolean success = parfumDAO.updateParfum(existingParfum);
            
            if (success) {
                request.getSession().setAttribute("success", "Parfum berhasil diupdate!");
                response.sendRedirect(request.getContextPath() + "/admin/parfum");
            } else {
                request.setAttribute("error", "Gagal mengupdate parfum: " + parfumDAO.getLastError());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/parfum/form.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (Exception e) {
            throw new Exception("Error updating parfum: " + e.getMessage(), e);
        }
    }
    
    private String uploadImage(Part filePart, HttpServletRequest request) throws IOException {
        // Get application path
        String appPath = request.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + UPLOAD_DIR;
        
        // Create upload directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        System.out.println("Uploading to: " + uploadDir.getAbsolutePath());
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("Upload directory created: " + created);
        }
        
        // Generate unique filename
        String fileName = filePart.getSubmittedFileName();
        if (fileName == null || fileName.isEmpty()) return null;
        
        String fileExtension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = fileName.substring(i);
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Write file
        String filePath = uploadPath + File.separator + uniqueFileName;
        System.out.println("Writing file to: " + filePath);
        try (InputStream input = filePart.getInputStream();
             OutputStream output = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (Exception e) {
            System.err.println("Error writing file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        return uniqueFileName;
    }
    
    private void deleteImage(String fileName, HttpServletRequest request) {
        try {
            String appPath = request.getServletContext().getRealPath("");
            String filePath = appPath + File.separator + UPLOAD_DIR + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
