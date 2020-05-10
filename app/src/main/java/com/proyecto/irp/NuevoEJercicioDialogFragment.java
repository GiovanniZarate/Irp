package com.proyecto.irp;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.proyecto.irp.db.entity.Ejercicio;
import com.proyecto.irp.viewmodel.NuevoEJercicioDialogViewModel;

//public class NuevoEJercicioDialogFragment extends Fragment {
//CAMBIAR A DIALOGFRAGMENT
public class NuevoEJercicioDialogFragment extends DialogFragment {

    //private NuevoEJercicioDialogViewModel mViewModel;

    public static NuevoEJercicioDialogFragment newInstance() {
        return new NuevoEJercicioDialogFragment();
    }

    private View view;

    //LOS COMPONENTES DEL XML
    private EditText etAnho;

   /* //AQUI ESTA SIENDO CREADO EL LAYOUT EN EL FRAGMENT  los oviamos xq retorna en onCreateDialog
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nuevo_e_jercicio_dialog_fragment, container, false);
    }*/

   //QUITAMOS DE AQUI PARA INSTANCIAR CUANDO SE VA A USAR

   /*

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NuevoEJercicioDialogViewModel.class);
        // TODO: Use the ViewModel
    }
*/


    //COPIADO DE LA PAGINA ANDROID DEVELOP USO DE DIALOGFRAGMEN
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Nuevo Ejercicio");
        builder.setMessage("Introduzca los datos del nuevo Ejercicio")
                .setPositiveButton("Grabar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // AQUI SE VA A GRABAR SI ES ACEPTAR
                        int anho = Integer.parseInt(etAnho.getText().toString());

                        //YA TENEMOS LA COMUNICACION CON EL VIEWMODEL PARA EL NUEVO DATOO.. EJERCICIO
                        //SE TENDRA UNA SOLA INSTANCIA UNICA = SI HAY DOS CLASES QUE LO INVOCAN
                        //RECIBIRAN LA MISMA INSTANCIA SI ESTAN EN EL MISMO CONTEXTO
                        //INSTANCIA UNICA COMO SI FUERA SINGLETON
                        NuevoEJercicioDialogViewModel mViewModel = ViewModelProviders.of(getActivity())
                                .get(NuevoEJercicioDialogViewModel.class);

                        mViewModel.insertarEjercicio(new Ejercicio(0,anho,0,
                                0,0,0,0,0));
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        dialog.dismiss();
                    }
                });

        //SE CREA EL INFLATER PARA ASOCIAR EL XML
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nuevo_e_jercicio_dialog_fragment,null);

        //AQUI SE PUEDEN INSTANCIAR LOS ELEMENTOS
        etAnho = view.findViewById(R.id.txtAnho);

        builder.setView(view);


        // Create the AlertDialog object and return it
        return builder.create();
    }

}
