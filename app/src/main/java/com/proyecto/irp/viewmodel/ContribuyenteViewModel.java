package com.proyecto.irp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.db.repository.ContribuyenteRepository;

import java.util.List;

public class ContribuyenteViewModel extends AndroidViewModel {

    private ContribuyenteRepository repository;
    private LiveData<List<Contribuyente>> allContribuyentes;
    //private LiveData<List<Contribuyente>> verificaLogin;

    public ContribuyenteViewModel(@NonNull Application application) {
        super(application);
        repository = new ContribuyenteRepository(application);
        allContribuyentes = repository.getAllContribuyentes();
       // verificaLogin = repository.verificaLogin();
    }

    public void insert(Contribuyente contribuyente){
        repository.insert(contribuyente);
    }

    public void update(Contribuyente contribuyente){
        repository.update(contribuyente);
    }

    public void delete(Contribuyente contribuyente){
        repository.delete(contribuyente);
    }

    public void deleteAllContribuyentes(){
        repository.deleteAllContribuyentes();
    }

    public LiveData<List<Contribuyente>> getAllContribuyentes() {
        return allContribuyentes;
    }

    //verfica cedula

    public int verificaCedula(String cedula){
       return repository.verificaCedula(cedula);
    }

    //PARA VERIFICAR EL ACCESO DEL USUARIO
    public LiveData<List<Contribuyente>> verificaLogin(String userName, String password){
        return repository.verificaLogin(userName,password);
    }
    /*public Contribuyente verificaCedula(){
        //return repository.verificaCedula();
    }*/

}
