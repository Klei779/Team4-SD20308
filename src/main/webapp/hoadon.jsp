<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>Quản lý hóa đơn</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #000;
            color: #fff;
        }

        .card {
            background: #0f0f0f;
            border: 1px solid #222;
            border-radius: 12px;
        }

        .form-control, .form-select {
            background: #111;
            color: #fff;
            border: 1px solid #333;
        }

        .form-control::placeholder {
            color: #888;
        }

        .table {
            color: #fff;
        }

        .table thead {
            background: #111;
        }

        .table-hover tbody tr:hover {
            background: #1a1a1a;
        }

        td, th {
            border-color: #333 !important;
        }

        .badge-paid {
            background: #22c55e;
        }

        .badge-unpaid {
            background: #ef4444;
        }

        .modal-content {
            border: 1px solid #333;
            box-shadow: 0 0 25px rgba(0,0,0,0.8);
        }

        th, td {
            white-space: nowrap;
        }

        .table thead th {
            position: sticky;
            top: -20px;
            background: #000 !important;
            color: #fff !important;
            padding-top: 8px;
            padding-bottom: 8px;
        }

        html, body {
            height: 100%;
            overflow: hidden;
        }

        .container-custom {
            height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .table-wrapper {
            flex: 1;
            position: relative;
            overflow-y: auto;
            scrollbar-width: none;
            -ms-overflow-style: none;
        }

        .table-wrapper::-webkit-scrollbar {
            display: none;
        }

    </style>
</head>

<body>

<div class="container mt-4 container-custom">

    <h3 class="text-white">
        QUẢN LÝ HÓA ĐƠN
    </h3>
    <p class="text-secondary">Giám sát toàn bộ giao dịch</p>

    <div class="card p-3 mb-3">
        <form method="get" action="hoadon" class="row g-2">

            <div class="col-md-4">
                <input type="text" name="keyword" class="form-control"
                       placeholder="Nhập mã NV (1, 2, 3) - trạng thái (true/false)"
                       value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
            </div>

            <div class="col-md-3">
                <select name="filter" class="form-select">
                    <option value="all" <%= "all".equals(request.getAttribute("filter")) ? "selected" : "" %>>Tất cả</option>
                    <option value="today" <%= "today".equals(request.getAttribute("filter")) ? "selected" : "" %>>Hôm nay</option>
                    <option value="7days" <%= "7days".equals(request.getAttribute("filter")) ? "selected" : "" %>>7 ngày</option>
                    <option value="month" <%= "month".equals(request.getAttribute("filter")) ? "selected" : "" %>>Tháng</option>
                </select>
            </div>

            <div class="col-md-2">
                <button class="btn btn-warning w-100">
                    <i class="bi bi-search"></i> Tìm
                </button>
            </div>

        </form>
    </div>

    <!-- bảng -->
    <div class="card p-3 table-wrapper">
        <table class="table table-hover text-center align-middle">

            <thead>
            <tr>
                <th>MÃ HĐ</th>
                <th>NHÂN VIÊN</th>
                <th>THỜI GIAN</th>
                <th>MÓN</th>
                <th>TỔNG TIỀN</th>
                <th>TRẠNG THÁI</th>
                <th>THAO TÁC</th>
            </tr>
            </thead>

            <tbody>
            <%
                List<Map<String, Object>> list =
                        (List<Map<String, Object>>) request.getAttribute("list");

                if (list != null && !list.isEmpty()) {
                    for (Map<String, Object> hd : list) {
            %>
            <tr>
                <td class="text-warning fw-bold">HD<%= hd.get("maHoaDon") %></td>
                <td><%= hd.get("nhanVien") %></td>
                <td><%= hd.get("ngayTao") %></td>
                <td><%= hd.get("soMon") %></td>
                <td><%= hd.get("tongTien") %> đ</td>
                <td>
                    <span class="badge <%= (boolean)hd.get("trangThai") ? "badge-paid" : "badge-unpaid" %>">
                        <%= (boolean)hd.get("trangThai") ? "Đã TT" : "Chưa TT" %>
                    </span>
                </td>
                <td>
                    <a href="hoadon?action=detail&id=<%= hd.get("maHoaDon") %>"
                       class="btn btn-sm btn-outline-light">
                        <i class="bi bi-eye"></i>
                    </a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="text-secondary">Không có dữ liệu</td>
            </tr>
            <%
                }
            %>
            </tbody>

        </table>
    </div>

</div>

<!--modal-->
<div class="modal fade" id="detailModal">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-4" style="background:#fff;color:#000;border-radius:12px;max-width:420px;margin:auto">

            <!-- HEADER -->
            <h4 class="text-center mb-1">PolyCoffe</h4>
            <p class="text-center" style="font-size:12px;">Hệ thống quản lý nhà hàng</p>
            <hr>

            <!-- INFO -->
            <div class="d-flex justify-content-between">
                <span>Mã HĐ:</span>
                <span>HD<%= request.getAttribute("maHD") %></span>
            </div>

            <div class="d-flex justify-content-between">
                <span>Nhân viên:</span>
                <span><%= request.getAttribute("nhanVien") %></span>
            </div>

            <div class="d-flex justify-content-between">
                <span>Thời gian:</span>
                <span><%= request.getAttribute("thoiGian") %></span>
            </div>

            <hr>

            <!-- LIST ITEM -->
            <%
                List<Map<String, Object>> ctList =
                        (List<Map<String, Object>>) request.getAttribute("ctList");

                int tong = 0;

                if (ctList != null) {
                    for (Map<String, Object> ct : ctList) {
                        int thanhTien = (int) ct.get("thanhTien");
                        tong += thanhTien;
            %>
            <div class="d-flex justify-content-between">
                <span>Đồ uống <%= ct.get("maDoUong") %> x<%= ct.get("soLuong") %></span>
                <span><%= thanhTien %> đ</span>
            </div>
            <%
                    }
                }
            %>

            <hr>

            <!-- TOTAL -->
            <%
                int vat = (int)(tong * 0.08);
                int total = tong + vat;
            %>

            <div class="d-flex justify-content-between">
                <span>Tạm tính:</span>
                <span><%= tong %> đ</span>
            </div>

            <div class="d-flex justify-content-between">
                <span>VAT (8%):</span>
                <span><%= vat %> đ</span>
            </div>

            <div class="d-flex justify-content-between fw-bold">
                <span>Tổng:</span>
                <span><%= total %> đ</span>
            </div>

            <hr>

            <p class="text-center fw-bold text-success">Đã thanh toán</p>
            <p class="text-center">Cảm ơn quý khách!</p>

            <button class="btn btn-secondary w-100 mt-2" data-bs-dismiss="modal">Đóng</button>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%
    Boolean openModal = (Boolean) request.getAttribute("openModal");
    if (openModal != null && openModal) {
%>
<script>
    var myModal = new bootstrap.Modal(document.getElementById('detailModal'));
    myModal.show();
</script>
<%
    }
%>

</body>
</html>