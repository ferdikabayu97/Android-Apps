package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class PendudukFragment extends Fragment  {

    String mtoken,mkelurahan,mkecamatan;
    final String Penduduk_URL = "http://rekusaha.com/api/pend/";

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private TextView keckel;
    ProgressDialog mDialog;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_penduduk, container , false);
        mtoken = getActivity().getIntent().getStringExtra("token");
        mkelurahan = getActivity().getIntent().getStringExtra("kelurahan");
        mkecamatan = getActivity().getIntent().getStringExtra("kecamatan");



        Log.d("Fragment Pend","Kelurahan : "+mkelurahan+" Kecamatan : "+mkecamatan+" token : "+mtoken);

        //
        bottomNavigation = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.tab_navigator_menu);
        fragmentManager = getActivity().getSupportFragmentManager();

        //
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Mengambil Data Penduduk");
        mDialog.setCancelable(false);

        keckel = v.findViewById(R.id.keckel);

        keckel.setText(mkecamatan+", "+mkelurahan);

        getAPIPenduduk(mkecamatan,mkelurahan,mtoken);

        //Untuk inisialisasi fragment pertama kali

    return v;
    }

    public void getAPIPenduduk(String kec, String kel,String token){
        RequestParams param = new RequestParams();
        param.put("kecamatan",kec);
        param.put("kelurahan",kel);
        param.put("token",token);
        pendudukNetworking(param);
    }
    public void pendudukNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Penduduk_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();

                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                final Fragment fragment1 = new PendudukFragmentJk();
                final Fragment fragment2 = new PendudukFragmentSk();
                final Fragment fragment5 = new PendudukFragmentPekerjaan();
                final Fragment fragment4 = new PendudukFragmentPendidikan();
                final Fragment fragment3 = new PendudukFragmentUmur();
                Log.d("Status"," response : "+response.toString());

                Bundle bundle = new Bundle(5);
                String obj = response.toString();
                bundle.putString("obj", obj);
                fragment1.setArguments(bundle);
                fragment2.setArguments(bundle);
                fragment3.setArguments(bundle);
                fragment4.setArguments(bundle);
                fragment5.setArguments(bundle);


                fragmentManager.beginTransaction().replace(R.id.main_container, fragment1).commit();

                //Memberikan listener saat menu item di bottom navigation diklik
                bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.menu_jk:
//                                fragment = fragment1;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment1).commit();

                                break;
                            case R.id.menu_sk:
//                                fragment = fragment2;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment2).commit();

                                break;
                            case R.id.menu_pekerjaan:
//                                fragment = fragment3;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment5).commit();
                                break;
                            case R.id.menu_pendidikan:
//                                fragment = fragment4;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment4).commit();

                                break;
                            case R.id.menu_umur:
//                                fragment = fragment5;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment3).commit();
                                break;
                        }
//                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(getActivity(),"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
                alertSatuTombol("Kesalahan terjadi, silahkan melakukan login ulang");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(getActivity(),"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
                alertSatuTombol("Mohon maaf, untuk sementara Rekusaha belum tersedia di daerahmu");
            }
        });

    }
    public void alertSatuTombol(String Msg) {
        new AlertDialog.Builder(getActivity())
                .setMessage(Msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent out=new Intent(getActivity(), Login.class);
                                startActivity(out);
                                getActivity().finish();
                                dialog.cancel();
                            }
                        }).show();
    }



}


