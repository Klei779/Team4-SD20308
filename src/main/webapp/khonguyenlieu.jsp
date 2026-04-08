<%--
  Created by IntelliJ IDEA.
  User: lannn
  Date: 3/22/2026
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kho nguyên liệu & Công thức</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>

        html, body {
            height: auto;
            overflow-x: hidden;
            background-color: #111;
            color: white;
            scrollbar-width: thin;
            scrollbar-color: #ffc107 #111;
        }

        .container-main {
            display: flex;
            flex-direction: column;
            padding: 20px;
            min-height: 100vh;
        }


        .header-fixed {
            flex-shrink: 0;
            background-color: #111;
            position: sticky;
            top: 0;
            z-index: 100;
            padding-bottom: 15px;
            border-bottom: 1px solid #333;
        }


        .card-custom {
            background-color: #252525;
            border-radius: 15px;
            padding: 18px;
            transition: all 0.3s ease;
            border: 1px solid #383838;
            height: 100%;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
        }

        .card-custom:hover {
            transform: translateY(-5px);
            background-color: #2d2d2d;
            border-color: #00b679;
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.5);
        }


        .card-custom.low {
            border: 1px solid #ff4d5e;
            background-color: #2a1b1b;
        }

        .progress {
            background-color: #333;
            border-radius: 10px;
        }


        .content-scroll {
            flex-grow: 1;
            padding: 25px 0;
        }


        .iframe-container {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 2px dashed #333;
            padding-bottom: 50px;
        }

        .custom-iframe {
            width: 100%;
            height: 800px;
            border: 1px solid #333;
            border-radius: 15px;
            background-color: #161616;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
        }


        .form-control, .form-select {
            background-color: #222 !important;
            border: 1px solid #444 !important;
            color: white !important;
        }

        .form-control:focus {
            border-color: #ffc107 !important;
            box-shadow: none !important;
        }
    </style>
</head>
<body class="p-3">

