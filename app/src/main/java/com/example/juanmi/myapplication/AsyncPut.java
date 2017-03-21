package com.example.juanmi.myapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Juanmi on 14/03/2017.
 */

public class AsyncPut extends AsyncTask<Bitmap,Void,Boolean>{

    @Override
    protected Boolean doInBackground(Bitmap... bm) {
        boolean correcto = true;
        //Conversión del Bitmap a String (Base64) para poder integrarlo en JSON:
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm[0].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String bmEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //Realización del PUT http con el snap codificado:
        try {
            URL url = new URL("http://192.168.1.29:5050");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            //Se crea el JSON:
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("snap","bmEncoded");
            //Se manda:
            out.write(jsonObject.toString());
            out.close();
        }catch (Exception e){
            correcto = false;
        }
        return correcto;
    }
}
