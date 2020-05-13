package com.proyecto.irp.ui.clasingreso;

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
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.ui.adapter.ClasificacionIngresoAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;

public class ClasIngresoFragment extends Fragment {
    public static final int ADD_CLASINGRESO_REQUEST = 1;
    public static final int EDIT_CLASINGRESO_REQUEST = 2;

    private ClasIngresoViewModel clasIngresoViewModel;
    RecyclerView recyclerView;
    ClasificacionIngresoAdapter adapter = new ClasificacionIngresoAdapter();

    String msgtoast = "Clasificación de Ingreso";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clasingreso, container, false);

        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddClasingreso);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ClasIngresoAddActivity.class);
                startActivityForResult(i,ADD_CLASINGRESO_REQUEST);
            }
        });

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_clasificacioningreso);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this)); ya no se usa this xq no es un activiy
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new ClasificacionIngresoAdapter();
        recyclerView.setAdapter(adapter);




        //INSTANCIAR EL VIEW MODEL
        clasIngresoViewModel = ViewModelProviders.of(this).get(ClasIngresoViewModel.class);
        clasIngresoViewModel.getAllClientes().observe(getActivity(), new Observer<List<ClasificacionIngreso>>() {
            @Override
            public void onChanged(List<ClasificacionIngreso> lista) {
                adapter.setClasficacionIngreso(lista);
            }
        });

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
        adapter.setOnItemClickListener(new ClasificacionIngresoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClasificacionIngreso entity) {
                Intent intent = new Intent(getActivity(),ClasIngresoAddActivity.class);
                intent.putExtra(ClasIngresoAddActivity.EXTRA_IDCLASINGRESO,entity.getIdclasificacioningreso());
                intent.putExtra(ClasIngresoAddActivity.EXTRA_DESCRICLASINGRESO,entity.getDescripcion());

                startActivityForResult(intent, EDIT_CLASINGRESO_REQUEST);
            }
        });

        //PARA QUE PUEDA ESTIRAR A LA DERECHA O IZQUIERDA Y ELIMINAR EL ENTE
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //clasIngresoViewModel.insert(new ClasificacionIngreso("Honorarios Profesionales y otras remuneracciones percibidas por servicios personales"));

        return view;
    }

    //PARA RETOMAR EL RESULTADO DEL ACTIVITY DE CARGA PARA GRABAR
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CLASINGRESO_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(ClasIngresoAddActivity.EXTRA_DESCRICLASINGRESO);


            ClasificacionIngreso entity = new ClasificacionIngreso(descripcion);
            clasIngresoViewModel.insert(entity);
            Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();

        }else if  (requestCode == EDIT_CLASINGRESO_REQUEST && resultCode == RESULT_OK) {
            String descripcion = data.getStringExtra(ClasIngresoAddActivity.EXTRA_DESCRICLASINGRESO);
            int id = data.getIntExtra(ClasIngresoAddActivity.EXTRA_IDCLASINGRESO,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            ClasificacionIngreso entity = new ClasificacionIngreso(descripcion);
            entity.setIdclasificacioningreso(id);
            clasIngresoViewModel.update(entity);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }
    }



    ClasificacionIngreso deteteItem = null;
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
            deteteItem = adapter.getClasificacionIngresoAt(position);
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
                        clasIngresoViewModel.delete(deteteItem);
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
