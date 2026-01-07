/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import model.Parfum;
/**
 *
 * @author Devi Pratiwi
 */
public class ParfumDAO extends BaseDAO {
    private String lastError;

    public String getLastError() {
        return lastError;
    }
    
    public ParfumDAO(Connection connection) {
        super(connection);
        ensureJenisColumnExists();
    }
    
    public ParfumDAO() {
        super();
        ensureJenisColumnExists();
    }
    
    private void ensureJenisColumnExists() {
        if (connection == null) return;
        try (Statement stmt = connection.createStatement()) {
            try {
                stmt.executeQuery("SELECT jenis FROM parfum LIMIT 0");
            } catch (SQLException e) {
                // Column missing
                System.out.println("Column 'jenis' missing in parfum table. Adding it...");
                stmt.executeUpdate("ALTER TABLE parfum ADD COLUMN jenis VARCHAR(50) AFTER merk");
            }
        } catch (SQLException e) {
            System.err.println("Error ensuring 'jenis' column: " + e.getMessage());
        }
    }

     public boolean create(Parfum parfum) {
        String sql = "INSERT INTO parfum (nama, merk, jenis, kategori, harga, stok, deskripsi, volume_ml, rating, gambar) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, parfum.getNama());
            ps.setString(2, parfum.getMerk());
            ps.setString(3, parfum.getJenis());
            ps.setString(4, parfum.getKategori());
            ps.setBigDecimal(5, parfum.getHarga());
            ps.setInt(6, parfum.getStok());
            ps.setString(7, parfum.getDeskripsi());
            ps.setInt(8, parfum.getVolumeMl());
            ps.setBigDecimal(9, parfum.getRating());
            ps.setString(10, parfum.getGambar());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            this.lastError = e.getMessage();
            System.err.println("SQL Error creating parfum: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    public boolean addParfum(Parfum parfum) {
        return create(parfum);
    }
    
    // Get parfum by ID
    public Parfum getById(int id) {
        String sql = "SELECT * FROM parfum WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToParfum(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting parfum by ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    public Parfum getParfumById(int id) {
        return getById(id);
    }
    
    // Get all parfum
    public List<Parfum> getAll() {
        List<Parfum> parfums = new ArrayList<>();
        String sql = "SELECT * FROM parfum ORDER BY created_at DESC";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                parfums.add(mapResultSetToParfum(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all parfum: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return parfums;
    }
    
    public List<Parfum> getAllParfum() {
        return getAll();
    }
    
    // Update parfum
    public boolean update(Parfum parfum) {
        String sql = "UPDATE parfum SET nama = ?, merk = ?, jenis = ?, kategori = ?, harga = ?, stok = ?, " +
                    "deskripsi = ?, volume_ml = ?, rating = ?, gambar = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, parfum.getNama());
            ps.setString(2, parfum.getMerk());
            ps.setString(3, parfum.getJenis());
            ps.setString(4, parfum.getKategori());
            ps.setBigDecimal(5, parfum.getHarga());
            ps.setInt(6, parfum.getStok());
            ps.setString(7, parfum.getDeskripsi());
            ps.setInt(8, parfum.getVolumeMl());
            ps.setBigDecimal(9, parfum.getRating());
            ps.setString(10, parfum.getGambar());
            ps.setInt(11, parfum.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            this.lastError = e.getMessage();
            System.err.println("SQL Error updating parfum: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    public boolean updateParfum(Parfum parfum) {
        return update(parfum);
    }
    
    // Delete parfum
    public boolean delete(int id) {
        String sql = "DELETE FROM parfum WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting parfum: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    public boolean deleteParfum(int id) {
        return delete(id);
    }
    
    // Search parfum by name or brand
    public List<Parfum> search(String keyword) {
        List<Parfum> parfums = new ArrayList<>();
        String sql = "SELECT * FROM parfum WHERE nama LIKE ? OR merk LIKE ? ORDER BY nama";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                parfums.add(mapResultSetToParfum(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching parfum: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return parfums;
    }
    
    // Update stock
    public boolean updateStock(int id, int quantity) {
        String sql = "UPDATE parfum SET stok = stok - ? WHERE id = ? AND stok >= ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.setInt(3, quantity);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get low stock items
    public List<Parfum> getLowStock(int threshold) {
        List<Parfum> parfums = new ArrayList<>();
        String sql = "SELECT * FROM parfum WHERE stok <= ? ORDER BY stok ASC";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, threshold);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                parfums.add(mapResultSetToParfum(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting low stock: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return parfums;
    }
    
    // Helper method to map ResultSet to Parfum object
    private Parfum mapResultSetToParfum(ResultSet rs) throws SQLException {
        Parfum parfum = new Parfum();
        parfum.setId(rs.getInt("id"));
        parfum.setNama(rs.getString("nama"));
        parfum.setMerk(rs.getString("merk"));
        parfum.setKategori(rs.getString("kategori"));
        parfum.setHarga(rs.getBigDecimal("harga"));
        parfum.setStok(rs.getInt("stok"));
        parfum.setDeskripsi(rs.getString("deskripsi"));
        parfum.setVolumeMl(rs.getInt("volume_ml"));
        parfum.setRating(rs.getBigDecimal("rating"));
        parfum.setGambar(rs.getString("gambar"));
        parfum.setCreatedAt(rs.getTimestamp("created_at"));
        parfum.setUpdatedAt(rs.getTimestamp("updated_at"));
        // Set jenis - try to read from database, fallback to kategori if empty
        try {
            String jenis = rs.getString("jenis");
            if (jenis == null || jenis.trim().isEmpty()) {
                jenis = parfum.getKategori();
            }
            parfum.setJenis(jenis);
        } catch (SQLException e) {
            parfum.setJenis(parfum.getKategori()); // fallback to kategori
        }
        return parfum;
    }
}
