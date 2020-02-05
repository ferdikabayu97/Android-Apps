package web.id.namawebanda.skripsi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PesaingTetapDataModel {
    private String mMsg;
    private int mStatus;
    private  static JSONArray mArray;

    // TODO: Create a WeatherDataModel from a JSON:
    public static PesaingTetapDataModel fromJson(JSONObject objek){
        try{
            PesaingTetapDataModel tetap = new PesaingTetapDataModel();

            tetap.mStatus = objek.getInt("status");
            if (tetap.mStatus == 200){
                tetap.mArray = objek.getJSONObject("data").getJSONArray("pesaing_tetap");

            } else {
                tetap.mMsg = objek.getString("msg");

            }

            return tetap;
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
