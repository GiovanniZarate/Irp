package com.proyecto.irp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.ui.adapter.ContribuyenteAdapter;
import com.proyecto.irp.viewmodel.ContribuyenteViewModel;

import java.util.List;

public class ContribuyenteActivity extends AppCompatActivity {

    public static final int ADD_CONTRIBUYENTE_REQUEST = 1;
    public static final int EDIT_CONTRIBUYENTE_REQUEST = 2;

    private ContribuyenteViewModel contribuyenteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribuyente);

        FloatingActionButton btnAgregarContribuyente = findViewById(R.id.btn_AddContribuyente);
        btnAgregarContribuyente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContribuyenteActivity.this,ContribuyenteAddActivity.class);
                startActivityForResult(intent,ADD_CONTRIBUYENTE_REQUEST);
            }
        });

        //AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        //INSTANCIAR EL RECYCLER VIEW
        RecyclerView recyclerView = findViewById(R.id.recycler_usuario);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //INSTANCIAR EL ADAPTER
        final ContribuyenteAdapter adapter = new ContribuyenteAdapter();
        recyclerView.setAdapter(adapter);

        //INSTANCIAR EL VIEW MODEL
        contribuyenteViewModel = ViewModelProviders.of(this).get(ContribuyenteViewModel.class);
        contribuyenteViewModel.getAllContribuyentes().observe(this, new Observer<List<Contribuyente>>() {
            @Override
            public void onChanged(List<Contribuyente> contribuyentes) {
                //update RecyclerView
                //Toast.makeText(UsuarioActivity.this,"onChange", Toast.LENGTH_LONG).show();
                adapter.submitList(contribuyentes);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // remove from adapter
                  contribuyenteViewModel.delete(adapter.getContribuyenteAt(viewHolder.getAdapterPosition()));
                  Toast.makeText(ContribuyenteActivity.this,"Contribuyente Eliminado",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContribuyenteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contribuyente contribuyente) {
                Intent intent = new Intent(ContribuyenteActivity.this,ContribuyenteAddActivity.class);
                intent.putExtra(ContribuyenteAddActivity.EXTRA_ID,contribuyente.getIdcontribuyente());
                intent.putExtra(ContribuyenteAddActivity.EXTRA_CEDULA,contribuyente.getDocumento());
                intent.putExtra(ContribuyenteAddActivity.EXTRA_RUC,contribuyente.getRuc());
                intent.putExtra(ContribuyenteAddActivity.EXTRA_NOMBRE,contribuyente.getNombres());
                intent.putExtra(ContribuyenteAddActivity.EXTRA_CONTRASENA,contribuyente.getContrasena());

                startActivityForResult(intent, EDIT_CONTRIBUYENTE_REQUEST);
            }
        });
        //PARA MOVER ITEM IZQUIERDA DERCHA
       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contribuyenteViewModel.delete(adapter.getContribuyenteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(UsuarioActivity.this,"Contribuyente Eliminado",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTRIBUYENTE_REQUEST && resultCode == RESULT_OK) {
            String cedula = data.getStringExtra(ContribuyenteAddActivity.EXTRA_CEDULA);
            String ruc = data.getStringExtra(ContribuyenteAddActivity.EXTRA_RUC);
            String nombres = data.getStringExtra(ContribuyenteAddActivity.EXTRA_NOMBRE);
            String contrasena = data.getStringExtra(ContribuyenteAddActivity.EXTRA_CONTRASENA);


            Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            if (contribuyenteViewModel.verificaCedula(contribuyente.getDocumento()) == 0){

                contribuyenteViewModel.insert(contribuyente);
                Toast.makeText(this,"Contribuyente Registrado con exito", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Contribuyente Ya está registrado", Toast.LENGTH_SHORT).show();
            }



        }else if  (requestCode == EDIT_CONTRIBUYENTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ContribuyenteAddActivity.EXTRA_ID,-1);
            if (id == -1){
                Toast.makeText(this,"Contribuyente no fue Modificado", Toast.LENGTH_SHORT).show();
                return;
            }
            String cedula = data.getStringExtra(ContribuyenteAddActivity.EXTRA_CEDULA);
            String ruc = data.getStringExtra(ContribuyenteAddActivity.EXTRA_RUC);
            String nombres = data.getStringExtra(ContribuyenteAddActivity.EXTRA_NOMBRE);
            String contrasena = data.getStringExtra(ContribuyenteAddActivity.EXTRA_CONTRASENA);

            Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombres,contrasena);
            contribuyente.setIdcontribuyente(id);
            contribuyenteViewModel.update(contribuyente);
            Toast.makeText(this,"Contribuyente modificado", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this,"Contribuyente no Registrado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete_contribuyente,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_contribuyente:
                contribuyenteViewModel.deleteAllContribuyentes();
                Toast.makeText(this,"Todos los Contribuyentes Eliminado",Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
