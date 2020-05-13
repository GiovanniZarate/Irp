package com.proyecto.irp.ui.clasingreso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.proyecto.irp.R;

public class ClasIngresoAddActivity extends AppCompatActivity implements View.OnClickListener{

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDCLASINGRESO = "com.proyecto.irp.ui.clasingreso.EXTRA_IDCLIENTE";
    public static final String EXTRA_DESCRICLASINGRESO = "com.proyecto.irp.ui.clasingreso.EXTRA_RUCVERI";

    EditText vdescripcion;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clas_ingreso_add);

        inicializacion();
        eventos();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDCLASINGRESO)){
            setTitle("Editar Clasif. de Ingreso");
            vdescripcion.setText(intent.getStringExtra(EXTRA_DESCRICLASINGRESO));
        }else {
            setTitle("Agregar Clasif. de Ingreso");
        }
    }

    private void inicializacion() {
        vdescripcion = findViewById(R.id.txtDescripcionClasIngreso);
        btnRegistrar = findViewById(R.id.btnGrabaClasIngreso);
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
            data.putExtra(EXTRA_DESCRICLASINGRESO,descripcion);


            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDCLASINGRESO,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDCLASINGRESO,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaClasIngreso:
                save();
                break;
        }
    }
}
