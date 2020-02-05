package web.id.namawebanda.skripsi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RekomendasiDataModel {
    private String mMsg;
    private int mStatus;
    private  static JSONArray mArray;

    // TODO: Create a WeatherDataModel from a JSON:
    public static RekomendasiDataModel fromJson(JSONObject objek){
        try{
            RekomendasiDataModel rekomend = new RekomendasiDataModel();

            rekomend.mStatus = objek.getInt("status");
            if (rekomend.mStatus == 200){
                rekomend.mArray = objek.getJSONArray("hasil");

            } else {
                rekomend.mMsg = objek.getString("msg");

            }

            return rekomend;
        }catch(JSONException e){
            return null;
        }
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMsg() {
        return mMsg;
    }

    public JSONArray getmArray() {
        return mArray;
    }


}
