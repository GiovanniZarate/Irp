package com.proyecto.irp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.R;
import com.proyecto.irp.db.dao.EjercicioDao;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.db.entity.Ejercicio;
import com.proyecto.irp.viewmodel.ContribuyenteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText cedula, contrasena;
    Button btnLogin;
    TextView tvCrearCuenta;

    private ContribuyenteViewModel contribuyenteViewModel;
   // private EjercicioDao ejercicioDao;

    AppDatabase db;
    SessionManager managerUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  db = AppDatabase.getDatabase(getApplicationContext());
        //QUITAR EL ACTION BAR
        getSupportActionBar().hide();
        inicializacion();
        eventos();

        //VERIFICA SI YA ESTA LOGEANDO LLEVA A LA PANTALLA PRINCIPAL
        if (managerUsuario.isLogin()){
            Intent intent = new Intent(MainActivity.this, MenuDrawerActivity.class);
            startActivity(intent);
            finish();


            //PARA OBTENER LOS DATOS
            //SessionManager managerUsuario = new SessionManager(getApplicationContext);
           // managerUsuario.ObtenerDatos().getUsuario();
        }

        //INSTANCIAR EL VIEW MODEL
        contribuyenteViewModel = ViewModelProviders.of(this).get(ContribuyenteViewModel.class);


        //DEVOLVER LA CANTIDAD DE REGISTRO QUE EXISTE EN LA TABLA
      //  int cantidad = db.getEjercicioDao().count();
      //  Toast.makeText(this,"Existen: "+cantidad+ " de registros",Toast.LENGTH_SHORT).show();

    }

    private void inicializacion() {
        cedula = findViewById(R.id.txtCedula);
        contrasena = findViewById(R.id.txtContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        tvCrearCuenta = findViewById(R.id.tvRegistrarContribuyente);
        managerUsuario = new SessionManager(getApplicationContext());
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnLogin.setOnClickListener(this);
        tvCrearCuenta.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnLogin:
                Login();
                break;
            case R.id.tvRegistrarContribuyente:
                llamarRegistrar();
        }
    }

    private void Login(){

        final String vcedula = cedula.getText().toString();
        final String vcontrasena = contrasena.getText().toString();

        if(vcedula.isEmpty()){
            cedula.setError("Debes ingresar la cedula");
        }else if(vcontrasena.isEmpty()){
            contrasena.setError("Debes ingresar la contraseña");
        }else{

            contribuyenteViewModel.verificaLogin(vcedula,vcontrasena).observe(MainActivity.this, new Observer<List<Contribuyente>>() {
                @Override
                public void onChanged(List<Contribuyente> contribuyentes) {
                    //Toast.makeText(MainActivity.this,"entra texto"+vcedula+ " pas "+vcontrasena,Toast.LENGTH_LONG).show();
                    if (contribuyentes.isEmpty()){
                        Toast.makeText(MainActivity.this, "Cedula o Contraseña Invalido..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Contribuyente user: contribuyentes){
                        if (user.getDocumento().equals(vcedula) && user.getContrasena().equals(vcontrasena)) {
                            //Guardar en un shared los datos del usuario
                            managerUsuario.RegistrarUsuario(user.getDocumento(),user.getContrasena());
                            Intent intent = new Intent(MainActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    }
    }

    private void llamarRegistrar(){
       // Intent i = new Intent(MainActivity.this, UsuarioActivity.class);
        Intent i = new Intent(MainActivity.this, CrearcuentaActivity.class);
        startActivity(i);
        //finish();
    }

    //llamamos a la bd
       /*

ESTABA EN EL ONCRATE
db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries()
                .build();*/


}

