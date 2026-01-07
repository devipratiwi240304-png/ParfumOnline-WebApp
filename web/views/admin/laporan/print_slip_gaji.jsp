<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Slip Gaji - ${slip.karyawan.namaLengkap}</title>
                <style>
                    body {
                        font-family: 'Courier New', Courier, monospace;
                        margin: 50px;
                        line-height: 1.6;
                    }

                    .container {
                        width: 600px;
                        margin: 0 auto;
                        border: 1px solid #000;
                        padding: 20px;
                    }

                    .header {
                        text-align: center;
                        border-bottom: 2px dashed #000;
                        padding-bottom: 10px;
                        margin-bottom: 20px;
                    }

                    .header h2 {
                        margin: 0;
                    }

                    .info-row {
                        display: flex;
                        justify-content: space-between;
                        margin-bottom: 5px;
                    }

                    .details {
                        margin-top: 20px;
                    }

                    .details-table {
                        width: 100%;
                        border-top: 1px dashed #000;
                        border-bottom: 1px dashed #000;
                        margin: 10px 0;
                        padding: 10px 0;
                    }

                    .total-row {
                        font-weight: bold;
                        margin-top: 10px;
                        font-size: 1.2em;
                    }

                    .signatures {
                        display: flex;
                        justify-content: space-between;
                        margin-top: 50px;
                    }

                    .signature-box {
                        text-align: center;
                        width: 200px;
                    }

                    .print-btn {
                        margin-bottom: 20px;
                        text-align: center;
                    }

                    @media print {
                        .print-btn {
                            display: none;
                        }

                        body {
                            margin: 20px;
                        }

                        .container {
                            border: none;
                            width: 100%;
                        }
                    }
                </style>
            </head>

            <body onload="window.print()">
                <div class="print-btn">
                    <button onclick="window.print()">Cetak</button>
                    <button onclick="history.back()">Kembali</button>
                </div>

                <div class="container">
                    <div class="header">
                        <h2>SLIP GAJI KARYAWAN</h2>
                        <p>PARFUM ONLINE</p>
                    </div>

                    <div class="info-row">
                        <span>Nama: ${slip.karyawan.namaLengkap}</span>
                        <span>NIP: ${slip.karyawan.nik}</span>
                    </div>
                    <div class="info-row">
                        <span>Jabatan: ${slip.karyawan.jabatan}</span>
                        <span>Periode:
                            <fmt:formatDate value="${slip.periode}" pattern="MMMM yyyy" />
                        </span>
                    </div>

                    <div class="details">
                        <div class="details-table">
                            <div class="info-row">
                                <span>Gaji Pokok</span>
                                <span>
                                    <fmt:formatNumber value="${slip.gajiPokok}" type="currency" currencySymbol="Rp " />
                                </span>
                            </div>
                            <div class="info-row">
                                <span>Tunjangan</span>
                                <span>
                                    <fmt:formatNumber value="${slip.tunjangan}" type="currency" currencySymbol="Rp " />
                                </span>
                            </div>
                            <div class="info-row">
                                <span>Bonus</span>
                                <span>
                                    <fmt:formatNumber value="${slip.bonus}" type="currency" currencySymbol="Rp " />
                                </span>
                            </div>
                            <div class="info-row">
                                <span>Potongan</span>
                                <span>(
                                    <fmt:formatNumber value="${slip.potongan}" type="currency" currencySymbol="Rp " />)
                                </span>
                            </div>
                        </div>

                        <div class="total-row info-row">
                            <span>TOTAL GAJI BERSIH</span>
                            <span>
                                <fmt:formatNumber value="${slip.totalGaji}" type="currency" currencySymbol="Rp " />
                            </span>
                        </div>
                    </div>

                    <div class="info-row" style="margin-top: 20px;">
                        <span>Status: ${slip.status}</span>
                        <span>Metode: ${slip.metodePembayaran}</span>
                    </div>

                    <div class="signatures">
                        <div class="signature-box">
                            <p>Penerima,</p>
                            <div style="height: 60px;"></div>
                            <p>( ${slip.karyawan.namaLengkap} )</p>
                        </div>
                        <div class="signature-box">
                            <p>Jakarta,
                                <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd MMMM yyyy" />
                            </p>
                            <p>Bendahara,</p>
                            <div style="height: 60px;"></div>
                            <p>( Admin )</p>
                        </div>
                    </div>
                </div>
            </body>

            </html>