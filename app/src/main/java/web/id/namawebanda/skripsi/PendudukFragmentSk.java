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

public class PendudukFragmentSk extends Fragment {
    TextView bk,k,ch,cm,jp;
    String mbk,mk,mch,mcm,mjp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_penduduk_sk, container , false);
        String obj= getArguments().getString("obj");

        DecimalFormat titikribu = new DecimalFormat("#,###,###");


        bk = v.findViewById(R.id.bk);
        k = v.findViewById(R.id.k);
        ch = v.findViewById(R.id.ch);
        cm = v.findViewById(R.id.cm);
        jp = v.findViewById(R.id.jp);

        try {

            JSONObject response =new JSONObject(obj);
            Log.d("rekusaha OBJ",""+response.toString());
            Log.d("Status"," response : "+response.toString());
            PendudukSkDataModel status = PendudukSkDataModel.fromJson(response);
            //
            final PendudukFragmentJk fragment1 = new PendudukFragmentJk();
            final PendudukFragmentSk fragment2 = new PendudukFragmentSk();
            final PendudukFragmentPekerjaan fragment3 = new PendudukFragmentPekerjaan();
            final PendudukFragmentPendidikan fragment4 = new PendudukFragmentPendidikan();
            final PendudukFragmentUmur fragment5 = new PendudukFragmentUmur();
            Log.d("Status"," response : "+response.toString());

            Bundle bundle = new Bundle();
            String objek = response.toString();
            bundle.putString("obj", objek);
            fragment1.setArguments(bundle);
            fragment2.setArguments(bundle);
            fragment3.setArguments(bundle);
            fragment4.setArguments(bundle);
            fragment5.setArguments(bundle);
            //
            if(status.getStatus() == 200){
                mbk = titikribu.format(Integer.parseInt(status.getMbk()));
                mk = titikribu.format(Integer.parseInt(status.getMk()));
                mch = titikribu.format(Integer.parseInt(status.getMch()));
                mcm = titikribu.format(Integer.parseInt(status.getMcm()));
                mjp = titikribu.format(Integer.parseInt(status.getMjp()));

                bk.setText(mbk);
                k.setText(mk);
                ch.setText(mch);
                cm.setText(mcm);
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
