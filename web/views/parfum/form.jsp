<%-- 
    Document   : form
    Created on : 3 Jan 2026, 01.19.57
    Author     : Devi Pratiwi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="/views/templates/header.jsp">
    <jsp:param name="title" value="${param.id != null ? 'Edit Parfum' : 'Tambah Parfum'}"/>
</jsp:include>

<h1>${param.id != null ? 'Edit Parfum' : 'Tambah Parfum'}</h1>

<form action="${pageContext.request.contextPath}/admin/parfum/save" method="post" enctype="multipart/form-data">
    <c:if test="${param.id != null}">
        <input type="hidden" name="id" value="${param.id}">
    </c:if>
    
    <div class="form-group">
        <label for="nama">Nama Parfum</label>
        <input type="text" id="nama" name="nama" value="${parfum.nama}" class="form-control" required>
    </div>
    
    <div class="form-group">
        <label for="merk">Merk</label>
        <input type="text" id="merk" name="merk" value="${parfum.merk}" class="form-control" required>
    </div>
    
    <div class="form-group">
        <label for="jenis">Jenis</label>
        <select id="jenis" name="jenis" class="form-control" required>
            <option value="">Pilih Jenis</option>
            <option value="EAU DE TOILETTE" ${parfum.jenis == 'EAU DE TOILETTE' ? 'selected' : ''}>Eau de Toilette</option>
            <option value="EAU DE PARFUM" ${parfum.jenis == 'EAU DE PARFUM' ? 'selected' : ''}>Eau de Parfum</option>
            <option value="EAU DE COLOGNE" ${parfum.jenis == 'EAU DE COLOGNE' ? 'selected' : ''}>Eau de Cologne</option>
            <option value="PERFUME" ${parfum.jenis == 'PERFUME' ? 'selected' : ''}>Perfume</option>
        </select>
    </div>
    
    <div class="form-group">
        <label for="volume">Volume (ml)</label>
        <input type="number" id="volume" name="volume" value="${parfum.volume}" class="form-control" required>
    </div>
    
    <div class="form-group">
        <label for="harga">Harga</label>
        <input type="number" id="harga" name="harga" value="${parfum.harga}" class="form-control" required>
    </div>
    
    <div class="form-group">
        <label for="stok">Stok</label>
        <input type="number" id="stok" name="stok" value="${parfum.stok}" class="form-control" required>
    </div>
    
    <div class="form-group">
        <label for="deskripsi">Deskripsi</label>
        <textarea id="deskripsi" name="deskripsi" class="form-control" rows="4">${parfum.deskripsi}</textarea>
    </div>
    
    <div class="form-group">
        <label for="gambar">Gambar</label>
        <input type="file" id="gambar" name="gambar" class="form-control">
        <c:if test="${not empty parfum.gambar}">
            <img src="${pageContext.request.contextPath}/assets/image/${parfum.gambar}" 
                 alt="${parfum.nama}" width="100" height="100" class="mt-2">
        </c:if>
    </div>
    <button type="submit" class="btn btn-primary">Simpan</button>
    <a href="${pageContext.request.contextPath}/admin/parfum" class="btn btn-secondary">Kembali</a>
</form>

<jsp:include page="/views/templates/footer.jsp"/>