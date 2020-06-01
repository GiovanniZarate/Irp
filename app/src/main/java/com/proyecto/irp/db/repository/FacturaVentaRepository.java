package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.FacturaVentaDao;
import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.entity.ReporteLibroVenta;

import java.util.Date;
import java.util.List;

public class FacturaVentaRepository {

    private FacturaVentaDao facturaVentaDao;
    private LiveData<List<Facturaventa>> allFacturaventa;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public FacturaVentaRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        facturaVentaDao = db.facturaVentaDao();
        //allFacturaventa = facturaVentaDao.getAllFacturaventa(codcontribu);
    }

    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<Facturaventa>> getAllFacturaVenta(int codcontribu, int codejercicio) {
        return facturaVentaDao.getAllFacturaventa(codcontribu,codejercicio);
    }

    //MOSTRAR EL TOTAL DE VENTAS PARA GRAFICO
    public LiveData<List<EstadisticaVentas>> getTotalVenta(int codcontribu, int codejercicio) {
        return facturaVentaDao.getTotalVenta(codcontribu,codejercicio);
    }

    //MOSTRAR EL TOTAL DE VENTAS PARA GRAFICO
    public List<ReporteLibroVenta> getLibroVenta(int contribu, int ejercicio, String desde, String hasta) {
        return facturaVentaDao.getLibroVenta(contribu,ejercicio,desde,hasta);
    }

    public void insert(Facturaventa facturaventa){
        new InsertAsyncTask(facturaVentaDao).execute(facturaventa);
    }

    public void update(Facturaventa facturaventa){
        new UpdateAsyncTask(facturaVentaDao).execute(facturaventa);
    }

    public void delete(Facturaventa facturaventa){
        new DeleteAsyncTask(facturaVentaDao).execute(facturaventa);
    }


    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<Facturaventa,Void,Void> {
        private FacturaVentaDao facturaVentaDao;

        private InsertAsyncTask(FacturaVentaDao facturaVentaDao){
            this.facturaVentaDao = facturaVentaDao;
        }

        @Override
        protected Void doInBackground(Facturaventa... facturaventas) {
            facturaVentaDao.insert(facturaventas[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Facturaventa,Void,Void> {
        private FacturaVentaDao facturaVentaDao;

        private UpdateAsyncTask(FacturaVentaDao facturaVentaDao){
            this.facturaVentaDao = facturaVentaDao;
        }

        @Override
        protected Void doInBackground(Facturaventa... facturaventas) {
            facturaVentaDao.update(facturaventas[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Facturaventa,Void,Void> {
        private FacturaVentaDao facturaVentaDao;

        private DeleteAsyncTask(FacturaVentaDao facturaVentaDao){
            this.facturaVentaDao = facturaVentaDao;
        }

        @Override
        protected Void doInBackground(Facturaventa... facturaventas) {
            facturaVentaDao.delete(facturaventas[0]);
            return null;
        }
    }

}
