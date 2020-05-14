package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.entity.TipoEgreso;

import java.util.ArrayList;
import java.util.List;


public class TipoEgresoAdapter extends RecyclerView.Adapter<TipoEgresoAdapter.TipoEgresoHolder> {

    private OnItemClickListener listener;
    private List<TipoEgreso> tipoEgresos = new ArrayList<>();

    //1) SE CREA LA CLASE Holder
    public class TipoEgresoHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvId,tvDescri;

        public TipoEgresoHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvidtipoegreso);
            tvDescri = itemView.findViewById(R.id.tvdescripciontipoegreso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(tipoEgresos.get(position));
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public TipoEgresoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipoegreso_item,parent,false);
        return new TipoEgresoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoEgresoHolder holder, int position) {
        TipoEgreso currentItem = tipoEgresos.get(position);
        holder.tvId.setText("Id: "+String.valueOf(currentItem.getIdtipoegreso()));
        holder.tvDescri.setText(currentItem.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return tipoEgresos.size();
    }

    //metodo para actualizar el ADAPTER
    public void setTipoegresos(List<TipoEgreso> tipoegresos){
        this.tipoEgresos = tipoegresos;
        notifyDataSetChanged();
    }

    public TipoEgreso getTipoEgresoAt(int position){
        return tipoEgresos.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        tipoEgresos.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,TipoEgreso delete){
        tipoEgresos.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(TipoEgreso tipoEgreso);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
