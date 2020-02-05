package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrasiDataModel {
    private String mMsg;
    private int mStatus;

    // TODO: Create a WeatherDataModel from a JSON:
    public static RegistrasiDataModel fromJson(JSONObject objek){
        try{
            RegistrasiDataModel regisdata = new RegistrasiDataModel();

            regisdata.mStatus = objek.getInt("status");
            regisdata.mMsg = objek.getString("msg");

            return regisdata;
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
