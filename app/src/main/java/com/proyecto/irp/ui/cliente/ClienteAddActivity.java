package com.proyecto.irp.ui.cliente;

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

public class ClienteAddActivity extends AppCompatActivity implements View.OnClickListener{
    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDCLIENTE = "com.proyecto.irp.ui.cliente.EXTRA_IDCLIENTE";
    public static final String EXTRA_RUCVERI = "com.proyecto.irp.ui.cliente.EXTRA_RUCVERI";
    public static final String EXTRA_RUCDIV = "com.proyecto.irp.ui.cliente.EXTRA_RUCDIV";
    public static final String EXTRA_NOMBRECLIENTE = "com.proyecto.irp.ui.cliente.EXTRA_NOMBRECLIENTE";
    public static final String EXTRA_TIPOCLIENTE = "com.proyecto.irp.ui.cliente.EXTRA_TIPOCLIENTE";

    RadioGroup grupoTipoCliente;
    RadioButton fisica,juridica;
    EditText vrucvericliente,vrucdivcliente,vnombrecliente;
    Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_add);
        inicializacion();
        eventos();

        //INSTANCIAR CLASE PARA CAPTURAR EL DIVIDOR DEL RUC
        final CalculaDivisor calculaDivisor = new CalculaDivisor();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDCLIENTE)){
            setTitle("Editar Cliente");
            vrucvericliente.setText(intent.getStringExtra(EXTRA_RUCVERI));
            vrucdivcliente.setText(intent.getStringExtra(EXTRA_RUCDIV));
            vnombrecliente.setText(intent.getStringExtra(EXTRA_NOMBRECLIENTE));
            if (intent.getStringExtra(EXTRA_TIPOCLIENTE).trim().equals("0")){
                grupoTipoCliente.check(R.id.rbFisica);
            }else{
                grupoTipoCliente.check(R.id.rbJuridica);
            }
        }else {
            setTitle("Agregar Cliente");
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
        grupoTipoCliente = findViewById(R.id.rbgTipoCliente);
        grupoTipoCliente.clearCheck();
        grupoTipoCliente.check(R.id.rbFisica);

        fisica = findViewById(R.id.rbFisica);
        juridica = findViewById(R.id.rbJuridica);

        vrucvericliente = findViewById(R.id.txtRucVeriCliente);
        vrucdivcliente = findViewById(R.id.txtRucDivCliente);
        vnombrecliente = findViewById(R.id.txtNombreCliente);

        btnRegistrar = findViewById(R.id.btnGrabaCliente);
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
            data.putExtra(EXTRA_NOMBRECLIENTE,nombrecliente);
            data.putExtra(EXTRA_TIPOCLIENTE,tipocliente);

            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDCLIENTE,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDCLIENTE,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaCliente:
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
