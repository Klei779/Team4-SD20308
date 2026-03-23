<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Công Thức - Poly Coffee</title>
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

        .form-control::placeholder {
            color: #666 !important;
        }

        /* Đồng bộ nút bấm */
        .btn {
            border-radius: 12px !important;
            transition: 0.3s;
        }
        .btn-info {
            background-color: #ffc107 !important;
            border: none;
            color: white !important;
            font-weight: 600;
        }
        .btn-outline-info {
            border: 1px solid #ffc107 !important;
            color: #ffc107 !important;
        }
        .btn-outline-info:hover {
            background-color: #ffc107 !important;
            color: white !important;
        }
        .btn-secondary {
            background-color: #333 !important;
            border: none;
        }

        /* Container chứa bảng */
        .table-container {
            background-color: #161616;
            border-radius: 18px;
            overflow: hidden;
            border: 1px solid #222;
        }

        /* Header của bảng */
        .table thead {
            background-color: #1f1f1f !important;
            color: #888 !important;
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .table {
            margin-bottom: 0 !important;
            --bs-table-bg: transparent;
            --bs-table-color: white;
            --bs-table-hover-bg: #1a1a1a;
        }

        /* Badge Trạng thái */
        .status-active { color: #2ecc71; font-size: 12px; font-weight: 500;}

        /* Modal Style */
        .modal-content {
            background-color: #1a1a1a;
            border: 1px solid #333;
            border-radius: 20px !important;
            color: white;
        }

        /* Thanh định lượng (Progress Bar) */
        .progress-dark {
            background-color: #222;
            height: 10px;
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #333;
        }
        .progress-bar-custom {
            background-color: #ffc107;
            border-radius: 10px;
        }

        /* Khung thêm nguyên liệu */
        .add-ingredient-box {
            background-color: #111;
            border: 1px dashed #444;
            border-radius: 12px;
            padding: 15px;
        }
    </style>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h5 class="fw-bold text-uppercase m-0">Định mức nguyên liệu theo món</h5>
        <small class="text-secondary small">Quản lý cấu trúc công thức và định lượng nguyên liệu</small>
    </div>
    <button class="btn btn-info px-4" data-bs-toggle="modal" data-bs-target="#modalThemCongThuc">
        <i class="fas fa-plus me-2"></i> TẠO CÔNG THỨC MỚI
    </button>
</div>

<form class="row g-2 mb-4 p-3 bg-dark border border-secondary border-opacity-10 shadow-sm" style="border-radius: 18px; margin: 0;">
    <div class="col-md-8">
        <input type="text" class="form-control py-2" placeholder="Tìm tên món ăn hoặc công thức...">
    </div>
    <div class="col-md-3">
        <select class="form-select py-2">
            <option>Tất cả danh mục</option>
            <option>Cà phê</option>
            <option>Trà sữa</option>
            <option>Bánh ngọt</option>
        </select>
    </div>
    <div class="col-md-1">
        <button type="button" class="btn btn-info w-100 py-2"><i class="fas fa-search"></i></button>
    </div>
</form>

<div class="table-container shadow">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th class="ps-4">Mã CT</th>
                <th>Tên món / Công thức</th>
                <th>Số lượng nguyên liệu</th>
                <th>Trạng thái</th>
                <th class="text-center">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <tr class="border-bottom border-secondary border-opacity-10">
                <td class="ps-4 text-secondary small">#CT-001</td>
                <td class="fw-bold">☕ Cà phê sữa đá (Size M)</td>
                <td class="text-secondary small">Gồm 4 thành phần</td>
                <td><span class="status-active"><i class="fas fa-check-circle me-1"></i> Đang áp dụng</span></td>
                <td class="text-center">
                    <button class="btn btn-info btn-sm text-white px-3" data-bs-toggle="modal" data-bs-target="#modalChiTietCongThuc">
                        <i class="fas fa-sliders-h me-1"></i> Xem chi tiết
                    </button>
                </td>
            </tr>
            <tr class="border-bottom border-secondary border-opacity-10">
                <td class="ps-4 text-secondary small">#CT-002</td>
                <td class="fw-bold">🍔 Classic Burger</td>
                <td class="text-secondary small">Gồm 5 thành phần</td>
                <td><span class="status-active"><i class="fas fa-check-circle me-1"></i> Đang áp dụng</span></td>
                <td class="text-center">
                    <button class="btn btn-info btn-sm text-white px-3" data-bs-toggle="modal" data-bs-target="#modalChiTietCongThuc">
                        <i class="fas fa-sliders-h me-1"></i> Xem chi tiết
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="modalChiTietCongThuc" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content shadow-lg">
            <div class="modal-header border-0 pb-0">
                <div>
                    <h5 class="modal-title fw-bold text-info m-0">CHI TIẾT CÔNG THỨC</h5>
                    <small class="text-secondary">Món: Cà phê sữa đá (Size M)</small>
                </div>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">

                <h6 class="text-uppercase text-secondary small fw-bold mb-3">Tỷ lệ định mức nguyên liệu</h6>

                <div class="mb-3">
                    <div class="d-flex justify-content-between small mb-1">
                        <span>Cốt cà phê pha phin</span>
                        <span class="text-info fw-bold">40 ml</span>
                    </div>
                    <div class="progress-dark">
                        <div class="progress-bar-custom" style="width: 40%; background-color: #f39c12;"></div>
                    </div>
                </div>

                <div class="mb-3">
                    <div class="d-flex justify-content-between small mb-1">
                        <span>Sữa đặc Ngôi sao phương nam</span>
                        <span class="text-info fw-bold">25 ml</span>
                    </div>
                    <div class="progress-dark">
                        <div class="progress-bar-custom" style="width: 25%; background-color: #ecf0f1;"></div>
                    </div>
                </div>

                <div class="mb-3">
                    <div class="d-flex justify-content-between small mb-1">
                        <span>Đá viên</span>
                        <span class="text-info fw-bold">120 g</span>
                    </div>
                    <div class="progress-dark">
                        <div class="progress-bar-custom" style="width: 100%; background-color: #ffc107;"></div>
                    </div>
                </div>

                <div class="mb-4">
                    <div class="d-flex justify-content-between small mb-1">
                        <span>Đường nước</span>
                        <span class="text-info fw-bold">10 ml</span>
                    </div>
                    <div class="progress-dark">
                        <div class="progress-bar-custom" style="width: 10%; background-color: #95a5a6;"></div>
                    </div>
                </div>

                <div class="add-ingredient-box mt-4">
                    <h6 class="text-uppercase text-secondary small fw-bold mb-3"><i class="fas fa-plus-circle me-1"></i> Bổ sung nguyên liệu</h6>
                    <div class="row g-2 align-items-center">
                        <div class="col-md-5">
                            <select class="form-select text-secondary">
                                <option>-- Chọn từ kho nguyên liệu --</option>
                                <option>Sữa tươi không đường</option>
                                <option>Bột cacao</option>
                                <option>Topping trân châu</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input type="number" class="form-control" placeholder="Định lượng">
                                <span class="input-group-text bg-dark text-secondary border-dark">ml/g</span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <button class="btn btn-outline-info w-100 h-100">THÊM</button>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer border-0 pt-0">
                <button type="button" class="btn btn-secondary px-4 fw-bold text-white" data-bs-dismiss="modal">HỦY</button>
                <button type="button" class="btn btn-info px-4 fw-bold">LƯU CÔNG THỨC</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>