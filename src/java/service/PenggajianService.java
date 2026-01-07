/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import dao.AbsensiDAO;
import dao.KaryawanDAO;
import dao.PenggajianDAO;
import model.Karyawan;
import model.Penggajian;
/**
 *
 * @author Devi Pratiwi
 */
public class PenggajianService {
     private PenggajianDAO penggajianDAO;
    private KaryawanDAO karyawanDAO;
    private AbsensiDAO absensiDAO;
    
    public PenggajianService() {
        this.penggajianDAO = new PenggajianDAO();
        this.karyawanDAO = new KaryawanDAO();
        this.absensiDAO = new AbsensiDAO();
    }
    
    // Hitung gaji karyawan untuk periode tertentu
    public Penggajian hitungGaji(int karyawanId, Date periode, BigDecimal bonus, BigDecimal potongan) {
        Karyawan karyawan = karyawanDAO.getById(karyawanId);
        if (karyawan == null) {
            throw new IllegalArgumentException("Karyawan tidak ditemukan");
        }
        
        // Hitung jumlah hari hadir
        Calendar cal = Calendar.getInstance();
        cal.setTime(periode);
        int tahun = cal.get(Calendar.YEAR);
        int bulan = cal.get(Calendar.MONTH) + 1;
        
        int hariHadir = absensiDAO.getHariHadir(karyawanId, tahun, bulan);
        int hariKerja = getHariKerja(tahun, bulan);
        
        // Hitung gaji berdasarkan kehadiran
        BigDecimal gajiPokok = karyawan.getGajiPokok();
        BigDecimal gajiPerHari = gajiPokok.divide(new BigDecimal(hariKerja), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal gajiBerdasarkanKehadiran = gajiPerHari.multiply(new BigDecimal(hariHadir));
        
        // Hitung total gaji
        BigDecimal total = gajiBerdasarkanKehadiran
            .add(karyawan.getTunjangan() != null ? karyawan.getTunjangan() : BigDecimal.ZERO)
            .add(bonus != null ? bonus : BigDecimal.ZERO)
            .subtract(potongan != null ? potongan : BigDecimal.ZERO);
        
        Penggajian penggajian = new Penggajian();
        penggajian.setKaryawanId(karyawanId);
        penggajian.setPeriode(periode);
        penggajian.setGajiPokok(gajiBerdasarkanKehadiran);
        penggajian.setTunjangan(karyawan.getTunjangan());
        penggajian.setBonus(bonus);
        penggajian.setPotongan(potongan);
        penggajian.setTotalGaji(total);
        penggajian.setStatus("pending");
        penggajian.setMetodePembayaran("transfer");
        
        return penggajian;
    }
    
    // Proses penggajian untuk semua karyawan
    public boolean prosesPenggajianBulanan(Date periode) {
        List<Karyawan> semuaKaryawan = karyawanDAO.getAll();
        boolean semuaBerhasil = true;
        
        for (Karyawan karyawan : semuaKaryawan) {
            try {
                Penggajian penggajian = hitungGaji(
                    karyawan.getId(), 
                    periode, 
                    BigDecimal.ZERO, 
                    BigDecimal.ZERO
                );
                
                penggajianDAO.create(penggajian);
            } catch (Exception e) {
                e.printStackTrace();
                semuaBerhasil = false;
            }
        }
        
        return semuaBerhasil;
    }
    
    // Helper method untuk menghitung hari kerja dalam bulan
    private int getHariKerja(int tahun, int bulan) {
        Calendar cal = Calendar.getInstance();
        cal.set(tahun, bulan - 1, 1);
        
        int hariKerja = 0;
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        for (int day = 1; day <= maxDay; day++) {
            cal.set(Calendar.DAY_OF_MONTH, day);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            // Sabtu = 7, Minggu = 1
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                hariKerja++;
            }
        }
        
        return hariKerja;
    }
    
    // Generate slip gaji
    public String generateSlipGaji(int penggajianId) {
        Penggajian penggajian = penggajianDAO.getById(penggajianId);
        if (penggajian == null) {
            return null;
        }
        
        // Format slip gaji sebagai HTML atau text
        StringBuilder slip = new StringBuilder();
        slip.append("SLIP GAJI - ").append(penggajian.getKaryawan().getNik()).append("\n");
        slip.append("=================================\n");
        slip.append("Nama: ").append(penggajian.getKaryawan().getUser().getNamaLengkap()).append("\n");
        slip.append("Jabatan: ").append(penggajian.getKaryawan().getJabatan()).append("\n");
        slip.append("Periode: ").append(penggajian.getPeriode()).append("\n");
        slip.append("---------------------------------\n");
        slip.append("Gaji Pokok: Rp ").append(penggajian.getGajiPokok()).append("\n");
        slip.append("Tunjangan: Rp ").append(penggajian.getTunjangan()).append("\n");
        slip.append("Bonus: Rp ").append(penggajian.getBonus()).append("\n");
        slip.append("Potongan: Rp ").append(penggajian.getPotongan()).append("\n");
        slip.append("---------------------------------\n");
        slip.append("TOTAL GAJI: Rp ").append(penggajian.getTotalGaji()).append("\n");
        slip.append("=================================\n");
        slip.append("Status: ").append(penggajian.getStatus()).append("\n");
        slip.append("Tanggal Pembayaran: ").append(penggajian.getTanggalPembayaran()).append("\n");
        
        return slip.toString();
    }
}
