package com.proyecto.irp.ui.clasegreso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.proyecto.irp.R;
import com.proyecto.irp.db.entity.TipoEgreso;
import com.proyecto.irp.ui.tipoegreso.TipoEgresoViewModel;


import java.util.List;

public class ClasEgresoAddActivity extends AppCompatActivity implements View.OnClickListener {


    //PARAMETRO EXTRA PARA PASAR AL OTRO ACTIVIY
    public static final String EXTRA_IDCLASEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_IDCLASEGRESO";
    public static final String EXTRA_DESCRICLASEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_DESCRICLASEGRESO";
    public static final String EXTRA_TIPOEGRESO = "com.proyecto.irp.ui.clasegreso.EXTRA_TIPOEGRESO";
    public static final String EXTRA_TIPOEGRESOSELECTED = "com.proyecto.irp.ui.clasegreso.EXTRA_TIPOEGRESOSELECTED";

    String desegreso;
    String codegreso;
    EditText vdescripcion;
    Button btnRegistrar;
    Spinner spinnerTipoEgreso;

    //int seleccionado;
    //String selectedcombo=null;

    private TipoEgresoViewModel tipoEgresoViewModel;
    private List<TipoEgreso> datoscombo;
   // private List<Cliente> clientes = new ArrayList<>();

    ArrayAdapter spinner_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clas_egreso_add);

        inicializacion();
        eventos();

        tipoEgresoViewModel = ViewModelProviders.of(this).get(TipoEgresoViewModel.class);
        //PONER EN EL TOOLBAR PARA CERRAR
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_volver);

        cargarcombo();

        //para poner titulo si es agregar o modificar
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDCLASEGRESO)){
            setTitle("Editar Clasif. de Egreso");
            vdescripcion.setText(intent.getStringExtra(EXTRA_DESCRICLASEGRESO));
           // spinnerTipoEgreso.setPromptId(Integer.parseInt(intent.getStringExtra(EXTRA_TIPOEGRESO)));

           // seleccionado = Integer.parseInt(intent.getStringExtra(EXTRA_TIPOEGRESO));
            //selectedcombo = intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED);

            /*for (int i = 0; i <  sp_tipo_personeria.getAdapter().getCount(); i++){
                if ( sp_tipo_personeria.getAdapter().getItem(i).equals(tipoPersoneria)){
                    sp_tipo_documento.setSelection(i);
                    break;
                }
            }*/

           // Toast.makeText(this,"valor selecte "+intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED), Toast.LENGTH_SHORT).show();
            for (int i = 0; i <  spinnerTipoEgreso.getAdapter().getCount(); i++){
                if ( spinnerTipoEgreso.getAdapter().getItem(i).toString().trim().equals(intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED).toString().trim())){
                    spinnerTipoEgreso.setSelection(i);
                    break;
                }
            }



            //Toast.makeText(this,"valor selecte "+intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED), Toast.LENGTH_SHORT).show();

           // int spinnerPosition = spinner_adapter.getPosition(intent.getStringExtra(EXTRA_TIPOEGRESOSELECTED));

            //Toast.makeText(this,"valor position "+String.valueOf(spinnerPosition), Toast.LENGTH_SHORT).show();
            //spinnerTipoEgreso.setSelection(spinnerPosition);



            //spinnerTipoEgreso.get
            //spinnerTipoEgreso.getposition
            //spinnerTipoEgreso.setSelection(1);

            //spinnerTipoEgreso.getSelectedItemPosition();
           // spinnerTipoEgreso.setTag(1,datoscombo);

            //spinnerTipoEgreso.setId(Integer.parseInt(intent.getStringExtra(EXTRA_TIPOEGRESO)));
            //spinnerTipoEgreso.


            //int pos = getSpinnerField().getAdapter().indexOf(value);
           // getSpinnerField().setSelection(pos);

            //int pos = spinnerTipoEgreso.getAdapter().i

           // spinnerTipoEgreso.setSelection(((ArrayAdapter)spinnerTipoEgreso.getAdapter())
            //        .getPosition(intent.getStringExtra(EXTRA_TIPOEGRESO)));
            //spinnerTipoEgreso.setSelection(ArrayAdapter<TipoEgreso>)spin;


        }else {
            setTitle("Agregar Clasif. de Egreso");
        }

        //CARGAR COMBO

        spinnerTipoEgreso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoEgreso tipoEgreso = (TipoEgreso) spinnerTipoEgreso.getAdapter().getItem(position);
                codegreso = String.valueOf(tipoEgreso.getIdtipoegreso());
                desegreso = tipoEgreso.getDescripciontipoegreso();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void cargarcombo() {
            datoscombo =  tipoEgresoViewModel.getAllTipoegresoCombo();
            /*ArrayAdapter<TipoEgreso> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,  datoscombo);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipoEgreso.setAdapter(dataAdapter);*/


        spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                datoscombo);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerTipoEgreso.setPrompt("Seleccione Tipo Egreso");

        //if (seleccionado != null) {

       // }
        spinnerTipoEgreso.setAdapter(spinner_adapter);

       /* Toast.makeText(this,"CARGO EL COMOBO", Toast.LENGTH_SHORT).show();

        Toast.makeText(this,"VALOR SELECTED "+selectedcombo, Toast.LENGTH_SHORT).show();
        if (selectedcombo != null) {
            int spinnerPosition = spinner_adapter.getPosition(selectedcombo);

            Toast.makeText(this,"VALOR POSITION"+spinnerPosition, Toast.LENGTH_SHORT).show();
            spinnerTipoEgreso.setSelection(spinnerPosition);
        }*/
        //int spinnerPosition = spinner_adapter.getPosition("TIPO EGRESO");
       // Toast.makeText(this,"valor position "+String.valueOf(spinnerPosition), Toast.LENGTH_SHORT).show();
       // spinnerTipoEgreso.setSelection(spinnerPosition);

    }

    private void inicializacion() {
        vdescripcion = findViewById(R.id.txtDescripcionClasEgreso);
        btnRegistrar = findViewById(R.id.btnGrabaClasEgreso);
        spinnerTipoEgreso = findViewById(R.id.spinnerTipoEgreso);
    }

    private void eventos() {
        //DEFINIR EL EVENTO CLIC SOBRE EL BOTON LOGIN
        btnRegistrar.setOnClickListener((View.OnClickListener) this);
    }

    //METODO PARA GRABAR CONTRIBUYENTE
    private void save() {
        String descripcion = vdescripcion.getText().toString();


        if(descripcion.trim().isEmpty()){
            vdescripcion.setError("Debes ingresar descripción");
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_DESCRICLASEGRESO,descripcion);
            data.putExtra(EXTRA_TIPOEGRESO,codegreso);


            //VERIFICAR SI ES MODIFICAR O NUEVO
            int id = getIntent().getIntExtra(EXTRA_IDCLASEGRESO,-1);
            if (id != -1){
                data.putExtra(EXTRA_IDCLASEGRESO,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGrabaClasEgreso:
               save();
               //Toast.makeText(this,"valor  "+codegreso+" - "+desegreso,Toast.LENGTH_SHORT).show();
                break;
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
