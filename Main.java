import java.util.Scanner;

/** Class Main: menu console aplikasi manajemen bengkel. */
public class Main {
    private Scanner scanner;
    private Bengkel bengkel;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.bengkel = new Bengkel("Bengkel Maju Jaya", 5);
    }

    public static void main(String[] args) {
        Main app = new Main();
        int pilihan;
        do {
            app.tampilkanMenu();
            pilihan = app.bacaInt("Pilih menu: ");
            app.prosesPilihan(pilihan);
        } while (pilihan != 0);
    }

    public void tampilkanMenu() {
        System.out.println("\n===== MENU BENGKEL =====");
        System.out.println("1. Tambah kendaraan");
        System.out.println("2. Tampilkan kendaraan (terurut plat)");
        System.out.println("3. Tambah mekanik");
        System.out.println("4. Tampilkan mekanik");
        System.out.println("5. Buat servis");
        System.out.println("6. Selesaikan servis");
        System.out.println("7. Cari kendaraan (plat)");
        System.out.println("8. Hapus kendaraan (plat)");
        System.out.println("9. Tampilkan semua servis");
        System.out.println("10. Ringkasan bengkel");
        System.out.println("0. Keluar");
    }

    public void prosesPilihan(int pilihan) {
        try {
            switch (pilihan) {
                case 1: tambahKendaraan(); break;
                case 2: bengkel.getDaftarKendaraan().tampilkan(); break;
                case 3: tambahMekanik(); break;
                case 4: tampilkanMekanik(); break;
                case 5: buatServis(); break;
                case 6:
                    bengkel.selesaikanServis(bacaString("Kode servis: "));
                    System.out.println("Servis berhasil diselesaikan.");
                    break;
                case 7: cariKendaraan(); break;
                case 8: hapusKendaraan(); break;
                case 9: tampilkanServis(); break;
                case 10: bengkel.tampilkanRingkasan(); break;
                case 0: System.out.println("Terima kasih. Program selesai."); break;
                default: System.out.println("[ERROR] Pilihan tidak valid.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void tambahKendaraan() {
        String plat = bacaString("Plat nomor: ");
        String merk = bacaString("Merk: ");
        String model = bacaString("Model: ");
        int tahun = bacaInt("Tahun: ");
        Kendaraan k = new Kendaraan(plat, merk, model, tahun);
        if (bengkel.getDaftarKendaraan().isFull())
            System.out.println("[ERROR] Daftar kendaraan penuh (kapasitas "
                    + bengkel.getDaftarKendaraan().getKapasitas() + ").");
        else if (!bengkel.tambahKendaraan(k))
            System.out.println("[ERROR] Plat " + k.getPlatNomor() + " sudah terdaftar.");
        else
            System.out.println("Kendaraan berhasil ditambahkan.");
    }

    private void tambahMekanik() {
        String id = bacaString("ID mekanik: ");
        String nama = bacaString("Nama: ");
        String spes = bacaString("Spesialisasi: ");
        bengkel.tambahMekanik(new Mekanik(id, nama, spes));
        System.out.println("Mekanik berhasil ditambahkan.");
    }

    private void tampilkanMekanik() {
        if (bengkel.getDaftarMekanik().isEmpty()) {
            System.out.println("(Belum ada mekanik)");
            return;
        }
        for (Mekanik m : bengkel.getDaftarMekanik()) m.tampilkanInfo();
    }

    private void buatServis() {
        String kode = bacaString("Kode servis: ");
        String plat = bacaString("Plat kendaraan: ");
        String idM = bacaString("ID mekanik: ");
        double jasa = bacaDouble("Biaya jasa: ");
        double suku = bacaDouble("Biaya suku cadang: ");
        Servis s = bengkel.buatServis(kode, plat, idM, jasa, suku);
        System.out.println("Servis dibuat. Total biaya: Rp" + String.format("%,.0f", s.hitungTotalBiaya()));
    }

    private void cariKendaraan() {
        Kendaraan k = bengkel.getDaftarKendaraan().search(bacaString("Plat dicari: "));
        if (k == null) System.out.println("Kendaraan tidak ditemukan.");
        else k.tampilkanInfo();
    }

    private void hapusKendaraan() {
        if (bengkel.getDaftarKendaraan().isEmpty()) {
            System.out.println("[ERROR] Daftar kendaraan kosong.");
            return;
        }
        String plat = bacaString("Plat dihapus: ");
        for (Servis s : bengkel.getDaftarServis()) {
            if (s.isStatusAktif() && s.getKendaraan().getPlatNomor().equalsIgnoreCase(plat.trim())) {
                System.out.println("[ERROR] Kendaraan masih memiliki servis aktif.");
                return;
            }
        }
        System.out.println(bengkel.getDaftarKendaraan().remove(plat)
                ? "Kendaraan dihapus." : "[ERROR] Kendaraan tidak ditemukan.");
    }

    private void tampilkanServis() {
        if (bengkel.getDaftarServis().isEmpty()) {
            System.out.println("(Belum ada servis)");
            return;
        }
        for (Servis s : bengkel.getDaftarServis()) s.tampilkanInfo();
    }

    // ---- helper input aman (tidak crash pada input salah) ----
    private String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    private int bacaInt(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) return 0;
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Input harus berupa angka bulat.");
            return -1;
        }
    }

    private double bacaDouble(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) return 0;
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Input harus berupa angka.");
            return -1; // ditolak oleh validasi setter
        }
    }
}
