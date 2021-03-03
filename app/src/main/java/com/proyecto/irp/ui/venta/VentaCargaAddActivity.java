package com.proyecto.irp.ui.venta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.proyecto.irp.Config.SessionManager;
import com.proyecto.irp.NuevoEJercicioDialogFragment;
import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.InputFilterMinMax;
import com.proyecto.irp.Utilitario.TextValidator;
import com.proyecto.irp.Utilitario.separador_miles;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.ui.clasingreso.ClasIngresoViewModel;
import com.proyecto.irp.ui.cliente.ClienteViewModel;
import com.proyecto.irp.ui.tipocomprobante.TipoComprobanteViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class VentaCargaAddActivity extends AppCompatActivity  {

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDFACTURAVENTA = "com.proyecto.irp.ui.venta.EXTRA_IDFACTURAVETNA";
    public static final String EXTRA_FECHAFACTURAVENTA = "com.proyecto.irp.ui.venta.EXTRA_FECHAFACTURAVENTA";
    public static final String EXTRA_CLIENTE = "com.proyecto.irp.ui.venta.EXTRA_CLIENTE";
    public static final String EXTRA_CLIENTESELECTED = "com.proyecto.irp.ui.venta.EXTRA_CLIENTESELECTED";
    public static final String EXTRA_CLASIFICACIONINGRESO = "com.proyecto.irp.ui.venta.EXTRA_CLASIFICACIONINGRESO";
    public static final String EXTRA_CLASIFICACIONINGRESOSELECTED = "com.proyecto.irp.ui.venta.EXTRA_CLASIFICACIONINGRESOSELECTED";
    public static final String EXTRA_TIPOCOMPROBANTE = "com.proyecto.irp.ui.venta.EXTRA_TIPOCOMPROBANTE";
    public static final String EXTRA_TIPOCOMPROBANTESELECTED = "com.proyecto.irp.ui.venta.EXTRA_TIPOCOMPROBANTESELECTED";
    public static final String EXTRA_CONTRIBUYENTE = "com.proyecto.irp.ui.venta.EXTRA_CONTRIBUYENTE";
    public static final String EXTRA_EJERCICIO = "com.proyecto.irp.ui.venta.EXTRA_EJERCICIO";
    public static final String EXTRA_NROFACTURA = "com.proyecto.irp.ui.venta.EXTRA_NROFACTURA";
    public static final String EXTRA_TOTALVENTA = "com.proyecto.irp.ui.venta.EXTRA_TOTALVENTA";
    public static final String EXTRA_EXENTAVENTA = "com.proyecto.irp.ui.venta.EXTRA_EXENTAVENTA";
    public static final String EXTRA_GRAVADA10VENTA = "com.proyecto.irp.ui.venta.EXTRA_GRAVADA10VENTA";
    public static final String EXTRA_IVA10VENTA = "com.proyecto.irp.ui.venta.EXTRA_IVA10VENTA";
    public static final String EXTRA_GRAVADA5VENTA = "com.proyecto.irp.ui.venta.EXTRA_GRAVADA5VENTA";
    public static final String EXTRA_IVA5VENTA = "com.proyecto.irp.ui.venta.EXTRA_IVA5VENTA";
    public static final String EXTRA_NRO1VENTA = "com.proyecto.irp.ui.venta.EXTRA_NRO1VENTA";
    public static final String EXTRA_NRO2VENTA = "com.proyecto.irp.ui.venta.EXTRA_NRO2VENTA";
    public static final String EXTRA_NRO3VENTA = "com.proyecto.irp.ui.venta.EXTRA_NRO3VENTA";
    public static final String EXTRA_DIAVENTA = "com.proyecto.irp.ui.venta.EXTRA_DIAVENTA";
    public static final String EXTRA_MESVENTA = "com.proyecto.irp.ui.venta.EXTRA_MESVENTA";
    public static final String EXTRA_ANHOVENTA = "com.proyecto.irp.ui.venta.EXTRA_ANHOVENTA";


    EditText tvdia,tvmes,tvanho,tvnro1,tvnro2,tvnro3,tvimpgrav10,tvimpgrav5,tvimpexenta,
    tvimp10,tvimp5,tvimpsubtotal,tviva10,tviva5,tvtotaliva,tvneto;

    Spinner spinnerTipoDocumento,spinnerTipoIngreso,spinnerCliente;

    TextInputLayout ilnro1,ilnro2,ilnro3,ildia,ilmes,ilanho,ilimpgrav10,ilimpgrav5,ilimpexe;

    TextView tvcontribuyente;

    Button btnAddCliente;

    String codtipodocumento,codtipoingreso,codcliente,ejercicioactual;

    private TipoComprobanteViewModel tipoComprobanteViewModel;
    private List<TipoComprobante> combocomprobante;

    private ClasIngresoViewModel clasIngresoViewModel;
    private List<ClasificacionIngreso> combotipoingreso;

    private ClienteViewModel clienteViewModel;
    private List<Cliente> combocliente;


    ArrayAdapter adapter_comprobante,adapter_tipoingreso,adapter_cliente;

    SessionManager managerUsuario;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_carga_add);

        inicializacion();
        intent = getIntent();
        eventos();

        tipoComprobanteViewModel = ViewModelProviders.of(this).get(TipoComprobanteViewModel.class);
        clasIngresoViewModel = ViewModelProviders.of(this).get(ClasIngresoViewModel.class);
        clienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);
        setTitle("Ingreso - "+managerUsuario.ObtenerDatos().getAnho());

        cargacombocomprobante();
        cargacombotipoingreso();
       // cargacombocliente();

        clienteViewModel.getAllClientes().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                adapter_cliente = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,
                        clientes);
                adapter_cliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCliente.setAdapter(adapter_cliente);

                //PARA MODIFICAR SELECCIONA EL VALOR SELECCIONADO
                if (intent.hasExtra(EXTRA_IDFACTURAVENTA)){
                    for (int i = 0; i <  spinnerCliente.getAdapter().getCount(); i++){
                        if (spinnerCliente.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_CLIENTESELECTED).toString().trim())){
                            spinnerCliente.setSelection(i);
                            break;
                        }
                }
                }
            }
        });


        //para completar con cero el nro factura
        completacero(tvnro1,3,"1",ilnro1);
        completacero(tvnro2,3,"2",ilnro2);
        completacero(tvnro3,7,"3",ilnro3);

       //PARA COMPLETAR DIA Y MES
        completacero(tvdia,2,"",ildia);
        completacero(tvmes,2,"",ilmes);
        completacero(tvanho,4,"",ilanho);

        //eventos campo importes
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
                       /* String valorcargado10= tvimpgrav10.getText().toString();
                        int longitudcarcater =  valorcargado10.trim().length();
                        if (longitudcarcater > 3){
                            int posicioninicial = longitudcarcater - 4;
                            int posicionfinal = posicioninicial +1;
                            String valorseparador = valorcargado10.substring(posicioninicial,posicionfinal);
                                    Toast.makeText(v.getContext(),"valor del 10% "+valorcargado10+
                                            "separador :"+valorcargado10.substring(posicioninicial,posicionfinal)
                                    , Toast.LENGTH_SHORT).show();
                            if (valorseparador.equals(",")){
                                valor10 = Integer.parseInt(tvimpgrav10.getText().toString().replace(",",""));
                            }else if (valorseparador.equals(".")){
                                valor10 = Integer.parseInt(tvimpgrav10.getText().toString().replace(".",""));
                            }
                        }else {*/
                            valor10 = Integer.parseInt(tvimpgrav10.getText().toString().replace(".",""));
                       // }

                        calculatotales(valor10,0);
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

        tvimpgrav10.addTextChangedListener(new separador_miles(tvimpgrav10));
        tvimpgrav5.addTextChangedListener(new separador_miles(tvimpgrav5));
        tvimpexenta.addTextChangedListener(new separador_miles(tvimpexenta));

        //PARA SABER SI SE VA A EDITAR O AGREGAR NUEVO
        modoAgregarEditar();

        //CAPTURAR VALOR SELECCIONADO COMOBO
        capturaCombo();

       //seleccionar valor text desde hasta
       // campo1.setSelection(start, end);
        //Seleccionar completo el Texto
        //campo1.selectAll();
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
        //COMBO TIPO INGRESO
        spinnerTipoIngreso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClasificacionIngreso clasificacionIngreso = (ClasificacionIngreso) spinnerTipoIngreso.getAdapter().getItem(position);
                codtipoingreso = String.valueOf(clasificacionIngreso.getIdclasificacioningreso());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        //COMBO CLIENTE
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) spinnerCliente.getAdapter().getItem(position);
                codcliente = String.valueOf(cliente.getIdcliente());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


    }

    private void inicializacion() {
        tvdia = findViewById(R.id.txtdiaFacturaVenta);
        tvmes = findViewById(R.id.txtmesFacturaVenta);
        tvanho = findViewById(R.id.txtanhoFacturaVenta);
        tvnro1 = findViewById(R.id.txtnro1FacturaVenta);
        tvnro2 = findViewById(R.id.txtnro2FacturaVenta);
        tvnro3 = findViewById(R.id.txtnro3FacturaVenta);
        ilnro1 = findViewById(R.id.InputLayoutNro1);
        ilnro2 = findViewById(R.id.InputLayoutNro2);
        ilnro3 = findViewById(R.id.InputLayoutNro3);



        ildia = findViewById(R.id.InputLayoutDia);
        ilmes = findViewById(R.id.InputLayoutMes);
        ilanho = findViewById(R.id.InputLayoutAnho);

        ilimpgrav10 = findViewById(R.id.InputLayoutImpGrav10);
        ilimpgrav5 = findViewById(R.id.InputLayoutImpGrav5);
        ilimpexe = findViewById(R.id.InputLayoutImpExenta);


        spinnerTipoDocumento = findViewById(R.id.spinnerTipoDocumento);
        spinnerTipoIngreso = findViewById(R.id.spinnerTipoIngreso);
        spinnerCliente = findViewById(R.id.spinnerCliente);

        tvimpgrav10 = findViewById(R.id.txtimpgrav10FacturaVenta);
        tvimpgrav5 = findViewById(R.id.txtimpgrav5FacturaVenta);
        tvimpexenta = findViewById(R.id.txtimpexentaFacturaVenta);

        tvimp10= findViewById(R.id.txtimp10FacturaVenta);
        tvimp5= findViewById(R.id.txtimp5FacturaVenta);
        tvimpsubtotal = findViewById(R.id.txtsubtotalFacturaVenta);

        tviva10= findViewById(R.id.txtiva10FacturaVenta);
        tviva5 = findViewById(R.id.txtiva5FacturaVenta);
        tvtotaliva = findViewById(R.id.txtivatotalFacturaVenta);

        tvneto = findViewById(R.id.txtnetoFacturaVenta);

        //VALIDAR CANTIDAD HASTA QUE NRO SE PUEDE CARGAR
        tvdia.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        tvmes.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        tvanho.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2060")});

        tvcontribuyente = findViewById(R.id.tvFacturaVentaContribuyente);

        managerUsuario = new SessionManager(getApplicationContext());

        btnAddCliente = findViewById(R.id.btnAgregarCliente);
    }

    private void modoAgregarEditar() {
        ejercicioactual = String.valueOf(managerUsuario.ObtenerDatos().getAnho());
        tvcontribuyente.setText(managerUsuario.ObtenerDatos().getRuc().trim()+" - "+managerUsuario.ObtenerDatos().getNombrecontribuyente().trim());
        //para poner titulo si es agregar o modificar
         //intent = getIntent();
        if (intent.hasExtra(EXTRA_IDFACTURAVENTA)){
            setTitle("Editar Ingreso");
            recibirValoresEditar();
            tvdia.requestFocus();
        }else {
            setTitle("Agregar Ingreso");

            if (ejercicioactual.trim()==null){
                Toast.makeText(this,"Debes Crear Ejercicio ",Toast.LENGTH_LONG).show();
            }else {
                tvanho.setText(ejercicioactual);
            }

        }
    }

    private void recibirValoresEditar() {
        tvdia.setText(intent.getStringExtra(EXTRA_DIAVENTA));
        tvmes.setText(intent.getStringExtra(EXTRA_MESVENTA));
        tvanho.setText(intent.getStringExtra(EXTRA_ANHOVENTA));
        for (int i = 0; i <  spinnerTipoDocumento.getAdapter().getCount(); i++){
            if (spinnerTipoDocumento.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_TIPOCOMPROBANTESELECTED).toString().trim())){
                spinnerTipoDocumento.setSelection(i);
                break;
            }
       }
        for (int i = 0; i <  spinnerTipoIngreso.getAdapter().getCount(); i++){
            if (spinnerTipoIngreso.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_CLASIFICACIONINGRESOSELECTED).toString().trim())){
                spinnerTipoIngreso.setSelection(i);
                break;
            }
        }
        /*for (int i = 0; i <  spinnerCliente.getAdapter().getCount(); i++){
            if (spinnerCliente.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_CLIENTESELECTED).toString().trim())){
                spinnerCliente.setSelection(i);
                break;
            }
        }*/
        tvnro1.setText(intent.getStringExtra(EXTRA_NRO1VENTA));
        tvnro2.setText(intent.getStringExtra(EXTRA_NRO2VENTA));
        tvnro3.setText(intent.getStringExtra(EXTRA_NRO3VENTA));

        //Toast.makeText(this,"valor grav10 "+intent.getStringExtra(EXTRA_GRAVADA10VENTA),Toast.LENGTH_SHORT).show();

        tvimpgrav10.setText(intent.getStringExtra(EXTRA_GRAVADA10VENTA));
        tvimpgrav5.setText(intent.getStringExtra(EXTRA_GRAVADA5VENTA));
        tvimpexenta.setText(intent.getStringExtra(EXTRA_EXENTAVENTA));

        formatotextoenableseparadormiles(tviva10,Integer.parseInt(intent.getStringExtra(EXTRA_IVA10VENTA)));
        formatotextoenableseparadormiles(tviva5,Integer.parseInt(intent.getStringExtra(EXTRA_IVA5VENTA)));

        //tviva10.setText(intent.getStringExtra(EXTRA_IVA10VENTA));
        //tviva5.setText(intent.getStringExtra(EXTRA_IVA5VENTA));
        calculatotales(Integer.parseInt(intent.getStringExtra(EXTRA_GRAVADA10VENTA)),0);
        calculatotales(Integer.parseInt(intent.getStringExtra(EXTRA_GRAVADA5VENTA)),1);
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

    //ANTES DE GRABAR QUE VUELVA A RECALCULAR EL TODAL
    /*private void calculatotalgrabar(){
        int  ivatotal = Integer.parseInt(tviva10.getText().toString().replace(".",""))
                +Integer.parseInt(tviva5.getText().toString().replace(".",""));
        int  gravadatotal = Integer.parseInt(tvimp10.getText().toString().replace(".",""))
                +Integer.parseInt(tvimp5.getText().toString().replace(".",""));



        int neto = ivatotal +gravadatotal +
                Integer.parseInt(tvimpexenta.getText().toString().replace(".",""));

        Toast.makeText(this,"ivatotal "+ivatotal+ "gravadatotal "+gravadatotal+" neto "+neto,Toast.LENGTH_LONG).show();

        formatotextoenableseparadormiles(tvimpsubtotal,gravadatotal);
        formatotextoenableseparadormiles(tvtotaliva,ivatotal);
        formatotextoenableseparadormiles(tvneto,neto);
    }*/


   private void formatotextoenableseparadormiles(EditText editText,int value){
      // DecimalFormat formateador = new DecimalFormat("###,###.##");
       DecimalFormat formateador = new DecimalFormat("###,###.##", new DecimalFormatSymbols(new Locale("es","PY")));
       editText.setText(formateador.format(value));
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
    private void cargacombocomprobante() {
            combocomprobante =  tipoComprobanteViewModel.getAllTipoComprobanteCombo();
            adapter_comprobante = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                    combocomprobante);
            adapter_comprobante.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipoDocumento.setAdapter(adapter_comprobante);
    }

    private void cargacombotipoingreso() {
        combotipoingreso =  clasIngresoViewModel.getAllTipoIngresoCombo();
        adapter_tipoingreso = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                combotipoingreso);
        adapter_tipoingreso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoIngreso.setAdapter(adapter_tipoingreso);
    }
    private void cargacombocliente() {
        combocliente =  clienteViewModel.getAllClienteCombo();
        adapter_cliente = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                combocliente);
        adapter_cliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adapter_cliente);
    }

    private void eventos() {
       //tvdia.addTextChangedListener(this);
       //tvmes.addTextChangedListener(this);
       //tvanho.addTextChangedListener(this);

        //btnAddCliente.setOnClickListener(this);
        btnAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INSTANCIAR EL FRAGMENTO CON EL GESTOR DE FRAGMENTO
                FragmentManager fm =getSupportFragmentManager();
                AddClienteDialogFragment dialogNuevoEjercicio = new AddClienteDialogFragment();
                dialogNuevoEjercicio.show(fm,"AddClienteDialogFragment");
            }
        });
    }



    //METODO PARA GRABAR
    private void save() {

        /*else if(grava10.trim().isEmpty()){
            ilimpgrav10.setError("Vacio");
        }else if(grava5.trim().isEmpty()){
            ilimpgrav5.setError("Vacio");
        }else if(exe.trim().isEmpty()){
            ilimpexe.setError("Vacio");
        }*/



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
        }else if(adapter_tipoingreso.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Tipo de Ingreso",Toast.LENGTH_SHORT).show();
        }else if(adapter_cliente.isEmpty()){
            Toast.makeText(this,"Debes Seleccionar Cliente",Toast.LENGTH_SHORT).show();
        }else if(tvnro1.getText().toString().trim().isEmpty()){
            ilnro1.setError("Vacio");
        }else if(tvnro2.getText().toString().trim().isEmpty()){
            ilnro2.setError("Vacio");
        }else if(tvnro3.getText().toString().trim().isEmpty()){
            ilnro3.setError("Vacio");
        }else if(!tvanho.getText().toString().trim().equals(ejercicioactual.trim())){
            //Toast.makeText(this,"valor cargado "+tvanho.getText().toString().trim()+" valor del ejercio"+ejercicioactual.trim(),Toast.LENGTH_LONG).show();
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
            data.putExtra(EXTRA_DIAVENTA,dia);
            data.putExtra(EXTRA_TIPOCOMPROBANTE,codtipodocumento);
            data.putExtra(EXTRA_FECHAFACTURAVENTA,codtipodocumento);
            data.putExtra(EXTRA_CLIENTE,codcliente);
            data.putExtra(EXTRA_CLASIFICACIONINGRESO,codtipoingreso);
            data.putExtra(EXTRA_NROFACTURA, nro1.trim()+"-"+nro2.trim()+"-"+nro3.trim());
            data.putExtra(EXTRA_CLASIFICACIONINGRESO,codtipoingreso);
            data.putExtra(EXTRA_CONTRIBUYENTE,contribu);
            data.putExtra(EXTRA_EJERCICIO,ejercicio);
            data.putExtra(EXTRA_TOTALVENTA,neto);
            data.putExtra(EXTRA_GRAVADA10VENTA,grava10);
            data.putExtra(EXTRA_GRAVADA5VENTA,grava5);
            data.putExtra(EXTRA_EXENTAVENTA,exe);
            data.putExtra(EXTRA_IVA10VENTA,iva10);
            data.putExtra(EXTRA_IVA5VENTA,iva5);
            data.putExtra(EXTRA_NRO1VENTA,nro1);
            data.putExtra(EXTRA_NRO2VENTA,nro2);
            data.putExtra(EXTRA_NRO3VENTA,nro3);
            data.putExtra(EXTRA_DIAVENTA,dia);
            data.putExtra(EXTRA_MESVENTA,mes);
            data.putExtra(EXTRA_ANHOVENTA,anho);

            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDFACTURAVENTA,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDFACTURAVENTA,id);
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
    /*@Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaClasEgreso:
                save();
                //Toast.makeText(this,"valor  "+codegreso+" - "+desegreso,Toast.LENGTH_SHORT).show();
                break;
        }
    }*/

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

 /*   @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }*/
}
