<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Poly Coffee - Quản lý Kho & Công Thức</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body { background: #0a0a0a; color: #eee; height: 100vh; overflow: hidden; display: flex; flex-direction: column; font-family: 'Segoe UI', sans-serif; }
        .section-half { height: 50vh; padding: 20px; overflow-y: auto; border-bottom: 1px solid #222; }
        .card-nl { background: #111; border: 1px solid #222; border-radius: 12px; padding: 15px; transition: 0.3s; }
        .card-nl:hover { border-color: #0dcaf0; }
        .progress-thin { height: 6px; background: #222; border-radius: 3px; margin: 10px 0; border: 1px solid #333; }
        .modal-content { background: #151515; color: white; border: 1px solid #333; border-radius: 15px; }
        .form-control, .form-select { background: #1a1a1a !important; border: 1px solid #333 !important; color: white !important; }
        .bg-black-section { background-color: #050505; }
        .custom-scroll::-webkit-scrollbar { width: 6px; }
        .custom-scroll::-webkit-scrollbar-thumb { background: #333; border-radius: 10px; }
    </style>
</head>
<body class="custom-scroll">

<%-- THÔNG BÁO --%>
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-success alert-dismissible fade show m-2 position-fixed top-0 end-0 shadow-lg" style="z-index: 9999;">
            ${sessionScope.message}
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("message"); %>
</c:if>

<%-- PHẦN 1: KHO NGUYÊN LIỆU --%>
<div class="section-half">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="text-success m-0"><i class="fas fa-warehouse me-2"></i>KHO NGUYÊN LIỆU</h5>
        <button class="btn btn-success btn-sm px-3" data-bs-toggle="modal" data-bs-target="#modalAddNL">
            <i class="fas fa-plus me-1"></i> Thêm Nguyên Liệu
        </button>
    </div>
    <div class="row g-3">
        <c:forEach var="nl" items="${list}">
            <div class="col-md-3">
                <div class="card-nl border-${nl.soLuongTon <= nl.soLuongToiThieu ? 'danger' : 'secondary'}">
                    <div class="d-flex justify-content-between small mb-1">
                        <span class="fw-bold">${nl.tenNguyenLieu}</span>
                        <span class="text-secondary">${nl.soLuongTon} / ${nl.soLuongToiThieu}</span>
                    </div>
                    <div class="progress-thin">
                        <c:set var="percent" value="${(nl.soLuongTon * 100) / (nl.soLuongToiThieu == 0 ? 100 : nl.soLuongToiThieu * 2)}" />
                        <div class="progress-bar bg-${nl.soLuongTon <= nl.soLuongToiThieu ? 'danger' : 'success'}" style="width: ${percent > 100 ? 100 : percent}%"></div>
                    </div>
                    <div class="d-flex justify-content-between align-items-end">
                        <div class="fw-bold fs-5">${nl.soLuongTon} <small class="text-secondary fs-6">${nl.donVi}</small></div>
                        <button class="btn btn-sm btn-outline-info border-0" onclick="openNhapKho('${nl.maNguyenLieu}', '${nl.tenNguyenLieu}')"><i class="fas fa-plus"></i> Nhập</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%-- PHẦN 2: CÔNG THỨC --%>
<div class="section-half bg-black-section">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="text-info m-0"><i class="fas fa-receipt me-2"></i>CÔNG THỨC PHA CHẾ</h5>
        <button class="btn btn-info btn-sm px-3" data-bs-toggle="modal" data-bs-target="#modalAddCT">
            <i class="fas fa-magic me-1"></i> Tạo Công Thức
        </button>
    </div>
    <table class="table table-dark table-hover align-middle">
        <thead>
        <tr>
            <th width="10%">Mã</th><th width="50%">Tên món</th><th width="40%" class="text-center">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ct" items="${listCongThuc}">
            <tr>
                <td class="text-secondary">#${ct.maCongThuc}</td>
                <td class="fw-bold text-info">${ct.tenCongThuc}</td>
                <td class="text-center">
                    <div class="btn-group">
                        <button class="btn btn-outline-info btn-sm" onclick="openEditCT('${ct.maCongThuc}', '${ct.tenCongThuc}')"><i class="fas fa-flask"></i> Định lượng</button>
                        <button class="btn btn-outline-light btn-sm" onclick="renameCT('${ct.maCongThuc}', '${ct.tenCongThuc}')"><i class="fas fa-edit"></i> Sửa</button>
                        <button class="btn btn-outline-warning btn-sm" onclick="softDeleteCT('${ct.maCongThuc}')"><i class="fas fa-eye-slash"></i> Ẩn</button>
                        <button class="btn btn-outline-danger btn-sm" onclick="resetCT('${ct.maCongThuc}')"><i class="fas fa-bomb"></i> Xóa sạch</button>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="modalAddNL"><div class="modal-dialog"><div class="modal-content">
    <form action="khonguyenlieu" method="post"><input type="hidden" name="action" value="add">
        <div class="modal-header border-0"><h5 class="modal-title">Thêm Nguyên Liệu</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <div class="modal-body">
            <input type="text" name="ten" class="form-control mb-3" placeholder="Tên nguyên liệu" required>
            <div class="row"><div class="col-6"><input type="number" name="soLuong" class="form-control mb-3" placeholder="Số lượng"></div><div class="col-6"><input type="text" name="donVi" class="form-control mb-3" placeholder="Đơn vị"></div></div>
            <select name="maLoai" class="form-select mb-3"><c:forEach var="l" items="${listLoai}"><option value="${l.maLoaiNguyenLieu}">${l.tenLoaiNguyenLieu}</option></c:forEach></select>
        </div>
        <div class="modal-footer border-0"><button type="submit" class="btn btn-success w-100">LƯU LẠI</button></div>
    </form>
</div></div></div>

<div class="modal fade" id="modalAddCT"><div class="modal-dialog modal-sm"><div class="modal-content">
    <form action="khonguyenlieu" method="post"><input type="hidden" name="action" value="addCT">
        <div class="modal-header border-0"><h5 class="modal-title text-info">Món Mới</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <div class="modal-body"><input type="text" name="tenCongThuc" class="form-control" placeholder="Tên đồ uống..." required></div>
        <div class="modal-footer border-0"><button type="submit" class="btn btn-info w-100">KHỞI TẠO</button></div>
    </form>
</div></div></div>

<div class="modal fade" id="modalNhapKho"><div class="modal-dialog modal-sm"><div class="modal-content">
    <form action="khonguyenlieu" method="post"><input type="hidden" name="action" value="nhap"><input type="hidden" name="id" id="nhap-id">
        <div class="modal-header border-0"><h6>Nhập thêm: <span id="nhap-ten"></span></h6></div>
        <div class="modal-body"><input type="number" name="soLuongNhap" class="form-control" placeholder="Số lượng..." required></div>
        <div class="modal-footer border-0"><button type="submit" class="btn btn-success w-100">NHẬP KHO</button></div>
    </form>
</div></div></div>

<div class="modal fade" id="modalEditCT" data-bs-backdrop="static">
    <div class="modal-dialog modal-lg"><div class="modal-content">
        <div class="modal-header border-0"><h5 class="text-info">ĐỊNH LƯỢNG: <span id="label-ten-ct"></span></h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
        <div class="modal-body"><div class="row">
            <div class="col-md-5 border-end border-secondary">
                <input type="hidden" id="current-maCT">
                <label class="small text-secondary mb-1">Chọn nguyên liệu</label>
                <select id="select-nl" class="form-select mb-3">
                    <c:forEach var="nl" items="${list}"><option value="${nl.maNguyenLieu}">${nl.tenNguyenLieu} (${nl.donVi})</option></c:forEach>
                </select>
                <label class="small text-secondary mb-1">Số lượng sử dụng</label>
                <div class="input-group"><input type="number" id="input-dl" class="form-control" placeholder="0"><button class="btn btn-info" onclick="addIngredient()"><i class="fas fa-plus"></i> Thêm</button></div>
            </div>
            <div class="col-md-7">
                <h6 class="text-success small fw-bold mb-3">DANH SÁCH THÀNH PHẦN</h6>
                <div id="sub-list-container" class="custom-scroll" style="max-height: 350px; overflow-y: auto;"></div>
            </div>
        </div></div>
    </div></div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function openNhapKho(id, ten) {
        document.getElementById('nhap-id').value = id;
        document.getElementById('nhap-ten').innerText = ten;
        new bootstrap.Modal(document.getElementById('modalNhapKho')).show();
    }

    function openEditCT(id, ten) {
        document.getElementById('label-ten-ct').innerText = ten;
        document.getElementById('current-maCT').value = id;
        loadSubList(id);
        new bootstrap.Modal(document.getElementById('modalEditCT')).show();
    }

    function loadSubList(maCT) {
        // Nối chuỗi truyền thống để tránh lỗi EL của JSP
        fetch('khonguyenlieu?action=getListCTCT&maCongThuc=' + maCT)
            .then(res => res.text())
            .then(html => document.getElementById('sub-list-container').innerHTML = html);
    }

    function addIngredient() {
        var maCT = document.getElementById('current-maCT').value;
        var maNL = document.getElementById('select-nl').value;
        var dl = document.getElementById('input-dl').value;

        if(!dl || dl <= 0) {
            alert("Vui lòng nhập định lượng hợp lệ!");
            return;
        }

        var p = new URLSearchParams();
        p.append('action', 'addCTCT');
        p.append('maCongThuc', maCT);
        p.append('maNguyenLieu', maNL);
        p.append('dinhLuong', dl);

        fetch('khonguyenlieu', { method: 'POST', body: p })
            .then(res => {
                if(res.ok) {
                    loadSubList(maCT);
                    document.getElementById('input-dl').value = ""; // Xóa số cũ
                } else {
                    alert("Lỗi khi thêm nguyên liệu!");
                }
            });
    }

    function deleteSub(idCTCT) {
        if(!confirm("Xóa thành phần?")) return;
        var p = new URLSearchParams();
        p.append('action', 'deleteCTCT'); p.append('idCTCT', idCTCT);
        fetch('khonguyenlieu', { method: 'POST', body: p })
            .then(() => loadSubList(document.getElementById('current-maCT').value));
    }

    function renameCT(id, old) {
        var n = prompt("Tên mới:", old);
        if(n && n.trim() !== "" && n !== old) submitForm('updateName', { maCongThuc: id, tenMoi: n });
    }
    function softDeleteCT(id) { if(confirm("Ẩn món này?")) submitForm('softDelete', { maCongThuc: id }); }
    function resetCT(id) { if(confirm("Xóa sạch định lượng?")) submitForm('resetCT', { maCongThuc: id }); }

    function submitForm(action, params) {
        const form = document.createElement('form'); form.method = 'POST'; form.action = 'khonguyenlieu';
        const act = document.createElement('input'); act.type = 'hidden'; act.name = 'action'; act.value = action;
        form.appendChild(act);
        for (const key in params) {
            const inp = document.createElement('input'); inp.type = 'hidden'; inp.name = key; inp.value = params[key];
            form.appendChild(inp);
        }
        document.body.appendChild(form); form.submit();
    }
</script>
</body>
</html>