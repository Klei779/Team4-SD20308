<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="entity.*" %>
<html>
<head>
    <title>Poly Coffee - Quản lý nhập hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: #000; color: #fff; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .card { background: #0f0f0f; border: 1px solid #222; border-radius: 15px; }
        .form-control, .form-select { background: #111; color: #fff; border: 1px solid #333; }
        .form-control:focus, .form-select:focus { background: #1a1a1a; border-color: #ffc107; box-shadow: none; color: #fff; }
        .table { color: #fff; margin-bottom: 0; }
        .table thead th { background: #000; border-bottom: 2px solid #333; color: #888; font-size: 13px; text-transform: uppercase; }
        .table-hover tbody tr:hover { background: #161616; cursor: pointer; }
        td { border-color: #222 !important; padding: 12px !important; }
        .btn-warning { background-color: #ffc107; color: black; font-weight: bold; }
        .modal-content-custom { background: #fff; color: #000; border-radius: 20px; padding: 20px; }
        .nguyen-lieu-row { display: flex; gap: 10px; margin-bottom: 8px; }
        .nguyen-lieu-row input, .nguyen-lieu-row select { flex: 1; }
    </style>
</head>
<body>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-warning mb-0">QUẢN LÝ NHẬP HÀNG</h3>
        <div>
            <button class="btn btn-warning fw-bold me-2" data-bs-toggle="modal" data-bs-target="#createPhieuModal">
                + Thêm phiếu nhập
            </button>
            <button class="btn btn-outline-warning btn-sm" onclick="location.href='nhaphang'">
                <i class="bi bi-arrow-clockwise"></i> Làm mới
            </button>
        </div>
    </div>

    <div class="card table-wrapper">
        <table class="table table-dark table-hover align-middle text-center">
            <thead>
            <tr>
                <th>Mã phiếu</th>
                <th>Nhân viên</th>
                <th>NCC</th>
                <th>Ngày nhập</th>
                <th>Tổng tiền</th>
                <th>Ghi chú</th>
                <th>Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<PhieuNhapKho> list = (List<PhieuNhapKho>) request.getAttribute("list");
                Map<Integer,String> mapNV = (Map<Integer,String>) request.getAttribute("mapNhanVien");
                Map<Integer,String> mapNCC = (Map<Integer,String>) request.getAttribute("mapNCC");
                if(list != null) {
                    for(PhieuNhapKho p : list) {
            %>
            <tr>
                <td><%= p.getMaPhieuNhapKho() %></td>
                <td><%= mapNV.get(p.getMaNguoiDung()) %></td>
                <td><%= mapNCC.get(p.getMaNCC()) %></td>
                <td><%= p.getNgayNhapKho() %></td>
                <td><%= String.format("%,d", p.getTongTien()) %> đ</td>
                <td>...</td>
                <td>
                    <a href="nhaphang?action=detail&id=<%= p.getMaPhieuNhapKho() %>" class="btn btn-warning btn-sm">Xem</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<!-- MODAL TẠO PHIẾU NHẬP MỚI -->
<div class="modal fade" id="createPhieuModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content modal-content-custom">
            <div class="text-center mb-3">
                <h3 class="fw-bold mb-0">THÊM PHIẾU NHẬP</h3>
                <p class="text-muted small">Nhập kho nguyên liệu</p>
                <div style="border-top: 2px dashed #ddd; margin: 15px 0;"></div>
            </div>

            <form method="post" action="nhaphang" id="createPhieuForm">
                <!-- Nhà cung cấp -->
                <div class="mb-3">
                    <label class="form-label">Nhà cung cấp</label>
                    <select name="maNCC" class="form-select">
                        <%
                            List<NhaCungCap> nccList = (List<NhaCungCap>) request.getAttribute("nccList");
                            if(nccList != null) {
                                for(NhaCungCap ncc : nccList) {
                        %>
                        <option value="<%= ncc.getMaNhaCungCap() %>"><%= ncc.getTenNhaCungCap() %></option>
                        <% } } %>
                    </select>
                </div>

                <!-- Ghi chú -->
                <div class="mb-3">
                    <label class="form-label">Ghi chú</label>
                    <textarea name="ghiChu" class="form-control"></textarea>
                </div>

                <!-- Nguyên liệu -->
                <div id="nguyenLieuWrapper">
                    <div class="nguyen-lieu-row">
                        <select name="maNL[]" class="form-select">
                            <%
                                List<NguyenLieu> nlList = (List<NguyenLieu>) request.getAttribute("nlList");
                                if(nlList != null) {
                                    for(NguyenLieu nl : nlList) {
                            %>
                            <option value="<%= nl.getMaNguyenLieu() %>"><%= nl.getTenNguyenLieu() %></option>
                            <% } } %>
                        </select>
                        <input type="number" name="soLuong[]" class="form-control" placeholder="Số lượng">
                        <input type="number" name="donGia[]" class="form-control" placeholder="Đơn giá">
                        <input type="date" name="hansd[]" class="form-control" placeholder="HSD">
                    </div>
                </div>

                <div class="d-flex gap-2 mb-3">
                    <button type="button" id="addRowBtn" class="btn btn-warning fw-bold">+ Thêm dòng</button>
                    <button type="submit" class="btn btn-warning fw-bold">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- MODAL CHI TIẾT PHIẾU NHẬP -->
<div class="modal fade" id="detailModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content modal-content-custom">
            <div class="modal-header">
                <h5 class="modal-title fw-bold">Chi tiết phiếu nhập</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><strong>Mã phiếu:</strong> <span id="modalMaPhieu"><%= request.getAttribute("maPhieu") %></span></p>
                <p><strong>Nhân viên:</strong> <span id="modalNhanVien"><%= request.getAttribute("nhanVien") %></span></p>
                <p><strong>Nhà cung cấp:</strong> <span id="modalNCC"><%= request.getAttribute("ncc") %></span></p>
                <p><strong>Ngày nhập:</strong> <span id="modalNgayNhap"><%= request.getAttribute("ngayNhap") %></span></p>
                <table class="table table-dark table-hover text-center">
                    <thead>
                    <tr>
                        <th>Nguyên liệu</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Map<String,Object>> ctList = (List<Map<String,Object>>) request.getAttribute("ctList");
                        if(ctList != null) {
                            for(Map<String,Object> item : ctList) {
                    %>
                    <tr>
                        <td><%= item.get("tenNL") %></td>
                        <td><%= item.get("soLuong") %></td>
                        <td><%= String.format("%,d", item.get("donGia")) %> đ</td>
                        <td><%= String.format("%,d", item.get("thanhTien")) %> đ</td>
                    </tr>
                    <% } } %>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <strong class="me-auto">Tổng tiền: <%= request.getAttribute("tongTienHD") %> đ</strong>
                <button type="button" class="btn btn-warning fw-bold" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('addRowBtn').addEventListener('click', function() {
        var wrapper = document.getElementById('nguyenLieuWrapper');
        var row = wrapper.querySelector('.nguyen-lieu-row').cloneNode(true);
        row.querySelectorAll('input').forEach(input => input.value = '');
        wrapper.appendChild(row);
    });

    // Mở modal chi tiết nếu cần
    <% Boolean openModal = (Boolean) request.getAttribute("openModal");
       if(openModal != null && openModal) { %>
    var modalEl = document.getElementById('detailModal');
    if(modalEl) {
        var myModal = new bootstrap.Modal(modalEl);
        myModal.show();
    }
    <% } %>
</script>

</body>
</html>