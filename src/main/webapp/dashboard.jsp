<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

        .grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 15px;
            margin-top: 20px;
        }

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
            position: relative;
            transition: 0.4s;
            display: flex;
            align-items: flex-start;
            justify-content: center;
        }

        .bar span {
            position: absolute;
            top: -20px;
            font-size: 11px;
            color: #fff;
            white-space: nowrap;
        }
        .bar:hover {
            transform: scaleY(1.1);
        }

        .bar:hover::after {
            content: attr(data-value) " đ";
            position: absolute;
            top: -25px;
            left: 0;
            right: 0;
            text-align: center;
            font-size: 12px;
            background: black;
            padding: 3px;
            border-radius: 4px;
        }

        .days {
            display: flex;
            justify-content: space-between;
            font-size: 11px;
            color: #aaa;
            margin-top: 5px;
        }

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
            margin-bottom: 10px;
        }

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

<div class="grid">

    <div class="chart">
        <h3>Doanh thu 7 ngày</h3>

        <div class="bars">
            <c:forEach var="p" items="${percents}" varStatus="i">
                <div class="bar" style="height:${p}%">
                    <span>${days[i.index].doanhThu} đ</span>
                </div>
            </c:forEach>
        </div>

        <div class="days">
            <c:forEach var="d" items="${days}">
                <span>${d.ngay}</span>
            </c:forEach>
        </div>

    </div>

    <div class="top">
        <h3>Món bán chạy</h3>

        <c:if test="${empty topList}">
            <p style="color:#aaa;">Không có dữ liệu</p>
        </c:if>

        <c:forEach var="item" items="${topList}" varStatus="loop">
            <div class="item">
                <span>${loop.index + 1}. ${item.tenDoUong}</span>
                <span>${item.soLuong}</span>
            </div>
        </c:forEach>

    </div>

    <div class="bottom">

        <div class="warning">
            <h3>⚠️ Nguyên liệu sắp hết</h3>

            <c:if test="${empty lowStockList}">
                <p style="color:#aaa;">Không có</p>
            </c:if>

            <c:forEach var="nl" items="${lowStockList}">
                <div class="alert">
                    ⚠️ <b>${nl.tenNguyenLieu}</b> :
                    còn ${nl.soLuongTon} (ngưỡng ${nl.soLuongToiThieu})
                </div>
            </c:forEach>
        </div>

        <div class="table-box">
            <h3>Hóa đơn gần đây</h3>

            <div class="table">
                <div class="row header">
                    <div>Mã HĐ</div>
                    <div>Tổng</div>
                    <div>Trạng thái</div>
                </div>

                <c:forEach var="bill" items="${billList}">
                    <div class="row">
                        <div>${bill.maHoaDon}</div>
                        <div>${bill.tongTien} đ</div>
                        <div class="status">✔ Đã TT</div>
                    </div>
                </c:forEach>

            </div>
        </div>

    </div>

</div>

</body>
</html>