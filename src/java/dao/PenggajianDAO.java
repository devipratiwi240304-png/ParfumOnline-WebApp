/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import model.Penggajian;
import model.Karyawan;
/**
 *
 * @author Devi Pratiwi
 */
public class PenggajianDAO extends BaseDAO {
     public boolean create(Penggajian penggajian) {
        String sql = "INSERT INTO penggajian (karyawan_id, periode, gaji_pokok, tunjangan, bonus, potongan, " +
                    "total_gaji, status, metode_pembayaran, catatan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, penggajian.getKaryawanId());
            ps.setDate(2, new java.sql.Date(penggajian.getPeriode().getTime()));
            ps.setBigDecimal(3, penggajian.getGajiPokok());
            ps.setBigDecimal(4, penggajian.getTunjangan());
            ps.setBigDecimal(5, penggajian.getBonus());
            ps.setBigDecimal(6, penggajian.getPotongan());
            ps.setBigDecimal(7, penggajian.getTotalGaji());
            ps.setString(8, penggajian.getStatus());
            ps.setString(9, penggajian.getMetodePembayaran());
            ps.setString(10, penggajian.getCatatan());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error creating penggajian: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get penggajian by ID
    public Penggajian getById(int id) {
        String sql = "SELECT p.*, k.nik, k.jabatan, u.nama_lengkap FROM penggajian p " +
                    "JOIN karyawan k ON p.karyawan_id = k.id " +
                    "JOIN users u ON k.user_id = u.id WHERE p.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPenggajian(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting penggajian by ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    // Get all penggajian with filter
    public List<Penggajian> getAll(String periode, String status) {
        List<Penggajian> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, k.nik, u.nama_lengkap FROM penggajian p " +
            "JOIN karyawan k ON p.karyawan_id = k.id " +
            "JOIN users u ON k.user_id = u.id WHERE 1=1"
        );
        
        if (periode != null && !periode.isEmpty()) {
            sql.append(" AND DATE_FORMAT(p.periode, '%Y-%m') = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND p.status = ?");
        }
        sql.append(" ORDER BY p.periode DESC, u.nama_lengkap");
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (periode != null && !periode.isEmpty()) {
                ps.setString(paramIndex++, periode);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToPenggajian(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all penggajian: " + e.getMessage());
        }
        return list;
    }
    
    // Update penggajian status
    public boolean updateStatus(int id, String status, Date tanggalPembayaran) {
        String sql = "UPDATE penggajian SET status = ?, tanggal_pembayaran = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setDate(2, new java.sql.Date(tanggalPembayaran.getTime()));
            ps.setInt(3, id);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating penggajian status: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get penggajian by karyawan ID
    public List<Penggajian> getByKaryawanId(int karyawanId) {
        List<Penggajian> list = new ArrayList<>();
        String sql = "SELECT * FROM penggajian WHERE karyawan_id = ? ORDER BY periode DESC";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, karyawanId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(mapResultSetToPenggajian(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting penggajian by karyawan ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return list;
    }
    
    // Get total penggajian per periode
    public BigDecimal getTotalGajiPeriode(String periode) {
        String sql = "SELECT SUM(total_gaji) as total FROM penggajian WHERE DATE_FORMAT(periode, '%Y-%m') = ? AND status = 'dibayar'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, periode);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total gaji: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return BigDecimal.ZERO;
    }
    
    public boolean update(Penggajian penggajian) {
        String sql = "UPDATE penggajian SET karyawan_id = ?, periode = ?, gaji_pokok = ?, tunjangan = ?, " +
                    "bonus = ?, potongan = ?, total_gaji = ?, status = ?, metode_pembayaran = ?, catatan = ? " +
                    "WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, penggajian.getKaryawanId());
            ps.setDate(2, new java.sql.Date(penggajian.getPeriode().getTime()));
            ps.setBigDecimal(3, penggajian.getGajiPokok());
            ps.setBigDecimal(4, penggajian.getTunjangan());
            ps.setBigDecimal(5, penggajian.getBonus());
            ps.setBigDecimal(6, penggajian.getPotongan());
            ps.setBigDecimal(7, penggajian.getTotalGaji());
            ps.setString(8, penggajian.getStatus());
            ps.setString(9, penggajian.getMetodePembayaran());
            ps.setString(10, penggajian.getCatatan());
            ps.setInt(11, penggajian.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating penggajian: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM penggajian WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting penggajian: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Helper method to map ResultSet to Penggajian object
    private Penggajian mapResultSetToPenggajian(ResultSet rs) throws SQLException {
        Penggajian penggajian = new Penggajian();
        penggajian.setId(rs.getInt("id"));
        penggajian.setKaryawanId(rs.getInt("karyawan_id"));
        penggajian.setPeriode(rs.getDate("periode"));
        penggajian.setGajiPokok(rs.getBigDecimal("gaji_pokok"));
        penggajian.setTunjangan(rs.getBigDecimal("tunjangan"));
        penggajian.setBonus(rs.getBigDecimal("bonus"));
        penggajian.setPotongan(rs.getBigDecimal("potongan"));
        penggajian.setTotalGaji(rs.getBigDecimal("total_gaji"));
        penggajian.setStatus(rs.getString("status"));
        penggajian.setTanggalPembayaran(rs.getDate("tanggal_pembayaran"));
        penggajian.setMetodePembayaran(rs.getString("metode_pembayaran"));
        penggajian.setCatatan(rs.getString("catatan"));
        penggajian.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Populate joined Karyawan data
        try {
            Karyawan k = new Karyawan();
            k.setId(rs.getInt("karyawan_id"));
            k.setNik(rs.getString("nik"));
            k.setNamaLengkap(rs.getString("nama_lengkap"));
            try { k.setJabatan(rs.getString("jabatan")); } catch (SQLException e) {}
            penggajian.setKaryawan(k);
        } catch (SQLException e) {
            // Fields might not be in the ResultSet if not joined
        }
        
        return penggajian;
    }
}
