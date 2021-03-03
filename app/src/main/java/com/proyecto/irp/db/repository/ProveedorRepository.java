package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ClienteDao;
import com.proyecto.irp.db.dao.ProveedorDao;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.Proveedor;

import java.util.List;

public class ProveedorRepository {
    private ProveedorDao proveedorDao;
    private LiveData<List<Proveedor>> allProveedor;
    private List<Proveedor> allProveedorCombo;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public ProveedorRepository(Application application){
        db = AppDatabase.getDatabase(application);
        proveedorDao = db.proveedorDao();
        allProveedor = proveedorDao.getAllProveedor();
        allProveedorCombo = proveedorDao.getAllProveedorCombo();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<Proveedor>> getAllProveedor() {
        return allProveedor;
    }

    public List<Proveedor> getAllProveedorCombo() {
        return allProveedorCombo;
    }

    public void insert(Proveedor proveedor){
        new InsertAsyncTask(proveedorDao).execute(proveedor);
    }

    public void update(Proveedor proveedor){
        new UpdateAsyncTask(proveedorDao).execute(proveedor);
    }

    public void delete(Proveedor proveedor){
        new DeleteAsyncTask(proveedorDao).execute(proveedor);
    }

    //VERIFICA CEDULA
    public int verificaCedula(String cedula){
        return proveedorDao.verificaCedula(cedula);
    }

    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<Proveedor,Void,Void> {
        private ProveedorDao proveedorDao;

        private InsertAsyncTask(ProveedorDao proveedorDao){
            this.proveedorDao = proveedorDao;
        }

        @Override
        protected Void doInBackground(Proveedor... proveedor) {
            proveedorDao.insert(proveedor[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Proveedor,Void,Void> {
        private ProveedorDao proveedorDao;

        private UpdateAsyncTask(ProveedorDao proveedorDao){
            this.proveedorDao = proveedorDao;
        }

        @Override
        protected Void doInBackground(Proveedor... proveedor) {
            proveedorDao.update(proveedor[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Proveedor,Void,Void> {
        private ProveedorDao proveedorDao;

        private DeleteAsyncTask(ProveedorDao proveedorDao){
            this.proveedorDao = proveedorDao;
        }

        @Override
        protected Void doInBackground(Proveedor... proveedor) {
            proveedorDao.delete(proveedor[0]);
            return null;
        }
    }

    //VERIFICA TIPO EGRESO
    public int verificaProveedor(int codigo){
        //ContribuyenteDao contribuyenteDao;
        return proveedorDao.verificaProveedor(codigo);
    }
}
