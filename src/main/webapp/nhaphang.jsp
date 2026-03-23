<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập hàng</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        body {
            background:#0d0d0d;
            color:white;
            padding:25px;
        }
        .table-container {
            background:#161616;
            border-radius:18px;
        }
        .btn-info {
            background:#ffc107 !important;
            border:none;
        }
        .modal-content {
            background:#1a1a1a;
            color:white;
            border-radius:20px;
        }
        .form-control {
            background:#161616;
            border:1px solid #333;
            color:white;
        }
    </style>
</head>

<body>

<!-- HEADER -->
<div class="d-flex justify-content-between mb-3">
    <h5>Quản lý nhập hàng</h5>
    <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#modalNhap">
        <i class="fas fa-plus"></i> Tạo phiếu
    </button>
</div>

<!-- TABLE -->
<div class="table-container p-3">
    <table class="table text-white">
        <thead>
        <tr>
            <th>Mã</th>
            <th>Ngày</th>
            <th>NCC</th>
            <th>Tổng</th>
            <th>Người tạo</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="p" items="${list}">
            <tr>
                <td>PN${p.maPhieuNhapKho}</td>
                <td><fmt:formatDate value="${p.ngayNhapKho}" pattern="dd/MM/yyyy"/></td>
                <td>${p.maNCC}</td>
                <td><fmt:formatNumber value="${p.tongTien}"/> đ</td>
                <td>${p.maNguoiDung}</td>

                <!-- ICON XEM -->
                <td>
                    <button class="btn btn-sm btn-dark"
                            onclick="viewDetail(${p.maPhieuNhapKho})">
                        <i class="fas fa-eye"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<!-- ================= POPUP TẠO ================= -->
<div class="modal fade" id="modalNhap">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header border-0">
                <h6 class="text-info">TẠO PHIẾU NHẬP</h6>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>

            <form method="post" action="tao-phieu-nhap">

                <div class="modal-body">

                    <input name="maNguoiDung" class="form-control mb-2" placeholder="Mã người dùng">
                    <input name="maNCC" class="form-control mb-2" placeholder="Mã NCC">

                    <!-- DANH SÁCH NGUYÊN LIỆU -->
                    <table class="table text-white">
                        <thead>
                        <tr>
                            <th>Nguyên liệu</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody id="items">
                        <tr>
                            <td><input name="maNguyenLieu" class="form-control"></td>
                            <td><input name="soLuong" class="form-control"></td>
                            <td><input name="donGia" class="form-control"></td>
                            <td><button type="button" onclick="removeRow(this)">X</button></td>
                        </tr>
                        </tbody>
                    </table>

                    <button type="button" onclick="addRow()" class="btn btn-sm btn-secondary">+ Thêm dòng</button>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-info w-100">Lưu</button>
                </div>

            </form>

        </div>
    </div>
</div>

<!-- ================= POPUP CHI TIẾT ================= -->
<div class="modal fade" id="modalDetail">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header border-0">
                <h6 class="text-info">CHI TIẾT PHIẾU NHẬP</h6>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body" id="detailContent">
                <!-- load bằng JS -->
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // thêm dòng
    function addRow() {
        let html = `
    <tr>
        <td><input name="maNguyenLieu" class="form-control"></td>
        <td><input name="soLuong" class="form-control"></td>
        <td><input name="donGia" class="form-control"></td>
        <td><button type="button" onclick="removeRow(this)">X</button></td>
    </tr>`;
        document.getElementById("items").insertAdjacentHTML("beforeend", html);
    }

    function removeRow(btn) {
        btn.closest("tr").remove();
    }

    // xem chi tiết (fake demo)
    function viewDetail(id) {
        document.getElementById("detailContent").innerHTML =
            "Đang load chi tiết phiếu #" + id;

        new bootstrap.Modal(document.getElementById('modalDetail')).show();
    }
</script>

</body>
</html>