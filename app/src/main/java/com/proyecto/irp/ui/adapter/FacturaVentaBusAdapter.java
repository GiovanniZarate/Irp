package com.proyecto.irp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturaventa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FacturaVentaBusAdapter extends RecyclerView.Adapter<FacturaVentaBusAdapter.FacturaVentaHolder> implements Filterable {
    private OnItemClickListener listener;
    private List<Facturaventa> facturaventas = new ArrayList<>();
    //private List<Facturaventa> facturaventas = new ArrayList<>();

    private List<Facturaventa> facturaventasAll = new ArrayList<>();

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
    public FacturaVentaBusAdapter.FacturaVentaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facturaventa_item,parent,false);
        return new FacturaVentaHolder(itemView);
    }

    /*public FacturaVentaBusAdapter(List<Facturaventa> facturaventasbusq) {
        facturaventasbus = new ArrayList<>(facturaventasbusq);
    }*/

    @Override
    public void onBindViewHolder(@NonNull FacturaVentaBusAdapter.FacturaVentaHolder holder, int position) {
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
        this.facturaventasAll = new ArrayList<>(facturaventas);
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


    //PARA FILTRO IMPLEMEDNTADO


    @Override
    public Filter getFilter() {
        return facturaFilter;
    }

    private Filter facturaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Facturaventa> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                //Log.e("filtro ","VACIO  "+constraint + "facturaventabus "+facturaventasAll);
                filteredList.addAll(facturaventasAll);
            }else {
                String filtrovalor = constraint.toString().toLowerCase().trim();
                Log.e("filtro ","valor filtro "+String.valueOf(filtrovalor));

                for (Facturaventa item : facturaventasAll){
                    //Log.e("filtro ","valor filtro dentro "+String.valueOf(item.getNrofacturaventa()));
                    if (item.getNrofacturaventa().toLowerCase().contains(filtrovalor)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            facturaventas.clear();
            //facturaventas.addAll((Collection<? extends Facturaventa>) results.values);
            facturaventas.addAll((List) results.values);
            //Log.e("filtro ","facturaventas"+facturaventas);
            notifyDataSetChanged();
        }
    };
}
