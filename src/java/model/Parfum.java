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
public class Parfum {
    private int id;
    private String nama;
    private String merk;
    private String kategori;
    private BigDecimal harga;
    private int stok;
    private String deskripsi;
    private int volumeMl;
    private BigDecimal rating;
    private String gambar;
    private String jenis;
    private Date createdAt;
    private Date updatedAt;
    
    // Constructors
    public Parfum() {}
    
    public Parfum(String nama, String merk, String kategori, BigDecimal harga, int stok) {
        this.nama = nama;
        this.merk = merk;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    
    public BigDecimal getHarga() { return harga; }
    public void setHarga(BigDecimal harga) { this.harga = harga; }
    
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    public int getVolumeMl() { return volumeMl; }
    public void setVolumeMl(int volumeMl) { this.volumeMl = volumeMl; }
    
    // Alias for frontend compatibility
    public int getVolume() { return volumeMl; }
    public void setVolume(int volume) { this.volumeMl = volume; }
    
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    
    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }
    
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
