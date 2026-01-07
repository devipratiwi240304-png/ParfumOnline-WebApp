package model;

import java.math.BigDecimal;

public class DetailTransaksi {
    private int id;
    private int transaksiId;
    private int parfumId;
    private int jumlah;
    private BigDecimal hargaSatuan;
    private BigDecimal subtotal;
    
    // UI field
    private String namaParfum;

    public DetailTransaksi() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTransaksiId() { return transaksiId; }
    public void setTransaksiId(int transaksiId) { this.transaksiId = transaksiId; }

    public int getParfumId() { return parfumId; }
    public void setParfumId(int parfumId) { this.parfumId = parfumId; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public BigDecimal getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(BigDecimal hargaSatuan) { this.hargaSatuan = hargaSatuan; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public String getNamaParfum() { return namaParfum; }
    public void setNamaParfum(String namaParfum) { this.namaParfum = namaParfum; }
}
