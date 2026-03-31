<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Nhập hàng</title>

    <style>
        body {
            background: #0f1115;
            color: #e5e7eb;
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 20px;
        }

        h2 {
            margin-bottom: 20px;
        }

        /* BUTTON */
        .btn {
            background: #facc15;
            color: black;
            padding: 10px 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: bold;
        }

        .btn:hover {
            background: #eab308;
        }

        /* CARD */
        .card {
            background: #1c1f26;
            padding: 20px;
            border-radius: 16px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }

        /* TABLE */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background: #1c1f26;
            padding: 12px;
        }

        td {
            padding: 10px;
            border-top: 1px solid #1c1f26;
            text-align: center;
        }

        tr:hover {
            background: #1c1f26;
        }

        a {
            color: #facc15;
            text-decoration: none;
        }

        /* MODAL */
        #modal {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: #1c1f26;
            padding: 20px;
            border-radius: 16px;
            display: none;
            width: 600px;
            box-shadow: 0 0 20px rgba(0,0,0,0.5);
        }

        input, select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            border-radius: 8px;
            border: none;
            outline: none;
        }

        textarea {
            resize: none;
        }
    </style>
</head>

<body>

<h2>📦 Quản lý nhập hàng</h2>

<button class="btn" onclick="openModal()">+ Thêm phiếu nhập</button>

<div class="card">
    <table>
        <tr>
            <th>Mã</th>
            <th>Ngày</th>
            <th>Nhân viên</th>
            <th>NCC</th>
            <th>Tổng tiền</th>
            <th>Ghi chú</th>
            <th>Action</th>
        </tr>

        <c:forEach var="p" items="${list}">
            <tr>
                <td>${p.maPhieu}</td>
                <td>
                    <fmt:formatDate value="${p.ngayNhap}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <td>${p.nhanVien}</td>
                <td>${p.ncc}</td>
                <td>
                    <fmt:formatNumber value="${p.tongTien}" type="number"/> đ
                </td>
                <td>${p.ghiChu}</td>
                <td>
                    <a href="nhaphang?action=detail&id=${p.maPhieu}">Xem</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<!-- MODAL -->
<div id="modal">

    <form action="nhaphang" method="post">

        <input type="hidden" name="maNguoiDung" value="1">

        <label>Nhà cung cấp</label>
        <select name="maNCC">
            <c:forEach var="ncc" items="${nccList}">
                <option value="${ncc.id}">${ncc.ten}</option>
            </c:forEach>
        </select>

        <label>Ghi chú</label>
        <textarea name="ghiChu"></textarea>

        <table id="tableNL">
            <tr>
                <th>Nguyên liệu</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>HSD</th>
            </tr>

            <tr>
                <td>
                    <select name="maNguyenLieu">
                        <c:forEach var="nl" items="${nlList}">
                            <option value="${nl.id}">${nl.ten}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="number" name="soLuong"></td>
                <td><input type="number" name="donGia"></td>
                <td><input type="date" name="hsd"></td>
            </tr>
        </table>

        <button type="button" class="btn" onclick="addRow()">+ Thêm dòng</button>
        <br><br>
        <button type="submit" class="btn">Lưu</button>
    </form>
</div>

<script>
    function openModal(){
        document.getElementById("modal").style.display="block";
    }

    function addRow(){
        let table = document.getElementById("tableNL");
        let row = table.insertRow();

        row.innerHTML = `
        <td>
            <select name="maNguyenLieu">
                ${document.querySelector('[name="maNguyenLieu"]').innerHTML}
            </select>
        </td>
        <td><input type="number" name="soLuong"></td>
        <td><input type="number" name="donGia"></td>
        <td><input type="date" name="hsd"></td>
        `;
    }
</script>

</body>
</html>