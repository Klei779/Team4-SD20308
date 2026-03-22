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
            background: #000 !important;
            border: 1px solid #333;
            box-shadow: 0 0 25px rgba(0,0,0,0.8);
        }

        th, td {
            white-space: nowrap;
        }
    </style>
</head>

<body>

<div class="container mt-4">

    <h3 class="text-white">
        Quản lý hóa đơn
    </h3>
    <p class="text-secondary">Giám sát toàn bộ giao dịch</p>

    <div class="card p-3 mb-3">
        <form method="get" action="hoadon" class="row g-2">

            <div class="col-md-4">
                <input type="text" name="keyword" class="form-control"
                       placeholder="Nhập mã NV - trạng thái (true/false)"
                       value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
            </div>

            <div class="col-md-3">
                <select name="filter" class="form-select">
                    <option value="all">Tất cả</option>
                    <option value="today">Hôm nay</option>
                    <option value="7days">7 ngày</option>
                    <option value="month">Tháng</option>
                </select>
            </div>

            <div class="col-md-2">
                <button class="btn btn-warning w-100">
                    <i class="bi bi-search"></i> Tìm
                </button>
            </div>

        </form>
    </div>

    <!-- TABLE -->
    <div class="card p-3">
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

<!-- ================= MODAL ================= -->
<div class="modal fade" id="detailModal">
    <div class="modal-dialog modal-dialog-centered modal-md">
        <div class="modal-content text-white rounded-4">

            <div class="modal-header border-0 pb-1">
                <h6 class="modal-title text-warning">
                    Hóa đơn #<%= request.getAttribute("maHD") %>
                </h6>
                <button type="button" class="btn-close btn-close-white"
                        data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body p-2">

                <table class="table table-dark table-sm text-center mb-2">
                    <tr>
                        <th>Mã HĐ</th>
                        <th>Mã ĐU</th>
                        <th>Đơn giá</th>
                        <th>SL</th>
                        <th>Thành tiền</th>
                    </tr>

                    <%
                        List<Map<String, Object>> ctList =
                                (List<Map<String, Object>>) request.getAttribute("ctList");

                        int tong = 0;

                        if (ctList != null) {
                            for (Map<String, Object> ct : ctList) {
                                tong += (int) ct.get("thanhTien");
                    %>
                    <tr>
                        <td><%= ct.get("maHoaDon") %></td>
                        <td><%= ct.get("maDoUong") %></td>
                        <td><%= ct.get("donGia") %></td>
                        <td><%= ct.get("soLuong") %></td>
                        <td><%= ct.get("thanhTien") %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>

            </div>

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