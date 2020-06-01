package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.entity.ReporteLibroVenta;

import java.util.Date;
import java.util.List;

@Dao
public interface FacturaVentaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Facturaventa facturaventa);

    @Update
    void update(Facturaventa facturaventa);

    @Delete
    void delete(Facturaventa facturaventa);


    @Query("SELECT facturaventa.*,tipocomprobante.descripcion_tipocomprobante,cliente.nombre, " +
            "clasificacioningreso.descripcion_clasificacioningreso " +
            "FROM facturaventa " +
            "INNER JOIN tipocomprobante on facturaventa.id_comprobante=tipocomprobante.idtipocomprobante " +
            "INNER JOIN cliente on facturaventa.id_cliente=cliente.idcliente " +
            "INNER JOIN clasificacioningreso on facturaventa.id_clasificacioningreso " +
            "=clasificacioningreso.idclasificacioningreso " +
            "where facturaventa.id_contribuyente= :codcontribuyente " +
            "and facturaventa.id_ejercicio= :codejercicio " +
            "order by idfacturaventa ASC")
    LiveData<List<Facturaventa>> getAllFacturaventa(int codcontribuyente,int codejercicio);


    //PARA TRAER EL TOTAL DE VENTA PARA MOSTRAR EN EL GRAFICO

    @Query("SELECT anho,sum(total_venta) totalventa,1 orden from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio " +
            "union " +
            "SELECT anho,sum(total_compra) totalventa,2 orden from facturacompra " +
            "join ejercicio on facturacompra.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio " +
            "order by orden ASC ")
    LiveData<List<EstadisticaVentas>> getTotalVenta(int codcontribuyente, int codejercicio);


    @Query("SELECT f.total_venta AS total_venta,t.descripcion_tipocomprobante AS tipodocumento, " +
            "f.exenta_venta AS exenta_venta,f.gravada5_venta AS gravada5_venta, " +
            "f.iva5_venta AS iva5_venta,f.gravada10_venta AS gravada10_venta,f.iva10_venta AS iva10_venta, " +
            "(gravada5_venta - iva5_venta) as importesiniva5, " +
            "(gravada10_venta - iva10_venta) as importesiniva10, " +
            "f.nrofacturaventa AS nrofacturaventa, pr.nombre AS cliente,pr.ruc AS ruccliente, " +
            "trim(f.dia_venta)||'/'||trim(f.mes_venta)||'/'||trim(f.anho_venta) AS fechaventaformato, " +
            "0 AS orden " +
            "FROM facturaventa f " +
            "LEFT JOIN cliente pr ON pr.idcliente = f.id_cliente " +
            "LEFT JOIN tipocomprobante t ON t.idtipocomprobante = f.id_comprobante " +
            "WHERE id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "AND f.fec_venta BETWEEN date(:desde) and date(:hasta) " +
            " UNION " +
            "SELECT sum(ff.total_venta) AS total_venta,'' AS tipodocumento, " +
            "sum(ff.exenta_venta) AS exenta_venta,sum(ff.gravada5_venta) AS gravada5_venta, " +
            "sum(ff.iva5_venta) AS iva5_venta,sum(ff.gravada10_venta) AS gravada10_venta, " +
            "sum(ff.iva10_venta) AS iva10_venta, " +
            "sum((gravada5_venta - iva5_venta)) as importesiniva5, " +
            "sum((gravada10_venta - iva10_venta)) as importesiniva10, " +
            "'Totales:' AS nrofacturaventa,'' AS cliente,'' AS ruccliente, " +
            "'' AS fechaventaformato ,1 AS orden " +
            "FROM facturaventa ff " +
            "WHERE id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "AND ff.fec_venta BETWEEN date(:desde) and date(:hasta) " +
            "ORDER BY orden ASC ")
    List<ReporteLibroVenta> getLibroVenta(int codcontribuyente, int codejercicio, String desde, String hasta);


    /*@Query("SELECT * FROM User WHERE date_of_birth BETWEEN date(:from) AND date(:to)")
    LiveData<List<User>> fetchUserBetweenDate(String from, String to);*/










    /*@Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso
    FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/
}
