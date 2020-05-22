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
    public boolean RegistrarUsuario(String usuario,String clave,String ruc,
                                    String nombrecontribuyente,int anho,
                                    int idejercicio,int idcontribuyente){
        SharedPreferences.Editor edit = contenedor.edit();
        edit.putBoolean("login",true);
        edit.putString("usuario",usuario);
        edit.putString("clave",clave);
        edit.putString("ruc",ruc);
        edit.putString("nombrecontribuyente",nombrecontribuyente);
        edit.putInt("anho",anho);
        edit.putInt("idejercicio",idejercicio);
        edit.putInt("idcontribuyente",idcontribuyente);
        edit.commit();
        return true;
    }

    //METODO PARA DESLOGEAR - si es necesario o no presentar la pantalla de login
    public boolean isLogin(){
        return  contenedor.getBoolean("login",false);
    }

    //METODO PARA
    public SessionUsuario ObtenerDatos(){
        return new SessionUsuario(contenedor.getString("usuario",""),
                contenedor.getString("clave",""),
                contenedor.getString("ruc",""),
                contenedor.getString("nombrecontribuyente",""),
                contenedor.getInt("anho",0),
                contenedor.getInt("idejercicio",0),
                contenedor.getInt("idcontribuyente",0));
    }

    //para salir y limpiar el shared
    public void logout(){
        SharedPreferences.Editor edit = contenedor.edit();
        edit.clear();
        edit.commit();
    }
}
