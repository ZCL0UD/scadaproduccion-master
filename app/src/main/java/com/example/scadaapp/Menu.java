package com.example.scadaapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Menu extends AppCompatActivity {


    CardView cardJugar;
    CardView cardAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cardJugar=(CardView) findViewById(R.id.cardJugar);
        cardAjustes=(CardView) findViewById(R.id.cardAjustes);


        cardJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //recibe los datos envaidos desde el INTENT
                String valor= getIntent().getStringExtra("codigoUsuario");


                Intent i = new Intent(Menu.this, Addincidente.class);
                i.putExtra("codigoUsuario",valor);
                startActivity(i);
            }
        });


        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Menu.this, GetIncidente.class);
                String valor= getIntent().getStringExtra("codigoUsuario");
                i.putExtra("codigoUsuario",valor);
                startActivity(i);
            }
        });



    }

    public void Addver(View view){
        Intent i = new Intent(Menu.this, Addincidente.class);
        startActivity(i);
    }



}