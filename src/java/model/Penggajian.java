/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Devi Pratiwi
 */
public class Penggajian {
     private int id;
    private int karyawanId;
    private Date periode;
    private BigDecimal gajiPokok;
    private BigDecimal tunjangan;
    private BigDecimal bonus;
    private BigDecimal potongan;
    private BigDecimal totalGaji;
    private String status;
    private Date tanggalPembayaran;
    private String metodePembayaran;
    private String catatan;
    private Date createdAt;
    private Karyawan karyawan;
    
    // Constructors
    public Penggajian() {}
    
    public Penggajian(int karyawanId, Date periode, BigDecimal totalGaji) {
        this.karyawanId = karyawanId;
        this.periode = periode;
        this.totalGaji = totalGaji;
        this.status = "pending";
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getKaryawanId() { return karyawanId; }
    public void setKaryawanId(int karyawanId) { this.karyawanId = karyawanId; }
    
    public Date getPeriode() { return periode; }
    public void setPeriode(Date periode) { this.periode = periode; }
    
    public BigDecimal getGajiPokok() { return gajiPokok; }
    public void setGajiPokok(BigDecimal gajiPokok) { this.gajiPokok = gajiPokok; }
    
    public BigDecimal getTunjangan() { return tunjangan; }
    public void setTunjangan(BigDecimal tunjangan) { this.tunjangan = tunjangan; }
    
    public BigDecimal getBonus() { return bonus; }
    public void setBonus(BigDecimal bonus) { this.bonus = bonus; }
    
    public BigDecimal getPotongan() { return potongan; }
    public void setPotongan(BigDecimal potongan) { this.potongan = potongan; }
    
    public BigDecimal getTotalGaji() { return totalGaji; }
    public void setTotalGaji(BigDecimal totalGaji) { this.totalGaji = totalGaji; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getTanggalPembayaran() { return tanggalPembayaran; }
    public void setTanggalPembayaran(Date tanggalPembayaran) { this.tanggalPembayaran = tanggalPembayaran; }
    
    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }
    
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Karyawan getKaryawan() { return karyawan; }
    public void setKaryawan(Karyawan karyawan) { this.karyawan = karyawan; }
    
    // Helper methods for JSP
    public String getBulan() {
        if (periode == null) return "";
        return new java.text.SimpleDateFormat("MM").format(periode);
    }
    
    public String getTahun() {
        if (periode == null) return "";
        return new java.text.SimpleDateFormat("yyyy").format(periode);
    }
}
