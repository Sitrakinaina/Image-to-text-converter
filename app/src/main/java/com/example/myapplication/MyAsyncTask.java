package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyAsyncTask extends AsyncTask<Void,Void,Void> {


    private OkHttpClient client;
//    public TextView textView;
    public MyAsyncTask(OkHttpClient client ) {
        this.client = new OkHttpClient();

    }
    @Override
    protected Void doInBackground(Void... voids) {
        Request request=new Request.Builder().url("http://10.10.11.29:5000").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("MISY OLANA "+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    System.out.println("ty le data"+jsonData);
//                    textView.setText(jsonData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
//        try {
//            // Remplacez "YOUR_SERVER_IP" par l'adresse IP de votre serveur Flask
//            URL url = new URL("http://10.10.11.29:5000");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//            Log.d("HTTP Response Code", String.valueOf(responseCode));
//
//            // Gérer la réponse ici...
//
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
