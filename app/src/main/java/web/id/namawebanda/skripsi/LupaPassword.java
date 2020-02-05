package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.Dialog;
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

public class LupaPassword extends AppCompatActivity {
    //constrant
    String LupaPass_URL = "http://rekusaha.com/api/lpass/";

    EditText email,username;
    ProgressDialog mDialog;
    String musername,memail;

    Button kirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        //
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sedang Mengecek data");
        mDialog.setCancelable(false);
        //
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);

        kirim = findViewById(R.id.btn_kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid;

                musername = username.getText().toString();
                memail = email.getText().toString();
                valid = verifikasiIsi(musername,memail);
                if(valid == true){
                    getAPILupaPassword(musername,memail);
                }else{
                    Toast.makeText(LupaPassword.this,"Silahkan Lengkapi Data Terlebihdahulu",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public boolean verifikasiIsi (String username, String email){

        if (username.isEmpty()){
            return false;
        }
        if (email.isEmpty()){
            return false;
        }

        return true;
    }
    public void getAPILupaPassword(String username, String email){
        RequestParams param = new RequestParams();
        param.put("username",username);
        param.put("email",email);
        lupaPassNetworking(param);
    }

    public void lupaPassNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(LupaPass_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                LupaPasswordDataModel status = LupaPasswordDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    alertSatuTombol(status.getMsg());
                } else {
                    Log.d("Rekusaha",""+status.getMsg());
                    Toast.makeText(LupaPassword.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(LupaPassword.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Rekusaha","ohnoooooo ");
                mDialog.dismiss();
                Toast.makeText(LupaPassword.this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void alertSatuTombol(String Msg) {
        new AlertDialog.Builder(LupaPassword.this)
                .setMessage(Msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent terkirim=new Intent(LupaPassword.this, Login.class);
                                startActivity(terkirim);
                                finish();
                                dialog.cancel();
                            }
                        }).show();
    }

    @Override
    public void onBackPressed() {
        Intent back=new Intent(LupaPassword.this, Login.class);
        startActivity(back);
        finish();
    }
}
