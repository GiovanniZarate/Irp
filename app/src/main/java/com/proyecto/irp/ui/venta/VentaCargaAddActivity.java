package com.proyecto.irp.ui.venta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
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
import java.util.List;

public class VentaCargaAddActivity extends AppCompatActivity  {

    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDFACTURAVETNA = "com.proyecto.irp.ui.venta.EXTRA_IDFACTURAVETNA";


    EditText tvdia,tvmes,tvanho,tvnro1,tvnro2,tvnro3,tvimpgrav10,tvimpgrav5,tvimpexenta,
    tvimp10,tvimp5,tvimpsubtotal,tviva10,tviva5,tvtotaliva,tvneto;
    Spinner spinnerTipoDocumento,spinnerTipoIngreso,spinnerCliente;
    TextInputLayout ilnro1,ilnro2,ilnro3;



    private TipoComprobanteViewModel tipoComprobanteViewModel;
    private List<TipoComprobante> combocomprobante;

    private ClasIngresoViewModel clasIngresoViewModel;
    private List<ClasificacionIngreso> combotipoingreso;

    private ClienteViewModel clienteViewModel;
    private List<Cliente> combocliente;


    ArrayAdapter adapter_comprobante,adapter_tipoingreso,adapter_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_carga_add);

        inicializacion();
        eventos();

        tipoComprobanteViewModel = ViewModelProviders.of(this).get(TipoComprobanteViewModel.class);
        clasIngresoViewModel = ViewModelProviders.of(this).get(ClasIngresoViewModel.class);
        clienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);

        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);
        setTitle("Ingreso - 2020");

        cargacombocomprobante();
        cargacombotipoingreso();
        cargacombocliente();

        //para completar con cero el nro factura
       validanrofactura(tvnro1,3,"1",ilnro1);
       validanrofactura(tvnro2,3,"2",ilnro2);
       validanrofactura(tvnro3,7,"3",ilnro3);

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

                        calculatotales();
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        int valor10=0,iva10=0,gravada10=0;
                        valor10 = Integer.parseInt(tvimpgrav10.getText().toString().replace(".",""));
                        //Math.round(Integer.parseInt(v_jtgrav10) * 0.1)
                        //iva10 = (int) Math.round(valor10/11 * 0.1);
                        iva10 = valor10/11 ;
                        gravada10 = valor10-iva10;

                        formatotextoenableseparadormiles(tvimp10,gravada10);
                        formatotextoenableseparadormiles(tviva10,iva10);
                        //tvimp10.setText(""+gravada10);
                        //tviva10.setText(""+iva10);
                        calculatotales();
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

                        calculatotales();
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        int valor5=0,iva5=0,gravada5=0;
                        valor5 = Integer.parseInt(tvimpgrav5.getText().toString().replace(".",""));
                        //iva5 = (int) Math.round(valor5/21 * 0.1);
                        iva5 = valor5/21 ;
                        gravada5 = valor5-iva5;

                        formatotextoenableseparadormiles(tvimp5,gravada5);
                        formatotextoenableseparadormiles(tviva5,iva5);
                        //tvimp5.setText(""+gravada5);
                        //tviva5.setText(""+iva5);

                        calculatotales();
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
                        calculatotales();
                    }else{
                        //CALCULAR IVA GRAVADA Y PASAR A LOS TEXTOS
                        calculatotales();
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

       //seleccionar valor text desde hasta
       // campo1.setSelection(start, end);
        //Seleccionar completo el Texto
        //campo1.selectAll();
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
    }

    private void calculatotales(){
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

   private void formatotextoenableseparadormiles(EditText editText,int value){
       DecimalFormat formateador = new DecimalFormat("###,###.##");
       editText.setText(formateador.format(value));
   }


    private void completacero(EditText editText,int cantidad) {
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


    private void validanrofactura(final EditText edtnrofactura, final int cantidad, final String msg,
                                  final TextInputLayout textInputLayout){
        //PARA QUE COMPLETE CON CERO
        edtnrofactura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus ){
                    if (edtnrofactura.getText().toString().isEmpty()){
                        //edtnrofactura.setError("Debe Ingresar Nro "+msg);
                        textInputLayout.setError("Ingresar Nro "+msg);
                    }else{
                        completacero(edtnrofactura,cantidad);
                        textInputLayout.setError("");
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
               // save();
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
