package web.id.namawebanda.skripsi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PendudukJKDataModel {
    private String mMsg;
    private int mStatus;
    private String mpria,mwanita,mjp;


    // TODO: Create a WeatherDataModel from a JSON:
    public static PendudukJKDataModel fromJson(JSONObject objek) {
        if (objek != null) {

            try {
                PendudukJKDataModel jk = new PendudukJKDataModel();

                jk.mStatus = objek.getInt("status");
                if (jk.mStatus == 200) {
                    jk.mpria = objek.getJSONObject("data penduduk").getJSONObject("jk").getJSONObject("data kriteria").getString("pria");
                    jk.mwanita = objek.getJSONObject("data penduduk").getJSONObject("jk").getJSONObject("data kriteria").getString("wanita");
                    jk.mjp = objek.getJSONObject("data penduduk").getJSONObject("jk").getString("jumlah penduduk");


                } else {
                    jk.mMsg = objek.getString("msg");

                }

                return jk;
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

    public String getMpria() {
        return mpria;
    }

    public String getMwanita() {
        return mwanita;
    }

    public String getMjp() {
        return mjp;
    }



}
