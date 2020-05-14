package com.proyecto.irp.ui.tipoegreso;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.TipoEgreso;
import com.proyecto.irp.db.repository.TipoEgresoRepository;

import java.util.List;

public class TipoEgresoViewModel extends AndroidViewModel {

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private TipoEgresoRepository repository;
    private LiveData<List<TipoEgreso>> allDatos;

    public TipoEgresoViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new TipoEgresoRepository(application);
        allDatos = repository.getAllTipoEgreso();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<TipoEgreso>> getAllTipoegreso() {
        return allDatos;
    }

    public void insert(TipoEgreso tipoEgreso){
        repository.insert(tipoEgreso);
    }

    public void update(TipoEgreso tipoEgreso){
        repository.update(tipoEgreso);
    }

    public void delete(TipoEgreso tipoEgreso){
        repository.delete(tipoEgreso);
    }


}