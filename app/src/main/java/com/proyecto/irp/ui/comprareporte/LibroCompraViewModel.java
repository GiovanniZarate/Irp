package com.proyecto.irp.ui.comprareporte;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.proyecto.irp.db.entity.ReporteLibroCompra;
import com.proyecto.irp.db.entity.ReporteLibroVenta;
import com.proyecto.irp.db.repository.FacturaCompraRepository;
import com.proyecto.irp.db.repository.FacturaVentaRepository;

import java.util.List;

public class LibroCompraViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaCompraRepository repository;
    // private LiveData<List<Cliente>> allClientes;
    private List<ReporteLibroCompra> facturacompra;

    public LibroCompraViewModel(@NonNull Application application) {
        super(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new FacturaCompraRepository(application);
    }


    public List<ReporteLibroCompra> getLibroCompra(int contribu, int ejercicio, String desde, String hasta) {
        return repository.getLibroCompra(contribu,ejercicio,desde,hasta);
    }
}
