package com.proyecto.irp.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.proyecto.irp.NuevoEJercicioDialogFragment;
import com.proyecto.irp.ui.adapter.MyEjercicioRecyclerViewAdapter;
import com.proyecto.irp.viewmodel.NuevoEJercicioDialogViewModel;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.Ejercicio;

import java.util.ArrayList;
import java.util.List;


public class EjercicioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    //private EjercicioInteractionListener mListener;

    //CREAR LIST EJERCICIO
    private List<Ejercicio> ejercicioList;
    //EL ADAPTADOR
    private MyEjercicioRecyclerViewAdapter adapterEjercicios;

    // view model nuevo ejercicio
    private NuevoEJercicioDialogViewModel ejercicioViewModel;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EjercicioFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EjercicioFragment newInstance(int columnCount) {
        EjercicioFragment fragment = new EjercicioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //PARA INDICAR QUE EL FRAGMENT TIENE UN PROPIO MENU Y NO TOME DEL ACTIVITY QUE LOS CONTIENE
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicio_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ejercicioList = new ArrayList<>();
           /* ejercicioList.add(new Ejercicio(1,2020,0,0,0,0,0,0));*/



            //reemplazamos
            //recyclerView.setAdapter(new MyEjercicioRecyclerViewAdapter(ejercicioList, mListener));
            adapterEjercicios = new MyEjercicioRecyclerViewAdapter(ejercicioList,getActivity());
            recyclerView.setAdapter(adapterEjercicios);

            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {
        //ejecutar la misma instancia de viewmodel que ejecutamos en EjercicioDialogFragment
                //OBENTENR LA MISMA INSTANCIA CON EL "ViewModelProvider" = devuelve misma instancia
        ejercicioViewModel = ViewModelProviders.of(getActivity())
                .get(NuevoEJercicioDialogViewModel.class);

        //invocar al metodo de listado para saber si hay nuevos datos
        ejercicioViewModel.getAllEjerecicios().observe(getActivity(), new Observer<List<Ejercicio>>() {
            @Override
            public void onChanged(List<Ejercicio> ejercicios) {
                //cada vez que hay un cambio lo obtenemos
                //y actualizamos el adapter
                adapterEjercicios.setNuevoEjercicio(ejercicios);
            }
        });
    }

/*
    //ESTE METODO ES EL PRIMERO QUE SE LANZA CUANDO NOSOTROS INSERTAMOS IN FRAGMENTO DENTRO DE UN ACTIVITY
    //
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EjercicioInteractionListener) {
            mListener = (EjercicioInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EjercicioInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    //PARA AÑADIR UN MENTO AL FRAGMENTO
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.opcions_menu_ejercicio_fragment,menu);
    }

    //

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_add_ejercicio:
                mostrarDialogoNuevoEjercicio();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoNuevoEjercicio() {
        //INSTANCIAR EL FRAGMENTO CON EL GESTOR DE FRAGMENTO
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NuevoEJercicioDialogFragment dialogNuevoEjercicio = new NuevoEJercicioDialogFragment();
        dialogNuevoEjercicio.show(fm,"NuevoEjercicioDialogFragment");
    }


    //NO CORRE POR AHORA
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }*/
}
