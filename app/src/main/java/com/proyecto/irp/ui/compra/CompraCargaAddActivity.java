package com.proyecto.irp.ui.compra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.InputFilterMinMax;
import com.proyecto.irp.Utilitario.separador_miles;
import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.db.entity.Proveedor;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.entity.TipoEgreso;
import com.proyecto.irp.ui.clasegreso.ClasEgresoViewModel;
import com.proyecto.irp.ui.proveedor.ProveedorViewModel;
import com.proyecto.irp.ui.tipocomprobante.TipoComprobanteViewModel;
import com.proyecto.irp.ui.tipoegreso.TipoEgresoViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class CompraCargaAddActivity extends AppCompatActivity {

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDFACTURACOMPRA = "com.proyecto.irp.ui.compra.EXTRA_IDFACTURACOMPRA";
    public static final String EXTRA_FECHAFACTURACOMPRA = "com.proyecto.irp.ui.compra.EXTRA_FECHAFACTURACOMPRA";
    public static final String EXTRA_PROVEEDOR = "com.proyecto.irp.ui.compra.EXTRA_PROVEEDOR";
    public static final String EXTRA_PROVEEDORSELECTED = "com.proyecto.irp.ui.compra.EXTRA_PROVEEDORSELECTED";
    public static final String EXTRA_TIPOEGRESO = "com.proyecto.irp.ui.compra.EXTRA_TIPOEGRESO";
    public static final String EXTRA_TIPOEGRESOSELECTED = "com.proyecto.irp.ui.compra.EXTRA_TIPOEGRESOSELECTED";
    public static final String EXTRA_CLASIFICACIONEGRESO = "com.proyecto.irp.ui.compra.EXTRA_CLASIFICACIONEGRESO";
    public static final String EXTRA_CLASIFICACIONEGRESOSELECTED = "com.proyecto.irp.ui.compra.EXTRA_CLASIFICACIONEGRESOSELECTED";
    public static final String EXTRA_TIPOCOMPROBANTE = "com.proyecto.irp.ui.compra.EXTRA_TIPOCOMPROBANTE";
    public static final String EXTRA_TIPOCOMPROBANTESELECTED = "com.proyecto.irp.ui.compra.EXTRA_TIPOCOMPROBANTESELECTED";
    public static final String EXTRA_CONTRIBUYENTE = "com.proyecto.irp.ui.compra.EXTRA_CONTRIBUYENTE";
    public static final String EXTRA_EJERCICIO = "com.proyecto.irp.ui.compra.EXTRA_EJERCICIO";
    public static final String EXTRA_NROFACTURA = "com.proyecto.irp.ui.compra.EXTRA_NROFACTURA";
    public static final String EXTRA_TOTALCOMPRA = "com.proyecto.irp.ui.compra.EXTRA_TOTALCOMPRA";
    public static final String EXTRA_EXENTACOMPRA = "com.proyecto.irp.ui.compra.EXTRA_EXENTACOMPRA";
    public static final String EXTRA_GRAVADA10COMPRA = "com.proyecto.irp.ui.compra.EXTRA_GRAVADA10COMPRA";
    public static final String EXTRA_IVA10COMPRA = "com.proyecto.irp.ui.compra.EXTRA_IVA10COMPRA";
    public static final String EXTRA_GRAVADA5COMPRA = "com.proyecto.irp.ui.compra.EXTRA_GRAVADA5COMPRA";
    public static final String EXTRA_IVA5COMPRA = "com.proyecto.irp.ui.compra.EXTRA_IVA5COMPRA";
    public static final String EXTRA_NRO1COMPRA = "com.proyecto.irp.ui.compra.EXTRA_NRO1COMPRA";
    public static final String EXTRA_NRO2COMPRA = "com.proyecto.irp.ui.compra.EXTRA_NRO2COMPRA";
    public static final String EXTRA_NRO3COMPRA = "com.proyecto.irp.ui.compra.EXTRA_NRO3COMPRA";
    public static final String EXTRA_DIACOMPRA = "com.proyecto.irp.ui.compra.EXTRA_DIACOMPRA";
    public static final String EXTRA_MESCOMPRA = "com.proyecto.irp.ui.compra.EXTRA_MESCOMPRA";
    public static final String EXTRA_ANHOCOMPRA = "com.proyecto.irp.ui.compra.EXTRA_ANHOCOMPRA";

    EditText tvdia,tvmes,tvanho,tvnro1,tvnro2,tvnro3,tvimpgrav10,tvimpgrav5,tvimpexenta,
            tvimp10,tvimp5,tvimpsubtotal,tviva10,tviva5,tvtotaliva,tvneto;

    Spinner spinnerTipoDocumento,spinnerTipoEgreso,spinnerClasTipoEgreso,spinnerProveedor;

    TextInputLayout ilnro1,ilnro2,ilnro3,ildia,ilmes,ilanho,ilimpgrav10,ilimpgrav5,ilimpexe;

    TextView tvcontribuyente;

    String codtipodocumento,codtipoegreso,codclasegreso,codproveedor,ejercicioactual;

    private TipoComprobanteViewModel tipoComprobanteViewModel;
    private List<TipoComprobante> combocomprobante;

    private TipoEgresoViewModel tipoEgresoViewModel;
    private List<TipoEgreso> combotipoegreso;


    private ClasEgresoViewModel clasEgresoViewModel;
    private List<ClasificacionEgreso> comboclastipoegreso;

    private ProveedorViewModel proveedorViewModel;
    private List<Proveedor> comboproveedor;


    ArrayAdapter adapter_comprobante,adapter_clastipoegreso,adapter_tipoegreso,adapter_proveedor;

    SessionManager managerUsuario;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_carga_add);

        inicializacion();

        inicializaViewModelCombo();

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);
        setTitle("Egreso - "+managerUsuario.ObtenerDatos().getAnho());


        //para completar con cero el nro factura
        completacero(tvnro1,3,"1",ilnro1);
        completacero(tvnro2,3,"2",ilnro2);
        completacero(tvnro3,7,"3",ilnro3);

        //PARA COMPLETAR DIA Y MES
        completacero(tvdia,2,"",ildia);
        completacero(tvmes,2,"",ilmes);
        completacero(tvanho,4,"",ilanho);

        //CONTROLAR EVENTOS DE ENTRADA Y SALIDA DEL FOCO
        eventosFocus();


        ///SEPARADOR DE MILES MIENTRAS SE ESCRIBE LOS MONTOS
        tvimpgrav10.addTextChangedListener(new separador_miles(tvimpgrav10));
        tvimpgrav5.addTextChangedListener(new separador_miles(tvimpgrav5));
        tvimpexenta.addTextChangedListener(new separador_miles(tvimpexenta));

        cargacombocomprobante();
        cargacombotipoegreso();

        cargacomboproveedor();

        //CAPTURAR VALOR SELECCIONADO COMOBO
        capturaCombo();


        //PARA SABER SI SE VA A EDITAR O AGREGAR NUEVO
        modoAgregarEditar();

    }

    private void modoAgregarEditar() {
        ejercicioactual = String.valueOf(managerUsuario.ObtenerDatos().getAnho());
        tvcontribuyente.setText(managerUsuario.ObtenerDatos().getRuc().trim()+" - "+managerUsuario.ObtenerDatos().getNombrecontribuyente().trim());
        //para poner titulo si es agregar o modificar
        intent = getIntent();
        if (intent.hasExtra(EXTRA_IDFACTURACOMPRA)){
            setTitle("Editar Egreso");
            recibirValoresEditar();
            tvdia.requestFocus();
        }else {
            setTitle("Agregar Egreso");

            if (ejercicioactual.trim()==null){
                Toast.makeText(this,"Debes Crear Ejercicio ",Toast.LENGTH_LONG).show();
            }else {
                tvanho.setText(ejercicioactual);
            }

        }
    }

    private void recibirValoresEditar() {

        tvdia.setText(intent.getStringExtra(EXTRA_DIACOMPRA));
        tvmes.setText(intent.getStringExtra(EXTRA_MESCOMPRA));
        tvanho.setText(intent.getStringExtra(EXTRA_ANHOCOMPRA));
        for (int i = 0; i <  spinnerTipoDocumento.getAdapter().getCount(); i++){
            if (spinnerTipoDocumento.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_TIPOCOMPROBANTESELECTED).toString().trim())){
                spinnerTipoDocumento.setSelection(i);
                break;
            }
        }
        for (int i = 0; i <  spinnerTipoEgreso.getAdapter().getCount(); i++){
            if (spinnerTipoEgreso.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED).toString().trim())){
                spinnerTipoEgreso.setSelection(i);
                break;
            }
        }
        /*Toast.makeText(this,"valor tipo egreso "+intent.getStringExtra(EXTRA_TIPOEGRESO).toString().trim()+
                        "VALOR CLAS EGRESO "+intent.getStringExtra(EXTRA_CLASIFICACIONEGRESOSELECTED).toString().trim(),
                Toast.LENGTH_LONG).show();*/
        //cargacomboclasegreso(Integer.parseInt(intent.getStringExtra(EXTRA_TIPOEGRESO).toString().trim()));
      /*  for (int i = 0; i <  spinnerClasTipoEgreso.getAdapter().getCount(); i++){
            if (spinnerClasTipoEgreso.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_CLASIFICACIONEGRESOSELECTED).toString().trim())){
                spinnerClasTipoEgreso.setSelection(i);
                break;
            }
        }*/
        for (int i = 0; i <  spinnerProveedor.getAdapter().getCount(); i++){
            if (spinnerProveedor.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_PROVEEDORSELECTED).toString().trim())){
                spinnerProveedor.setSelection(i);
                break;
            }
        }
        tvnro1.setText(intent.getStringExtra(EXTRA_NRO1COMPRA));
        tvnro2.setText(intent.getStringExtra(EXTRA_NRO2COMPRA));
        tvnro3.setText(intent.getStringExtra(EXTRA_NRO3COMPRA));

        //Toast.makeText(this,"valor grav10 "+intent.getStringExtra(EXTRA_GRAVADA10VENTA),Toast.LENGTH_SHORT).show();

        tvimpgrav10.setText(intent.getStringExtra(EXTRA_GRAVADA10COMPRA));
        tvimpgrav5.setText(intent.getStringExtra(EXTRA_GRAVADA5COMPRA));
        tvimpexenta.setText(intent.getStringExtra(EXTRA_EXENTACOMPRA));

        formatotextoenableseparadormiles(tviva10,Integer.parseInt(intent.getStringExtra(EXTRA_IVA10COMPRA)));
        formatotextoenableseparadormiles(tviva5,Integer.parseInt(intent.getStringExtra(EXTRA_IVA5COMPRA)));


        calculatotales(Integer.parseInt(intent.getStringExtra(EXTRA_GRAVADA10COMPRA)),0);
        calculatotales(Integer.parseInt(intent.getStringExtra(EXTRA_GRAVADA5COMPRA)),1);
    }


    private void capturaCombo() {

        //COMBO TIPO DOCUMENTO
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoComprobante tipoComprobante = (TipoComprobante) spinnerTipoDocumento.getAdapter().getItem(position);
                codtipodocumento = String.valueOf(tipoComprobante.getIdtipocomprobante());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        //COMBO TIPO EGRESO
        spinnerTipoEgreso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoEgreso tipoEgreso = (TipoEgreso) spinnerTipoEgreso.getAdapter().getItem(position);
                codtipoegreso = String.valueOf(tipoEgreso.getIdtipoegreso());
                //Toast.makeText(view.getContext(),"TIPOEGRESO SELECTED "+codtipoegreso, Toast.LENGTH_SHORT).show();
                cargacomboclasegreso(Integer.parseInt(codtipoegreso));
                if (intent.getStringExtra(EXTRA_TIPOEGRESO)!=null){
                //PARA SELECCIONAR EL VALOR QUE SE GRABO EN EL COMBO SOLO PARA MODIFICAR
                    for (int i = 0; i <  spinnerClasTipoEgreso.getAdapter().getCount(); i++){
                        if (spinnerClasTipoEgreso.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_CLASIFICACIONEGRESOSELECTED).toString().trim())){
                            spinnerClasTipoEgreso.setSelection(i);
                            break;
                        }
                   }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        //COMBO CLASIFICACION EGRESO
        spinnerClasTipoEgreso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClasificacionEgreso clasificacionEgreso = (ClasificacionEgreso) spinnerClasTipoEgreso.getAdapter().getItem(position);
                codclasegreso = String.valueOf(clasificacionEgreso.getIdclasificacionegreso());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        //COMBO CLIENTE
        spinnerProveedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proveedor proveedor = (Proveedor) spinnerProveedor.getAdapter().getItem(position);
                codproveedor = String.valueOf(proveedor.getIdproveedor());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


    }

    private void eventosFocus() {
        //GRAVADA 10
        tvimpgrav10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //Toast.makeText(v.getContext(),"Sale",Toast.LENGTH_SHORT).show();
                    //SI ESTA VACIO PONE EN CERO
                    if (tvimpgrav10.getText().toString().isEmpty()){
                        tvimpgrav10.setText("0");

                        //VACIA CUANDO SE LIMPIA EL CAMPO
                        tvimp10.setText("0");
                        tviva10.setText("0");

                        calculatotales(0,3);
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        int valor10=0;
                        valor10 = Integer.parseInt(tvimpgrav10.getText().toString().replace(".",""));
                        //Math.round(Integer.parseInt(v_jtgrav10) * 0.1)
                        //iva10 = (int) Math.round(valor10/11 * 0.1);
                        //calculaiva(valor10,0);
                        //tvimp10.setText(""+gravada10);
                        //tviva10.setText(""+iva10);
                        calculatotales(valor10,0);
                        //ilimpgrav10.setError("");
                    }
                }else{
                    //Toast.makeText(v.getContext(),"Entra",Toast.LENGTH_SHORT).show();
                    if (tvimpgrav10.getText().toString().trim().equals("0")){
                        tvimpgrav10.setText("");
                    }
                }
            }
        });
        //GRAVADA 5
        tvimpgrav5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //SI ESTA VACIO PONE EN CERO
                    if (tvimpgrav5.getText().toString().isEmpty()){
                        tvimpgrav5.setText("0");
                        //VACIA CUANDO SE LIMPIA EL CAMPO
                        tvimp5.setText("0");
                        tviva5.setText("0");

                        calculatotales(0,3);
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        int valor5=0;
                        valor5 = Integer.parseInt(tvimpgrav5.getText().toString().replace(".",""));
                        //iva5 = (int) Math.round(valor5/21 * 0.1);
                        //calculaiva(valor5,1);
                        //tvimp5.setText(""+gravada5);
                        //tviva5.setText(""+iva5);

                        calculatotales(valor5,1);
                        //ilimpgrav5.setError("");
                    }
                }else{
                    //Toast.makeText(v.getContext(),"Entra",Toast.LENGTH_SHORT).show();
                    if (tvimpgrav5.getText().toString().trim().equals("0")){
                        tvimpgrav5.setText("");
                    }
                }
            }
        });

        //EXENTA
        tvimpexenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //SI ESTA VACIO PONE EN CERO
                    if (tvimpexenta.getText().toString().isEmpty()){
                        tvimpexenta.setText("0");
                        calculatotales(0,3);
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        calculatotales(0,3);
                        //ilimpexe.setError("");
                    }
                }else{
                    if (tvimpexenta.getText().toString().trim().equals("0")){
                        tvimpexenta.setText("");
                    }
                }
            }
        });

    }

    private void cargacombocomprobante() {
        combocomprobante =  tipoComprobanteViewModel.getAllTipoComprobanteCombo();
        adapter_comprobante = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                combocomprobante);
        adapter_comprobante.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDocumento.setAdapter(adapter_comprobante);
    }

    private void cargacombotipoegreso() {
        combotipoegreso =  tipoEgresoViewModel.getAllTipoegresoCombo();
        adapter_tipoegreso = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                combotipoegreso);
        adapter_tipoegreso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoEgreso.setAdapter(adapter_tipoegreso);
    }

    private void cargacomboclasegreso(int codtipoegreso) {
        comboclastipoegreso =  clasEgresoViewModel.getAllClasficacionEgresoCombo(codtipoegreso);
        adapter_clastipoegreso = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                comboclastipoegreso);
        adapter_clastipoegreso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClasTipoEgreso.setAdapter(adapter_clastipoegreso);
    }
    private void cargacomboproveedor() {
        comboproveedor =  proveedorViewModel.getAllProveedorCombo();
        adapter_proveedor = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                comboproveedor);
        adapter_proveedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProveedor.setAdapter(adapter_proveedor);
    }
    private void inicializaViewModelCombo() {
        tipoComprobanteViewModel = ViewModelProviders.of(this).get(TipoComprobanteViewModel.class);
        tipoEgresoViewModel = ViewModelProviders.of(this).get(TipoEgresoViewModel.class);
        clasEgresoViewModel = ViewModelProviders.of(this).get(ClasEgresoViewModel.class);
        proveedorViewModel = ViewModelProviders.of(this).get(ProveedorViewModel.class);
    }

    private void inicializacion() {
        tvdia = findViewById(R.id.txtdiaFacturaCompra);
        tvmes = findViewById(R.id.txtmesFacturaCompra);
        tvanho = findViewById(R.id.txtanhoFacturaCompra);
        tvnro1 = findViewById(R.id.txtnro1FacturaCompra);
        tvnro2 = findViewById(R.id.txtnro2FacturaCompra);
        tvnro3 = findViewById(R.id.txtnro3FacturaCompra);
        ilnro1 = findViewById(R.id.InputLayoutNro1Compra);
        ilnro2 = findViewById(R.id.InputLayoutNro2Compra);
        ilnro3 = findViewById(R.id.InputLayoutNro3Compra);



        ildia = findViewById(R.id.InputLayoutDiaCompra);
        ilmes = findViewById(R.id.InputLayoutMesCompra);
        ilanho = findViewById(R.id.InputLayoutAnhoCompra);

        ilimpgrav10 = findViewById(R.id.InputLayoutImpGrav10Compra);
        ilimpgrav5 = findViewById(R.id.InputLayoutImpGrav5Compra);
        ilimpexe = findViewById(R.id.InputLayoutImpExentaCompra);


        spinnerTipoDocumento = findViewById(R.id.spinnerTipoDocumentoCompra);
        spinnerTipoEgreso = findViewById(R.id.spinnerTipoEgreso);
        spinnerClasTipoEgreso = findViewById(R.id.spinnerClasEgreso);
        spinnerProveedor = findViewById(R.id.spinnerProveedor);

        tvimpgrav10 = findViewById(R.id.txtimpgrav10FacturaCompra);
        tvimpgrav5 = findViewById(R.id.txtimpgrav5FacturaCompra);
        tvimpexenta = findViewById(R.id.txtimpexentaFacturaCompra);

        tvimp10= findViewById(R.id.txtimp10FacturaCompra);
        tvimp5= findViewById(R.id.txtimp5FacturaCompra);
        tvimpsubtotal = findViewById(R.id.txtsubtotalFacturaCompra);

        tviva10= findViewById(R.id.txtiva10FacturaCompra);
        tviva5 = findViewById(R.id.txtiva5FacturaCompra);
        tvtotaliva = findViewById(R.id.txtivatotalFacturaCompra);

        tvneto = findViewById(R.id.txtnetoFacturaCompra);

        //VALIDAR CANTIDAD HASTA QUE NRO SE PUEDE CARGAR
        tvdia.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        tvmes.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        tvanho.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2060")});

        tvcontribuyente = findViewById(R.id.tvFacturaCompraContribuyente);

        managerUsuario = new SessionManager(getApplicationContext());
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
    private void calculatotales(int valorgravada,int tipo){
        calculaiva(valorgravada,tipo);

        int  ivatotal = Integer.parseInt(tviva10.getText().toString().replace(".",""))
                +Integer.parseInt(tviva5.getText().toString().replace(".",""));
        int  gravadatotal = Integer.parseInt(tvimp10.getText().toString().replace(".",""))
                +Integer.parseInt(tvimp5.getText().toString().replace(".",""));

        int neto = ivatotal +gravadatotal +
                Integer.parseInt(tvimpexenta.getText().toString().replace(".",""));

        formatotextoenableseparadormiles(tvimpsubtotal,gravadatotal);
        formatotextoenableseparadormiles(tvtotaliva,ivatotal);
        formatotextoenableseparadormiles(tvneto,neto);


        //tvimpsubtotal.setText(""+gravadatotal);
        // tvtotaliva.setText(""+ivatotal);
        // tvneto.setText(""+neto);
    }

    private void calculaiva(int valorgravada, int tipoiva) {
        //gravda 10 - 0 gravada5 1
        if (tipoiva==0){
            int iva10=0,gravada10=0;
            iva10 = valorgravada/11 ;
            gravada10 = valorgravada-iva10;
            formatotextoenableseparadormiles(tvimp10,gravada10);
            formatotextoenableseparadormiles(tviva10,iva10);
        }else if(tipoiva==1){
            int iva5=0,gravada5=0;
            iva5 = valorgravada/21 ;
            gravada5 = valorgravada-iva5;
            formatotextoenableseparadormiles(tvimp5,gravada5);
            formatotextoenableseparadormiles(tviva5,iva5);
        }
    }

    private void formatotextoenableseparadormiles(EditText editText,int value){
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        editText.setText(formateador.format(value));
    }

    //METODO PARA GRABAR
    private void save() {
        if(tvimpgrav5.getText().toString().trim().isEmpty()){
            ilimpgrav5.setError("Vacio");
            return;
        }else if(tvimpgrav10.getText().toString().trim().isEmpty()){
            ilimpgrav10.setError("Vacio");
            return;
        }else if(tvimpexenta.getText().toString().trim().isEmpty()){
            ilimpexe.setError("Vacio");
            return;
        }

        //  CALCULA DE NUEVO SI SE CAMBIA UN VALOR DE GRAVADA Y NO SE DA FOCUS
        calculatotales(Integer.parseInt(tvimpgrav10.getText().toString().replace(".","")),0);
        calculatotales(Integer.parseInt(tvimpgrav5.getText().toString().replace(".","")),1);
        //calculatotales(Integer.parseInt(intent.getStringExtra(EXTRA_GRAVADA10VENTA)),0);

        String grava10 = tvimpgrav10.getText().toString().replace(".","");
        String grava5 = tvimpgrav5.getText().toString().replace(".","");
        String exe = tvimpexenta.getText().toString().replace(".","");



        String iva10 = tviva10.getText().toString().replace(".","");
        String iva5 = tviva5.getText().toString().replace(".","");


        String neto = tvneto.getText().toString().replace(".","");

        String contribu = String.valueOf(managerUsuario.ObtenerDatos().getIdcontribuyente());
        String ejercicio =  String.valueOf(managerUsuario.ObtenerDatos().getIdejercicio());

        //Toast.makeText(this,"VALOR CONTR "+contribu,Toast.LENGTH_SHORT).show();

        if(tvdia.getText().toString().trim().isEmpty()){
            ildia.setError("Vacio");
        }else if(tvmes.getText().toString().trim().isEmpty()){
            ilmes.setError("Vacio");
        }else if(tvanho.getText().toString().trim().isEmpty()){
            ilanho.setError("Vacio");
        }else if(adapter_comprobante.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Tipo de Documento",Toast.LENGTH_SHORT).show();
        }else if(adapter_tipoegreso.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Tipo de Egreso",Toast.LENGTH_SHORT).show();
        }else if(adapter_clastipoegreso.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Clasificación de Egreso",Toast.LENGTH_SHORT).show();
        }else if(adapter_proveedor.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Proveedor",Toast.LENGTH_SHORT).show();
        }else if(tvnro1.getText().toString().trim().isEmpty()){
            ilnro1.setError("Vacio");
        }else if(tvnro2.getText().toString().trim().isEmpty()){
            ilnro2.setError("Vacio");
        }else if(tvnro3.getText().toString().trim().isEmpty()){
            ilnro3.setError("Vacio");
        }else if(!tvanho.getText().toString().trim().equals(ejercicioactual.trim())){
            ilanho.setError("Año no Corresponde al ejercicio");
        }else if(contribu.trim() == null){
            Toast.makeText(this,"Contribuyente no existe",Toast.LENGTH_SHORT).show();
        }else if(ejercicio.trim() == null){
            Toast.makeText(this,"Ejercicio no existe",Toast.LENGTH_SHORT).show();
        }else if(neto.trim().equals("0")){
            Toast.makeText(this,"Valor Neto No Puede Estar en Cero",Toast.LENGTH_SHORT).show();
        }
        else{
            //VALIDA A LA HORA DE GRABAR LA CANTIDAD DE CARACTERES EN LOS CAMPOS
            //DIA MES AÑO
            verificacantidadcaractermayor(tvdia,2);
            verificacantidadcaractermayor(tvmes,2);
            verificacantidadcaractermayor(tvanho,4);

            //AQUI LO CANTRARIO SI ES MENOR DEBE COMPLETAR DE NUEVO CON CEROS NRO1, NRO2 Y NRO3
            verificacantidadcaractermayor(tvnro1,3);
            verificacantidadcaractermayor(tvnro2,3);
            verificacantidadcaractermayor(tvnro3,7);

            String dia = tvdia.getText().toString();
            String mes = tvmes.getText().toString();
            String anho = tvanho.getText().toString();

            String nro1 = tvnro1.getText().toString();
            String nro2 = tvnro2.getText().toString();
            String nro3 = tvnro3.getText().toString();
            //si tienen todos cero
            if (nro1.trim().equals("000")||nro2.trim().equals("000")||nro3.trim().equals("0000000")){
                Toast.makeText(this,"Campos Nro.1, Nro.2 o Nro. 3 Tienes todos los valores en cero",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Intent data = new Intent();
            data.putExtra(EXTRA_DIACOMPRA,dia);
            data.putExtra(EXTRA_TIPOCOMPROBANTE,codtipodocumento);
            data.putExtra(EXTRA_FECHAFACTURACOMPRA,codtipodocumento);
            data.putExtra(EXTRA_PROVEEDOR,codproveedor);
            data.putExtra(EXTRA_TIPOEGRESO,codtipoegreso);
            data.putExtra(EXTRA_CLASIFICACIONEGRESO,codclasegreso);
            data.putExtra(EXTRA_NROFACTURA, nro1.trim()+"-"+nro2.trim()+"-"+nro3.trim());
            data.putExtra(EXTRA_CONTRIBUYENTE,contribu);
            data.putExtra(EXTRA_EJERCICIO,ejercicio);
            data.putExtra(EXTRA_TOTALCOMPRA,neto);
            data.putExtra(EXTRA_GRAVADA10COMPRA,grava10);
            data.putExtra(EXTRA_GRAVADA5COMPRA,grava5);
            data.putExtra(EXTRA_EXENTACOMPRA,exe);
            data.putExtra(EXTRA_IVA10COMPRA,iva10);
            data.putExtra(EXTRA_IVA5COMPRA,iva5);
            data.putExtra(EXTRA_NRO1COMPRA,nro1);
            data.putExtra(EXTRA_NRO2COMPRA,nro2);
            data.putExtra(EXTRA_NRO3COMPRA,nro3);
            data.putExtra(EXTRA_DIACOMPRA,dia);
            data.putExtra(EXTRA_MESCOMPRA,mes);
            data.putExtra(EXTRA_ANHOCOMPRA,anho);

            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDFACTURACOMPRA,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDFACTURACOMPRA,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_contribuyente,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_contribuyente:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
