package com.proyecto.irp.ui.tipocomprobante;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.proyecto.irp.R;

public class TipoComprobanteAddActivity extends AppCompatActivity implements View.OnClickListener{
    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDTIPOCOMPROBANTE = "com.proyecto.irp.ui.tipocomprobante.EXTRA_IDTIPOCOMPROBANTE";
    public static final String EXTRA_DESCRITIPOCOMPROBANTE = "com.proyecto.irp.ui.tipocomprobante.EXTRA_DESCRITIPOCOMPROBANTE";
    public static final String EXTRA_TIPOCPB = "com.proyecto.irp.ui.tipocomprobante.EXTRA_TIPOCPB";

    EditText vdescripcion;
    Button btnRegistrar;

    RadioGroup grupoTipoCpb;
    RadioButton ingreso,egreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_comprobante_add);

        inicializacion();
        eventos();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDTIPOCOMPROBANTE)){
            setTitle("Editar Tipo Comprobante");
            vdescripcion.setText(intent.getStringExtra(EXTRA_DESCRITIPOCOMPROBANTE));
            if (intent.getStringExtra(EXTRA_TIPOCPB).trim().equals("0")){
                grupoTipoCpb.check(R.id.rbCompra);
            }else{
                grupoTipoCpb.check(R.id.rbVenta);
            }
        }else {
            setTitle("Agregar Tipo Comprobante");
        }
    }

    private void inicializacion() {
        vdescripcion = findViewById(R.id.txtDescripcionTipoComprobante);
        btnRegistrar = findViewById(R.id.btnGrabaTipoComprobante);

        grupoTipoCpb = findViewById(R.id.rbgTipocpb);
        grupoTipoCpb.clearCheck();
        grupoTipoCpb.check(R.id.rbCompra);

        ingreso = findViewById(R.id.rbCompra);
        egreso = findViewById(R.id.rbVenta);
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnRegistrar.setOnClickListener((View.OnClickListener) this);
    }

    //METODO PARA GRABAR CONTRIBUYENTE
    private void save() {
        String descripcion = vdescripcion.getText().toString();

        String tipocpb="";
        // tipocpb : 0 - INGRESO   1- EGRESO
        if (ingreso.isChecked()){
            tipocpb="0";
        }else{
            tipocpb="1";
        }


        if(descripcion.trim().isEmpty()){
            vdescripcion.setError("Debes ingresar descripción");
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_DESCRITIPOCOMPROBANTE,descripcion);
            data.putExtra(EXTRA_TIPOCPB,tipocpb);


            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDTIPOCOMPROBANTE,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDTIPOCOMPROBANTE,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaTipoComprobante:
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
