package com.proyecto.irp.ui.venta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proyecto.irp.R;

public class VentaCargaFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.ventacarga_fragment, container, false);

        FloatingActionButton btnAgregar = view.findViewById(R.id.btn_AddFacturaVenta);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),VentaCargaAddActivity.class);
                startActivity(i);
                //startActivityForResult(i,ADD_PROVEEDOR_REQUEST);

            }
        });

         return view;
    }



}
