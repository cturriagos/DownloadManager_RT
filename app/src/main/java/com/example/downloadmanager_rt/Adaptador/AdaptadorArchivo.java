package com.example.downloadmanager_rt.Adaptador;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.downloadmanager_rt.Modelo.Archivo;
import com.example.downloadmanager_rt.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorArchivo extends RecyclerView.Adapter<AdaptadorArchivo.ViewHolder> {

    List<Archivo> archivos;
    LayoutInflater layout;
    Context context;

    public AdaptadorArchivo(Context context, ArrayList<Archivo> datos) {
        archivos = datos;
        layout = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return archivos.size();
    }

    @NonNull
    @Override
    public AdaptadorArchivo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layout.inflate(R.layout.adapter, null);
        AdaptadorArchivo.ViewHolder vh = new AdaptadorArchivo.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorArchivo.ViewHolder holder, int position) {
        holder.bindData(archivos.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtfichero;
        TextView txtfecha;
        ImageButton btnPdf;

        ViewHolder(View view) {
            super(view);
            txtfichero  = view.findViewById(R.id.txtfichero);
            txtfecha = view.findViewById(R.id.txtfecha);
            btnPdf = view.findViewById(R.id.btnPdf);
        }

        void bindData(final Archivo archivo) {
            txtfichero.setText(archivo.getFichero());
            txtfecha.setText(archivo.getFecha());
            btnPdf.setImageURI(Uri.parse(archivo.getRuta()));
        }
    }
}
