package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ClienteDao;
import com.proyecto.irp.db.entity.Cliente;

import java.util.List;

public class ClienteRepository {
    private ClienteDao clienteDao;
    private LiveData<List<Cliente>> allClientes;
    private List<Cliente> clientecombo;

    AppDatabase db;

    //METODO CONSTRUCTOR
    public ClienteRepository(Application application){
        db = AppDatabase.getDatabase(application);
        clienteDao = db.clienteDao();
        allClientes = clienteDao.getAllClientes();
        clientecombo = clienteDao.getAllClienteCombo();
    }


    //EVENTOS A REALIZAR CON LA CLASE: INSERT, UPDATE, DELETE, LISTAR, ETC.
    public LiveData<List<Cliente>> getAllClientes() {
        return allClientes;
    }

    public List<Cliente> getAllClienteCombo() {
        return clientecombo;
    }

    public void insert(Cliente cliente){
        new InsertAsyncTask(clienteDao).execute(cliente);
    }

    public void update(Cliente cliente){
        new UpdateAsyncTask(clienteDao).execute(cliente);
    }

    public void delete(Cliente cliente){
        new DeleteAsyncTask(clienteDao).execute(cliente);
    }

    //VERIFICA CEDULA
    public int verificaCedula(String cedula){
        return clienteDao.verificaCedula(cedula);
    }

    //CLASES ASYNCTACSK
    private static class InsertAsyncTask extends AsyncTask<Cliente,Void,Void> {
        private ClienteDao clienteDao;

        private InsertAsyncTask(ClienteDao clienteDao){
            this.clienteDao = clienteDao;
        }

        @Override
        protected Void doInBackground(Cliente... cliente) {
            clienteDao.insert(cliente[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Cliente,Void,Void> {
        private ClienteDao clienteDao;

        private UpdateAsyncTask(ClienteDao clienteDao){
            this.clienteDao = clienteDao;
        }

        @Override
        protected Void doInBackground(Cliente... cliente) {
            clienteDao.update(cliente[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Cliente,Void,Void> {
        private ClienteDao clienteDao;

        private DeleteAsyncTask(ClienteDao clienteDao){
            this.clienteDao = clienteDao;
        }

        @Override
        protected Void doInBackground(Cliente... cliente) {
            clienteDao.delete(cliente[0]);
            return null;
        }
    }

    //VERIFICA TIPO EGRESO
    public int verificaCliente(int codigo){
        //ContribuyenteDao contribuyenteDao;
        return clienteDao.verificaCliente(codigo);
    }




}
