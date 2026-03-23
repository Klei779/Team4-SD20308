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
        /* Đồng bộ nền và font chữ */
        body {
            background-color: #0d0d0d;
            color: white;
            padding: 25px;
            font-family: 'Segoe UI', sans-serif;
        }

        /* Đồng bộ Input & Select */
        .form-control, .form-select {
            background-color: #161616 !important;
            border: 1px solid #333 !important;
            color: white !important;
            border-radius: 12px !important;
            box-shadow: none !important;
        }

        /* Đồng bộ nút bấm màu INFO giống Nhà cung cấp */
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

        /* Avatar hình vuông bo góc giống mẫu Nhà cung cấp */
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

        /* Badge vai trò & trạng thái */
        .badge-role {
            font-size: 10px;
            padding: 3px 8px;
            border-radius: 6px;
            border: 1px solid rgba(51, 51, 51, 0.89);
            color: #ffc107;
        }

        .status-active { color: #2ecc71; font-size: 12px; }
        .status-locked { color: #e74c3c; font-size: 12px; }

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
        <h5 class="fw-bold text-uppercase m-0">Quản lý nhân viên</h5>
        <small class="text-secondary small">Danh sách tài khoản hệ thống Poly Coffee</small>
    </div>
    <button class="btn btn-info px-4 fw-bold" data-bs-toggle="modal" data-bs-target="#modalThemNhanVien">
        <i class="fas fa-plus me-2"></i> THÊM MỚI
    </button>
</div>

<form action="quanlynhanvien" method="GET"
      class="row g-2 mb-4 p-3 bg-dark border border-secondary border-opacity-10 shadow-sm" style="border-radius: 18px;">
    <div class="col-md-5">
        <input type="text" name="txtSearch" class="form-control py-2" placeholder="Tìm tên hoặc mã nhân viên..."
               value="${param.txtSearch}">
    </div>
    <div class="col-md-3">
        <select name="role" class="form-select py-2">
            <option value="">Tất cả chức vụ</option>
            <option value="ADMIN">Quản lý</option>
            <option value="STAFF">Nhân viên</option>
        </select>
    </div>
    <div class="col-md-3">
        <select name="status" class="form-select py-2">
            <option value="">Trạng thái</option>
            <option value="1">Đang hoạt động</option>
            <option value="0">Bị khóa</option>
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
                <th>Nhân viên</th>
                <th>Vai trò</th>
                <th>Liên hệ</th>
                <th>Trạng thái</th>
                <th class="text-center">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty dsNhanVien}">
                    <tr>
                        <td colspan="6" class="text-center py-5 text-secondary">Chưa có dữ liệu nhân viên.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="nd" items="${dsNhanVien}">
                        <tr class="border-bottom border-secondary border-opacity-10">
                            <td class="ps-4 text-secondary small">#${nd.maNguoiDung}</td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <div class="avatar-square me-3">
                                            ${nd.tenNguoiDung.charAt(0).toString().toUpperCase()}
                                    </div>
                                    <div>
                                        <div class="fw-bold text-white">${nd.tenNguoiDung}</div>
                                        <div class="small text-secondary" style="font-size: 10px;">ID: ${nd.tenDangNhap}</div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span class="badge-role">${nd.vaiTro}</span>
                            </td>
                            <td class="small text-secondary">${nd.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${nd.trangThai}">
                                        <span class="status-active"><i class="fas fa-circle me-1" style="font-size: 8px;"></i> Hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-locked"><i class="fas fa-circle me-1" style="font-size: 8px;"></i> Bị khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <button class="btn btn-link text-secondary p-1 me-2" data-bs-toggle="modal" data-bs-target="#modalSuaNhanVien">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="btn btn-link text-danger p-1"><i class="fas fa-trash-alt"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="modalThemNhanVien" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow-lg">
            <div class="modal-header border-0 pb-0">
                <h6 class="modal-title fw-bold text-info">TẠO TÀI KHOẢN MỚI</h6>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="text" class="form-control mb-3" placeholder="Họ và tên">
                <div class="row g-2 mb-3">
                    <div class="col-md-6"><input type="text" class="form-control" placeholder="Tên đăng nhập"></div>
                    <div class="col-md-6"><input type="password" class="form-control" placeholder="Mật khẩu"></div>
                </div>
                <input type="email" class="form-control mb-3" placeholder="Email liên hệ">
                <div class="row g-2">
                    <div class="col-md-6">
                        <select class="form-select">
                            <option value="STAFF">Nhân viên</option>
                            <option value="ADMIN">Quản lý</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <select class="form-select">
                            <option value="1">Kích hoạt</option>
                            <option value="0">Khóa</option>
                        </select>
                    </div>
                </div>
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