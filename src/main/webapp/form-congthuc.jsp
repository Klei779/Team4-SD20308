<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container mt-4">
    <div class="card shadow p-4">

        <form action="congthuc" method="post">

            <input type="hidden" name="action" value="${ct != null ? 'update' : 'insert'}"/>

            <c:if test="${ct != null}">
                <input type="hidden" name="id" value="${ct.maCongThuc}"/>
            </c:if>

            <div class="mb-3">
                <label>Tên công thức</label>
                <input class="form-control" type="text" name="ten"
                       value="${ct.tenCongThuc}" required/>
            </div>

            <c:if test="${ct != null}">
                <div class="mb-3">
                    <label>Trạng thái</label>
                    <select class="form-select" name="trangThai">
                        <option value="true">Hiện</option>
                        <option value="false">Ẩn</option>
                    </select>
                </div>
            </c:if>

            <button class="btn btn-success">Lưu</button>

        </form>

    </div>
</div>