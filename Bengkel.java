import java.util.ArrayList;

/**
 * Class utama bengkel. Memiliki (komposisi) daftar kendaraan berbentuk Sorted List,
 * daftar mekanik, dan daftar servis.
 */
public class Bengkel {
    private String namaBengkel;
    private SortedListKendaraan daftarKendaraan;
    private ArrayList<Mekanik> daftarMekanik;
    private ArrayList<Servis> daftarServis;

    public Bengkel() {
        this("Bengkel Default", 10);
    }

    public Bengkel(String namaBengkel, int kapasitasKendaraan) {
        this.namaBengkel = (namaBengkel == null || namaBengkel.trim().isEmpty())
                ? "Bengkel Default" : namaBengkel.trim();
        this.daftarKendaraan = new SortedListKendaraan(kapasitasKendaraan);
        this.daftarMekanik = new ArrayList<>();
        this.daftarServis = new ArrayList<>();
    }

    public String getNamaBengkel() { return namaBengkel; }
    public SortedListKendaraan getDaftarKendaraan() { return daftarKendaraan; }
    public ArrayList<Mekanik> getDaftarMekanik() { return daftarMekanik; }
    public ArrayList<Servis> getDaftarServis() { return daftarServis; }

    /** Menambah kendaraan. @return false jika list penuh atau plat duplikat. */
    public boolean tambahKendaraan(Kendaraan k) {
        return daftarKendaraan.insert(k);
    }

    /** Menambah mekanik; ID tidak boleh duplikat. */
    public void tambahMekanik(Mekanik m) {
        if (m == null) throw new IllegalArgumentException("Mekanik tidak boleh null.");
        if (cariMekanik(m.getIdMekanik()) != null)
            throw new IllegalArgumentException("ID mekanik " + m.getIdMekanik() + " sudah terdaftar.");
        daftarMekanik.add(m);
    }

    /**
     * Membuat servis baru. Aturan bisnis:
     * kendaraan & mekanik harus ada, kode unik, mekanik tidak sedang bertugas,
     * kendaraan tidak sedang punya servis aktif.
     */
    public Servis buatServis(String kode, String plat, String idMekanik, double jasa, double sukuCadang) {
        Kendaraan k = daftarKendaraan.search(plat);
        if (k == null) throw new IllegalArgumentException("Kendaraan dengan plat " + plat + " tidak ditemukan.");
        Mekanik m = cariMekanik(idMekanik);
        if (m == null) throw new IllegalArgumentException("Mekanik dengan ID " + idMekanik + " tidak ditemukan.");
        if (cariServis(kode) != null) throw new IllegalArgumentException("Kode servis " + kode + " sudah dipakai.");
        for (Servis s : daftarServis) {
            if (s.isStatusAktif() && s.getKendaraan() == k)
                throw new IllegalStateException("Kendaraan " + k.getPlatNomor() + " masih memiliki servis aktif.");
        }
        Servis baru = new Servis(kode, k, m, jasa, sukuCadang); // melempar exception jika mekanik bertugas
        daftarServis.add(baru);
        return baru;
    }

    /** Menyelesaikan servis berdasarkan kode. */
    public void selesaikanServis(String kode) {
        Servis s = cariServis(kode);
        if (s == null) throw new IllegalArgumentException("Servis " + kode + " tidak ditemukan.");
        s.selesaikanServis();
    }

    public Mekanik cariMekanik(String id) {
        if (id == null) return null;
        for (Mekanik m : daftarMekanik)
            if (m.getIdMekanik().equalsIgnoreCase(id.trim())) return m;
        return null;
    }

    public Servis cariServis(String kode) {
        if (kode == null) return null;
        for (Servis s : daftarServis)
            if (s.getKodeServis().equalsIgnoreCase(kode.trim())) return s;
        return null;
    }

    /** Ringkasan bengkel: jumlah data dan total biaya servis. */
    public void tampilkanRingkasan() {
        int aktif = 0;
        double total = 0;
        for (Servis s : daftarServis) {
            if (s.isStatusAktif()) aktif++;
            total += s.hitungTotalBiaya();
        }
        System.out.println("=== RINGKASAN " + namaBengkel.toUpperCase() + " ===");
        System.out.println("Jumlah kendaraan : " + daftarKendaraan.size() + " / " + daftarKendaraan.getKapasitas());
        System.out.println("Jumlah mekanik   : " + daftarMekanik.size());
        System.out.println("Jumlah servis    : " + daftarServis.size() + " (aktif: " + aktif + ")");
        System.out.printf("Total biaya servis: Rp%,.0f%n", total);
    }
}
