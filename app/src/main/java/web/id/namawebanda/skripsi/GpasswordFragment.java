package web.id.namawebanda.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class GpasswordFragment extends Fragment {
    private String mpasslama,mpassbaru,mpasskonf,mtoken,musername;
    private String GantiPass_URL = "http://rekusaha.com/api/gpass/";
    private EditText passlama,passkonf,passbaru;
    private ProgressDialog mDialog;

    Button gpass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gpassword, container , false);

        mtoken = getActivity().getIntent().getStringExtra("token");
        musername = getActivity().getIntent().getStringExtra("username");
        passlama = v.findViewById(R.id.passlama);
        passbaru = v.findViewById(R.id.passbaru);
        passkonf = v.findViewById(R.id.kpassbaru);


        //
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Sedang Mengecek data");
        mDialog.setCancelable(false);
        //

        gpass = v.findViewById(R.id.btn_kirimgpass);

        gpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid;

                mpasslama = passlama.getText().toString();
                mpassbaru = passbaru.getText().toString();
                mpasskonf = passkonf.getText().toString();
                valid = verifikasiIsi(mpasslama,mpassbaru,mpasskonf);
                if(valid == true){
                    if(mpassbaru.equals(mpasskonf)){
                    getAPIGantiPassword(mtoken,musername, mpassbaru, mpasslama);

                    }else{
                        Toast.makeText(getActivity(),"Password baru tidak sama dengan konfirmasi password",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getActivity(),"Silahkan Lengkapi Data Terlebihdahulu",Toast.LENGTH_SHORT).show();

                }
            }
        });

        return v;
    }
    public boolean verifikasiIsi (String pass, String gpass, String kpass){

        if (pass.isEmpty()){
            return false;
        }
        if (gpass.isEmpty()){
            return false;
        }
        if (kpass.isEmpty()){
            return false;
        }
        return true;
    }
    public void getAPIGantiPassword(String token,String username, String pass1, String pass2){
        RequestParams param = new RequestParams();
        param.put("token",token);
        param.put("username",username);
        param.put("newpass",pass1);
        param.put("oldpass",pass2);

        gantiPassNetworking(param);
    }

    public void gantiPassNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        mDialog.show();
        client.get(GantiPass_URL,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Status"," response : "+response.toString());
                GpasswordDataModel status = GpasswordDataModel.fromJson(response);
                if(status.getStatus() == 200){
                    Log.d("Rekusaha",""+status.getMsg());
                    mDialog.dismiss();
                    alertSatuTombol(status.getMsg());
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
    public void alertSatuTombol(String Msg) {
        new AlertDialog.Builder(getActivity())
                .setMessage(Msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        }).show();
    }
}
