package com.example.downloadmanager_rt.Adaptador;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
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

import java.io.File;
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
            txtfichero = view.findViewById(R.id.txtfichero);
            txtfecha = view.findViewById(R.id.txtfecha);
            btnPdf = view.findViewById(R.id.btnPdf);
        }

        void bindData(final Archivo archivo) {
            txtfichero.setText(archivo.getFichero());
            txtfecha.setText(archivo.getFecha());
            btnPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(context.getExternalFilesDir(null), "Dummy");

                    DownloadManager.Request request = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        request = new DownloadManager.Request(Uri.parse(archivo.getRuta()))
                                .setTitle("PRUEBA")
                                .setDescription("Downloading")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setDestinationUri(Uri.fromFile(file))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true);
                    } else {
                        request = new DownloadManager.Request(Uri.parse(archivo.getRuta()))
                                .setTitle("PRUEBA")
                                .setDescription("Downloading")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                                .setDestinationUri(Uri.fromFile(file))
                                .setAllowedOverRoaming(true);
                    }

                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            });
        }
    }
}
