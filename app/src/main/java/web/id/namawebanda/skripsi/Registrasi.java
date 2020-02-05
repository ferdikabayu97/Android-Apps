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

public class Registrasi extends AppCompatActivity {

    String Registrasi_URL = "http://rekusaha.com/api/bakun/";
    Button buatAkun;
    ProgressDialog mDialog;

    EditText nama,email,username,pass1,pass2;

    String  mnama,memail,musername,mpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        buatAkun = findViewById(R.id.btn_buatakun);
        //
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sedang Mengecek data");
        mDialog.setCancelable(false);
        //
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        pass1 = findViewById(R.id.pass1);
        pass2 = findViewById(R.id.pass2);

        buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnama = nama.getText().toString();
                memail = email.getText().toString();
                musername = username.getText().toString();
                mpass = pass1.getText().toString();

                Log.d("Rekusaha",""+pass1.getText().toString());
                Log.d("Rekusaha",""+pass2.getText().toString());

                if(verifikasiIsi(nama.getText().toString())&&verifikasiIsi(pass2.getText().toString())&&verifikasiIsi(pass1.getText().toString())&&verifikasiIsi(username.getText().toString())&&verifikasiIsi(email.getText().toString())){
                    if(pass1.getText().toString().equals(pass2.getText().toString())){
                    getAPIRegistrasi(musername, memail, mpass, mnama);
                }else{
                    Toast.makeText(Registrasi.this,"Password dan Konfirmasi Password Tidak sama",Toast.LENGTH_SHORT).show();
                }
                } else {
                    Toast.makeText(Registrasi.this,"Silahkan Lengkapi Data Terlebihdahulu",Toast.LENGTH_SHORT).show();

                }

                //

                //
            }
        });
    }
    public boolean verifikasiIsi (String isi){

        if (isi.isEmpty()){
            return false;
        }

        return true;
    }

    public void getAPIRegistrasi(String username, String email, String pass,  String nama){
        RequestParams param = new RequestParams();
        param.put("nama",nama);
        param.put("email",email);
        param.put("username",username);
        param.put("password",pass);
        RegistrasiNetworking(param);
    }

    public void RegistrasiNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(Registrasi_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                RegistrasiDataModel status = RegistrasiDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    alertSatuTombol(status.getMsg());
                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                    Toast.makeText(Registrasi.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo 2");
                mDialog.dismiss();
                Toast.makeText(Registrasi.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.e("Rekusaha","ohnoooooo 1");
                mDialog.dismiss();
                Toast.makeText(Registrasi.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void alertSatuTombol(String Msg) {
        new AlertDialog.Builder(Registrasi.this)
                .setMessage(Msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent regis=new Intent(Registrasi.this, Verifikasi.class);
                                regis.putExtra("username", username.getText().toString());
                                startActivity(regis);
                                finish();
                                dialog.cancel();
                            }
                        }).show();
    }

    @Override
    public void onBackPressed() {
        Intent back=new Intent(Registrasi.this, Login.class);
        startActivity(back);
        finish();
    }
}
