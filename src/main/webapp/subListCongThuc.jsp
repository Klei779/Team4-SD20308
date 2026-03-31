<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %> <%-- QUAN TRỌNG: Phải có dòng này để chạy EL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="item" items="${listSub}">
    <div class="ingredient-item d-flex justify-content-between align-items-center mb-2 p-2 border-start border-info"
         style="background: #1a1a1a; border-radius: 4px; border-left: 4px solid #0dcaf0 !important;">
        <div>
            <c:forEach var="nl" items="${listNL}">
                <c:if test="${nl.maNguyenLieu == item.maNguyenLieu}">
                    <div class="fw-bold text-info">${nl.tenNguyenLieu}</div>
                    <small class="text-white-50">
                        Định lượng: <b class="text-white">${item.dinhLuong}</b> ${nl.donVi}
                    </small>
                </c:if>
            </c:forEach>
        </div>
        <button class="btn btn-sm text-danger border-0" onclick="deleteSub(${item.maCTCT})">
            <i class="fas fa-trash-alt"></i>
        </button>
    </div>
</c:forEach>