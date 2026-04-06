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

        .btn {
            background: #facc15;
            color: black;
            padding: 10px 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: bold;
        }

        .card {
            background: #1c1f26;
            padding: 20px;
            border-radius: 16px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        tr:hover {
            background: #1c1f26;
        }

        a {
            color: #facc15;
        }

        /* MODAL ADD */
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
        }

        /* MODAL DETAIL */
        #detailModal {
            display: none;
            position: fixed;
            top:0; left:0;
            width:100%; height:100%;
            background: rgba(0,0,0,0.6);
        }

        #detailBox {
            width: 450px;
            background: white;
            color: black;
            margin: 50px auto;
            padding: 20px;
            border-radius: 16px;
        }

        input, select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 10px;
            border-radius: 8px;
            border: none;
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
                    <a href="#" onclick="showDetail('${p.maPhieu}')">Xem</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<!-- MODAL THÊM -->
<div id="modal">
    <form action="nhaphang" method="post">

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

<!-- MODAL CHI TIẾT -->
<div id="detailModal">
    <div id="detailBox">
        <h2 style="text-align:center">POLY COFFEE</h2>

        <div id="detailContent"></div>

        <button class="btn" onclick="closeDetail()">Đóng</button>
    </div>
</div>
<script>
    const baseUrl = "${pageContext.request.contextPath}";
</script>
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

    function showDetail(id){
        console.log("URL =", baseUrl + "/quanly/nhaphang?action=detailAjax&id=" + id);

        fetch(baseUrl + "/quanly/nhaphang?action=detailAjax&id=" + id)
            .then(res => res.text())
            .then(data => {
                console.log("RESPONSE =", data);

                let json = JSON.parse(data);

                let html = `
                <p><b>Mã phiếu:</b> ${json.maPhieu}</p>
                <p><b>Nhân viên:</b> ${json.nhanVien}</p>
                <p><b>Nhà cung cấp:</b> ${json.ncc}</p>
                <p><b>Ngày:</b> ${json.ngayNhap}</p>

                <hr>

                <table border="1" width="100%">
                    <tr>
                        <th>Nguyên liệu</th>
                        <th>SL</th>
                        <th>Đơn giá</th>
                    </tr>
            `;

                json.chiTiet.forEach(ct => {
                    html += `
                    <tr>
                        <td>${ct.tenNL}</td>
                        <td>${ct.soLuong}</td>
                        <td>${ct.donGia}</td>
                    </tr>
                `;
                });

                html += `</table>
                <h3 style="text-align:right">
                    Tổng: ${json.tongTien} đ
                </h3>
            `;

                document.getElementById("detailContent").innerHTML = html;
                document.getElementById("detailModal").style.display = "block";
            });
    }

    function closeDetail(){
        document.getElementById("detailModal").style.display = "none";
    }
</script>

</body>
</html>