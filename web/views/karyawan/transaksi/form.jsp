<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="Transaksi Baru" />
            </jsp:include>

            <h1>Transaksi Baru</h1>

            <div class="row">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            <h3>Pilih Parfum</h3>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <input type="text" id="searchParfum" class="form-control" placeholder="Cari parfum...">
                            </div>

                            <div class="parfum-grid">
                                <c:forEach var="parfum" items="${parfumList}">
                                    <div class="parfum-card" data-id="${parfum.id}" data-nama="${parfum.nama}"
                                        data-harga="${parfum.harga}" data-stok="${parfum.stok}">
                                        <div class="parfum-image">
                                            <c:if test="${not empty parfum.gambar}">
                                                <img src="${pageContext.request.contextPath}/assets/image/${parfum.gambar}"
                                                    alt="${parfum.nama}">
                                            </c:if>
                                        </div>
                                        <div class="parfum-info">
                                            <h4>${parfum.nama}</h4>
                                            <p>${parfum.merk} - ${parfum.jenis}</p>
                                            <p>
                                                <strong>
                                                    <fmt:formatNumber value="${parfum.harga}" type="currency" />
                                                </strong>
                                            </p>
                                            <p>Stok: ${parfum.stok}</p>
                                            <button type="button" class="btn btn-primary btn-sm btn-add-to-cart">
                                                <i class="fas fa-cart-plus"></i> Tambah
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <h3>Keranjang Belanja</h3>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/karyawan/transaksi/save" method="post"
                                id="transaksiForm">
                                <table class="table" id="cartTable">
                                    <thead>
                                        <tr>
                                            <th>Parfum</th>
                                            <th>Qty</th>
                                            <th>Harga</th>
                                            <th>Subtotal</th>
                                            <th>Aksi</th>
                                        </tr>
                                    </thead>
                                    <tbody id="cartItems">
                                        <!-- Items akan ditambahkan via JavaScript -->
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th colspan="3">Total</th>
                                            <th id="totalAmount">0</th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
                                </table>

                                <div class="form-group mb-3">
                                    <label for="metode_pembayaran">Metode Pembayaran</label>
                                    <select id="metode_pembayaran" name="metode_pembayaran" class="form-control"
                                        required>
                                        <option value="tunai">Tunai (Cash)</option>
                                        <option value="Transfer BCA">Transfer Bank BCA</option>
                                        <option value="Transfer BRI">Transfer Bank BRI</option>
                                        <option value="Transfer CIMB Niaga">Transfer Bank CIMB Niaga</option>
                                    </select>
                                </div>

                                <div id="bank_info" class="alert alert-info py-2 mb-3" style="display: none;">
                                    <small>
                                        <i class="fas fa-university"></i> No. Rekening: <br>
                                        <strong>24618261861</strong> <br>
                                        a/n Parfum Online
                                    </small>
                                </div>

                                <div class="form-group mb-3" id="pembayaran_group">
                                    <label for="pembayaran">Pembayaran</label>
                                    <input type="number" id="pembayaran" name="pembayaran" class="form-control"
                                        required>
                                </div>

                                <div class="form-group">
                                    <label for="kembalian">Kembalian</label>
                                    <input type="text" id="kembalian" name="kembalian" class="form-control" readonly>
                                </div>

                                <button type="submit" class="btn btn-success btn-block">
                                    <i class="fas fa-check"></i> Simpan Transaksi
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                // JavaScript untuk mengelola keranjang belanja
                let cart = [];

                document.querySelectorAll('.btn-add-to-cart').forEach(button => {
                    button.addEventListener('click', function () {
                        const card = this.closest('.parfum-card');
                        const id = card.dataset.id;
                        const nama = card.dataset.nama;
                        const harga = parseFloat(card.dataset.harga);
                        const stok = parseInt(card.dataset.stok);

                        // Cek jika item sudah ada di keranjang
                        const existingItem = cart.find(item => item.id === id);

                        if (existingItem) {
                            if (existingItem.quantity < stok) {
                                existingItem.quantity++;
                            } else {
                                alert('Stok tidak mencukupi!');
                            }
                        } else {
                            cart.push({
                                id: id,
                                nama: nama,
                                harga: harga,
                                quantity: 1,
                                stok: stok
                            });
                        }

                        updateCart();
                    });
                });

                function updateCart() {
                    const cartItems = document.getElementById('cartItems');
                    cartItems.innerHTML = '';
                    let total = 0;

                    cart.forEach((item, index) => {
                        const subtotal = item.harga * item.quantity;
                        total += subtotal;

                        const row = document.createElement('tr');
                        row.innerHTML = '<td>' + item.nama + '</td>' +
                            '<td><input type="number" class="form-control form-control-sm quantity-input" value="' + item.quantity + '" min="1" max="' + item.stok + '" data-index="' + index + '"></td>' +
                            '<td>' + formatCurrency(item.harga) + '</td>' +
                            '<td>' + formatCurrency(subtotal) + '</td>' +
                            '<td><button type="button" class="btn btn-danger btn-sm remove-item" data-index="' + index + '"><i class="fas fa-trash"></i></button></td>';
                        cartItems.appendChild(row);
                    });

                    document.getElementById('totalAmount').textContent = formatCurrency(total);
                    document.getElementById('totalAmount').dataset.value = total; // Simpan nilai asli

                    // Update event listeners
                    document.querySelectorAll('.quantity-input').forEach(input => {
                        input.addEventListener('change', function () {
                            const index = this.dataset.index;
                            const newQuantity = parseInt(this.value);

                            if (newQuantity > cart[index].stok) {
                                alert('Stok tidak mencukupi!');
                                this.value = cart[index].stok;
                                cart[index].quantity = cart[index].stok;
                            } else if (newQuantity < 1) {
                                cart.splice(index, 1);
                            } else {
                                cart[index].quantity = newQuantity;
                            }

                            updateCart();
                        });
                    });

                    document.querySelectorAll('.remove-item').forEach(button => {
                        button.addEventListener('click', function () {
                            const index = this.dataset.index;
                            cart.splice(index, 1);
                            updateCart();
                        });
                    });

                    // Update kembalian
                    updateKembalian();
                }

                function updateKembalian() {
                    const metode = document.getElementById('metode_pembayaran').value;
                    let total = 0;
                    cart.forEach(item => {
                        total += (item.harga * item.quantity);
                    });

                    const pembayaranInput = document.getElementById('pembayaran');
                    const kembalianInput = document.getElementById('kembalian');
                    const bankInfo = document.getElementById('bank_info');

                    if (metode !== 'tunai') {
                        pembayaranInput.value = total;
                        pembayaranInput.readOnly = true;
                        kembalianInput.value = formatCurrency(0);
                        bankInfo.style.display = 'block';
                    } else {
                        pembayaranInput.readOnly = false;
                        bankInfo.style.display = 'none';
                        const pembayaran = parseFloat(pembayaranInput.value) || 0;

                        if (pembayaran > 0) {
                            const kembalian = pembayaran - total;
                            if (kembalian >= 0) {
                                kembalianInput.value = formatCurrency(kembalian);
                                kembalianInput.style.color = 'green';
                            } else {
                                kembalianInput.value = 'Kurang ' + formatCurrency(Math.abs(kembalian));
                                kembalianInput.style.color = 'red';
                            }
                        } else {
                            kembalianInput.value = formatCurrency(0);
                            kembalianInput.style.color = 'inherit';
                        }
                    }
                }

                // Multiple event listeners for maximum responsiveness
                ['input', 'change', 'keyup'].forEach(evt => {
                    document.getElementById('metode_pembayaran').addEventListener(evt, updateKembalian);
                    document.getElementById('pembayaran').addEventListener(evt, updateKembalian);
                });

                // Format currency
                function formatCurrency(amount) {
                    return new Intl.NumberFormat('id-ID', {
                        style: 'currency',
                        currency: 'IDR',
                        minimumFractionDigits: 0
                    }).format(amount);
                }

                // Search functionality
                document.getElementById('searchParfum').addEventListener('input', function () {
                    const searchTerm = this.value.toLowerCase();
                    document.querySelectorAll('.parfum-card').forEach(card => {
                        const nama = card.dataset.nama.toLowerCase();
                        if (nama.includes(searchTerm)) {
                            card.style.display = 'block';
                        } else {
                            card.style.display = 'none';
                        }
                    });
                });

                // Form submission
                document.getElementById('transaksiForm').addEventListener('submit', function (e) {
                    e.preventDefault();

                    if (cart.length === 0) {
                        alert('Keranjang belanja kosong!');
                        return;
                    }

                    const pembayaran = parseFloat(document.getElementById('pembayaran').value);
                    const total = parseFloat(document.getElementById('totalAmount').dataset.value) || 0;

                    if (pembayaran < total) {
                        alert('Pembayaran kurang!');
                        return;
                    }

                    // Tambahkan input hidden untuk setiap item di cart
                    cart.forEach((item, index) => {
                        const inputId = document.createElement('input');
                        inputId.type = 'hidden';
                        inputId.name = "items[" + index + "].parfumId";
                        inputId.value = item.id;
                        this.appendChild(inputId);

                        const inputQty = document.createElement('input');
                        inputQty.type = 'hidden';
                        inputQty.name = "items[" + index + "].quantity";
                        inputQty.value = item.quantity;
                        this.appendChild(inputQty);
                    });

                    this.submit();
                });
            </script>

            <style>
                .parfum-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                    gap: 15px;
                    margin-top: 20px;
                }

                .parfum-card {
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    padding: 10px;
                    text-align: center;
                }

                .parfum-image img {
                    width: 100px;
                    height: 100px;
                    object-fit: cover;
                }

                .parfum-info h4 {
                    font-size: 14px;
                    margin: 10px 0 5px 0;
                }

                .parfum-info p {
                    font-size: 12px;
                    margin: 5px 0;
                }
            </style>

            <jsp:include page="/views/templates/footer.jsp" />