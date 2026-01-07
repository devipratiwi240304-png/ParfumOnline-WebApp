<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>${parfum != null ? 'Edit Parfum' : 'Tambah Parfum'} - Parfum Online</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    .container {
                        max-width: 900px;
                        margin: 50px auto;
                        padding: 30px;
                        background: white;
                        border-radius: 10px;
                        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                    }

                    .form-title {
                        text-align: center;
                        margin-bottom: 30px;
                        color: #333;
                        border-bottom: 2px solid #4a6cf7;
                        padding-bottom: 15px;
                    }

                    .form-group {
                        margin-bottom: 20px;
                    }

                    .form-label {
                        font-weight: 600;
                        color: #555;
                        margin-bottom: 8px;
                        display: block;
                    }

                    .form-control {
                        border-radius: 5px;
                        border: 1px solid #ddd;
                        padding: 12px;
                        width: 100%;
                        transition: all 0.3s;
                    }

                    .form-control:focus {
                        border-color: #4a6cf7;
                        box-shadow: 0 0 0 3px rgba(74, 108, 247, 0.1);
                        outline: none;
                    }

                    .btn-primary {
                        background: #4a6cf7;
                        border: none;
                        padding: 12px 30px;
                        border-radius: 5px;
                        color: white;
                        font-weight: 600;
                        cursor: pointer;
                        transition: all 0.3s;
                    }

                    .btn-primary:hover {
                        background: #3a5ce5;
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(74, 108, 247, 0.3);
                    }

                    .btn-secondary {
                        background: #6c757d;
                        border: none;
                        padding: 12px 30px;
                        border-radius: 5px;
                        color: white;
                        font-weight: 600;
                        cursor: pointer;
                        transition: all 0.3s;
                        text-decoration: none;
                        display: inline-block;
                    }

                    .btn-secondary:hover {
                        background: #5a6268;
                        text-decoration: none;
                    }

                    .btn-group {
                        display: flex;
                        gap: 10px;
                        margin-top: 30px;
                    }

                    .alert {
                        padding: 15px;
                        border-radius: 5px;
                        margin-bottom: 20px;
                    }

                    .alert-danger {
                        background-color: #f8d7da;
                        color: #721c24;
                        border: 1px solid #f5c6cb;
                    }

                    .alert-success {
                        background-color: #d4edda;
                        color: #155724;
                        border: 1px solid #c3e6cb;
                    }

                    .image-preview {
                        margin-top: 10px;
                        display: flex;
                        gap: 15px;
                        align-items: center;
                    }

                    .image-preview img {
                        border: 2px solid #ddd;
                        border-radius: 5px;
                        padding: 5px;
                        background: #f8f9fa;
                    }

                    .image-preview .current-image {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                    }

                    .image-preview .current-image label {
                        font-size: 12px;
                        color: #666;
                        margin-top: 5px;
                    }

                    .form-note {
                        font-size: 12px;
                        color: #666;
                        margin-top: 5px;
                    }

                    .required::after {
                        content: " *";
                        color: red;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <!-- Header -->
                    <jsp:include page="/views/templates/header.jsp">
                        <jsp:param name="title" value="${parfum != null ? 'Edit Parfum' : 'Tambah Parfum'}" />
                    </jsp:include>

                    <!-- Title -->
                    <h1 class="form-title">
                        <i class="fas ${parfum != null ? 'fa-edit' : 'fa-plus'}"></i>
                        ${parfum != null ? 'Edit Parfum' : 'Tambah Parfum Baru'}
                    </h1>

                    <!-- Error Messages -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${sessionScope.error}
                        </div>
                        <% session.removeAttribute("error"); %>
                    </c:if>

                    <!-- Success Messages -->
                    <c:if test="${not empty sessionScope.success}">
                        <div class="alert alert-success">
                            <i class="fas fa-check-circle"></i> ${sessionScope.success}
                        </div>
                        <% session.removeAttribute("success"); %>
                    </c:if>

                    <!-- Form -->
                    <form
                        action="${pageContext.request.contextPath}${parfum != null ? '/admin/parfum/update' : '/admin/parfum'}"
                        method="post" enctype="multipart/form-data" id="parfumForm">

                        <!-- Hidden ID for edit -->
                        <c:if test="${parfum != null}">
                            <input type="hidden" name="id" value="${parfum.id}">
                        </c:if>

                        <div class="row">
                            <!-- Column 1 -->
                            <div class="col-md-6">
                                <!-- Nama Parfum -->
                                <div class="form-group">
                                    <label for="nama" class="form-label required">Nama Parfum</label>
                                    <input type="text" id="nama" name="nama"
                                        value="${not empty parfum.nama ? parfum.nama : param.nama}" class="form-control"
                                        required placeholder="Masukkan nama parfum" maxlength="100">
                                </div>

                                <!-- Merk -->
                                <div class="form-group">
                                    <label for="merk" class="form-label required">Merk</label>
                                    <input type="text" id="merk" name="merk"
                                        value="${not empty parfum.merk ? parfum.merk : param.merk}" class="form-control"
                                        required placeholder="Contoh: Chanel, Dior, Versace" maxlength="50">
                                </div>

                                <!-- Jenis -->
                                <div class="form-group">
                                    <label for="jenis" class="form-label required">Jenis</label>
                                    <select id="jenis" name="jenis" class="form-control" required>
                                        <option value="">-- Pilih Jenis Parfum --</option>
                                        <option value="EAU DE TOILETTE" ${(parfum.jenis=='EAU DE TOILETTE' ||
                                            param.jenis=='EAU DE TOILETTE' ) ? 'selected' : '' }>
                                            Eau de Toilette
                                        </option>
                                        <option value="EAU DE PARFUM" ${(parfum.jenis=='EAU DE PARFUM' ||
                                            param.jenis=='EAU DE PARFUM' ) ? 'selected' : '' }>
                                            Eau de Parfum
                                        </option>
                                        <option value="EAU DE COLOGNE" ${(parfum.jenis=='EAU DE COLOGNE' ||
                                            param.jenis=='EAU DE COLOGNE' ) ? 'selected' : '' }>
                                            Eau de Cologne
                                        </option>
                                        <option value="PERFUME" ${(parfum.jenis=='PERFUME' || param.jenis=='PERFUME' )
                                            ? 'selected' : '' }>
                                            Perfume (Extrait de Parfum)
                                        </option>
                                        <option value="BODY MIST" ${(parfum.jenis=='BODY MIST' ||
                                            param.jenis=='BODY MIST' ) ? 'selected' : '' }>
                                            Body Mist
                                        </option>
                                    </select>
                                    <div class="form-note">
                                        <small>
                                            <strong>Keterangan:</strong><br>
                                            • Eau de Toilette: Konsentrasi 5-15% (tahan 3-4 jam)<br>
                                            • Eau de Parfum: Konsentrasi 15-20% (tahan 5-8 jam)<br>
                                            • Perfume: Konsentrasi 20-30% (tahan 8-12 jam)
                                        </small>
                                    </div>
                                </div>

                                <!-- Volume -->
                                <div class="form-group">
                                    <label for="volume" class="form-label required">Volume (ml)</label>
                                    <input type="number" id="volume" name="volume"
                                        value="${not empty parfum.volume ? parfum.volume : param.volume}"
                                        class="form-control" required min="1" max="1000" placeholder="Contoh: 100">
                                    <div class="form-note">
                                        <small>Volume umum: 30ml, 50ml, 100ml</small>
                                    </div>
                                </div>
                            </div>

                            <!-- Column 2 -->
                            <div class="col-md-6">
                                <!-- Harga -->
                                <div class="form-group">
                                    <label for="harga" class="form-label required">Harga (Rp)</label>
                                    <input type="number" id="harga" name="harga"
                                        value="${not empty parfum.harga ? parfum.harga : param.harga}"
                                        class="form-control" required min="1000" step="1000"
                                        placeholder="Contoh: 1500000">
                                    <div class="form-note">
                                        <small>Harga tanpa titik atau koma</small>
                                    </div>
                                </div>

                                <!-- Stok -->
                                <div class="form-group">
                                    <label for="stok" class="form-label required">Stok</label>
                                    <input type="number" id="stok" name="stok"
                                        value="${not empty parfum.stok ? parfum.stok : param.stok}" class="form-control"
                                        required min="0" placeholder="Jumlah stok tersedia">
                                </div>

                                <!-- Deskripsi -->
                                <div class="form-group">
                                    <label for="deskripsi" class="form-label">Deskripsi</label>
                                    <textarea id="deskripsi" name="deskripsi" class="form-control" rows="4"
                                        maxlength="500"
                                        placeholder="Deskripsi parfum (aroma, notes, karakteristik)">${not empty parfum.deskripsi ? parfum.deskripsi : param.deskripsi}</textarea>
                                    <div class="form-note">
                                        <small id="charCount">0/500 karakter</small>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Gambar Upload -->
                        <div class="form-group">
                            <label for="gambar" class="form-label">Gambar Parfum</label>

                            <!-- File Upload -->
                            <input type="file" id="gambar" name="gambar" class="form-control" accept="image/*"
                                onchange="previewImage(this)">

                            <div class="form-note">
                                <small>Format: JPG, PNG, GIF. Maksimal 2MB. Ukuran disarankan: 500x500px</small>
                            </div>

                            <!-- Image Preview Area -->
                            <div id="imagePreview" class="image-preview mt-3">
                                <c:if test="${not empty parfum.gambar}">
                                    <div class="current-image">
                                        <img src="${pageContext.request.contextPath}/assets/image/${parfum.gambar}"
                                            alt="Gambar saat ini" width="150" height="150" id="currentImage">
                                        <label>Gambar Saat Ini</label>
                                        <input type="hidden" name="existingImage" value="${parfum.gambar}">
                                    </div>
                                </c:if>
                                <div id="newImagePreview" style="display: none;">
                                    <img id="preview" alt="Preview Gambar Baru" width="150" height="150">
                                    <label>Preview Gambar Baru</label>
                                </div>
                            </div>
                        </div>

                        <!-- Action Buttons -->
                        <div class="btn-group">
                            <button type="submit" class="btn-primary">
                                <i class="fas fa-save"></i> ${parfum != null ? 'Update Parfum' : 'Simpan Parfum'}
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/parfum" class="btn-secondary">
                                <i class="fas fa-arrow-left"></i> Kembali ke Daftar
                            </a>
                            <button type="reset" class="btn-secondary" onclick="resetForm()">
                                <i class="fas fa-redo"></i> Reset Form
                            </button>
                        </div>
                    </form>
                </div>

                <!-- JavaScript -->
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    // Character counter for description
                    document.getElementById('deskripsi').addEventListener('input', function () {
                        const charCount = this.value.length;
                        document.getElementById('charCount').textContent = charCount + '/500 karakter';
                    });

                    // Initialize character count on page load
                    document.addEventListener('DOMContentLoaded', function () {
                        const descTextarea = document.getElementById('deskripsi');
                        if (descTextarea) {
                            const charCount = descTextarea.value.length;
                            document.getElementById('charCount').textContent = charCount + '/500 karakter';
                        }
                    });

                    // Image preview function
                    function previewImage(input) {
                        const preview = document.getElementById('preview');
                        const newImagePreview = document.getElementById('newImagePreview');
                        const currentImage = document.getElementById('currentImage');

                        if (input.files && input.files[0]) {
                            const file = input.files[0];

                            // Validate file size (max 2MB)
                            if (file.size > 2 * 1024 * 1024) {
                                alert('Ukuran file maksimal 2MB');
                                input.value = '';
                                return;
                            }

                            // Validate file type
                            const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
                            if (!validTypes.includes(file.type)) {
                                alert('Format file harus JPG, PNG, atau GIF');
                                input.value = '';
                                return;
                            }

                            const reader = new FileReader();

                            reader.onload = function (e) {
                                preview.src = e.target.result;
                                newImagePreview.style.display = 'block';

                                // Hide current image if exists
                                if (currentImage) {
                                    currentImage.style.opacity = '0.5';
                                }
                            }

                            reader.readAsDataURL(file);
                        } else {
                            newImagePreview.style.display = 'none';
                            if (currentImage) {
                                currentImage.style.opacity = '1';
                            }
                        }
                    }

                    // Form validation
                    document.getElementById('parfumForm').addEventListener('submit', function (e) {
                        const harga = document.getElementById('harga').value;
                        const stok = document.getElementById('stok').value;
                        const volume = document.getElementById('volume').value;

                        if (parseInt(harga) < 1000) {
                            alert('Harga minimal Rp 1.000');
                            e.preventDefault();
                            return;
                        }

                        if (parseInt(stok) < 0) {
                            alert('Stok tidak boleh negatif');
                            e.preventDefault();
                            return;
                        }

                        if (parseInt(volume) < 1) {
                            alert('Volume minimal 1ml');
                            e.preventDefault();
                            return;
                        }
                    });

                    // Reset form function
                    function resetForm() {
                        document.getElementById('parfumForm').reset();
                        document.getElementById('newImagePreview').style.display = 'none';
                        const currentImage = document.getElementById('currentImage');
                        if (currentImage) {
                            currentImage.style.opacity = '1';
                        }
                        document.getElementById('charCount').textContent = '0/500 karakter';
                    }

                    // Auto format price input
                    document.getElementById('harga').addEventListener('input', function (e) {
                        let value = this.value.replace(/[^\d]/g, '');
                        if (value) {
                            value = parseInt(value, 10);
                            this.value = value;
                        }
                    });

                    // Auto-hide alerts after 5 seconds
                    setTimeout(function () {
                        const alerts = document.querySelectorAll('.alert');
                        alerts.forEach(function (alert) {
                            alert.style.transition = 'opacity 0.5s';
                            alert.style.opacity = '0';
                            setTimeout(() => alert.remove(), 500);
                        });
                    }, 5000);
                </script>

                <!-- Footer -->
                <jsp:include page="/views/templates/footer.jsp" />
            </body>

            </html>