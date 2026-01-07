<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Slip Gaji - ${penggajian.karyawan.namaLengkap}</title>
                <style>
                    body {
                        font-family: 'Courier New', Courier, monospace;
                        font-size: 14px;
                        margin: 20px;
                        color: #000;
                    }

                    .slip-container {
                        width: 450px;
                        margin: 0 auto;
                        padding: 10px;
                    }

                    .text-center {
                        text-align: center;
                    }

                    .text-right {
                        text-align: right;
                    }

                    pre {
                        margin: 0;
                        font-family: inherit;
                        font-size: inherit;
                    }

                    .signature-box {
                        margin-top: 30px;
                        display: flex;
                        justify-content: space-between;
                        padding: 0 30px;
                    }

                    @media print {
                        .no-print {
                            display: none;
                        }

                        body {
                            margin: 0;
                        }

                        .slip-container {
                            width: 100%;
                            border: none;
                        }
                    }
                </style>
            </head>

            <body>
                <div class="no-print" style="text-align: center; margin-bottom: 20px;">
                    <button onclick="window.print()"
                        style="padding: 10px 20px; cursor: pointer; background: #4a6cf7; color: white; border: none; border-radius: 5px;">
                        <i class="fas fa-print"></i> Cetak Slip Gaji
                    </button>
                    <button onclick="window.close()"
                        style="padding: 10px 20px; cursor: pointer; background: #6c757d; color: white; border: none; border-radius: 5px;">Tutup</button>
                </div>

                <div class="slip-container">
                    <div class="text-center">
                        ==========================================<br>
                        <strong>SLIP GAJI KARYAWAN</strong><br>
                        <strong>PARFUM ONLINE</strong><br>
                        ==========================================
                    </div>

                    <div style="margin: 10px 0;">
                        <pre>Nama Karyawan : ${penggajian.karyawan.namaLengkap}</pre>
                        <pre>NIP           : ${penggajian.karyawan.nik}</pre>
                        <pre>Jabatan       : ${penggajian.karyawan.jabatan}</pre>
                        <pre>Periode       : <fmt:setLocale value="id_ID"/><fmt:formatDate value="${penggajian.periode}" pattern="MMMM yyyy"/></pre>
                    </div>

                    <div class="text-center">
                        ==========================================<br>
                        <strong>RINCIAN GAJI</strong><br>
                        ==========================================
                    </div>

                    <div style="margin: 10px 0;">
                        <table style="width: 100%; border-collapse: collapse;">
                            <tr>
                                <td>Gaji Pokok</td>
                                <td>:</td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${penggajian.gajiPokok}" type="currency"
                                        currencySymbol="Rp " minFractionDigits="0" />
                                </td>
                            </tr>
                            <tr>
                                <td>Tunjangan</td>
                                <td>:</td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${penggajian.tunjangan}" type="currency"
                                        currencySymbol="Rp " minFractionDigits="0" />
                                </td>
                            </tr>
                            <tr>
                                <td>Potongan</td>
                                <td>:</td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${penggajian.potongan}" type="currency"
                                        currencySymbol="Rp " minFractionDigits="0" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">------------------------------------------</td>
                            </tr>
                            <tr style="font-weight: bold;">
                                <td>TOTAL GAJI</td>
                                <td>:</td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${penggajian.totalGaji}" type="currency"
                                        currencySymbol="Rp " minFractionDigits="0" />
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="text-center">
                        ==========================================<br>
                        Status : <strong>${penggajian.status}</strong><br>
                        ==========================================
                    </div>

                    <div class="signature-box">
                        <div class="text-center">
                            Karyawan<br><br><br>
                            ________<br>
                            ${penggajian.karyawan.namaLengkap}
                        </div>
                        <div class="text-center">
                            Admin<br><br><br>
                            _______<br>
                            ${not empty sessionScope.nama ? sessionScope.nama : 'Admin System'}
                        </div>
                    </div>

                    <div class="text-center" style="margin-top: 30px;">
                        ==========================================<br>
                        <jsp:useBean id="today" class="java.util.Date" />
                        Dicetak:
                        <fmt:formatDate value="${today}" pattern="yyyy-MM-dd HH:mm:ss" /><br>
                        ==========================================
                    </div>
                </div>
                </div>
            </body>

            </html>