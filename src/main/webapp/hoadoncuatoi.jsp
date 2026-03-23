<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hóa đơn của tôi</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <style>
        body {
            background-color: #0f0f0f;
            color: white;
        }

        .card-dark {
            background-color: #1a1a1a;
            border-radius: 12px;
            padding: 20px;
        }

        .revenue-box {
            background: #1a1a1a;
            border-radius: 15px;
            padding: 20px;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .revenue-icon {
            font-size: 30px;
            color: orange;
        }

        .badge-paid {
            background-color: #198754;
        }

        .badge-wait {
            background-color: #ffc107;
            color: black;
        }

        table {
            border-radius: 10px;
            overflow: hidden;
        }
    </style>
</head>

<body>

<div class="container mt-4">

    <!-- Tiêu đề -->
    <h3>📄 Hóa đơn của tôi</h3>
    <p class="text-muted">Tra cứu các giao dịch trong ca làm việc</p>

    <!-- 🔥 Thanh lọc + Doanh thu -->
    <div class="row mb-4">

        <!-- Lọc thời gian -->
        <div class="col-md-8">
            <div class="card-dark">
                <h6>Lọc theo thời gian</h6>
                <form action="HoaDonServlet" method="get">
                    <div class="row mt-2">
                        <div class="col-md-4">
                            <input type="date" name="fromDate" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <input type="date" name="toDate" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-warning w-100">Tìm kiếm</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Tổng doanh thu -->
        <div class="col-md-4">
            <div class="revenue-box">
                <div class="revenue-icon">💰</div>
                <div>
                    <!-- Sau này Servlet set -->
                    <h4>${tongDoanhThu}đ</h4>
                    <small class="text-muted">DOANH THU</small><br>
                    <span style="color:#00ff9c">${soHoaDon} hóa đơn</span>
                </div>
            </div>
        </div>

    </div>

    <!-- Bảng -->
    <div class="table-responsive">
        <table class="table table-dark table-hover text-center">
            <thead>
            <tr>
                <th>Mã HD</th>
                <th>Thời gian</th>
                <th>Số món</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Chi tiết</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="hd" items="${listHoaDon}">
                <tr>
                    <td>HD${hd.maHoaDon}</td>
                    <td>${hd.ngayTao}</td>
                    <td>--</td> <!-- chưa có số món -->
                    <td>${hd.tongTien}đ</td>

                    <td>
                        <c:choose>
                            <c:when test="${hd.trangThai}">
                                <span class="badge badge-paid">Đã TT</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-wait">Chờ TT</span>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <a href="ChiTietServlet?id=${hd.maHoaDon}"
                           class="btn btn-sm btn-outline-light">👁</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

</div>

</body>
</html>