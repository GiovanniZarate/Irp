package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FacturaCompraAdapter extends RecyclerView.Adapter<FacturaCompraAdapter.FacturaCompraHolder>  {
    private OnItemClickListener listener;
    private List<Facturacompra> facturacompras = new ArrayList<>();

   // DecimalFormat formateador = new DecimalFormat("###,###.##");
   DecimalFormat formateador = new DecimalFormat("###,###.##", new DecimalFormatSymbols(new Locale("es","PY")));


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
    public FacturaCompraAdapter.FacturaCompraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facturacompra_item,parent,false);
        return new FacturaCompraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaCompraAdapter.FacturaCompraHolder holder, int position) {
        Facturacompra currentItem = facturacompras.get(position);
        holder.tvNrofactura.setText("Nro. Factura: "+String.valueOf(currentItem.getNrofacturacompra()));
        holder.tvFechaFactura.setText("Fecha: "+String.valueOf(currentItem.getDia_compra().trim()
                .concat("/").concat(currentItem.getMes_compra().trim()).concat("/")
                .concat(currentItem.getAnho_compra())));
        holder.tvTipoFactura.setText("Tipo: "+currentItem.tipoComprobante.getDescripciontipocomprobante());
        holder.tvRucClienteFactura.setText("Proveedor: "+currentItem.proveedor.getNombre());
        holder.tvTotalFactura.setText("Total: "+formateador.format(currentItem.getTotal_compra()));
    }

    @Override
    public int getItemCount() {
        return facturacompras.size();
    }

    //metodo para actualizar el ADAPTER
    public void setFacturaCompras(List<Facturacompra> facturacompras){
        this.facturacompras = facturacompras;
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



}
