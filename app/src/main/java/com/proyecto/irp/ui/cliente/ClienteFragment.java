package com.proyecto.irp.ui.cliente;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.ui.adapter.ClienteAdapter;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;

public class ClienteFragment extends Fragment {

    public static final int ADD_CLIENTE_REQUEST = 1;
    public static final int EDIT_CLIENTE_REQUEST = 2;


    //INCIALIZAR ESTOAS
    private ClienteViewModel clienteViewModel;
    RecyclerView recyclerView;
    ClienteAdapter adapter = new ClienteAdapter();

    String msgtoast = "Cliente";

    //PARA LLAMAR A OTRO FRAGMENTO
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*clienteViewModel =
                ViewModelProviders.of(this).get(ClienteViewModel.class);*/
        final View view = inflater.inflate(R.layout.fragment_cliente, container, false);



       FloatingActionButton btnAgregarContribuyente = view.findViewById(R.id.btn_AddCliente);
        btnAgregarContribuyente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ClienteAddFragment clienteAddFragment = new ClienteAddFragment();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedor_cliente,clienteAddFragment);
                fragmentTransaction.commit();*/


                Intent i = new Intent(getActivity(),ClienteAddActivity.class);
                startActivityForResult(i,ADD_CLIENTE_REQUEST);

              //  Intent intent = new Intent(ContribuyenteActivity.this,ContribuyenteAddActivity.class);
              //  startActivityForResult(intent,ADD_CONTRIBUYENTE_REQUEST);


               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
            }
        });


        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_cliente);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this)); ya no se usa this xq no es un activiy
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new ClienteAdapter();
        recyclerView.setAdapter(adapter);




        //INSTANCIAR EL VIEW MODEL
       clienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);
       clienteViewModel.getAllClientes().observe(getActivity(), new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                //update RecyclerView
                //Toast.makeText(UsuarioActivity.this,"onChange", Toast.LENGTH_LONG).show();
                //adapter.submitList(contribuyentes);
                adapter.setClientes(clientes);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

       /// clienteViewModel.insert(new Cliente("Giovanni Zarate","4203593-7",1,4203593,7));

        //PARA PASAR LOS DATOS  EN LOS TEXTOS PARA EDITAR (para ello se crea en al adpater un listener)
        adapter.setOnItemClickListener(new ClienteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente) {
                Intent intent = new Intent(getActivity(),ClienteAddActivity.class);
                intent.putExtra(ClienteAddActivity.EXTRA_IDCLIENTE,cliente.getIdcliente());
                intent.putExtra(ClienteAddActivity.EXTRA_RUCVERI,String.valueOf(cliente.getRucveri()));
                intent.putExtra(ClienteAddActivity.EXTRA_RUCDIV,String.valueOf(cliente.getDiv()));
                intent.putExtra(ClienteAddActivity.EXTRA_NOMBRECLIENTE,cliente.getNombre());
                intent.putExtra(ClienteAddActivity.EXTRA_TIPOCLIENTE,String.valueOf(cliente.getTipo()));

                startActivityForResult(intent, EDIT_CLIENTE_REQUEST);
            }
        });

        return view;
    }

    //PARA RETOMAR EL RESULTADO DEL ACTIVITY DE CARGA PARA GRABAR
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CLIENTE_REQUEST && resultCode == RESULT_OK) {
            int rucveri =Integer.parseInt(data.getStringExtra(ClienteAddActivity.EXTRA_RUCVERI));
            int rucdiv = Integer.parseInt(data.getStringExtra(ClienteAddActivity.EXTRA_RUCDIV));
            String nombres = data.getStringExtra(ClienteAddActivity.EXTRA_NOMBRECLIENTE);
            String tipo = data.getStringExtra(ClienteAddActivity.EXTRA_TIPOCLIENTE);
            String ruc = String.valueOf(rucveri).trim().concat("-").concat(String.valueOf(rucdiv).trim());
            int tipocliente;
            if (tipo.trim().equals("0")){
                tipocliente=0;
            }else {
                tipocliente=1;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            Cliente cliente = new Cliente(nombres,ruc,tipocliente,rucveri,rucdiv);
            if (clienteViewModel.verificaCedula(String.valueOf(cliente.getRucveri())) == 0){

                clienteViewModel.insert(cliente);
                Toast.makeText(getActivity(),msgtoast+" Registrado con exito", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),msgtoast+" Ya está registrado", Toast.LENGTH_SHORT).show();
            }

        }else if  (requestCode == EDIT_CLIENTE_REQUEST && resultCode == RESULT_OK) {
            int rucveri =Integer.parseInt(data.getStringExtra(ClienteAddActivity.EXTRA_RUCVERI));
            int rucdiv = Integer.parseInt(data.getStringExtra(ClienteAddActivity.EXTRA_RUCDIV));
            String nombres = data.getStringExtra(ClienteAddActivity.EXTRA_NOMBRECLIENTE);
            String tipo = data.getStringExtra(ClienteAddActivity.EXTRA_TIPOCLIENTE);
            String ruc = String.valueOf(rucveri).trim().concat("-").concat(String.valueOf(rucdiv).trim());
            int tipocliente;
            if (tipo.trim().equals("0")){
                tipocliente=0;
            }else {
                tipocliente=1;
            }
            int id = data.getIntExtra(ClienteAddActivity.EXTRA_IDCLIENTE,-1);
            if (id == -1){
                Toast.makeText(getActivity(),msgtoast+" no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            //Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            Cliente cliente = new Cliente(nombres,ruc,tipocliente,rucveri,rucdiv);
            cliente.setIdcliente(id);
            clienteViewModel.update(cliente);
            Toast.makeText(getActivity(),msgtoast+"  modificado", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(getActivity(),msgtoast+" no Registrado", Toast.LENGTH_SHORT).show();
        }
    }



    Cliente deteteItem = null;
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
            deteteItem = adapter.getClienteAt(position);
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
                        clienteViewModel.delete(deteteItem);
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
