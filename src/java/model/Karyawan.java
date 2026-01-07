/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;

/**
 *
 * @author Devi Pratiwi
 */
public class Karyawan {
    private int id;
    private int userId;
    private String nik;
    private String jabatan;
    private BigDecimal gajiPokok;
    private BigDecimal tunjangan;
    private String bank;
    private String nomorRekening;
    private String namaLengkap;
    private String username;
    private String email;
    private User user;
    
    // Constructors
    public Karyawan() {}
    
    public Karyawan(String nik, String jabatan, BigDecimal gajiPokok) {
        this.nik = nik;
        this.jabatan = jabatan;
        this.gajiPokok = gajiPokok;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }
    
    public BigDecimal getGajiPokok() { return gajiPokok; }
    public void setGajiPokok(BigDecimal gajiPokok) { this.gajiPokok = gajiPokok; }
    
    public BigDecimal getTunjangan() { return tunjangan; }
    public void setTunjangan(BigDecimal tunjangan) { this.tunjangan = tunjangan; }
    
    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }
    
    public String getNomorRekening() { return nomorRekening; }
    public void setNomorRekening(String nomorRekening) { this.nomorRekening = nomorRekening; }
    
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    // Helper method
    public BigDecimal getTotalGaji() {
        BigDecimal total = gajiPokok != null ? gajiPokok : BigDecimal.ZERO;
        total = total.add(tunjangan != null ? tunjangan : BigDecimal.ZERO);
        return total;
    } 
}
