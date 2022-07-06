package com.example.scadaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    RequestQueue queque;
    Conexion base= new Conexion();

     String url= base.url+"usuario";
    String urlog= base.url+"log";

    //List<String> datos= new ArrayList<String>();
    //ListView listDatos;




    TextView idUsuario;
    TextView clave;
    Button btn_submit;
    TextView boton;
    ProgressDialog progressDialog;


    LinearLayout ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        idUsuario = (TextView) findViewById(R.id.txtidUsuario);
        clave = (TextView) findViewById(R.id.txtclave);

        queque = Volley.newRequestQueue(this);
        //GetApidata();

        //btn_submit = (Button) findViewById(R.id.btnsubmit);
        boton=(TextView)findViewById(R.id.btnsubmit);

        boton.setOnClickListener(v -> {
            verificar();

        });

    }

    private void GetApidata(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj= response.getJSONObject(i);
                            Usuarios u= new Usuarios();
                            u.setIdUsuario(obj.get("idUsuario").toString());
                            u.setClave(obj.get("clave").toString());

                            TextView t=new TextView(getApplicationContext());
                            t.setId(i);
                            t.setText(u.getIdUsuario());
                            // t.setText(obj.get("idUsuario").toString());
                            ln.addView(t);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x=0;
            }
        });
        queque.add(request);

    }



    public void verificar(){
        Gson gson = new Gson();
        Usuarios u= new Usuarios();
        u.setIdUsuario(idUsuario.getText().toString());
        u.setClave(clave.getText().toString());
        String JSON = gson.toJson(u);

        if(idUsuario.getText().toString().isEmpty() || clave.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Completar los campos", Toast.LENGTH_LONG).show();

        }else{
            probar(JSON);
        }

    }

    public void probar( String json){

        //Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();

        final String savedata= json;

        queque = Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("ok")){
                    //Toast.makeText(MainActivity.this, "Iniciando sección", Toast.LENGTH_LONG).show();

                    Enviar_Log(idUsuario.getText().toString());

                    progressDialog= new ProgressDialog(MainActivity.this);

                    ObjectAnimator.ofInt(progressDialog,"progress",100).setDuration(2000).start();
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, Menu.class);
                            //enviar datos a incidentes

                            i.putExtra("codigoUsuario",idUsuario.getText().toString());

                            startActivity(i);
                            finish();
                        }
                    },2000);

                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

                    //Intent i = new Intent(MainActivity.this, Menu.class);
                    //startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "esta en eror", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }
        )
        {
            @Override
            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    return savedata==null ? null : savedata.getBytes("utf-8");
                }catch (UnsupportedEncodingException uee){
                    return null;
                }
            }
        };

        queque.add(stringRequest);
    }

    public void Enviar_Log(String idUsuario){
        Gson gson = new Gson();
        //SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyyHHmmss");
        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiManager wifiManager2=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);






        Log l= new Log(0,idUsuario,getMacAddr(),"Ingreso al sistema correcto", null, getLocalIpAddress());
        String JSON = gson.toJson(l);


        EnviarLog_Login(JSON);
    }
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {

        }
        return "02:00:00:00:00:00";
    }

    public void EnviarLog_Login( String json){

        //Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
        final String savedata= json;

        queque = Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, urlog, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("1")){

                    //Toast.makeText(MainActivity.this, "log enviado correctamente", Toast.LENGTH_LONG).show();

                }else{
                    //Toast.makeText(MainActivity.this, "log no enviado", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "esta en eror", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }
        )
        {
            @Override
            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    return savedata==null ? null : savedata.getBytes("utf-8");
                }catch (UnsupportedEncodingException uee){
                    return null;
                }
            }
        };

        queque.add(stringRequest);
    }


    public void ultima(String data){

        //Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();

        final String savedata= data;

        queque = Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("ok")){
                    //Toast.makeText(MainActivity.this, "Iniciando sección", Toast.LENGTH_LONG).show();
                    verificar();
                    progressDialog= new ProgressDialog(MainActivity.this);

                    ObjectAnimator.ofInt(progressDialog,"progress",100).setDuration(2000).start();
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, Menu.class);
                            startActivity(i);
                            finish();
                        }
                    },2000);

                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

                    //Intent i = new Intent(MainActivity.this, Menu.class);
                    //startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "esta en eror", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }
        )
        {
            @Override
            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    return savedata==null ? null : savedata.getBytes("utf-8");
                }catch (UnsupportedEncodingException uee){
                    return null;
                }
            }
        };

        queque.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        progressDialog.dismiss();
    }


}