<div class="container-main">

    <div class="header-fixed">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="fw-bold text-uppercase"><i class="bi bi-box-seam me-2"></i>Kho nguyên liệu</h3>
            <button class="btn btn-warning fw-bold" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="bi bi-plus-circle me-1"></i> Thêm mới
            </button>
        </div>

        <c:set var="TONG_SUC_CHUA" value="100000"/>
        <c:set var="tongTonKho" value="0"/>
        <c:forEach var="item" items="${list}">
            <c:set var="tongTonKho" value="${tongTonKho + item.soLuongTon}"/>
        </c:forEach>
        <c:set var="phanTramKho" value="${(tongTonKho * 100) / TONG_SUC_CHUA}"/>

        <c:choose>
            <c:when test="${phanTramKho > 80}">
                <c:set var="totalBarColor" value="bg-danger"/>
                <c:set var="totalTextColor" value="text-danger"/>
            </c:when>
            <c:when test="${phanTramKho >= 40}">
                <c:set var="totalBarColor" value="bg-warning"/>
                <c:set var="totalTextColor" value="text-warning"/>
            </c:when>
            <c:otherwise>
                <c:set var="totalBarColor" value="bg-success"/>
                <c:set var="totalTextColor" value="text-success"/>
            </c:otherwise>
        </c:choose>

        <div class="mb-3">
            <div class="d-flex justify-content-between mb-1">
                <small class="text-secondary">Sức chứa hệ thống: <b class="text-white">${tongTonKho}</b>
                    / ${TONG_SUC_CHUA}</small>
                <small class="${totalTextColor} fw-bold" style="font-size: 1.1rem;">
                    ${String.format("%.1f", phanTramKho)}%
                </small>
            </div>
            <div class="progress" style="height: 12px;">
                <c:set var="percent" value="${phanTramKho > 100 ? 100 : phanTramKho}"/>

                <div class="progress-bar ${totalBarColor}"
                     style="width: ${percent}%"></div>
            </div>
        </div>

        <div class="row g-2 mb-2">
            <c:if test="${phanTramKho >= 90}">
                <div class="col-md-6">
                    <div class="badge bg-danger py-2 mb-0 border-0 shadow-sm">
                        <i class="bi bi-exclamation-triangle-fill"></i> <b>CẢNH BÁO TRÀN KHO!</b>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty sapHet}">
                <div class="col-md-12"> <!-- Tăng độ rộng lên col-12 để hiển thị tên hàng dài hơn -->
                    <div class="badge bg-danger py-2 mb-0 border-0 shadow-sm text-light d-flex align-items-center">
                        <i class="bi bi-cart-dash me-2"></i>
                        <div>
                            <b>SẮP HẾT HÀNG (${sapHet.size()}):</b>
                            <span class="ms-1">
                        <c:forEach var="item" items="${sapHet}" varStatus="status">
                            ${item.tenNguyenLieu}<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    </span>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <form method="get" action="khonguyenlieu" class="mt-3">
            <div class="row">
                <div class="col-md-4">
                    <select name="maLoai" class="form-select shadow-sm" onchange="this.form.submit()">
                        <option value="0" ${selectedLoai == '0' || empty selectedLoai ? 'selected' : ''}>Tất cả loại
                            nguyên liệu
                        </option>
                        <c:forEach var="l" items="${listLoai}">
                            <option value="${l.maLoaiNguyenLieu}" ${selectedLoai == l.maLoaiNguyenLieu.toString() ? 'selected' : ''}>
                                    ${l.tenLoaiNguyenLieu}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
    </div>

    <div class="content-scroll">
        <div class="row">
            <c:forEach var="nl" items="${list}">
                <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
                    <c:set var="percentItem" value="${(nl.soLuongTon * 100) / 100000}"/>
                    <c:set var="isLow" value="${nl.soLuongTon < nl.soLuongToiThieu}"/>

                    <div class="card-custom ${isLow ? 'low' : ''}">
                        <div class="d-flex justify-content-between align-items-start mb-2">
                            <h5 class="text-truncate fw-bold mb-0" title="${nl.tenNguyenLieu}">${nl.tenNguyenLieu}</h5>
                            <c:if test="${isLow}">
                                <span class="badge bg-danger">Hết/Sắp hết</span>
                            </c:if>
                        </div>

                        <h3 class="my-2 fw-bold text-warning">${nl.soLuongTon} <small
                                class="text-secondary fs-6">${nl.donVi}</small></h3>

                        <div class="d-flex justify-content-between small mb-1">
                            <span class="text-secondary">Tối thiểu: <b
                                    class="text-white">${nl.soLuongToiThieu}</b></span>
                            <span class="${isLow ? 'text-danger' : 'text-success'} fw-bold">${String.format("%.1f", percentItem)}%</span>
                        </div>

                        <div class="progress mb-3">
                            <div class="progress-bar ${isLow ? 'bg-danger' : 'bg-success'}"
                                 style="width: ${percentItem > 100 ? 100 : percentItem}%"></div>
                        </div>

                        <div class="d-flex justify-content-between gap-2">
                            <button class="btn btn-sm btn-outline-success flex-fill"
                                    onclick="openNhap(${nl.maNguyenLieu})" title="Nhập kho">
                                <i class="bi bi-plus-lg"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-warning flex-fill"
                                    onclick="openEdit('${nl.maNguyenLieu}','${nl.tenNguyenLieu}','${nl.soLuongTon}','${nl.donVi}','${nl.soLuongToiThieu}','${nl.maLoaiNguyenLieu}','${nl.ghiChu}')">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <form method="post" action="khonguyenlieu" class="flex-fill"
                                  onsubmit="return confirm('Bạn có chắc muốn xóa?')">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${nl.maNguyenLieu}">
                                <button class="btn btn-sm btn-outline-danger w-100"><i class="bi bi-trash3"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="iframe-container">
        <div class="d-flex align-items-center mb-3">
            <h3 class="text-warning fw-bold"><i class="bi bi-receipt-cutoff me-2"></i> QUẢN LÝ CÔNG THỨC PHA CHẾ</h3>
        </div>
        <iframe src="congthuc" class="custom-iframe shadow-lg"></iframe>
    </div>
</div>

