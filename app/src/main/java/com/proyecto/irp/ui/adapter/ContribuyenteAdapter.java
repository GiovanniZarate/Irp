package com.proyecto.irp.ui.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Contribuyente;

import java.util.ArrayList;
import java.util.List;

//public class ContribuyenteAdapter extends ListAdapter<Contribuyente,ContribuyenteAdapter.ContribuyenteHolder> {
public class ContribuyenteAdapter extends RecyclerView.Adapter<ContribuyenteAdapter.ContribuyenteHolder> {
    private List<Contribuyente> contribuyentes = new ArrayList<>();
    private OnItemClickListener listener;

    /*public ContribuyenteAdapter() {
        super(DIFF_CALLBACK);
    }*/

   /* public static final DiffUtil.ItemCallback<Contribuyente> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contribuyente>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contribuyente oldItem, @NonNull Contribuyente newItem) {
            return oldItem.getIdcontribuyente() == newItem.getIdcontribuyente();
        }


        @Override
        public boolean areContentsTheSame(@NonNull Contribuyente oldItem, @NonNull Contribuyente newItem) {
            return oldItem.getDocumento().equals(newItem.getDocumento())
                    && oldItem.getRuc().equals(newItem.getRuc())
                    && oldItem.getNombres().equals(newItem.getNombres())
                    && oldItem.getContrasena().equals(newItem.getContrasena());
        }
    };*/

    @NonNull
    @Override
    public ContribuyenteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contribuyente_item,parent,false);
        return new ContribuyenteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContribuyenteHolder holder, int position) {
        //Contribuyente currentContribuyente = contribuyentes.get(position);
       // Contribuyente currentContribuyente = getItem(position);
        Contribuyente currentContribuyente = contribuyentes.get(position);
        holder.tvIdcontribuyente.setText("Id: "+String.valueOf(currentContribuyente.getIdcontribuyente()));
        holder.tvCedula.setText("Ci: "+currentContribuyente.getDocumento());
        holder.tvRuc.setText("Ruc: "+currentContribuyente.getRuc());
        holder.tvNombreApellido.setText("Nombres: "+currentContribuyente.getNombres());
    }

    @Override
    public int getItemCount() {
        return contribuyentes.size();
    }

  /*  @Override
    public int getItemCount() {
        return contribuyentes.size();
    }*/

    //metodo para actualizar el ADAPTER
    public void setContribuyentes(List<Contribuyente> contribuyentes){
        this.contribuyentes = contribuyentes;
        notifyDataSetChanged();
    }

    public Contribuyente getContribuyenteAt(int position){
        //return contribuyentes.get(position);
        return contribuyentes.get(position);
    }

    /*public List<Contribuyente> getContribuyenteAtList(int position){
        //return contribuyentes.get(position);
        return (List<Contribuyente>) contribuyentes.get(position);
    }*/



    //REMOVER ITEM de la lista
    public void removeItem(int position){
        contribuyentes.remove(position);
        notifyItemRemoved(position);
    }

    //ADD ITEM de la lista
    public void addItem(int position,Contribuyente delete){
        contribuyentes.add(position,delete);
        notifyItemInserted(position);
    }


    //Clase ViewHolder
    class ContribuyenteHolder extends RecyclerView.ViewHolder{
        private TextView tvIdcontribuyente;
        private TextView tvCedula;
        private TextView tvRuc;
        private TextView tvNombreApellido;

        public ContribuyenteHolder(@NonNull View itemView) {
            super(itemView);
            tvIdcontribuyente = itemView.findViewById(R.id.tvidcontribuyente);
            tvCedula = itemView.findViewById(R.id.tvcedula);
            tvRuc = itemView.findViewById(R.id.tvruc);
            tvNombreApellido = itemView.findViewById(R.id.tvnombreapellido);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        //listener.onItemClick(contribuyentes.get(position));
                        listener.onItemClick(contribuyentes.get(position));
                    }

                }
            });
        }

        //EVENTO ITEM CLICK

    }

    public interface OnItemClickListener{
        void onItemClick(Contribuyente contribuyente);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
