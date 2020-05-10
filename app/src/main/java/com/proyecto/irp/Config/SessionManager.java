package com.proyecto.irp.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private Context context;
    private SharedPreferences contenedor;

    public SessionManager(Context context) {
        contenedor= context.getSharedPreferences("login",context.MODE_PRIVATE);
    }

    //metodo para registrar
    public boolean RegistrarUsuario(String usuario,String clave){
        SharedPreferences.Editor edit = contenedor.edit();
        edit.putBoolean("login",true);
        edit.putString("usuario",usuario);
        edit.putString("clave",clave);
        edit.commit();
        return true;
    }

    //METODO PARA DESLOGEAR - si es necesario o no presentar la pantalla de login
    public boolean isLogin(){
        return  contenedor.getBoolean("login",false);
    }

    //METODO PARA
    public SessionUsuario ObtenerDatos(){
        return new SessionUsuario(contenedor.getString("usuario",""),contenedor.getString("clave",""));
    }

    //para salir y limpiar el shared
    public void logout(){
        SharedPreferences.Editor edit = contenedor.edit();
        edit.clear();
        edit.commit();
    }
}
