package com.proyecto.irp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.proyecto.irp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstadisticasFragment extends Fragment {
    PieChart torta;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_estadisticas, container, false);

        torta = view.findViewById(R.id.graficoPastel);

        crearGraficoPastel();
        return view;
    }

    private void crearGraficoPastel() {
        Description description = new Description();
        description.setText("Grafico de Pastel Venta");
        description.setTextSize(12);

        torta.setDescription(description);

        //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2,3));
        pieEntries.add(new PieEntry(3,5));
        pieEntries.add(new PieEntry(4,7));
        pieEntries.add(new PieEntry(5,8));
        pieEntries.add(new PieEntry(6,5));

        //MANDAMOS LOS DATOS PARA CREAR LA GRAFICA
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Grafico de Torta");

        PieData pieData = new PieData(pieDataSet);

        //PONER COLOAR A CADA BARRA
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //SEPARACION ENTRE LAS BARRAS

        torta.setData(pieData);
    }
}
