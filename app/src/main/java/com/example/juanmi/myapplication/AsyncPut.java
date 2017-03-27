package com.example.juanmi.myapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Juanmi on 14/03/2017.
 */

public class AsyncPut extends AsyncTask<Bitmap,Void,Boolean>{

    @Override
    protected Boolean doInBackground(Bitmap... bm) {
        boolean correcto = true;

        //Conversión del Bitmap a String (Base64) para poder integrarlo en JSON.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm[0].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String bmEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        //Se crea el JSON.
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("snap",bmEncoded);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Realización del POST http con el snap codificado.

        //Primero se indica la url, es decir, direccion ip y puerto del servidor en escucha.
        URL url = null;
        try {
            url = new URL("http://192.168.1.14:5050");
            //URL url = new URL("http://172.19.209.241:5050");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Se crea el objeto principal de la conexion y se abre conexion a la dirección servidora.
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setDoOutput(true);
        /*
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        */

        //Se establece el tamaño, el cual no es fijo por si acaso.
        conn.setChunkedStreamingMode(jsonObject.toString().length());

        //Se manda.
        try {

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(jsonObject.toString());
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        } finally {

            //Desconectamos.

            if(conn!=null)
                conn.disconnect();
        }
        return correcto;
    }
}
