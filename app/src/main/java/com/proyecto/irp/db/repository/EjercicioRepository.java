package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.EjercicioDao;
import com.proyecto.irp.db.entity.Ejercicio;

import java.util.List;

public class EjercicioRepository {
    private EjercicioDao ejercicioDao;
    private LiveData<List<Ejercicio>> allEjercicios;
    //private Ejercicio ejercicioActual;

    public EjercicioRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        ejercicioDao = db.getEjercicioDao();
        allEjercicios = ejercicioDao.getAllEjerciciosLV();
    }

    //Metodo para traer datos
    public LiveData<List<Ejercicio>> getAll() {return allEjercicios;}

    //METODO PARA INSERTAR DATOS
    public void insert(Ejercicio ejercicio){
        new insertAsyncTask(ejercicioDao).execute(ejercicio);
    }

    //VERIFICA EJERCICIO ACTUAL
    public int verificaEjercicio(int anho){
        return ejercicioDao.verificaAnho(anho);
    }


    //TRAE EJERCICIO ACTUAL
    public Ejercicio traeEjercicioActual(int anho){
        return ejercicioDao.TraeEjerecicioActual(anho);
    }

    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<Ejercicio,Void,Void> {
        private EjercicioDao ejercicioDao;

        private InsertAsyncTask(EjercicioDao ejercicioDao){
            this.ejercicioDao = ejercicioDao;
        }

        @Override
        protected Void doInBackground(Ejercicio... ejercicios) {
            ejercicioDao.insert(ejercicios[0]);
            return null;
        }
    }


    //PARA INSERTAR EN LA BASE DE DATOS
    private static class insertAsyncTask extends AsyncTask<Ejercicio ,Void, Void> {
        private EjercicioDao ejercicioDaoAsyncTask;

        insertAsyncTask(EjercicioDao dao){
            ejercicioDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Ejercicio... ejercicios) {
            ejercicioDaoAsyncTask.insert(ejercicios[0]);
            return null;
        }
    }

    public void update(Ejercicio ejercicio){new updateAsyncTask(ejercicioDao).execute(ejercicio); }
    //PARA MODIFICAR EN LA BASE DE DATOS
    private static class updateAsyncTask extends AsyncTask<Ejercicio ,Void, Void> {
        private EjercicioDao ejercicioDaoAsyncTask;

        updateAsyncTask(EjercicioDao dao){
            ejercicioDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Ejercicio... ejercicios) {
            ejercicioDaoAsyncTask.update(ejercicios[0]);
            return null;
        }
    }


}
