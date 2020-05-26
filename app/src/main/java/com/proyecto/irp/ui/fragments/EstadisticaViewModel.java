package com.proyecto.irp.ui.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.repository.ClienteRepository;
import com.proyecto.irp.db.repository.FacturaVentaRepository;
import com.proyecto.irp.ui.venta.VentaBusquedaViewModel;

import java.util.List;


//ESTE ES UN VIEWMODEL GENERADO AUTOMATICAMENTE EN VEZ DE EXTENDER A ViewModel - AndroidViewModel
public class EstadisticaViewModel extends AndroidViewModel {

    //private MutableLiveData<String> mText;

    //SE INICIALIZA EL REPOSITORY Y EL LISTADO DE CLIENTES EN ESTE CASO
    private FacturaVentaRepository repository;
   // private LiveData<List<Cliente>> allClientes;
    private LiveData<List<EstadisticaVentas>> facturaventas;



    SessionManager managerUsuario;

    public EstadisticaViewModel(@NonNull Application application) {
        super(application);
        managerUsuario = new SessionManager(application);
        //Al llamar a la clase viewmodel carga estos datos
        repository = new FacturaVentaRepository(application);
       // allClientes = repository.getAllClientes();


        facturaventas = repository.getTotalVenta(managerUsuario.ObtenerDatos().getIdcontribuyente(),
                managerUsuario.ObtenerDatos().getIdejercicio());
    }

    //AQUI LLAMA A LOS DISTINTOS METODOS DE LA CLASE REPOSITORY COMO SER : INSERT, UPDATE, DELETE, ETC
   // public LiveData<List<Cliente>> getAllClientes() {
    //    return allClientes;
   // }

    public LiveData<List<EstadisticaVentas>> getTotalVentas() {
        return facturaventas;
   }


    //verfica cedula
    //public int verificaCedula(String cedula){
     //   return repository.verificaCedula(cedula);
   // }

}