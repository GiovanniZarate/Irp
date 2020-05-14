package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.Proveedor;

import java.util.ArrayList;
import java.util.List;


public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ProveedorHolder> {
    private OnItemClickListener listener;
    private List<Proveedor> proveedor = new ArrayList<>();

    //1) SE CREA LA CLASE Holder
    public class ProveedorHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvIdProveedor,tvRucProveedor,tvNombreProveedor,tvTipoProveedor;

        public ProveedorHolder(@NonNull View itemView) {
            super(itemView);
            tvIdProveedor = itemView.findViewById(R.id.tvidproveedor);
            tvRucProveedor = itemView.findViewById(R.id.tvrucproveedor);
            tvNombreProveedor = itemView.findViewById(R.id.tvnombreproveedor);
            tvTipoProveedor = itemView.findViewById(R.id.tvtipoproveedor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(proveedor.get(position));
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public ProveedorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.proveedor_item,parent,false);
        return new ProveedorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorHolder holder, int position) {
        String tipo="Tipo: ";
        Proveedor current = proveedor.get(position);
        holder.tvIdProveedor.setText("Id: "+String.valueOf(current.getIdproveedor()));
        holder.tvRucProveedor.setText("Ruc: "+current.getRuc());
        holder.tvNombreProveedor.setText("Nombres: "+current.getNombre());
        if (current.getTipo()==0){
            holder.tvTipoProveedor.setText(tipo+"Fisica");
        }else {
            holder.tvTipoProveedor.setText(tipo+"Juridica");
        }
    }

    @Override
    public int getItemCount() {
        return proveedor.size();
    }

    //metodo para actualizar el ADAPTER
    public void setProveedor(List<Proveedor> proveedor){
        this.proveedor = proveedor;
        notifyDataSetChanged();
    }

    public Proveedor getProveedorAt(int position){
        return proveedor.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        proveedor.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,Proveedor delete){
        proveedor.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(Proveedor proveedor);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
