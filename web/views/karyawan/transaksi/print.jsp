<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Struk Transaksi - ${transaksi.kodeTransaksi}</title>
                <style>
                    body {
                        font-family: 'Courier New', Courier, monospace;
                        font-size: 14px;
                        margin: 20px;
                        color: #000;
                    }

                    .struk-container {
                        width: 350px;
                        margin: 0 auto;
                        padding: 10px;
                        border: 1px dashed #ccc;
                    }

                    .text-center {
                        text-align: center;
                    }

                    .text-right {
                        text-align: right;
                    }

                    .divider {
                        border-top: 1px dashed #000;
                        margin: 10px 0;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                    }

                    .no-print {
                        text-align: center;
                        margin-bottom: 20px;
                    }

                    @media print {
                        .no-print {
                            display: none;
                        }

                        body {
                            margin: 0;
                        }

                        .struk-container {
                            width: 100%;
                            border: none;
                        }
                    }
                </style>
            </head>

            <body>
                <div class="no-print">
                    <button onclick="window.print()"
                        style="padding: 8px 16px; cursor: pointer; background: #4a6cf7; color: white; border: none; border-radius: 4px;">
                        <i class="fas fa-print"></i> Cetak Struk
                    </button>
                    <button onclick="window.close()"
                        style="padding: 8px 16px; cursor: pointer; background: #6c757d; color: white; border: none; border-radius: 4px;">Tutup</button>
                </div>

                <div class="struk-container">
                    <div class="text-center">
                        <strong>PARFUM ONLINE</strong><br>
                        Jl. Wangi Sekali No. 123<br>
                        Telp: 0812-3456-7890<br>
                        ==============================
                    </div>

                    <div style="font-size: 12px; margin: 5px 0;">
                        ID Trans : ${transaksi.kodeTransaksi}<br>
                        Tanggal :
                        <fmt:formatDate value="${transaksi.tanggalTransaksi}" pattern="dd/MM/yyyy HH:mm" /><br>
                        Kasir : ${transaksi.namaLengkap}
                    </div>

                    <div class="divider"></div>

                    <table>
                        <c:forEach var="detail" items="${transaksi.details}">
                            <tr>
                                <td colspan="3">${detail.namaParfum}</td>
                            </tr>
                            <tr>
                                <td width="40%">${detail.jumlah} x
                                    <fmt:formatNumber value="${detail.hargaSatuan}" pattern="#,###" />
                                </td>
                                <td class="text-right">
                                    <fmt:formatNumber value="${detail.subtotal}" pattern="#,###" />
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div class="divider"></div>

                    <table>
                        <tr>
                            <td><strong>TOTAL</strong></td>
                            <td class="text-right"><strong>
                                    <fmt:formatNumber value="${transaksi.totalHarga}" pattern="#,###" />
                                </strong></td>
                        </tr>
                        <tr>
                            <td>BAYAR (${transaksi.metodePembayaran})</td>
                            <td class="text-right">
                                <fmt:formatNumber value="${transaksi.pembayaran}" pattern="#,###" />
                            </td>
                        </tr>
                        <tr>
                            <td>KEMBALI</td>
                            <td class="text-right">
                                <fmt:formatNumber value="${transaksi.kembalian}" pattern="#,###" />
                            </td>
                        </tr>
                    </table>

                    <div class="divider"></div>

                    <div class="text-center" style="font-size: 12px; margin-top: 10px;">
                        Terima Kasih Atas Kunjungan Anda<br>
                        Barang yang sudah dibeli<br>
                        tidak dapat ditukar/dikembalikan<br>
                        ==============================
                    </div>
                </div>
            </body>

            </html>