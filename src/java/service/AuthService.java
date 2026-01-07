/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import dao.UserDAO;
import model.User;
import util.PasswordUtil;
/**
 *
 * @author Devi Pratiwi
 */
public class AuthService {
  private UserDAO userDAO;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    public User login(String username, String password) {
        return userDAO.authenticate(username, password);
    }
    
    public boolean register(User user) {
        // Validasi data
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password minimal 6 karakter");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email tidak valid");
        }
        
        return userDAO.create(user);
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User tidak ditemukan");
        }
        
        // Verifikasi password lama
        if (!PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Password lama tidak sesuai");
        }
        
        return userDAO.updatePassword(userId, newPassword);
    }
    
    public boolean isUsernameAvailable(String username) {
        return userDAO.getByUsername(username) == null;
    }  
}
