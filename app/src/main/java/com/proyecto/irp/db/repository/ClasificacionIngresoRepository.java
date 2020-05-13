package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ClasificacionIngresoDao;
import com.proyecto.irp.db.entity.ClasificacionIngreso;


import java.util.List;

public class ClasificacionIngresoRepository {
    private ClasificacionIngresoDao clasificacionIngresoDao;
    private LiveData<List<ClasificacionIngreso>> allClasificacioningreso;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public ClasificacionIngresoRepository(Application application){
        db = AppDatabase.getDatabase(application);
        clasificacionIngresoDao = db.clasificacionIngresoDao();
        allClasificacioningreso = clasificacionIngresoDao.getAllClasificacioningreso();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<ClasificacionIngreso>> getAllClasificacioningreso() {
        return allClasificacioningreso;
    }

    public void insert(ClasificacionIngreso cliente){
        new InsertAsyncTask(clasificacionIngresoDao).execute(cliente);
    }

    public void update(ClasificacionIngreso cliente){
        new UpdateAsyncTask(clasificacionIngresoDao).execute(cliente);
    }

    public void delete(ClasificacionIngreso cliente){
        new DeleteAsyncTask(clasificacionIngresoDao).execute(cliente);
    }



    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<ClasificacionIngreso,Void,Void> {
        private ClasificacionIngresoDao clasificacionIngresoDao;

        private InsertAsyncTask(ClasificacionIngresoDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionIngreso... clasificacionIngresos) {
            clasificacionIngresoDao.insert(clasificacionIngresos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ClasificacionIngreso,Void,Void> {
        private ClasificacionIngresoDao clasificacionIngresoDao;

        private UpdateAsyncTask(ClasificacionIngresoDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionIngreso... clasificacionIngresos) {
            clasificacionIngresoDao.update(clasificacionIngresos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ClasificacionIngreso,Void,Void> {
        private ClasificacionIngresoDao clasificacionIngresoDao;

        private DeleteAsyncTask(ClasificacionIngresoDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(ClasificacionIngreso... clasificacionIngresos) {
            clasificacionIngresoDao.delete(clasificacionIngresos[0]);
            return null;
        }
    }




}
