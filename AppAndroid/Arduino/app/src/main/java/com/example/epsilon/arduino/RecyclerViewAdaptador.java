package com.example.epsilon.arduino;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titulo,descripcion;
        Button boton;
        ImageView fotoAuto;
        Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            titulo = (TextView)itemView.findViewById(R.id.tituloCard);
            descripcion = (TextView)itemView.findViewById(R.id.descripcionCard);
            boton = (Button)itemView.findViewById(R.id.botonCard);
            fotoAuto = (ImageView)itemView.findViewById(R.id.imgAuto);
        }
       void setOnClickListeners (){
            boton.setOnClickListener(this);
       }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.botonCard:
                    if(titulo.getText() == "Manual") {
                        Intent intent = new Intent(context, dispositivosBT.class);
                        context.startActivity(intent);
                        break;
                    }if(titulo.getText() == "Automatico"){
                    Intent intent = new Intent(context, ModosAutomaticos.class);
                    context.startActivity(intent);
                    break;
                }if(titulo.getText() == "Evitador de Obstaculos"){
                    Intent intent = new Intent(context, dispositivosBT2.class);
                    context.startActivity(intent);
                    break;
                }if(titulo.getText() == "Seguidor de Luz"){
                    Intent intent = new Intent(context, dispositivosBT3.class);
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

    public List<CardModelo> cardLista;

    public RecyclerViewAdaptador(List<CardModelo> cardLista) {
        this.cardLista = cardLista;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titulo.setText(cardLista.get(position).getTitulo());
        holder.descripcion.setText(cardLista.get(position).getDescripcion());
        holder.boton.setText(cardLista.get(position).getBoton());
        holder.fotoAuto.setImageResource(cardLista.get(position).getFotoAuto());

        holder.setOnClickListeners();

    }


    @Override
    public int getItemCount() {
        return cardLista.size();
    }
}
