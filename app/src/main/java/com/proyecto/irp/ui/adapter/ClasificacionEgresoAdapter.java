package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.dao.ClasificacionEgresoDao;
import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.TipoEgreso;

import java.util.ArrayList;
import java.util.List;


public class ClasificacionEgresoAdapter extends RecyclerView.Adapter<ClasificacionEgresoAdapter.ClasificacionEgresoHolder> {

    private OnItemClickListener listener;
    private List<ClasificacionEgreso> clasificacionEgresos = new ArrayList<>();


    //1) SE CREA LA CLASE Holder
    public class ClasificacionEgresoHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvIdclasegreso,tvDescriEgreso,tvDescriClasegreso;

        public ClasificacionEgresoHolder(@NonNull View itemView) {
            super(itemView);
            tvIdclasegreso = itemView.findViewById(R.id.tvidclasegreso);
            tvDescriEgreso = itemView.findViewById(R.id.tvdescripcionegreso);
            tvDescriClasegreso = itemView.findViewById(R.id.tvdescriclasegreso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(clasificacionEgresos.get(position));
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public ClasificacionEgresoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clasegreso_item,parent,false);
        return new ClasificacionEgresoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClasificacionEgresoHolder holder, int position) {
        ClasificacionEgreso currentItem = clasificacionEgresos.get(position);
        //TipoEgreso tegreso = new TipoEgreso("");
        holder.tvIdclasegreso.setText("Id: "+String.valueOf(currentItem.getIdclasificacionegreso()));
        holder.tvDescriEgreso.setText(currentItem.tipoEgreso.getDescripciontipoegreso());
        //holder.tvDescriEgreso.setText(String.valueOf(currentItem.getCodtipoegreso()));
        holder.tvDescriClasegreso.setText(currentItem.getDescripcion());
        //book.getAuthor().getName()


    }

    @Override
    public int getItemCount() {
        return clasificacionEgresos.size();
    }

    //metodo para actualizar el ADAPTER
    public void setClasficacionEgreso(List<ClasificacionEgreso> clasficacionEgreso){
        this.clasificacionEgresos = clasficacionEgreso;
        notifyDataSetChanged();
    }

    public ClasificacionEgreso getClasificacionEgresoAt(int position){
        return clasificacionEgresos.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        clasificacionEgresos.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,ClasificacionEgreso delete){
        clasificacionEgresos.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(ClasificacionEgreso clasificacionEgreso);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
