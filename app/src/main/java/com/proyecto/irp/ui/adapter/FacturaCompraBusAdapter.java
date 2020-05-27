package com.proyecto.irp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FacturaCompraBusAdapter extends RecyclerView.Adapter<FacturaCompraBusAdapter.FacturaCompraHolder> implements Filterable {
    private OnItemClickListener listener;
    private List<Facturacompra> facturacompras = new ArrayList<>();
    //private List<Facturaventa> facturaventas = new ArrayList<>();

    private List<Facturacompra> facturacomprasAll = new ArrayList<>();

    DecimalFormat formateador = new DecimalFormat("###,###.##");


    public class FacturaCompraHolder extends RecyclerView.ViewHolder {
        //Se crean las variables
        private TextView tvNrofactura,tvFechaFactura,tvTipoFactura,tvRucClienteFactura,tvTotalFactura;
        public FacturaCompraHolder(@NonNull View itemView) {
            super(itemView);
            tvNrofactura = itemView.findViewById(R.id.tvnrofacturacompra);
            tvFechaFactura = itemView.findViewById(R.id.tvfechafacturacompra);
            tvTipoFactura = itemView.findViewById(R.id.tvtipofacturacompra);
            tvRucClienteFactura = itemView.findViewById(R.id.tvrucproveedorfacturacompra);
            tvTotalFactura = itemView.findViewById(R.id.tvtotalfacturcompra);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(facturacompras.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FacturaCompraBusAdapter.FacturaCompraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facturacompra_item,parent,false);
        return new FacturaCompraHolder(itemView);
    }

    /*public FacturaVentaBusAdapter(List<Facturaventa> facturaventasbusq) {
        facturaventasbus = new ArrayList<>(facturaventasbusq);
    }*/

    @Override
    public void onBindViewHolder(@NonNull FacturaCompraBusAdapter.FacturaCompraHolder holder, int position) {
        Facturacompra currentItem = facturacompras.get(position);
        holder.tvNrofactura.setText("Nro. Factura: "+String.valueOf(currentItem.getNrofacturacompra()));
        holder.tvFechaFactura.setText("Fecha: "+String.valueOf(currentItem.getDia_compra().trim()
                .concat("/").concat(currentItem.getMes_compra().trim()).concat("/")
                .concat(currentItem.getAnho_compra())));
        holder.tvTipoFactura.setText("Tipo: "+currentItem.tipoComprobante.getDescripciontipocomprobante());
        holder.tvRucClienteFactura.setText("Cliente: "+currentItem.proveedor.getNombre());
        holder.tvTotalFactura.setText("Total: "+formateador.format(currentItem.getTotal_compra()));
    }

    @Override
    public int getItemCount() {
        return facturacompras.size();
    }

    //metodo para actualizar el ADAPTER
    public void setFacturacompras(List<Facturacompra> facturacompras){
        this.facturacompras = facturacompras;
        this.facturacomprasAll = new ArrayList<>(facturacompras);
        notifyDataSetChanged();
    }

    public Facturacompra getFacturaCompraAt(int position){
        return facturacompras.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        facturacompras.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,Facturacompra delete){
        facturacompras.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(Facturacompra facturacompra);
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
            List<Facturacompra> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(facturacomprasAll);
            }else {
                String filtrovalor = constraint.toString().toLowerCase().trim();

                for (Facturacompra item : facturacomprasAll){
                    //Log.e("filtro ","valor filtro dentro "+String.valueOf(item.getNrofacturaventa()));
                    if (item.getNrofacturacompra().toLowerCase().contains(filtrovalor)){
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
            facturacompras.clear();
            //facturaventas.addAll((Collection<? extends Facturaventa>) results.values);
            facturacompras.addAll((List) results.values);
            //Log.e("filtro ","facturaventas"+facturaventas);
            notifyDataSetChanged();
        }
    };
}
