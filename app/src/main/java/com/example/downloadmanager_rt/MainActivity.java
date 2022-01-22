package com.example.downloadmanager_rt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.downloadmanager_rt.Adaptador.AdaptadorArchivo;
import com.example.downloadmanager_rt.Modelo.Archivo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long downloadId;
    ArrayList<Archivo> listar;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    AdaptadorArchivo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerArchivos();
    }

    private void obtenerArchivos(){
        String url = "https://raw.githubusercontent.com/cturriagos/DownloadManager_RT/master/db.json";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url , null ,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int size = response.length();
                            JSONObject jsonob;
                            if (size > 0) {
                                Archivo ar;
                                listar = new ArrayList<>();
                                for (int i = 0; i < size; i++) {
                                    jsonob = response.getJSONObject(i);
                                    ar = new Archivo(jsonob.getString("fichero"),
                                                     jsonob.getString("fecha"),
                                                     jsonob.getString("ruta"));
                                    listar.add(ar);
                                }
                                llenarLista();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }
    }

    public void llenarLista (){
        adapter = new AdaptadorArchivo(MainActivity.this, listar);
        recyclerView = (RecyclerView) findViewById(R.id.listado);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}