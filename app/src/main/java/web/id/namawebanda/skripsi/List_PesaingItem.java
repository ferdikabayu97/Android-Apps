package web.id.namawebanda.skripsi;

public class List_PesaingItem {
    private String nama_pemilik;
    private String alamat;
    private String ket;

    public List_PesaingItem(String nama_pemilik, String alamat, String ket) {
        this.nama_pemilik = nama_pemilik;
        this.alamat = alamat;
        this.ket = ket;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
}
