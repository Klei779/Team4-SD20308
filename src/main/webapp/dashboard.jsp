<%--
  Created by IntelliJ IDEA.
  User: thuon
  Date: 22/03/2026
  Time: 10:09 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>

<%
    String[] foods = (String[]) request.getAttribute("topFoods");
    int[] counts = (int[]) request.getAttribute("topCounts");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <style>
        body {
            background: #0f1115;
            color: white;
            font-family: Arial;
            padding: 20px;
        }

        h1 { margin-bottom: 20px; }

        /* Cards */
        .cards {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
        }

        .card {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .card p { color: #aaa; }
        .card h2 { margin-top: 10px; }

        /* Layout */
        .grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 15px;
            margin-top: 20px;
        }

        /* Chart */
        .chart {
            background: #1c1f26;
            padding: 20px;
            border-radius: 12px;
        }

        .bars {
            display: flex;
            align-items: flex-end;
            height: 150px;
            gap: 10px;
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

<!-- Nội dung -->
<div class="grid">

    <!-- Chart -->
    <div class="chart">
        <h3>Doanh thu 7 ngày</h3>
        <div class="bars">
            <div class="bar" style="height:40%"></div>
            <div class="bar" style="height:70%"></div>
            <div class="bar" style="height:60%"></div>
            <div class="bar" style="height:80%"></div>
            <div class="bar" style="height:50%"></div>
            <div class="bar" style="height:90%"></div>
            <div class="bar" style="height:75%"></div>
        </div>
    </div>

    <!-- Top món -->
    <div class="top">
        <h3>Món bán chạy</h3>

        <% for(int i = 0; i < foods.length; i++) { %>
        <div class="item">
            <span><%= (i+1) + ". " + foods[i] %></span>
            <span><%= counts[i] %></span>
        </div>
        <% } %>

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

</div>

</body>
</html>