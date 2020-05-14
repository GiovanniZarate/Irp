package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ClasificacionEgresoDao;
import com.proyecto.irp.db.dao.TipoEgresoDao;
import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.db.entity.TipoEgreso;

import java.util.List;

public class ClasificacionEgresoRepository {
    private ClasificacionEgresoDao clasificacionEgresoDao;
    private LiveData<List<ClasificacionEgreso>> allClasificacionEgreso;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public ClasificacionEgresoRepository(Application application){
        db = AppDatabase.getDatabase(application);
        clasificacionEgresoDao = db.clasificacionEgresoDao();
        allClasificacionEgreso = clasificacionEgresoDao.getAllClasificacionegreso();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionEgreso() {
        return allClasificacionEgreso;
    }

    public void insert(ClasificacionEgreso clasificacionEgreso){
        new InsertAsyncTask(clasificacionEgresoDao).execute(clasificacionEgreso);
    }

    public void update(ClasificacionEgreso clasificacionEgreso){
        new UpdateAsyncTask(clasificacionEgresoDao).execute(clasificacionEgreso);
    }

    public void delete(ClasificacionEgreso clasificacionEgreso){
        new DeleteAsyncTask(clasificacionEgresoDao).execute(clasificacionEgreso);
    }



    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<ClasificacionEgreso,Void,Void> {
        private ClasificacionEgresoDao clasificacionEgresoDao;

        private InsertAsyncTask(ClasificacionEgresoDao clasificacionEgresoDao){
            this.clasificacionEgresoDao = clasificacionEgresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionEgreso... clasificacionEgresos) {
            clasificacionEgresoDao.insert(clasificacionEgresos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasificacionEgreso,Void,Void> {
        private ClasificacionEgresoDao clasificacionEgresoDao;

        private UpdateAsyncTask(ClasificacionEgresoDao clasificacionEgresoDao){
            this.clasificacionEgresoDao = clasificacionEgresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionEgreso... clasificacionEgresos) {
            clasificacionEgresoDao.update(clasificacionEgresos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasificacionEgreso,Void,Void> {
        private ClasificacionEgresoDao clasificacionEgresoDao;

        private DeleteAsyncTask(ClasificacionEgresoDao clasificacionEgresoDao){
            this.clasificacionEgresoDao = clasificacionEgresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionEgreso... clasificacionEgresos) {
            clasificacionEgresoDao.delete(clasificacionEgresos[0]);
            return null;
        }
    }




}
