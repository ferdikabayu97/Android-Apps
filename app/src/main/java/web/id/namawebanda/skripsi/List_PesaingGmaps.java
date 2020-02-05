package web.id.namawebanda.skripsi;

public class List_PesaingGmaps {
    private String name;
    private String vicinity;
    private String rating;
    private String open_now;

    public List_PesaingGmaps(String name, String vicinity, String rating, String open_now) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.open_now = open_now;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpen_now() {
        return open_now;
    }

    public void setOpen_now(String open_now) {
        this.open_now = open_now;
    }
}
