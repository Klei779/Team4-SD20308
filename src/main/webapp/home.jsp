<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Poly Coffee - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --dark: #0f1115;
            --card: #1c1f26;
            --accent: #ffc107;
        }

        body {
            background-color: var(--dark);
            color: white;
            font-family: 'Segoe UI', sans-serif;
        }

        .navbar-custom {
            background: var(--card);
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
            padding: 0.8rem 1rem;
        }

        .card {
            background: var(--card);
            border: none;
            border-radius: 16px;
            overflow: hidden;
            transition: 0.3s;
            height: 100%;
        }

        .card:hover {
            transform: translateY(-8px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
        }

        .img-box {
            height: 220px;
            overflow: hidden;
            position: relative;
            background: #000;
        }

        .card-img-top {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .btn-warning {
            font-weight: 700;
            border-radius: 10px;
            padding: 8px 24px;
            text-transform: uppercase;
        }

        .main-title {
            font-weight: 800;
            text-transform: uppercase;
            letter-spacing: 2px;
            margin: 50px 0;
            text-align: center;
        }

        .main-title span {
            color: var(--accent);
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-custom sticky-top px-4">
    <div class="container-fluid">
        <a class="navbar-brand text-warning fw-bold fs-4" href="${ctx}/home">
            <i class="fas fa-coffee me-2"></i>POLY COFFEE
        </a>

        <div class="collapse navbar-collapse" id="navbarMenu">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-white-50" href="#" data-bs-toggle="dropdown">Khám phá
                        Menu</a>
                    <ul class="dropdown-menu dropdown-menu-dark">
                        <c:forEach var="loai" items="${loaiList}">
                            <li><a class="dropdown-item" href="${ctx}/home?maLoai=${loai.maLoai}">${loai.tenLoai}</a>
                            </li>
                        </c:forEach>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item text-warning" href="${ctx}/home">Tất cả sản phẩm</a></li>
                    </ul>
                </li>
            </ul>

            <div class="d-flex align-items-center">
                <a href="${ctx}/login" class="btn btn-warning">
                    <i class="fas fa-sign-in-alt me-2"></i>ĐĂNG NHẬP
                </a>
            </div>
        </div>
    </div>
</nav>

<div class="container my-5">
    <h1 class="main-title">Menu <span>Polly Coffee</span></h1>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
        <c:forEach var="item" items="${menuList}">
            <div class="col">
                <div class="card h-100">
                    <div class="img-box">
                            <%-- Logic kiểm tra ảnh: Nếu rỗng thì dùng noimg.jpg trong uploads --%>
                        <c:choose>
                            <c:when test="${empty item.hinhAnh || item.hinhAnh == 'null'}">
                                <c:set var="finalImg" value="${ctx}/uploads/noimg.jpg"/>
                            </c:when>
                            <c:when test="${item.hinhAnh.startsWith('http')}">
                                <c:set var="finalImg" value="${item.hinhAnh}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="finalImg" value="${ctx}/uploads/${item.hinhAnh}"/>
                            </c:otherwise>
                        </c:choose>

                        <img src="${finalImg}" class="card-img-top" alt="${item.tenDoUong}"
                             onerror="this.onerror=null; this.src='${ctx}/uploads/noimg.jpg';">

                        <c:if test="${not item.trangThai}">
                            <div style="position: absolute; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.6); display:flex; align-items:center; justify-content:center;">
                                <span class="badge bg-danger px-3 py-2">HẾT HÀNG</span>
                            </div>
                        </c:if>
                    </div>

                    <div class="card-body">
                        <h5 class="card-title text-warning">${item.tenDoUong}</h5>
                        <p class="card-text text-secondary small">${item.moTa}</p>
                    </div>

                    <div class="card-footer bg-transparent border-0 p-3 d-flex justify-content-between align-items-center">
                        <span class="text-white fw-bold fs-5">
                            <fmt:formatNumber value="${item.giaTien}" pattern="#,###"/> <small>đ</small>
                        </span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>