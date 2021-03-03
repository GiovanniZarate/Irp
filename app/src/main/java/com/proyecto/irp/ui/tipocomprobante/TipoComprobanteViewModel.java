package com.proyecto.irp.ui.tipocomprobante;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.repository.TipoComprobanteRepository;

import java.util.List;

public class TipoComprobanteViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private TipoComprobanteRepository repository;
    private LiveData<List<TipoComprobante>> allDatos;
    private List<TipoComprobante> tipcomprobantecombo;
    public TipoComprobanteViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new TipoComprobanteRepository(application);
        allDatos = repository.getAllTipoComprobante();
        tipcomprobantecombo = repository.getAllTipoComprobanteCombo();
    }
    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<TipoComprobante>> getAllDatos() {
        return allDatos;
    }

    public List<TipoComprobante> getAllTipoComprobanteCombo() {
        return tipcomprobantecombo;
    }

    public void insert(TipoComprobante datos){
        repository.insert(datos);
    }

    public void update(TipoComprobante datos){
        repository.update(datos);
    }

    public void delete(TipoComprobante datos){
        repository.delete(datos);
    }

    //verfica tipo egreso

    public int verificaTipocomprobante(int codigo){
        return repository.verificaTipocomprobante(codigo);
    }
}
