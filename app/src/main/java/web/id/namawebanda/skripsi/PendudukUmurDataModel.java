package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class PendudukUmurDataModel {
    private String mMsg;
    private int mStatus;
    private String mu0,mu5,mu10,mu15,mu20,mu25,mu30,mu35,mu40,mu45,mu50,mu55,mu60,mu65,mu70,mu75,mjp;

    public static PendudukUmurDataModel fromJson(JSONObject objek){
        if(objek != null) {

            try {
                PendudukUmurDataModel umur = new PendudukUmurDataModel();

                umur.mStatus = objek.getInt("status");
                if (umur.mStatus == 200) {
                    umur.mu0 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u0_4");
                    umur.mu5 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u5_9");
                    umur.mu10 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u10_14");
                    umur.mu15 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u15_19");
                    umur.mu20 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u20_24");
                    umur.mu25 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u25_29");
                    umur.mu30 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u30_34");
                    umur.mu35 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u35_39");
                    umur.mu40 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u40_44");
                    umur.mu45 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u45_49");
                    umur.mu50 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u50_54");
                    umur.mu55 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u55_59");
                    umur.mu60 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u60_64");
                    umur.mu65 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u65_69");
                    umur.mu70 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u70_74");
                    umur.mu75 = objek.getJSONObject("data penduduk").getJSONObject("umur").getJSONObject("data kriteria").getString("u75_above");
                    umur.mjp = objek.getJSONObject("data penduduk").getJSONObject("jk").getString("jumlah penduduk");

                } else {
                    umur.mMsg = objek.getString("msg");

                }

                return umur;
            } catch (JSONException e) {
                return null;
            }
        }
        return null;
    }

    public String getMsg() {
        return mMsg;
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMu0() {
        return mu0;
    }

    public String getMu5() {
        return mu5;
    }

    public String getMu10() {
        return mu10;
    }

    public String getMu15() {
        return mu15;
    }

    public String getMu20() {
        return mu20;
    }

    public String getMu25() {
        return mu25;
    }

    public String getMu30() {
        return mu30;
    }

    public String getMu35() {
        return mu35;
    }

    public String getMu40() {
        return mu40;
    }

    public String getMu45() {
        return mu45;
    }

    public String getMu50() {
        return mu50;
    }

    public String getMu55() {
        return mu55;
    }

    public String getMu60() {
        return mu60;
    }

    public String getMu65() {
        return mu65;
    }

    public String getMu70() {
        return mu70;
    }

    public String getMu75() {
        return mu75;
    }

    public String getMjp() {
        return mjp;
    }
}
