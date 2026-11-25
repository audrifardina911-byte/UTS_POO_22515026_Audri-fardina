/**
 * Entitas Kendaraan yang diservis di bengkel.
 * Semua atribut private; perubahan nilai lewat setter yang memvalidasi.
 */
public class Kendaraan {
    private static final int TAHUN_MIN = 1900;
    private static final int TAHUN_MAX = 2100;

    private String platNomor;
    private String merk;
    private String model;
    private int tahun;

    /** Konstruktor default. */
    public Kendaraan() {
        this.platNomor = "-";
        this.merk = "-";
        this.model = "-";
        this.tahun = 2000;
    }

    /** Konstruktor berparameter (memakai setter agar tervalidasi). */
    public Kendaraan(String platNomor, String merk, String model, int tahun) {
        setPlatNomor(platNomor);
        setMerk(merk);
        setModel(model);
        setTahun(tahun);
    }

    public String getPlatNomor() { return platNomor; }
    public String getMerk() { return merk; }
    public String getModel() { return model; }
    public int getTahun() { return tahun; }

    public void setPlatNomor(String platNomor) {
        if (platNomor == null || platNomor.trim().isEmpty())
            throw new IllegalArgumentException("Plat nomor tidak boleh kosong.");
        this.platNomor = platNomor.trim().toUpperCase();
    }

    public void setMerk(String merk) {
        if (merk == null || merk.trim().isEmpty())
            throw new IllegalArgumentException("Merk tidak boleh kosong.");
        this.merk = merk.trim();
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty())
            throw new IllegalArgumentException("Model tidak boleh kosong.");
        this.model = model.trim();
    }

    public void setTahun(int tahun) {
        if (tahun < TAHUN_MIN || tahun > TAHUN_MAX)
            throw new IllegalArgumentException("Tahun harus antara " + TAHUN_MIN + " dan " + TAHUN_MAX + ".");
        this.tahun = tahun;
    }

    /** Menampilkan informasi kendaraan ke console. */
    public void tampilkanInfo() {
        System.out.printf("Plat: %-10s | Merk: %-10s | Model: %-12s | Tahun: %d%n",
                platNomor, merk, model, tahun);
    }
}
