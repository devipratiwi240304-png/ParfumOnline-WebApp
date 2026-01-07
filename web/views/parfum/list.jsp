<%-- Document : list Created on : 3 Jan 2026, 01.18.06 Author : Devi Pratiwi --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <jsp:include page="/views/templates/header.jsp">
                    <jsp:param name="title" value="Daftar Parfum" />
                </jsp:include>

                <h1>Daftar Parfum</h1>

                <div class="action-bar">
                    <a href="${pageContext.request.contextPath}/parfum/new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Tambah Parfum
                    </a>
                </div>

                <c:if test="${not empty message}">
                    <div class="alert alert-success">
                        ${message}
                    </div>
                </c:if>

                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Gambar</th>
                            <th>Nama</th>
                            <th>Merk</th>
                            <th>Kategori</th>
                            <th>Volume</th>
                            <th>Harga</th>
                            <th>Stok</th>
                            <th>Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="parfum" items="${parfumList}">
                            <tr>
                                <td>${parfum.id}</td>
                                <td>
                                    <c:if test="${not empty parfum.gambar}">
                                        <img src="${pageContext.request.contextPath}/assets/image/${parfum.gambar}"
                                            alt="${parfum.nama}" width="50" height="50">
                                    </c:if>
                                </td>
                                <td>${parfum.nama}</td>
                                <td>${parfum.merk}</td>
                                <td>${parfum.kategori}</td>
                                <td>${parfum.volumeMl} ml</td>
                                <td>
                                    <fmt:formatNumber value="${parfum.harga}" type="currency" />
                                </td>
                                <td>${parfum.stok}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/parfum/edit?id=${parfum.id}"
                                        class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/parfum/delete?id=${parfum.id}"
                                        class="btn btn-danger btn-sm"
                                        onclick="return confirm('Yakin ingin menghapus parfum ini?')">
                                        <i class="fas fa-trash"></i> Hapus
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <jsp:include page="/views/templates/footer.jsp" />