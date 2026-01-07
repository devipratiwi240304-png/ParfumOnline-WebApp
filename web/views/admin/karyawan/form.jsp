<%-- Document : form Created on : 3 Jan 2026 Author : Devi Pratiwi --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <jsp:include page="/views/templates/header.jsp">
                <jsp:param name="title" value="${karyawan != null ? 'Edit Karyawan' : 'Tambah Karyawan'}" />
            </jsp:include>

            <h1>${karyawan != null ? 'Edit Karyawan' : 'Tambah Karyawan'}</h1>

            <form action="${pageContext.request.contextPath}/karyawan/${karyawan != null ? 'update' : 'create'}"
                method="post">
                <c:if test="${karyawan != null}">
                    <input type="hidden" name="id" value="${karyawan.id}">
                </c:if>

                <div class="form-group">
                    <label for="user_id">User</label>
                    <select id="user_id" name="user_id" class="form-control" required>
                        <option value="">Pilih User</option>
                        <c:forEach var="user" items="${userList}">
                            <option value="${user.id}" ${karyawan !=null && karyawan.userId==user.id ? 'selected' : ''
                                }>
                                ${user.namaLengkap} (${user.username})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="nik">NIK</label>
                    <input type="text" id="nik" name="nik" value="${karyawan.nik}" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="jabatan">Jabatan</label>
                    <input type="text" id="jabatan" name="jabatan" value="${karyawan.jabatan}" class="form-control"
                        required>
                </div>

                <div class="form-group">
                    <label for="gaji_pokok">Gaji Pokok</label>
                    <input type="number" id="gaji_pokok" name="gaji_pokok" value="${karyawan.gajiPokok}"
                        class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="tunjangan">Tunjangan</label>
                    <input type="number" id="tunjangan" name="tunjangan" value="${karyawan.tunjangan}"
                        class="form-control">
                </div>

                <div class="form-group">
                    <label for="bank">Bank</label>
                    <input type="text" id="bank" name="bank" value="${karyawan.bank}" class="form-control">
                </div>

                <div class="form-group">
                    <label for="nomor_rekening">Nomor Rekening</label>
                    <input type="text" id="nomor_rekening" name="nomor_rekening" value="${karyawan.nomorRekening}"
                        class="form-control">
                </div>

                <button type="submit" class="btn btn-primary">Simpan</button>
                <a href="${pageContext.request.contextPath}/karyawan/list" class="btn btn-secondary">Kembali</a>
            </form>

            <jsp:include page="/views/templates/footer.jsp" />