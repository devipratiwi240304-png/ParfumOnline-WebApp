<%-- Document : list Created on : 3 Jan 2026 Author : Devi Pratiwi --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <jsp:include page="/views/templates/header.jsp">
                    <jsp:param name="title" value="Daftar Karyawan" />
                </jsp:include>

                <h1>Daftar Karyawan</h1>

                <div class="action-bar">
                    <a href="${pageContext.request.contextPath}/karyawan/new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Tambah Karyawan
                    </a>
                </div>

                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>NIK</th>
                            <th>Nama</th>
                            <th>Jabatan</th>
                            <th>Gaji Pokok</th>
                            <th>Tunjangan</th>
                            <th>Bank</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="karyawan" items="${karyawanList}">
                            <tr>
                                <td>${karyawan.id}</td>
                                <td>${karyawan.nik}</td>
                                <td>${karyawan.namaLengkap}</td>
                                <td>${karyawan.jabatan}</td>
                                <td>
                                    <fmt:formatNumber value="${karyawan.gajiPokok}" type="currency"
                                        currencyCode="IDR" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${karyawan.tunjangan}" type="currency"
                                        currencyCode="IDR" />
                                </td>
                                <td>${karyawan.bank} - ${karyawan.nomorRekening}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/karyawan/edit?id=${karyawan.id}"
                                        class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/karyawan/delete?id=${karyawan.id}"
                                        class="btn btn-danger btn-sm"
                                        onclick="return confirm('Yakin ingin menghapus karyawan ini?')">
                                        <i class="fas fa-trash"></i> Hapus
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <jsp:include page="/views/templates/footer.jsp" />