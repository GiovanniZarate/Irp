package com.proyecto.irp.ui;

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
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.CalculaDivisor;

public class ContribuyenteAddActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.proyecto.irp.ui.EXTRA_ID";
    public static final String EXTRA_CEDULA = "com.proyecto.irp.ui.EXTRA_CEDULA";
    public static final String EXTRA_RUC = "com.proyecto.irp.ui.EXTRA_RUC";
    public static final String EXTRA_NOMBRE = "com.proyecto.irp.ui.EXTRA_NOMBRE";
    public static final String EXTRA_CONTRASENA = "com.proyecto.irp.ui.EXTRA_CONTRASENA";

    EditText vcedula,vruc,vnombre, vcontrasena;
    Button btnRegistrarContribuyente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribuyente_add);
        inicializacion();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        //INSTANCIAR CLASE PARA CAPTURAR EL DIVIDOR DEL RUC
        final CalculaDivisor calculaDivisor = new CalculaDivisor();

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Editar Contribuyente");
            vcedula.setText(intent.getStringExtra(EXTRA_CEDULA));
            vruc.setText(intent.getStringExtra(EXTRA_RUC));
            vnombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            vcontrasena.setText(intent.getStringExtra(EXTRA_CONTRASENA));
        }else {
            setTitle("Agregar Contribuyente");
        }



       /* vcedula.OnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });  */
        //PARA QUE CALCULE EL CODIGO VERIFICADRO DEL RUC
        vcedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String cicargado = vcedula.getText().toString().trim();
                   vruc.setText(cicargado+"-"+String.valueOf(calculaDivisor.Calcular_divisor(cicargado,11)));
                }
            }
        });
    }

    private void inicializacion() {
        vcedula = findViewById(R.id.txtCedulaContribuyente);
        vruc = findViewById(R.id.txtRucContribuyente);
        vnombre = findViewById(R.id.txtNombreContribuyente);
        vcontrasena = findViewById(R.id.txtContrasena);
    }



    //METODO PARA GRABAR CONTRIBUYENTE
    private void saveContribuyente() {
        String cedula = vcedula.getText().toString();
        String ruc = vruc.getText().toString();
        String nombre = vnombre.getText().toString();
        String contrasena = vcontrasena.getText().toString();

        if(cedula.trim().isEmpty()){
            vcedula.setError("Debes ingresar la cedula");
        }else if(ruc.trim().isEmpty()){
            vruc.setError("Debes ingresar el ruc");
        }else if(nombre.trim().isEmpty()){
            vnombre.setError("Debes ingresar el nombre y apellido");
        }else if(contrasena.trim().isEmpty()){
            vcontrasena.setError("Debes ingresar la contraseña");
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_CEDULA,cedula);
            data.putExtra(EXTRA_RUC,ruc);
            data.putExtra(EXTRA_NOMBRE,nombre);
            data.putExtra(EXTRA_CONTRASENA,contrasena);

            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1){
                data.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK,data);
            finish();
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
                saveContribuyente();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
