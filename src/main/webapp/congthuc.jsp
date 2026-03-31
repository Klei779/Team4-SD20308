<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý công thức - Gold Dark Style</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --bg-dark: #121212;
            --card-dark: #1e1e1e;
            --accent-gold: #ffc107;
            --accent-hover: #e0a800;
            --border-color: #333;
        }

        body {
            background-color: var(--bg-dark);
            color: #ffffff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            background-color: var(--card-dark) !important;
            border: 1px solid var(--border-color) !important;
            border-radius: 12px;
        }

        /* Tiêu đề trắng */
        .card-header h5 {
            color: #ffffff !important;
        }

        /* Input tối và Placeholder */
        .form-control {
            background-color: #1a1a1a !important;
            border: 1px solid var(--border-color) !important;
            color: white !important;
        }
        .form-control::placeholder {
            color: #666 !important;
        }

        .btn-gold {
            background-color: var(--accent-gold);
            color: #000;
            font-weight: 600;
            border-radius: 8px;
        }

        /* SỬA LỖI NỀN TRẮNG Ở TABLE */
        .table-dark-custom {
            background-color: transparent !important;
            color: #ffffff !important;
            margin-bottom: 0;
        }

        .table-dark-custom thead th {
            background-color: #252525 !important;
            color: var(--accent-gold) !important;
            border-bottom: 2px solid var(--border-color) !important;
            padding: 15px;
        }

        /* Ép tất cả td về nền tối */
        .table-dark-custom td {
            background-color: var(--card-dark) !important;
            color: white !important;
            padding: 15px;
            border-bottom: 1px solid var(--border-color) !important;
        }

        .table-dark-custom tbody tr:hover td {
            background-color: #252525 !important; /* Hiệu ứng hover cho dòng */
        }

        .btn-action {
            width: 38px;
            height: 38px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 8px;
            border: none;
        }

        .badge-status {
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: 600;
            background-color: transparent;
            color: var(--accent-gold);
            border: 1px solid var(--accent-gold);
        }

        .modal-content {
            background-color: var(--card-dark);
            border: 1px solid var(--accent-gold);
        }
    </style>
</head>
<body>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="container mt-5">
    <!-- Khu vực thêm mới -->
    <div class="card shadow-lg mb-4 p-2" style="background-color: #1a1a1a !important;">
        <div class="card-body">
            <form action="${ctx}/congthuc" method="post" class="row g-3">
                <input type="hidden" name="action" value="insert">
                <div class="col-md-9">
                    <!-- Đã thêm placeholder theo yêu cầu -->
                    <input name="ten" class="form-control py-2" placeholder="Nhập tên công thức mới..." required>
                </div>
                <div class="col-md-3">
                    <button class="btn btn-gold w-100 h-100 fw-bold">
                        <i class="bi bi-plus-circle-fill me-2"></i> THÊM MỚI
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Danh sách -->
    <div class="card shadow-lg">
        <div class="card-header py-3">
            <h5 class="mb-0 fw-bold"><i class="bi bi-journal-text text-gold me-2"></i> QUẢN LÝ CÔNG THỨC</h5>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-dark-custom align-middle">
                    <thead>
                    <tr>
                        <th class="ps-4" width="80"># ID</th>
                        <th>Tên công thức</th>
                        <th class="text-center">Trạng thái</th>
                        <th class="text-end pe-4">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ct" items="${list}">
                        <tr>
                            <td class="ps-4" style="color: #666 !important;">${ct.maCongThuc}</td>
                            <td><span class="fw-bold">${ct.tenCongThuc}</span></td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${ct.trangThai}">
                                        <span class="badge badge-status">ĐANG HIỆN</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge" style="color: #666; border: 1px solid #444;">ĐÃ ẨN</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end pe-4">
                                <!-- Sửa -->
                                <button class="btn-action bg-warning" onclick="openModal(${ct.maCongThuc})">
                                    <i class="bi bi-pencil-square text-dark"></i>
                                </button>

                                <!-- Ẩn/Hiện -->
                                <form action="${ctx}/congthuc" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="softDelete">
                                    <input type="hidden" name="id" value="${ct.maCongThuc}">
                                    <c:choose>
                                        <c:when test="${ct.trangThai}">
                                            <button class="btn-action" style="background-color: #555;">
                                                <i class="bi bi-eye-slash-fill text-white"></i>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn-action" style="background-color: #28a745;">
                                                <i class="bi bi-eye-fill text-white"></i>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>

                                <!-- Xóa -->
                                <form action="${ctx}/congthuc" method="post" style="display:inline;" onsubmit="return confirm('Xóa vĩnh viễn?')">
                                    <input type="hidden" name="action" value="resetData">
                                    <input type="hidden" name="id" value="${ct.maCongThuc}">
                                    <button class="btn-action bg-danger">
                                        <i class="bi bi-trash3-fill text-white"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- MODAL CHI TIẾT -->
