package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class PendudukSkDataModel {
    private String mMsg;
    private int mStatus;
    String mbk,mk,mch,mcm,mjp;

    // TODO: Create a WeatherDataModel from a JSON:
    public static PendudukSkDataModel fromJson(JSONObject objek) {
        if (objek != null) {

            try {
                PendudukSkDataModel sk = new PendudukSkDataModel();

                sk.mStatus = objek.getInt("status");
                if (sk.mStatus == 200) {
                    sk.mbk = objek.getJSONObject("data penduduk").getJSONObject("sk").getJSONObject("data kriteria").getString("belum_kawin");
                    sk.mk = objek.getJSONObject("data penduduk").getJSONObject("sk").getJSONObject("data kriteria").getString("kawin");
                    sk.mch = objek.getJSONObject("data penduduk").getJSONObject("sk").getJSONObject("data kriteria").getString("cerai_hidup");
                    sk.mcm = objek.getJSONObject("data penduduk").getJSONObject("sk").getJSONObject("data kriteria").getString("cerai_mati");
                    sk.mjp = objek.getJSONObject("data penduduk").getJSONObject("sk").getString("jumlah penduduk");

                } else {
                    sk.mMsg = objek.getString("msg");

                }

                return sk;
            } catch (JSONException e) {
                return null;
            }
        }
    return null;
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMsg() {
        return mMsg;
    }

    public String getMbk() {
        return mbk;
    }

    public String getMk() {
        return mk;
    }

    public String getMch() {
        return mch;
    }

    public String getMcm() {
        return mcm;
    }

    public String getMjp() {
        return mjp;
    }
}
