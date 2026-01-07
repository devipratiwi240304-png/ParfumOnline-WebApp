/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.PasswordUtil;
/**
 *
 * @author Devi Pratiwi
 */
public class UserDAO extends BaseDAO  {
     public boolean create(User user) {
        String sql = "INSERT INTO users (username, password, email, role, nama_lengkap, tanggal_bergabung, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getNamaLengkap());
            ps.setDate(6, new java.sql.Date(user.getTanggalBergabung().getTime()));
            ps.setString(7, user.getStatus());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Get user by ID
    public User getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    // Get user by username
    public User getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND status = 'active'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return null;
    }
    
    // Get all users
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return users;
    }
    
    // Update user
    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, role = ?, nama_lengkap = ?, status = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getNamaLengkap());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Update password
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, PasswordUtil.hashPassword(newPassword));
            ps.setInt(2, userId);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Delete user (soft delete)
    public boolean delete(int id) {
        String sql = "UPDATE users SET status = 'inactive' WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        } finally {
            closeResources(ps);
        }
    }
    
    // Authenticate user
    public User authenticate(String username, String password) {
        User user = getByUsername(username);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    // Count active users
    public int countUsers() {
        String sql = "SELECT COUNT(*) as total FROM users WHERE status = 'active'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        return 0;
    }
    
    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setNamaLengkap(rs.getString("nama_lengkap"));
        user.setTanggalBergabung(rs.getDate("tanggal_bergabung"));
        user.setStatus(rs.getString("status"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    } 
}
