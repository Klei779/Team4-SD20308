<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Poly Coffee - Quản lý hóa đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #000;
            color: #fff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            background: #0f0f0f;
            border: 1px solid #222;
            border-radius: 15px;
        }

        .form-control, .form-select {
            background: #111;
            color: #fff;
            border: 1px solid rgba(51, 51, 51, 0);
        }

        .form-control:focus, .form-select:focus {
            background: #1a1a1a;
            color: #fff;
            border-color: #ffc107;
            box-shadow: none;
        }

        .table {
            color: #fff;
            margin-bottom: 0;
        }

        .table thead th {
            background: #000;
            border-bottom: 2px solid #333;
            color: #888;
            font-size: 13px;
            text-transform: uppercase;
        }

        .table-hover tbody tr:hover {
            background: #161616;
            cursor: pointer;
        }

        td {
            border-color: #222 !important;
            padding: 15px !important;
        }

        .badge-paid {
            background: rgba(34, 197, 94, 0.2);
            color: #22c55e;
            border: 1px solid #22c55e;
            padding: 5px 12px;
        }

        .badge-unpaid {
            background: rgba(239, 68, 68, 0.2);
            color: #ef4444;
            border: 1px solid #ef4444;
            padding: 5px 12px;
        }

        .modal-content {
            background: #fff;
            color: #000;
            border-radius: 20px;
            border: none;
        }

        .item-img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 10px;
            border: 1px solid #eee;
        }

        .table-wrapper {
            flex: 1;
            overflow-y: auto;
            scrollbar-width: none;
        }

        .table-wrapper::-webkit-scrollbar {
            display: none;
        }

        .container-custom {
            height: 100vh;
            display: flex;
            flex-direction: column;
            padding-bottom: 20px;
        }

        .form-control::placeholder {
            color: #ffffff !important;
            opacity: 1;
        }

        .form-control::-webkit-input-placeholder {
            color: #ffffff !important;
        }
        .form-control:-ms-input-placeholder {
            color: #ffffff !important;
        }

        .form-control::placeholder {
            color: #6c757d !important;
            opacity: 0.6 !important;
        }

        .form-control::-webkit-input-placeholder {
            color: #6c757d !important;
            opacity: 0.6 !important;
        }

        .form-control::-moz-placeholder {
            color: #6c757d !important;
            opacity: 0.6 !important;
        }

        .form-control:-ms-input-placeholder {
            color: #6c757d !important;
            opacity: 0.6 !important;
        }

        .custom-placeholder::placeholder {
            color: #6c757d !important;
            opacity: 0.6 !important;
        }

        .form-control {
            background: #111;
            color: #ffffff !important;
            border: 1px solid #333;
        }

        .form-control::placeholder {
            color: #888888 !important;
            opacity: 0.5 !important;
        }

        .form-control::-webkit-input-placeholder {
            color: #888888 !important;
            opacity: 0.5 !important;
        }

        .form-control:focus {
            background: #1a1a1a;
            color: #ffffff !important;
            border-color: #ffc107;
            box-shadow: none;
        }
    </style>
</head>
<body>

