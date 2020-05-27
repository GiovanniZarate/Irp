package com.proyecto.irp.ui.compra;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.repository.FacturaCompraRepository;
import com.proyecto.irp.db.repository.FacturaVentaRepository;

import java.util.List;


public class CompraBusquedaViewModel extends AndroidViewModel {
    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaCompraRepository repository;
    private LiveData<List<Facturacompra>> allDatos;
    SessionManager managerUsuario;

    public CompraBusquedaViewModel(@NonNull Application application) {
        super(application);
        managerUsuario = new SessionManager(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new FacturaCompraRepository(application);
        allDatos = repository.getAllFacturaCompra(managerUsuario.ObtenerDatos().getIdcontribuyente(),
                managerUsuario.ObtenerDatos().getIdejercicio());
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
    public LiveData<List<Facturacompra>> getAllFacturaCompras() {
        return allDatos;
    }

    public void insert(Facturacompra facturacompra){
        repository.insert(facturacompra);
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
