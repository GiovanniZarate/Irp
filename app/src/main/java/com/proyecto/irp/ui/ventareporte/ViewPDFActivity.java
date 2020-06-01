package com.proyecto.irp.ui.ventareporte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.proyecto.irp.R;

import java.io.File;

public class ViewPDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);
        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            file = new File(bundle.getString("path",""));
        }

        //CARGAR EL PDF
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }
}
