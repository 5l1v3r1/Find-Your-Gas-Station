package com.example.felixmac.tp_ip_infodb.FindMyCarb.parser;

import android.util.Log;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MyZip {
    /**
     * Logger
     */
    public static final String TAG = MyZip.class.getName();

    /**
     * Dézippe un flux InputStream et enregistre le fichier
     * @param _zipFileStream
     * @param vers
     */
    public void unzip(InputStream _zipFileStream, String vers) {
        try  {
            Log.d(TAG, "Starting to unzip");
            InputStream fin = _zipFileStream;

            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.d(TAG, "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(vers + "/" + ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(new File(vers, ze.getName()));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;

                    // reading and writing
                    while((count = zin.read(buffer)) != -1)
                    {
                        baos.write(buffer, 0, count);
                        byte[] bytes = baos.toByteArray();
                        fout.write(bytes);
                        baos.reset();
                    }

                    fout.close();
                    zin.closeEntry();
                }

            }
            zin.close();
            Log.d(TAG, "Finished unzip");
        } catch(Exception e) {
            Log.e(TAG, "Unzip Error", e);
        }

    }

    /**
     * Vérifie si un dossier existe, le créer si besoin
     * @param dir
     */
    private void _dirChecker(String dir) {
        File f = new File(dir);
        Log.i(TAG, "creating dir " + dir);

        if(dir.length() >= 0 && !f.isDirectory() ) {
            f.mkdirs();
        }
    }
}