<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Đồng bộ nền và font chữ giống Nhân viên */
        body {
            background-color: #0d0d0d;
            color: white;
            padding: 25px;
            font-family: 'Segoe UI', sans-serif;
        }

        /* Chống chữ nghiêng toàn trang */
        * {
            font-style: normal !important;
        }

        /* Đồng bộ Input & Select */
        .form-control, .form-select {
            background-color: #161616 !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 12px !important;
            box-shadow: none !important;
        }

        /* Đồng bộ nút bấm màu INFO */
        .btn {
            border-radius: 12px !important;
            transition: 0.3s;
        }
        .btn-info {
            background-color: #ffc107 !important;
            border: none;
            color: white !important;
        }

        /* Đồng bộ Container chứa bảng */
        .table-container {
            background-color: #161616;
            border-radius: 18px;
            overflow: hidden;
            border: 1px solid #222;
        }

        /* Đồng bộ Header của bảng */
        .table thead {
            background-color: #1f1f1f !important;
            color: #888 !important;
            font-size: 11px;
            text-transform: uppercase;
        }

        .table {
            margin-bottom: 0 !important;
            --bs-table-bg: transparent;
            --bs-table-color: white;
            --bs-table-hover-bg: #1a1a1a;
        }

        /* Avatar hình vuông bo góc giống mẫu */
        .avatar-square {
            width: 35px;
            height: 35px;
            background: #222;
            color: #ffc107;
            border: 1px solid #333;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 8px;
            font-weight: bold;
        }

        /* Badge Khu vực/Địa chỉ */
        .badge-location {
            font-size: 11px;
            padding: 4px 10px;
            border-radius: 8px;
            border: 1px solid rgba(51, 51, 51, 0.89);
            background: #1a1a1a;
            color: #ffc107;
            display: inline-flex;
            align-items: center;
        }

        /* Đồng bộ Modal */
        .modal-content {
            background-color: #1a1a1a;
            border: 1px solid #333;
            border-radius: 20px !important;
            color: white;
        }
    </style>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h5 class="fw-bold text-uppercase m-0">Quản lý nhà cung cấp</h5>
        <small class="text-secondary small">Danh mục đối tác cung ứng nguyên liệu Poly Coffee</small>
    </div>
    <button class="btn btn-info px-4 fw-bold" data-bs-toggle="modal" data-bs-target="#modalThemNCC">
        <i class="fas fa-plus me-2"></i> THÊM MỚI
    </button>
</div>

<form action="quanlynhacungcap" method="GET"
      class="row g-2 mb-4 p-3 bg-dark border border-secondary border-opacity-10 shadow-sm" style="border-radius: 18px;">
    <div class="col-md-11">
        <input type="text" name="txtSearch" class="form-control py-2" placeholder="Tìm tên hoặc số điện thoại nhà cung cấp..."
               value="${param.txtSearch}">
    </div>
    <div class="col-md-1">
        <button type="submit" class="btn btn-info w-100 py-2 text-white"><i class="fas fa-search"></i></button>
    </div>
</form>

<div class="table-container shadow">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th class="ps-4">Mã</th>
                <th>Nhà cung cấp</th>
                <th>Địa chỉ / Khu vực</th>
                <th>Liên hệ</th>
                <th class="text-center">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty dsNCC}">
                    <tr>
                        <td colspan="5" class="text-center py-5 text-secondary">Chưa có dữ liệu nhà cung cấp.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="ncc" items="${dsNCC}">
                        <tr class="border-bottom border-secondary border-opacity-10">
                            <td class="ps-4 text-secondary small">#${ncc.maNhaCungCap}</td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <div class="avatar-square me-3 text-uppercase">
                                            ${ncc.tenNhaCungCap.substring(0,1)}
                                    </div>
                                    <div>
                                        <div class="fw-bold text-white">${ncc.tenNhaCungCap}</div>
                                        <div class="small text-secondary" style="font-size: 10px;">ID: NCC-${ncc.maNhaCungCap}</div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span class="badge-location">
                                    <i class="fas fa-map-marker-alt me-1"></i> ${ncc.diaChi}
                                </span>
                            </td>
                            <td class="text-warning small fw-bold">
                                <i class="fas fa-phone-alt me-1"></i> ${ncc.dienThoai}
                            </td>
                            <td class="text-center">
                                <button class="btn btn-link text-secondary p-1 me-2" title="Sửa">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="btn btn-link text-danger p-1" title="Xóa"><i class="fas fa-trash-alt"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="modalThemNCC" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow-lg">
            <div class="modal-header border-0 pb-0">
                <h6 class="modal-title fw-bold text-info">THÊM ĐỐI TÁC CUNG ỨNG</h6>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="text" class="form-control mb-3" placeholder="Tên nhà cung cấp">
                <input type="text" class="form-control mb-3" placeholder="Số điện thoại liên hệ">
                <textarea class="form-control" rows="2" placeholder="Địa chỉ trụ sở..."></textarea>
            </div>
            <div class="modal-footer border-0">
                <button class="btn btn-info w-100 text-white fw-bold">XÁC NHẬN TẠO</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>