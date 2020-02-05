package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class PendudukPendidikanDataModel {
    private String mMsg;
    private int mStatus;
    private String mbs,mbts,msd,msmp,msma,mdi,mdiii,ms1,ms2,ms3,mjp;

    public static PendudukPendidikanDataModel fromJson(JSONObject objek){



            try {

                PendudukPendidikanDataModel pend = new PendudukPendidikanDataModel();

                pend.mStatus = objek.getInt("status");
                if (pend.mStatus == 200) {
                    pend.mbs = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("belum_sekolah");
                    pend.mbts = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("belum_tamat_sd");
                    pend.msd = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("tamat_sd");
                    pend.msmp = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("smp");
                    pend.msma = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("sma");
                    pend.mdi = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("di_dii");
                    pend.mdiii = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("diii");
                    pend.ms1 = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("s1");
                    pend.ms2 = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("s2");
                    pend.ms3 = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getJSONObject("data kriteria").getString("s3");
                    pend.mjp = objek.getJSONObject("data penduduk").getJSONObject("pendidikan").getString("jumlah penduduk");

                } else {
                    pend.mMsg = objek.getString("msg");

                }

                return pend;
            } catch (JSONException e) {

                return null;

            }
        }


    public int getStatus() {return mStatus;}


    public String getMsg() {return mMsg;}


    public String getMbs() {
        return mbs;
    }

    public String getMbts() {
        return mbts;
    }

    public String getMsd() {
        return msd;
    }

    public String getMsmp() {
        return msmp;
    }

    public String getMsma() {
        return msma;
    }

    public String getMdi() {
        return mdi;
    }

    public String getMdiii() {
        return mdiii;
    }

    public String getMs1() {
        return ms1;
    }

    public String getMs2() {
        return ms2;
    }

    public String getMs3() {
        return ms3;
    }

    public String getMjp() {
        return mjp;
    }
}
