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
    private List<TipoEgreso> tipoegresoCombo;

    public TipoEgresoViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new TipoEgresoRepository(application);
        allDatos = repository.getAllTipoEgreso();
        tipoegresoCombo = repository.getAllTipoEgresoCombo();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<TipoEgreso>> getAllTipoegreso() {
        return allDatos;
    }

    public List<TipoEgreso> getAllTipoegresoCombo() {
        return tipoegresoCombo;
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

    //verfica tipo egreso

    public int verificaTipoEgreso(int codigo){
        return repository.verificaTipoEgreso(codigo);
    }


}