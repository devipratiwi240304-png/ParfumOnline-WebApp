<%-- 
    Document   : dashboard
    Created on : 3 Jan 2026, 01.31.43
    Author     : Devi Pratiwi
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/views/templates/header.jsp">
    <jsp:param name="title" value="Dashboard Karyawan"/>
</jsp:include>

<div class="dashboard">
    <h1>Dashboard Karyawan</h1>
    
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon">
                <i class="fas fa-shopping-cart"></i>
            </div>
            <div class="stat-info">
                <h3>${transaksiHariIni}</h3>
                <p>Transaksi Hari Ini</p>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">
                <i class="fas fa-money-bill-wave"></i>
            </div>
            <div class="stat-info">
                <h3>
                    <fmt:formatNumber value="${totalPenjualanHariIni}" type="currency"/>
                </h3>
                <p>Penjualan Hari Ini</p>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">
                <i class="fas fa-wind"></i>
            </div>
            <div class="stat-info">
                <h3>${parfumTerlaris}</h3>
                <p>Parfum Terlaris</p>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">
                <i class="fas fa-exclamation-triangle"></i>
            </div>
            <div class="stat-info">
                <h3>${stokKritis}</h3>
                <p>Stok Kritis</p>
            </div>
        </div>
    </div>
    
    <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/karyawan/transaksi/create" class="btn btn-primary btn-lg">
                <i class="fas fa-plus"></i> Transaksi Baru
            </a>
            <a href="${pageContext.request.contextPath}/karyawan/parfum" class="btn btn-success btn-lg">
                <i class="fas fa-wind"></i> Lihat Parfum
            </a>
        </div>
    </div>
</div>

<jsp:include page="/views/templates/footer.jsp"/>