<div class="modal fade" id="ctModal" tabindex="-1">
    <div class="modal-dialog modal-lg text-white">
        <div class="modal-content">
            <div class="modal-header border-secondary">
                <h5 class="modal-title fw-bold">CHI TIẾT CÔNG THỨC #<span id="displayID"></span></h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body p-4">
                <input type="hidden" id="maCT_Hidden">
                <table class="table table-dark-custom mb-4">
                    <thead>
                    <tr class="text-gold">
                        <th>Nguyên liệu</th>
                        <th>Định lượng</th>
                        <th class="text-center">Xóa</th>
                    </tr>
                    </thead>
                    <tbody id="ctctTable"></tbody>
                </table>

                <div class="mt-4 p-3 rounded-4" style="background-color: #1a1a1a; border: 1px solid #333;">
                    <div class="row g-2">
                        <div class="col-md-5">
                            <select id="selNL" class="form-select bg-dark text-white border-secondary"></select>
                        </div>
                        <div class="col-md-4">
                            <input id="inpDL" type="number" class="form-control bg-dark text-white border-secondary" placeholder="Định lượng">
                        </div>
                        <div class="col-md-3">
                            <button onclick="addNL()" class="btn btn-gold w-100">THÊM</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // JS giữ nguyên từ file trước...
    const ctx = "${pageContext.request.contextPath}";
    let modalObj = null;
    window.onload = loadNL;
    function openModal(maCT) {
        document.getElementById("maCT_Hidden").value = maCT;
        document.getElementById("displayID").innerText = maCT;
        refreshTable(maCT);
        if(!modalObj) modalObj = new bootstrap.Modal(document.getElementById('ctModal'));
        modalObj.show();
    }
    function loadNL() {
        fetch(ctx + "/congthuc?action=getNguyenLieu")
            .then(res => res.json())
            .then(data => {
                const s = document.getElementById("selNL");
                s.innerHTML = '<option value="">-- Chọn nguyên liệu --</option>';
                data.forEach(n => {
                    let opt = document.createElement("option");
                    opt.value = n.id;
                    opt.textContent = n.ten;
                    s.appendChild(opt);
                });
            });
    }
    function refreshTable(maCT) {
        fetch(ctx + "/congthuc?action=getCTCT&id=" + maCT)
            .then(res => res.json())
            .then(data => {
                let html = "";
                data.forEach(item => {
                    html += `<tr>
                        <td class="fw-bold text-white">\${item.tenNL}</td>
                        <td class="text-gold fw-bold">\${item.dinhLuong}</td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteNL(\${item.maCTCT}, \${maCT})">
                                <i class="bi bi-x-lg"></i>
                            </button>
                        </td>
                    </tr>`;
                });
                document.getElementById("ctctTable").innerHTML = html || '<tr><td colspan="3" class="text-center text-secondary py-4 small">Trống</td></tr>';
            });
    }
    function addNL() {
        const maCT = document.getElementById("maCT_Hidden").value;
        const maNL = document.getElementById("selNL").value;
        const dl = document.getElementById("inpDL").value;
        if(!maNL || !dl) return alert("Vui lòng điền đủ!");
        fetch(ctx + "/congthuc", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: new URLSearchParams({ action: "addCTCT_AJAX", maCongThuc: maCT, maNguyenLieu: maNL, dinhLuong: dl })
        }).then(() => { refreshTable(maCT); document.getElementById("inpDL").value = ""; });
    }
    function deleteNL(idXoa, maCT) {
        if(!confirm("Xóa dòng này?")) return;
        fetch(ctx + "/congthuc", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: new URLSearchParams({ action: "deleteCTCT_AJAX", id: idXoa })
        }).then(() => refreshTable(maCT));
    }
</script>
</body>
</html>