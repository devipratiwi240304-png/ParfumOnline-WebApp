<%-- 
    Document   : index
    Created on : 26 Des 2025, 20.47.20
    Author     : Devi Pratiwi
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Parfum Online - Sistem Manajemen Parfum</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
        }
        .container {
            text-align: center;
            max-width: 800px;
            padding: 40px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 15px;
            backdrop-filter: blur(10px);
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
        }
        h1 {
            font-size: 48px;
            margin-bottom: 20px;
            color: white;
        }
        p {
            font-size: 18px;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        .btn {
            display: inline-block;
            padding: 15px 30px;
            background: white;
            color: #667eea;
            text-decoration: none;
            border-radius: 50px;
            font-weight: 600;
            font-size: 18px;
            margin: 10px;
            transition: all 0.3s ease;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
        }
        .features {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 40px;
        }
        .feature {
            margin: 15px;
            padding: 20px;
            background: rgba(255, 255, 255, 0.15);
            border-radius: 10px;
            width: 200px;
        }
        .feature i {
            font-size: 40px;
            margin-bottom: 15px;
            color: #ffd700;
        }
        .feature h3 {
            margin: 10px 0;
        }
        .logo {
            font-size: 60px;
            margin-bottom: 20px;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">
            <i class="fas fa-wind"></i>
        </div>
        <h1>Parfum Online</h1>
        <p>Sistem Manajemen Parfum Terintegrasi untuk Admin dan Karyawan.<br>
           Kelola produk, transaksi, laporan, dan penggajian dalam satu aplikasi.</p>
        
        <div>
            <a href="${pageContext.request.contextPath}/views/auth/login.jsp" class="btn">
                <i class="fas fa-sign-in-alt"></i> Login Sekarang
            </a>
            <a href="#features" class="btn">
                <i class="fas fa-info-circle"></i> Pelajari Fitur
            </a>
        </div>
        
        <div class="features" id="features">
            <div class="feature">
                <i class="fas fa-cube"></i>
                <h3>Manajemen Produk</h3>
                <p>Kelola data parfum, stok, dan harga</p>
            </div>
            <div class="feature">
                <i class="fas fa-shopping-cart"></i>
                <h3>Transaksi</h3>
                <p>Proses penjualan cepat dan akurat</p>
            </div>
            <div class="feature">
                <i class="fas fa-chart-line"></i>
                <h3>Laporan</h3>
                <p>Laporan penjualan dan stok lengkap</p>
            </div>
            <div class="feature">
                <i class="fas fa-users"></i>
                <h3>Penggajian</h3>
                <p>Kelola gaji karyawan dengan mudah</p>
            </div>
        </div>
        
        <div style="margin-top: 40px; font-size: 14px; color: rgba(255,255,255,0.7);">
            <p>© 2023 Parfum Online. Dibangun dengan Java Servlet & JSP</p>
        </div>
    </div>
</body>
</html>