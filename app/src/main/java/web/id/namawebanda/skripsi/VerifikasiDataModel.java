package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class VerifikasiDataModel {
    private String mMsg;
    private int mStatus;

    // TODO: Create a WeatherDataModel from a JSON:
    public static VerifikasiDataModel fromJson(JSONObject objek){
        try{
            VerifikasiDataModel veridata = new VerifikasiDataModel();

            veridata.mStatus = objek.getInt("status");
            veridata.mMsg = objek.getString("msg");

            return veridata;
        }catch(JSONException e){

            return null;
        }

    }

    // TODO: Uncomment to this to get the weather image name from the condition:


    // TODO: Create getter methods for temperature, city, and icon name:



    public int getStatus() {
        return mStatus;
    }

    public String getMsg() {
        return mMsg;
    }
}
