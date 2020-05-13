package com.proyecto.irp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Cliente;

import java.util.ArrayList;
import java.util.List;


public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteHolder> {

    private OnItemClickListener listener;
    private List<Cliente> clientes = new ArrayList<>();

    //1) SE CREA LA CLASE Holder
    public class ClienteHolder extends RecyclerView.ViewHolder{
        //Se crean las variables
        private TextView tvIdcliente,tvRucCliente,tvNombreCliente,tvTipoCliente;

        public ClienteHolder(@NonNull View itemView) {
            super(itemView);
            tvIdcliente = itemView.findViewById(R.id.tvidcliente);
            tvRucCliente = itemView.findViewById(R.id.tvruccliente);
            tvNombreCliente = itemView.findViewById(R.id.tvnombrecliente);
            tvTipoCliente = itemView.findViewById(R.id.tvtipocliente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(clientes.get(position));
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_item,parent,false);
        return new ClienteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteHolder holder, int position) {
        String tipo="Tipo: ";
        Cliente currentCliente = clientes.get(position);
        holder.tvIdcliente.setText("Id: "+String.valueOf(currentCliente.getIdcliente()));
        holder.tvRucCliente.setText("Ruc: "+currentCliente.getRuc());
        holder.tvNombreCliente.setText("Nombres: "+currentCliente.getNombre());
        if (currentCliente.getTipo()==0){
            holder.tvTipoCliente.setText(tipo+"Fisica");
        }else {
            holder.tvTipoCliente.setText(tipo+"Juridica");
        }

    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    //metodo para actualizar el ADAPTER
    public void setClientes(List<Cliente> clientes){
        this.clientes = clientes;
        notifyDataSetChanged();
    }

    public Cliente getClienteAt(int position){
        //return contribuyentes.get(position);
        return clientes.get(position);
    }

    //REMOVER ITEM de la lista
    public void removeItem(int position){
        clientes.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,Cliente delete){
        clientes.add(position,delete);
        notifyItemInserted(position);
    }


    public interface OnItemClickListener{
        void onItemClick(Cliente cliente);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