<div class="container mt-4 container-custom">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-warning mb-0">QUẢN LÝ HÓA ĐƠN</h3>
            <p class="text-secondary small">Dữ liệu từ hệ thống Poly Coffee</p>
        </div>
        <button class="btn btn-outline-warning btn-sm" onclick="location.href='hoadon'">
            <i class="bi bi-arrow-clockwise"></i> Làm mới
        </button>
    </div>

    <div class="card p-3 mb-3">
        <form method="get" action="hoadon" class="row g-2">
            <div class="col-md-5">
                <div class="input-group">
                    <span class="input-group-text bg-transparent border-secondary text-secondary">
                        <i class="bi bi-search"></i>
                    </span>
                    <input type="text" name="keyword"
                           class="form-control text-secondary border-secondary"
                           placeholder="Nhập tên nhân viên - trạng thái (thanh toán/chờ)..."
                           style="color: white;">
                </div>
            </div>
            <div class="col-md-4">
                <select name="filter" class="form-select border-secondary">
                    <option value="all" <%= "all".equals(request.getAttribute("filter")) ? "selected" : "" %>>Tất cả
                        thời gian
                    </option>
                    <option value="today" <%= "today".equals(request.getAttribute("filter")) ? "selected" : "" %>>Hôm
                        nay
                    </option>
                    <option value="7days" <%= "7days".equals(request.getAttribute("filter")) ? "selected" : "" %>>7 ngày
                        gần nhất
                    </option>
                    <option value="month" <%= "month".equals(request.getAttribute("filter")) ? "selected" : "" %>>Trong
                        tháng này
                    </option>
                </select>
            </div>
            <div class="col-md-3">
                <button type="submit" class="btn btn-warning w-100 fw-bold text-dark">LỌC DỮ LIỆU</button>
            </div>
        </form>
    </div>

    <div class="card table-wrapper">
        <table class="table table-dark table-hover align-middle text-center">
            <thead>
            <tr>
                <th>Mã HĐ</th>
                <th>Nhân viên</th>
                <th>Ngày tạo</th>
                <th>Số món</th>
                <th>Tổng cộng</th>
                <th>Trạng thái</th>
                <th>Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("list");
                if (list != null && !list.isEmpty()) {
                    for (Map<String, Object> hd : list) {
            %>
            <tr>
                <td class="text-warning fw-bold">#<%= hd.get("maHoaDon") %>
                </td>
                <td><i class="bi bi-person-badge me-1"></i> <%= hd.get("nhanVien") %>
                </td>
                <td class="small text-secondary"><%= hd.get("ngayTao") %>
                </td>
                <td><span class="badge bg-secondary"><%= hd.get("soMon") %> món</span></td>
                <td class="fw-bold"><%= String.format("%,.0f", Double.parseDouble(hd.get("tongTien").toString())) %>đ
                </td>
                <td>
                        <span class="badge rounded-pill <%= (boolean)hd.get("trangThai") ? "badge-paid" : "badge-unpaid" %>">
                            <%= (boolean) hd.get("trangThai") ? "Đã thanh toán" : "Chờ xử lý" %>
                        </span>
                </td>
                <td>
                    <a href="hoadon?action=detail&id=<%= hd.get("maHoaDon") %>&keyword=<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>&filter=<%= request.getAttribute("filter") != null ? request.getAttribute("filter") : "all" %>"
                       class="btn btn-sm btn-dark border-secondary">
                        <i class="bi bi-eye-fill text-warning"></i>
                    </a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="py-5 text-secondary">Không có dữ liệu hóa đơn nào.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="detailModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-4 shadow-lg">
            <div class="text-center mb-3">
                <h3 class="fw-bold mb-0">POLY COFFEE</h3>
                <p class="text-muted small">123 Trịnh Văn Bô, Nam Từ Liêm, Hà Nội</p>
                <div style="border-top: 2px dashed #ddd; margin: 15px 0;"></div>
            </div>

            <div class="row small mb-3">
                <div class="col-6 text-muted">Mã hóa đơn:</div>
                <div class="col-6 text-end fw-bold">#<%= request.getAttribute("maHD") %>
                </div>
                <div class="col-6 text-muted">Nhân viên:</div>
                <div class="col-6 text-end"><%= request.getAttribute("nhanVien") %>
                </div>
                <div class="col-6 text-muted">Thời gian:</div>
                <div class="col-6 text-end"><%= request.getAttribute("thoiGian") %>
                </div>
            </div>

            <div class="item-list mb-3" style="max-height: 250px; overflow-y: auto;">
                <%
                    List<Map<String, Object>> ctList = (List<Map<String, Object>>) request.getAttribute("ctList");
                    double tamTinh = 0;
                    if (ctList != null) {
                        for (Map<String, Object> ct : ctList) {
                            tamTinh += Double.parseDouble(ct.get("thanhTien").toString());
                            // Lấy tên ảnh từ Database (Ví dụ: sinhtomangcau.jpg)
                            String img = (ct.get("hinhAnh") != null) ? ct.get("hinhAnh").toString() : "default.png";
                %>
                <div class="d-flex align-items-center justify-content-between mb-3">
                    <div class="d-flex align-items-center">
                        <img src="uploads/<%= img %>" class="item-img me-3"
                             onerror="this.src='https://placehold.co/50x50?text=Poly'">
                        <div>
                            <div class="fw-bold" style="font-size: 14px;"><%= ct.get("tenDoUong") %>
                            </div>
                            <div class="text-muted small">x<%= ct.get("soLuong") %>
                                | <%= String.format("%,.0f", Double.parseDouble(ct.get("donGia").toString())) %>đ
                            </div>
                        </div>
                    </div>
                    <span class="fw-bold"><%= String.format("%,.0f", Double.parseDouble(ct.get("thanhTien").toString())) %>đ</span>
                </div>
                <%
                        }
                    }
                %>
            </div>

            <div style="border-top: 2px dashed #ddd; margin: 15px 0;"></div>

            <%
                double vat = tamTinh * 0.08;
                double tongCong = tamTinh + vat;
            %>
            <div class="row mb-1">
                <div class="col-6 text-muted">Tạm tính:</div>
                <div class="col-6 text-end"><%= String.format("%,.0f", tamTinh) %> đ</div>
            </div>
            <div class="row mb-2">
                <div class="col-6 text-muted">VAT (8%):</div>
                <div class="col-6 text-end"><%= String.format("%,.0f", vat) %> đ</div>
            </div>
            <div class="row mb-3 fs-5 fw-bold text-danger">
                <div class="col-6">TỔNG CỘNG:</div>
                <div class="col-6 text-end"><%= String.format("%,.0f", tongCong) %> đ</div>
            </div>

            <div class="text-center mt-3">
                <p class="mb-1 small">Quét mã để xem hóa đơn online</p>
                <img src="https://api.qrserver.com/v1/create-qr-code/?size=80x80&data=POLYCOFFEE_HD<%= request.getAttribute("maHD") %>"
                     class="mb-3">
                <button type="button" class="btn btn-dark border-secondary w-100 py-2 fw-bold text-warning"
                        data-bs-dismiss="modal">ĐÓNG HÓA ĐƠN
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%-- Tự động mở Modal --%>
<%
    Boolean openModal = (Boolean) request.getAttribute("openModal");
    if (openModal != null && openModal) {
%>
<script>
    var myModal = new bootstrap.Modal(document.getElementById('detailModal'));
    myModal.show();
</script>
<% } %>

</body>
</html>