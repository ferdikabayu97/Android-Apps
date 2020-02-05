package web.id.namawebanda.skripsi;

public class List_RekomendasiItem {
    private String nama_alternatif;
    private String peringkat;
    private String skor;
    private String rharga;

    public List_RekomendasiItem(String nama_alternatif, String peringkat, String skor, String rharga) {
        this.nama_alternatif = nama_alternatif;
        this.peringkat = peringkat;
        this.skor = skor;
        this.rharga = rharga;
    }

    public String getNama_alternatif() {
        return nama_alternatif;
    }

    public void setNama_alternatif(String nama_alternatif) {
        this.nama_alternatif = nama_alternatif;
    }

    public String getPeringkat() {
        return peringkat;
    }

    public void setPeringkat(String peringkat) {
        this.peringkat = peringkat;
    }

    public String getSkor() {
        return skor;
    }

    public void setSkor(String skor) {
        this.skor = skor;
    }

    public String getRharga() {
        return rharga;
    }

    public void setRharga(String rharga) {
        this.rharga = rharga;
    }
}
