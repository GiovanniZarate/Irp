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

import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.InputFilterMinMax;
import com.proyecto.irp.Utilitario.TextValidator;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.ui.clasingreso.ClasIngresoViewModel;
import com.proyecto.irp.ui.cliente.ClienteViewModel;
import com.proyecto.irp.ui.tipocomprobante.TipoComprobanteViewModel;

import java.util.List;

public class VentaCargaAddActivity extends AppCompatActivity  {

    EditText tvdia,tvmes,tvanho,tvnro1,tvnro2,tvnro3;
    Spinner spinnerTipoDocumento,spinnerTipoIngreso,spinnerCliente;

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


        cargacombocomprobante();
        cargacombotipoingreso();
        cargacombocliente();
       /* tvdia.addTextChangedListener(new TextValidator(tvdia)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
            }
        });*/

        //para completar con cero el nro factura
       validanrofactura(tvnro1,3,"1");
       validanrofactura(tvnro2,3,"2");
       validanrofactura(tvnro3,7,"3");
}



    private void completacero(EditText editText,int cantidad) {
        int l = 0;
        String aux = "";
        aux = editText.getText().toString().trim();
        int vnro2 = Integer.parseInt(editText.getText().toString());
        while (l < cantidad) {
            if (aux.length() >= cantidad) {
                aux = aux;
                l = 3;
            } else {
                aux = "0" + aux;
                l = aux.length();
            }
        }
        editText.setText(aux);
    }


    private void validanrofactura(final EditText edtnrofactura, final int cantidad, final String msg){
        //PARA QUE COMPLETE CON CERO
        edtnrofactura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus ){
                    if (edtnrofactura.getText().toString().isEmpty()){
                        edtnrofactura.setError("Debe Ingresar Nro "+msg);
                    }else{
                        completacero(edtnrofactura,cantidad);
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


    private void inicializacion() {
        tvdia = findViewById(R.id.txtdiaFacturaVenta);
        tvmes = findViewById(R.id.txtmesFacturaVenta);
        tvanho = findViewById(R.id.txtanhoFacturaVenta);
        tvnro1 = findViewById(R.id.txtnro1FacturaVenta);
        tvnro2 = findViewById(R.id.txtnro2FacturaVenta);
        tvnro3 = findViewById(R.id.txtnro3FacturaVenta);

        spinnerTipoDocumento = findViewById(R.id.spinnerTipoDocumento);
        spinnerTipoIngreso = findViewById(R.id.spinnerTipoIngreso);
        spinnerCliente = findViewById(R.id.spinnerCliente);

        //VALIDAR CANTIDAD HASTA QUE NRO SE PUEDE CARGAR
        tvdia.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        tvmes.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        tvanho.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2060")});
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
