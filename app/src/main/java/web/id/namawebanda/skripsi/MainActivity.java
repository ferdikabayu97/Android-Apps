package web.id.namawebanda.skripsi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    boolean doubleBackToExitPressedOnce = false;
    private GoogleMap mMap;
    String lng,lat,latlng;
    ProgressDialog mDialog;
    final String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    final String App_ID = "AIzaSyAaYa3SZ4n5pYR3Q-Twzqd4lNPPzf4kzlM";


    //
    Button ceklokasi;

    String mtoken,mnama,memail,musername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //
        Intent token = getIntent();
        mtoken= token.getStringExtra("Token");
        mnama= token.getStringExtra("nama");
        memail= token.getStringExtra("email");
        musername= token.getStringExtra("username");
        lat = token.getStringExtra("lat");
        lng = token.getStringExtra("lng");
        Log.d("Cek 1","Ke"+lat+"kel"+lng);
//
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Reverse Geocode");
        mDialog.setCancelable(false);
        //

        //

        ceklokasi = findViewById(R.id.ceklokasi);
        ceklokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latlng == null||latlng == ""){
                    latlng = lat+","+lng;
                }

                RequestParams param = new RequestParams();

                param.put("latlng",latlng);
                param.put("location_type","ROOFTOP");
                param.put("result_type","street_address");
                param.put("key",App_ID);
                getRGEOCODE(param);
            }
        });
    }
    public void getRGEOCODE(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(GEOCODE_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();
                Log.d("Status", " response : " + response.toString());

                MainDataModel lokasidet = MainDataModel.fromJson(response);
                Log.d("CEKPLISATUH",lokasidet.getStatus());

                if (lokasidet.getStatus().equals("OK")) {


                    Intent brequest = new Intent(MainActivity.this, LoggedActivity.class);
                    brequest.putExtra("kecamatan", lokasidet.getkecamatan());
                    brequest.putExtra("kelurahan", lokasidet.getkelurahan());
                    brequest.putExtra("token", mtoken);
                    brequest.putExtra("nama", mnama);
                    brequest.putExtra("email", memail);
                    brequest.putExtra("username", musername);
                    brequest.putExtra("latlng", latlng);
                    startActivity(brequest);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"Koordinat anda belum tidak terdefinisi, coba tekan lokasi terdekat sekitarnya",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("app","there is an arror "+ throwable.toString());
                Log.d("status","code "+statusCode);
                Log.e("status","code "+errorResponse.toString());
                mDialog.dismiss();

                Toast.makeText(MainActivity.this,"Request Gagal",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mDialog.dismiss();

                Toast.makeText(MainActivity.this,"Request Gagal",Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("Cek ",""+lat+"###"+lng);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Lokasi Anda").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(MainActivity.this, "Dragging Start",
                        Toast.LENGTH_SHORT).show();
                latlng = marker.getPosition().latitude +","+ marker.getPosition().longitude;
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("Rekusaha",""+latLng);
                mMap.clear();
                MarkerOptions a = new MarkerOptions()
                        .position(latLng);
                mMap.addMarker(a);
                String[] latlong =  latLng.toString().split("\\(");
                String[] latilong = latlong[1].split("\\)");
                latlng = latilong[0];
                Toast.makeText(MainActivity.this,"Lokasi Baru Pencarian Ditambahkan, koordinat"+latlng,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent veri=new Intent(MainActivity.this, Login.class);
            startActivity(veri);
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan kembali dua kali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
