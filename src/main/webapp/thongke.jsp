<%--
  Created by IntelliJ IDEA.
  User: thuon
  Date: 20/03/2026
  Time: 4:47 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo</title>
    <style>
        body {
            background: #0f172a;
            color: white;
            font-family: Arial;
        }
        .card {
            background: #1e293b;
            padding: 20px;
            border-radius: 10px;
            margin: 10px;
        }
        .row {
            display: flex;
            gap: 20px;
        }
        .bar {
            display: flex;
            align-items: flex-end;
            height: 150px;
            gap: 10px;
        }
        .bar div {
            width: 25px;
            background: orange;
        }
        table {
            width: 100%;
        }
        td, th {
            padding: 8px;
        }
        .green { color: #22c55e; }
        .red { color: red; }
    </style>
</head>
<body>

<h2>Báo cáo</h2>

<!-- Doanh thu -->
<div class="card">
    <h3>Doanh thu theo ngày</h3>
    <div class="bar">
        <%
            int[] revenues = (int[]) request.getAttribute("revenues");
            for (int r : revenues) {
        %>
        <div style="height:<%= r/100000 %>px"></div>
        <%
            }
        %>
    </div>
</div>

<!-- Top món -->
<div class="card">
    <h3>Top món bán chạy</h3>
    <%
        String[] foods = (String[]) request.getAttribute("foods");
        int[] quantities = (int[]) request.getAttribute("quantities");

        for (int i = 0; i < foods.length; i++) {
    %>
    <p><%= foods[i] %> - <%= quantities[i] %></p>
    <%
        }
    %>
</div>

<!-- Nhân viên -->
<div class="card">
    <h3>Hiệu suất nhân viên</h3>
    <table>
        <tr>
            <th>Tên</th>
            <th>Số HĐ</th>
            <th>Doanh thu</th>
        </tr>

        <%
            String[] names = (String[]) request.getAttribute("names");
            int[] bills = (int[]) request.getAttribute("bills");
            int[] doanhThuNV = (int[]) request.getAttribute("doanhThuNV");

            for (int i = 0; i < names.length; i++) {
        %>
        <tr>
            <td><%= names[i] %></td>
            <td><%= bills[i] %></td>
            <td><%= doanhThuNV[i] %> đ</td>
        </tr>
        <%
            }
        %>
    </table>
</div>

<!-- Lợi nhuận -->
<div class="card">
    <h3>Lợi nhuận</h3>

    <p>Doanh thu: <%= request.getAttribute("doanhThu") %> đ</p>
    <p class="red">Chi phí: -<%= request.getAttribute("chiPhi") %> đ</p>
    <p class="red">VAT: -<%= request.getAttribute("vat") %> đ</p>

    <h2 class="green">
        Lợi nhuận: <%= request.getAttribute("loiNhuan") %> đ
    </h2>
</div>

</body>
</html>
