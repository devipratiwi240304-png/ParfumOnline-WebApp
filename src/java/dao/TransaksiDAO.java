package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import model.Transaksi;
import model.DetailTransaksi;

/**
 *
 * @author Devi Pratiwi
 */
public class TransaksiDAO extends BaseDAO {
    private String lastError;

    public String getLastError() {
        return lastError;
    }
    
    public TransaksiDAO() {
        super();
        ensureColumnsExist();
    }
    
    private void ensureColumnsExist() {
        if (connection == null) {
            System.err.println("Cannot ensure columns exist: Database connection is null!");
            return;
        }
        try {
            String[] columns = {"pembayaran", "kembalian"};
            for (String col : columns) {
                try (Statement stmt = connection.createStatement()) {
                    // Check if column exists by trying to select it from a limit 0 query
                    try {
                        stmt.executeQuery("SELECT " + col + " FROM transaksi LIMIT 0");
                    } catch (SQLException e) {
                        // Column likely doesn't exist, try to add it
                        System.out.println("Column " + col + " missing in transaksi table. Attempting to add...");
                        stmt.executeUpdate("ALTER TABLE transaksi ADD COLUMN " + col + " DECIMAL(12,2) NOT NULL DEFAULT 0");
                        System.out.println("Column " + col + " added successfully.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error ensuring column " + col + " exists: " + e.getMessage());
                }
            }
            
            // Fix Column 'metode_pembayaran' which is restricted ENUM in original SQL
            try (Statement stmt = connection.createStatement()) {
                System.out.println("Ensuring 'metode_pembayaran' supports 'tunai'...");
                stmt.executeUpdate("ALTER TABLE transaksi MODIFY COLUMN metode_pembayaran VARCHAR(50) DEFAULT 'tunai'");
            } catch (SQLException e) {
                System.err.println("Error altering metode_pembayaran: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Critical error in ensureColumnsExist: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Transaksi> getAll(Integer userId) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, u.nama_lengkap, (SELECT COUNT(*) FROM detail_transaksi dt WHERE dt.transaksi_id = t.id) as jumlah_item " +
                    "FROM transaksi t " +
                    "JOIN users u ON t.user_id = u.id ";
        
        if (userId != null) {
            sql += "WHERE t.user_id = ? ";
        }
        
        sql += "ORDER BY t.tanggal_transaksi DESC";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            if (userId != null) {
                ps.setInt(1, userId);
            }
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setKodeTransaksi(rs.getString("kode_transaksi"));
                t.setUserId(rs.getInt("user_id"));
                t.setNamaLengkap(rs.getString("nama_lengkap"));
                t.setTanggalTransaksi(rs.getTimestamp("tanggal_transaksi"));
                t.setTotalHarga(rs.getBigDecimal("total_harga"));
                t.setStatus(rs.getString("status"));
                t.setMetodePembayaran(rs.getString("metode_pembayaran"));
                t.setJumlahItem(rs.getInt("jumlah_item"));
                
                // Fields that might need addition to DB
                try {
                    t.setPembayaran(rs.getBigDecimal("pembayaran"));
                    t.setKembalian(rs.getBigDecimal("kembalian"));
                } catch (SQLException e) {
                    // Columns might not exist yet
                    t.setPembayaran(BigDecimal.ZERO);
                    t.setKembalian(BigDecimal.ZERO);
                }
                
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaksi: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return list;
    }
    
    public List<Transaksi> getRecentTransactions(int limit) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, u.nama_lengkap, (SELECT COUNT(*) FROM detail_transaksi dt WHERE dt.transaksi_id = t.id) as jumlah_item " +
                    "FROM transaksi t " +
                    "JOIN users u ON t.user_id = u.id " +
                    "ORDER BY t.tanggal_transaksi DESC LIMIT ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setKodeTransaksi(rs.getString("kode_transaksi"));
                t.setUserId(rs.getInt("user_id"));
                t.setNamaLengkap(rs.getString("nama_lengkap"));
                t.setTanggalTransaksi(rs.getTimestamp("tanggal_transaksi"));
                t.setTotalHarga(rs.getBigDecimal("total_harga"));
                t.setStatus(rs.getString("status"));
                t.setMetodePembayaran(rs.getString("metode_pembayaran"));
                t.setJumlahItem(rs.getInt("jumlah_item"));
                
                try {
                    t.setPembayaran(rs.getBigDecimal("pembayaran"));
                    t.setKembalian(rs.getBigDecimal("kembalian"));
                } catch (SQLException e) {
                    t.setPembayaran(BigDecimal.ZERO);
                    t.setKembalian(BigDecimal.ZERO);
                }
                
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error getting recent transactions: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return list;
    }

    public Transaksi getById(int id) {
        String sql = "SELECT t.*, u.nama_lengkap FROM transaksi t " +
                    "JOIN users u ON t.user_id = u.id WHERE t.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setKodeTransaksi(rs.getString("kode_transaksi"));
                t.setUserId(rs.getInt("user_id"));
                t.setNamaLengkap(rs.getString("nama_lengkap"));
                t.setTanggalTransaksi(rs.getTimestamp("tanggal_transaksi"));
                t.setTotalHarga(rs.getBigDecimal("total_harga"));
                t.setStatus(rs.getString("status"));
                t.setMetodePembayaran(rs.getString("metode_pembayaran"));
                
                try {
                    t.setPembayaran(rs.getBigDecimal("pembayaran"));
                    t.setKembalian(rs.getBigDecimal("kembalian"));
                } catch (SQLException e) {
                    t.setPembayaran(BigDecimal.ZERO);
                    t.setKembalian(BigDecimal.ZERO);
                }
                
                t.setDetails(getDetailsByTransaksiId(id));
                return t;
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaksi by id: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }

    public List<DetailTransaksi> getDetailsByTransaksiId(int transaksiId) {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT dt.*, p.nama as nama_parfum FROM detail_transaksi dt " +
                    "JOIN parfum p ON dt.parfum_id = p.id WHERE dt.transaksi_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, transaksiId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                DetailTransaksi dt = new DetailTransaksi();
                dt.setId(rs.getInt("id"));
                dt.setTransaksiId(rs.getInt("transaksi_id"));
                dt.setParfumId(rs.getInt("parfum_id"));
                dt.setNamaParfum(rs.getString("nama_parfum"));
                dt.setJumlah(rs.getInt("jumlah"));
                dt.setHargaSatuan(rs.getBigDecimal("harga_satuan"));
                dt.setSubtotal(rs.getBigDecimal("subtotal"));
                list.add(dt);
            }
        } catch (SQLException e) {
            System.err.println("Error getting detail transaksi: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return list;
    }

    public boolean save(Transaksi transaksi) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection.setAutoCommit(false);
            
            // 1. Insert Transaksi
            String sqlT = "INSERT INTO transaksi (kode_transaksi, user_id, total_harga, status, metode_pembayaran, pembayaran, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sqlT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaksi.getKodeTransaksi());
            ps.setInt(2, transaksi.getUserId());
            ps.setBigDecimal(3, transaksi.getTotalHarga());
            ps.setString(4, "selesai");
            ps.setString(5, transaksi.getMetodePembayaran() != null ? transaksi.getMetodePembayaran() : "tunai");
            ps.setBigDecimal(6, transaksi.getPembayaran());
            ps.setBigDecimal(7, transaksi.getKembalian());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Inserting transaksi failed.");
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                transaksi.setId(rs.getInt(1));
            }
            
            // 2. Insert Details and Update Stock
            String sqlD = "INSERT INTO detail_transaksi (transaksi_id, parfum_id, jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";
            String sqlS = "UPDATE parfum SET stok = stok - ? WHERE id = ?";
            
            PreparedStatement psD = connection.prepareStatement(sqlD);
            PreparedStatement psS = connection.prepareStatement(sqlS);
            
            for (DetailTransaksi detail : transaksi.getDetails()) {
                // Save detail
                psD.setInt(1, transaksi.getId());
                psD.setInt(2, detail.getParfumId());
                psD.setInt(3, detail.getJumlah());
                psD.setBigDecimal(4, detail.getHargaSatuan());
                psD.setBigDecimal(5, detail.getSubtotal());
                psD.executeUpdate();
                
                // Update stock
                psS.setInt(1, detail.getJumlah());
                psS.setInt(2, detail.getParfumId());
                psS.executeUpdate();
            }
            
            psD.close();
            psS.close();
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            this.lastError = e.getMessage();
            try { connection.rollback(); } catch (SQLException re) { re.printStackTrace(); }
            System.err.println("Error saving transaksi: " + e.getMessage());
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            closeResources(rs, ps);
        }
    }
    
