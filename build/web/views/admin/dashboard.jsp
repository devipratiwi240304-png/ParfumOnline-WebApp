<%-- Document : dashboard Created on : 3 Jan 2026, 01.15.33 Author : Devi Pratiwi --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <jsp:include page="/views/templates/header.jsp">
                    <jsp:param name="title" value="Dashboard Admin" />
                </jsp:include>
                <div class="dashboard">
                    <h1>Dashboard Admin</h1>

                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-wind"></i>
                            </div>
                            <div class="stat-info">
                                <h3>${totalParfum}</h3>
                                <p>Total Parfum</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-users"></i>
                            </div>
                            <div class="stat-info">
                                <h3>${totalKaryawan}</h3>
                                <p>Total Karyawan</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-money-bill-wave"></i>
                            </div>
                            <div class="stat-info">
                                <h3>
                                    <fmt:formatNumber value="${totalPenjualan}" type="currency" />
                                </h3>
                                <p>Total Penjualan Bulan Ini</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-chart-line"></i>
                            </div>
                            <div class="stat-info">
                                <h3>${transaksiHariIni}</h3>
                                <p>Transaksi Hari Ini</p>
                            </div>
                        </div>
                    </div>

                    <div class="recent-activities">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h2 class="mb-0">Aktivitas Terbaru</h2>
                            <a href="${pageContext.request.contextPath}/admin/karyawan/transaksi"
                                class="btn btn-sm btn-primary">
                                <i class="fas fa-list"></i> Lihat Semua
                            </a>
                        </div>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Waktu</th>
                                    <th>Aktivitas</th>
                                    <th>User</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="transaksi" items="${recentActivities}">
                                    <tr>
                                        <td>
                                            <fmt:formatDate value="${transaksi.tanggalTransaksi}"
                                                pattern="dd MMM yyyy, HH:mm" />
                                        </td>
                                        <td>
                                            <span class="activity-type">Penjualan</span>
                                            ${transaksi.kodeTransaksi} (${transaksi.jumlahItem} item)
                                        </td>
                                        <td>${transaksi.namaLengkap}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <jsp:include page="/views/templates/footer.jsp" />