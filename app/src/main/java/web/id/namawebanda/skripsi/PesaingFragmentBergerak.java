package web.id.namawebanda.skripsi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesaingFragmentBergerak extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<List_PesaingItem> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pesaing_bergerak, container , false);


        //
        mRecyclerView = v.findViewById(R.id.recyclerview);//ceklagi
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
        listItems = new ArrayList<>();
        //
        String obj= getArguments().getString("obj");

        try {


            JSONObject response =new JSONObject(obj);
            Log.d("rekusaha OBJ",""+response.toString());
            Log.d("Status"," response : "+response.toString());
            PesaingBergerakDataModel status = PesaingBergerakDataModel.fromJson(response);
            if(status.getStatus() == 200){
                JSONArray array = status.getmArray();
                if(array.length() > 0){
                    for (int i=0; i<array.length();i++){

                        JSONObject o = array.getJSONObject(i);
                        List_PesaingItem item = new List_PesaingItem(
                                o.getString("nama_pemilik"),
                                o.getString("alamat"),
                                o.getString("ket")
                        );
                        listItems.add(item);
                    }
                    mAdapter = new AdapterPesaing(listItems,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    TextView a = v.findViewById(R.id.kosong);
                    a.setText("Data Kosong");
                }
            } else {
                Log.d("Rekusaha",""+status.getMsg());
                Toast.makeText(getActivity(),status.getMsg(),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }
}
