package com.proyecto.irp.ui.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.proyecto.irp.viewmodel.NuevoEJercicioDialogViewModel;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Ejercicio;

import java.util.List;

public class MyEjercicioRecyclerViewAdapter extends RecyclerView.Adapter<MyEjercicioRecyclerViewAdapter.ViewHolder> {

    //private final List<Ejercicio> mValues;  //NO VA A SER final xq su valor va a cambiar
    private  List<Ejercicio> mValues;
    private Context ctx;
    //private final EjercicioInteractionListener mListener;

    //modificar
    private NuevoEJercicioDialogViewModel viewModel;


    public MyEjercicioRecyclerViewAdapter(List<Ejercicio> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
        //modificar (instancia del activity)
        viewModel = ViewModelProviders.of((AppCompatActivity) ctx).get(NuevoEJercicioDialogViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ejercicio, parent, false);
        return new ViewHolder(view);
    }


    //METODO QUE ES INVOCADO UNA VEZ POR CADA ELEMENTO DE NUESTRA LISTA DE NOTAS
    //RECIBE LA POSICION POR CADA ITEM
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvIdejercicio.setText(String.valueOf(holder.mItem.getIdejercicio()));
        holder.tvAnho.setText(String.valueOf(holder.mItem.getAnho()));

        holder.tvAnho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PARA ACTUALIZAR DE FORMA DIRECTA
                //viewModel.actualizarEjercicio(holder.mItem);
                Toast.makeText(ctx,"Hola", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //metodo para actualizar el ADAPTER
    public void setNuevoEjercicio(List<Ejercicio> nuevoEjercicio){
        this.mValues = nuevoEjercicio;
        notifyDataSetChanged(); //actualiza el adaptador
    }

    //EL ViewHolde hace referencia a todos los componentes de la parte visual ej: TextView, Button, etc.

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvIdejercicio;
        public final TextView tvAnho;
        public Ejercicio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvIdejercicio =  view.findViewById(R.id.tvIdEjercicio);
            tvAnho = view.findViewById(R.id.tvAnho);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvAnho.getText() + "'";
        }
    }
}
