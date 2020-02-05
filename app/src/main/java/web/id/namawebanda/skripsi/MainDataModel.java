package web.id.namawebanda.skripsi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainDataModel {
    private String mkecamatan,mkelurahan,mStatus;

    // TODO: Create a WeatherDataModel from a JSON:
    public static MainDataModel fromJson(JSONObject objek){
        try{
            MainDataModel maindata = new MainDataModel();
            maindata.mStatus = objek.getString("status");

            if(maindata.mStatus.equals("ZERO_RESULTS")) {

            }else{
                maindata.mkelurahan = objek.getJSONArray("results").getJSONObject(0)
                        .getJSONArray("address_components").getJSONObject(2).getString("long_name");
                maindata.mkecamatan = objek.getJSONArray("results").getJSONObject(0)
                        .getJSONArray("address_components").getJSONObject(3).getString("long_name");
                String a = "";
                if (maindata.mkecamatan.length() > 8) {
                    a = maindata.mkecamatan.substring(0, 9).toLowerCase();
                }
                Log.d("string", " " + a);
                if (a.equals("kecamatan")) {
                    maindata.mkecamatan = maindata.mkecamatan.substring(9);
                    Log.d("Kecamatan", "" + maindata.mkecamatan);
                }
                int counting = objek.getJSONArray("results").getJSONObject(0)
                        .getJSONArray("address_components").length();
                Log.d("Length :", "" + counting);
                if (counting > 8) {
                    maindata.mkelurahan = objek.getJSONArray("results").getJSONObject(0)
                            .getJSONArray("address_components").getJSONObject(3).getString("long_name");
                    maindata.mkecamatan = objek.getJSONArray("results").getJSONObject(0)
                            .getJSONArray("address_components").getJSONObject(4).getString("long_name");

                    if (maindata.mkecamatan.length() > 8) {
                        a = maindata.mkecamatan.substring(0, 9).toLowerCase();
                    }
                    Log.d("string", " " + a);
                    if (a.equals("kecamatan")) {
                        maindata.mkecamatan = maindata.mkecamatan.substring(9);
                        Log.d("Kecamatan", "" + maindata.mkecamatan);
                    }
                }
            }



            return maindata;
        }catch(JSONException e){

            return null;
        }

    }

    // TODO: Uncomment to this to get the weather image name from the condition:


    // TODO: Create getter methods for temperature, city, and icon name:



    public String getkecamatan() {
        return mkecamatan;
    }

    public String getkelurahan() {
        return mkelurahan;
    }

    public String getStatus() {
        return mStatus;
    }
}
