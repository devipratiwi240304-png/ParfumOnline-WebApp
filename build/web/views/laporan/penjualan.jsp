<%-- 
    Document   : penjualan
    Created on : 3 Jan 2026, 01.29.09
    Author     : Devi Pratiwi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="/views/templates/header.jsp">
    <jsp:param name="title" value="Laporan Penjualan"/>
</jsp:include>

<h1>Laporan Penjualan</h1>

<form action="${pageContext.request.contextPath}/admin/laporan/penjualan" method="get" class="filter-form">
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label for="bulan">Bulan</label>
                <select id="bulan" name="bulan" class="form-control">
                    <option value="">Semua Bulan</option>
                    <option value="01" ${param.bulan == '01' ? 'selected' : ''}>Januari</option>
                    <option value="02" ${param.bulan == '02' ? 'selected' : ''}>Februari</option>
                    <option value="03" ${param.bulan == '03' ? 'selected' : ''}>Maret</option>
                    <option value="04" ${param.bulan == '04' ? 'selected' : ''}>April</option>
                    <option value="05" ${param.bulan == '05' ? 'selected' : ''}>Mei</option>
                    <option value="06" ${param.bulan == '06' ? 'selected' : ''}>Juni</option>
                    <option value="07" ${param.bulan == '07' ? 'selected' : ''}>Juli</option>
                    <option value="08" ${param.bulan == '08' ? 'selected' : ''}>Agustus</option>
                    <option value="09" ${param.bulan == '09' ? 'selected' : ''}>September</option>
                    <option value="10" ${param.bulan == '10' ? 'selected' : ''}>Oktober</option>
                    <option value="11" ${param.bulan == '11' ? 'selected' : ''}>November</option>
                    <option value="12" ${param.bulan == '12' ? 'selected' : ''}>Desember</option>
                </select>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label for="tahun">Tahun</label>
                <input type="number" id="tahun" name="tahun" value="${param.tahun}" 
                       class="form-control" min="2020" max="2030">
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label for="produk">Produk</label>
                <select id="produk" name="produk" class="form-control">
                    <option value="">Semua Produk</option>
                    <c:forEach var="parfum" items="${parfumList}">
                        <option value="${parfum.id}" ${param.produk == parfum.id ? 'selected' : ''}>
                            ${parfum.nama}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-md-2">
            <div class="form-group">
                <label>&nbsp;</label>
                <button type="submit" class="btn btn-primary btn-block">Filter</button>
            </div>
        </div>
    </div>
</form>

<div class="action-bar">
    <a href="${pageContext.request.contextPath}/admin/laporan/penjualan/export?bulan=${param.bulan}&tahun=${param.tahun}&produk=${param.produk}" 
       class="btn btn-success">
        <i class="fas fa-file-excel"></i> Export Excel
    </a>
    <a href="${pageContext.request.contextPath}/admin/laporan/penjualan/print?bulan=${param.bulan}&tahun=${param.tahun}&produk=${param.produk}" 
       class="btn btn-info" target="_blank">
        <i class="fas fa-print"></i> Cetak Laporan
    </a>
</div>

<table class="table">
    <thead>
        <tr>
            <th>No</th>
            <th>Tanggal</th>
            <th>No. Transaksi</th>
            <th>Produk</th>
            <th>Jumlah</th>
            <th>Harga</th>
            <th>Subtotal</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="penjualan" items="${penjualanList}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${penjualan.tanggal}</td>
                <td>${penjualan.noTransaksi}</td>
                <td>${penjualan.produkNama}</td>
                <td>${penjualan.jumlah}</td>
                <td>
                    <fmt:formatNumber value="${penjualan.harga}" type="currency"/>
                </td>
                <td>
                    <fmt:formatNumber value="${penjualan.subtotal}" type="currency"/>
                </td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <th colspan="6" class="text-right">Total Penjualan:</th>
            <th>
                <fmt:formatNumber value="${totalPenjualan}" type="currency"/>
            </th>
        </tr>
    </tfoot>
</table>

<jsp:include page="/views/templates/footer.jsp"/>
