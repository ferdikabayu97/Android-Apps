package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Verifikasi extends AppCompatActivity {
    String Verifikasi_URL = "http://rekusaha.com/api/veri/";
    EditText kodever;
    Button kirim;
    ProgressDialog mDialog;
    String musername,mkode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        Intent username = getIntent();

        musername= username.getStringExtra("username");
        kodever = findViewById(R.id.kodever);
        //
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sedang Mengecek data");
        mDialog.setCancelable(false);
        //

        kirim = findViewById(R.id.btn_kirimkode);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mkode = kodever.getText().toString();
            if(verifikasiIsi(mkode)&&verifikasiIsi(musername)){
                Log.d("Rekusaha","username "+musername);
                getAPIVerifikasi(musername,mkode);
            }else {
                Toast.makeText(Verifikasi.this,"Silahkan Lengkapi Data Terlebihdahulu",Toast.LENGTH_SHORT).show();

            }

            }
        });
    }
    public boolean verifikasiIsi (String isi){

        if (isi.isEmpty()){
            return false;
        }

        return true;
    }

    public void getAPIVerifikasi(String username, String kode){
        RequestParams param = new RequestParams();
        param.put("username",username);
        param.put("kode",kode);
        VerifikasiNetworking(param);
    }

    public void VerifikasiNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Verifikasi_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                VerifikasiDataModel status = VerifikasiDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    alertSatuTombol(status.getMsg());
                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                    Toast.makeText(Verifikasi.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(Verifikasi.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(Verifikasi.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void alertSatuTombol(String Msg) {
        new AlertDialog.Builder(Verifikasi.this)
                .setMessage(Msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent veri=new Intent(Verifikasi.this, Login.class);
                                startActivity(veri);
                                finish();
                                dialog.cancel();
                            }
                        }).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(Verifikasi.this)
                .setMessage("Apakah Anda yakin ingin meninggalkan laman verifikasi? \n Meninggalkan Laman berarti akun anda tidak akan aktif")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent veri=new Intent(Verifikasi.this, Login.class);
                                startActivity(veri);
                                finish();
                                dialog.cancel();
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        }).show();
    }
}
