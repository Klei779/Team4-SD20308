<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Nhà Cung Cấp - Poly Coffee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        /* --- TỔNG THỂ NỀN ĐEN --- */
        body {
            background-color: #0d0d0d !important;
            color: #ffffff !important;
            padding: 25px;
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
        }

        /* Chống chữ nghiêng và căn chỉnh */
        * { font-style: normal !important; }

        /* --- THANH TÌM KIẾM --- */
        .search-area {
            background-color: #161616 !important;
            border: 1px solid #333 !important;
            border-radius: 18px;
            padding: 20px;
            margin-bottom: 25px;
        }

        .form-control {
            background-color: #222 !important;
            border: 1px solid #444 !important;
            color: white !important;
            border-radius: 12px !important;
            padding: 10px 15px;
        }

        .form-control:focus {
            border-color: #ffc107 !important;
            box-shadow: none !important;
        }

        /* --- NÚT BẤM MÀU VÀNG (INFO) --- */
        .btn-info-custom {
            background-color: #ffc107 !important;
            color: #000 !important;
            font-weight: bold;
            border: none;
            border-radius: 12px;
            padding: 10px 25px;
            transition: 0.3s;
        }
        .btn-info-custom:hover {
            background-color: #e5af06 !important;
            transform: translateY(-2px);
        }

        /* --- BẢNG (FIX LỖI MÀU TRẮNG) --- */
        .table-container {
            background-color: #161616 !important;
            border-radius: 18px;
            border: 1px solid #222;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
        }

        .table {
            margin-bottom: 0 !important;
            color: #ffffff !important;
            background-color: #161616 !important;
            border-collapse: collapse;
        }

        /* Header bảng */
        .table thead th {
            background-color: #1f1f1f !important;
            color: #888 !important;
            font-size: 11px;
            text-transform: uppercase;
            padding: 18px;
            border-bottom: 2px solid #333 !important;
            border-top: none;
        }

        /* Dòng trong bảng */
        .table tbody tr {
            background-color: #161616 !important;
            border-bottom: 1px solid #222 !important;
            transition: 0.2s;
        }

        .table tbody td {
            padding: 18px;
            vertical-align: middle;
            color: #dddddd !important;
            background-color: transparent !important;
        }

        .table-hover tbody tr:hover {
            background-color: #1d1d1d !important;
        }

        /* --- AVATAR & BADGE --- */
        .avatar-square {
            width: 40px; height: 40px;
            background: #222; color: #ffc107;
            border: 1px solid #333;
            display: flex; align-items: center; justify-content: center;
            border-radius: 10px; font-weight: bold; font-size: 16px;
        }

        .badge-location {
            font-size: 11px; padding: 6px 12px; border-radius: 8px;
            border: 1px solid #333; background: #1a1a1a; color: #ffc107;
            display: inline-flex; align-items: center;
        }

        /* --- MODAL DARK --- */
        .modal-content {
            background-color: #161616 !important;
            border: 1px solid #333 !important;
            border-radius: 20px !important;
            color: white;
        }
        .modal-header { border-bottom: 1px solid #222; }
        .modal-footer { border-top: none; }
    </style>
</head>
<body>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h4 class="fw-bold text-uppercase m-0">Quản lý nhà cung cấp</h4>
        <p class="text-secondary small mb-0">Danh mục đối tác cung ứng nguyên liệu Poly Coffee</p>
    </div>
    <button class="btn btn-info-custom" onclick="prepareAdd()">
        <i class="fas fa-plus me-2"></i> THÊM MỚI
    </button>
</div>

<div class="search-area">
    <form action="quanlynhacungcap" method="GET" class="row g-2 m-0">
        <div class="col-md-11">
            <input type="text" name="txtSearch" class="form-control"
                   placeholder="Tìm tên hoặc số điện thoại nhà cung cấp..." value="${param.txtSearch}">
        </div>
        <div class="col-md-1">
            <button type="submit" class="btn btn-info-custom w-100">
                <i class="fas fa-search"></i>
            </button>
        </div>
    </form>
</div>

<div class="table-container shadow">
    <table class="table table-hover align-middle">
        <thead>
        <tr>
            <th class="ps-4" style="width: 100px;">Mã</th>
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
                    <td colspan="5" class="text-center py-5 text-secondary">
                        <i class="fas fa-box-open fa-2x mb-3"></i><br>Chưa có dữ liệu nhà cung cấp.
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="ncc" items="${dsNCC}">
                    <tr>
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
                            <button class="btn btn-link text-info p-1 me-2" title="Sửa"
                                    onclick="prepareEdit('${ncc.maNhaCungCap}', '${ncc.tenNhaCungCap}', '${ncc.dienThoai}', '${ncc.diaChi}')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-link text-danger p-1" title="Xóa"
                                    onclick="confirmDelete('${ncc.maNhaCungCap}', '${ncc.tenNhaCungCap}')">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<div class="modal fade" id="modalNCC" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="quanlynhacungcap" method="POST">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="maNhaCungCap" id="mMa">

                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title fw-bold text-warning" id="modalTitle">THÔNG TIN ĐỐI TÁC</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <div class="mb-3">
                        <label class="small text-secondary mb-1">Tên nhà cung cấp</label>
                        <input type="text" name="tenNhaCungCap" id="mTen" class="form-control" placeholder="Nhập tên đối tác..." required>
                    </div>
                    <div class="mb-3">
                        <label class="small text-secondary mb-1">Số điện thoại</label>
                        <input type="text" name="dienThoai" id="mSdt" class="form-control" placeholder="Nhập số liên hệ..." required>
                    </div>
                    <div class="mb-3">
                        <label class="small text-secondary mb-1">Địa chỉ trụ sở</label>
                        <textarea name="diaChi" id="mDiaChi" class="form-control" rows="3" placeholder="Địa chỉ chi tiết..." required></textarea>
                    </div>
                </div>
                <div class="modal-footer border-0">
                    <button type="submit" class="btn btn-info-custom w-100">XÁC NHẬN LƯU</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const modalObj = new bootstrap.Modal(document.getElementById('modalNCC'));

    // Hàm mở modal để thêm mới
    function prepareAdd() {
        document.getElementById('modalTitle').innerText = "THÊM ĐỐI TÁC CUNG ỨNG";
        document.getElementById('mMa').value = "";
        document.getElementById('mTen').value = "";
        document.getElementById('mSdt').value = "";
        document.getElementById('mDiaChi').value = "";
        modalObj.show();
    }

    // Hàm mở modal để chỉnh sửa
    function prepareEdit(ma, ten, sdt, diachi) {
        document.getElementById('modalTitle').innerText = "CẬP NHẬT NHÀ CUNG CẤP #" + ma;
        document.getElementById('mMa').value = ma;
        document.getElementById('mTen').value = ten;
        document.getElementById('mSdt').value = sdt;
        document.getElementById('mDiaChi').value = diachi;
        modalObj.show();
    }

    // Hàm xác nhận xóa (Gửi POST qua Form động)
    function confirmDelete(ma, ten) {
        if (confirm("Xác nhận xóa nhà cung cấp: " + ten + "?")) {
            const f = document.createElement('form');
            f.method = 'POST';
            f.action = 'quanlynhacungcap';
            f.innerHTML = `
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="maNhaCungCap" value="${ma}">
            `;
            document.body.appendChild(f);
            f.submit();
        }
    }
</script>
</body>
</html>