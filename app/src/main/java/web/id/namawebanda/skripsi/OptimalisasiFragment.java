package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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


public class OptimalisasiFragment extends Fragment {
    String mtoken;
    String Optimalisasi_URL = "http://rekusaha.com/api/opt/";
    String[] malternatifs,mjk,msk,mpend,mpeker,mumur,mrharga;
    Spinner jenismakanan,jk,sk,pend,peker,umur,rharga;
    String al_jk,al_sk,al_pend,al_peker,al_umur,rek_harga,nama_al;
    Button kirim;
    ProgressDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_optimalisasi, container , false);
        if(getArguments() != null){
            malternatifs = getArguments().getStringArray("array");
        }

        //
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Sedang Mengecek data");
        mDialog.setCancelable(false);
        //
        Log.d("AAAABBB",malternatifs[3]);
        mjk = new String[] {"Pria","Wanita"};
        msk = new String[] {"Belum Kawin","Kawin","Cerai Hidup","Cerai Mati"};
        mpend = new String[] {"Belum Sekolah","Belum Tamat SD","Tamat SD","SMP","SMA","DI DII","DIII","S1","S2","S3"};
        mpeker = new String[] {"Tidak Bekerja","Aparat Pejabat Negara","Tenaga Pengajar","Wiraswasta","Pertanian","Nelayan","Bidang Keagamaan","Pelajar Dan Mahasiswa","Tenaga Kesehatan","Pensiunan","Lainnya"};
        mumur = new String[] {"0-4 Tahun","5-9 Tahun","10-14 Tahun","15-19 Tahun","20-24 Tahun","25-29 Tahun","30-34 Tahun","35-39 Tahun","40-44 Tahun","45-49 Tahun","50-54 Tahun","55-59 Tahun","60-64 Tahun","65-69 Tahun","70-74 Tahun","75 Tahun Keatas"};
        mrharga = new String[] {"5-10 Ribu","10-15 Ribu","15-20 Ribu","20-25 Ribu","25-30 Ribu","30 Ribu Keatas"};




        mtoken = getActivity().getIntent().getStringExtra("token");

        Log.d("Fragment Opt"," token : "+mtoken);
        jenismakanan = v.findViewById(R.id.jmopsi);
        jk = v.findViewById(R.id.jkopsi);
        sk = v.findViewById(R.id.skopsi);
        pend = v.findViewById(R.id.pendidikanopsi);
        peker = v.findViewById(R.id.pekeropsi);
        umur = v.findViewById(R.id.umuropsi);
        rharga = v.findViewById(R.id.rhargaopsi);

        ArrayAdapter<String> adapterjm = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, malternatifs);
        jenismakanan.setAdapter(adapterjm);
        jenismakanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                nama_al = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterjk = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mjk);
        jk.setAdapter(adapterjk);
        jk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                al_jk = ((String) parent.getItemAtPosition(position)).toLowerCase();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adaptersk = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, msk);
        sk.setAdapter(adaptersk);
        sk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                al_sk = ((String) parent.getItemAtPosition(position)).toLowerCase().replaceAll(" ","_");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterpend = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mpend);
        pend.setAdapter(adapterpend);
        pend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                al_pend = (String) ((String) parent.getItemAtPosition(position)).toLowerCase().replaceAll(" ","_");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterpeker = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mpeker);
        peker.setAdapter(adapterpeker);
        peker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                al_peker = (String) ((String) parent.getItemAtPosition(position)).toLowerCase().replaceAll(" ","_");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterumur = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mumur);
        umur.setAdapter(adapterumur);
        umur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                al_umur = "u"+((String) parent.getItemAtPosition(position)).substring(0,((String) parent.getItemAtPosition(position)).length()-6).replaceAll("-","_");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterrharga = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mrharga);
        rharga.setAdapter(adapterrharga);
        rharga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                if (((String) parent.getItemAtPosition(position)).equalsIgnoreCase("30 Ribu Keatas")){
                    rek_harga = "h"+((String) parent.getItemAtPosition(position)).substring(0,((String) parent.getItemAtPosition(position)).length()-12) + "_abv";

                }else{
                    rek_harga = "h"+((String) parent.getItemAtPosition(position)).substring(0,((String) parent.getItemAtPosition(position)).length()-5).replaceAll("-","_");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        alertSatuTombol("Optimalisasi Data ini dilakukan untuk menambahkan sampel data peng-konsumsi suatu jenis makanan, hal ini diperlukan untuk menyempurnakan proses rekomendasi usaha anda, Maka dari itu isi data sesuai dengan apa yang diinstruksikan !","Oke saya mengerti");
        kirim = v.findViewById(R.id.kirimoptimal);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAPIOptimalisasi(nama_al,al_jk,al_sk,al_pend,al_peker,al_umur,rek_harga,mtoken);
            }
        });

        return v;
    }
    public void getAPIOptimalisasi(String nama_al,String al_jk, String al_sk, String al_pend,String al_peker,String al_umur,String rek_harga,String token){
        RequestParams param = new RequestParams();
        param.put("nama_al",nama_al);
        param.put("field_al_jk",al_jk);
        param.put("field_al_sk",al_sk);
        param.put("field_al_pend",al_pend);
        param.put("field_al_peker",al_peker);
        param.put("field_al_umur",al_umur);
        param.put("field_rek_harga",rek_harga);
        param.put("token",token);
        optimalisasiNetworking(param);
    }

    public void optimalisasiNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Optimalisasi_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                OptimalisasiDataModel status = OptimalisasiDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    alertSatuTombol(status.getMsg(),"Oke");
                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    Toast.makeText(getActivity(),status.getMsg(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(getActivity(),"Terjadi kesalahan",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(getActivity(),"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void alertSatuTombol(String Msg,String txt) {
        new AlertDialog.Builder(getActivity())
                .setMessage(Msg)
                .setPositiveButton(txt,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        }).show();
    }
}
