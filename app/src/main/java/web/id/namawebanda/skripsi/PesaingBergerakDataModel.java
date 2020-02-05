package web.id.namawebanda.skripsi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PesaingBergerakDataModel {
    private String mMsg;
    private int mStatus;
    private  static JSONArray mArray;

    // TODO: Create a WeatherDataModel from a JSON:
    public static PesaingBergerakDataModel fromJson(JSONObject objek){
        try{
            PesaingBergerakDataModel bergerak = new PesaingBergerakDataModel();

            bergerak.mStatus = objek.getInt("status");
            if (bergerak.mStatus == 200){
                bergerak.mArray = objek.getJSONObject("data").getJSONArray("pesaing_keliling");

            } else {
                bergerak.mMsg = objek.getString("msg");

            }

            return bergerak;
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
