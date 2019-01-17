package com.example.felixmac.tp_ip_infodb.FindMyCarb.task;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.Main3Activity;
import com.example.felixmac.tp_ip_infodb.FindMyCarb.R;
import com.example.felixmac.tp_ip_infodb.FindMyCarb.parser.Carburants;
import com.example.felixmac.tp_ip_infodb.FindMyCarb.parser.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchStationTask extends AsyncTask<String, String, List<Station>> {
    /**
     * LOGGER
     */
    public static final String TAG = SearchStationTask.class.getName();
    private AppCompatActivity parentActivity;
    private ProgressDialog progressDialog;
    private Carburants carburants;
    private Context context;
    private String typeCarburants;
    private String ville;
    private Boolean distanceTrie;

    /**
     * @param parentActivity
     * @param progressDialog
     * @param context
     * @param typeCarburants
     */
    public SearchStationTask(AppCompatActivity parentActivity, ProgressDialog progressDialog, Context context, String typeCarburants, Boolean distanceTrie) {
        this.parentActivity = parentActivity;
        this.progressDialog = progressDialog;
        this.carburants = new Carburants();
        this.context = context;
        this.typeCarburants = typeCarburants;
        this.distanceTrie = distanceTrie;
    }

    /**
     * @param strings
     * @return
     */
    @Override
    protected List<Station> doInBackground(String... strings) {
        for (String ville : strings) {

            try {
                publishProgress(context.getString(R.string.parsing));
                carburants.parseFile(context.getFilesDir().toString() + "/PrixCarburants_instantane.xml");

                List<Station> listStations;
                listStations = carburants.stationsOf(ville);
                this.ville = ville;

                // On supprime les stations inutile
                for (int i = listStations.size() - 1; i >= 0; i--) {
                    switch (typeCarburants) {
                        case "Gazole":
                            if (listStations.get(i).getGazole().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                        case "SP95":
                            if (listStations.get(i).getSP95().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                        case "SP98":
                            if (listStations.get(i).getSP98().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                        case "E10":
                            if (listStations.get(i).getE10().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                        case "E85":
                            if (listStations.get(i).getE85().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                        case "GPLc":
                            if (listStations.get(i).getGPLc().toString().equals("0.0")) {
                                listStations.remove(i);
                            }
                            break;
                    }
                }

                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                // Si l'autorisation de GPS est validé et si on dispose d'une connexion on effectue le géocodage
                if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) && isConnected) {
                    publishProgress(context.getString(R.string.geocoding));
                    // On calcule la distance entre chaque stations
                    for (Station station : listStations) {
                        Float distance = carburants.getDistanceFromAddress(station.getAdresse() + " " + ville, context);
                        Log.d(TAG, "adresse : " + station.getAdresse() + " " + ville);
                        station.setDistance_user(distance);
                    }
                }

                // On effectue les trie de la liste en fonction des paramètres de l'utilisateur
                if (distanceTrie) {
                    Log.d(TAG,"Trie distances");
                    Collections.sort(listStations, Station.distance_userComparator);
                } else {
                    // Trie selon le carburant choisi
                    switch (typeCarburants) {
                        case "Gazole":
                            Collections.sort(listStations, Station.GazoleComparator);
                            break;
                        case "SP95":
                            Collections.sort(listStations, Station.SP95Comparator);
                            break;
                        case "SP98":
                            Collections.sort(listStations, Station.SP98Comparator);
                            break;
                        case "E10":
                            Collections.sort(listStations, Station.E10Comparator);
                            break;
                        case "E85":
                            Collections.sort(listStations, Station.E85Comparator);
                            break;
                        case "GPLc":
                            Collections.sort(listStations, Station.GPLcComparator);
                            break;
                    }
                }

                return listStations;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param listStation
     */
    @Override
    protected void onPostExecute(List<Station> listStation) {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        // Changement d'activité
        Intent intent = new Intent(parentActivity, Main3Activity.class);

        intent.putParcelableArrayListExtra("listStation", (ArrayList) listStation);
        intent.putExtra("typeCarburants", typeCarburants);
        intent.putExtra("ville", ville);

        parentActivity.startActivity(intent);
    }

    /**
     * @param values
     */
    @Override
    protected void onProgressUpdate(String... values) {
        progressDialog.setMessage(values[0]);
    }
}
