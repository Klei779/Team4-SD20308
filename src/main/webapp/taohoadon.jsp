<%--
  Created by IntelliJ IDEA.
  User: lannn
  Date: 3/22/2026
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, entity.*" %>

<html>
<head>
    <title>Tạo hóa đơn</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #000;
            color: #fff;
        }

        .card {
            background: #111;
            border: 1px solid #333;
            border-radius: 12px;
        }

        /* LEFT SIDE */
        .left-panel {
            height: 100vh;
            overflow: hidden;
        }

        .fixed-top-bar {
            position: sticky;
            top: 0;
            z-index: 10;
            background: #000;
            padding-bottom: 10px;
        }

        .drink-list {
            height: calc(100vh - 140px);
            overflow-y: auto;
        }

        /* RIGHT SIDE */
        .right-panel {
            height: 100vh;
            position: sticky;
            top: 0;
        }

        .btn-add {
            width: 100%;
        }

        .drink-img {
            height: 120px;
            object-fit: cover;
            border-radius: 8px;
        }

        textarea {
            background: #111;
            color: #fff;
            border: 1px solid #333;
        }

        /* modal */
        .modal-content {
            background: #000;
            color: #fff;
            border: 1px solid #333;
        }

    </style>
</head>

<body>

<div class="container-fluid">
    <div class="row">

        <!-- ================= LEFT ================= -->
        <div class="col-md-8 left-panel">

            <!-- FIXED -->
            <div class="fixed-top-bar">

                <!-- SEARCH -->
                <form method="get" action="taohoadon" class="mb-2">
                    <input type="text" name="keyword" class="form-control"
                           placeholder="Tìm đồ uống...">
                </form>

                <!-- LOẠI -->
                <div class="d-flex gap-2 overflow-auto">
                    <a href="taohoadon" class="btn btn-outline-light btn-sm">Tất cả</a>

                    <%
                        List<LoaiDoUong> loaiList = (List<LoaiDoUong>) request.getAttribute("loaiList");
                        if (loaiList != null) {
                            for (LoaiDoUong l : loaiList) {
                    %>
                    <a href="taohoadon?maLoai=<%=l.getMaLoai()%>"
                       class="btn btn-outline-warning btn-sm">
                        <%=l.getTenLoai()%>
                    </a>
                    <% }} %>
                </div>

            </div>

            <!-- SCROLL -->
            <div class="drink-list mt-3">
                <div class="row">

                    <%
                        List<DoUong> list = (List<DoUong>) request.getAttribute("listDoUong");

                        if (list != null) {
                            for (DoUong d : list) {
                    %>

                    <!-- 3 card / row -->
                    <div class="col-md-4 mb-3">
                        <div class="card p-2">

                            <img src="<%=d.getHinhAnh()%>" class="drink-img mb-2">

                            <h6><%=d.getTenDoUong()%></h6>
                            <p class="text-warning"><%=d.getGiaTien()%> đ</p>

                            <form method="post" action="taohoadon">
                                <input type="hidden" name="action" value="them">
                                <input type="hidden" name="maDoUong" value="<%=d.getMaDoUong()%>">
                                <button class="btn btn-warning btn-add">Thêm</button>
                            </form>

                        </div>
                    </div>

                    <% }} %>

                </div>
            </div>

        </div>


        <!-- ================= RIGHT ================= -->
        <div class="col-md-4 right-panel">

            <div class="card p-3 mt-3">

                <div class="d-flex justify-content-between mb-2">
                    <h5>Đơn hiện tại</h5>

                    <form method="post" action="taohoadon">
                        <input type="hidden" name="action" value="xoaAll">
                        <button class="btn btn-danger btn-sm">Xóa tất cả</button>
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
                <textarea class="form-control mt-2" placeholder="Ghi chú..."></textarea>

                <!-- THANH TOÁN -->
                <form method="post" action="taohoadon" class="mt-3">
                    <input type="hidden" name="action" value="thanhtoan">
                    <button class="btn btn-success w-100">Thanh toán</button>
                </form>

            </div>

        </div>

    </div>
</div>


<!-- ================= MODAL ================= -->
<div class="modal fade" id="hoaDonModal">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-3">

            <h5 class="text-warning">Hóa đơn chi tiết</h5>

            <table class="table table-dark text-center">
                <tr>
                    <th>Tên</th>
                    <th>SL</th>
                    <th>Giá</th>
                    <th>TT</th>
                </tr>

                <%
                    List<GioHang> hoaDon = (List<GioHang>) request.getAttribute("hoaDon");

                    if (hoaDon != null) {
                        for (GioHang g : hoaDon) {
                %>
                <tr>
                    <td><%=g.getTenDoUong()%></td>
                    <td><%=g.getSoLuong()%></td>
                    <td><%=g.getDonGia()%></td>
                    <td><%=g.getThanhTien()%></td>
                </tr>
                <% }} %>

            </table>

        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%
    Boolean open = (Boolean) request.getAttribute("openModal");
    if (open != null && open) {
%>
<script>
    new bootstrap.Modal(document.getElementById('hoaDonModal')).show();
</script>
<%
    }
%>

</body>
</html>