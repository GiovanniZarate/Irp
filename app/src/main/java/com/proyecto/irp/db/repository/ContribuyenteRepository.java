package com.proyecto.irp.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.AppDatabase;
import com.proyecto.irp.db.dao.ContribuyenteDao;
import com.proyecto.irp.db.entity.Contribuyente;

import java.util.List;

public class ContribuyenteRepository {
    private ContribuyenteDao contribuyenteDao;
    private LiveData<List<Contribuyente>> allContribuyentes;

    String userName;
    String password;

    AppDatabase db;
    //private LiveData<List<Contribuyente>> Contriconcedula;

    public ContribuyenteRepository(Application application){
         db = AppDatabase.getDatabase(application);
        contribuyenteDao = db.contribuyenteDao();
        allContribuyentes = contribuyenteDao.getAllContribuyentes();
    }

    public void insert(Contribuyente contribuyente){
        new InsertContribuyenteAsyncTask(contribuyenteDao).execute(contribuyente);
    }

    public void update(Contribuyente contribuyente){
        new UpdateContribuyenteAsyncTask(contribuyenteDao).execute(contribuyente);
    }

    public void delete(Contribuyente contribuyente){
        new DeleteContribuyenteAsyncTask(contribuyenteDao).execute(contribuyente);
    }

    public void deleteAllContribuyentes(){
        new DeleteAllContribuyentesAsyncTask(contribuyenteDao).execute();
    }



    public LiveData<List<Contribuyente>> getAllContribuyentes() {
        return allContribuyentes;
    }

    //PARA VERIFICAR EL ACCESO LOGIN
    public LiveData<List<Contribuyente>> verificaLogin(String userName, String password){
        return db.contribuyenteDao().verificaLogin(userName, password);
        //return feelingFitDatabase.getUsersDao().verifyUserLogin(userName, password);
    }

    //VERIFICA CEDULA
    public int verificaCedula(String cedula){
         //ContribuyenteDao contribuyenteDao;
         return contribuyenteDao.verificaCedula(cedula);
    }

    /*private static class verificaCedulaAsyncTask extends AsyncTask<Contribuyente,Void,Void> {
        private ContribuyenteDao contribuyenteDao;

        private verificaCedulaAsyncTask(ContribuyenteDao contribuyenteDao){
            this.contribuyenteDao = contribuyenteDao;
        }

        @Override
        protected Void doInBackground(Contribuyente... contribuyentes) {
            contribuyenteDao.verificaCedula(contribuyentes[0].getDocumento());
            return null;
        }
    }*/


    //CLASES ASYNCTACSK
    private static class InsertContribuyenteAsyncTask extends AsyncTask<Contribuyente,Void,Void> {
        private ContribuyenteDao contribuyenteDao;

        private InsertContribuyenteAsyncTask(ContribuyenteDao contribuyenteDao){
            this.contribuyenteDao = contribuyenteDao;
        }

        @Override
        protected Void doInBackground(Contribuyente... contribuyentes) {
            contribuyenteDao.insert(contribuyentes[0]);
            return null;
        }
    }

    private static class UpdateContribuyenteAsyncTask extends AsyncTask<Contribuyente,Void,Void> {
        private ContribuyenteDao contribuyenteDao;

        private UpdateContribuyenteAsyncTask(ContribuyenteDao contribuyenteDao){
            this.contribuyenteDao = contribuyenteDao;
        }

        @Override
        protected Void doInBackground(Contribuyente... contribuyentes) {
            contribuyenteDao.update(contribuyentes[0]);
            return null;
        }
    }

    private static class DeleteContribuyenteAsyncTask extends AsyncTask<Contribuyente,Void,Void> {
        private ContribuyenteDao contribuyenteDao;

        private DeleteContribuyenteAsyncTask(ContribuyenteDao contribuyenteDao){
            this.contribuyenteDao = contribuyenteDao;
        }

        @Override
        protected Void doInBackground(Contribuyente... contribuyentes) {
            contribuyenteDao.delete(contribuyentes[0]);
            return null;
        }
    }

    private static class DeleteAllContribuyentesAsyncTask extends AsyncTask<Void,Void,Void> {
        private ContribuyenteDao contribuyenteDao;

        private DeleteAllContribuyentesAsyncTask(ContribuyenteDao contribuyenteDao){
            this.contribuyenteDao = contribuyenteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contribuyenteDao.deleteAllContribuyentes();
            return null;
        }
    }
}
