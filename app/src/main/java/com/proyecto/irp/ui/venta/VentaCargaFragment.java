package com.proyecto.irp.ui.venta;

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
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.ui.adapter.FacturaVentaAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;

public class VentaCargaFragment extends Fragment {
    public static final int ADD_FACTURAVENTA_REQUEST = 1;
    public static final int EDIT_FACTURAVENTA_REQUEST = 2;

    //INCIALIZAR ESTOAS
    private VentaCargaViewModel ventaCargaViewModel;
    RecyclerView recyclerView;
    FacturaVentaAdapter adapter = new FacturaVentaAdapter();

    String msgtoast = "Factura Venta";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.ventacarga_fragment, container, false);

        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddFacturaVenta);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),VentaCargaAddActivity.class);
                //startActivity(i);
                startActivityForResult(i,ADD_FACTURAVENTA_REQUEST);

            }
        });

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_facturaventacarga);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this)); ya no se usa this xq no es un activiy
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new FacturaVentaAdapter();
        recyclerView.setAdapter(adapter);

        //INSTANCIAR EL VIEW MODEL
        ventaCargaViewModel = ViewModelProviders.of(this).get(VentaCargaViewModel.class);
        ventaCargaViewModel.getAllFacturaVentas().observe(getActivity(), new Observer<List<Facturaventa>>() {
            @Override
            public void onChanged(List<Facturaventa> facturaventas) {
                adapter.setFacturaventas(facturaventas);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
        adapter.setOnItemClickListener(new FacturaVentaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Facturaventa facturaventa) {
                Intent intent = new Intent(getActivity(),VentaCargaAddActivity.class);
                intent.putExtra(VentaCargaAddActivity.EXTRA_IDFACTURAVENTA,facturaventa.getIdfacturaventa());
                intent.putExtra(VentaCargaAddActivity.EXTRA_DIAVENTA,String.valueOf(facturaventa.getDia_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_MESVENTA,String.valueOf(facturaventa.getMes_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_ANHOVENTA,facturaventa.getAnho_venta());
                intent.putExtra(VentaCargaAddActivity.EXTRA_TIPOCOMPROBANTE,String.valueOf(facturaventa.getId_comprobante()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_CLASIFICACIONINGRESO,String.valueOf(facturaventa.getId_clasificacioningreso()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_CLIENTE,String.valueOf(facturaventa.getId_cliente()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_TIPOCOMPROBANTESELECTED,facturaventa.tipoComprobante.getDescripciontipocomprobante());
                intent.putExtra(VentaCargaAddActivity.EXTRA_CLASIFICACIONINGRESOSELECTED,facturaventa.clasificacionIngreso.getDescripcionclasificacioningreso());
                intent.putExtra(VentaCargaAddActivity.EXTRA_CLIENTESELECTED,facturaventa.cliente.getNombre());
                intent.putExtra(VentaCargaAddActivity.EXTRA_NRO1VENTA,facturaventa.getNro1_venta());
                intent.putExtra(VentaCargaAddActivity.EXTRA_NRO2VENTA,facturaventa.getNro2_venta());
                intent.putExtra(VentaCargaAddActivity.EXTRA_NRO3VENTA,facturaventa.getNro3_venta());
                intent.putExtra(VentaCargaAddActivity.EXTRA_GRAVADA10VENTA,String.valueOf(facturaventa.getGravada10_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_GRAVADA5VENTA,String.valueOf(facturaventa.getGravada5_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_EXENTAVENTA,String.valueOf(facturaventa.getExenta_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_IVA10VENTA,String.valueOf(facturaventa.getIva10_venta()));
                intent.putExtra(VentaCargaAddActivity.EXTRA_IVA5VENTA,String.valueOf(facturaventa.getIva5_venta()));

                //Toast.makeText(getActivity(),"valor enviado 10 "+facturaventa.getGravada10_venta(),Toast.LENGTH_SHORT).show();
                //intent.putExtra(ClasEgresoAddActivity.EXTRA_TIPOEGRESOSELECTED,entity.tipoEgreso.getDescripciontipoegreso());


                startActivityForResult(intent, EDIT_FACTURAVENTA_REQUEST);
            }
        });

        return view;
    }

    //PARA RETOMAR EL RESULTADO DEL ACTIVITY DE CARGA PARA GRABAR
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (data!=null){
        String dia = data.getStringExtra(VentaCargaAddActivity.EXTRA_DIAVENTA);
        String mes =  data.getStringExtra(VentaCargaAddActivity.EXTRA_MESVENTA);
        String anho =  data.getStringExtra(VentaCargaAddActivity.EXTRA_ANHOVENTA);

        String nro1 = data.getStringExtra(VentaCargaAddActivity.EXTRA_NRO1VENTA);
        String nro2 =  data.getStringExtra(VentaCargaAddActivity.EXTRA_NRO2VENTA);
        String nro3 =  data.getStringExtra(VentaCargaAddActivity.EXTRA_NRO3VENTA);

        long fecha = Long.parseLong(dia+mes+anho);
        int idcliente = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_CLIENTE));
        int idclasingreso = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_CLASIFICACIONINGRESO));
        int idcontribuyente = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_CONTRIBUYENTE));
        int idejercicio = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_EJERCICIO));
        int idcomprobante = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_TIPOCOMPROBANTE));

        String nrofactura = String.valueOf(nro1).trim().concat("-")
                .concat(String.valueOf(nro2).trim().concat("-")
                        .concat(String.valueOf(nro3).trim()));

        int total = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_TOTALVENTA));
        int EX = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_TOTALVENTA));
        int grav10 = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_GRAVADA10VENTA));
        int grav5 = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_GRAVADA5VENTA));
        int exen = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_EXENTAVENTA));

        int iva10 = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_IVA10VENTA));
        int iva5 = Integer.parseInt(data.getStringExtra(VentaCargaAddActivity.EXTRA_IVA5VENTA));

        //Toast.makeText(getActivity(),"data "+data,Toast.LENGTH_SHORT).show();
        if (requestCode == ADD_FACTURAVENTA_REQUEST && resultCode == RESULT_OK) {
            Facturaventa facturaventa = new Facturaventa(fecha,idcliente,idclasingreso,
                    idcontribuyente,idejercicio,idcomprobante,nrofactura,total,exen,grav10,iva10,
                    grav5,iva5,nro1,nro2,nro3,dia,mes,anho);
            //if (clienteViewModel.verificaCedula(String.valueOf(cliente.getRucveri())) == 0){
                ventaCargaViewModel.insert(facturaventa);
                Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();
            //}else {
              //  Toast.makeText(getActivity(),msgtoast+" Ya está registrado", Toast.LENGTH_SHORT).show();
            //}

        }

        else if  (requestCode == EDIT_FACTURAVENTA_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(VentaCargaAddActivity.EXTRA_IDFACTURAVENTA,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            Facturaventa facturaventa = new Facturaventa(fecha,idcliente,idclasingreso,
                    idcontribuyente,idejercicio,idcomprobante,nrofactura,total,exen,grav10,iva10,
                    grav5,iva5,nro1,nro2,nro3,dia,mes,anho);
            facturaventa.setIdfacturaventa(id);
            ventaCargaViewModel.update(facturaventa);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();


        } else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }

    }
    }



    Facturaventa deteteItem = null;
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
            deteteItem = adapter.getFacturaVentaAt(position);
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
                        ventaCargaViewModel.delete(deteteItem);
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
