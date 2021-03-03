package com.proyecto.irp.ui.tipocomprobante;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.ui.adapter.TipoComprobanteAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;

public class TipoComprobanteFragment extends Fragment {

    public static final int ADD_TIPOCOMPROBANTE_REQUEST = 1;
    public static final int EDIT_TIPOCOMPROBANTE_REQUEST = 2;

    private TipoComprobanteViewModel tipoComprobanteViewModel;
    RecyclerView recyclerView;
    TipoComprobanteAdapter adapter = new TipoComprobanteAdapter();

    String msgtoast = "Tipo de Comprobante";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo_comprobante, container, false);

        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddTipocomprobante);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),TipoComprobanteAddActivity.class);
                startActivityForResult(i,ADD_TIPOCOMPROBANTE_REQUEST);
            }
        });

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_tipocomprobante);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this)); ya no se usa this xq no es un activiy
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        //INSTANCIAR EL ADAPTER
        adapter = new TipoComprobanteAdapter();
        recyclerView.setAdapter(adapter);

        //INSTANCIAR EL VIEW MODEL
        tipoComprobanteViewModel = ViewModelProviders.of(this).get(TipoComprobanteViewModel.class);
        tipoComprobanteViewModel.getAllDatos().observe(getActivity(), new Observer<List<TipoComprobante>>() {
            @Override
            public void onChanged(List<TipoComprobante> lista) {
                adapter.setTipocomprobante(lista);
            }
        });

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
       adapter.setOnItemClickListener(new TipoComprobanteAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(TipoComprobante entity) {
               Intent intent = new Intent(getActivity(),TipoComprobanteAddActivity.class);
               intent.putExtra(TipoComprobanteAddActivity.EXTRA_IDTIPOCOMPROBANTE,entity.getIdtipocomprobante());
               intent.putExtra(TipoComprobanteAddActivity.EXTRA_DESCRITIPOCOMPROBANTE,entity.getDescripciontipocomprobante());
               intent.putExtra(TipoComprobanteAddActivity.EXTRA_TIPOCPB,String.valueOf(entity.getTipocpb()));

               startActivityForResult(intent, EDIT_TIPOCOMPROBANTE_REQUEST);
           }
       });


        //PARA QUE PUEDA ESTIRAR A LA DERECHA O IZQUIERDA Y ELIMINAR EL ENTE
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    //PARA RETOMAR EL RESULTADO DEL ACTIVITY DE CARGA PARA GRABAR
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TIPOCOMPROBANTE_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(TipoComprobanteAddActivity.EXTRA_DESCRITIPOCOMPROBANTE);
            String tipo = data.getStringExtra(TipoComprobanteAddActivity.EXTRA_TIPOCPB);
            int tipocpb;
            if (tipo.trim().equals("0")){
                tipocpb=0;
            }else {
                tipocpb=1;
            }

            TipoComprobante entity = new TipoComprobante(descripcion,tipocpb);
            tipoComprobanteViewModel.insert(entity);
            Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();

        }else if  (requestCode == EDIT_TIPOCOMPROBANTE_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(TipoComprobanteAddActivity.EXTRA_DESCRITIPOCOMPROBANTE);
            String tipo = data.getStringExtra(TipoComprobanteAddActivity.EXTRA_TIPOCPB);
            int tipocpb;
            if (tipo.trim().equals("0")){
                tipocpb=0;
            }else {
                tipocpb=1;
            }
            int id = data.getIntExtra(TipoComprobanteAddActivity.EXTRA_IDTIPOCOMPROBANTE,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }

            TipoComprobante entity = new TipoComprobante(descripcion,tipocpb);
            entity.setIdtipocomprobante(id);
            tipoComprobanteViewModel.update(entity);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }
    }



    TipoComprobante deteteItem = null;
    //PARA LA ELIMINACION DE LOS ITEM DESLIZANDO
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            deteteItem = adapter.getTipoComprobanteAt(position);
            adapter.removeItem(position);

            //VALIDAR PARA QUE NO PUEDA ELIMINAR SI YA TIENE REFERENCIA CON OTRA TABLA
            if (tipoComprobanteViewModel.verificaTipocomprobante(deteteItem.getIdtipocomprobante()) == 0){
                eliminar(position);
            }else {
                Toast.makeText(getActivity(),"No puede ser eliminado, tiene referencia con otra tabla",Toast.LENGTH_SHORT).show();
            }
        }

        //AGREGAR IMAGEN ELIMINAR mientras se mueve

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorShipe))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorShipe))
                    .addSwipeRightActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void eliminar(final int posicioneliminar){
        new MaterialAlertDialogBuilder(getActivity(),R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("Eliminar")
                .setMessage("¿Desea Eliminar Item?")
                .setIcon(R.drawable.ic_exit_to_app)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoComprobanteViewModel.delete(deteteItem);
                        Toast.makeText(getActivity(),msgtoast+" Eliminado",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.addItem(posicioneliminar,deteteItem);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
