<%-- 
    Document   : list
    Created on : 3 Jan 2026, 01.34.29
    Author     : Devi Pratiwi
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/views/templates/header.jsp">
    <jsp:param name="title" value="Daftar Transaksi"/>
</jsp:include>

<h1>Daftar Transaksi</h1>

<div class="action-bar">
    <a href="${pageContext.request.contextPath}/karyawan/transaksi/create" class="btn btn-primary">
        <i class="fas fa-plus"></i> Transaksi Baru
    </a>
</div>

<table class="table">
    <thead>
        <tr>
            <th>No. Transaksi</th>
            <th>Tanggal</th>
            <th>Jumlah Item</th>
            <th>Total Harga</th>
            <th>Pembayaran</th>
            <th>Kembalian</th>
            <th>Aksi</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="transaksi" items="${transaksiList}">
            <tr>
                <td>${transaksi.kodeTransaksi}</td>
                <td>${transaksi.tanggalTransaksi}</td>
                <td>${transaksi.jumlahItem}</td>
                <td>
                    <fmt:formatNumber value="${transaksi.totalHarga}" type="currency"/>
                </td>
                <td>
                    <fmt:formatNumber value="${transaksi.pembayaran}" type="currency"/>
                </td>
                <td>
                    <fmt:formatNumber value="${transaksi.kembalian}" type="currency"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/karyawan/transaksi/detail?id=${transaksi.id}" 
                       class="btn btn-info btn-sm">
                        <i class="fas fa-eye"></i> Detail
                    </a>
                    <a href="${pageContext.request.contextPath}/karyawan/transaksi/print?id=${transaksi.id}" 
                       class="btn btn-warning btn-sm" target="_blank">
                        <i class="fas fa-print"></i> Cetak
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<jsp:include page="/views/templates/footer.jsp"/>