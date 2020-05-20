package com.proyecto.irp.ui.venta;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.ui.adapter.FacturaVentaAdapter;

import java.util.List;

public class VentaBusquedaFragment extends Fragment {

    //INCIALIZAR ESTOAS
    private VentaBusquedaViewModel ventaBusquedaViewModel;
    RecyclerView recyclerView;
    FacturaVentaAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.venta_busqueda_fragment, container, false);

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_facturaventabusca);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new FacturaVentaAdapter();
        recyclerView.setAdapter(adapter);


        //INSTANCIAR EL VIEW MODEL
        ventaBusquedaViewModel = ViewModelProviders.of(this).get(VentaBusquedaViewModel.class);

        //FACTURA VENTA - INGRESO PRUEBA CARGA
        /*Facturaventa facturaventa =
                new Facturaventa(25052020,1,1,1,1,1,
                        "001-002-0000123",1500000,0,0,0,0,
                        0,1,2,123,20,5,2020);
        ventaBusquedaViewModel.insert(facturaventa);*/

        ventaBusquedaViewModel.getAllFacturaVentas().observe(getActivity(), new Observer<List<Facturaventa>>() {
            @Override
            public void onChanged(List<Facturaventa> facturaventas) {
                adapter.setFacturaventas(facturaventas);
            }
        });

        return view;
    }


}
