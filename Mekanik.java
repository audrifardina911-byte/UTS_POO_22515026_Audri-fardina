/**
 * Entitas Mekanik. Aturan bisnis: satu mekanik hanya boleh menangani satu servis aktif.
 */
public class Mekanik {
    private String idMekanik;
    private String nama;
    private String spesialisasi;
    private boolean sedangBertugas;

    /** Konstruktor default. */
    public Mekanik() {
        this.idMekanik = "-";
        this.nama = "-";
        this.spesialisasi = "-";
        this.sedangBertugas = false;
    }

    /** Konstruktor berparameter; mekanik baru selalu belum bertugas. */
    public Mekanik(String idMekanik, String nama, String spesialisasi) {
        setIdMekanik(idMekanik);
        setNama(nama);
        setSpesialisasi(spesialisasi);
        this.sedangBertugas = false;
    }

    public String getIdMekanik() { return idMekanik; }
    public String getNama() { return nama; }
    public String getSpesialisasi() { return spesialisasi; }
    public boolean isSedangBertugas() { return sedangBertugas; }

    public void setIdMekanik(String idMekanik) {
        if (idMekanik == null || idMekanik.trim().isEmpty())
            throw new IllegalArgumentException("ID mekanik tidak boleh kosong.");
        this.idMekanik = idMekanik.trim().toUpperCase();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty())
            throw new IllegalArgumentException("Nama mekanik tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public void setSpesialisasi(String spesialisasi) {
        if (spesialisasi == null || spesialisasi.trim().isEmpty())
            throw new IllegalArgumentException("Spesialisasi tidak boleh kosong.");
        this.spesialisasi = spesialisasi.trim();
    }

    /** Menandai mekanik mulai menangani servis. Gagal jika masih bertugas. */
    public void mulaiServis() {
        if (sedangBertugas)
            throw new IllegalStateException("Mekanik " + nama + " masih menangani servis aktif.");
        this.sedangBertugas = true;
    }

    /** Menandai mekanik selesai menangani servis. */
    public void selesaiServis() {
        this.sedangBertugas = false;
    }

    /** Menampilkan informasi mekanik ke console. */
    public void tampilkanInfo() {
        System.out.printf("ID: %-6s | Nama: %-12s | Spesialisasi: %-12s | Status: %s%n",
                idMekanik, nama, spesialisasi, sedangBertugas ? "Bertugas" : "Tersedia");
    }
}
