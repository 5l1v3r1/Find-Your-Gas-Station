package com.example.felixmac.tp_ip_infodb.FindMyCarb.parser;

import android.content.Context;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import android.content.pm.PackageManager;
import android.location.Address;
import android.Manifest;
import android.location.Location;

import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Carburants{
    /**
     * Logger
     */
    public static final String TAG = Carburants.class.getName();

    /**
     * Liste des stations services
     */
    private List<List<String>> listOfLists = new ArrayList<List<String>>();

    /**
     * Flux de données
     */
    private InputStream stream = null;

    /**
     * Constructeur Basic
     */
    public Carburants() {
    }

    /**
     * Va chercher l'Open-Stream via une requête http
     */
    public void getHttpStream() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();

            Request requestTest = new Request.Builder().url("https://donnees.roulez-eco.fr/opendata/instantane").build();
            Response http_response_test = okHttpClient.newCall(requestTest).execute();
            InputStream stream = http_response_test.body().byteStream();

            this.stream = stream;


            Log.d(TAG, "Récupération du flux de données");
        } catch (Exception e) {
            Log.e(TAG, "Problème de récupération du flux de données : " + e);
        }
    }

    /**
     * Décompresse une archive zip
     *
     * @param chemin
     */
    public void unzipTo(String chemin) {
        MyZip zipFile = new MyZip();
        zipFile.unzip(this.stream, chemin);
    }

    /**
     * Parse le fichier xml de models
     *
     * @param chemin
     */
    public void parseFile(String chemin) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            CarburantsParser SaxRead = new CarburantsParser();

            xr.setContentHandler(SaxRead);
            xr.parse(chemin);

            this.listOfLists = SaxRead.getListOfLists();

            Log.d(TAG, "Parsing Ok");

        } catch (Exception e) {
            Log.e(TAG, "Problème lors du parsing : " + e);
        }
    }

    /**
     * Donne en fonction de la ville les stations-services (adresses+carburants)
     *
     * @param ville
     * @return
     */
    public List<Station> stationsOf(String ville) {
        List<Station> listStations = new ArrayList<Station>();
        int j;
        String[] Array;
        for (List<String> listOfList : listOfLists) {

            if (listOfList.get(1).equals("ville:" + ville.trim().toLowerCase())) {
                j = 0;
                Station station = new Station();

                for (String x : listOfList) {
                    Array = listOfList.get(j).split(":");

                    switch (Array[0]) {
                        case "adresse":
                            station.setAdresse(Array[1]);
                            break;
                        case "Gazole":
                            station.setGazole(Array[1]);
                            break;
                        case "SP95":
                            station.setSP95(Array[1]);
                            break;
                        case "SP98":
                            station.setSP98(Array[1]);
                            break;
                        case "E10":
                            station.setE10(Array[1]);
                            break;
                        case "E85":
                            station.setE85(Array[1]);
                            break;
                        case "GPLc":
                            station.setGPLc(Array[1]);
                            break;
                    }
                    ++j;
                }
                listStations.add(station);
            }
        }
        return listStations;
    }

    /**
     * Calcule la distance entre une adresse et le point GPS du téléphone.
     *
     * @param strAddress
     * @param context
     * @return
     */
    public Float getDistanceFromAddress(String strAddress, Context context) {
        Geocoder coder = new Geocoder(context);

        try {
            List<Address> address = coder.getFromLocationName(strAddress, 10);
            Address location = address.get(0);
            double latitude_station = location.getLatitude();
            double longitude_station = location.getLongitude();

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission non accordé");
                return null;
            } else {
                // Localisation téléphone
                Location loc2 = new Location("");
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                assert lm != null;
                Location location_user = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location_user != null) {
                    double longitude_user = location_user.getLongitude();
                    double latitude_user = location_user.getLatitude();
                    loc2.setLatitude(latitude_user);
                    loc2.setLongitude(longitude_user);

                    Log.d(TAG, "Géocodage terminé");

                    Location loc1 = new Location("");
                    loc1.setLatitude(latitude_station);
                    loc1.setLongitude(longitude_station);

                    float distanceInKMeters = loc1.distanceTo(loc2) / 1000;

                    // On retourne la distance en kilomètres
                    return (float) Math.round(distanceInKMeters * 10) / 10;
                }else{
                    Log.d(TAG,"Fast Fix GPS null");
                }
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Geocodage erreur : " + e);
        }
        return null;
    }
}
