package com.proyecto.irp.ui.compra;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.repository.FacturaCompraRepository;

import java.util.List;

public class CompraCargaViewModel extends AndroidViewModel {

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaCompraRepository repository;
    private LiveData<List<Facturacompra>> allDatos;
    SessionManager managerUsuario;
    public CompraCargaViewModel(@NonNull Application application) {
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

    public void update(Facturacompra facturacompra){
        repository.update(facturacompra);
    }

    public void delete(Facturacompra facturacompra){
        repository.delete(facturacompra);
    }
}
