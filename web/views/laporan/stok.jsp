<%-- 
    Document   : stok
    Created on : 3 Jan 2026, 01.29.55
    Author     : Devi Pratiwi
--%>

<<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/views/templates/header.jsp">
    <jsp:param name="title" value="Laporan Stok"/>
</jsp:include>

<h1>Laporan Stok Parfum</h1>

<div class="action-bar">
    <a href="${pageContext.request.contextPath}/admin/laporan/stok/export" 
       class="btn btn-success">
        <i class="fas fa-file-excel"></i> Export Excel
    </a>
    <a href="${pageContext.request.contextPath}/admin/laporan/stok/print" 
       class="btn btn-info" target="_blank">
        <i class="fas fa-print"></i> Cetak Laporan
    </a>
</div>

<table class="table">
    <thead>
        <tr>
            <th>No</th>
            <th>Nama Parfum</th>
            <th>Merk</th>
            <th>Jenis</th>
            <th>Volume</th>
            <th>Stok Awal</th>
            <th>Stok Masuk</th>
            <th>Stok Keluar</th>
            <th>Stok Akhir</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="stok" items="${stokList}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${stok.namaParfum}</td>
                <td>${stok.merk}</td>
                <td>${stok.jenis}</td>
                <td>${stok.volume} ml</td>
                <td>${stok.stokAwal}</td>
                <td>${stok.stokMasuk}</td>
                <td>${stok.stokKeluar}</td>
                <td>${stok.stokAkhir}</td>
                <td>
                    <c:choose>
                        <c:when test="${stok.stokAkhir <= 10}">
                            <span class="badge badge-danger">Kritis</span>
                        </c:when>
                        <c:when test="${stok.stokAkhir <= 20}">
                            <span class="badge badge-warning">Menipis</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-success">Aman</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<jsp:include page="/views/templates/footer.jsp"/>