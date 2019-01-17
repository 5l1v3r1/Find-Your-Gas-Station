package com.example.felixmac.tp_ip_infodb.FindMyCarb;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.felixmac.tp_ip_infodb.FindMyCarb.task.DownloadExtractTask;

public class MainActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;

    private static Context context;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        // On set les "property" pour le parsing
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(context.getString(R.string.wait));
        progressDialog.show();

        DownloadExtractTask downloadExtractTask = new DownloadExtractTask(this, progressDialog, MainActivity.context);
        downloadExtractTask.execute("");
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
}
