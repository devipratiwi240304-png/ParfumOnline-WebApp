/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Devi Pratiwi
 */
public class AbsensiDAO extends BaseDAO {
    // Get jumlah hari hadir
    public int getHariHadir(int karyawanId, int tahun, int bulan) {
        String sql = "SELECT COUNT(*) as hari_hadir FROM absensi WHERE karyawan_id = ? " +
                    "AND YEAR(tanggal) = ? AND MONTH(tanggal) = ? AND status = 'hadir'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, karyawanId);
            ps.setInt(2, tahun);
            ps.setInt(3, bulan);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("hari_hadir");
            }
        } catch (SQLException e) {
            System.err.println("Error getting hari hadir: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return 0;
    }
}
