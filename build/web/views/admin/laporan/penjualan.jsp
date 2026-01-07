<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="Laporan Penjualan" />
            </jsp:include>

            <div class="container-fluid py-4">
                <div class="row mb-4">
                    <div class="col-md-6">
                        <h1>Laporan Penjualan</h1>
                    </div>
                    <div class="col-md-6 text-end">
                        <a href="${pageContext.request.contextPath}/admin/laporan/generate-penjualan"
                            class="btn btn-primary">
                            <i class="fas fa-file-pdf"></i> Cetak Laporan (Semua)
                        </a>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Daftar Transaksi Terakhir</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Kode</th>
                                        <th>Tanggal</th>
                                        <th>Nama Pekerja</th>
                                        <th>Total</th>
                                        <th>Status</th>
                                        <th>Metode</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listTransaksi}" var="t">
                                        <tr>
                                            <td>${t.kodeTransaksi}</td>
                                            <td>
                                                <fmt:formatDate value="${t.tanggalTransaksi}"
                                                    pattern="dd/MM/yyyy HH:mm" />
                                            </td>
                                            <td>${t.namaLengkap}</td>
                                            <td>
                                                <fmt:formatNumber value="${t.totalHarga}" type="currency"
                                                    currencySymbol="Rp " />
                                            </td>
                                            <td>
                                                <span
                                                    class="badge ${t.status == 'selesai' ? 'bg-success' : 'bg-warning'}">
                                                    ${t.status}
                                                </span>
                                            </td>
                                            <td>${t.metodePembayaran}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty listTransaksi}">
                                        <tr>
                                            <td colspan="6" class="text-center">Belum ada data transaksi.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/views/templates/footer.jsp" />