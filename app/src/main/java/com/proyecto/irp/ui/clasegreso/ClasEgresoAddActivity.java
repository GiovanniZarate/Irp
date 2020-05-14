package com.proyecto.irp.ui.clasegreso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.proyecto.irp.R;

public class ClasEgresoAddActivity extends AppCompatActivity implements View.OnClickListener {


    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDCLASEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_IDCLASEGRESO";
    public static final String EXTRA_DESCRICLASEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_DESCRICLASEGRESO";
    public static final String EXTRA_TIPOEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_TIPOEGRESO";

    EditText vdescripcion;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clas_egreso_add);

        inicializacion();
        eventos();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDCLASEGRESO)){
            setTitle("Editar Clasif. de Egreso");
            vdescripcion.setText(intent.getStringExtra(EXTRA_DESCRICLASEGRESO));
        }else {
            setTitle("Agregar Clasif. de Egreso");
        }


        //val items = listOf("Material", "Design", "Components", "Android")
        //val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        //(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private void inicializacion() {
        vdescripcion = findViewById(R.id.txtDescripcionClasEgreso);
        btnRegistrar = findViewById(R.id.btnGrabaClasEgreso);
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnRegistrar.setOnClickListener((View.OnClickListener) this);
    }

    //METODO PARA GRABAR CONTRIBUYENTE
    private void save() {
        String descripcion = vdescripcion.getText().toString();


        if(descripcion.trim().isEmpty()){
            vdescripcion.setError("Debes ingresar descripción");
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_DESCRICLASEGRESO,descripcion);


            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDCLASEGRESO,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDCLASEGRESO,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaClasEgreso:
                save();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_contribuyente,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_contribuyente:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
