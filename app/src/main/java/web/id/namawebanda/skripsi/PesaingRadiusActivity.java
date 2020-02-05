package web.id.namawebanda.skripsi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PesaingRadiusActivity extends FragmentActivity implements OnMapReadyCallback   {

    private String mju,mlatlng,div[];
    private Double mlat,mlng;
     String name[];
    private String radius = "250";
    ProgressDialog mDialog;
    private GoogleMap mMap;
    private Button back;
     Double lat[], lng[];

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<List_PesaingGmaps> listItems;

    final String PESAINGGMAPS_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final String App_ID = "AIzaSyAaYa3SZ4n5pYR3Q-Twzqd4lNPPzf4kzlM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesaing_radius);

        Intent kiriman = getIntent();



        mRecyclerView = findViewById(R.id.recyclerview);//ceklagi
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PesaingRadiusActivity.this));

        listItems = new ArrayList<>();

        //
        back = findViewById(R.id.back);

        //

        mju = kiriman.getStringExtra("ju");
        mlatlng = kiriman.getStringExtra("latlng");
        div = mlatlng.split(",");
        mlat = Double.parseDouble(div[0]);
        mlng = Double.parseDouble(div[1]);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Place API");
        mDialog.setCancelable(false);
        mDialog.show();

        RequestParams param = new RequestParams();

        param.put("location",mlatlng);
        param.put("radius",radius);
        param.put("keyword",mju);
        param.put("key",App_ID);
        getPESAINGGMAPS(param);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PesaingRadiusActivity.super.onBackPressed();
            }
        });

    }

    public void getPESAINGGMAPS(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PESAINGGMAPS_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();
                Log.d("Status"," response : "+response.toString());
                try {
                    PesaingRadiusDataModel status = PesaingRadiusDataModel.fromJson(response);
                    if(status.getStatus().equals("OK")){
                        JSONArray array = status.getmArray();
                        if(array.length() > 0){
                            lat = new Double[array.length()];
                            lng = new Double[array.length()];
                            name = new String[array.length()];

                            for (int i=0; i<array.length();i++){
                                Log.d("Range",""+array.length());
                                JSONObject o = array.getJSONObject(i);
                                String Aa = "Tidak Tercantumkan";
//
                                lat[i] = o.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                lng[i] = o.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                                name[i] = o.getString("name");
                                if (o.length() == 14){
                                    if (o.getJSONObject("opening_hours").getBoolean("open_now")){
                                        Aa = "Buka";
                                    } else {
                                        Aa = "Tutup";
                                    }
                                }


                                int B = o.getInt("rating");
                                List_PesaingGmaps item = new List_PesaingGmaps(
                                        o.getString("name"),
                                        o.getString("vicinity"),
                                        Integer.toString(B),
                                        Aa
                                );
                                listItems.add(item);

                            }
                            mAdapter = new AdapterPesaingGmap(listItems,PesaingRadiusActivity.this);
                            mRecyclerView.setAdapter(mAdapter);

                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(PesaingRadiusActivity.this);

                        }else{
                            TextView a = findViewById(R.id.kosong);
                            a.setText("Data Kosong");
                        }

                    } else {

                        Log.d("Rekusaha",""+status.getMsg());
                        Toast.makeText(PesaingRadiusActivity.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("app","there is an arror "+ throwable.toString());
                Log.d("status","code "+statusCode);
                Log.e("status","code "+errorResponse.toString());
                mDialog.dismiss();

                Toast.makeText(PesaingRadiusActivity.this,"Request Gagal",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mDialog.dismiss();

                Toast.makeText(PesaingRadiusActivity.this,"Request Gagal",Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng Myplace = new LatLng(mlat, mlng);
        mMap.addMarker(new MarkerOptions().position(Myplace).title("Lokasi Kamu").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Myplace,16));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        if (lat.length > 0){
            Log.d("Masuk",""+lat.length);
            for(int i=0 ; i<lat.length ; i++ ){

                Log.d("Panggil",""+i);

                LatLng place = new LatLng(lat[i], lng[i]);
                mMap.addMarker(new MarkerOptions().position(place).title(name[i]));



            }

        }
        Log.d("Keluar",""+lat.length);

    }

}
