<%--
  Created by IntelliJ IDEA.
  User: lannn
  Date: 3/22/2026
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Kho nguyên liệu</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #000000; /* Đen tuyền */
            color: white;
        }

        .card-custom {
            background-color: #111111;
            border-radius: 15px;
            padding: 15px;
            transition: 0.2s;
        }

        .card-custom:hover {
            transform: scale(1.03);
        }

        .low {
            border: 2px solid #dc3545;
        }

        .progress {
            height: 6px;
        }

        .toast-body {
            color: white;
        }
    </style>
</head>
<body class="p-4">

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Kho nguyên liệu</h2>
    <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#addModal">
        <i class="bi bi-plus-circle"></i> Thêm
    </button>
</div>

<c:choose>
    <c:when test="${not empty sapHet}">
        <div class="alert alert-danger">
            ⚠ Có <b>${sapHet.size()}</b> nguyên liệu dưới mức tối thiểu:
            <ul>
                <c:forEach var="nl" items="${sapHet}">
                    <li>${nl.tenNguyenLieu} (còn ${nl.soLuongTon})</li>
                </c:forEach>
            </ul>
        </div>
    </c:when>
    <c:otherwise>
        <div class="alert alert-success">
            Không có nguyên liệu nào dưới mức tối thiểu
        </div>
    </c:otherwise>
</c:choose>

<div class="row">
    <c:forEach var="nl" items="${list}">
        <div class="col-md-3 mb-3">
            <div class="card-custom ${nl.soLuongTon < nl.soLuongToiThieu ? 'low' : ''}">
                <h5>
                        ${nl.tenNguyenLieu}
                    <c:if test="${nl.soLuongTon < nl.soLuongToiThieu}">
                        <span class="badge bg-danger">Sắp hết</span>
                    </c:if>
                </h5>
                <h3>${nl.soLuongTon} <small>${nl.donVi}</small></h3>
                <small>Tối thiểu: ${nl.soLuongToiThieu}</small>

                <c:set var="percent" value="${(nl.soLuongTon * 100) / nl.soLuongToiThieu}" />
                <c:if test="${percent > 100}"><c:set var="percent" value="100"/></c:if>

                <div class="progress mt-2">
                    <div class="progress-bar ${nl.soLuongTon < nl.soLuongToiThieu ? 'bg-danger' : 'bg-success'}"
                         style="width: ${percent}%"></div>
                </div>

                <div class="mt-3 d-flex justify-content-between">
                    <!-- nhập -->
                    <button class="btn btn-sm btn-success" onclick="openNhap(${nl.maNguyenLieu})">
                        <i class="bi bi-plus"></i>
                    </button>

                    <!-- sửa -->
                    <button class="btn btn-sm btn-warning"
                            onclick="openEdit(
                                    '${nl.maNguyenLieu}',
                                    '${nl.tenNguyenLieu}',
                                    '${nl.soLuongTon}',
                                    '${nl.donVi}',
                                    '${nl.soLuongToiThieu}',
                                    '${nl.maLoaiNguyenLieu}',
                                    '${nl.ghiChu}'
                                    )">
                    <i class="bi bi-pencil"></i>
                    </button>

                    <!-- xóa -->
                    <form method="post" action="khonguyenlieu"
                          onsubmit="return confirm('Bạn có chắc muốn xóa không?')">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${nl.maNguyenLieu}">
                        <button class="btn btn-sm btn-danger">
                            <i class="bi bi-trash"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- modal thêm -->
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-white">
            <div class="modal-header">
                <h5>Thêm nguyên liệu</h5>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="add">
                <div class="modal-body">
                    <label>Tên nguyên liệu</label>
                    <input class="form-control mb-2" name="ten" required>
                    <label>Số lượng</label>
                    <input class="form-control mb-2" name="soLuong" type="number" required>
                    <label>Đơn vị</label>
                    <input class="form-control mb-2" name="donVi" required>
                    <label>Số lượng tối thiểu</label>
                    <input class="form-control mb-2" name="toiThieu" type="number" required>
                    <label>Mã loại</label>
                    <select name="maLoai" class="form-control">
                        <c:forEach var="l" items="${listLoai}">
                            <option value="${l.maLoaiNguyenLieu}">
                                    ${l.tenLoaiNguyenLieu}
                            </option>
                        </c:forEach>
                    </select>                    <label>Ghi chú</label>
                    <input class="form-control mb-2" name="ghiChu">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-warning"><i class="bi bi-check-circle"></i> Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- modal sửa -->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-white">
            <div class="modal-header">
                <h5>Sửa nguyên liệu</h5>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" id="id">
                <div class="modal-body">
                    <label>Tên nguyên liệu</label>
                    <input class="form-control mb-2" name="ten" id="ten">
                    <label>Số lượng</label>
                    <input class="form-control mb-2" name="soLuong" id="soLuong">
                    <label>Đơn vị</label>
                    <input class="form-control mb-2" name="donVi" id="donVi">
                    <label>Số lượng tối thiểu</label>
                    <input class="form-control mb-2" name="toiThieu" id="toiThieu">
                    <label>Mã loại</label>
                    <input class="form-control" name="maLoai" id="maLoai">
                    <label>Ghi chú</label>
                    <input class="form-control" name="ghiChu" id="ghiChu">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- modal nhập -->
<div class="modal fade" id="nhapModal">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-white">
            <div class="modal-header">
                <h5>Nhập kho</h5>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="nhap">
                <input type="hidden" name="id" id="nhap-id">
                <div class="modal-body">
                    <label>Số lượng nhập</label>
                    <input class="form-control" name="soLuongNhap" type="number">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success">Xác nhận</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- toast -->
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="liveToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body" id="toast-body"></div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function openEdit(id, ten, soLuong, donVi, toiThieu, maLoai, ghiChu) {
        document.getElementById("id").value = id;
        document.getElementById("ten").value = ten;
        document.getElementById("soLuong").value = soLuong;
        document.getElementById("donVi").value = donVi;
        document.getElementById("toiThieu").value = toiThieu;
        document.getElementById("maLoai").value = maLoai;
        document.getElementById("ghiChu").value = ghiChu;
        new bootstrap.Modal(document.getElementById('editModal')).show();
    }

    function openNhap(id) {
        document.getElementById("nhap-id").value = id;
        new bootstrap.Modal(document.getElementById('nhapModal')).show();
    }

    // thông báo
    document.addEventListener("DOMContentLoaded", function() {
        const toastEl = document.getElementById('liveToast');
        const toastBody = document.getElementById('toast-body');

        <% if (session.getAttribute("message") != null) { %>
        toastBody.innerText = '<%= session.getAttribute("message").toString().replace("'", "\\'") %>';
        toastEl.classList.remove('bg-danger');
        toastEl.classList.add('bg-success');
        new bootstrap.Toast(toastEl, {delay: 3000}).show();
        <% session.removeAttribute("message"); %>
        <% } else if (session.getAttribute("error") != null) { %>
        toastBody.innerText = '<%= session.getAttribute("error").toString().replace("'", "\\'") %>';
        toastEl.classList.remove('bg-success');
        toastEl.classList.add('bg-danger');
        new bootstrap.Toast(toastEl, {delay: 3000}).show();
        <% session.removeAttribute("error"); %>
        <% } %>
    });
</script>

</body>
</html>