<%--
  Created by IntelliJ IDEA.
  User: thuon
  Date: 20/03/2026
  Time: 4:43 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String[] ids = (String[]) request.getAttribute("ids");
    String[] dates = (String[]) request.getAttribute("dates");
    String[] suppliers = (String[]) request.getAttribute("suppliers");
    String[] items = (String[]) request.getAttribute("items");
    int[] totals = (int[]) request.getAttribute("totals");
    String[] creators = (String[]) request.getAttribute("creators");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập hàng</title>

    <style>
        body {
            background:#0f1115;
            color:white;
            font-family:Arial;
            padding:20px;
        }

        /* Header */
        .header {
            display:flex;
            justify-content:space-between;
            align-items:center;
            margin-bottom:20px;
        }

        .btn {
            background:orange;
            color:black;
            padding:12px 20px;
            border-radius:8px;
            font-weight:bold;
            cursor:pointer;
        }

        /* Table */
        .table-box {
            background:#1c1f26;
            padding:20px;
            border-radius:12px;
        }

        .row {
            display:grid;
            grid-template-columns: 1fr 1fr 2fr 2fr 1fr 1fr;
            padding:12px 0;
            border-bottom:1px solid #333;
        }

        .header-row {
            color:#aaa;
        }

        .id {
            color:orange;
            font-weight:bold;
        }

        .money {
            color:#ffcc66;
        }
    </style>

</head>
<body>

<h1>Nhập hàng</h1>
<p style="color:#aaa">Tạo phiếu nhập và theo dõi lịch sử nhập kho</p>

<div class="header">
    <h2>📥 Nhập hàng</h2>
    <div class="btn">+ Tạo phiếu nhập</div>
</div>

<div class="table-box">

    <div class="row header-row">
        <div>MÃ PHIẾU</div>
        <div>NGÀY NHẬP</div>
        <div>NHÀ CUNG CẤP</div>
        <div>NGUYÊN LIỆU</div>
        <div>TỔNG TIỀN</div>
        <div>NGƯỜI TẠO</div>
    </div>

    <% if(ids != null) { %>
    <% for(int i = 0; i < ids.length; i++) { %>
    <div class="row">
        <div class="id"><%= ids[i] %></div>
        <div><%= dates[i] %></div>
        <div><%= suppliers[i] %></div>
        <div><%= items[i] %></div>
        <div class="money"><%= totals[i] %> đ</div>
        <div><%= creators[i] %></div>
    </div>
    <% } %>
    <% } %>

</div>

</body>
</html>