package com.example.scadaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetIncidente extends AppCompatActivity {

    Conexion base= new Conexion();

    RecyclerView recyclerView;
    List<Lista> songs;
    String urlIncidentes= base.url+"incidente/idJefe/";
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_incidente);

        recyclerView = findViewById(R.id.songsList);
        songs = new ArrayList<>();
        extractSongs();

    }

    private void extractSongs() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String valor= getIntent().getStringExtra("codigoUsuario");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlIncidentes+valor, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        Lista inc= new Lista();

                        JSONObject obj= response.getJSONObject(i);


                        inc.setIdIncidente(Integer.parseInt(obj.get("idIncidente").toString()));
                        inc.setDescripcion(obj.getString("descripcion").toString());
                        inc.setFechaEvento(obj.getString("fechaEvento").toString());
                        inc.setFechaIngreso(obj.getString("fechaIngreso").toString());
                        inc.setIdJefe(Integer.parseInt(obj.get("idJefe").toString()));
                        inc.setCodigoIncidencia(obj.get("codigoIncidencia").toString());
                        inc.setIdResponsable(Integer.parseInt(obj.get("idResponsable").toString()));
                        inc.setNroPoste(obj.getString("nroPoste").toString());
                        //inc.setNombreAlimentador(obj.getJSONObject("scdAlimentadore").getString("nombreAlimentador"));



                        inc.setDescripcionEvento(obj.getJSONObject("scdEvento").getString("descripcionEvento"));
                        inc.setManiobra(obj.getString("maniobrasRealizadas").toString());

                        songs.add(inc);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),songs);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

}