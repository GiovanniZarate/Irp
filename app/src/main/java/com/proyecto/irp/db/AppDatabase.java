package com.proyecto.irp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.proyecto.irp.Config.Constantes;
import com.proyecto.irp.db.dao.ClasificacionEgresoDao;
import com.proyecto.irp.db.dao.ClasificacionIngresoDao;
import com.proyecto.irp.db.dao.ClienteDao;
import com.proyecto.irp.db.dao.ContribuyenteDao;
import com.proyecto.irp.db.dao.EjercicioDao;
import com.proyecto.irp.db.dao.FacturaVentaDao;
import com.proyecto.irp.db.dao.ProveedorDao;
import com.proyecto.irp.db.dao.TipoComprobanteDao;
import com.proyecto.irp.db.dao.TipoEgresoDao;
import com.proyecto.irp.db.entity.ClasificacionEgreso;
import com.proyecto.irp.db.entity.ClasificacionIngreso;
import com.proyecto.irp.db.entity.Cliente;
import com.proyecto.irp.db.entity.Contribuyente;
import com.proyecto.irp.db.entity.Ejercicio;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.entity.Proveedor;
import com.proyecto.irp.db.entity.TipoComprobante;
import com.proyecto.irp.db.entity.TipoEgreso;


//paso 1 - LOS PERMISOS PARA ACCEDER A LAS ENTIDADES
@Database(entities = {Ejercicio.class, Contribuyente.class, Cliente.class,
        ClasificacionIngreso.class, TipoComprobante.class, Proveedor.class, TipoEgreso.class,
        ClasificacionEgreso.class, Facturaventa.class}, version = 28,exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {



    public abstract EjercicioDao getEjercicioDao();
    public abstract ContribuyenteDao contribuyenteDao();
    public abstract ClienteDao clienteDao();
    public abstract ClasificacionIngresoDao clasificacionIngresoDao();
    public abstract TipoComprobanteDao tipoComprobanteDao();
    public abstract ProveedorDao proveedorDao();
    public abstract TipoEgresoDao tipoEgresoDao();
    public abstract ClasificacionEgresoDao clasificacionEgresoDao();

    public abstract FacturaVentaDao facturaVentaDao();

    //Para crear instancia de la base de datos
    //Verificar si no existe crear de caso contrario
    private static  AppDatabase INSTANCE;

    //EL METODO QUE VA A VERIFICAR SI YA EXISTE LA INSTANCIA A LA BASE DE DATOS
    public static synchronized AppDatabase getDatabase(Context context){
        if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constantes.BD_NAME)
                            //.fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)  //PARA INSERTAR LOS DATOS DE PREUBA
                            .build();
        }
        //caso contrario ya existe la instancia y retorna el mismo
        return INSTANCE;
    }

    //PARA INSERTAR DATOS DE PRUEBA AL INICIAR
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private ClasificacionIngresoDao clasificacionIngresoDao;
        private TipoComprobanteDao tipoComprobanteDao;
        private TipoEgresoDao tipoEgresoDao;
        private ClasificacionEgresoDao clasificacionEgresoDao;
        private ClienteDao clienteDao;
        private ProveedorDao proveedorDao;
        private EjercicioDao ejercicioDao;

        private FacturaVentaDao facturaVentaDao;

        private PopulateDbAsyncTask(AppDatabase db){
            clasificacionIngresoDao = db.clasificacionIngresoDao();
            tipoComprobanteDao = db.tipoComprobanteDao();
            tipoEgresoDao = db.tipoEgresoDao();
            clasificacionEgresoDao = db.clasificacionEgresoDao();
            clienteDao = db.clienteDao();
            proveedorDao = db.proveedorDao();
            ejercicioDao = db.getEjercicioDao();
            facturaVentaDao = db.facturaVentaDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //contribuyenteDao.insert(new Contribuyente("4203593","4203593-7","Carlos Giovanni Zarate Ruiz","123"));

            ///CLASIFICACION DE INGRESO POR DEFECTO
            clasificacionIngresoDao.insert(new ClasificacionIngreso("Honorarios Profesionales y otras remuneraciones percibidas por servicios prestados"));
            clasificacionIngresoDao.insert(new ClasificacionIngreso("Dividendos y utilidades"));
            clasificacionIngresoDao.insert(new ClasificacionIngreso("Venta Ocacional de Inmuebles, cesión de derechos, venta de títulos, acciones, cuotas de capital y similares"));
            clasificacionIngresoDao.insert(new ClasificacionIngreso("Intereses, comisiones o redimientos de Capitales Mobiliarios e Inmobiliarios(Ej.: Venta de Bienes Muebles; Alquiler de Muebles e Inmuebles)"));
            clasificacionIngresoDao.insert(new ClasificacionIngreso("Otros Ingresos Gravados o No Gravados por el IRP"));
            //TIPO DE COMPROBANTEO POR DEFECTO
            // tipocpb : 0 - INGRESO
            tipoComprobanteDao.insert(new TipoComprobante("Factura",0));
            tipoComprobanteDao.insert(new TipoComprobante("Nota de Crédito",0));
            tipoComprobanteDao.insert(new TipoComprobante("Liquidación de Salario",0));
            tipoComprobanteDao.insert(new TipoComprobante("Extracto de Cuenta (cuando no exista la obligación de emitir comprobantes de ventas)",0));
            tipoComprobanteDao.insert(new TipoComprobante("Otros Documentos que respalden los ingresos (cunado no exista la obligación de emitir comprobantes de ventas)",0));
            //1- EGRESO
            tipoComprobanteDao.insert(new TipoComprobante("Factura",1));
            tipoComprobanteDao.insert(new TipoComprobante("Autofactura",1));
            tipoComprobanteDao.insert(new TipoComprobante("Boleta de Venta",1));
            tipoComprobanteDao.insert(new TipoComprobante("Nota de Crédito",1));
            //FALTA MAS VER LUEGO

            //TIPO DE EGRESO
            tipoEgresoDao.insert(new TipoEgreso("Gasto"));
            tipoEgresoDao.insert(new TipoEgreso("Inversiones Relacionadas a la Actividad Gravada"));
            tipoEgresoDao.insert(new TipoEgreso("Inversiones Personales y de familiares a Cargo"));

            //CLASIFICACION EGRESO DEPENDE DE TIPO EGRESO
            //1- GASTO
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Gastos personales y de familiares a cargo realizados en el país",1));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Gastos relacionados a la actividad gravada realizados en el pais",1));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Donaciones",1));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Amortización o cancelación de préstamos obtenidos antes de ser contribuyente del IRP, así como sus intereses, comisiones y otros recargos",1));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Cuotas de capital de las financiaciones, así como los intereses, las comisiones y otros recargos pagados por la adquisición de bienes o servicios",1));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Intereses, comisiones y otros recargos pagados por los préstamos obtenidos, con posterioridad a ser contribuyente del IRP",1));
            //2- INVERSIONES RELACIONADAS A LA ACTIVIDAD GRAVADA
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Muebles, Equipos y Herramientas",2));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Adquisición de inmuebles, construcción o mejoras de inmuebles",2));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Educación y/o Capacitación",2));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Inversión en licencias, franquicias y otros similares",2));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Compra de acciones o cuotas partes de sociedades constituídas en el país",2));
            //3- INVERSIONES PERSONALES Y DE FAMILIARES A CARGO
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Adquisición de inmuebles, construcción o mejoras de inmuebles",3));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Educación y/o Capacitación",3));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Colocaciones de dinero",3));
            clasificacionEgresoDao.insert(new ClasificacionEgreso("Salud",3));


            //CLIENTE DE PRUEBA
            clienteDao.insert(new Cliente("Carlos Giovanni Zarate Ruiz","4203593-7",0,4203593,7));

            //PROVEEDOR DE PRUEBA
            proveedorDao.insert(new Proveedor("Carlos Giovanni Zarate Ruiz","4203593-7",0,4203593,7));


            //EJERECICIO DEBE TOMAR EL AÑO ACTUAL
            ejercicioDao.insert(new Ejercicio(1,2020,0,0,0,0,0,0));


            //FACTURA VENTA - INGRESO PRUEBA CARGA
            Facturaventa facturaventa =
                    new Facturaventa(25052020,1,1,1,1,1,
                            "001-002-0000123",1500000,0,0,0,0,
                            0,"1","2","123","20","5","2020");
            facturaVentaDao.insert(facturaventa);

            return null;
        }
    }

}
