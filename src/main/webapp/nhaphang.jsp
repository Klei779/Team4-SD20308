<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>Quản lý nhập hàng</title>

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

        .table {
            color: #fff;
        }

        .table-hover tbody tr:hover {
            background: #1a1a1a;
        }

        td, th {
            border-color: #333 !important;
        }

        .modal-content {
            background: #000 !important;
            border: 1px solid #333;
        }

        .table-wrapper {
            height: 70vh;
            overflow-y: auto;
        }
    </style>
</head>

<body>

<div class="container mt-4">

    <h3 class="text-warning">Quản lý phiếu nhập</h3>

    <!-- TABLE -->
    <div class="card p-3 table-wrapper">

        <table class="table table-hover text-center align-middle">

            <thead>
            <tr>
                <th>MÃ</th>
                <th>NHÂN VIÊN</th>
                <th>NHÀ CUNG CẤP</th>
                <th>NGÀY NHẬP</th>
                <th>TỔNG TIỀN</th>
                <th>THAO TÁC</th>
            </tr>
            </thead>

            <tbody>

            <%
                List<Map<String, Object>> list =
                        (List<Map<String, Object>>) request.getAttribute("list");

                if (list != null) {
                    for (Map<String, Object> p : list) {
            %>

            <tr>
                <td class="text-warning">PN<%= p.get("maPhieu") %></td>
                <td><%= p.get("nhanVien") %></td>
                <td><%= p.get("ncc") %></td>
                <td><%= p.get("ngayNhap") %></td>
                <td><%= p.get("tongTien") %> đ</td>

                <td>
                    <a href="nhaphang?action=detail&id=<%= p.get("maPhieu") %>"
                       class="btn btn-sm btn-outline-light">
                        <i class="bi bi-eye"></i>
                    </a>
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

<!-- MODAL -->
<div class="modal fade" id="detailModal">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content text-white">

            <div class="modal-header">
                <h6 class="text-warning">
                    Chi tiết phiếu nhập #<%= request.getAttribute("maPN") %>
                </h6>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">

                <table class="table table-dark text-center">

                    <tr>
                        <th>TÊN NGUYÊN LIỆU</th>
                        <th>SỐ LƯỢNG</th>
                        <th>ĐƠN GIÁ</th>
                        <th>HSD</th>
                        <th>THÀNH TIỀN</th>
                    </tr>

                    <%
                        List<Map<String, Object>> ctList =
                                (List<Map<String, Object>>) request.getAttribute("ctList");

                        if (ctList != null) {
                            for (Map<String, Object> ct : ctList) {
                    %>

                    <tr>
                        <td><%= ct.get("tenNL") %></td>
                        <td><%= ct.get("soLuong") %></td>
                        <td><%= ct.get("donGia") %></td>
                        <td><%= ct.get("hsd") %></td>
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
    new bootstrap.Modal(document.getElementById('detailModal')).show();
</script>
<%
    }
%>

</body>
</html>