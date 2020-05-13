package com.proyecto.irp.ui.cliente;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.repository.ClienteRepository;

import java.util.List;


//ESTE ES UN VIEWMODEL GENERADO AUTOMATICAMENTE EN VEZ DE EXTENDER A ViewModel - AndroidViewModel
public class ClienteViewModel extends AndroidViewModel {

    //private MutableLiveData<String> mText;

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private ClienteRepository repository;
    private LiveData<List<Cliente>> allClientes;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new ClienteRepository(application);
        allClientes = repository.getAllClientes();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<Cliente>> getAllClientes() {
        return allClientes;
    }

    public void insert(Cliente cliente){
        repository.insert(cliente);
    }

    public void update(Cliente cliente){
        repository.update(cliente);
    }

    public void delete(Cliente cliente){
        repository.delete(cliente);
    }

    //verfica cedula
    public int verificaCedula(String cedula){
        return repository.verificaCedula(cedula);
    }


   // public LiveData<String> getText() {
    //    return mText;
   // }
}