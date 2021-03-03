package com.proyecto.irp.ui.proveedor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.Proveedor;
import com.proyecto.irp.db.repository.ProveedorRepository;

import java.util.List;

public class ProveedorViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private ProveedorRepository repository;
    private LiveData<List<Proveedor>> allDatos;
    private List<Proveedor> allDatoscombo;

    public ProveedorViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new ProveedorRepository(application);
        allDatos = repository.getAllProveedor();
        allDatoscombo = repository.getAllProveedorCombo();
    }

    public List<Proveedor> getAllProveedorCombo() {
        return allDatoscombo;
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<Proveedor>> getAllProveedores() {
        return allDatos;
    }

    public void insert(Proveedor proveedor){
        repository.insert(proveedor);
    }

    public void update(Proveedor proveedor){
        repository.update(proveedor);
    }

    public void delete(Proveedor proveedor){
        repository.delete(proveedor);
    }

    //verfica cedula
    public int verificaCedula(String cedula){
        return repository.verificaCedula(cedula);
    }

    //verfica tipo egreso

    public int verificaProveedor(int codigo){
        return repository.verificaProveedor(codigo);
    }
}