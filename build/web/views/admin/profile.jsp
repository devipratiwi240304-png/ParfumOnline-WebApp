<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="Profil Admin" />
            </jsp:include>

            <div class="container-fluid py-4">
                <div class="row">
                    <div class="col-md-4">
                        <!-- Profile Card -->
                        <div class="card shadow mb-4">
                            <div class="card-body text-center">
                                <div class="mb-3">
                                    <i class="fas fa-user-circle fa-7x text-primary border rounded-circle p-2"></i>
                                </div>
                                <h4 class="card-title">${sessionScope.user.namaLengkap}</h4>
                                <p class="text-muted mb-1">${sessionScope.user.role.toUpperCase()}</p>
                                <p class="text-muted small">Bergabung sejak:
                                    <fmt:formatDate value="${sessionScope.user.tanggalBergabung}"
                                        pattern="dd MMMM yyyy" />
                                </p>
                                <div class="badge bg-success">${sessionScope.user.status}</div>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                    <span>Username</span>
                                    <span class="fw-bold">${sessionScope.user.username}</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                    <span>Email</span>
                                    <span class="fw-bold">${sessionScope.user.email}</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="col-md-8">
                        <!-- Settings Card -->
                        <div class="card shadow">
                            <div class="card-header bg-primary text-white">
                                <h5 class="mb-0"><i class="fas fa-cog me-2"></i> Pengaturan Akun</h5>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/admin/profile" method="POST">
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <label for="username" class="form-label">Username</label>
                                            <input type="text" class="form-control" id="username"
                                                value="${sessionScope.user.username}" readonly>
                                            <div class="form-text">Username tidak dapat diubah.</div>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="email" class="form-label">Email</label>
                                            <input type="email" class="form-control" name="email" id="email"
                                                value="${sessionScope.user.email}" required>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="nama_lengkap" class="form-label">Nama Lengkap</label>
                                        <input type="text" class="form-control" name="nama_lengkap" id="nama_lengkap"
                                            value="${sessionScope.user.namaLengkap}" required>
                                    </div>

                                    <hr class="my-4">
                                    <h6 class="mb-3">Ganti Password (Biarkan kosong jika tidak ingin mengubah)</h6>

                                    <div class="row mb-4">
                                        <div class="col-md-12">
                                            <label for="password" class="form-label">Password Baru</label>
                                            <div class="input-group">
                                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                                <input type="password" class="form-control" name="password"
                                                    id="password" placeholder="Masukkan password baru">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="text-end">
                                        <button type="reset" class="btn btn-light me-2">Batal</button>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-save me-1"></i> Simpan Perubahan
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/views/templates/footer.jsp" />