<%-- 
    Document   : sidebar
    Created on : 3 Jan 2026, 01.08.29
    Author     : Devi Pratiwi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="sidebar">
    <div class="sidebar-header">
        <h3>Menu</h3>
    </div>
    <ul class="sidebar-menu">
        <c:if test="${sessionScope.userRole == 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-tachometer-alt"></i> Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/parfum"><i class="fas fa-wind"></i> Parfum</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/karyawan"><i class="fas fa-users"></i> Karyawan</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/penggajian"><i class="fas fa-money-bill-wave"></i> Penggajian</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/laporan"><i class="fas fa-chart-bar"></i> Laporan</a></li>
        </c:if>
        <c:if test="${sessionScope.userRole == 'KARYAWAN'}">
            <li><a href="${pageContext.request.contextPath}/karyawan/dashboard"><i class="fas fa-tachometer-alt"></i> Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/karyawan/transaksi"><i class="fas fa-shopping-cart"></i> Transaksi</a></li>
            <li><a href="${pageContext.request.contextPath}/karyawan/parfum"><i class="fas fa-wind"></i> Parfum</a></li>
        </c:if>
    </ul>
</div>
