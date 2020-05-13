package com.proyecto.irp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.R;
import com.proyecto.irp.ui.fragments.IniFragment;

public class MenuDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    //VARIABLES PARA CARGAR EL FRAGMENT INICIAL
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer);

        toolbar = findViewById(R.id.toolbar_drawer);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationViewDrawer);

        //Establecer evento onclick al navigationview
        navigationView.setNavigationItemSelectedListener(this);

        //PARA QUE EL MENU SE MUESTRE
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //CARGAR FRAGMENT INICIAL
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor_drawer,new IniFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //PARA CERRAR EL DRAWER AL SELECCIONAR UN ITEM
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()){
            case R.id.home:
                //CARGAR FRAGMENT INICIAL
                //fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.contenedor_drawer,new IniFragment());
                //fragmentTransaction.commit();
                fragmentManager.beginTransaction().replace(R.id.contenedor_drawer,new IniFragment()).commit();
                break;
            case R.id.libroventa:
               // Intent i = new Intent(MenuDrawerActivity.this, MenuRefVentaActivity.class);
                //startActivity(i);
                break;
            case R.id.referenciaVenta:
                Intent i = new Intent(MenuDrawerActivity.this, MenuRefVentaActivity.class);
                startActivity(i);
                break;
            case R.id.cerrar_sesion:
                 cerrarSesion();
                break;

        }
        return false;
    }

    //PARA VALIDAR LA SALIDA DEL SISTEMA MUESTRE UN MENSAJE
    //Controla la pulsación del botón Atrás

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            salirApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cerrarSesion(){
        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("Salir")
                .setMessage("¿Desea Cerrar Sesión?")
                .setIcon(R.drawable.ic_exit_to_app)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //PARA EL SALIR debe estar en el otro activity
                        SessionManager managerUsuario = new SessionManager(getApplicationContext());
                        managerUsuario.logout();
                        dialog.dismiss();
                        finish();
                        Intent intent = new Intent(MenuDrawerActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
              .show();
        }

    private void salirApp(){
        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("Salir")
                .setMessage("¿Desea Salir de IRP?")
                .setIcon(R.drawable.ic_exit_to_app)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                       // dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
        /* private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea Cerrar Sesión?")
                    .setTitle("Salir")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //PARA EL SALIR debe estar en el otro activity
                SessionManager managerUsuario = new SessionManager(getApplicationContext());
                managerUsuario.logout();
                dialog.dismiss();
                finish();
                Intent intent = new Intent(MenuDrawerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
*/

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
