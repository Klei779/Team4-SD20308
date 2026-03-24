<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>

    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #0f1115;
            color: white;
        }
        .navbar-custom {
            background: #1c1f26;
        }
        .dropdown-menu {
            background: #1c1f26;
        }
        .dropdown-item {
            color: white;
        }
        .dropdown-item:hover {
            background-color: orange;
            color: black;
        }
        .card {
            background: #1c1f26;
            color: white;
            border: none;
            border-radius: 12px;
        }
        .card:hover {
            box-shadow: 0 0 10px orange;
        }
        .card-title {
            font-weight: bold;
        }
        .card-text {
            font-size: 14px;
        }
        .content-container {
            padding: 30px;
        }
    </style>
</head>
<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-custom px-4">
    <a class="navbar-brand text-warning fw-bold" href="#">☕ Quản lý quán nước</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMenu"
            aria-controls="navbarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarMenu">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

            <!-- Dropdown đồ uống -->
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Chọn loại đồ uống
                </a>
                <ul class="dropdown-menu">
                    <c:forEach var="loai" items="${loaiList}">
                        <li>
                            <a class="dropdown-item" href="home?maLoai=${loai.maLoai}">
                                    ${loai.tenLoai}
                            </a>
                        </li>
                    </c:forEach>

                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item text-warning" href="home">Tất cả</a></li>
                </ul>
            </li>
        </ul>

        <div class="d-flex">
            <!-- Nút mở modal đăng nhập -->
            <button type="button" class="btn btn-warning text-black" data-bs-toggle="modal" data-bs-target="#loginModal">
                Đăng nhập
            </button>
        </div>
    </div>
</nav>

<!-- CONTENT -->
<div class="container content-container">

    <h1 class="mb-4 text-center">Danh sách món ăn</h1>

    <div class="row row-cols-1 row-cols-md-4 g-4">

        <c:forEach var="item" items="${menuList}">
            <div class="col">
                <div class="card h-100">

                    <!-- IMAGE -->
                    <img src="${item.hinhAnh}" class="card-img-top" alt="${item.tenDoUong}"
                         style="height: 200px; object-fit: cover;">

                    <!-- BODY -->
                    <div class="card-body">
                        <h5 class="card-title">${item.tenDoUong}</h5>
                        <p class="card-text">${item.moTa}</p>
                    </div>

                    <!-- FOOTER -->
                    <div class="card-footer text-center">
                    <span class="text-warning fw-bold fs-5">
                        ${item.giaTien} VND
                    </span>
                    </div>

                </div>
            </div>
        </c:forEach>

    </div>
</div>

<!-- ===== LOGIN MODAL Bootstrap ===== -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content bg-dark text-white rounded-3">

            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Đăng nhập</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" action="<c:url value='/login'/>">
                    <div class="mb-3">
                        <input type="text" name="username" placeholder="Tên đăng nhập" class="form-control" required />
                    </div>
                    <div class="mb-3">
                        <input type="password" name="password" placeholder="Mật khẩu" class="form-control" required />
                    </div>
                    <button type="submit" class="btn btn-warning w-100 fw-bold">Đăng nhập</button>
                </form>
            </div>

        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle (Popper + Bootstrap JS) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- Mở modal tự động nếu có lỗi -->
<c:if test="${not empty error}">
    <script>
        const loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
        document.addEventListener("DOMContentLoaded", function () {
            loginModal.show();
        });
    </script>
</c:if>

</body>
</html>