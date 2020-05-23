package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturaventa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FacturaVentaAdapter extends RecyclerView.Adapter<FacturaVentaAdapter.FacturaVentaHolder>  {
    private OnItemClickListener listener;
    private List<Facturaventa> facturaventas = new ArrayList<>();

    DecimalFormat formateador = new DecimalFormat("###,###.##");


    public class FacturaVentaHolder extends RecyclerView.ViewHolder {
        //Se crean las variables
        private TextView tvNrofactura,tvFechaFactura,tvTipoFactura,tvRucClienteFactura,tvTotalFactura;
        public FacturaVentaHolder(@NonNull View itemView) {
            super(itemView);
            tvNrofactura = itemView.findViewById(R.id.tvnrofacturaventa);
            tvFechaFactura = itemView.findViewById(R.id.tvfechafacturaventa);
            tvTipoFactura = itemView.findViewById(R.id.tvtipofacturaventa);
            tvRucClienteFactura = itemView.findViewById(R.id.tvrucclientefacturaventa);
            tvTotalFactura = itemView.findViewById(R.id.tvtotalfacturaventa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(facturaventas.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FacturaVentaAdapter.FacturaVentaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facturaventa_item,parent,false);
        return new FacturaVentaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaVentaAdapter.FacturaVentaHolder holder, int position) {
        Facturaventa currentItem = facturaventas.get(position);
        holder.tvNrofactura.setText("Nro. Factura: "+String.valueOf(currentItem.getNrofacturaventa()));
        holder.tvFechaFactura.setText("Fecha: "+String.valueOf(currentItem.getDia_venta().trim()
                .concat("/").concat(currentItem.getMes_venta().trim()).concat("/")
                .concat(currentItem.getAnho_venta())));
        holder.tvTipoFactura.setText("Tipo: "+currentItem.tipoComprobante.getDescripciontipocomprobante());
        holder.tvRucClienteFactura.setText("Cliente: "+currentItem.cliente.getNombre());
        holder.tvTotalFactura.setText("Total: "+formateador.format(currentItem.getTotal_venta()));
    }

    @Override
    public int getItemCount() {
        return facturaventas.size();
    }

    //metodo para actualizar el ADAPTER
    public void setFacturaventas(List<Facturaventa> facturaventas){
        this.facturaventas = facturaventas;
        notifyDataSetChanged();
    }

    public Facturaventa getFacturaVentaAt(int position){
        return facturaventas.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        facturaventas.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,Facturaventa delete){
        facturaventas.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(Facturaventa facturaventa);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
