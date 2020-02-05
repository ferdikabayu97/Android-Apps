package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class PendudukPekerjaanDataModel {
    private String mMsg;
    private int mStatus;
    String mtb,mapn,mtp,mw,mper,mnel,mbkea,mpdm,mtk,mpensi,mlain,mjp;


    public static PendudukPekerjaanDataModel fromJson(JSONObject objek) {
        if (objek != null) {

            try {
                PendudukPekerjaanDataModel peker = new PendudukPekerjaanDataModel();

                peker.mStatus = objek.getInt("status");
                if (peker.mStatus == 200) {
                    peker.mtb = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("tidak_bekerja");
                    peker.mapn = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("aparat_pejabat_negara");
                    peker.mtp = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("tenaga_pengajar");
                    peker.mw = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("wiraswasta");
                    peker.mper = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("pertanian");
                    peker.mnel = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("nelayan");
                    peker.mbkea = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("bidang_keagamaan");
                    peker.mpdm = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("pelajar_dan_mahasiswa");
                    peker.mtk = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("tenaga_kesehatan");
                    peker.mpensi = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("pensiunan");
                    peker.mlain = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getJSONObject("data kriteria").getString("lainnya");
                    peker.mjp = objek.getJSONObject("data penduduk").getJSONObject("pekerjaan").getString("jumlah penduduk");

                } else {
                    peker.mMsg = objek.getString("msg");

                }

                return peker;
            } catch (JSONException e) {
                return null;
            }
        }
        return null;
    }
    public int getStatus() {return mStatus;}

    public String getMsg() { return mMsg;}


    public String getMtb() {
        return mtb;
    }

    public String getMapn() {
        return mapn;
    }

    public String getMtp() {
        return mtp;
    }

    public String getMw() {
        return mw;
    }

    public String getMper() {
        return mper;
    }

    public String getMnel() {
        return mnel;
    }

    public String getMbkea() {
        return mbkea;
    }

    public String getMpdm() {
        return mpdm;
    }

    public String getMtk() {
        return mtk;
    }

    public String getMpensi() {
        return mpensi;
    }

    public String getMlain() {
        return mlain;
    }

    public String getMjp() {
        return mjp;
    }
}
