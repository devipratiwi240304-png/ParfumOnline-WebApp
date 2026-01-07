package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Transaksi {
    private int id;
    private String kodeTransaksi;
    private int userId;
    private Date tanggalTransaksi;
    private BigDecimal totalHarga;
    private String status;
    private String metodePembayaran;
    
    // UI fields
    private BigDecimal pembayaran;
    private BigDecimal kembalian;
    private int jumlahItem;
    private List<DetailTransaksi> details;
    
    private String namaLengkap; // from join with users

    public Transaksi() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKodeTransaksi() { return kodeTransaksi; }
    public void setKodeTransaksi(String kodeTransaksi) { this.kodeTransaksi = kodeTransaksi; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getTanggalTransaksi() { return tanggalTransaksi; }
    public void setTanggalTransaksi(Date tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }

    public BigDecimal getTotalHarga() { return totalHarga; }
    public void setTotalHarga(BigDecimal totalHarga) { this.totalHarga = totalHarga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }

    public BigDecimal getPembayaran() { return pembayaran; }
    public void setPembayaran(BigDecimal pembayaran) { this.pembayaran = pembayaran; }

    public BigDecimal getKembalian() { return kembalian; }
    public void setKembalian(BigDecimal kembalian) { this.kembalian = kembalian; }

    public int getJumlahItem() { return jumlahItem; }
    public void setJumlahItem(int jumlahItem) { this.jumlahItem = jumlahItem; }

    public List<DetailTransaksi> getDetails() { return details; }
    public void setDetails(List<DetailTransaksi> details) { this.details = details; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
}
