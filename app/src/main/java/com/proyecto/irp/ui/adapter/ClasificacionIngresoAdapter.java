package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.Cliente;

import java.util.ArrayList;
import java.util.List;


public class ClasificacionIngresoAdapter extends RecyclerView.Adapter<ClasificacionIngresoAdapter.ClasificacionIngresoHolder> {

    private OnItemClickListener listener;
    private List<ClasificacionIngreso> clasficacionIngreso = new ArrayList<>();

    //1) SE CREA LA CLASE Holder
    public class ClasificacionIngresoHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvIdclasingreso,tvDescriClasIngreso;

        public ClasificacionIngresoHolder(@NonNull View itemView) {
            super(itemView);
            tvIdclasingreso = itemView.findViewById(R.id.tvidclasingreso);
            tvDescriClasIngreso = itemView.findViewById(R.id.tvdescripcionclasingreso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(clasficacionIngreso.get(position));
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public ClasificacionIngresoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clasingreso_item,parent,false);
        return new ClasificacionIngresoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClasificacionIngresoHolder holder, int position) {
        ClasificacionIngreso currentItem = clasficacionIngreso.get(position);
        holder.tvIdclasingreso.setText("Id: "+String.valueOf(currentItem.getIdclasificacioningreso()));
        holder.tvDescriClasIngreso.setText(currentItem.getDescripcionclasificacioningreso());
    }

    @Override
    public int getItemCount() {
        return clasficacionIngreso.size();
    }

    //metodo para actualizar el ADAPTER
    public void setClasficacionIngreso(List<ClasificacionIngreso> clasficacionIngreso){
        this.clasficacionIngreso = clasficacionIngreso;
        notifyDataSetChanged();
    }

    public ClasificacionIngreso getClasificacionIngresoAt(int position){
        //return contribuyentes.get(position);
        return clasficacionIngreso.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        clasficacionIngreso.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,ClasificacionIngreso delete){
        clasficacionIngreso.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(ClasificacionIngreso clasificacionIngreso);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
