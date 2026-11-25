/**
 * Entitas Servis yang menghubungkan Kendaraan dan Mekanik.
 * Biaya servis = biaya jasa + biaya suku cadang.
 */
public class Servis {
    private String kodeServis;
    private Kendaraan kendaraan;
    private Mekanik mekanik;
    private double biayaJasa;
    private double biayaSukuCadang;
    private boolean statusAktif;

    /** Konstruktor default (tanpa kendaraan/mekanik, tidak aktif). */
    public Servis() {
        this.kodeServis = "-";
        this.biayaJasa = 0;
        this.biayaSukuCadang = 0;
        this.statusAktif = false;
    }

    /**
     * Konstruktor berparameter. Servis langsung aktif dan mekanik ditandai bertugas.
     * Melempar IllegalStateException bila mekanik sedang bertugas.
     */
    public Servis(String kodeServis, Kendaraan kendaraan, Mekanik mekanik,
                  double biayaJasa, double biayaSukuCadang) {
        setKodeServis(kodeServis);
        setKendaraan(kendaraan);
        setMekanik(mekanik);
        setBiayaJasa(biayaJasa);
        setBiayaSukuCadang(biayaSukuCadang);
        mekanik.mulaiServis();      // aturan: 1 mekanik = 1 servis aktif
        this.statusAktif = true;
    }

    public String getKodeServis() { return kodeServis; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Mekanik getMekanik() { return mekanik; }
    public double getBiayaJasa() { return biayaJasa; }
    public double getBiayaSukuCadang() { return biayaSukuCadang; }
    public boolean isStatusAktif() { return statusAktif; }

    public void setKodeServis(String kodeServis) {
        if (kodeServis == null || kodeServis.trim().isEmpty())
            throw new IllegalArgumentException("Kode servis tidak boleh kosong.");
        this.kodeServis = kodeServis.trim().toUpperCase();
    }

    public void setKendaraan(Kendaraan kendaraan) {
        if (kendaraan == null) throw new IllegalArgumentException("Kendaraan tidak boleh null.");
        this.kendaraan = kendaraan;
    }

    public void setMekanik(Mekanik mekanik) {
        if (mekanik == null) throw new IllegalArgumentException("Mekanik tidak boleh null.");
        this.mekanik = mekanik;
    }

    public void setBiayaJasa(double biayaJasa) {
        if (biayaJasa < 0) throw new IllegalArgumentException("Biaya jasa tidak boleh negatif.");
        this.biayaJasa = biayaJasa;
    }

    public void setBiayaSukuCadang(double biayaSukuCadang) {
        if (biayaSukuCadang < 0) throw new IllegalArgumentException("Biaya suku cadang tidak boleh negatif.");
        this.biayaSukuCadang = biayaSukuCadang;
    }

    /** Total biaya = jasa + suku cadang. */
    public double hitungTotalBiaya() {
        return biayaJasa + biayaSukuCadang;
    }

    /** Menyelesaikan servis dan membebaskan mekanik. */
    public void selesaikanServis() {
        if (!statusAktif)
            throw new IllegalStateException("Servis " + kodeServis + " sudah selesai.");
        this.statusAktif = false;
        mekanik.selesaiServis();
    }

    /** Menampilkan informasi servis ke console. */
    public void tampilkanInfo() {
        System.out.printf("Kode: %-6s | Plat: %-10s | Mekanik: %-10s | Jasa: Rp%,.0f | Suku cadang: Rp%,.0f | Total: Rp%,.0f | %s%n",
                kodeServis, kendaraan.getPlatNomor(), mekanik.getNama(),
                biayaJasa, biayaSukuCadang, hitungTotalBiaya(),
                statusAktif ? "AKTIF" : "SELESAI");
    }
}
