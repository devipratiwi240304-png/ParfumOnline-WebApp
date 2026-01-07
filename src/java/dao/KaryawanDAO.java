/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import model.Karyawan;
/**
 *
 * @author Devi Pratiwi
 */
public class KaryawanDAO extends BaseDAO  {
    public boolean create(Karyawan karyawan) {
        String sql = "INSERT INTO karyawan (user_id, nik, jabatan, gaji_pokok, tunjangan, bank, nomor_rekening) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, karyawan.getUserId());
            ps.setString(2, karyawan.getNik());
            ps.setString(3, karyawan.getJabatan());
            ps.setBigDecimal(4, karyawan.getGajiPokok());
            ps.setBigDecimal(5, karyawan.getTunjangan());
            ps.setString(6, karyawan.getBank());
            ps.setString(7, karyawan.getNomorRekening());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error creating karyawan: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get karyawan by ID
    public Karyawan getById(int id) {
        String sql = "SELECT k.*, u.username, u.email, u.nama_lengkap FROM karyawan k " +
                    "JOIN users u ON k.user_id = u.id WHERE k.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToKaryawan(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting karyawan by ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    // Get all karyawan
    public List<Karyawan> getAll() {
        List<Karyawan> list = new ArrayList<>();
        String sql = "SELECT k.*, u.username, u.email, u.nama_lengkap FROM karyawan k " +
                    "JOIN users u ON k.user_id = u.id ORDER BY u.nama_lengkap";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(mapResultSetToKaryawan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all karyawan: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return list;
    }
    
    // Update karyawan
    public boolean update(Karyawan karyawan) {
        String sql = "UPDATE karyawan SET nik = ?, jabatan = ?, gaji_pokok = ?, tunjangan = ?, " +
                    "bank = ?, nomor_rekening = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, karyawan.getNik());
            ps.setString(2, karyawan.getJabatan());
            ps.setBigDecimal(3, karyawan.getGajiPokok());
            ps.setBigDecimal(4, karyawan.getTunjangan());
            ps.setString(5, karyawan.getBank());
            ps.setString(6, karyawan.getNomorRekening());
            ps.setInt(7, karyawan.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating karyawan: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Delete karyawan
    public boolean delete(int id) {
        String sql = "DELETE FROM karyawan WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting karyawan: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get karyawan by user ID
    public Karyawan getByUserId(int userId) {
        String sql = "SELECT k.*, u.username, u.email, u.nama_lengkap FROM karyawan k " +
                    "JOIN users u ON k.user_id = u.id WHERE k.user_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToKaryawan(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting karyawan by user ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    // Helper method to map ResultSet to Karyawan object
    private Karyawan mapResultSetToKaryawan(ResultSet rs) throws SQLException {
        Karyawan karyawan = new Karyawan();
        karyawan.setId(rs.getInt("id"));
        karyawan.setUserId(rs.getInt("user_id"));
        karyawan.setNik(rs.getString("nik"));
        karyawan.setJabatan(rs.getString("jabatan"));
        karyawan.setGajiPokok(rs.getBigDecimal("gaji_pokok"));
        karyawan.setTunjangan(rs.getBigDecimal("tunjangan"));
        karyawan.setBank(rs.getString("bank"));
        karyawan.setNomorRekening(rs.getString("nomor_rekening"));
        
        // Populate joined user data if available in ResultSet
        try {
            karyawan.setNamaLengkap(rs.getString("nama_lengkap"));
            karyawan.setUsername(rs.getString("username"));
            karyawan.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            // Field not in result set, skip
        }
        
        return karyawan;
    }
}
