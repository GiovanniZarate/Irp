package com.proyecto.irp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.proyecto.irp.Config.Constantes;
import com.proyecto.irp.db.dao.ClasificacionIngresoDao;
import com.proyecto.irp.db.dao.ClienteDao;
import com.proyecto.irp.db.dao.ContribuyenteDao;
import com.proyecto.irp.db.dao.EjercicioDao;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.db.entity.Ejercicio;


//paso 1 - LOS PERMISOS PARA ACCEDER A LAS ENTIDADES
@Database(entities = {Ejercicio.class, Contribuyente.class, Cliente.class, ClasificacionIngreso.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EjercicioDao getEjercicioDao();
    public abstract ContribuyenteDao contribuyenteDao();
    public abstract ClienteDao clienteDao();
    public abstract ClasificacionIngresoDao clasificacionIngresoDao();

    //Para crear instancia de la base de datos
    //Verificar si no existe crear de caso contrario
    private static  AppDatabase INSTANCE;

    //EL METODO QUE VA A VERIFICAR SI YA EXISTE LA INSTANCIA A LA BASE DE DATOS
    public static synchronized AppDatabase getDatabase(Context context){
        if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constantes.BD_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)  //PARA INSERTAR LOS DATOS DE PREUBA
                            .build();
        }
        //caso contrario ya existe la instancia y retorna el mismo
        return INSTANCE;
    }

    //PARA INSERTAR DATOS DE PRUEBA AL INICIAR
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private ContribuyenteDao contribuyenteDao;

        private PopulateDbAsyncTask(AppDatabase db){
            contribuyenteDao = db.contribuyenteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            contribuyenteDao.insert(new Contribuyente("4203593","4203593-7","Carlos Giovanni Zarate Ruiz","123"));
            contribuyenteDao.insert(new Contribuyente("757522","757522-7","Ignacio Zarate","123"));
            contribuyenteDao.insert(new Contribuyente("5331231","5331231-7","Juana Carolina Fariña Silvero","123"));

            return null;
        }
    }

}
