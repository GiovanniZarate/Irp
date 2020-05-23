package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.TipoComprobante;

import java.util.ArrayList;
import java.util.List;


public class TipoComprobanteAdapter extends RecyclerView.Adapter<TipoComprobanteAdapter.TipoComprabanteHolder> {

    private OnItemClickListener listener;
    private List<TipoComprobante> tipoComprobantes = new ArrayList<>();

    //1) SE CREA LA CLASE Holder
    public class TipoComprabanteHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvId,tvDescri;

        public TipoComprabanteHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvidtipocomprobante);
            tvDescri = itemView.findViewById(R.id.tvdescripciontipocomprobante);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(tipoComprobantes.get(position));
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public TipoComprabanteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipocomprobante_item,parent,false);
        return new TipoComprabanteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoComprabanteHolder holder, int position) {
        TipoComprobante currentItem = tipoComprobantes.get(position);
        holder.tvId.setText("Id: "+String.valueOf(currentItem.getIdtipocomprobante()));
        holder.tvDescri.setText(currentItem.getDescripciontipocomprobante());
    }

    @Override
    public int getItemCount() {
        return tipoComprobantes.size();
    }

    //metodo para actualizar el ADAPTER
    public void setTipocomprobante(List<TipoComprobante> tipocomprobante){
        this.tipoComprobantes = tipocomprobante;
        notifyDataSetChanged();
    }

    public TipoComprobante getTipoComprobanteAt(int position){
        return tipoComprobantes.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        tipoComprobantes.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,TipoComprobante delete){
        tipoComprobantes.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(TipoComprobante tipoComprobante);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
