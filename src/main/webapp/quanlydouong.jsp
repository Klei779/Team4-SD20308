<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Đồ Uống - Poly Coffee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body { background-color: #0c0c0c; color: #e0e0e0; font-family: 'Segoe UI', sans-serif; }
        .sticky-wrapper { position: sticky; top: 0; z-index: 1040; background-color: #0c0c0c; padding: 20px 30px; border-bottom: 1px solid #222; }
        .table-container { background: #141414; border-radius: 12px; border: 1px solid #282828; margin: 20px 30px; }
        .table thead th { background-color: #1f1f1f !important; color: #888; font-size: 0.75rem; text-transform: uppercase; padding: 15px; }
        .status-badge { font-size: 0.7rem; padding: 4px 10px; border-radius: 20px; font-weight: bold; border: 1px solid; }
        .status-on { color: #ffc107; border-color: #ffc107; background: rgba(255, 193, 7, 0.1); }
        .status-off { color: #f44336; border-color: #f44336; background: rgba(244, 67, 54, 0.1); }
        .modal-content { background-color: #121212; border: 1px solid #333; color: white; border-radius: 15px; }
        .form-control, .form-select { background-color: #1a1a1a !important; border: 1px solid #333 !important; color: white !important; }
        .img-preview-box { width: 80px; height: 80px; border-radius: 10px; border: 2px dashed #333; overflow: hidden; display: flex; align-items: center; justify-content: center; background: #000; cursor: pointer; }
    </style>
</head>
<body>

<div class="sticky-wrapper">
    <div class="d-flex justify-content-between align-items-center">
        <h4 class="fw-bold m-0 text-white">QUẢN LÝ ĐỒ UỐNG</h4>
        <div class="d-flex gap-3">
            <form action="${ctx}/nhanvien/quanlydouong" method="GET" class="position-relative">
                <input type="text" name="txtSearch" class="form-control ps-5" placeholder="Tìm kiếm..." value="${searchValue}">
                <i class="fas fa-search position-absolute" style="left: 15px; top: 12px; color: #666;"></i>
            </form>
            <button class="btn btn-warning px-4 fw-bold" onclick="showAddModal()">+ THÊM MỚI</button>
        </div>
    </div>
</div>

<div class="table-container shadow-lg">
    <table class="table table-dark table-hover align-middle mb-0">
        <thead>
        <tr>
            <th class="ps-4">ẢNH</th>
            <th>TÊN ĐỒ UỐNG</th>
            <th>GIÁ BÁN</th>
            <th>TRẠNG THÁI</th>
            <th class="text-center">THAO TÁC</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${dsDoUong}">
            <tr>
                <td class="ps-4">
                    <img src="${ctx}/uploads/${(empty d.hinhAnh || d.hinhAnh == 'null') ? 'noimg.jpg' : d.hinhAnh}"
                         onerror="this.src='${ctx}/uploads/noimg.jpg'"
                         style="width: 50px; height: 50px; object-fit: cover; border-radius: 8px;">
                </td>
                <td>
                    <div class="fw-bold text-white">${d.tenDoUong}</div>
                    <div class="small text-secondary text-truncate" style="max-width: 250px;">${d.moTa}</div>
                </td>
                <td class="text-warning fw-bold">${d.giaTien}đ</td>
                <td>
                        <span class="status-badge ${d.trangThai ? 'status-on' : 'status-off'}">
                                ${d.trangThai ? 'ĐANG BÁN' : 'NGỪNG BÁN'}
                        </span>
                </td>
                <td class="text-center">
                    <button class="btn btn-link text-info p-2" onclick="showEditModal('${d.maDoUong}', '${d.tenDoUong}', '${d.maLoai}', '${d.maCongThuc}', '${d.giaTien}', '${d.moTa}', '${d.hinhAnh}', ${d.trangThai})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-link text-danger p-2" onclick="confirmDelete('${d.maDoUong}', '${d.tenDoUong}')">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="modalDoUong" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <form action="${ctx}/nhanvien/quanlydouong" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="maDoUong" id="mID">
                <input type="hidden" name="hinhAnhOld" id="mHinhOld">

                <div class="modal-header border-0 px-4 pt-4">
                    <h5 class="modal-title fw-bold text-warning" id="modalTitle">THÔNG TIN ĐỒ UỐNG</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body px-4">
                    <div class="row g-3">
                        <div class="col-md-3">
                            <label class="small text-secondary mb-1">Hình ảnh</label>
                            <div class="img-preview-box" onclick="document.getElementById('mFile').click()">
                                <img id="imgPreview" src="${ctx}/uploads/noimg.jpg" style="width: 100%; height: 100%; object-fit: cover;">
                            </div>
                            <input type="file" name="hinhAnhFile" id="mFile" hidden accept="image/*" onchange="previewImage(this)">
                        </div>
                        <div class="col-md-9">
                            <div class="row g-3">
                                <div class="col-md-8">
                                    <label class="small text-secondary mb-1">Tên đồ uống</label>
                                    <input type="text" name="tenDoUong" id="mTen" class="form-control" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="small text-secondary mb-1">Giá (VNĐ)</label>
                                    <input type="number" name="giaTien" id="mGia" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="small text-secondary mb-1">Danh mục</label>
                                    <select name="maLoai" id="mLoai" class="form-select">
                                        <option value="1">Trà sữa</option>
                                        <option value="2">Cà phê</option>
                                        <option value="3">Nước ép</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="small text-secondary mb-1">Công thức</label>
                                    <select name="maCongThuc" id="mCT" class="form-select" required>
                                        <c:forEach var="ct" items="${dsCongThuc}">
                                            <option value="${ct.maCongThuc}">${ct.tenCongThuc}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="small text-secondary mb-1">Mô tả</label>
                            <textarea name="moTa" id="mMoTa" class="form-control" rows="2"></textarea>
                        </div>
                        <div class="col-12">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" name="trangThai" id="mTrangThai" checked>
                                <label class="form-check-label text-secondary ms-2" for="mTrangThai">Đang kinh doanh</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-0 p-4">
                    <button type="submit" class="btn btn-warning w-100 py-2 fw-bold">LƯU THÔNG TIN</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const modalObj = new bootstrap.Modal(document.getElementById('modalDoUong'));

    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = e => {
                document.getElementById('imgPreview').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    function showAddModal() {
        document.getElementById('modalTitle').innerText = "THÊM ĐỒ UỐNG MỚI";
        document.getElementById('mID').value = "";
        document.getElementById('mHinhOld').value = "";
        document.getElementById('imgPreview').src = "${ctx}/uploads/noimg.jpg";
        document.getElementById('mTen').value = "";
        modalObj.show();
    }

    function showEditModal(id, ten, loai, ct, gia, moTa, hinh, trangThai) {
        document.getElementById('modalTitle').innerText = "SỬA ĐỒ UỐNG #" + id;
        document.getElementById('mID').value = id;
        document.getElementById('mTen').value = ten;
        document.getElementById('mGia').value = gia;
        document.getElementById('mLoai').value = loai;
        document.getElementById('mCT').value = ct;
        document.getElementById('mMoTa').value = moTa;
        document.getElementById('mHinhOld').value = hinh;

        const imgPreview = document.getElementById('imgPreview');
        imgPreview.src = (hinh && hinh !== 'null' && hinh !== '') ? "${ctx}/uploads/" + hinh : "${ctx}/uploads/noimg.jpg";

        document.getElementById('mTrangThai').checked = (trangThai === true || trangThai === 'true');
        modalObj.show();
    }

    function confirmDelete(id, ten) {
        if (confirm("Xóa món " + ten + "?")) {
            location.href = "${ctx}/nhanvien/quanlydouong?action=delete&maDoUong=" + id;
        }
    }
</script>
</body>
</html>