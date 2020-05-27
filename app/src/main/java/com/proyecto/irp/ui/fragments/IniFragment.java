package com.proyecto.irp.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturaventa;

import java.util.ArrayList;
import java.util.List;

public class IniFragment extends Fragment {
    BarChart graficaBarras;
    TextView tvejercicio,tvcontribuyente;


    //List<EstadisticaVentas> listaVentas = new ArrayList<>();
    //List<EstadisticaVentas> listaVentas = new ArrayList<>();
    private String[]months={"Ingreso","Egreso","","",""};
    private int[]colors=new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.BLUE,Color.MAGENTA};

    List<BarEntry> entradas = new ArrayList<>();
    private EstadisticaViewModel estadisticaViewModel;

    SessionManager managerUsuario;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ini,container,false);
        graficaBarras = view.findViewById(R.id.barChartVenta);
        tvejercicio = view.findViewById(R.id.txt_ejercicio_inicio);
        tvcontribuyente = view.findViewById(R.id.txt_contribuyente_inicio);

        managerUsuario = new SessionManager(getContext());
        tvejercicio.setText("Ejercicio: "+String.valueOf(managerUsuario.ObtenerDatos().getAnho()));
        tvcontribuyente.setText(managerUsuario.ObtenerDatos().getRuc().trim()+" - "+managerUsuario.ObtenerDatos().getNombrecontribuyente().trim());

        obtenerVentas();
        //createCharts();
        return view;
    }
    private void obtenerVentas() {
        //INSTANCIAR EL VIEW MODEL
        estadisticaViewModel = ViewModelProviders.of(this).get(EstadisticaViewModel.class);

        estadisticaViewModel.getTotalVentas().observe(getActivity(), new Observer<List<EstadisticaVentas>>() {
            @Override
            public void onChanged(List<EstadisticaVentas> estadisticaVentas) {

                entradas.clear();

                //PARA MOSTRAR LA PARTE NEGATIVA
                int totalingreso1=0,totalegreso1=0,diferencia1=0;
                for (int i = 0; i< estadisticaVentas.size(); i++){
                    if (i==0){
                        totalingreso1 = estadisticaVentas.get(i).getTotalventa();
                    }else if (i==1){
                        totalegreso1 = estadisticaVentas.get(i).getTotalventa();
                    }
                    //entradas.add(new BarEntry(i,estadisticaVentas.get(i).getTotalventa()));
                }
                diferencia1 = totalingreso1 - totalegreso1;

                if (diferencia1<0){
                    diferencia1 = diferencia1;
                }else{
                    diferencia1 = 0;
                }





                createCharts(estadisticaVentas,diferencia1);


               /* //AGREGAR LA DIFERENCIA
                entradas.add(new BarEntry(2,2500000));

                Description description = new Description();
                description.setText("");
                //description.setTextSize(12);

                graficaBarras.setDescription(description);
                //MANDAMOS LOS DATOS PARA CREAR GRAFICA
                BarDataSet datos = new BarDataSet(entradas,"Ingreso - Egreso");

                BarData data = new BarData(datos);

                //PONEMOS COLOR A CADA BARRA
                datos.setColors(ColorTemplate.COLORFUL_COLORS);

                //PONER EL BORDE
                data.setBarWidth(0.9f);

                graficaBarras.setData(data);
                //PONE LAS BARRAS CENTRADAS
                graficaBarras.setFitBars(true);
                //HACER REFRESH
                graficaBarras.invalidate();*/
            }
        });
    }


    private void createCharts(List<EstadisticaVentas> estadisticaVentas, int minx){
        graficaBarras=(BarChart)getSameChart(graficaBarras,"Estadistica Compra - Venta",Color.CYAN,Color.LTGRAY,3000);
        graficaBarras.setDrawGridBackground(true);
        graficaBarras.setDrawBarShadow(true);
        graficaBarras.setData(getBarData(estadisticaVentas));
        graficaBarras.invalidate();
        axisX(graficaBarras.getXAxis());
        axisLeft(graficaBarras.getAxisLeft(),minx);
        axisRight(graficaBarras.getAxisRight());
        graficaBarras.getLegend().setEnabled(false);
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);

        return chart;
    }

    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for(int i=0;i<months.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }


    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
//        axis.setEnabled(false);
    }

    private void axisLeft(YAxis axis,int minx){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(minx);

    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);

    }

    private BarData getBarData(List<EstadisticaVentas> estadisticaVentas){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(estadisticaVentas),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private List<BarEntry>getBarEntries(List<EstadisticaVentas> estadisticaVentas){
        int totalingreso=0,totalegreso=0,diferencia=0;
       // entradas.add(new BarEntry(-1,-10000000));
        for (int i = 0; i< estadisticaVentas.size(); i++){
            if (i==0){
                totalingreso = estadisticaVentas.get(i).getTotalventa();
            }else if (i==1){
                totalegreso = estadisticaVentas.get(i).getTotalventa();
            }
            entradas.add(new BarEntry(i,estadisticaVentas.get(i).getTotalventa()));
        }
        diferencia = totalingreso - totalegreso;
        entradas.add(new BarEntry(2,diferencia));
        return entradas;
    }

}
