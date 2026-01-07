/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;

/**
 *
 * @author Devi Pratiwi
 */
public class User {
       private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String namaLengkap;
    private Date tanggalBergabung;
    private String status;
    private Date createdAt;
    
    // Constructor
    public User() {}
    
    public User(String username, String password, String email, String role, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.namaLengkap = namaLengkap;
        this.tanggalBergabung = new Date();
        this.status = "active";
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    
    public Date getTanggalBergabung() { return tanggalBergabung; }
    public void setTanggalBergabung(Date tanggalBergabung) { this.tanggalBergabung = tanggalBergabung; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
}
