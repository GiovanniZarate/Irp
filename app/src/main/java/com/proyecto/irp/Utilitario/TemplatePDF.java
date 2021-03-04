package com.proyecto.irp.Utilitario;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto.irp.db.entity.ReporteLibroVenta;
import com.proyecto.irp.ui.ventareporte.ViewPDFActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemplatePDF {
    private Context context;
    private Activity activity;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD, BaseColor.RED);

    //METODO CONTRUCTOR QUE RECIBE EL CONTEXTO DE LA APLICACION
    public  TemplatePDF(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }
    //ABRIR DOCUMENTO
    public void openDocument(){
        createFile();
        try {
            document = new Document(PageSize.A4.rotate());
            pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    //CREAR ARCHIVO EN EL DISPOSITIVO
   /* private void createFile(){
        File folder = new File(Environment.getExternalStorageDirectory().toString() ,"PDF");

        //SI NO EXISTE CARPETA
        if (!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder,"templatePDF.pdf");
        Toast.makeText(context,"archivo que se crea "+pdfFile,Toast.LENGTH_LONG).show();
    }*/

    /*private void createFile() {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if (!folder.exists()){
            //Crea directorio
            folder.mkdirs();
            pdfFile = new File(folder, "templatePDF.pdf");
            //Crea archivo
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

   /* private void permisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            int permissionCheck = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No se tiene permiso para leer.");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                //ActivityCompat.requestPermissions(getClass(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            } else {
                Log.i("Mensaje", "Se tiene permiso para leer!");
            }
        }

        /*if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //-- Tiene permisos hace la lectura de tu pdf
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),REQUEST_PERMISSION_CODE);
        }*/
   /* }*/


    private void createFile(){
        //permisos();
        //File folder=new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        File folder=new File(context.getExternalFilesDir("PDF"),"PDF");
        if (folder.exists()){
            folder.mkdirs();
            pdfFile=new File(folder,"templatePDF.pdf");
        }else if (!folder.exists()){
            folder.mkdirs();
            pdfFile=new File(folder,"templatePDF.pdf");
        }else{
            Log.i("Mensaje", "NO TIENE PERMISO PARA CREAR ARCHIVO!");
        }
    }




    public void closeDocument(){
        document.close();
    }

    //AGREGAR LA CABECERA
    public void addMetaData(String title,String subject,String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title,String subTitle,String date){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            addChildP(new Paragraph(subTitle,fSubTitle));
            addChildP(new Paragraph(date,fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles",e.toString());
        }
    }

    //para agregar parrafos hijos en el parrafo padre de arriba
    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        //aqui agrega al parrafo padre el parrafo hijo
        paragraph.add(childParagraph);
    }

    //AGREGAR PARRAFO
    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text,fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }

    //CREAR TABLAS en itext
    public void createTable(String[] header, ArrayList<String[]>facturafilas){
        try {
        paragraph=new Paragraph();
        paragraph.setFont(fText);
        //crea la tabla
        PdfPTable pdfPTable = new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);//tomar el ancho completo
             pdfPTable.setSpacingBefore(10);
        //crea la columna
        PdfPCell pdfPCell;
        int indexC=0;
        //AQUI SE AGREGAN LAS CELDAS DE ENCABEZADO LOS TITULOS
        while (indexC<header.length){
            pdfPCell = new PdfPCell(new Phrase(header[indexC++],fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GREEN);
            pdfPTable.addCell(pdfPCell);
        }
        //para las filas del detalles
        for (int indexRow=0;indexRow<facturafilas.size();indexRow++){
                String[] row = facturafilas.get(indexRow); //SE OBIENTE LA FILA Y SE VA GUARDONDO EN ROW
            //PARA RECORRER LAS COLUMNAS QUE SE ENCUENTRAN EN LA FILA
            for (indexC=0; indexC<header.length;indexC++){
                //SE CREA LA NUEVA CELDA
                pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(50);//tamaño de la celda
                pdfPTable.addCell(pdfPCell);
            }
        }
        paragraph.add(pdfPTable);
        document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }

    //para mostrar el pdf
   /* public void viewPDF(){
        Intent intent = new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/

    public void viewPDF() {

        //File file = new File(Environment.getExternalStorageDirectory().toString() + "/"+  "PDF", "templatePDF.pdf");

       // if (file.exists()){
            //Archivo existe.
            Intent intent = new Intent(context, ViewPDFActivity.class);
            intent.putExtra("path",pdfFile.getAbsolutePath());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        //}else{
           // Log.e("templatePDF", "Archivo no existe!");
        //}

    }

    //para abrir con pdf
    public void appViewPDF(Activity activity){

        //File file = new File(context.getExternalFilesDir());
        File file=new File(context.getExternalFilesDir("PDF"),"PDF"+"/templatePDF.pdf");

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity.getApplicationContext(),"No cuentas con una aplicación par abrir el archivo PDF",Toast.LENGTH_LONG).show();
        }


        /*if (pdfFile.exists()){
            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"application/pdf");
            try {
                activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                //activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.google.android.apps.pdfviewer&hl=es")));
                Toast.makeText(activity.getApplicationContext(),"No cuentas con una aplicación par abrir el archivo PDF",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(activity.getApplicationContext(),"Archivo No encontrado",Toast.LENGTH_LONG).show();
        }*/
    }

}
