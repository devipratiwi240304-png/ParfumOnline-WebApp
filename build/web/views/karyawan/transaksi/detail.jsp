<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="Detail Transaksi" />
            </jsp:include>

            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h3>Detail Transaksi #${transaksi.kodeTransaksi}</h3>
                    <div>
                        <a href="${pageContext.request.contextPath}/karyawan/transaksi/list" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Kembali
                        </a>
                        <a href="${pageContext.request.contextPath}/karyawan/transaksi/print?id=${transaksi.id}"
                            class="btn btn-warning" target="_blank">
                            <i class="fas fa-print"></i> Cetak Struk
                        </a>
                    </div>
                </div>
                <div class="card-body">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h5>Informasi Transaksi</h5>
                            <table class="table table-borderless table-sm">
                                <tr>
                                    <td width="35%">Tanggal</td>
                                    <td>:
                                        <fmt:formatDate value="${transaksi.tanggalTransaksi}"
                                            pattern="dd MMM yyyy, HH:mm" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>Kasir</td>
                                    <td>: ${transaksi.namaLengkap}</td>
                                </tr>
                                <tr>
                                    <td>Status</td>
                                    <td>: <span class="badge bg-success">${transaksi.status}</span></td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6 text-md-end">
                            <h5>Metode Pembayaran</h5>
                            <h4 class="text-primary text-uppercase">${transaksi.metodePembayaran}</h4>
                        </div>
                    </div>

                    <h5>Rincian Produk</h5>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Produk</th>
                                <th class="text-center">Qty</th>
                                <th class="text-end">Harga Satuan</th>
                                <th class="text-end">Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="detail" items="${transaksi.details}">
                                <tr>
                                    <td>${detail.namaParfum}</td>
                                    <td class="text-center">${detail.jumlah}</td>
                                    <td class="text-end">
                                        <fmt:formatNumber value="${detail.hargaSatuan}" type="currency" />
                                    </td>
                                    <td class="text-end">
                                        <fmt:formatNumber value="${detail.subtotal}" type="currency" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="3" class="text-end">TOTAL</th>
                                <th class="text-end">
                                    <fmt:formatNumber value="${transaksi.totalHarga}" type="currency" />
                                </th>
                            </tr>
                            <tr>
                                <th colspan="3" class="text-end">Bayar</th>
                                <td class="text-end">
                                    <fmt:formatNumber value="${transaksi.pembayaran}" type="currency" />
                                </td>
                            </tr>
                            <tr>
                                <th colspan="3" class="text-end">Kembali</th>
                                <td class="text-end">
                                    <fmt:formatNumber value="${transaksi.kembalian}" type="currency" />
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <jsp:include page="/views/templates/footer.jsp" />