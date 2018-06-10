package com.example.epsilon.arduino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ModosAutomaticos extends AppCompatActivity {

    private RecyclerView recyclerViewCard;
    private RecyclerViewAdaptador adaptadorCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modos_automaticos);
        //Codigo para las cards del inicio
        recyclerViewCard = (RecyclerView) findViewById(R.id.listaAutomaticos);
        recyclerViewCard.setLayoutManager(new LinearLayoutManager(this));
        adaptadorCard = new RecyclerViewAdaptador(obtenerCards());
        recyclerViewCard.setAdapter(adaptadorCard);



    }

    //Codigo para las cards del inicio
    public List<CardModelo> obtenerCards(){
        List<CardModelo> card = new ArrayList<>();
        card.add(new CardModelo("Evitador de Obstaculos","Diviertete viendo al auto manejarse solo","Quiero verlo!",R.drawable.havac));
        card.add(new CardModelo("Seguidor de Luz","Pues eso, sigue la luz","Sigue la Luz!",R.drawable.battletrack));
        return  card;
    }
}
