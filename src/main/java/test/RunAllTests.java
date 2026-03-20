package test;

public class RunAllTests {

    public static void main(String[] args) {

        System.out.println("===== BẮT ĐẦU CHẠY TEST =====\n");

        try {
            System.out.println(">>> Test NguoiDungDAO");
            TestNguoiDungDAO.main(null);

            System.out.println("\n>>> Test NguyenLieuDAO");
            TestNguyenLieuDAO.main(null);

            System.out.println("\n>>> Test LoaiDoUongDAO");
            TestLoaiDoUongDAO.main(null);

            System.out.println("\n>>> Test HoaDonDAO");
            TestHoaDonDAO.main(null);

            System.out.println("\n>>> Test HoaDonChiTietDAO");
            TestHoaDonChiTietDAO.main(null);

            System.out.println("\n>>> Test PhieuNhapKhoDAO");
            TestPhieuNhapKhoDAO.main(null);

            System.out.println("\n>>> Test PhieuNhapKhoChiTietDAO");
            TestPhieuNhapKhoChiTietDAO.main(null);

            System.out.println("\n>>> Test ThongKeDAO");
            TestThongKeDAO.main(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n===== HOÀN TẤT TEST =====");
    }
}