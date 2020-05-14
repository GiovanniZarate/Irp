package com.proyecto.irp.ui.proveedor;

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
import com.proyecto.irp.Utilitario.CalculaDivisor;

public class ProveedorAddActivity extends AppCompatActivity implements View.OnClickListener{

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDPROVEEDOR = "com.proyecto.irp.ui.proveedor.EXTRA_IDPROVEEDOR";
    public static final String EXTRA_RUCVERI = "com.proyecto.irp.ui.proveedor.EXTRA_RUCVERI";
    public static final String EXTRA_RUCDIV = "com.proyecto.irp.ui.proveedor.EXTRA_RUCDIV";
    public static final String EXTRA_NOMBREPROVEEDOR = "com.proyecto.irp.ui.proveedor.EXTRA_NOMBREPROVEEDOR";
    public static final String EXTRA_TIPOPROVEEDOR = "com.proyecto.irp.ui.proveedor.EXTRA_TIPOPROVEEDOR";

    RadioGroup grupoTipoCliente;
    RadioButton fisica,juridica;
    EditText vrucvericliente,vrucdivcliente,vnombrecliente;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_add);

        inicializacion();
        eventos();

        //INSTANCIAR CLASE PARA CAPTURAR EL DIVIDOR DEL RUC
        final CalculaDivisor calculaDivisor = new CalculaDivisor();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDPROVEEDOR)){
            setTitle("Editar Proveedor");
            vrucvericliente.setText(intent.getStringExtra(EXTRA_RUCVERI));
            vrucdivcliente.setText(intent.getStringExtra(EXTRA_RUCDIV));
            vnombrecliente.setText(intent.getStringExtra(EXTRA_NOMBREPROVEEDOR));
            if (intent.getStringExtra(EXTRA_TIPOPROVEEDOR).trim().equals("0")){
                grupoTipoCliente.check(R.id.rbFisicaP);
            }else{
                grupoTipoCliente.check(R.id.rbJuridicaP);
            }
        }else {
            setTitle("Agregar Proveedor");
        }

        //PARA QUE CALCULE EL CODIGO VERIFICADRO DEL RUC
        vrucvericliente.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String cicargado = vrucvericliente.getText().toString().trim();
                    vrucdivcliente.setText(String.valueOf(calculaDivisor.Calcular_divisor(cicargado,11)));
                }
            }
        });
    }

    private void inicializacion() {
        grupoTipoCliente = findViewById(R.id.rbgTipoProveedor);
        grupoTipoCliente.clearCheck();
        grupoTipoCliente.check(R.id.rbFisicaP);

        fisica = findViewById(R.id.rbFisicaP);
        juridica = findViewById(R.id.rbJuridicaP);

        vrucvericliente = findViewById(R.id.txtRucVeriProveedor);
        vrucdivcliente = findViewById(R.id.txtRucDivProveedor);
        vnombrecliente = findViewById(R.id.txtNombreProveedor);

        btnRegistrar = findViewById(R.id.btnGrabaProveedor);
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnRegistrar.setOnClickListener(this);
    }
    //METODO PARA GRABAR CONTRIBUYENTE
    private void save() {
        String rucveri = vrucvericliente.getText().toString();
        String rucdiv = vrucdivcliente.getText().toString();
        String nombrecliente = vnombrecliente.getText().toString();
        String tipocliente="";
        //TIPO RUC = 0-FISICA  1-JURIDICA
        if (fisica.isChecked()){
            tipocliente="0";
        }else{
            tipocliente="1";
        }
        //String contrasena = .getText().toString();

        if(rucveri.trim().isEmpty()){
            vrucvericliente.setError("Debes ingresar la cedula");
        }else if(rucdiv.trim().isEmpty()){
            vrucdivcliente.setError("Debes ingresar el DIV");
        }else if(nombrecliente.trim().isEmpty()){
            vnombrecliente.setError("Debes ingresar el nombre y apellido");
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_RUCVERI,rucveri);
            data.putExtra(EXTRA_RUCDIV,rucdiv);
            data.putExtra(EXTRA_NOMBREPROVEEDOR,nombrecliente);
            data.putExtra(EXTRA_TIPOPROVEEDOR,tipocliente);

            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDPROVEEDOR,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDPROVEEDOR,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaProveedor:
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
