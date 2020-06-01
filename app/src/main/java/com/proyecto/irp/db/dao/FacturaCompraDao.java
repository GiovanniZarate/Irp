package com.proyecto.irp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.proyecto.irp.db.entity.EstadisticaVentas;
import com.proyecto.irp.db.entity.Facturacompra;
import com.proyecto.irp.db.entity.Facturaventa;
import com.proyecto.irp.db.entity.ReporteLibroCompra;
import com.proyecto.irp.db.entity.ReporteLibroVenta;

import java.util.List;

@Dao
public interface FacturaCompraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Facturacompra facturacompra);

    @Update
    void update(Facturacompra facturacompra);

    @Delete
    void delete(Facturacompra facturacompra);


    @Query("SELECT facturacompra.*,tipocomprobante.descripcion_tipocomprobante,proveedor.nombre, " +
            "clasificacionegreso.descripcion, tipoegreso.descripcion_tipoegreso " +
            "FROM facturacompra " +
            "INNER JOIN tipocomprobante on facturacompra.id_comprobante=tipocomprobante.idtipocomprobante " +
            "INNER JOIN proveedor on facturacompra.id_proveedor=proveedor.idproveedor " +
            "INNER JOIN tipoegreso on facturacompra.id_tipoegresocompra=tipoegreso.idtipoegreso " +
            "INNER JOIN clasificacionegreso on facturacompra.id_clasificacionegreso " +
            "=clasificacionegreso.idclasificacionegreso " +
            "where facturacompra.id_contribuyente= :codcontribuyente " +
            "and facturacompra.id_ejercicio= :codejercicio " +
            "order by idfacturacompra ASC")
    LiveData<List<Facturacompra>> getAllFacturacompra(int codcontribuyente, int codejercicio);


    @Query("SELECT f.total_compra AS total_compra,t.descripcion_tipocomprobante AS tipodocumento, " +
            "f.exenta_compra AS exenta_compra,f.gravada5_compra AS gravada5_compra, " +
            "f.iva5_compra AS iva5_compra,f.gravada10_compra AS gravada10_compra,f.iva10_compra AS iva10_compra, " +
            "(gravada5_compra - iva5_compra) as importesiniva5, " +
            "(gravada10_compra - iva10_compra) as importesiniva10, " +
            "f.nrofacturacompra AS nrofacturacompra, pr.nombre AS proveedor,pr.ruc AS rucproveedor, " +
            "trim(f.dia_compra)||'/'||trim(f.mes_compra)||'/'||trim(f.anho_compra) AS fechaventaformato, " +
            "0 AS orden " +
            "FROM facturacompra f " +
            "LEFT JOIN proveedor pr ON pr.idproveedor = f.id_proveedor " +
            "LEFT JOIN tipocomprobante t ON t.idtipocomprobante = f.id_comprobante " +
            "WHERE id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "AND f.fec_compra BETWEEN date(:desde) and date(:hasta) " +
            " UNION " +
            "SELECT sum(ff.total_compra) AS total_compra,'' AS tipodocumento, " +
            "sum(ff.exenta_compra) AS exenta_compra,sum(ff.gravada5_compra) AS gravada5_compra, " +
            "sum(ff.iva5_compra) AS iva5_compra,sum(ff.gravada10_compra) AS gravada10_compra, " +
            "sum(ff.iva10_compra) AS iva10_compra, " +
            "sum((gravada5_compra - iva5_compra)) as importesiniva5, " +
            "sum((gravada10_compra - iva10_compra)) as importesiniva10, " +
            "'Totales:' AS nrofacturacompra,'' AS proveedor,'' AS rucproveedor, " +
            "'' AS fechaventaformato ,1 AS orden " +
            "FROM facturacompra ff " +
            "WHERE id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "AND ff.fec_compra BETWEEN date(:desde) and date(:hasta) " +
            "ORDER BY orden ASC ")
    List<ReporteLibroCompra> getLibroCompra(int codcontribuyente, int codejercicio, String desde, String hasta);



    //PARA TRAER EL TOTAL DE VENTA PARA MOSTRAR EN EL GRAFICO

    /*@Query("SELECT anho,sum(total_venta) totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio " +
            "union " +
            "SELECT anho,1500000 totalventa from facturaventa " +
            "join ejercicio on facturaventa.id_ejercicio=ejercicio._id " +
            "where id_contribuyente=:codcontribuyente and id_ejercicio=:codejercicio " +
            "GROUP by id_ejercicio ")
    LiveData<List<EstadisticaVentas>> getTotalVenta(int codcontribuyente, int codejercicio);*/






    /*@Query("SELECT clasificacionegreso.*,tipoegreso.descripcion_tipoegreso AS descripcion_tipoegreso
    FROM clasificacionegreso "
            +"INNER JOIN tipoegreso  on clasificacionegreso.id_tipoegreso=tipoegreso.idtipoegreso "
            +"order by idclasificacionegreso ASC ")
    public LiveData<List<ClasificacionEgreso>> getAllClasificacionegreso();*/
}
