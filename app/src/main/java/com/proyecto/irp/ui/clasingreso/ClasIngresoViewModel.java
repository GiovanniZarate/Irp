package com.proyecto.irp.ui.clasingreso;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.repository.ClasificacionIngresoRepository;

import java.util.List;

public class ClasIngresoViewModel extends AndroidViewModel {

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private ClasificacionIngresoRepository repository;
    private LiveData<List<ClasificacionIngreso>> allDatos;
    private List<ClasificacionIngreso> tipoingreso;
    public ClasIngresoViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new ClasificacionIngresoRepository(application);
        allDatos = repository.getAllClasificacioningreso();
        tipoingreso = repository.getAllTipoIngresoCombo();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<ClasificacionIngreso>> getAllClientes() {
        return allDatos;
    }

    public List<ClasificacionIngreso> getAllTipoIngresoCombo() {
        return tipoingreso;
    }

    public void insert(ClasificacionIngreso datos){
        repository.insert(datos);
    }

    public void update(ClasificacionIngreso datos){
        repository.update(datos);
    }

    public void delete(ClasificacionIngreso datos){
        repository.delete(datos);
    }
}