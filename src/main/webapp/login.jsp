<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập Hệ thống</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #0d0d0d; color: white; display: flex; align-items: center; height: 100vh; justify-content: center; }
        .login-card { background: #161616; padding: 30px; border-radius: 15px; border: 1px solid #333; width: 350px; }
    </style>
</head>
<body>
<div class="login-card shadow">
    <h4 class="text-center text-warning mb-4">POLY COFFEE</h4>
    <form action="login" method="post">
        <div class="mb-3">
            <label class="small">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control bg-dark text-white border-secondary" required>
        </div>
        <div class="mb-3">
            <label class="small">Mật khẩu</label>
            <input type="password" name="password" class="form-control bg-dark text-white border-secondary" required>
        </div>
        <button type="submit" class="btn btn-warning w-100 fw-bold">ĐĂNG NHẬP</button>
    </form>
</div>
</body>
</html>