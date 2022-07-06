package com.example.scadaapp;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class Addincidente extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;

    int PICK_IMAGE = 100;
    RequestQueue queque;
    Conexion base= new Conexion();
    String urlUsuarios= base.url+"usuario";
    String urlEventos= base.url+"evento";
    String urlAlimentador= base.url+"alimentadores/idSubestacion/";
    String urlSubestacion= base.url+"subestaciones";
    String urlIncidentes= base.url+"incidente";
    String urlZonas= base.url+"zona";
    String urlog= base.url+"log";
    String urlSubestacionZona= base.url+"subestacionZona/idZona/";

    List<String> datos= new ArrayList<String>();

    /// Subir Imagenes---- base 64----
    GridView gvImagenes;
    Uri imageUri;
    List<Uri> listaImagenes = new ArrayList<>();
    List<String> listaBase64Imagenes = new ArrayList<>();


    GridViewAdapter baseAdapter;
    ProgressDialog progressDialog;
//-------------------------------

    ///----------CLASES----------
    Alimentadores clase_alimentadores;
    Eventos clase_eventos;
    ArrayList<ScdAnexo> listaImagen= new ArrayList<>();
    ScdAnexo clase_imagen;


    //------------------------
    ImageView iamgen;
    EditText resul;
    EditText fechaEnvio;
    //TextInputLayout Descripcion;
    EditText Descripcion;
    EditText NroPoste;
    //TextInputLayout maniobras;
    EditText maniobras;
    EditText id;

    Spinner spiner;
    Spinner spinnerAlim;
    Spinner spinnerEventos;
    Spinner spinnerAuth;
    Spinner spinnerZonas;
    ArrayList<Subestaciones> subestaciones = new ArrayList<>();
    ArrayList<Alimentadores> alimentadores = new ArrayList<>();
    ArrayList<Eventos> eventos = new ArrayList<>();
    ArrayList<Zonas> zonas = new ArrayList<>();
    ArrayList<Usuarios> usuarios = new ArrayList<>();

    private Dialog dialog;
    Button Okay;
    Button Cancel;

    private boolean first=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addincidente);

        queque= Volley.newRequestQueue(this);
        spiner= (Spinner)findViewById(R.id.subestaciones);
        spinnerAlim=(Spinner)findViewById(R.id.spinnerAlim);
        gvImagenes = findViewById(R.id.gridImagen);
        //resul= findViewById(R.id.txtres);
        spinnerEventos=(Spinner)findViewById(R.id.spinnerEvento);
        spinnerAuth=(Spinner)findViewById(R.id.spinnerAuth);
        spinnerZonas= (Spinner) findViewById(R.id.spinnerzona);
        fechaEnvio=(EditText) findViewById(R.id.txtFechaEnvio);
        Descripcion=(EditText) findViewById(R.id.txtDescripcion);
        NroPoste=(EditText)findViewById(R.id.txtnroPoste);
        maniobras= (EditText) findViewById(R.id.txtManiobras);


        //llamado a los servicios
        //GetApidata(); subestaciones antigua tabla
        GetEventos();
        GetUsuarios();
        FechaActual();
        GetIncidentes();
        GetZonas();
        //SeleccionSpinner();

////////////----------------INSTANCIA DIALOG ALERT----------------------

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //dialog.getWindow().setLayout(ViewGroup., ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Okay = dialog.findViewById(R.id.btn_okay);
        Cancel = dialog.findViewById(R.id.btn_cancel);




