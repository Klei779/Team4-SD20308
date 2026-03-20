<%--
  Created by IntelliJ IDEA.
  User: thuon
  Date: 20/03/2026
  Time: 4:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Sora:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Sora', sans-serif;
            background: #0f1115;
            color: #fff;
            padding: 20px;
        }

        h1 {
            margin-bottom: 20px;
        }

        /* Cards */
        .cards {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
            margin-bottom: 20px;
        }

        .card {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
            transition: 0.3s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card p {
            color: #aaa;
        }

        .card h2 {
            margin-top: 10px;
        }

        /* Layout dưới */
        .grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 15px;
        }

        /* Chart fake */
        .chart {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .bars {
            display: flex;
            align-items: flex-end;
            gap: 10px;
            margin-top: 20px;
            height: 150px;
        }

        .bar {
            flex: 1;
            background: linear-gradient(to top, orange, gold);
            border-radius: 6px;
        }

        /* Top món */
        .top {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .item {
            display: flex;
            justify-content: space-between;
            margin: 10px 0;
        }

        .item span:last-child {
            color: orange;
        }

        .bottom {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-top: 20px;
        }

        /* Nguyên liệu */
        .warning {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .alert {
            background: #3a1a1a;
            border: 1px solid red;
            padding: 15px;
            border-radius: 8px;
            color: #ff6b6b;
        }

        /* Hóa đơn */
        .table-box {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .table {
            width: 100%;
            margin-top: 10px;
        }

        .row {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            padding: 10px 0;
            border-bottom: 1px solid #333;
        }

        .header {
            color: #aaa;
        }

        .status {
            color: #00ff88;
        }

    </style>
</head>
<body>

<h1>Dashboard</h1>

<!-- Cards -->
<div class="cards">
    <div class="card">
        <p>Doanh thu hôm nay</p>
        <h2>${revenueToday} đ</h2>
    </div>

    <div class="card">
        <p>Đơn hàng hôm nay</p>
        <h2>${ordersToday}</h2>
    </div>

    <div class="card">
        <p>Hóa đơn đang chờ</p>
        <h2>${pendingBills}</h2>
    </div>

    <div class="card">
        <p>Nhân viên hoạt động</p>
        <h2>${activeStaff}</h2>
    </div>
</div>

<!-- Nội dung dưới -->
<div class="grid">

    <!-- Chart -->
    <div class="chart">
        <h3>Doanh thu 7 ngày</h3>
        <div class="bars">
            <div class="bar" style="height: 40%"></div>
            <div class="bar" style="height: 70%"></div>
            <div class="bar" style="height: 60%"></div>
            <div class="bar" style="height: 80%"></div>
            <div class="bar" style="height: 50%"></div>
            <div class="bar" style="height: 90%"></div>
            <div class="bar" style="height: 75%"></div>
        </div>
    </div>

    <!-- Top món -->
    <div class="top">
        <h3>Món bán chạy</h3>

        <div class="item">
            <span>1. ${top1}</span>
            <span>${top1Count}</span>
        </div>

        <div class="item">
            <span>2. ${top2}</span>
            <span>${top2Count}</span>
        </div>

        <div class="item">
            <span>3. ${top3}</span>
            <span>${top3Count}</span>
        </div>

        <div class="item">
            <span>4. ${top4}</span>
            <span>${top4Count}</span>
        </div>

    </div>

</div>
<%
    String[] billIds = (String[]) request.getAttribute("billIds");
    int[] totals = (int[]) request.getAttribute("totals");
%>
<div class="bottom">

    <!-- Nguyên liệu -->
    <div class="warning">
        <h3>⚠️ Nguyên liệu sắp hết</h3>

        <div class="alert">
            ⚠️ <b>${lowStockName}</b> : còn ${lowStockAmount} lít (ngưỡng ${lowStockThreshold})
        </div>
    </div>

    <!-- Hóa đơn -->
    <div class="table-box">
        <h3>Hóa đơn gần đây</h3>

        <div class="table">
            <div class="row header">
                <div>Mã HĐ</div>
                <div>Tổng</div>
                <div>Trạng thái</div>
            </div>

            <% if (billIds != null && totals != null) { %>
            <% for (int i = 0; i < billIds.length; i++) { %>
            <div class="row">
                <div><%= billIds[i] %></div>
                <div><%= totals[i] %> đ</div>
                <div class="status">✔ Đã TT</div>
            </div>
            <% } %>
            <% } else { %>
            <p>Không có dữ liệu</p>
            <% } %>

        </div>
    </div>

</div>

</body>
</html>
