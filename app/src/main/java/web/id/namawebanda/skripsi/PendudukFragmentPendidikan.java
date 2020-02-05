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

public class PendudukFragmentPendidikan extends Fragment {
   TextView bs,bts,sd,smp,sma,di,diii,s1,s2,s3,jp;
    String mbs,mbts,msd,msmp,msma,mdi,mdiii,ms1,ms2,ms3,mjp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_penduduk_pendidikan, container , false);
        String obj= getArguments().getString("obj");

        DecimalFormat titikribu = new DecimalFormat("#,###,###");


        bs= v.findViewById(R.id.bs);
        bts= v.findViewById(R.id.bts);
        sd= v.findViewById(R.id.sd);
        smp= v.findViewById(R.id.smp);
        sma= v.findViewById(R.id.sma);
        di= v.findViewById(R.id.didii);
        diii= v.findViewById(R.id.diii);
        s1= v.findViewById(R.id.s1);
        s2= v.findViewById(R.id.s2);
        s3= v.findViewById(R.id.s3);
        jp= v.findViewById(R.id.jp);

        if(obj != null) {

            try {


                JSONObject response = new JSONObject(obj);
                PendudukPendidikanDataModel status = PendudukPendidikanDataModel.fromJson(response);



                if (status.getStatus() == 200) {
                    mbs = titikribu.format(Integer.parseInt(status.getMbs()));
                    mbts = titikribu.format(Integer.parseInt(status.getMbts()));
                    msd = titikribu.format(Integer.parseInt(status.getMsd()));
                    msmp = titikribu.format(Integer.parseInt(status.getMsmp()));
                    msma = titikribu.format(Integer.parseInt(status.getMsma()));
                    mdi = titikribu.format(Integer.parseInt(status.getMdi()));
                    mdiii = titikribu.format(Integer.parseInt(status.getMdiii()));
                    ms1 = titikribu.format(Integer.parseInt(status.getMs1()));
                    ms2 = titikribu.format(Integer.parseInt(status.getMs2()));
                    ms3 = titikribu.format(Integer.parseInt(status.getMs3()));
                    mjp = titikribu.format(Integer.parseInt(status.getMjp()));

                    bs.setText(mbs);
                    bts.setText(mbts);
                    sd.setText(msd);
                    smp.setText(msmp);
                    sma.setText(msma);
                    di.setText(mdi);
                    diii.setText(mdiii);
                    s1.setText(ms1);
                    s2.setText(ms2);
                    s3.setText(ms3);
                    jp.setText(mjp);


                } else {
                    Log.d("Rekusaha", "" + status.getMsg());
                    Toast.makeText(getActivity(), status.getMsg(), Toast.LENGTH_SHORT).show();

//                    pass.setText("");
//
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return v;
    }
}
