package com.example.epsilon.arduino;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Manual extends AppCompatActivity {

    FloatingActionButton blueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        listaDispActivity();
    }






    public void listaDispActivity(){
        blueBtn = (FloatingActionButton)findViewById(R.id.bluetoothButton);

        blueBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent next = new Intent(Manual.this,dispositivosBT.class);
                startActivity(next);
                Toast.makeText(getApplicationContext(), "Dispositivos Bluetooth", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