    // Get total penjualan per bulan
    public BigDecimal getTotalPenjualanBulanan(int tahun, int bulan) {
        String sql = "SELECT SUM(total_harga) as total FROM transaksi " +
                    "WHERE YEAR(tanggal_transaksi) = ? AND MONTH(tanggal_transaksi) = ? AND status = 'selesai'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, tahun);
            ps.setInt(2, bulan);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            System.err.println("Error getting total penjualan: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return BigDecimal.ZERO;
    }

    public int getTransaksiCountToday(Integer userId) {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE DATE(tanggal_transaksi) = CURDATE()";
        if (userId != null) sql += " AND user_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (userId != null) ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BigDecimal getTotalPenjualanToday(Integer userId) {
        String sql = "SELECT SUM(total_harga) FROM transaksi WHERE DATE(tanggal_transaksi) = CURDATE()";
        if (userId != null) sql += " AND user_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (userId != null) ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal(1);
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public String getTopSellingParfum(Integer userId) {
        String sql = "SELECT p.nama, SUM(dt.jumlah) as total_qty " +
                    "FROM detail_transaksi dt " +
                    "JOIN transaksi t ON dt.transaksi_id = t.id " +
                    "JOIN parfum p ON dt.parfum_id = p.id " +
                    "WHERE DATE(t.tanggal_transaksi) = CURDATE() ";
        if (userId != null) sql += "AND t.user_id = ? ";
        sql += "GROUP BY p.id ORDER BY total_qty DESC LIMIT 1";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (userId != null) ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("nama");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-";
    }
}
