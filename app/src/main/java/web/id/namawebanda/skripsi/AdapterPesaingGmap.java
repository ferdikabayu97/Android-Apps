package web.id.namawebanda.skripsi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterPesaingGmap extends RecyclerView.Adapter<AdapterPesaingGmap.ViewHolder>{


    private List<List_PesaingGmaps> listItems;
    private Context mContext;

    public AdapterPesaingGmap(List<List_PesaingGmaps> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pesaing_gmaps, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List_PesaingGmaps listItem = listItems.get(i);

        viewHolder.textViewName.setText(listItem.getName());
        viewHolder.textViewVicinity.setText(listItem.getVicinity());
        viewHolder.textViewOpen_now.setText(listItem.getOpen_now());
        viewHolder.textViewRating.setText(listItem.getRating());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewVicinity;
        public TextView textViewOpen_now;
        public TextView textViewRating;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewVicinity = itemView.findViewById(R.id.vicinity);
            textViewOpen_now = itemView.findViewById(R.id.open_now);
            textViewRating = itemView.findViewById(R.id.rating);
        }
    }
}
