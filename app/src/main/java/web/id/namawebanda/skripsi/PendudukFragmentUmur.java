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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class PendudukFragmentUmur extends Fragment {

    TextView u0,u5,u10,u15,u20,u25,u30,u35,u40,u45,u50,u55,u60,u65,u70,u75,jp;
    String mu0,mu5,mu10,mu15,mu20,mu25,mu30,mu35,mu40,mu45,mu50,mu55,mu60,mu65,mu70,mu75,mjp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_penduduk_umur, container , false);
        String obj= getArguments().getString("obj");
        DecimalFormat titikribu = new DecimalFormat("#,###,###");


        u0= v.findViewById(R.id.u0);
        u5= v.findViewById(R.id.u5);
        u10= v.findViewById(R.id.u10);
        u15= v.findViewById(R.id.u15);
        u20= v.findViewById(R.id.u20);
        u25= v.findViewById(R.id.u25);
        u30= v.findViewById(R.id.u30);
        u35= v.findViewById(R.id.u35);
        u40= v.findViewById(R.id.u40);
        u45= v.findViewById(R.id.u45);
        u50= v.findViewById(R.id.u50);
        u55= v.findViewById(R.id.u55);
        u60= v.findViewById(R.id.u60);
        u65= v.findViewById(R.id.u65);
        u70= v.findViewById(R.id.u70);
        u75= v.findViewById(R.id.u75);
        jp= v.findViewById(R.id.jp);
        try {


            JSONObject response =new JSONObject(obj);
            Log.d("rekusaha OBJ",""+response.toString());

            PendudukUmurDataModel status = PendudukUmurDataModel.fromJson(response);

            if(status.getStatus() == 200){
            mu0= titikribu.format(Integer.parseInt(status.getMu0()));
            mu5= titikribu.format(Integer.parseInt(status.getMu5()));
            mu10= titikribu.format(Integer.parseInt(status.getMu10()));
            mu15= titikribu.format(Integer.parseInt(status.getMu15()));
            mu20= titikribu.format(Integer.parseInt(status.getMu20()));
            mu25= titikribu.format(Integer.parseInt(status.getMu25()));
            mu30= titikribu.format(Integer.parseInt(status.getMu30()));
            mu35= titikribu.format(Integer.parseInt(status.getMu35()));
            mu40= titikribu.format(Integer.parseInt(status.getMu40()));
            mu45= titikribu.format(Integer.parseInt(status.getMu45()));
            mu50= titikribu.format(Integer.parseInt(status.getMu50()));
            mu55= titikribu.format(Integer.parseInt(status.getMu55()));
            mu60= titikribu.format(Integer.parseInt(status.getMu60()));
            mu65= titikribu.format(Integer.parseInt(status.getMu65()));
            mu70= titikribu.format(Integer.parseInt(status.getMu70()));
            mu75= titikribu.format(Integer.parseInt(status.getMu75()));
            mjp= titikribu.format(Integer.parseInt(status.getMjp()));


            u0.setText(mu0);
            u5.setText(mu5);
            u10.setText(mu10);
            u15.setText(mu15);
            u20.setText(mu20);
            u25.setText(mu25);
            u30.setText(mu30);
            u35.setText(mu35);
            u40.setText(mu40);
            u45.setText(mu45);
            u50.setText(mu50);
            u55.setText(mu55);
            u60.setText(mu60);
            u65.setText(mu65);
            u70.setText(mu70);
            u75.setText(mu75);
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
