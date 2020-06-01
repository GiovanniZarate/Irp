package com.proyecto.irp.ui.ventareporte;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.db.entity.ReporteLibroVenta;
import com.proyecto.irp.db.repository.FacturaVentaRepository;

import java.util.Date;
import java.util.List;

public class LibroVentaViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaVentaRepository repository;
    // private LiveData<List<Cliente>> allClientes;
    private List<ReporteLibroVenta> facturaventas;

    public LibroVentaViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new FacturaVentaRepository(application);
    }


    public List<ReporteLibroVenta> getLibroVenta(int contribu, int ejercicio, String desde, String hasta) {
        return repository.getLibroVenta(contribu,ejercicio,desde,hasta);
    }
}
