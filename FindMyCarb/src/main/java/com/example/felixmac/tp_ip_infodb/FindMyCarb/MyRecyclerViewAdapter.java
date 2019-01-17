package com.example.felixmac.tp_ip_infodb.FindMyCarb;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.parser.Station;

import java.text.Normalizer;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    /**
     * LOGGER
     */
    private static String TAG = "MyRecyclerViewAdapter";
    private List<Station> mDataset;
    private static MyClickListener myClickListener;
    private String typeCarburant;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView adress;
        TextView price;
        TextView typeCarburant;
        TextView distance;
        /**
         * @param itemView
         */
        public DataObjectHolder(View itemView) {
            super(itemView);
            adress = (TextView) itemView.findViewById(R.id.textView2);
            price = (TextView) itemView.findViewById(R.id.textView4);
            typeCarburant = (TextView) itemView.findViewById(R.id.textView3);
            distance = (TextView) itemView.findViewById(R.id.textView);
            Log.i(TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    /**
     * @param myClickListener
     */
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    /**
     * @param myDataset
     * @param typeCarburant
     */
    public MyRecyclerViewAdapter(List<Station> myDataset, String typeCarburant) {
        this.mDataset = myDataset;
        this.typeCarburant = typeCarburant;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.adress.setText(mDataset.get(position).getAdresse());
        holder.typeCarburant.setText(typeCarburant);
        switch (typeCarburant) {
            case "Gazole":
                holder.price.setText(mDataset.get(position).getGazole().toString() + "€");
                break;
            case "SP95":
                holder.price.setText(mDataset.get(position).getSP95().toString() + "€");
                break;
            case "SP98":
                holder.price.setText(mDataset.get(position).getSP98().toString() + "€");
                break;
            case "E10":
                holder.price.setText(mDataset.get(position).getE10().toString() + "€");
                break;
            case "E85":
                holder.price.setText(mDataset.get(position).getE85().toString() + "€");
                break;
            case "GPLc":
                holder.price.setText(mDataset.get(position).getGPLc().toString() + "€");
                break;
        }
        if (mDataset.get(position).getDistance_user() != null) {
            String st = mDataset.get(position).getDistance_user().toString() + " km";
            holder.distance.setText(st);
        }
    }

    /**
     * Permet de spliter l'adresse au format de l'url de google maps
     *
     * @param adresse
     * @return
     */
    public String splitAdresse(String adresse) {
        String[] array = adresse.split(" ");
        adresse = "";
        int i = 0;
        for (String morceau : array) {
            morceau = Normalizer.normalize(morceau, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // On enlève tous les accents
            adresse = adresse + morceau;
            if (i < array.length - 1) adresse = adresse + "+";
            i++;
        }
        return adresse;
    }

    /**
     * @return
     */
    public List<Station> getmDataset() {
        return mDataset;
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Listener clique item
     */
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}