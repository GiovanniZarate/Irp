package com.proyecto.irp.ui.comprareporte;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.InputFilterMinMax;
import com.proyecto.irp.Utilitario.TemplatePDF;
import com.proyecto.irp.db.entity.ReporteIrpTotal;
import com.proyecto.irp.db.entity.ReporteLibroCompra;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IrpFinalFragment extends Fragment {
    EditText tvdiadesde,tvmesdesde,tvanhodesde,tvdiahasta,tvmeshasta,tvanhohasta;
    TextInputLayout ildiadesde,ilmesdesde,ilanhodesde,ildiahasta,ilmeshasta,ilanhohasta;
    SessionManager managerUsuario;

    private IrpFinalViewModel IrpFinalViewModel;
    Button btnverpdf,btnabrirpdf;
    String ejercicioactual;

    List<ReporteIrpTotal> libroCompras = new ArrayList<>();

    private String[] header = {"Descripcion","Importe"};


    private String contribuyenteActual;
    private String longText="";

    TemplatePDF templatePDF;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.irp_final_fragment, container, false);

        inicialization(view);
        permisos();
        //PARA COMPLETAR DIA Y MES
        completacero(tvdiadesde,2,"",ildiadesde);
        completacero(tvmesdesde,2,"",ilmesdesde);
        completacero(tvanhodesde,4,"",ilanhodesde);
        completacero(tvdiahasta,2,"",ildiahasta);
        completacero(tvmeshasta,2,"",ilmeshasta);
        completacero(tvanhohasta,4,"",ilanhohasta);

        tvdiadesde.setText("01");
        tvmesdesde.setText("01");
        tvdiahasta.setText("31");
        tvmeshasta.setText("12");

        tvanhodesde.setText(ejercicioactual);
        tvanhohasta.setText(ejercicioactual);


        btnverpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verinforme();
            }
        });

        btnabrirpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfinforme();
            }
        });


        return view;
    }

    //PARA VERIFICAR LA CANTIDAD DE CARACTERES MAYORES  EN DIA MES AÑO
    private void verificacantidadcaractermayor(EditText edtfecha,int cantcaracterpermitido){
        if(edtfecha.getText().toString().length()>cantcaracterpermitido){
            //VALIDAR QUE SE ESCRIBA LA CANTIDAD REQUERIDA
            int maxcaracter = edtfecha.getText().toString().trim().length();
            int mincaracter = maxcaracter - cantcaracterpermitido;
            edtfecha.setText(edtfecha.getText().toString().trim()
                    .substring(mincaracter,maxcaracter));
        }else {
            completacerosifalta(edtfecha,cantcaracterpermitido);
        }
    }
    private void verinforme() {
        //VALIDA A LA HORA DE GRABAR LA CANTIDAD DE CARACTERES EN LOS CAMPOS
        //DIA MES AÑO
        verificacantidadcaractermayor(tvdiadesde,2);
        verificacantidadcaractermayor(tvmesdesde,2);
        verificacantidadcaractermayor(tvanhodesde,4);

        verificacantidadcaractermayor(tvdiahasta,2);
        verificacantidadcaractermayor(tvmeshasta,2);
        verificacantidadcaractermayor(tvanhohasta,4);

        if(tvdiadesde.getText().toString().trim().isEmpty()){
            ildiadesde.setError("Vacio");
        }else if(tvmesdesde.getText().toString().trim().isEmpty()){
            ilmesdesde.setError("Vacio");
        }else if(tvanhodesde.getText().toString().trim().isEmpty()){
            ilanhodesde.setError("Vacio");
        }else if(tvdiahasta.getText().toString().trim().isEmpty()){
            ildiahasta.setError("Vacio");
        }else if(tvmeshasta.getText().toString().trim().isEmpty()){
            ilmeshasta.setError("Vacio");
        }else if(tvanhohasta.getText().toString().trim().isEmpty()){
            ilanhohasta.setError("Vacio");
        }else{
            //CAPTURA EL VALOR DE LA FECHA PARA PASAR A LA CONSULTA
            String vfdesde = tvanhodesde.getText().toString().trim().concat("-").concat(tvmesdesde.getText().toString().trim()
                    .concat("-").concat(tvdiadesde.getText().toString().trim()));

            String vfhasta = tvanhohasta.getText().toString().concat("-").trim().concat(tvmeshasta.getText().toString().trim()
                    .concat("-").concat(tvdiahasta.getText().toString().trim()));

            String fdesdemuestra = tvdiadesde.getText().toString().trim().concat("/").concat(tvmesdesde.getText().toString().trim()
                    .concat("/").concat(tvanhodesde.getText().toString().trim()));

            String fhastamuestra = tvdiahasta.getText().toString().trim().concat("/").concat(tvmeshasta.getText().toString().trim()
                    .concat("/").concat(tvanhohasta.getText().toString().trim()));

            templatePDF = new TemplatePDF(getContext());
            templatePDF.openDocument();
            templatePDF.addMetaData("Formulario 104","IVA","Carlos Giovanni Zarate Ruiz");
            templatePDF.addTitles("Formulario 104","Formulario 104","Fecha: "+fdesdemuestra+ " al "+ fhastamuestra);
            templatePDF.addParagraph("Contribuyente: "+contribuyenteActual);
            templatePDF.createTable(header,getIrpTotal(vfdesde,vfhasta));

            //PARA EL TOTAL
            //templatePDF.createTable(header,getIrpTotal(vfdesde,vfhasta));

            //templatePDF.addParagraph(longText);
            templatePDF.closeDocument();

            templatePDF.viewPDF();
        }

    }


    private void pdfinforme() {
        //VALIDA A LA HORA DE GRABAR LA CANTIDAD DE CARACTERES EN LOS CAMPOS
        //DIA MES AÑO
        verificacantidadcaractermayor(tvdiadesde,2);
        verificacantidadcaractermayor(tvmesdesde,2);
        verificacantidadcaractermayor(tvanhodesde,4);

        verificacantidadcaractermayor(tvdiahasta,2);
        verificacantidadcaractermayor(tvmeshasta,2);
        verificacantidadcaractermayor(tvanhohasta,4);

        if(tvdiadesde.getText().toString().trim().isEmpty()){
            ildiadesde.setError("Vacio");
        }else if(tvmesdesde.getText().toString().trim().isEmpty()){
            ilmesdesde.setError("Vacio");
        }else if(tvanhodesde.getText().toString().trim().isEmpty()){
            ilanhodesde.setError("Vacio");
        }else if(tvdiahasta.getText().toString().trim().isEmpty()){
            ildiahasta.setError("Vacio");
        }else if(tvmeshasta.getText().toString().trim().isEmpty()){
            ilmeshasta.setError("Vacio");
        }else if(tvanhohasta.getText().toString().trim().isEmpty()){
            ilanhohasta.setError("Vacio");
        }else{
            //CAPTURA EL VALOR DE LA FECHA PARA PASAR A LA CONSULTA
            String vfdesde = tvanhodesde.getText().toString().trim().concat("-").concat(tvmesdesde.getText().toString().trim()
                    .concat("-").concat(tvdiadesde.getText().toString().trim()));

            String vfhasta = tvanhohasta.getText().toString().concat("-").trim().concat(tvmeshasta.getText().toString().trim()
                    .concat("-").concat(tvdiahasta.getText().toString().trim()));

            String fdesdemuestra = tvdiadesde.getText().toString().trim().concat("/").concat(tvmesdesde.getText().toString().trim()
                    .concat("/").concat(tvanhodesde.getText().toString().trim()));

            String fhastamuestra = tvdiahasta.getText().toString().trim().concat("/").concat(tvmeshasta.getText().toString().trim()
                    .concat("/").concat(tvanhohasta.getText().toString().trim()));

            templatePDF = new TemplatePDF(getContext());
            templatePDF.openDocument();
            templatePDF.addMetaData("Formulario 104","IVA","Carlos Giovanni Zarate Ruiz");
            templatePDF.addTitles("Formulario 104","Formulario 104","Fecha: "+fdesdemuestra+ " al "+ fhastamuestra);
            templatePDF.addParagraph("Contribuyente: "+contribuyenteActual);
            templatePDF.createTable(header,getIrpTotal(vfdesde,vfhasta));

            //templatePDF.addParagraph(longText);
            templatePDF.closeDocument();
            templatePDF.appViewPDF(getActivity());
    }
    }


    /*private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnverpdf.setOnClickListener(this);
        btnabrirpdf.setOnClickListener(this);
    }*/

    private void permisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            int permissionCheck = ContextCompat.checkSelfPermission(
                    getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No se tiene permiso para leer.");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            } else {
                Log.i("Mensaje", "Se tiene permiso para leer!");
            }
        }
    }

    private void inicialization(View view) {
        btnverpdf = view.findViewById(R.id.btnVerPdfIrp);
        btnabrirpdf = view.findViewById(R.id.btnImprimirPdfIrp);

        tvdiadesde = view.findViewById(R.id.txtdiaDesde);
        tvmesdesde = view.findViewById(R.id.txtmesDesde);
        tvanhodesde = view.findViewById(R.id.txtanhoDesde);
        tvdiahasta = view.findViewById(R.id.txtdiaHasta);
        tvmeshasta = view.findViewById(R.id.txtmesHasta);
        tvanhohasta = view.findViewById(R.id.txtanhoHasta);

        ildiadesde = view.findViewById(R.id.InputLayoutDiaDesde);
        ilmesdesde = view.findViewById(R.id.InputLayoutMesDesde);
        ilanhodesde = view.findViewById(R.id.InputLayoutAnhoDesde);
        ildiahasta = view.findViewById(R.id.InputLayoutDiaHasta);
        ilmeshasta = view.findViewById(R.id.InputLayoutMesHasta);
        ilanhohasta = view.findViewById(R.id.InputLayoutAnhoHasta);

        //VALIDAR CANTIDAD HASTA QUE NRO SE PUEDE CARGAR
        tvdiadesde.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        tvmesdesde.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        tvanhodesde.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2060")});
        tvdiahasta.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        tvmeshasta.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        tvanhohasta.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2060")});

        managerUsuario = new SessionManager(getContext());
        ejercicioactual = String.valueOf(managerUsuario.ObtenerDatos().getAnho());
        contribuyenteActual = managerUsuario.ObtenerDatos().getRuc().concat(" - ")
                .concat(managerUsuario.ObtenerDatos().getNombrecontribuyente());
    }

   /* public void pdfView(View view){
        templatePDF.viewPDF();
    }*/

   //CARGAR LOS DATOS DE LA BASE DE DATOS POR FECHA
   // private List<ReporteLibroVenta> getFacturaVenta(String desde,String hasta){
   private ArrayList<String[]> getIrpTotal(String desde, String hasta){
        //INSTANCIAR EL VIEW MODEL
        IrpFinalViewModel = ViewModelProviders.of(this).get(IrpFinalViewModel.class);

       Log.i("Mensaje", ""+managerUsuario.ObtenerDatos().getIdcontribuyente()+managerUsuario.ObtenerDatos().getIdejercicio()+
               desde+hasta);

        libroCompras = IrpFinalViewModel.getIrpTotal(managerUsuario.ObtenerDatos().getIdcontribuyente(),
                managerUsuario.ObtenerDatos().getIdejercicio(),desde,hasta);

        ArrayList<String[]> rows = new ArrayList<>();

        /*private String[] header = {"Fecha","Ruc","Nombre","Tipo Doc.","Nro. Factura","Exenta","Gravada 5",
                "Iva 5","Gravada 10","Iva 10","Total"};*/
        for (ReporteIrpTotal riva: libroCompras){
            rows.add(new String[]{riva.getDescripcion(),
                    separadormil(riva.getTotalirp())

            });
        }
        return rows;
    }


    private String separadormil(int value){
        //DecimalFormat formateador = new DecimalFormat("###,###.##");
        DecimalFormat formateador = new DecimalFormat("###,###.##", new DecimalFormatSymbols(new Locale("es","PY")));
        return  (formateador.format(value));
    }


    private void completacero(final EditText edtnrofactura, final int cantidad, final String msg,
                              final TextInputLayout textInputLayout){
        //PARA QUE COMPLETE CON CERO
        edtnrofactura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus ){
                    if (edtnrofactura.getText().toString().isEmpty()){
                        //edtnrofactura.setError("Debe Ingresar Nro "+msg);
                        textInputLayout.setError("Vacio"+msg);
                    }else{
                        if(edtnrofactura.getText().toString().length()>cantidad){
                            //VALIDAR QUE SE ESCRIBA LA CANTIDAD REQUERIDA
                            int maxcaracter = edtnrofactura.getText().toString().trim().length();
                            int mincaracter = maxcaracter - cantidad;
                            edtnrofactura.setText(edtnrofactura.getText().toString().trim()
                                    .substring(mincaracter,maxcaracter));
                        } else{
                            completacerosifalta(edtnrofactura,cantidad);
                            textInputLayout.setError("");
                        }
                    }
                }
            }
        });
    }

    private void completacerosifalta(EditText editText,int cantidad) {
        int l = 0;
        String aux = "";
        aux = editText.getText().toString().trim();
        int vnro2 = Integer.parseInt(editText.getText().toString());
        while (l < cantidad) {
            if (aux.length() >= cantidad) {
                aux = aux;
                l = cantidad;
            } else {
                aux = "0" + aux;
                l = aux.length();
            }
        }
        editText.setText(aux);
    }

}
