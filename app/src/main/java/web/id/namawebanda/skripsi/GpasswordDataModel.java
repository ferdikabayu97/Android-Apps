package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class GpasswordDataModel {
    private String mMsg;
    private int mStatus;

    public static GpasswordDataModel fromJson(JSONObject objek){
        try{
            GpasswordDataModel gpassdata = new GpasswordDataModel();

            gpassdata.mStatus = objek.getInt("status");
            gpassdata.mMsg = objek.getString("msg");

            return gpassdata;
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
