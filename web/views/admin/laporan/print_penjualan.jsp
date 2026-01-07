<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Laporan Penjualan</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 30px;
                    }

                    .header {
                        text-align: center;
                        margin-bottom: 30px;
                    }

                    .header h1 {
                        margin: 0;
                        text-transform: uppercase;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px;
                    }

                    th,
                    td {
                        border: 1px solid #ddd;
                        padding: 10px;
                        text-align: left;
                    }

                    th {
                        background-color: #f2f2f2;
                    }

                    .footer {
                        margin-top: 50px;
                        text-align: right;
                    }

                    .print-btn {
                        margin-bottom: 20px;
                    }

                    @media print {
                        .print-btn {
                            display: none;
                        }
                    }
                </style>
            </head>

            <body onload="window.print()">
                <div class="print-btn">
                    <button onclick="window.print()">Cetak</button>
                    <button onclick="history.back()">Kembali</button>
                </div>

                <div class="header">
                    <h1>Laporan Penjualan</h1>
                    <p>Parfum Online - Toko Parfum Terpercaya</p>
                    <p>Tanggal Cetak:
                        <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy HH:mm" />
                    </p>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Kode Transaksi</th>
                            <th>Tanggal</th>
                            <th>Nama Pekerja</th>
                            <th>Total Harga</th>
                            <th>Status</th>
                            <th>Metode</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listTransaksi}" var="t" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${t.kodeTransaksi}</td>
                                <td>
                                    <fmt:formatDate value="${t.tanggalTransaksi}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>${t.namaLengkap}</td>
                                <td>
                                    <fmt:formatNumber value="${t.totalHarga}" type="currency" currencySymbol="Rp " />
                                </td>
                                <td>${t.status}</td>
                                <td>${t.metodePembayaran}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th colspan="4" style="text-align: right;">Total Penjualan</th>
                            <th colspan="3">
                                <c:set var="total" value="0" />
                                <c:forEach items="${listTransaksi}" var="t">
                                    <c:set var="total" value="${total + t.totalHarga}" />
                                </c:forEach>
                                <fmt:formatNumber value="${total}" type="currency" currencySymbol="Rp " />
                            </th>
                        </tr>
                    </tfoot>
                </table>

                <div class="footer">
                    <p>Dicetak oleh: Admin</p>
                </div>
            </body>

            </html>