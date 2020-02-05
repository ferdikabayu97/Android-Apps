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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class PesaingFragment extends Fragment {
    String mtoken,mkelurahan,mkecamatan,mMakanan,mlatlng;

    final String Pesaing_URL = "http://rekusaha.com/api/pes/";

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Button pesaingradius;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    ProgressDialog mDialog;
    String[] malternatifs;
    Spinner jenismakanan;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesaing, container , false);

        if(getArguments() != null){
            malternatifs = getArguments().getStringArray("array");

        }

        Log.d("AAAA",malternatifs[1]);

        mtoken = getActivity().getIntent().getStringExtra("token");
        mkelurahan = getActivity().getIntent().getStringExtra("kelurahan");
        mkecamatan = getActivity().getIntent().getStringExtra("kecamatan");
        mlatlng = getActivity().getIntent().getStringExtra("latlng");

        jenismakanan = v.findViewById(R.id.spinnerpesaing);
        bottomNavigation = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.tab_navigator_pesaing);
        fragmentManager = getActivity().getSupportFragmentManager();

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Mengambil Data Penduduk");
        mDialog.setCancelable(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, malternatifs);
//set the spinners adapter to the previously created one.
        jenismakanan.setAdapter(adapter);
        jenismakanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                mMakanan = (String) parent.getItemAtPosition(position);
                getAPIPesaing(mkecamatan,mkelurahan,mtoken,mMakanan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pesaingradius = v.findViewById(R.id.gmapspesaing);
        pesaingradius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesaingradius =new Intent(getActivity(), PesaingRadiusActivity.class);
                pesaingradius.putExtra("ju", mMakanan);
                pesaingradius.putExtra("latlng", mlatlng);
                startActivity(pesaingradius);
            }
        });

        Log.d("Fragment Pes","Kelurahan : "+mkelurahan+" Kecamatan : "+mkecamatan+" token : "+mtoken);
        return v;
    }

    public void getAPIPesaing(String kec, String kel,String token,String ju){
        RequestParams param = new RequestParams();
        param.put("kecamatan",kec);
        param.put("kelurahan",kel);
        param.put("token",token);
        param.put("jenisusaha",ju);

        pesaingNetworking(param);
    }
    public void pesaingNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Pesaing_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mDialog.dismiss();

                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                final Fragment fragment1 = new PesaingFragmentTetap();
                final Fragment fragment2 = new PesaingFragmentBergerak();
                Log.d("Status"," response : "+response.toString());

                Bundle bundle = new Bundle();
                String obj = response.toString();
                bundle.putString("obj", obj);
                fragment1.setArguments(bundle);
                fragment2.setArguments(bundle);


                fragmentManager.beginTransaction().replace(R.id.main_container, fragment1).commit();

                //Memberikan listener saat menu item di bottom navigation diklik
                bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        switch (id){
                            case R.id.menu_pesaing_menetap:
//                                fragment = fragment1;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment1).commit();

                                break;
                            case R.id.menu_pesaing_bergerak:
//                                fragment = fragment2;
                                fragmentManager.beginTransaction().replace(R.id.main_container, fragment2).commit();

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
