package com.proyecto.irp.ui.venta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.repository.FacturaVentaRepository;

import java.util.List;


public class VentaBusquedaViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaVentaRepository repository;
    private LiveData<List<Facturaventa>> allDatos;

    public VentaBusquedaViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new FacturaVentaRepository(application);
        allDatos = repository.getAllFacturaVenta();
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<Facturaventa>> getAllFacturaVentas() {
        return allDatos;
    }

    public void insert(Facturaventa facturaventa){
        repository.insert(facturaventa);
    }

   /* public void update(Proveedor proveedor){
        repository.update(proveedor);
    }

    public void delete(Proveedor proveedor){
        repository.delete(proveedor);
    }

    //verfica cedula
    public int verificaCedula(String cedula){
        return repository.verificaCedula(cedula);
    }*/

}
