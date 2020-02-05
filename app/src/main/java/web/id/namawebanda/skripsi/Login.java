package web.id.namawebanda.skripsi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    ProgressDialog progDailog = null;
    //
    Intent locatorService = null;
    AlertDialog alertDialog = null;
    //
    String lng,lat;
    //koordinat
    final int REQ_CHECK = 123;
    final long MIN_TIME = 5000;
    Intent halamanutama;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 100;
    // set LOCATION_PROVIDER
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    // mendeklarasikan LocationManager dan LocationListiner
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    //koordinat
    //constrant
    String Login_URL = "http://rekusaha.com/api/login/";
    //progress act
    Button btn_login;
    ProgressDialog mDialog;
    TextView registrasi,lupapassword;
    String username,password;
    EditText id,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        halamanutama=new Intent(Login.this, MainActivity.class);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sedang Melakukan Login");
        mDialog.setCancelable(false);
        //
        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);

        startService();


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lng = String.valueOf(location.getLongitude());
                lat = String.valueOf(location.getLatitude());
                halamanutama.putExtra("lat",lat);
                halamanutama.putExtra("lng",lng);
                Log.d("Cek 2","Ke"+lat+"kel"+lng);
                progDailog.dismiss();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("Cek 2","Ke"+lat+"kel"+lng);
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("weather", "Provider di-enable");
            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };
        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQ_CHECK);
            finish();
            return;
        }



        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);


        btn_login = findViewById(R.id.btn_masuk);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            boolean valid;
                username = id.getText().toString();
                password = pass.getText().toString();
                valid = verifikasiIsi(username,password);
                if(valid == true){
                    Log.d("Rekusaha","use "+username);
                    Log.d("Rekusaha","use "+password);
                    Log.d("Rekusaha",""+Login_URL);
                    getAPILogin(username,password);
                }else{
                    Toast.makeText(Login.this,"Silahkan Lengkapi Data Terlebihdahulu",Toast.LENGTH_SHORT).show();

                    pass.setText("");
                }

            }
        });

        registrasi = findViewById(R.id.regis);
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent Regis=new Intent(Login.this, Registrasi.class);
                startActivity(Regis);
                finish();
            }
        });
        lupapassword = findViewById(R.id.lupapass);
        lupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lupapassword=new Intent(Login.this, LupaPassword.class);
                startActivity(lupapassword);
                finish();
            }
        });

    }

    public boolean verifikasiIsi (String username, String password){

        if (username.isEmpty()){
            return false;
        }
        if (password.isEmpty()){
            return false;
        }

        return true;
    }

    public void getAPILogin(String username, String password){
        RequestParams param = new RequestParams();
        param.put("id",username);
        param.put("pass",password);
        loginNetworking(param);
    }

    public void loginNetworking(RequestParams params){

        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Login_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Status"," response : "+response.toString());
                LoginDataModel status = LoginDataModel.fromJson(response);
                if(status.getStatus() == 200){


                    Log.d("Rekusaha",""+status.getStatus());
                    Log.d("Rekusaha",""+status.getNama());
                    Log.d("Rekusaha",""+status.getToken());
                    mDialog.dismiss();


                    halamanutama.putExtra("Token",status.getToken());
                    halamanutama.putExtra("nama",status.getNama());
                    halamanutama.putExtra("email",status.getEmail());
                    halamanutama.putExtra("username",status.getUsername());
                    Log.d("Cek 3","Ke"+lat+"kel"+lng);

                    startActivity(halamanutama);
                    finish();
                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                Toast.makeText(Login.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    pass.setText("");

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(Login.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(Login.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
//                Toast.makeText(Login.this, "Service Started",Toast.LENGTH_LONG).show();
            }
        });

    }



    //
    public boolean stopService() {
        if (this.locatorService != null) {
            this.locatorService = null;
        }
        return true;
    }

    public boolean startService() {
        try {
            // this.locatorService= new
            // Intent(FastMainActivity.this,LocatorService.class);
            // startService(this.locatorService);

            FetchCordinates fetchCordinates = new FetchCordinates();
            fetchCordinates.execute();
            return true;
        } catch (Exception error) {
            return false;
        }

    }

    public AlertDialog CreateAlert(String title, String message) {
        AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle(title);

        alert.setMessage(message);

        return alert;

    }

    public class FetchCordinates extends AsyncTask<String, Integer, String> {

        public double lati = 0.0;
        public double longi = 0.0;

        public LocationManager mLocationManager;
        public VeggsterLocationListener mVeggsterLocationListener;

        @Override
        protected void onPreExecute() {
            mVeggsterLocationListener = new VeggsterLocationListener();
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0,
                    mVeggsterLocationListener);

            progDailog = new ProgressDialog(Login.this);
            progDailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    FetchCordinates.this.cancel(true);
                }
            });
            progDailog.setMessage("Lokasi Berdasarkan Wifi..");
            progDailog.setIndeterminate(true);
            progDailog.setCancelable(true);
            progDailog.show();

        }

        @Override
        protected void onCancelled(){
            System.out.println("Cancelled by user!");
            progDailog.dismiss();
            mLocationManager.removeUpdates(mVeggsterLocationListener);
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();

            lat = Double.toString(lati);
            lng = Double.toString(longi);
            Log.d("Cek Wifi","Ke"+lat+"kel"+lng);
            halamanutama.putExtra("lat",lat);
            halamanutama.putExtra("lng",lng);

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            while (this.lati == 0.0) {

            }
            return null;
        }

        public class VeggsterLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {

                int lat = (int) location.getLatitude(); // * 1E6);
                int log = (int) location.getLongitude(); // * 1E6);
                int acc = (int) (location.getAccuracy());

                String info = location.getProvider();
                try {

                    // LocatorService.myLatitude=location.getLatitude();

                    // LocatorService.myLongitude=location.getLongitude();

                    lati = location.getLatitude();
                    longi = location.getLongitude();

                } catch (Exception e) {
                    // progDailog.dismiss();
                    // Toast.makeText(getApplicationContext(),"Unable to get Location"
                    // , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("OnProviderDisabled", "OnProviderDisabled");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("onProviderEnabled", "onProviderEnabled");
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.i("onStatusChanged", "onStatusChanged");

            }

        }

    }

    //

}
