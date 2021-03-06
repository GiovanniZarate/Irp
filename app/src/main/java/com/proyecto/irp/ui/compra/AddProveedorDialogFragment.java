package com.proyecto.irp.ui.compra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.proyecto.irp.R;
import com.proyecto.irp.Utilitario.CalculaDivisor;
import com.proyecto.irp.db.entity.Ejercicio;
import com.proyecto.irp.db.entity.Proveedor;
import com.proyecto.irp.ui.proveedor.ProveedorViewModel;
import com.proyecto.irp.viewmodel.NuevoEJercicioDialogViewModel;

//public class NuevoEJercicioDialogFragment extends Fragment {
//CAMBIAR A DIALOGFRAGMENT
public class AddProveedorDialogFragment extends DialogFragment {
    public static AddProveedorDialogFragment newInstance() {
        return new AddProveedorDialogFragment();
    }
    private View view;

    //LOS COMPONENTES DEL XML
    RadioGroup grupoTipoCliente;
    RadioButton fisica,juridica;
    EditText vrucvericliente,vrucdivcliente,vnombrecliente;
    Button btnRegistrar;

    //COPIADO DE LA PAGINA ANDROID DEVELOP USO DE DIALOGFRAGMEN
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Proveedor");
        //builder.setMessage("Introduzca los datos del nuevo Ejercicio")
                builder.setPositiveButton("Grabar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // AQUI SE VA A GRABAR SI ES ACEPTAR
                        //int anho = Integer.parseInt(etAnho.getText().toString());
                        //YA TENEMOS LA COMUNICACION CON EL VIEWMODEL PARA EL NUEVO DATOO.. EJERCICIO
                        //SE TENDRA UNA SOLA INSTANCIA UNICA = SI HAY DOS CLASES QUE LO INVOCAN
                        //RECIBIRAN LA MISMA INSTANCIA SI ESTAN EN EL MISMO CONTEXTO
                       save(dialog);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        //SE CREA EL INFLATER PARA ASOCIAR EL XML
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialogfragment_addproveedor,null);
        //AQUI SE PUEDEN INSTANCIAR LOS ELEMENTOS
        inicializacion();
        //INSTANCIAR CLASE PARA CAPTURAR EL DIVIDOR DEL RUC
        final CalculaDivisor calculaDivisor = new CalculaDivisor();
        //PARA QUE CALCULE EL CODIGO VERIFICADRO DEL RUC
        vrucvericliente.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String cicargado = vrucvericliente.getText().toString().trim();
                    vrucdivcliente.setText(String.valueOf(calculaDivisor.Calcular_divisor(cicargado,11)));
                }
            }
        });
        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    //METODO PARA GRABAR CONTRIBUYENTE
    private void save(DialogInterface dialog) {
        String rucveri = vrucvericliente.getText().toString();
        String rucdiv = vrucdivcliente.getText().toString();
        String nombrecliente = vnombrecliente.getText().toString();
        String ruc = String.valueOf(rucveri).trim().concat("-").concat(String.valueOf(rucdiv).trim());
        String tipocliente="";
        //TIPO RUC = 0-FISICA  1-JURIDICA
        if (fisica.isChecked()){
            tipocliente="0";
        }else{
            tipocliente="1";
        }

        if(rucveri.trim().isEmpty()){
            //vrucvericliente.setError("Debes ingresar la cedula");
            Toast.makeText(view.getContext(),"No Registrado Ingresar Ruc y Nombre",Toast.LENGTH_LONG).show();
            return;
        }else if(rucdiv.trim().isEmpty()){
            //vrucdivcliente.setError("Debes ingresar el DIV");
            return;
        }else if(nombrecliente.trim().isEmpty()){
            //vnombrecliente.setError("Debes ingresar el nombre y apellido");
            Toast.makeText(view.getContext(),"No Registrado Ingresar Ruc y Nombre",Toast.LENGTH_LONG).show();
            return;
        }else{
            //INSTANCIA UNICA COMO SI FUERA SINGLETON
            ProveedorViewModel mViewModel = ViewModelProviders.of(getActivity()).get(ProveedorViewModel.class);

            Proveedor proveedor = new Proveedor(nombrecliente,ruc,Integer.parseInt(tipocliente),
                    Integer.parseInt(rucveri),Integer.parseInt(rucdiv));
            if (mViewModel.verificaCedula(String.valueOf(proveedor.getRuc())) == 0){
                mViewModel.insert(proveedor);
                Toast.makeText(getActivity(),"Proveedor Registrado con exito", Toast.LENGTH_SHORT).show();
              //  dialog.dismiss();
            }else {
                Toast.makeText(getActivity(),"Proveedor Ya está registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void inicializacion() {
        grupoTipoCliente = view.findViewById(R.id.rbgTipoProveedor);
        grupoTipoCliente.clearCheck();
        grupoTipoCliente.check(R.id.rbFisicaPn);

        fisica = view.findViewById(R.id.rbFisicaPn);
        juridica = view.findViewById(R.id.rbJuridicaPn);

        vrucvericliente = view.findViewById(R.id.txtRucVeriProveedorn);
        vrucdivcliente = view.findViewById(R.id.txtRucDivProveedorn);
        vnombrecliente = view.findViewById(R.id.txtNombreProveedorn);

        //btnRegistrar = view.findViewById(R.id.btnGrabaProveedor);


    }

}
