<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-4">

    <div class="card shadow p-4">

        <h4>Chi tiết công thức</h4>

        <input type="hidden" id="maCongThuc" value="${maCongThuc}"/>

        <table class="table table-bordered">
            <tr>
                <th>Nguyên liệu</th>
                <th>Định lượng</th>
            </tr>

            <c:forEach var="c" items="${ctctList}">
                <tr>
                    <td>${c.maNguyenLieu}</td>
                    <td>${c.dinhLuong}</td>
                </tr>
            </c:forEach>
        </table>

        <hr>

        <h5>Thêm nguyên liệu</h5>

        <div class="row">
            <div class="col">
                <select id="nguyenLieu" class="form-select">
                    <c:forEach var="nl" items="${nguyenLieuList}">
                        <option value="${nl.maNguyenLieu}">
                                ${nl.tenNguyenLieu}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col">
                <input id="dinhLuong" type="number" class="form-control" placeholder="Định lượng"/>
            </div>

            <div class="col">
                <button onclick="addNguyenLieu()" class="btn btn-success">Thêm</button>
            </div>
        </div>

    </div>
</div>

<script>
    function addNguyenLieu() {
        let maCongThuc = document.getElementById("maCongThuc").value;
        let maNguyenLieu = document.getElementById("nguyenLieu").value;
        let dinhLuong = document.getElementById("dinhLuong").value;

        fetch("cong-thuc", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `action=addCTCT_AJAX&maCongThuc=${maCongThuc}&maNguyenLieu=${maNguyenLieu}&dinhLuong=${dinhLuong}`
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    alert("Thêm thành công");
                    location.reload();
                } else {
                    alert(data.message);
                }
            });
    }
</script>