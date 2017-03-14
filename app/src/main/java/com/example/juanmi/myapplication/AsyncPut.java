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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm[0].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String bmEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        try {
            URL url = new URL("http://www.spycam.com/snap");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("snap","bmEncoded");
            out.write(jsonObject.toString());
            out.close();
        }catch (Exception e){
            correcto = false;
        }
        return correcto;
    }
}
