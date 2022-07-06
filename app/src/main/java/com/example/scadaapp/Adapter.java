package com.example.scadaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
    implements View.OnClickListener
{
    LayoutInflater inflater;
    List<Lista> songs;
    private View.OnClickListener listener;

    public Adapter(Context ctx, List<Lista> songs){
        this.inflater = LayoutInflater.from(ctx);
        this.songs = songs;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data

        holder.descripcion.setText("Descripción: "+songs.get(position).getDescripcion());
        holder.codigoIncidencia.setText("Código: "+songs.get(position).getCodigoIncidencia());
        //holder.descripcion.setText(songs.get(position).getDescripcion());
        holder.fecha.setText("Fecha de ingreso: "+songs.get(position).getFechaIngreso());
        holder.evento.setText("Evento: "+songs.get(position).getDescripcionEvento());
        holder.nroposte.setText("Poste: "+songs.get(position).getNroPoste());
        holder.maniobra.setText("Maniobra: "+songs.get(position).getManiobra());


        //Picasso.get().load(songs.get(position).getCoverImage()).into(holder.songCoverImage);

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if(listener!=null){
            listener.onClick(view);
        }
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView songTitle,songArtists;
        TextView descripcion, nroposte, evento,fecha, maniobra , codigoIncidencia;
        ImageView songCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            descripcion= itemView.findViewById(R.id.descripcionIncidente);
            nroposte= itemView.findViewById(R.id.nroPosteInc);
            evento= itemView.findViewById(R.id.eventoInc);
            fecha= itemView.findViewById(R.id.fechaInc);
            maniobra= itemView.findViewById(R.id.maniobrasget);
            codigoIncidencia = itemView.findViewById(R.id.codigoInc);


            songCoverImage = itemView.findViewById(R.id.coverImage);

            // handle onClick

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
