package com.example.felixmac.tp_ip_infodb.FindMyCarb.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.Main2Activity;
import com.example.felixmac.tp_ip_infodb.FindMyCarb.R;
import com.example.felixmac.tp_ip_infodb.FindMyCarb.parser.Carburants;


public class DownloadExtractTask extends AsyncTask<String, String, Carburants>{
    /**
     * LOGGER
     */
    public static final String TAG = DownloadExtractTask.class.getName();

    private AppCompatActivity parentActivity;
    private ProgressDialog progressDialog;
    private Carburants carburants;
    private Context context;

    /**
     *
     * @param parentActivity
     * @param progressDialog
     * @param context
     */
    public DownloadExtractTask(AppCompatActivity parentActivity, ProgressDialog progressDialog, Context context) {
        this.parentActivity = parentActivity;
        this.progressDialog = progressDialog;
        this.carburants = new Carburants();
        this.context = context;
    }

    /**
     *
     * @param strings
     * @return
     */
    @Override
    protected Carburants doInBackground(String... strings) {
        for (String rien : strings) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            publishProgress(context.getString(R.string.download));
            // Si on détécte le réseau on effectue le téléchargement des données et l'unzip
            if(isConnected) carburants.getHttpStream();

            try {
                for (String i : context.fileList()) {
                    Log.d(TAG, "Files list before : " + i);
                }

                publishProgress(context.getString(R.string.unzip));
                if (isConnected) carburants.unzipTo(context.getFilesDir().toString());

                for (String i : context.fileList()) {
                    Log.d(TAG, "Files list after : " + i);
                }
                // Aucun retour necessaire
                return null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * @param carburants
     */
    @Override
    protected void onPostExecute(Carburants carburants) {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        // Changement d'activité
        Intent intent = new Intent(parentActivity, Main2Activity.class);

        parentActivity.startActivity(intent);
    }

    /**
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(String... values) {
        progressDialog.setMessage(values[0]);
    }

}
