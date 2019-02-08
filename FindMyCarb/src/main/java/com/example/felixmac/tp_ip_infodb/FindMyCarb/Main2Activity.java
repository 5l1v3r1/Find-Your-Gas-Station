package com.example.felixmac.tp_ip_infodb.FindMyCarb;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.task.SearchStationTask;

public class Main2Activity extends AppCompatActivity {

    /**
     * LOGGER
     */
    public static final String TAG = Main2Activity.class.getName();

    private static Context context;

    protected ProgressDialog progressDialog;

    private String typeCarburant = "Gazole";

    private LocationManager locationManager;

    private LocationListener listenerGPS;

    private int countFixGPS =0;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Main2Activity.context = getApplicationContext();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // Message d'erreur de connexion au réseau
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.network,
                    Snackbar.LENGTH_INDEFINITE)
                    .show();
        }
        // FIX GPS
        fixGPS();
    }

    /**
     * Permet d'éffectuer 5 fix GPS
     */
    public void fixGPS(){
        listenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "loc : " + location.getLongitude() + " " + location.getLatitude());

                locationManager.removeUpdates(listenerGPS);
                Log.i(TAG, "Got location");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager == null) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listenerGPS);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listenerGPS);
        }
    }

    /**
     * Permet de lancer l'Async Task de recherche de stations
     * @param view
     */
    public void connect(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(context.getString(R.string.wait));
        progressDialog.show();

        TextView textView = findViewById(R.id.ville);
        ToggleButton toggleButton = findViewById(R.id.toggleButton);

        SearchStationTask searchStationTask = new SearchStationTask(this, progressDialog, Main2Activity.context, typeCarburant,toggleButton.isChecked());
        searchStationTask.execute(textView.getText().toString());
    }

    /**
     * Vérification du radio boutton séléctionné.
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        int i = (view.getId());
        if (i == R.id.radioButtonGazole) {
            if (checked)
                typeCarburant = "Gazole";

        } else if (i == R.id.radioButtonSP98) {
            if (checked)
                typeCarburant = "SP98";

        } else if (i == R.id.radioButtonSP95) {
            if (checked)
                typeCarburant = "SP95";

        } else if (i == R.id.radioButtonE10) {
            if (checked)
                typeCarburant = "E10";

        } else if (i == R.id.radioButtonE85) {
            if (checked)
                typeCarburant = "E85";

        } else if (i == R.id.radioButtonGPLc) {
            if (checked)
                typeCarburant = "GPLc";

        }
    }

    /**
     * Utilisé pour éviter le retour à l'activité précédente.
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();

        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Message d'erreur de connexion au réseau
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.network,
                    Snackbar.LENGTH_INDEFINITE)
                    .show();
        }
    }
}
