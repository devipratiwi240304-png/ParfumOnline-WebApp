<%-- Document : detail Created on : 3 Jan 2026, 01.21.16 Author : Devi Pratiwi --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <jsp:include page="/views/templates/header.jsp">
                    <jsp:param name="title" value="Detail Parfum" />
                </jsp:include>

                <h1>Detail Parfum</h1>

                <div class="detail-card">
                    <div class="row">
                        <div class="col-md-4">
                            <c:if test="${not empty parfum.gambar}">
                                <img src="${pageContext.request.contextPath}/assets/image/${parfum.gambar}"
                                    alt="${parfum.nama}" class="img-fluid">
                            </c:if>
                        </div>
                        <div class="col-md-8">
                            <table class="table">
                                <tr>
                                    <th>Nama</th>
                                    <td>${parfum.nama}</td>
                                </tr>
                                <tr>
                                    <th>Merk</th>
                                    <td>${parfum.merk}</td>
                                </tr>
                                <tr>
                                    <th>Jenis</th>
                                    <td>${parfum.jenis}</td>
                                </tr>
                                <tr>
                                    <th>Volume</th>
                                    <td>${parfum.volumeMl} ml</td>
                                </tr>
                                <tr>
                                    <th>Harga</th>
                                    <td>
                                        <fmt:formatNumber value="${parfum.harga}" type="currency" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>Stok</th>
                                    <td>${parfum.stok}</td>
                                </tr>
                                <tr>
                                    <th>Deskripsi</th>
                                    <td>${parfum.deskripsi}</td>
                                </tr>
                            </table>

                            <a href="${pageContext.request.contextPath}/admin/parfum"
                                class="btn btn-secondary">Kembali</a>
                        </div>
                    </div>
                </div>

                <jsp:include page="/views/templates/footer.jsp" />