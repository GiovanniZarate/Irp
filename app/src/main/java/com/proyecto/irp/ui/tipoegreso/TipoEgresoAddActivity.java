package com.proyecto.irp.ui.tipoegreso;

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

public class TipoEgresoAddActivity extends AppCompatActivity implements View.OnClickListener{

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDTIPOEGRESO = "com.proyecto.irp.ui.tipoegreso.EXTRA_IDTIPOEGRESO";
    public static final String EXTRA_DESCRITIPOEGRESO = "com.proyecto.irp.ui.tipoegreso.EXTRA_DESCRITIPOEGRESO";
    EditText vdescripcion;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_egreso_add);
        inicializacion();
        eventos();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDTIPOEGRESO)){
            setTitle("Editar Tipo Egreso");
            vdescripcion.setText(intent.getStringExtra(EXTRA_DESCRITIPOEGRESO));
        }else {
            setTitle("Agregar Tipo Egreso");
        }
    }

    private void inicializacion() {
        vdescripcion = findViewById(R.id.txtDescripcionTipoEgreso);
        btnRegistrar = findViewById(R.id.btnGrabaTipoEgreso);
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
            data.putExtra(EXTRA_DESCRITIPOEGRESO,descripcion);


            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDTIPOEGRESO,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDTIPOEGRESO,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaTipoEgreso:
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
