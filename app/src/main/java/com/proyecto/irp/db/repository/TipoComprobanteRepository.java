package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ClasificacionIngresoDao;
import com.proyecto.irp.db.dao.TipoComprobanteDao;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.TipoComprobante;

import java.util.List;

public class TipoComprobanteRepository {
    private TipoComprobanteDao clasificacionIngresoDao;
    private LiveData<List<TipoComprobante>> allClasificacioningreso;
    private List<TipoComprobante> tipoComprobantecombo;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public TipoComprobanteRepository(Application application){
        db = AppDatabase.getDatabase(application);
        clasificacionIngresoDao = db.tipoComprobanteDao();
        allClasificacioningreso = clasificacionIngresoDao.getAllTipoComprobante();
        tipoComprobantecombo = clasificacionIngresoDao.getAllTipoComprobanteCombo();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<TipoComprobante>> getAllTipoComprobante() {
        return allClasificacioningreso;
    }

    public List<TipoComprobante> getAllTipoComprobanteCombo() {
        return tipoComprobantecombo;
    }

    public void insert(TipoComprobante cliente){
        new InsertAsyncTask(clasificacionIngresoDao).execute(cliente);
    }

    public void update(TipoComprobante cliente){
        new UpdateAsyncTask(clasificacionIngresoDao).execute(cliente);
    }

    public void delete(TipoComprobante cliente){
        new DeleteAsyncTask(clasificacionIngresoDao).execute(cliente);
    }



    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<TipoComprobante,Void,Void> {
        private TipoComprobanteDao clasificacionIngresoDao;

        private InsertAsyncTask(TipoComprobanteDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(TipoComprobante... clasificacionIngresos) {
            clasificacionIngresoDao.insert(clasificacionIngresos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<TipoComprobante,Void,Void> {
        private TipoComprobanteDao clasificacionIngresoDao;

        private UpdateAsyncTask(TipoComprobanteDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(TipoComprobante... clasificacionIngresos) {
            clasificacionIngresoDao.update(clasificacionIngresos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<TipoComprobante,Void,Void> {
        private TipoComprobanteDao clasificacionIngresoDao;

        private DeleteAsyncTask(TipoComprobanteDao clasificacionIngresoDao){
            this.clasificacionIngresoDao = clasificacionIngresoDao;
        }

        @Override
        protected Void doInBackground(TipoComprobante... clasificacionIngresos) {
            clasificacionIngresoDao.delete(clasificacionIngresos[0]);
            return null;
        }
    }

    //VERIFICA TIPO EGRESO
    public int verificaTipocomprobante(int codigo){
        //ContribuyenteDao contribuyenteDao;
        return clasificacionIngresoDao.verificaTipocomprobante(codigo);
    }




}
