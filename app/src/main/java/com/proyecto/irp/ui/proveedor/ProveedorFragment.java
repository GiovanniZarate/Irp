package com.proyecto.irp.ui.proveedor;

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
import com.proyecto.irp.db.entity.Proveedor;
import com.proyecto.irp.ui.adapter.ProveedorAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;


public class ProveedorFragment extends Fragment {

    public static final int ADD_PROVEEDOR_REQUEST = 1;
    public static final int EDIT_PROVEEDOR_REQUEST = 2;

    //INCIALIZAR ESTOAS
    private ProveedorViewModel proveedorViewModel;
    RecyclerView recyclerView;
    ProveedorAdapter adapter = new ProveedorAdapter();
    String msgtoast = "Proveedor";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_proveedor, container, false);


        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddProveedor);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ProveedorAddActivity.class);
                startActivityForResult(i,ADD_PROVEEDOR_REQUEST);

            }
        });

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_proveedor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new ProveedorAdapter();
        recyclerView.setAdapter(adapter);


        //INSTANCIAR EL VIEW MODEL
        proveedorViewModel = ViewModelProviders.of(this).get(ProveedorViewModel.class);
        proveedorViewModel.getAllProveedores().observe(getActivity(), new Observer<List<Proveedor>>() {
            @Override
            public void onChanged(List<Proveedor> proveedor) {
                adapter.setProveedor(proveedor);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
        adapter.setOnItemClickListener(new ProveedorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Proveedor proveedor) {
                Intent intent = new Intent(getActivity(),ProveedorAddActivity.class);
                intent.putExtra(ProveedorAddActivity.EXTRA_IDPROVEEDOR,proveedor.getIdproveedor());
                intent.putExtra(ProveedorAddActivity.EXTRA_RUCVERI,String.valueOf(proveedor.getRucveri()));
                intent.putExtra(ProveedorAddActivity.EXTRA_RUCDIV,String.valueOf(proveedor.getDiv()));
                intent.putExtra(ProveedorAddActivity.EXTRA_NOMBREPROVEEDOR,proveedor.getNombre());
                intent.putExtra(ProveedorAddActivity.EXTRA_TIPOPROVEEDOR,String.valueOf(proveedor.getTipo()));

                startActivityForResult(intent, EDIT_PROVEEDOR_REQUEST);
            }
        });

        return view;
    }

    //PARA RETOMAR EL RESULTADO DEL ACTIVITY DE CARGA PARA GRABAR
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PROVEEDOR_REQUEST && resultCode == RESULT_OK) {
            int rucveri =Integer.parseInt(data.getStringExtra(ProveedorAddActivity.EXTRA_RUCVERI));
            int rucdiv = Integer.parseInt(data.getStringExtra(ProveedorAddActivity.EXTRA_RUCDIV));
            String nombres = data.getStringExtra(ProveedorAddActivity.EXTRA_NOMBREPROVEEDOR);
            String tipo = data.getStringExtra(ProveedorAddActivity.EXTRA_TIPOPROVEEDOR);
            String ruc = String.valueOf(rucveri).trim().concat("-").concat(String.valueOf(rucdiv).trim());
            int tipocliente;
            if (tipo.trim().equals("0")){
                tipocliente=0;
            }else {
                tipocliente=1;
            }

            Proveedor cliente = new Proveedor(nombres,ruc,tipocliente,rucveri,rucdiv);
            if (proveedorViewModel.verificaCedula(String.valueOf(cliente.getRucveri())) == 0){

                proveedorViewModel.insert(cliente);
                Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),msgtoast+" Ya está registrado", Toast.LENGTH_SHORT).show();
            }

        }else if  (requestCode == EDIT_PROVEEDOR_REQUEST && resultCode == RESULT_OK) {
            int rucveri =Integer.parseInt(data.getStringExtra(ProveedorAddActivity.EXTRA_RUCVERI));
            int rucdiv = Integer.parseInt(data.getStringExtra(ProveedorAddActivity.EXTRA_RUCDIV));
            String nombres = data.getStringExtra(ProveedorAddActivity.EXTRA_NOMBREPROVEEDOR);
            String tipo = data.getStringExtra(ProveedorAddActivity.EXTRA_TIPOPROVEEDOR);
            String ruc = String.valueOf(rucveri).trim().concat("-").concat(String.valueOf(rucdiv).trim());
            int tipocliente;
            if (tipo.trim().equals("0")){
                tipocliente=0;
            }else {
                tipocliente=1;
            }
            int id = data.getIntExtra(ProveedorAddActivity.EXTRA_IDPROVEEDOR,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            Proveedor cliente = new Proveedor(nombres,ruc,tipocliente,rucveri,rucdiv);
            cliente.setIdproveedor(id);
            proveedorViewModel.update(cliente);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }
    }


    Proveedor deteteItem = null;
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
            deteteItem = adapter.getProveedorAt(position);
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
                        proveedorViewModel.delete(deteteItem);
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
