package web.id.namawebanda.skripsi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterPesaing extends RecyclerView.Adapter<AdapterPesaing.ViewHolder>{


    private List<List_PesaingItem> listItems;
    private Context mContext;

    public AdapterPesaing(List<List_PesaingItem> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pesaing_item, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List_PesaingItem listItem = listItems.get(i);

        viewHolder.textViewNama_Alternatif.setText(listItem.getNama_pemilik());
        viewHolder.textViewAlamat.setText(listItem.getAlamat());
        viewHolder.textViewKet.setText(listItem.getKet());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewNama_Alternatif;
        public TextView textViewAlamat;
        public TextView textViewKet;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama_Alternatif = itemView.findViewById(R.id.nama_pemilik);
            textViewAlamat = itemView.findViewById(R.id.alamat);
            textViewKet = itemView.findViewById(R.id.ket);
        }
    }
}
