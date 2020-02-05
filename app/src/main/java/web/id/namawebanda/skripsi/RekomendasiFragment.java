package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class RekomendasiFragment extends Fragment {
    String mtoken,mkelurahan,mkecamatan,lat,lng,mlatlng,mnama,memail,musername;
    final String Rekomendasi_URL = "http://rekusaha.com/api/req/";
    ProgressDialog mDialog;
    OnDataPass dataPasser;
    TextView lokasi,koor;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<List_RekomendasiItem> listItems;

    public interface OnDataPass {
        public void onDataPass(String data[]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public void passData(String[] data) {
        dataPasser.onDataPass(data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rekomendasi, container , false);


        //
        mRecyclerView = v.findViewById(R.id.recyclerview);//ceklagi
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
        listItems = new ArrayList<>();
        //
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Menghitung Rekomendasi");
        mDialog.setCancelable(false);
        //
        lokasi = v.findViewById(R.id.lokasi);
        koor = v.findViewById(R.id.koordinat);



        mnama= getActivity().getIntent().getStringExtra("nama");
        memail = getActivity().getIntent().getStringExtra("email");
        musername = getActivity().getIntent().getStringExtra("username");
        mtoken = getActivity().getIntent().getStringExtra("token");
        mkelurahan = getActivity().getIntent().getStringExtra("kelurahan");
        mkecamatan = getActivity().getIntent().getStringExtra("kecamatan");
        mlatlng = getActivity().getIntent().getStringExtra("latlng");
        String[] latilong = mlatlng.split(",");
        lat = latilong[0];
        lng = latilong[1];
        lokasi.setText("Anda berada di Kec."+mkecamatan+" Kel."+mkelurahan);
        koor.setText("dengan lat : "+lat+" lng :"+lng);
            getAPIRekomendasi(mkecamatan,mkelurahan,mtoken);
        Log.d("Rekomendasi",""+mtoken+mkecamatan+mkelurahan);
        return v;
    }

    public void getAPIRekomendasi(String kec, String kel,String token){
        RequestParams param = new RequestParams();
        param.put("kecamatan",kec);
        param.put("kelurahan",kel);
        param.put("token",token);
        rekomendasiNetworking(param);
    }

    public void rekomendasiNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Rekomendasi_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                RekomendasiDataModel status = RekomendasiDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    mDialog.dismiss();

                    try {
                    JSONArray array = status.getmArray();
                        Bundle bundle = new Bundle();
                        String[] alternatif = new String[array.length()];
                    for (int i=0; i<array.length();i++){
//                        String obj = response.toString();
//                        bundle.putString("obj", obj);
//                        fragment1.setArguments(bundle);
//                        fragment2.setArguments(bundle);
                            JSONObject o = array.getJSONObject(i);
                            List_RekomendasiItem item = new List_RekomendasiItem(
                                    o.getString("nama_alternatif"),
                                    o.getString("peringkat"),
                                    o.getString("skor"),
                                    o.getString("rharga")
                            );
                            listItems.add(item);
                            alternatif[i] = o.getString("nama_alternatif");
                        }
                            mAdapter = new AdapterRekomendasi(listItems,getActivity());
                            mRecyclerView.setAdapter(mAdapter);
                            dataPasser.onDataPass(alternatif);

                        } catch (JSONException e){
                                e.printStackTrace();
                        }

                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                    Toast.makeText(getActivity(),status.getMsg(),Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
//                    pass.setText("");
//
                }

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
                alertSatuTombol("Mohon maaf, Kecamatan "+mkecamatan+", Kelurahan "+mkelurahan+" Belum menjadi Area Rek Usaha");
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
                                Intent out=new Intent(getActivity(), MainActivity.class);
                                out.putExtra("lat",lat);
                                out.putExtra("lng",lng);
                                out.putExtra("Token",mtoken);
                                out.putExtra("nama",mnama);
                                out.putExtra("email",memail);
                                out.putExtra("username",musername);
                                startActivity(out);
                                getActivity().finish();
                                dialog.cancel();
                            }
                        }).show();
    }




}
