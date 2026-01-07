<%-- Document : list Created on : 3 Jan 2026, 01.25.16 Author : Devi Pratiwi --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <jsp:include page="/views/templates/header.jsp">
                    <jsp:param name="title" value="Daftar Penggajian" />
                </jsp:include>

                <h1>Daftar Penggajian</h1>

                <div class="action-bar">
                    <a href="${pageContext.request.contextPath}/admin/penggajian/create" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Tambah Penggajian
                    </a>
                </div>

                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Karyawan</th>
                            <th>Periode</th>
                            <th>Gaji Pokok</th>
                            <th>Tunjangan</th>
                            <th>Potongan</th>
                            <th>Total Gaji</th>
                            <th>Status</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="penggajian" items="${penggajianList}">
                            <tr>
                                <td>${penggajian.id}</td>
                                <td>${penggajian.karyawan.namaLengkap}</td>
                                <td>${penggajian.bulan}/${penggajian.tahun}</td>
                                <td>
                                    <fmt:formatNumber value="${penggajian.gajiPokok}" type="currency" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${penggajian.tunjangan}" type="currency" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${penggajian.potongan}" type="currency" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${penggajian.totalGaji}" type="currency" />
                                </td>
                                <td>
                                    <span class="badge ${penggajian.status == 'dibayar' ? 'bg-success' : 'bg-warning'}">
                                        ${penggajian.status}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/penggajian/edit?id=${penggajian.id}"
                                        class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/penggajian/delete?id=${penggajian.id}"
                                        class="btn btn-danger btn-sm"
                                        onclick="return confirm('Yakin ingin menghapus data penggajian ini?')">
                                        <i class="fas fa-trash"></i> Hapus
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/penggajian/print?id=${penggajian.id}"
                                        class="btn btn-info btn-sm" target="_blank">
                                        <i class="fas fa-print"></i> Slip
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <jsp:include page="/views/templates/footer.jsp" />