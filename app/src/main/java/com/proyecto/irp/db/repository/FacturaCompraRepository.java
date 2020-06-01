package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.FacturaCompraDao;
import com.proyecto.irp.db.dao.FacturaVentaDao;
import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.entity.ReporteLibroCompra;
import com.proyecto.irp.db.entity.ReporteLibroVenta;

import java.util.List;

public class FacturaCompraRepository {

    private FacturaCompraDao facturaCompraDao;
    private LiveData<List<Facturacompra>> allFacturacompra;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public FacturaCompraRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        facturaCompraDao = db.facturaCompraDao();
        //allFacturaventa = facturaVentaDao.getAllFacturaventa(codcontribu);
    }

    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<Facturacompra>> getAllFacturaCompra(int codcontribu, int codejercicio) {
        return facturaCompraDao.getAllFacturacompra(codcontribu,codejercicio);
    }

    //MOSTRAR EL TOTAL DE VENTAS PARA GRAFICO
    public List<ReporteLibroCompra> getLibroCompra(int contribu, int ejercicio, String desde, String hasta) {
        return facturaCompraDao.getLibroCompra(contribu,ejercicio,desde,hasta);
    }

    //MOSTRAR EL TOTAL DE VENTAS PARA GRAFICO
    //public LiveData<List<EstadisticaVentas>> getTotalCompra(int codcontribu, int codejercicio) {
    //    return facturaCompraDao.get(codcontribu,codejercicio);
    //}

    public void insert(Facturacompra facturacompra){
        new InsertAsyncTask(facturaCompraDao).execute(facturacompra);
    }

    public void update(Facturacompra facturacompra){
        new UpdateAsyncTask(facturaCompraDao).execute(facturacompra);
    }

    public void delete(Facturacompra facturacompra){
        new DeleteAsyncTask(facturaCompraDao).execute(facturacompra);
    }


    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<Facturacompra,Void,Void> {
        private FacturaCompraDao facturaCompraDao;

        private InsertAsyncTask(FacturaCompraDao facturaCompraDao){
            this.facturaCompraDao = facturaCompraDao;
        }

        @Override
        protected Void doInBackground(Facturacompra... facturacompras) {
            facturaCompraDao.insert(facturacompras[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Facturacompra,Void,Void> {
        private FacturaCompraDao facturaCompraDao;

        private UpdateAsyncTask(FacturaCompraDao facturaCompraDao){
            this.facturaCompraDao = facturaCompraDao;
        }

        @Override
        protected Void doInBackground(Facturacompra... facturacompras) {
            facturaCompraDao.update(facturacompras[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Facturacompra,Void,Void> {
        private FacturaCompraDao facturaCompraDao;

        private DeleteAsyncTask(FacturaCompraDao facturaCompraDao){
            this.facturaCompraDao = facturaCompraDao;
        }

        @Override
        protected Void doInBackground(Facturacompra... facturacompras) {
            facturaCompraDao.delete(facturacompras[0]);
            return null;
        }
    }

}
