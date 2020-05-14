package com.proyecto.irp.ui.clasegreso;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.ui.adapter.ClasificacionEgresoAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;


public class ClasEgresoFragment extends Fragment {
    public static final int ADD_CLASEGRESO_REQUEST = 1;
    public static final int EDIT_CLASEGRESO_REQUEST = 2;

    private ClasEgresoViewModel clasEgresoViewModel;
    RecyclerView recyclerView;
    ClasificacionEgresoAdapter adapter = new ClasificacionEgresoAdapter();

    String msgtoast = "Clasificación de Egreso";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clasegreso, container, false);

        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddClasegreso);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ClasEgresoAddActivity.class);
                startActivityForResult(i,ADD_CLASEGRESO_REQUEST);

            }
        });

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_clasificacionegreso);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this)); ya no se usa this xq no es un activiy
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        //INSTANCIAR EL ADAPTER
        adapter = new ClasificacionEgresoAdapter();
        recyclerView.setAdapter(adapter);

        //INSTANCIAR EL VIEW MODEL
        clasEgresoViewModel = ViewModelProviders.of(this).get(ClasEgresoViewModel.class);
        clasEgresoViewModel.getAllClasficacionEgreso().observe(getActivity(), new Observer<List<ClasificacionEgreso>>() {
            @Override
            public void onChanged(List<ClasificacionEgreso> lista) {
                adapter.setClasficacionEgreso(lista);
            }
        });

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
        adapter.setOnItemClickListener(new ClasificacionEgresoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClasificacionEgreso entity) {
                Intent intent = new Intent(getActivity(),ClasEgresoAddActivity.class);
                intent.putExtra(ClasEgresoAddActivity.EXTRA_IDCLASEGRESO,entity.getIdclasificacionegreso());
                intent.putExtra(ClasEgresoAddActivity.EXTRA_DESCRICLASEGRESO,entity.getDescripcion());

                startActivityForResult(intent, EDIT_CLASEGRESO_REQUEST);
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
        if (requestCode == ADD_CLASEGRESO_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(ClasEgresoAddActivity.EXTRA_DESCRICLASEGRESO);
            int tipoegreso = Integer.parseInt(data.getStringExtra(ClasEgresoAddActivity.EXTRA_TIPOEGRESO));


            ClasificacionEgreso entity = new ClasificacionEgreso(descripcion,tipoegreso);
            clasEgresoViewModel.insert(entity);
            Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();

        }else if  (requestCode == EDIT_CLASEGRESO_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(ClasEgresoAddActivity.EXTRA_DESCRICLASEGRESO);
            int tipoegreso = Integer.parseInt(data.getStringExtra(ClasEgresoAddActivity.EXTRA_TIPOEGRESO));
            int id = data.getIntExtra(ClasEgresoAddActivity.EXTRA_IDCLASEGRESO,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            ClasificacionEgreso entity = new ClasificacionEgreso(descripcion,tipoegreso);
            entity.setIdclasificacionegreso(id);
            clasEgresoViewModel.update(entity);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }
    }



    ClasificacionEgreso deteteItem = null;
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
            deteteItem = adapter.getClasificacionEgresoAt(position);
            adapter.removeItem(position);

            eliminar(position);
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
                        clasEgresoViewModel.delete(deteteItem);
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
