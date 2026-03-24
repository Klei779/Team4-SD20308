<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, entity.*" %>

<html>
<head>
    <title>Tạo hóa đơn</title>

    <!-- Bootstrap + Icon -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #0f0f0f;
            color: #fff;
        }

        html, body {
            height: 100%;
            overflow: hidden;
        }

        .card {
            background: #1a1a1a;
            border-radius: 12px;
            border: 1px solid #333;
        }

        .card-dark {
            background-color: #1a1a1a;
            border-radius: 12px;
            padding: 15px;
        }

        .search-box {
            position: relative;
        }

        .search-box input {
            padding-left: 35px;
        }

        .search-box i {
            position: absolute;
            top: 50%;
            left: 10px;
            transform: translateY(-50%);
            color: #aaa;
        }

        .filter-wrapper {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }

        .filter-btn {
            font-size: 12px;
            padding: 4px 10px;
        }

        .drink-list {
            height: calc(100vh - 230px);
            overflow-y: auto;
            scrollbar-width: none;
            -ms-overflow-style: none;
        }

        .drink-list::-webkit-scrollbar {
            display: none;
        }

        .drink-card {
            text-align: center;
            padding: 10px;
        }

        .drink-img {
            width: 90px;
            height: 90px;
            object-fit: cover;
            border-radius: 8px;
        }

        .btn-add {
            width: 100%;
            font-size: 13px;
            padding: 5px;
        }

        .order-title {
            color: #fff;
        }

        textarea {
            background: #111;
            color: #fff;
            border: 1px solid #333;
        }

        .modal-content {
            background: #000;
            color: #fff;
            border: 1px solid #333;
        }

    </style>
</head>

<body>

<div class="container-fluid">

    <!-- TITLE -->
    <div class="mt-4">
        <h3>TẠO HÓA ĐƠN</h3>
        <p class="text-muted">Tạo và thanh toán hóa đơn nhanh</p>
    </div>

    <div class="row mt-3">

        <!-- LEFT PANEL -->
        <div class="col-md-8">
            <!-- SEARCH + FILTER -->
            <div class="card-dark">
                <form method="get" action="taohoadon" class="mb-2">
                    <div class="search-box">
                        <i class="bi bi-search"></i>
                        <input type="text" name="keyword" class="form-control" placeholder="Tìm đồ uống...">
                    </div>
                </form>

                <div class="filter-wrapper">
                    <a href="taohoadon" class="btn btn-outline-light filter-btn">Tất cả</a>
                    <%
                        List<LoaiDoUong> loaiList = (List<LoaiDoUong>) request.getAttribute("loaiList");
                        if (loaiList != null) {
                            for (LoaiDoUong l : loaiList) {
                    %>
                    <a href="taohoadon?maLoai=<%=l.getMaLoai()%>" class="btn btn-outline-warning filter-btn">
                        <%=l.getTenLoai()%>
                    </a>
                    <% }} %>
                </div>
            </div>

            <!-- DRINK LIST -->
            <div class="drink-list mt-3">
                <div class="row">
                    <%
                        List<DoUong> list = (List<DoUong>) request.getAttribute("listDoUong");
                        if (list != null) {
                            for (DoUong d : list) {
                    %>
                    <div class="col-md-3 mb-3">
                        <div class="card drink-card">
                            <div class="d-flex justify-content-center">
                                <img src="<%=d.getHinhAnh()%>" class="drink-img">
                            </div>
                            <h6 class="mt-2"><%=d.getTenDoUong()%></h6>
                            <p class="text-warning"><%=d.getGiaTien()%> đ</p>

                            <form method="post" action="taohoadon">
                                <input type="hidden" name="action" value="them">
                                <input type="hidden" name="maDoUong" value="<%=d.getMaDoUong()%>">
                                <button class="btn btn-warning btn-add">
                                    <i class="bi bi-plus-circle"></i> Thêm
                                </button>
                            </form>
                        </div>
                    </div>
                    <% }} %>
                </div>
            </div>
        </div>

        <!-- RIGHT PANEL -->
        <div class="col-md-4">
            <div class="card p-3">
                <div class="d-flex justify-content-between mb-2">
                    <h5 class="order-title"><i class="bi bi-cart"></i> Đơn hiện tại</h5>
                    <form method="post" action="taohoadon">
                        <input type="hidden" name="action" value="xoaAll">
                        <button class="btn btn-danger btn-sm">Xóa</button>
                    </form>
                </div>

                <table class="table table-dark table-sm text-center">
                    <tr>
                        <th>Tên</th>
                        <th>SL</th>
                        <th>Giá</th>
                        <th>TT</th>
                    </tr>
                    <%
                        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");
                        int tong = 0;
                        if (cart != null) {
                            for (GioHang g : cart) {
                                tong += g.getThanhTien();
                    %>
                    <tr>
                        <td><%=g.getTenDoUong()%></td>
                        <td><%=g.getSoLuong()%></td>
                        <td><%=g.getDonGia()%></td>
                        <td><%=g.getThanhTien()%></td>
                    </tr>
                    <% }} %>
                </table>

                <h5 class="text-warning">Tổng: <%=tong%> đ</h5>

                <!-- GHI CHÚ -->
                <textarea id="ghiChu" class="form-control mt-2" placeholder="Ghi chú..."></textarea>

                <form method="post" action="taohoadon" class="mt-3">
                    <input type="hidden" name="action" value="thanhtoan">
                    <button type="button" onclick="thanhToan()" class="btn btn-success w-100">
                        <i class="bi bi-credit-card"></i> Thanh toán
                    </button>
                </form>
            </div>
        </div>

    </div>
</div>

<!-- MODAL -->
<div class="modal fade" id="invoiceModal">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-4">
            <!-- Thẻ này sẽ chứa HTML hóa đơn từ servlet -->
            <div id="invoiceContent"></div>
            <button class="btn btn-secondary mt-3 w-100" data-bs-dismiss="modal">Đóng</button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function thanhToan() {
        const ghiChuValue = encodeURIComponent(document.getElementById("ghiChu").value);

        fetch('<%=request.getContextPath()%>/nhanvien/taohoadon', {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "action=thanhtoan&ghiChu=" + ghiChuValue,
            credentials: "same-origin"
        })
            .then(res => res.text())
            .then(data => {
                document.getElementById("invoiceContent").innerHTML = data;
                let modal = new bootstrap.Modal(document.getElementById('invoiceModal'));
                modal.show();
            })
            .catch(err => {
                console.error("Lỗi khi thanh toán:", err);
                alert("Có lỗi xảy ra khi thanh toán!");
            });
    }
</script>

</body>
</html>