////////-----------------------------------------------------

        fechaEnvio.setFocusable(false);
        //fechaEnvio.setEnabled(false);
        //fechaEnvio.setCursorVisible(false);
        //fechaEnvio.setKeyListener(null);

        Calendar calendar= Calendar.getInstance();
        final int year= calendar.get(Calendar.YEAR);
        final int month= calendar.get(Calendar.MONTH);
        final int day= calendar.get(Calendar.DAY_OF_MONTH);

        fechaEnvio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        Addincidente.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date="";
                        String mesformat="";
                        String diaformat="";

                        if(month<10 || day<10){

                            if(month<10){
                                mesformat="0"+month;
                                //date= day+"/"+mesformat+"/"+year;
                                date=year+"-"+mesformat+"-"+day;
                            }
                            if(day<10 && month<10){
                                diaformat="0"+day;
                                date=year+"-"+mesformat+"-"+diaformat;
                            }
                            if(day<10 && month>9){
                                diaformat="0"+day;
                                date=year+"-"+month+"-"+diaformat;
                            }

                        }else{
                            //date= day+"/"+month+"/"+year;
                            date=year+"-"+month+"-"+day;
                        }

                        fechaEnvio.setText(date);
                    }
                },year,month,day);
                ///agregar fecha maxima-- actual----------------------
                Calendar calendarioMin = Calendar.getInstance();
                calendarioMin.add(Calendar.MONTH,0); //Mes anterior
                calendarioMin.add(Calendar.DAY_OF_MONTH, 0); //dia anterior
                //defines que el día que deseas
                datePickerDialog.getDatePicker().setMaxDate(calendarioMin.getTimeInMillis() - 1000);
                calendarioMin.getTimeInMillis();
                //---------------------------------------
                //no borrar
                datePickerDialog.show();

            }
        });



        spinnerZonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
              String res = zonas.get(position).getIdZona().toString();
                //Toast.makeText(Addincidente.this, "pasa el "+res, Toast.LENGTH_LONG).show();
                GetSubestacionesZona(res);
                subestaciones.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String res = subestaciones.get(position).getIdSubestacion();
                //Toast.makeText(Addincidente.this, "pasa el "+res, Toast.LENGTH_LONG).show();
                GetAlimentadores(res);
                alimentadores.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }


    private void GetIncidentes(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlIncidentes, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj= response.getJSONObject(i);
                            //para las imagenes
                            //String valor = response.get("ss").toString();
                            JSONArray arreglo= response.getJSONArray(i);

                            JSONObject obj2= new JSONObject(arreglo.get(0).toString());

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



    public void verificarJson(View view){

        subirImagenes();

        if(listaImagen.size()>0) {
        Subestaciones sub= (Subestaciones) spiner.getSelectedItem();
        Alimentadores al=(Alimentadores)spinnerAlim.getSelectedItem();
        Usuarios u= (Usuarios)spinnerAuth.getSelectedItem();
        Eventos ev=(Eventos)spinnerEventos.getSelectedItem();
        MainActivity m = new MainActivity();
        String codAlimentador= alimentadores.get(spinnerAlim.getSelectedItemPosition()).getCodigoAlimentador();
        String nameAlimentador= alimentadores.get(spinnerAlim.getSelectedItemPosition()).getNombreAlimentador();
        Integer codEmpeladoZona= Integer.parseInt(subestaciones.get(spiner.getSelectedItemPosition()).getCodigoEmpleado());



        Gson gson = new Gson();


        Alimentadores alimentadores= new Alimentadores(al.getCodigoAlimentador(),al.getNombreAlimentador());

        Eventos eventos= new Eventos(ev.getIdEvento(),ev.getDescripcion());


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Integer idjefe= Integer.parseInt(getIntent().getStringExtra("codigoUsuario"));

        dialog.show();

            Okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Descripcion.getText().toString().isEmpty() || NroPoste.getText().toString().isEmpty() ||
                            fechaEnvio.getText().toString().isEmpty()) {

                        Toast.makeText(Addincidente.this, "Complete todos los campos !", Toast.LENGTH_LONG).show();

                    } else {
                        Incidente incidente = new Incidente(codAlimentador, Descripcion.getText().toString(), fechaEnvio.getText().toString(), dateFormat.format(date),
                                idjefe, Integer.parseInt(u.getIdUsuario().toString()),
                                NroPoste.getText().toString(), maniobras.getText().toString(), codEmpeladoZona, listaImagen, eventos);

                        String JSON = gson.toJson(incidente);
                        PostIncidente(JSON);
                    }
                    dialog.dismiss();
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(Addincidente.this, " Petición cancelada!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        }else{
            Toast.makeText(Addincidente.this, " Sin imagénes, agregar imagénes!", Toast.LENGTH_SHORT).show();

        }




        //return JSON;
    }


    public void Enviar_Log(String idUsuario){
        Gson gson = new Gson();
        //SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyyHHmmss");
        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiManager wifiManager2=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);






        com.example.scadaapp.Log l= new Log(0,idUsuario,getMacAddr(),"Ingreso de incidente correcto", null, getLocalIpAddress());
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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

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

    public void PostIncidente(String Json){



        final String savedata= Json;

        queque = Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, urlIncidentes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                  //if(response.equalsIgnoreCase("1")){
               Enviar_Log(getIntent().getStringExtra("codigoUsuario"));

                    progressDialog= new ProgressDialog(Addincidente.this);

                ObjectAnimator.ofInt(progressDialog,"progress",100).setDuration(2000).start();
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                },2000);


                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Toast.makeText(Addincidente.this, "Incidente enviado!", Toast.LENGTH_LONG).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, "esta en eror", Toast.LENGTH_LONG).show();
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

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


    public void SeleccionSpinner(){
        Subestaciones sub= (Subestaciones) spiner.getSelectedItem();
        String res= "el id seleccionado es:"+sub.getIdSubestacion();
        Toast.makeText(this, res, Toast.LENGTH_LONG).show();


    }

    public void FechaActual(){
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    }


    public void Agregar(View view){

        String sub= spiner.getSelectedItem().toString();
        String alim= spinnerAlim.getSelectedItem().toString();
        Toast.makeText(this, sub+"--"+alim, Toast.LENGTH_LONG).show();
        subirImagenes();


    }


