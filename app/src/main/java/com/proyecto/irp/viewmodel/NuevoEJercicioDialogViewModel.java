package com.proyecto.irp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.entity.Ejercicio;
import com.proyecto.irp.db.repository.EjercicioRepository;

import java.util.List;

public class NuevoEJercicioDialogViewModel extends AndroidViewModel {
    // ES LA CLASE MEDIADORA PARA TRANSFERIR DATOS DE UN FRAGMENTO A OTRO.
    //LiveData = Datos Vivos - datos que van cambiando
    private LiveData<List<Ejercicio>> allEjerecicios;
    private EjercicioRepository ejercicioRepository;

    //METODO CONSTRUCTOR
    public NuevoEJercicioDialogViewModel(Application application){
        super(application);

        ejercicioRepository = new EjercicioRepository(application);
        allEjerecicios = ejercicioRepository.getAll();
    }

    //El fragment que necesita recibir la nueva lista de datos
    public LiveData<List<Ejercicio>> getAllEjerecicios() {return allEjerecicios; }

    //El fragment que inserte un nuevo ejercicio, debera comunicarse a este viewmodel
    public void insertarEjercicio(Ejercicio nuevoEjercicio) {ejercicioRepository.insert(nuevoEjercicio);}

    //PARA ACTUALIZAR
    public void actualizarEjercicio(Ejercicio ejercicioactualizar){
        ejercicioRepository.update(ejercicioactualizar);
    }

    //verfica EJERCIICO ACTUAL
    public int verificaEjercicio(int anho){
        return ejercicioRepository.verificaEjercicio(anho);
    }

    //TRAE EJERCIICO ACTUAL
    public Ejercicio traeEjercicioActual(int anho){
        return ejercicioRepository.traeEjercicioActual(anho);
    }
}
