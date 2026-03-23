<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Báo cáo</title>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        body {
            background: #0f172a;
            color: white;
            font-family: Arial;
        }
        .row {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .card {
            background: #1e293b;
            padding: 20px;
            border-radius: 10px;
            flex: 1;
        }
        canvas {
            background: #111827;
            border-radius: 10px;
            padding: 10px;
        }
    </style>
</head>
<body>

<h2>Báo cáo & Phân tích</h2>
<form method="get" action="thongke">
    <select name="range" onchange="this.form.submit()">
        <option value="today" ${param.range == 'today' ? 'selected' : ''}>Hôm nay</option>
        <option value="7days" ${param.range == '7days' ? 'selected' : ''}>7 ngày</option>
        <option value="30days" ${param.range == '30days' ? 'selected' : ''}>30 ngày</option>
    </select>
</form>
<!-- ===== Tổng quan ===== -->
<div class="row">
    <div class="card">
        <h4>Doanh thu</h4>
        <h2>${tk.doanhThu} đ</h2>
    </div>
    <div class="card">
        <h4>Lợi nhuận</h4>
        <h2>${tk.loiNhuan} đ</h2>
    </div>
    <div class="card">
        <h4>Hóa đơn</h4>
        <h2>${tk.soHoaDon}</h2>
    </div>
    <div class="card">
        <h4>Đã bán</h4>
        <h2>${tk.tongSoLuong}</h2>
    </div>
</div>

<!-- ===== Chart doanh thu ===== -->
<div class="card">
    <h3>Doanh thu theo ngày</h3>
    <canvas id="chartDoanhThu"></canvas>
</div>

<!-- ===== Chart top món ===== -->
<div class="card">
    <h3>Top đồ uống</h3>
    <canvas id="chartTop"></canvas>
</div>

<script>
    const labels = [
        <c:forEach var="d" items="${listNgay}" varStatus="s">
        "${d.ngay}"${!s.last ? ',' : ''}
        </c:forEach>
    ];

    const data = [
        <c:forEach var="d" items="${listNgay}" varStatus="s">
        ${d.doanhThu}${!s.last ? ',' : ''}
        </c:forEach>
    ];

    new Chart(document.getElementById('chartDoanhThu'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: data
            }]
        }
    });

    const topLabels = [
        <c:forEach var="d" items="${topDoUong}" varStatus="s">
        "${d.tenDoUong}"${!s.last ? ',' : ''}
        </c:forEach>
    ];

    const topData = [
        <c:forEach var="d" items="${topDoUong}" varStatus="s">
        ${d.soLuong}${!s.last ? ',' : ''}
        </c:forEach>
    ];

    new Chart(document.getElementById('chartTop'), {
        type: 'bar',
        data: {
            labels: topLabels,
            datasets: [{
                label: 'Số lượng bán',
                data: topData
            }]
        }
    });
</script>

</body>
</html>