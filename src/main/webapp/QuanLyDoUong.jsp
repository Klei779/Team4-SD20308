<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Đồ uống</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Đồng bộ màu nền và font chữ */
        body {
            background-color: #0d0d0d;
            color: white;
            padding: 25px;
            font-family: 'Segoe UI', sans-serif;
        }

        /* Đồng bộ Input giống Nhà cung cấp */
        .form-control, .form-select {
            background-color: #161616 !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 12px !important;
            box-shadow: none !important;
        }

        /* Đồng bộ nút bấm (Sử dụng màu Info giống mẫu) */
        .btn {
            border-radius: 12px !important;
            transition: 0.3s;
        }

        .btn-info {
            background-color: #ffc107 !important;
            border: none;
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

        /* Loại bỏ viền trắng mặc định của table bootstrap */
        .table {
            margin-bottom: 0 !important;
            --bs-table-bg: transparent;
            --bs-table-color: white;
            --bs-table-hover-bg: #1a1a1a;
        }

        /* Avatar/Hình ảnh vuông giống mẫu Nhà cung cấp */
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
            overflow: hidden;
        }

        .avatar-square img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        /* Đồng bộ Modal */
        .modal-content {
            background-color: #1a1a1a;
            border: 1px solid #333;
            border-radius: 20px !important;
            color: white;
        }

        /* Badge trạng thái bo tròn */
        .status-badge {
            font-size: 11px;
            padding: 4px 12px;
            border-radius: 20px;
            font-weight: 600;
        }

        .status-on {
            background: rgba(51, 51, 51, 0.89);
            color: #ffc107;
            border: 1px solid rgba(51, 51, 51, 0.89);
        }

        .status-off {
            background: rgba(220, 53, 69, 0.1);
            color: #dc3545;
            border: 1px solid rgba(220, 53, 69, 0.3);
        }
    </style>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h5 class="fw-bold text-uppercase m-0">Quản lý Đồ uống</h5>
    <button class="btn btn-info px-4 text-white fw-bold" data-bs-toggle="modal" data-bs-target="#modalThemDoUong">
        <i class="fas fa-plus me-2"></i> THÊM MỚI
    </button>
</div>

<form action="quanlydouong" method="GET"
      class="row g-2 mb-4 p-3 bg-dark border border-secondary border-opacity-10 shadow-sm" style="border-radius: 18px;">
    <div class="col-md-9">
        <input type="text" name="txtSearch" class="form-control py-2"
               placeholder="Tìm kiếm theo tên đồ uống..." value="${param.txtSearch}">
    </div>
    <div class="col-md-2">
        <select class="form-select py-2">
            <option selected>Danh mục</option>
        </select>
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
                <th>Hình</th>
                <th>Tên đồ uống</th>
                <th>Giá bán</th>
                <th>Trạng thái</th>
                <th class="text-center">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${dsDoUong}">
                <tr class="border-bottom border-secondary border-opacity-10">
                    <td class="ps-4 text-secondary">#${d.maDoUong}</td>
                    <td>
                        <div class="avatar-square">
                            <c:choose>
                                <c:when test="${not empty d.hinhAnh}">
                                    <img src="${d.hinhAnh}">
                                </c:when>
                                <c:otherwise>${d.tenDoUong.charAt(0)}</c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td>
                        <div class="fw-bold">${d.tenDoUong}</div>
                        <div class="small text-secondary" style="font-size: 11px;">Mô tả: ${d.moTa}</div>
                    </td>
                    <td class="text-warning fw-bold">
                        <fmt:formatNumber value="${d.giaTien}" type="currency" currencySymbol="đ"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${d.trangThai}">
                                <span class="status-badge status-on">Đang bán</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-badge status-off">Ngừng bán</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center">
                        <button class="btn btn-link text-secondary p-1"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-link text-danger p-1"><i class="fas fa-trash-alt"></i></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="modalThemDoUong" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow-lg">
            <div class="modal-header border-0">
                <h6 class="modal-title fw-bold text-info">THÊM ĐỒ UỐNG MỚI</h6>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="text" class="form-control mb-3" placeholder="Tên đồ uống">
                <div class="row g-2 mb-3">
                    <div class="col">
                        <select class="form-select">
                            <option>Chọn danh mục</option>
                        </select>
                    </div>
                    <div class="col">
                        <input type="number" class="form-control" placeholder="Giá bán (đ)">
                    </div>
                </div>
                <textarea class="form-control" rows="2" placeholder="Mô tả đồ uống"></textarea>
            </div>
            <div class="modal-footer border-0">
                <button class="btn btn-info w-100 text-white fw-bold">XÁC NHẬN LƯU</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>