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
            background: #000;
            color: #fff;
            font-family: Arial;
            padding: 20px;
        }

        h2 {
            margin-bottom: 20px;
        }

        .row {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .card {
            background: #111;
            padding: 20px;
            border-radius: 12px;
            flex: 1;
            box-shadow: 0 0 10px rgba(255,255,255,0.05);
        }

        canvas {
            background: #000;
            border-radius: 10px;
            padding: 10px;
            height: 300px !important;
        }

        select {
            padding: 8px;
            border-radius: 6px;
            border: none;
            background: #222;
            color: #fff;
            margin-bottom: 8px;
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

<!-- ===== 2 biểu đồ ===== -->
<div class="row">

    <!-- Doanh thu theo ngày -->
    <div class="card">
        <h3>Doanh thu theo giờ</h3>
        <canvas id="chartDoanhThu"></canvas>
    </div>

    <!-- Top đồ uống -->
    <div class="card">
        <h3>Top đồ uống</h3>
        <canvas id="chartTop"></canvas>
    </div>

</div>

<script>
    // ===== Format ngày đẹp =====
    function formatDate(dateStr) {
        const d = new Date(dateStr);
        return d.toLocaleDateString('vi-VN'); // 30/03/2026
    }

    // ===== Chart doanh thu =====
    const labels = [
        <c:forEach var="d" items="${listNgay}" varStatus="s">
        formatDate("${d.ngay}")${!s.last ? ',' : ''}
        </c:forEach>
    ];

    const data = [
        <c:forEach var="d" items="${listNgay}" varStatus="s">
        ${d.doanhThu}${!s.last ? ',' : ''}
        </c:forEach>
    ];

    new Chart(document.getElementById('chartDoanhThu'), {
        type: 'line', // 👉 đổi sang line cho đẹp hơn
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: data,
                borderColor: '#facc15',
                backgroundColor: '#facc15',
                tension: 0.4,
                fill: false
            }]
        },
        options: {
            plugins: {
                legend: {
                    labels: { color: 'white' }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.raw.toLocaleString('vi-VN') + " đ";
                        }
                    }
                }
            },
            scales: {
                x: {
                    ticks: { color: 'white' }
                },
                y: {
                    ticks: {
                        color: 'white',
                        callback: function(value) {
                            return value.toLocaleString('vi-VN');
                        }
                    }
                }
            }
        }
    });

    // ===== Chart top đồ uống =====
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
                data: topData,
                backgroundColor: '#facc15',
                borderRadius: 6
            }]
        },
        options: {
            plugins: {
                legend: {
                    labels: { color: 'white' }
                }
            },
            scales: {
                x: {
                    ticks: { color: 'white' }
                },
                y: {
                    ticks: { color: 'white' }
                }
            }
        }
    });
</script>

</body>
</html>