<div class="modal fade" id="addModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-white border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title fw-bold">Thêm nguyên liệu mới</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="add">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Tên nguyên liệu</label>
                        <input class="form-control" name="ten" placeholder="Vd: Hạt cà phê Robusta" required>
                    </div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <label class="form-label">Số lượng hiện có</label>
                            <input class="form-control" name="soLuong" type="number" value="0" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Đơn vị tính</label>
                            <input class="form-control" name="donVi" placeholder="Kg, Lít, Túi..." required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <label class="form-label">Định mức tối thiểu</label>
                            <input class="form-control" name="toiThieu" type="number" value="10" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Loại nguyên liệu</label>
                            <select name="maLoai" class="form-select">
                                <c:forEach var="l" items="${listLoai}">
                                    <option value="${l.maLoaiNguyenLieu}">${l.tenLoaiNguyenLieu}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ghi chú</label>
                        <textarea class="form-control" name="ghiChu" rows="2"></textarea>
                    </div>
                </div>
                <div class="modal-footer border-secondary">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-warning fw-bold px-4">Lưu dữ liệu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-dark text-white border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title fw-bold text-warning">Chỉnh sửa thông tin</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" id="id">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Tên nguyên liệu</label>
                        <input class="form-control" name="ten" id="ten" required>
                    </div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <label class="form-label">Số lượng</label>
                            <input class="form-control" name="soLuong" id="soLuong" type="number" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Đơn vị</label>
                            <input class="form-control" name="donVi" id="donVi" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <label class="form-label">Số lượng tối thiểu</label>
                            <input class="form-control" name="toiThieu" id="toiThieu" type="number" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Mã loại</label>
                            <input class="form-control" name="maLoai" id="maLoai" readonly>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ghi chú</label>
                        <input class="form-control" name="ghiChu" id="ghiChu">
                    </div>
                </div>
                <div class="modal-footer border-secondary">
                    <button type="submit" class="btn btn-success fw-bold">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="nhapModal" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content bg-dark text-white border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title">Nhập thêm kho</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="khonguyenlieu">
                <input type="hidden" name="action" value="nhap">
                <input type="hidden" name="id" id="nhap-id">
                <div class="modal-body text-center">
                    <label class="form-label">Số lượng nhập thêm</label>
                    <input class="form-control form-control-lg text-center" name="soLuongNhap" type="number" value="1"
                           min="1" required>
                </div>
                <div class="modal-footer border-secondary justify-content-center">
                    <button type="submit" class="btn btn-success px-4">Xác nhận nhập</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 1055">
    <div id="liveToast" class="toast align-items-center text-white border-0" role="alert" aria-live="assertive"
         aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body" id="toast-body"></div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const TONG_SUC_CHUA = 100000;
    let currentTongTonKho = ${tongTonKho};
    let oldQty = 0;

    function validateStock(inputSoLuongMoi, soLuongCu = 0) {
        const change = parseInt(inputSoLuongMoi) - parseInt(soLuongCu);
        if (currentTongTonKho + change > TONG_SUC_CHUA) {
            alert("LỖI: Tổng kho sẽ bị tràn! Sức chứa còn lại: " + (TONG_SUC_CHUA - currentTongTonKho));
            return false;
        }
        return true;
    }

    function openEdit(id, ten, soLuong, donVi, toiThieu, maLoai, ghiChu) {
        oldQty = soLuong;
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

    document.addEventListener("DOMContentLoaded", function () {
        document.querySelector('#nhapModal form').onsubmit = function () {
            const nhapVal = this.querySelector('[name="soLuongNhap"]').value;
            return validateStock(nhapVal, 0);
        };

        document.querySelector('#editModal form').onsubmit = function () {
            const editVal = document.getElementById("soLuong").value;
            return validateStock(editVal, oldQty);
        };

        document.querySelector('#addModal form').onsubmit = function () {
            const addVal = this.querySelector('[name="soLuong"]').value;
            return validateStock(addVal, 0);
        };

        const toastEl = document.getElementById('liveToast');
        const toastBody = document.getElementById('toast-body');

        <% if (session.getAttribute("message") != null) { %>
        toastBody.innerText = '<%= session.getAttribute("message") %>';
        toastEl.classList.add('bg-success');
        new bootstrap.Toast(toastEl).show();
        <% session.removeAttribute("message"); %>
        <% } else if (session.getAttribute("error") != null) { %>
        toastBody.innerText = '<%= session.getAttribute("error") %>';
        toastEl.classList.add('bg-danger');
        new bootstrap.Toast(toastEl).show();
        <% session.removeAttribute("error"); %>
        <% } %>
    });
</script>

</body>
</html>