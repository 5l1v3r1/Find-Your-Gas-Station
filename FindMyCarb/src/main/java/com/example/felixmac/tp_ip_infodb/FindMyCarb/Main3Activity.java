package com.example.felixmac.tp_ip_infodb.FindMyCarb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.parser.Station;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    /**
     * LOGGER
     */
    public static final String TAG = Main3Activity.class.getName();

    private static Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Main3Activity.context = getApplicationContext();

        List<Station> listStation;
        listStation = (ArrayList) getIntent().getParcelableArrayListExtra("listStation");

        // Vérification des données
        Log.d(TAG, "listStations empty ? " + listStation.isEmpty());
        for (int i = 0; i < listStation.size(); i++) {
            Log.d(TAG, "Station : " + listStation.get(i).getAdresse());
        }

        // Set du recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String typeCarburant = getIntent().getExtras().getString("typeCarburants");
        mAdapter = new MyRecyclerViewAdapter(listStation, typeCarburant);
        mRecyclerView.setAdapter(mAdapter);

        // Message d'erreur de connexion au réseau
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){
            Snackbar.make(findViewById(R.id.myCoordinatorLayout2), R.string.network,
                    Snackbar.LENGTH_INDEFINITE)
                    .show();
        }
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, " Clicked on Item " + position);
                List<Station> list = ((MyRecyclerViewAdapter) mAdapter).getmDataset();
                String Adresse = list.get(position).getAdresse();
                String adresse = ((MyRecyclerViewAdapter) mAdapter).splitAdresse(Adresse);

                Log.d(TAG, "URL : " + "https://www.google.com/maps/search/?api=1&query=" + adresse+"+"+getIntent().getExtras().getString("ville"));
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.google.com/maps/search/?api=1&query=" + adresse+"+"+getIntent().getExtras().getString("ville")));
                startActivity(intent);
            }
        });
    }

}
