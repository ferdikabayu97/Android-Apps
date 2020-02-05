package web.id.namawebanda.skripsi;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginDataModel {

    // TODO: Declare the member variables here
    private String mNama,mToken,mMsg,mEmail,musername;
    private int mStatus;

    // TODO: Create a WeatherDataModel from a JSON:
    public static LoginDataModel fromJson(JSONObject objek){
        try{
            LoginDataModel logindata = new LoginDataModel();

            logindata.mStatus = objek.getInt("status");
            if (logindata.mStatus == 200){
                logindata.mNama = objek.getJSONObject("data").getString("nama");
                logindata.mToken = objek.getJSONObject("data").getString("token");
                logindata.mEmail = objek.getJSONObject("data").getString("email");
                logindata.musername = objek.getJSONObject("data").getString("id_user");

            } else {
                logindata.mMsg = objek.getString("msg");

            }

//            weatherdata.mCondition = objek.getJSONArray("weather").getJSONObject(0).getInt("id");
//            weatherdata.mIconName = updateWeatherIcon(weatherdata.mCondition);
//
//            double a = objek.getJSONObject("main").getDouble("temp");
//            int ab = (int) Math.rint(a - 237.15);
//            weatherdata.mTemperature = Integer.toString(ab);


            return logindata;
        }catch(JSONException e){

            return null;
        }

    }

    // TODO: Uncomment to this to get the weather image name from the condition:
//    private static String updateWeatherIcon(int condition) {
//
//        if (condition >= 0 && condition < 300) {
//            return "tstorm1";
//        } else if (condition >= 300 && condition < 500) {
//            return "light_rain";
//        } else if (condition >= 500 && condition < 600) {
//            return "shower3";
//        } else if (condition >= 600 && condition <= 700) {
//            return "snow4";
//        } else if (condition >= 701 && condition <= 771) {
//            return "fog";
//        } else if (condition >= 772 && condition < 800) {
//            return "tstorm3";
//        } else if (condition == 800) {
//            return "sunny";
//        } else if (condition >= 801 && condition <= 804) {
//            return "cloudy2";
//        } else if (condition >= 900 && condition <= 902) {
//            return "tstorm3";
//        } else if (condition == 903) {
//            return "snow5";
//        } else if (condition == 904) {
//            return "sunny";
//        } else if (condition >= 905 && condition <= 1000) {
//            return "tstorm3";
//        }
//
//        return "dunno";
//    }

    // TODO: Create getter methods for temperature, city, and icon name:


//    public String getTemperature() {
//        return mTemperature + "°";
//    }

    public int getStatus() {
        return mStatus;
    }
    public String getNama() {
        return mNama;
    }
    public String getToken() {
        return mToken;
    }
    public String getEmail() {
        return mEmail;
    }
    public String getUsername() {
        return musername;
    }
    public String getMsg() {
        return mMsg;
    }
    //
//    public String getIconName() {
//        return mIconName;
//    }

}
