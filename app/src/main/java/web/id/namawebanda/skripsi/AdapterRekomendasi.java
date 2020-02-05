package web.id.namawebanda.skripsi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterRekomendasi extends RecyclerView.Adapter<AdapterRekomendasi.ViewHolder>{


    private List<List_RekomendasiItem> listItems;
    private Context mContext;

    public AdapterRekomendasi(List<List_RekomendasiItem> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rekomendasi_item, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List_RekomendasiItem listItem = listItems.get(i);

        viewHolder.textViewAlternatif.setText(listItem.getNama_alternatif());
        viewHolder.textViewPeringkat.setText(listItem.getPeringkat());
        viewHolder.textViewSkor.setText(listItem.getSkor());
        viewHolder.textViewRharga.setText(listItem.getRharga());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewAlternatif;
        public TextView textViewPeringkat;
        public TextView textViewSkor;
        public TextView textViewRharga;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAlternatif = itemView.findViewById(R.id.alternatif);
            textViewPeringkat = itemView.findViewById(R.id.peringkat);
            textViewRharga = itemView.findViewById(R.id.rharga);
            textViewSkor = itemView.findViewById(R.id.skor);
        }
    }
}
