package com.proyecto.irp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.CalculaDivisor;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.viewmodel.ContribuyenteViewModel;

public class CrearcuentaActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvLoginVolver;
    Button btnRegistrame;
    EditText vcedula,vruc,vnombre, vcontrasena;

    private ContribuyenteViewModel contribuyenteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta);
        //QUITAR EL ACTION BAR
        getSupportActionBar().hide();

        //INSTANCIAR CLASE PARA CAPTURAR EL DIVIDOR DEL RUC
        final CalculaDivisor calculaDivisor = new CalculaDivisor();

        //INSTANCIAR EL VIEW MODEL
        contribuyenteViewModel = ViewModelProviders.of(this).get(ContribuyenteViewModel.class);
       /* contribuyenteViewModel.getAllContribuyentes().observe(this, new Observer<List<Contribuyente>>() {
            @Override
            public void onChanged(List<Contribuyente> contribuyentes) {
                //update RecyclerView
                //Toast.makeText(UsuarioActivity.this,"onChange", Toast.LENGTH_LONG).show();
                adapter.submitList(contribuyentes);
            }
        });*/
        inicializacion();
        eventos();

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
        tvLoginVolver = findViewById(R.id.tvLoginVolver);
        btnRegistrame = findViewById(R.id.btnRegistrame);
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        tvLoginVolver.setOnClickListener(this);
        btnRegistrame.setOnClickListener(this);
    }
    //opcion de los botones
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tvLoginVolver:
                volverLogin();
            case R.id.btnRegistrame:
                registrar();
        }
    }

    private void volverLogin() {
        //Intent i = new Intent(CrearcuentaActivity.this, MainActivity.class);
        Intent i = new Intent(CrearcuentaActivity.this, ContribuyenteActivity.class);
        startActivity(i);
        finish();
    }

    private void registrar(){
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
            //carga el pojo
            Contribuyente contribuyente = new Contribuyente(cedula,ruc,nombre,contrasena);
            //verifica si existe ya la cuenta por Cedula
            if (contribuyenteViewModel.verificaCedula(contribuyente.getDocumento()) == 0){
                //INSERTA EN LA BD SQLITE
                contribuyenteViewModel.insert(contribuyente);
                Toast.makeText(this,"Contribuyente Registrado con exito", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this,"Contribuyente Ya está registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
