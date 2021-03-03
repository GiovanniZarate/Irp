package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.TipoComprobanteDao;
import com.proyecto.irp.db.dao.TipoEgresoDao;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.entity.TipoEgreso;

import java.util.List;

public class TipoEgresoRepository {
    private TipoEgresoDao tipoEgresoDao;
    private LiveData<List<TipoEgreso>> alltipoegreso;
    private List<TipoEgreso> tipoegresocombo;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public TipoEgresoRepository(Application application){
        db = AppDatabase.getDatabase(application);
        tipoEgresoDao = db.tipoEgresoDao();
        alltipoegreso = tipoEgresoDao.getAllTipoEgreso();
        tipoegresocombo = tipoEgresoDao.getAllTipoEgresoCombo();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<TipoEgreso>> getAllTipoEgreso() {
        return alltipoegreso;
    }

    public List<TipoEgreso> getAllTipoEgresoCombo() {
        return tipoegresocombo;
    }

    public void insert(TipoEgreso tipoEgreso){
        new InsertAsyncTask(tipoEgresoDao).execute(tipoEgreso);
    }

    public void update(TipoEgreso tipoEgreso){
        new UpdateAsyncTask(tipoEgresoDao).execute(tipoEgreso);
    }

    public void delete(TipoEgreso tipoEgreso){
        new DeleteAsyncTask(tipoEgresoDao).execute(tipoEgreso);
    }



    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<TipoEgreso,Void,Void> {
        private TipoEgresoDao tipoEgresoDao;

        private InsertAsyncTask(TipoEgresoDao tipoEgresoDao){
            this.tipoEgresoDao = tipoEgresoDao;
        }

        @Override
        protected Void doInBackground(TipoEgreso... tipoEgresos) {
            tipoEgresoDao.insert(tipoEgresos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<TipoEgreso,Void,Void> {
        private TipoEgresoDao tipoEgresoDao;

        private UpdateAsyncTask(TipoEgresoDao tipoEgresoDao){
            this.tipoEgresoDao = tipoEgresoDao;
        }

        @Override
        protected Void doInBackground(TipoEgreso... tipoEgresos) {
            tipoEgresoDao.update(tipoEgresos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<TipoEgreso,Void,Void> {
        private TipoEgresoDao tipoEgresoDao;

        private DeleteAsyncTask(TipoEgresoDao tipoEgresoDao){
            this.tipoEgresoDao = tipoEgresoDao;
        }

        @Override
        protected Void doInBackground(TipoEgreso... tipoEgresos) {
            tipoEgresoDao.delete(tipoEgresos[0]);
            return null;
        }
    }

    //VERIFICA TIPO EGRESO
    public int verificaTipoEgreso(int codigo){
        //ContribuyenteDao contribuyenteDao;
        return tipoEgresoDao.verificaTipoEgreso(codigo);
    }




}
