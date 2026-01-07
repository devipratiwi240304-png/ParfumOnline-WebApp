<%-- Document : form Created on : 3 Jan 2026, 01.25.56 Author : Devi Pratiwi --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="${param.id != null ? 'Edit Penggajian' : 'Tambah Penggajian'}" />
            </jsp:include>

            <h1>${param.id != null ? 'Edit Penggajian' : 'Tambah Penggajian'}</h1>

            <form action="${pageContext.request.contextPath}/admin/penggajian/save" method="post">
                <c:if test="${param.id != null}">
                    <input type="hidden" name="id" value="${param.id}">
                </c:if>

                <div class="form-group">
                    <label for="karyawan_id">Karyawan</label>
                    <select id="karyawan_id" name="karyawan_id" class="form-control" required>
                        <option value="">Pilih Karyawan</option>
                        <c:forEach var="karyawan" items="${karyawanList}">
                            <option value="${karyawan.id}" ${penggajian.karyawanId==karyawan.id ? 'selected' : '' }>
                                ${karyawan.nik} - ${karyawan.namaLengkap}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="bulan">Bulan</label>
                            <select id="bulan" name="bulan" class="form-control" required>
                                <option value="">Pilih Bulan</option>
                                <option value="01" ${penggajian.bulan=='01' ? 'selected' : '' }>Januari</option>
                                <option value="02" ${penggajian.bulan=='02' ? 'selected' : '' }>Februari</option>
                                <option value="03" ${penggajian.bulan=='03' ? 'selected' : '' }>Maret</option>
                                <option value="04" ${penggajian.bulan=='04' ? 'selected' : '' }>April</option>
                                <option value="05" ${penggajian.bulan=='05' ? 'selected' : '' }>Mei</option>
                                <option value="06" ${penggajian.bulan=='06' ? 'selected' : '' }>Juni</option>
                                <option value="07" ${penggajian.bulan=='07' ? 'selected' : '' }>Juli</option>
                                <option value="08" ${penggajian.bulan=='08' ? 'selected' : '' }>Agustus</option>
                                <option value="09" ${penggajian.bulan=='09' ? 'selected' : '' }>September</option>
                                <option value="10" ${penggajian.bulan=='10' ? 'selected' : '' }>Oktober</option>
                                <option value="11" ${penggajian.bulan=='11' ? 'selected' : '' }>November</option>
                                <option value="12" ${penggajian.bulan=='12' ? 'selected' : '' }>Desember</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="tahun">Tahun</label>
                            <input type="number" id="tahun" name="tahun" value="${penggajian.tahun}"
                                class="form-control" required min="2020" max="2030">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="gaji_pokok">Gaji Pokok</label>
                    <input type="number" id="gaji_pokok" name="gaji_pokok" value="${penggajian.gajiPokok}"
                        class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="tunjangan">Tunjangan</label>
                    <input type="number" id="tunjangan" name="tunjangan" value="${penggajian.tunjangan}"
                        class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="potongan">Potongan</label>
                    <input type="number" id="potongan" name="potongan" value="${penggajian.potongan}"
                        class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status" class="form-control" required>
                        <option value="pending" ${penggajian.status=='pending' ? 'selected' : '' }>Belum
                            Dibayar</option>
                        <option value="dibayar" ${penggajian.status=='dibayar' ? 'selected' : '' }>Dibayar</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Simpan</button>
                <a href="${pageContext.request.contextPath}/admin/penggajian" class="btn btn-secondary">Kembali</a>
            </form>

            <jsp:include page="/views/templates/footer.jsp" />