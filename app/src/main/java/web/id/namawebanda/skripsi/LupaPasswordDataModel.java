package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class LupaPasswordDataModel {
    // TODO: Declare the member variables here
    private String mMsg;
    private int mStatus;

    // TODO: Create a WeatherDataModel from a JSON:
    public static LupaPasswordDataModel fromJson(JSONObject objek){
        try{
            LupaPasswordDataModel lupapassdata = new LupaPasswordDataModel();

            lupapassdata.mStatus = objek.getInt("status");
                lupapassdata.mMsg = objek.getString("msg");

            return lupapassdata;
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
