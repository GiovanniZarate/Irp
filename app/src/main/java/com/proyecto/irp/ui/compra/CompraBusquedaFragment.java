package com.proyecto.irp.ui.compra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.ui.adapter.FacturaCompraBusAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompraBusquedaFragment extends Fragment {

    //INCIALIZAR ESTOAS
    private CompraBusquedaViewModel compraBusquedaViewModel;
    RecyclerView recyclerView;
    FacturaCompraBusAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compra_busqueda, container, false);

        //INSTANCIAR EL RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recycler_facturacomprabusca);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        //INSTANCIAR EL ADAPTER
        adapter = new FacturaCompraBusAdapter();
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);

        //INSTANCIAR EL VIEW MODEL
        compraBusquedaViewModel = ViewModelProviders.of(this).get(CompraBusquedaViewModel.class);

        compraBusquedaViewModel.getAllFacturaCompras().observe(getActivity(), new Observer<List<Facturacompra>>() {
            @Override
            public void onChanged(List<Facturacompra> facturacompras) {
                adapter.setFacturacompras(facturacompras);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busqueda,menu);

        MenuItem busquedaItem = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) busquedaItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}
