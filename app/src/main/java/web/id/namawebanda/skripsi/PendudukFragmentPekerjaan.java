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

public class PendudukFragmentPekerjaan extends Fragment {
    TextView tb,apn,tp,w,per,nel,bkea,pdm,tk,pensi,lain,jp;
    String mtb,mapn,mtp,mw,mper,mnel,mbkea,mpdm,mtk,mpensi,mlain,mjp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_penduduk_pekerjaan, container , false);
        String obj= getArguments().getString("obj");

        DecimalFormat titikribu = new DecimalFormat("#,###,###");


        tb = v.findViewById(R.id.tb);
        apn = v.findViewById(R.id.apn);
        tp = v.findViewById(R.id.tp);
        w = v.findViewById(R.id.w);
        per = v.findViewById(R.id.per);
        nel = v.findViewById(R.id.nel);
        bkea = v.findViewById(R.id.bkea);
        pdm = v.findViewById(R.id.pdm);
        tk = v.findViewById(R.id.tk);
        pensi = v.findViewById(R.id.pensi);
        lain = v.findViewById(R.id.lain);
        jp = v.findViewById(R.id.jp);


        try {


            JSONObject response =new JSONObject(obj);
            Log.d("rekusaha OBJ",""+response.toString());
            PendudukPekerjaanDataModel status = PendudukPekerjaanDataModel.fromJson(response);
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

                mtb= titikribu.format(Integer.parseInt(status.getMtb()));
                mapn= titikribu.format(Integer.parseInt(status.getMapn()));
                mtp= titikribu.format(Integer.parseInt(status.getMtp()));
                mw= titikribu.format(Integer.parseInt(status.getMw()));
                mper= titikribu.format(Integer.parseInt(status.getMper()));
                mnel= titikribu.format(Integer.parseInt(status.getMnel()));
                mbkea= titikribu.format(Integer.parseInt(status.getMbkea()));
                mpdm= titikribu.format(Integer.parseInt(status.getMpdm()));
                mtk= titikribu.format(Integer.parseInt(status.getMtk()));
                mpensi= titikribu.format(Integer.parseInt(status.getMpensi()));
                mlain= titikribu.format(Integer.parseInt(status.getMlain()));

                mjp = titikribu.format(Integer.parseInt(status.getMjp()));


                tb.setText(mtb);
                apn.setText(mapn);
                tp.setText(mtp);
                w.setText(mw);
                per.setText(mper);
                nel.setText(mnel);
                bkea.setText(mbkea);
                pdm.setText(mpdm);
                tk.setText(mtk);
                pensi.setText(mpensi);
                lain.setText(mlain);
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
