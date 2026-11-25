/**
 * ADT Sorted List berbasis array. Data selalu terurut menaik berdasarkan plat nomor.
 * Atribut internal private; interaksi hanya lewat operasi publik.
 * Kondisi penuh/kosong/tidak ditemukan ditangani tanpa crash.
 */
public class SortedListKendaraan {
    private static final int KAPASITAS_DEFAULT = 10;

    private Kendaraan[] data;
    private int ukuran;
    private int kapasitas;

    public SortedListKendaraan() {
        this(KAPASITAS_DEFAULT);
    }

    public SortedListKendaraan(int kapasitas) {
        if (kapasitas <= 0) kapasitas = KAPASITAS_DEFAULT;
        this.kapasitas = kapasitas;
        this.data = new Kendaraan[kapasitas];
        this.ukuran = 0;
    }

    /**
     * Menyisipkan kendaraan agar tetap terurut. O(n).
     * @return false jika penuh, null, atau plat sudah ada.
     */
    public boolean insert(Kendaraan k) {
        if (k == null || isFull() || search(k.getPlatNomor()) != null) return false;
        int i = ukuran - 1;
        // geser elemen yang lebih besar ke kanan
        while (i >= 0 && data[i].getPlatNomor().compareTo(k.getPlatNomor()) > 0) {
            data[i + 1] = data[i];
            i--;
        }
        data[i + 1] = k;
        ukuran++;
        return true;
    }

    /** Menghapus berdasarkan plat nomor. O(n). @return false jika kosong/tidak ditemukan. */
    public boolean remove(String platNomor) {
        int idx = cariIndeks(platNomor);
        if (idx < 0) return false;
        for (int i = idx; i < ukuran - 1; i++) data[i] = data[i + 1];
        data[--ukuran] = null;
        return true;
    }

    /** Pencarian biner berdasarkan plat nomor. O(log n). @return objek atau null. */
    public Kendaraan search(String platNomor) {
        int idx = cariIndeks(platNomor);
        return idx < 0 ? null : data[idx];
    }

    /** Mengambil elemen pada indeks; null jika indeks di luar jangkauan. */
    public Kendaraan get(int index) {
        if (index < 0 || index >= ukuran) return null;
        return data[index];
    }

    /** Menampilkan seluruh isi list (terurut). */
    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("(Daftar kendaraan kosong)");
            return;
        }
        for (int i = 0; i < ukuran; i++) {
            System.out.print((i + 1) + ". ");
            data[i].tampilkanInfo();
        }
    }

    public int size() { return ukuran; }
    public boolean isEmpty() { return ukuran == 0; }
    public boolean isFull() { return ukuran == kapasitas; }
    public int getKapasitas() { return kapasitas; }

    /** Binary search; mengembalikan indeks atau -1. */
    private int cariIndeks(String platNomor) {
        if (platNomor == null || isEmpty()) return -1;
        String kunci = platNomor.trim().toUpperCase();
        int kiri = 0, kanan = ukuran - 1;
        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            int cmp = data[tengah].getPlatNomor().compareTo(kunci);
            if (cmp == 0) return tengah;
            if (cmp < 0) kiri = tengah + 1; else kanan = tengah - 1;
        }
        return -1;
    }
}
