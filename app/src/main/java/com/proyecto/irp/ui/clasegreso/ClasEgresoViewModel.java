package com.proyecto.irp.ui.clasegreso;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.db.repository.ClasificacionEgresoRepository;

import java.util.List;

public class ClasEgresoViewModel extends AndroidViewModel {

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private ClasificacionEgresoRepository repository;
    private LiveData<List<ClasificacionEgreso>> allDatos;

    public ClasEgresoViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new ClasificacionEgresoRepository(application);
        allDatos = repository.getAllClasificacionEgreso();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<ClasificacionEgreso>> getAllClasficacionEgreso() {
        return allDatos;
    }

    public void insert(ClasificacionEgreso clasificacionEgreso){
        repository.insert(clasificacionEgreso);
    }

    public void update(ClasificacionEgreso clasificacionEgreso){
        repository.update(clasificacionEgreso);
    }

    public void delete(ClasificacionEgreso clasificacionEgreso){
        repository.delete(clasificacionEgreso);
    }
}