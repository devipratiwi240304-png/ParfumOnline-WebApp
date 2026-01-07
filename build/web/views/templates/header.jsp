<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="id">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>
                <%= request.getParameter("title") !=null ? request.getParameter("title") : "Parfum Online" %>
            </title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        </head>

        <body>
            <header class="header">
                <nav class="navbar navbar-expand-lg navbar-dark"
                    style="background: linear-gradient(135deg, #4a6cf7 0%, #667eea 100%);">
                    <div class="container">
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">
                            <i class="fas fa-wind"></i> <strong>Parfum Online</strong>
                        </a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav me-auto">
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/dashboard') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/dashboard">
                                        <i class="fas fa-home"></i> Dashboard
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/parfum') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/parfum">
                                        <i class="fas fa-spray-can"></i> Parfum
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/karyawan') && !pageContext.request.requestURI.contains('/transaksi') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/karyawan">
                                        <i class="fas fa-users"></i> Karyawan
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/transaksi') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/karyawan/transaksi">
                                        <i class="fas fa-exchange-alt"></i> Transaksi
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/penggajian') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/penggajian">
                                        <i class="fas fa-money-bill-wave"></i> Penggajian
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link ${pageContext.request.requestURI.contains('/laporan') ? 'active' : ''}"
                                        href="${pageContext.request.contextPath}/admin/laporan">
                                        <i class="fas fa-chart-bar"></i> Laporan
                                    </a>
                                </li>
                            </ul>
                            <ul class="navbar-nav">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                        data-bs-toggle="dropdown">
                                        <i class="fas fa-user-circle"></i>
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.loggedInUser}">
                                                ${sessionScope.loggedInUser.username}
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${sessionScope.nama}" default="Admin" />
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li><a class="dropdown-item"
                                                href="${pageContext.request.contextPath}/admin/profile">
                                                <i class="fas fa-user"></i> Profile
                                            </a></li>
                                        <li>
                                            <hr class="dropdown-divider">
                                        </li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">
                                                <i class="fas fa-sign-out-alt"></i> Logout
                                            </a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>

            <div class="container main-content mt-4">
                <c:if test="${not empty sessionScope.success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i> ${sessionScope.success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="success" scope="session" />
                </c:if>

                <c:if test="${not empty sessionScope.error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-circle me-2"></i> ${sessionScope.error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="error" scope="session" />
                </c:if>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>