package com.proyecto.irp.ui.perfilusuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.viewmodel.ContribuyenteViewModel;

import java.util.List;

public class PerfilUsuarioFragment extends Fragment  {

    EditText vcedulaperfil,vrucperfil,vnombreperfil, vcontrasenaperfil;
    Button btnActualizaPerfil;
    private ContribuyenteViewModel contribuyenteViewModel;

    SessionManager managerUsuario;

    int codigocontribu;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario,container,false);

        inicializacion(view);

       // btnActualizaPerfil.setOnClickListener(getActivity());

        //PARA OBTENER LOS DATOS

        String vcedula = managerUsuario.ObtenerDatos().getUsuario();
        final String vcontrasena = managerUsuario.ObtenerDatos().getClave();


        //INSTANCIAR EL VIEW MODEL
        contribuyenteViewModel = ViewModelProviders.of(this).get(ContribuyenteViewModel.class);
        contribuyenteViewModel.verificaLogin(vcedula,vcontrasena).observe(getActivity(), new Observer<List<Contribuyente>>() {
            @Override
            public void onChanged(List<Contribuyente> contribuyentes) {
                for (Contribuyente perfil: contribuyentes){
                    vcedulaperfil.setText(perfil.getDocumento().toString().trim());
                    vrucperfil.setText(perfil.getRuc().toString().trim());
                    vnombreperfil.setText(perfil.getNombres().toString().trim());
                    vcontrasenaperfil.setText(perfil.getContrasena().toString().trim());
                    codigocontribu = perfil.getIdcontribuyente();
                }
            }
        });

        btnActualizaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACTUALIZA EN LA BASE DE DATOS


                Contribuyente contribuyente = new Contribuyente(vcedulaperfil.getText().toString(),vrucperfil.getText().toString(),
                        vnombreperfil.getText().toString(),vcontrasenaperfil.getText().toString());

                contribuyente.setIdcontribuyente(codigocontribu);
                contribuyenteViewModel.update(contribuyente);

                //ACTUALIZA EN EL SHARED PREFERENCES
                //Guardar en un shared los datos del usuario
                managerUsuario.RegistrarUsuario( vcedulaperfil.getText().toString(),vcontrasenaperfil.getText().toString());

                //Snackbar.make(v, "Datos Modificados..", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"Datos Modificados ",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }



    private void inicializacion(View view) {
        vcedulaperfil =  view.findViewById(R.id.txtCedulaPefil);
        vrucperfil = view.findViewById(R.id.txtRucPerfil);
        vnombreperfil = view.findViewById(R.id.txtNombrePerfil);
        vcontrasenaperfil = view.findViewById(R.id.txtContrasenaPerfil);

        btnActualizaPerfil = view.findViewById(R.id.btnActualizaPassword);

        managerUsuario = new SessionManager(getContext());
    }
}
