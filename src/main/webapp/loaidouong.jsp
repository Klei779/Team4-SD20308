<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý loại đồ uống</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #111;
            color: #fff;
            padding-top: 80px;
            overflow-x: hidden;
        }

        .card {
            background: #1c1c1c;
            border-radius: 12px;
            overflow: hidden;
        }

        a {
            text-decoration: none;
        }

        .header-fixed {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            background: #111;
            padding: 15px 20px;
            z-index: 1000;
            border-bottom: 1px solid #333;
        }

        .left-fixed {
            position: fixed;
            top: 90px;
            left: 20px;
            width: 38%;
            max-height: calc(100vh - 110px);

            overflow-y: auto;
            overflow-x: hidden;

            scrollbar-width: none;
        }

        .left-fixed::-webkit-scrollbar {
            display: none;
        }

        .right-header {
            position: sticky;
            top: 0;
            background: #1c1c1c;
            padding: 10px;
            z-index: 10;
            border-bottom: 1px solid #333;
            color: #fff;
            font-weight: bold;
        }

        .right-scroll {
            max-height: calc(100vh - 160px);
            overflow-y: auto;
            overflow-x: hidden;
            scrollbar-width: none; /* Firefox */
        }

        .right-scroll::-webkit-scrollbar {
            display: none; /* Chrome, Safari */
        }

        img {
            max-width: 100%;
            display: block;
        }

        .right-fixed {
            position: fixed;
            top: 90px;
            right: 20px;
            width: 57%;
        }

        thead th {
            position: sticky;
            top: 0; /* QUAN TRỌNG: phải = 0 */
            background: #1c1c1c;
            z-index: 10;
        }

        .table-scroll {
            max-height: 400px;
            overflow-y: auto;

            scrollbar-width: none;
        }

        .table-scroll::-webkit-scrollbar {
            display: none;
        }
    </style>
</head>

<body class="p-4">

<h3 class="header-fixed">QUẢN LÝ LOẠI ĐỒ UỐNG</h3>
<!-- toast -->
<div class="toast-container position-fixed top-0 end-0 p-3">

    <c:if test="${not empty sessionScope.success}">
        <div class="toast align-items-center text-bg-success border-0 show">
            <div class="d-flex">
                <div class="toast-body">
                        ${sessionScope.success}
                </div>
            </div>
        </div>
        <c:remove var="success" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="toast align-items-center text-bg-danger border-0 show">
            <div class="d-flex">
                <div class="toast-body">
                        ${sessionScope.error}
                </div>
            </div>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>

</div>

<div class="row mt-4">

    <!-- left -->
    <div class="col-md-5">
        <div class="card p-3 left-fixed">

            <form method="post"
                  action="${pageContext.request.contextPath}/loaidouong"
                  class="d-flex mb-3 sticky-top bg-dark p-2"
                  style="top:0; z-index:10;">
                <input type="hidden" name="action" value="add">
                <input type="text" name="tenLoai" class="form-control me-2" placeholder="Nhập tên loại" required>
                <button class="btn btn-warning">
                    <i class="bi bi-plus-circle"></i> Thêm
                </button>
            </form>

            <div class="table-scroll">
                <table class="table table-dark table-hover align-middle">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Tên loại</th>
                    <th width="120">Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="l" items="${listLoai}">
                    <tr>
                        <td>${l.maLoai}</td>

                        <td>
                            <a href="${pageContext.request.contextPath}/loaidouong?maLoai=${l.maLoai}"
                               class="text-white fw-bold">
                                    ${l.tenLoai}
                            </a>
                        </td>

                        <td>
                            <button class="btn btn-sm btn-warning"
                                    onclick="edit(${l.maLoai}, '${l.tenLoai}')">
                                <i class="bi bi-pencil"></i>
                            </button>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/loaidouong"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="maLoai" value="${l.maLoai}">

                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Bạn có chắc muốn xóa không?')">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listLoai}">
                    <tr>
                        <td colspan="3" class="text-center">Không có dữ liệu</td>
                    </tr>
                </c:if>

                </tbody>
            </table>

        </div>
    </div>

    <!-- right -->
    <div class="col-md-7 offset-md-5 right-fixed">
        <div class="card p-3 text-white">
            <h5 class="right-header">Danh sách đồ uống</h5>
            <div class="right-scroll">

            <c:if test="${empty listDoUong}">
                <p class="text-white">Chọn loại để xem</p>
            </c:if>

            <div class="row">
                <c:forEach var="d" items="${listDoUong}">
                    <div class="col-md-6 mb-3">
                        <div class="card p-2">

                            <img src="${d.hinhAnh}" class="img-fluid rounded"
                                 style="height:150px; object-fit:cover;">

                            <h6 class="text-white mt-2">${d.tenDoUong}</h6>
                            <p class="text-warning fw-bold">${d.giaTien} đ</p>

                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </div>

</div>

<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/loaidouong" class="modal-content">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="maLoai" id="editId">

            <div class="modal-header">
                <h5 class="modal-title">Sửa loại</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <input type="text" name="tenLoai" id="editName" class="form-control" required>
            </div>

            <div class="modal-footer">
                <button class="btn btn-success">
                    <i class="bi bi-check-circle"></i> Lưu
                </button>
            </div>
        </form>
    </div>
</div>
</div>

<script>
    function edit(id, name) {
        document.getElementById("editId").value = id;
        document.getElementById("editName").value = name;

        let modal = new bootstrap.Modal(document.getElementById('editModal'));
        modal.show();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>