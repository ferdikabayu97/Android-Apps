package web.id.namawebanda.skripsi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class PendudukFragmentJk extends Fragment {

    TextView pria,wanita,jp;
    String mpria,mwanita,mjp;
    String mu0,mu5,mu10,mu15,mu20,mu25,mu30,mu35,mu40,mu45,mu50,mu55,mu60,mu65,mu70,mu75;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_penduduk_jk, container , false);

        String obj= getArguments().getString("obj");

        DecimalFormat titikribu = new DecimalFormat("#,###,###");

        pria = v.findViewById(R.id.pria);
        wanita = v.findViewById(R.id.wanita);
        jp = v.findViewById(R.id.jp);

        try {


            JSONObject response =new JSONObject(obj);
            Log.d("rekusaha OBJ",""+response.toString());
            Log.d("Status"," response : "+response.toString());
            PendudukJKDataModel status = PendudukJKDataModel.fromJson(response);
            if(status.getStatus() == 200){
                mpria = titikribu.format(Integer.parseInt(status.getMpria()));
                mwanita = titikribu.format(Integer.parseInt(status.getMwanita()));
                mjp = titikribu.format(Integer.parseInt(status.getMjp()));

                pria.setText(mpria);
                wanita.setText(mwanita);
                jp.setText(mjp);



            } else {
                Log.d("Rekusaha",""+status.getMsg());
                Toast.makeText(getActivity(),status.getMsg(),Toast.LENGTH_SHORT).show();

//                    pass.setText("");
//
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

}
