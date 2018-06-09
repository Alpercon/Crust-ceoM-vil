package com.example.epsilon.arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Codigo para las cards del inicio
    private RecyclerView recyclerViewCard;
    private RecyclerViewAdaptador adaptadorCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Codigo para las cards del inicio
        recyclerViewCard = (RecyclerView) findViewById(R.id.recyclerMode);
        recyclerViewCard.setLayoutManager(new LinearLayoutManager(this));
        adaptadorCard = new RecyclerViewAdaptador(obtenerCards());
        recyclerViewCard.setAdapter(adaptadorCard);

    }


    //Codigo para las cards del inicio
    public List<CardModelo> obtenerCards(){
        List<CardModelo> card = new ArrayList<>();
        card.add(new CardModelo("Manual","Controla tu mismo este poderoso auto!","Quiero manejarlo ya!",R.drawable.auto));
        card.add(new CardModelo("Automatico","Cansado de conducir? Prueba los moddos automaticos","Llevame a ellos!",R.drawable.auto2));
        return  card;
    }




}
