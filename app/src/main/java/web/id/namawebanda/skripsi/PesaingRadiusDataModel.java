package web.id.namawebanda.skripsi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PesaingRadiusDataModel {

    private String mStatus,mMsg;
    private  static JSONArray mArray;

    // TODO: Create a WeatherDataModel from a JSON:
    public static PesaingRadiusDataModel fromJson(JSONObject objek){
        try{
            PesaingRadiusDataModel pesaing = new PesaingRadiusDataModel();

            pesaing.mStatus = objek.getString("status");
            if (pesaing.mStatus.equals("OK")){
                pesaing.mArray = objek.getJSONArray("results");

            } else {
                pesaing.mMsg = "Data Kosong";

            }

            return pesaing;
        }catch(JSONException e){
            return null;
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public String getMsg() {
        return mMsg;
    }

    public JSONArray getmArray() {
        return mArray;
    }


}