/*
    private void GetApidata(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlSubestacion, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            subestaciones.add(new Subestaciones(obj.get("numerosubestacion").toString(), obj.get("comentarios").toString()));

                            ArrayAdapter<Subestaciones> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.support_simple_spinner_dropdown_item,subestaciones);
                            spiner.setAdapter(adapter);



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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queque.add(request);
    }
*/


    private void GetUsuarios(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlUsuarios, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj= response.getJSONObject(i);
                            usuarios.add(new Usuarios(obj.get("idUsuario").toString(), obj.get("clave").toString(),
                                    obj.get("nombres").toString() ,obj.get("apellidos").toString()));


                            ArrayAdapter<Usuarios> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.support_simple_spinner_dropdown_item,usuarios);
                            spinnerAuth.setAdapter(adapter);

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


    private void GetEventos(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlEventos, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            eventos.add(new Eventos(Integer.parseInt(obj.get("idEvento").toString()), obj.get("descripcionEvento").toString()));

                            ArrayAdapter<Eventos> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.multiline_spinner_dropdown_item,eventos);
                            spinnerEventos.setAdapter(adapter);



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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queque.add(request);
    }



    private void GetZonas(){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlZonas, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            zonas.add(new Zonas(obj.get("descripcion").toString(), obj.get("estado").toString(), Integer.parseInt(obj.get("idZona").toString())));
                            ArrayAdapter<Zonas> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.multiline_spinner_dropdown_item,zonas);
                            spinnerZonas.setAdapter(adapter);

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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queque.add(request);
    }

    private void GetSubestacionesZona(String id){

        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlSubestacionZona+id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            subestaciones.add((new Subestaciones(obj.get("numerosubestacion").toString(),
                                    obj.get("nombreSubestacion").toString(), obj.get("codigoEmpleado").toString())));



                            ArrayAdapter<Subestaciones> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.support_simple_spinner_dropdown_item,subestaciones);
                            spiner.setAdapter(adapter);

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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queque.add(request);
    }




    private void GetAlimentadores(String id){
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urlAlimentador+id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()>0){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            alimentadores.add(new Alimentadores(obj.get("codigoalimentador").toString(),
                                    obj.get("zonainfluencia").toString()));

                            ArrayAdapter<Alimentadores> adapter= new ArrayAdapter<>(Addincidente.this,R.layout.support_simple_spinner_dropdown_item,alimentadores);
                            spinnerAlim.setAdapter(adapter);

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
                Toast.makeText(Addincidente.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        queque.add(request);
    }



    public void SeleccionSubestacion(View view){
        ArrayList<Subestaciones> subestaciones = new ArrayList<>();



        ArrayAdapter<Subestaciones> adapter= new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,subestaciones);
        spiner.setAdapter(adapter);
    }

    public void Galeria(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    public void openGallery(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);


        intent.setAction(Intent.ACTION_PICK);

        startActivityForResult(Intent.createChooser(intent, "SELECCIONA LAS IMAGENES"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            if(clipData == null) {
                imageUri = data.getData();
                listaImagenes.add(imageUri);
            } else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    listaImagenes.add(clipData.getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(Addincidente.this, listaImagenes);

        gvImagenes.setAdapter(baseAdapter);

    }




    public String convertirUriToBase64(Bitmap bitmap) {

        //Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 16, baos);
        byte[] byteArray = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


        return imageEncoded;
    }



    public void subirImagenes() {

        listaBase64Imagenes.clear();
        listaImagen.clear();
        //String res="";

        int max=0;
        if(listaImagenes.size()>6){
            max=6;
        }else{
            max=listaImagenes.size();
        }


        for(int i = 0 ; i < max ; i++) {
            try {
                InputStream is = getContentResolver().openInputStream(listaImagenes.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                // (System.out.println("bitmap..-."+bitmap.ge);
                String cadena = convertirUriToBase64(bitmap);
                ScdAnexo imagen= new ScdAnexo(0,cadena);
                listaImagen.add(imagen);
                //enviarImagenes("nomIma"+i, cadena);
                //res+=cadena+"--------";
                bitmap.recycle();

            } catch (IOException e) { }

        }
        // resul.setText(res);
        // Toast.makeText(Incidentes.this, res, Toast.LENGTH_LONG).show();

    }

}